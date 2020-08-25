package com.jaybon.opgg.view.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jaybon.opgg.R;
import com.jaybon.opgg.databinding.CommunityFooterBinding;
import com.jaybon.opgg.databinding.CommunityItemBinding;
import com.jaybon.opgg.model.dto.CommunityDto;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class CommunityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "ContentAdapter";

    // 콜백 레퍼런스
    private ItemClickCallback itemClickCallback;

    // 리스트 데이터
    private List<CommunityDto> communityDtos = new ArrayList<>();

    // 아이템 개별 추가
    public void addContent(CommunityDto communityDto) {
        communityDtos.add(communityDto);
        Log.d(TAG, "addContent: 아이템 추가됨");
    }

    // 아이템 통으로 추가
    public void addContents(List<CommunityDto> communityDtos) {
        this.communityDtos = communityDtos;
    }

    // 아이템 타입(다른모양의 뷰홀더)
    @Override
    public int getItemViewType(int position) {
        return communityDtos.get(position).getType();
    }

    // 생성자
    public CommunityAdapter(ItemClickCallback itemClickCallback) {
        this.itemClickCallback = itemClickCallback;
    }

    // 뷰홀더 생성
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        // type 0 = header / 2 = footer
        if (viewType == 1) {
            View view = inflater.inflate(R.layout.community_item, parent, false);
            return new MyViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.community_footer, parent, false);
            return new FooterViewHolder(view);
        }
    }

    // 뷰홀더에 데이터 연결
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CommunityDto communityDto = communityDtos.get(position);
        if (holder instanceof MyViewHolder) {
            ((MyViewHolder) holder).setContent(communityDto);
        } else {
            ((FooterViewHolder) holder).setContent(communityDto);
        }
    }

    // 뷰홀더 사이즈
    @Override
    public int getItemCount() {
        return communityDtos.size();
    }


    // ViewHolder (뷰들의 책꽂이)
    public static class MyViewHolder extends RecyclerView.ViewHolder { // 뷰홀더

        // 규칙1 (xml이 들고있는 뷰)
        private CommunityItemBinding communityItemBinding;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            // 규칙2 뷰들을 변수에 연결
        }

        // 규칙3 뷰에 데이터 넣기
        public void setContent(CommunityDto communityDto) {
        }

        // ~시간전 계산
        public String getTimeAgo(Timestamp timestamp) {

            if (timestamp == null) {
                return "-";
            }

            long currentTime = System.currentTimeMillis(); // 현재시간
            long ts = timestamp.getTime(); // 가져온시간
            long mTime = currentTime - ts; // 시간 빼기

            if (mTime < 3600000) {
                long agoMinutes = (mTime / 60000);
                return agoMinutes + " 분 전";
            } else if (mTime < 86400000) { // 차이가 24시간 내라면 ~시간 전 리턴
                long agoHours = (mTime / 3600000);
                return agoHours + " 시간 전";
            } else {
                long agoDays = (mTime / 86400000);
                return agoDays + " 일 전";
            }
        }
    }

    public static class FooterViewHolder extends RecyclerView.ViewHolder { // 뷰홀더

        // 규칙1 (xml이 들고있는 뷰)
        private CommunityFooterBinding communityFooterBinding;

        public FooterViewHolder(@NonNull View itemView) {
            super(itemView);

        }
        // 규칙3
        public void setContent(CommunityDto communityDto) {

//            tvCommunityFooter.setText(communityDto.getPage()+"");
//            tvCommunityFooter.setVisibility(View.INVISIBLE); // 뷰를 안보이게 함
//
//            if(communityDto.getPage() == 0){
//                btnCommunityFooterPrev.setVisibility(View.INVISIBLE);
//            }
        }
    }
}

