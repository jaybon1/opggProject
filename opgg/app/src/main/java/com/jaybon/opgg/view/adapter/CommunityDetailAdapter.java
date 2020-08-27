package com.jaybon.opgg.view.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.jaybon.opgg.R;
import com.jaybon.opgg.databinding.CommunityDetailContentBinding;
import com.jaybon.opgg.databinding.CommunityDetailReplyBinding;
import com.jaybon.opgg.model.dto.CommunityDetailDto;

import java.util.ArrayList;
import java.util.List;

public class CommunityDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "CommunityDetailAdapter";

    // 콜백 레퍼런스
    private ItemClickCallback itemClickCallback;

    // 리스트 데이터
    private List<CommunityDetailDto> communityDetailDtos = new ArrayList<>();

    // 아이템 개별 추가
    public void addContent(CommunityDetailDto communityDetailDto) {
        communityDetailDtos.add(communityDetailDto);
        Log.d(TAG, "addContent: 아이템 추가됨");
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
    public CommunityDetailAdapter(ItemClickCallback itemClickCallback) {
        this.itemClickCallback = itemClickCallback;
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

            return new CommunityDetailAdapter.ContentViewHolder(communityDetailContentBinding);

        } else {

            CommunityDetailReplyBinding communityDetailReplyBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.getContext()), R.layout.community_detail_reply, parent, false);

            return new CommunityDetailAdapter.ReplyViewHolder(communityDetailReplyBinding);

        }
    }

    // 뷰홀더에 데이터 연결
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CommunityDetailDto communityDetailDto = communityDetailDtos.get(position);

        if (holder instanceof CommunityDetailAdapter.ContentViewHolder) {
            ((ContentViewHolder) holder).communityDetailContentBinding.setCommunityDetailDto(communityDetailDto);
        } else {
            ((ReplyViewHolder) holder).communityDetailReplyBinding.setCommunityDetailDto(communityDetailDto);
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

        public ContentViewHolder(@NonNull CommunityDetailContentBinding communityDetailContentBinding) {
//            super(itemView);

            // 규칙2 뷰들을 변수에 연결
            super(communityDetailContentBinding.getRoot());
            this.communityDetailContentBinding = communityDetailContentBinding;

        }

    }

    public static class ReplyViewHolder extends RecyclerView.ViewHolder { // 뷰홀더

        // 규칙1 (xml이 들고있는 뷰)
        private CommunityDetailReplyBinding communityDetailReplyBinding;

        public ReplyViewHolder(@NonNull CommunityDetailReplyBinding communityDetailReplyBinding) {
//            super(itemView);
            super(communityDetailReplyBinding.getRoot());
            this.communityDetailReplyBinding = communityDetailReplyBinding;

        }
    }
}
