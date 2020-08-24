package com.jaybon.opgg.view.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.jaybon.opgg.R;
import com.jaybon.opgg.databinding.RankHeaderBinding;
import com.jaybon.opgg.databinding.RankItemBinding;
import com.jaybon.opgg.model.dto.InfoDto;
import com.jaybon.opgg.model.dto.RankingDto;
import com.jaybon.opgg.view.info.InfoActivity;

import java.util.ArrayList;
import java.util.List;

public class RankAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "RankAdapter";

    private List<RankingDto> rankingDtos = new ArrayList<>();

    public void addContent(RankingDto rankingDto) {
        rankingDtos.add(rankingDto);
        Log.d(TAG, "addContent: 아이템 추가됨");
    }

    public void addContents(List<RankingDto> rankingDtos) {
        Log.d(TAG, "addContents: 호출됨");
        this.rankingDtos = rankingDtos;
    }

    @Override
    public int getItemViewType(int position) {
        return rankingDtos.get(position).getType();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        // type 0 = header / 2 = footer
        if (viewType == 1) {
            RankItemBinding rankItemBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.getContext()), R.layout.rank_item, parent, false
            );
            return new MyViewHolder(rankItemBinding);

        } else {

            RankHeaderBinding rankHeaderBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.getContext()), R.layout.rank_header, parent, false
            );
            return new HeaderViewHolder(rankHeaderBinding);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RankingDto rankingDto = rankingDtos.get(position);
        if (holder instanceof MyViewHolder) {
            ((MyViewHolder) holder).rankItemBinding.setRankingDto(rankingDto);
        } else {
            ((HeaderViewHolder) holder).rankHeaderBinding.setRankingDto(rankingDto);
        }
    }

    @Override
    public int getItemCount() {
        return rankingDtos.size();
    }


    // ViewHolder (뷰들의 책꽂이)
    public static class MyViewHolder extends RecyclerView.ViewHolder { // 뷰홀더

        private RankItemBinding rankItemBinding;

        public MyViewHolder(@NonNull RankItemBinding rankItemBinding) {
//            super(itemView);

            super(rankItemBinding.getRoot());
            this.rankItemBinding = rankItemBinding;

            rankItemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // 리사이클러뷰에서 액티비티 전환하기
                    Intent intent = new Intent(rankItemBinding.getRoot().getContext(), InfoActivity.class);
                    intent.putExtra("summonerName", rankItemBinding.getRankingDto().getRankingModel().getSummonerName());
                    rankItemBinding.getRoot().getContext().startActivity(intent);

                }
            });



        }

        // 규칙3 뷰에 데이터 넣기
        public void setContent(InfoDto infoDto) {
        }
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder { // 뷰홀더

        // 규칙1 (xml이 들고있는 뷰)
        private RankHeaderBinding rankHeaderBinding;

        public HeaderViewHolder(@NonNull RankHeaderBinding rankHeaderBinding) {
            super(rankHeaderBinding.getRoot());
            this.rankHeaderBinding = rankHeaderBinding;
        }

        // 규칙3 뷰에 데이터넣기
        public void setContent(InfoDto communityDto) {
        }
    }

}