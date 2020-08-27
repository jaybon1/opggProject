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

    // 10명의 유저의 가장 많이 넣은 딜량
    private long maxDeal;

    // 현재 검색한 소환사
    private String nowSummoner;

    private MatchCommonModel matchCommonModel;
    private ItemClickCallback itemClickCallback;
    private List<MatchSummonerModel> matchSummonerModels;

    // 생성자
    public DetailAdapter() {
    }

    // 생성자
    public DetailAdapter(MatchCommonModel matchCommonModel, long maxDeal, String nowSummoner, ItemClickCallback itemClickCallback) {
        this.matchCommonModel = matchCommonModel;
        this.maxDeal = maxDeal;
        this.nowSummoner = nowSummoner;
        this.itemClickCallback = itemClickCallback;
        matchSummonerModels = new ArrayList<>();
    }

    // 개별아이템추가
    public void addContent(MatchSummonerModel matchSummonerModel) {
        matchSummonerModels.add(matchSummonerModel);
        Log.d(TAG, "addContent: 아이템 추가됨");
    }

    // 아이템 통으로 추가
    public void addContents(List<MatchSummonerModel> matchSummonerModels) {
        Log.d(TAG, "addContents: 호출됨");
        this.matchSummonerModels = matchSummonerModels;
    }

    // 뷰홀더 타입
    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    // 뷰홀더 생성
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        DetailItemBinding detailItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.detail_item, parent, false
        );
        return new MyViewHolder(detailItemBinding, itemClickCallback);
    }


    // 뷰홀더 데이터 연결
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MatchSummonerModel matchSummonerModel = matchSummonerModels.get(position);
        if (holder instanceof MyViewHolder) {
            ((MyViewHolder) holder).detailItemBinding.setMatchSummonerModel(matchSummonerModel);
            ((MyViewHolder) holder).detailItemBinding.setMatchCommonModel(matchCommonModel);
            ((MyViewHolder) holder).detailItemBinding.setMaxDeal(maxDeal);
            ((MyViewHolder) holder).detailItemBinding.pgDetail.setMax(XmlAdapter.longToint(maxDeal));
            ((MyViewHolder) holder).detailItemBinding.pgDetail.setProgress(XmlAdapter.longToint(matchSummonerModel.getTotalDamageDealtToChampions()));
            if(((MyViewHolder) holder).detailItemBinding.getMatchSummonerModel().getSummonerName().replace(" ", "").toLowerCase()
                    .equals(nowSummoner.replace(" ", "").toLowerCase())){
                ((MyViewHolder) holder).detailItemBinding.layoutDetailColor.setBackgroundResource(R.color.nowSummoner);
            }
        }
    }

    // 리스트 사이즈 확인
    @Override
    public int getItemCount() {
        return matchSummonerModels.size();
    }


    // ViewHolder (뷰들의 책꽂이)
    public static class MyViewHolder extends RecyclerView.ViewHolder { // 뷰홀더

        // 데이터바인딩
        private DetailItemBinding detailItemBinding;

        // 콜백 레퍼런스
        private ItemClickCallback itemClickCallback;

        public MyViewHolder(@NonNull DetailItemBinding detailItemBinding, ItemClickCallback itemClickCallback) {
//            super(itemView);
            super(detailItemBinding.getRoot());

            this.detailItemBinding = detailItemBinding;
            this.itemClickCallback = itemClickCallback;

            // 아이템 클릭시
            detailItemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.d(TAG, "onClick: "+detailItemBinding.getMatchSummonerModel().getSummonerName());

                    // 콜백으로 info액티비티로 이동
                    itemClickCallback.onClick(detailItemBinding.getMatchSummonerModel().getSummonerName());
                }
            });

        }

        // 규칙3 뷰에 데이터 넣기
        public void setContent() {
        }
    }
}
