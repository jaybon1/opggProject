package com.jaybon.opgg.view.adapter;

import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.jaybon.opgg.R;
import com.jaybon.opgg.databinding.CommunityDetailContentBinding;
import com.jaybon.opgg.databinding.CommunityDetailReplyBinding;
import com.jaybon.opgg.model.dto.CommunityDetailDto;
import com.jaybon.opgg.view.CommunityDetailActivity;
import com.jaybon.opgg.view.WriteActivity;
import com.jaybon.opgg.view.callback.CommunityDetailCallback;

import java.util.ArrayList;
import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

public class CommunityDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "CommunityDetailAdapter";

    // page - 해당글의 페이지
    private int page;

    // 콜백 레퍼런스
    private CommunityDetailCallback communityDetailCallback;

    // 리스트 데이터
    private List<CommunityDetailDto> communityDetailDtos = new ArrayList<>();

    private int userId;
    private String nickname;

    // 아이템 개별 추가
    public void addContent(CommunityDetailDto communityDetailDto) {
        communityDetailDtos.add(communityDetailDto);
    }

    public List<CommunityDetailDto> getCommunityDetailDtos() {
        return communityDetailDtos;
    }

    // 아이템 통으로 추가
    public void addContents(List<CommunityDetailDto> communityDetailDtos) {
        this.communityDetailDtos = communityDetailDtos;
    }

    // 아이템 타입(다른모양의 뷰홀더)
    @Override
    public int getItemViewType(int position) {
        return communityDetailDtos.get(position).getType();
    }

    // 생성자
    public CommunityDetailAdapter(CommunityDetailCallback communityDetailCallback, int page, int userId, String nickname) {
        this.communityDetailCallback = communityDetailCallback;
        this.page = page;
        this.userId = userId;
        this.nickname = nickname;
    }

    // 뷰홀더 생성
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        // type 0 = header / 2 = footer
        if (viewType == 0) {
            CommunityDetailContentBinding communityDetailContentBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.getContext()), R.layout.community_detail_content, parent, false);

            return new CommunityDetailAdapter.ContentViewHolder(communityDetailContentBinding, communityDetailCallback, userId, nickname);

        } else {

            CommunityDetailReplyBinding communityDetailReplyBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.getContext()), R.layout.community_detail_reply, parent, false);

            return new CommunityDetailAdapter.ReplyViewHolder(communityDetailReplyBinding, communityDetailCallback);

        }
    }

    // 뷰홀더에 데이터 연결
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CommunityDetailDto communityDetailDto = communityDetailDtos.get(position);

        if (holder instanceof CommunityDetailAdapter.ContentViewHolder) { // 내용
            ((ContentViewHolder) holder).communityDetailContentBinding.setCommunityDetailDto(communityDetailDto);

            ((ContentViewHolder) holder).setPage(page);
            ((ContentViewHolder) holder).setPosition(position);

            if (userId == 0) {
                ((ContentViewHolder) holder).communityDetailContentBinding.layoutCommunityDetailReply.setVisibility(View.GONE);
            } else {
                ((ContentViewHolder) holder).communityDetailContentBinding.tvCommunityDetailReplyUser.setText(nickname);
            }

            if (userId != communityDetailDto.getPost().getUser().getId()) {
                ((ContentViewHolder) holder).communityDetailContentBinding.tvCommunityDetailDelete.setVisibility(View.GONE);
                ((ContentViewHolder) holder).communityDetailContentBinding.tvCommunityDetailUpdate.setVisibility(View.GONE);
            }

        } else { // 리플
            ((ReplyViewHolder) holder).communityDetailReplyBinding.setCommunityDetailDto(communityDetailDto);

            if (userId != communityDetailDto.getReply().getUser().getId()) {
                ((ReplyViewHolder) holder).communityDetailReplyBinding.tvCommunityDetailReplyDelete.setVisibility(View.GONE);
            } else {
                ((ReplyViewHolder) holder).communityDetailReplyBinding.tvCommunityDetailReplyDelete.setVisibility(View.VISIBLE);
                ((ReplyViewHolder) holder).communityDetailReplyBinding.tvCommunityDetailReplyNickname.setText(communityDetailDto.getReply().getUser().getNickname());
            }
        }
    }

    // 뷰홀더 사이즈
    @Override
    public int getItemCount() {
        return communityDetailDtos.size();
    }


    // ViewHolder (뷰들의 책꽂이)
    public static class ContentViewHolder extends RecyclerView.ViewHolder { // 뷰홀더

        // 규칙1 (xml이 들고있는 뷰)
        private CommunityDetailContentBinding communityDetailContentBinding;
        private CommunityDetailCallback communityDetailCallback;
        private int userId;
        private String nickname;
        private int page;
        private int position;

        public ContentViewHolder(@NonNull CommunityDetailContentBinding communityDetailContentBinding, CommunityDetailCallback communityDetailCallback, int userId, String nickname) {
//            super(itemView);

            // 규칙2 뷰들을 변수에 연결
            super(communityDetailContentBinding.getRoot());
            this.communityDetailContentBinding = communityDetailContentBinding;
            this.communityDetailCallback = communityDetailCallback;
            this.userId = userId;
            this.nickname = nickname;

            initListener();
        }

        public void setPage(int page) {
            this.page = page;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public void initListener() {

            // 좋아요늘리기
            communityDetailContentBinding.btnCommunityDetailLikecount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    communityDetailCallback.updateLikeCount(communityDetailContentBinding.getCommunityDetailDto().getPost().getId());
                }
            });

            // 글삭제
            communityDetailContentBinding.tvCommunityDetailDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(communityDetailContentBinding.getRoot().getContext());
                    builder.setMessage("삭제 하시겠습니까?");
                    builder.setPositiveButton("예",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    communityDetailCallback.deletePost(communityDetailContentBinding.getCommunityDetailDto().getPost().getId());

                                }
                            });
                    builder.setNegativeButton("아니오",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                    builder.show();

                }
            });

            // 글수정
            communityDetailContentBinding.tvCommunityDetailUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(communityDetailContentBinding.getRoot().getContext(), WriteActivity.class);
                    intent.putExtra("title", communityDetailContentBinding.tvCommunityDetailTitle.getText().toString());
                    intent.putExtra("content", communityDetailContentBinding.tvCommunityDetailContent.getText().toString());
                    intent.putExtra("postId", communityDetailContentBinding.getCommunityDetailDto().getPost().getId());
                    intent.putExtra("page", page);
                    intent.putExtra("position", position);
                    intent.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
                    communityDetailContentBinding.getRoot().getContext().startActivity(intent);

                }
            });

            // 댓글쓰기
            communityDetailContentBinding.btnCommunityDetailReplySubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // 키보드 숨기기
                    InputMethodManager imm = (InputMethodManager)  communityDetailContentBinding.getRoot().getContext().getSystemService(communityDetailContentBinding.getRoot().getContext().INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(communityDetailContentBinding.btnCommunityDetailReplySubmit.getWindowToken(), 0);

                    if(communityDetailContentBinding.etCommunityDetailReplyContent.getText() == null ||
                    communityDetailContentBinding.etCommunityDetailReplyContent.getText().toString().equals("")){
                        AlertDialog.Builder builder = new AlertDialog.Builder(communityDetailContentBinding.getRoot().getContext());
                        builder.setMessage("댓글 내용을 입력하세요.");
                        builder.setPositiveButton("확인",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                        builder.show();
                    } else {
                        communityDetailCallback.sendReply(communityDetailContentBinding.getCommunityDetailDto().getPost().getId(), communityDetailContentBinding.etCommunityDetailReplyContent.getText().toString());
                        communityDetailContentBinding.etCommunityDetailReplyContent.setText("");
                    }


                }
            });

        }

    }

    public static class ReplyViewHolder extends RecyclerView.ViewHolder { // 뷰홀더

        // 규칙1 (xml이 들고있는 뷰)
        private CommunityDetailReplyBinding communityDetailReplyBinding;
        private CommunityDetailCallback communityDetailCallback;

        public ReplyViewHolder(@NonNull CommunityDetailReplyBinding communityDetailReplyBinding, CommunityDetailCallback communityDetailCallback) {
//            super(itemView);
            super(communityDetailReplyBinding.getRoot());
            this.communityDetailReplyBinding = communityDetailReplyBinding;
            this.communityDetailCallback = communityDetailCallback;

            initListener();

        }

        private void initListener(){

            // 댓글 삭제
            communityDetailReplyBinding.tvCommunityDetailReplyDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(communityDetailReplyBinding.getRoot().getContext());
                    builder.setMessage("삭제 하시겠습니까?");
                    builder.setPositiveButton("예",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    communityDetailCallback.deleteReply(communityDetailReplyBinding.getCommunityDetailDto().getReply().getId());

                                }
                            });
                    builder.setNegativeButton("아니오",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                    builder.show();



                }
            });


        }
    }

}
