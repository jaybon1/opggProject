package com.jaybon.opgg.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jaybon.opgg.R;
import com.jaybon.opgg.dao.dto.CommunityDto;

import org.w3c.dom.Text;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CommunityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "ContentAdapter";

    private List<CommunityDto> communityDtos = new ArrayList<>();

    public void addContent(CommunityDto communityDto) {
        communityDtos.add(communityDto);
        Log.d(TAG, "addContent: 아이템 추가됨");
    }

    public void addContents(List<CommunityDto> communityDtos){
        this.communityDtos = communityDtos;
    }

    @Override
    public int getItemViewType(int position) {
        return communityDtos.get(position).getType();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        // type 0 = header / 2 = footer
        if(viewType == 1)
        {
            View view = inflater.inflate(R.layout.community_item, parent, false);
            return new MyViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.community_footer, parent, false);
            return new FooterViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CommunityDto communityDto = communityDtos.get(position);
        if(holder instanceof MyViewHolder) {
            ((MyViewHolder) holder).setContent(communityDto);
        } else {
            ((FooterViewHolder) holder).setContent(communityDto);
        }
    }

    @Override
    public int getItemCount() {
        return communityDtos.size();
    }


    // ViewHolder (뷰들의 책꽂이)
    public static class MyViewHolder extends RecyclerView.ViewHolder { // 뷰홀더

        // 규칙1 (xml이 들고있는 뷰)
        private TextView tvCommunityPostLike;
        private TextView tvCommunityPostTitle;
        private TextView tvCommunityPostTimeAgo;
        private TextView tvCommunityUserUsername;
        private TextView tvCommunityReplyCount;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            // 규칙2 뷰들을 변수에 연결
            tvCommunityPostLike = itemView.findViewById(R.id.tv_community_count_post_like);
            tvCommunityPostTitle = itemView.findViewById(R.id.tv_community_post_title);
            tvCommunityPostTimeAgo = itemView.findViewById(R.id.tv_community_post_time_ago);
            tvCommunityUserUsername = itemView.findViewById(R.id.tv_community_user_username);
            tvCommunityReplyCount = itemView.findViewById(R.id.tv_community_reply_count);
        }

        // 규칙3 뷰에 데이터 넣기
        public void setContent(CommunityDto communityDto) {
            tvCommunityPostLike.setText(communityDto.getPost().getLike()+"");
            tvCommunityPostTitle.setText(communityDto.getPost().getTitle());
            tvCommunityPostTimeAgo.setText(getTimeAgo(communityDto.getPost().getCreateDate()));
            tvCommunityUserUsername.setText(communityDto.getNickname());
            tvCommunityReplyCount.setText("["+communityDto.getReplyCount()+"]");
        }

        // ~시간전 계산
        public String getTimeAgo(Timestamp timestamp){

            if(timestamp == null){
                return "-";
            }

            long currentTime = System.currentTimeMillis(); // 현재시간
            long ts = timestamp.getTime(); // 가져온시간
            long mTime = currentTime - ts; // 시간 빼기

            if(mTime < 3600000){
                long agoMinutes = (mTime/60000);
                return agoMinutes + " 분 전";
            } else if(mTime < 86400000){ // 차이가 24시간 내라면 ~시간 전 리턴
                long agoHours = (mTime/3600000);
                return agoHours + " 시간 전";
            } else {
                long agoDays = (mTime / 86400000);
                return agoDays + " 일 전";
            }
        }
    }

    public static class FooterViewHolder extends RecyclerView.ViewHolder { // 뷰홀더

        // 규칙1 (xml이 들고있는 뷰)
        private TextView tvCommunityFooter;
        private Button btnCommunityFooterPrev;
        private Button btnCommunityFooterNext;

        public FooterViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCommunityFooter = itemView.findViewById(R.id.tv_community_footer);
            btnCommunityFooterPrev = itemView.findViewById(R.id.btn_community_footer_prev);
            btnCommunityFooterNext = itemView.findViewById(R.id.btn_community_footer_next);

        }

        // 규칙3
        public void setContent(CommunityDto communityDto) {

            tvCommunityFooter.setText(communityDto.getPage()+"");
            tvCommunityFooter.setVisibility(View.INVISIBLE); // 뷰를 안보이게 함

            if(communityDto.getPage() == 0){
                btnCommunityFooterPrev.setVisibility(View.INVISIBLE);
            }
        }

    }
}




