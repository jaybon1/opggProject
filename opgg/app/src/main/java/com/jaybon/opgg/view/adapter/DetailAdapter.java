package com.jaybon.opgg.view.adapter;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.jaybon.opgg.R;
import com.jaybon.opgg.databinding.DetailItemBinding;
import com.jaybon.opgg.model.apidao.MatchCommonModel;
import com.jaybon.opgg.model.apidao.MatchSummonerModel;

import java.util.ArrayList;
import java.util.List;

public class DetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "DetailAdapter";
    private MatchCommonModel matchCommonModel;
    private long maxDeal;
    private String nowSummoner;
    private List<MatchSummonerModel> matchSummonerModels;

    public DetailAdapter() {
    }

    public DetailAdapter(MatchCommonModel matchCommonModel, long maxDeal, String nowSummoner) {
        this.matchCommonModel = matchCommonModel;
        this.maxDeal = maxDeal;
        this.nowSummoner = nowSummoner;
        matchSummonerModels = new ArrayList<>();
    }

    public void addContent(MatchSummonerModel matchSummonerModel) {
        matchSummonerModels.add(matchSummonerModel);
        Log.d(TAG, "addContent: 아이템 추가됨");
    }

    public void addContents(List<MatchSummonerModel> matchSummonerModels) {
        Log.d(TAG, "addContents: 호출됨");
        this.matchSummonerModels = matchSummonerModels;
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        DetailItemBinding detailItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.detail_item, parent, false
        );
        return new MyViewHolder(detailItemBinding);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MatchSummonerModel matchSummonerModel = matchSummonerModels.get(position);
        if (holder instanceof MyViewHolder) {
            ((MyViewHolder) holder).detailItemBinding.setMatchSummonerModel(matchSummonerModel);
            ((MyViewHolder) holder).detailItemBinding.setMatchCommonModel(matchCommonModel);
            ((MyViewHolder) holder).detailItemBinding.setMaxDeal(maxDeal);
            ((MyViewHolder) holder).detailItemBinding.pgDetail.setMax(XmlAdapter.longToint(maxDeal));
            ((MyViewHolder) holder).detailItemBinding.pgDetail.setProgress(XmlAdapter.longToint(matchSummonerModel.getTotalDamageDealtToChampions()));
            if(((MyViewHolder) holder).detailItemBinding.getMatchSummonerModel().getSummonerName().equals(nowSummoner)){
                ((MyViewHolder) holder).detailItemBinding.layoutDetailColor.setBackgroundResource(R.color.nowSummoner);
            }
        }
    }

    @Override
    public int getItemCount() {
        return matchSummonerModels.size();
    }


    // ViewHolder (뷰들의 책꽂이)
    public static class MyViewHolder extends RecyclerView.ViewHolder { // 뷰홀더

        private DetailItemBinding detailItemBinding;

        public MyViewHolder(@NonNull DetailItemBinding detailItemBinding) {
//            super(itemView);
            super(detailItemBinding.getRoot());
            this.detailItemBinding = detailItemBinding;

            detailItemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });


        }

        // 규칙3 뷰에 데이터 넣기
        public void setContent() {
        }
    }
}
