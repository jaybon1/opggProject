package com.jaybon.opgg.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

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

    // 리사이클러뷰 데이터
    private List<RankingDto> rankingDtos = new ArrayList<>();

    // 콜백 레퍼런스
    private ItemClickCallback itemClickCallback;

    // 로딩 중복을 방지하기 위한 변수
    boolean nowLoading =false;

    // 아이템 개별추가
    public void addContent(RankingDto rankingDto) {
        rankingDtos.add(rankingDto);
        Log.d(TAG, "addContent: 아이템 추가됨");
    }

    // 아이템 통으로 추가
    public void addContents(List<RankingDto> rankingDtos) {
        Log.d(TAG, "addContents: 호출됨");
        this.rankingDtos = rankingDtos;
    }

    // 외부로 데이터 전달
    public List<RankingDto> getRankingDtos() {
        return rankingDtos;
    }

    // 외부에서 로딩여부 가져오기
    public void setNowLoading(boolean nowLoading) {
        this.nowLoading = nowLoading;
    }

    // 뷰홀더 타입
    @Override
    public int getItemViewType(int position) {
        return rankingDtos.get(position).getType();
    }

    // 생성자
    public RankAdapter(ItemClickCallback itemClickCallback) {
        this.itemClickCallback = itemClickCallback;


    }

    // 뷰홀더 생성
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        // type 0 = header / 2 = footer
        if (viewType == 1) {
            RankItemBinding rankItemBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.getContext()), R.layout.rank_item, parent, false
            );
            return new MyViewHolder(rankItemBinding);

        } else if(viewType == 0) {

            RankHeaderBinding rankHeaderBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.getContext()), R.layout.rank_header, parent, false
            );
            return new HeaderViewHolder(rankHeaderBinding, itemClickCallback);

        } else {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.rank_load,parent,false);
            return new LoadingViewHolder(view);
        }
    }

    // 뷰홀더에 데이터 연결
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RankingDto rankingDto = rankingDtos.get(position);
        if (holder instanceof MyViewHolder) {
            ((MyViewHolder) holder).rankItemBinding.setRankingDto(rankingDto);
        } else if (holder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) holder).rankHeaderBinding.setRankingDto(rankingDto);
        }

        if(rankingDtos.size()-1 == position && !nowLoading){
            nowLoading = true;
            rankingDtos.add(RankingDto.builder().type(2).build());
            Log.d(TAG, "onBindViewHolder: rankingDtos.add(RankingDto.builder().type(2).build());");
            itemClickCallback.onClick();
        }
    }

    // 데이터 사이즈 체크
    @Override
    public int getItemCount() {
        return rankingDtos.size();
    }


    // ViewHolder (뷰들의 책꽂이)
    public static class MyViewHolder extends RecyclerView.ViewHolder { // 뷰홀더

        // 데이터 바인딩
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

        // 데이터 바인딩
        private RankHeaderBinding rankHeaderBinding;

        // 콜백 레퍼런스
        private ItemClickCallback itemClickCallback;

        // 엔터키 중복입력방지를 위한 코드
        private boolean enterKeyDown;
        private boolean enterKeyUp;

        public HeaderViewHolder(@NonNull RankHeaderBinding rankHeaderBinding, ItemClickCallback itemClickCallback) {
            super(rankHeaderBinding.getRoot());
            this.rankHeaderBinding = rankHeaderBinding;
            this.itemClickCallback = itemClickCallback;
            enterKeyDown = false;
            enterKeyUp = false;

            // 터치로 검색
            rankHeaderBinding.ivSearchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.d(TAG, "onClick: "+rankHeaderBinding.etSearchInput.getText());

                    if(rankHeaderBinding.etSearchInput.getText() == null || rankHeaderBinding.etSearchInput.getText().toString().equals("")){

                        Log.d(TAG, "onClick: 널 또는 공백");
                        Toast.makeText(rankHeaderBinding.getRoot().getContext(), "소환사명을 입력하세요.", Toast.LENGTH_SHORT).show();

                    } else {

                        Log.d(TAG, "onClick: 입력");

                        itemClickCallback.onClick(rankHeaderBinding.etSearchInput.getText().toString());
                        rankHeaderBinding.etSearchInput.setText("");

                    }
                }
            });

            // 엔터로 검색
            rankHeaderBinding.etSearchInput.setOnKeyListener(new View.OnKeyListener() {

                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {

                    Log.d(TAG, "onKey: " + keyCode);

                    if (keyCode == event.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP && !enterKeyUp) {

                        Log.d(TAG, "onKey: 엔터키업");
                        enterKeyUp = true;

                        // 액티비티 이동
                        Log.d(TAG, "onClick: "+rankHeaderBinding.etSearchInput.getText());

                        if(rankHeaderBinding.etSearchInput.getText() == null || rankHeaderBinding.etSearchInput.getText().toString().equals("")){

                            Log.d(TAG, "onClick: 널 또는 공백");
                            Toast.makeText(rankHeaderBinding.getRoot().getContext(), "소환사명을 입력하세요.", Toast.LENGTH_SHORT).show();

                        } else {

                            Log.d(TAG, "onClick: 입력");
                            itemClickCallback.onClick(rankHeaderBinding.etSearchInput.getText().toString());
                            rankHeaderBinding.etSearchInput.setText("");

                        }
                        return true;
                    } else if (keyCode == event.KEYCODE_ENTER) {

                        Log.d(TAG, "onKey: 엔터키다운");
                        enterKeyDown = true;
                        return true;

                    }
                    return false;
                }
            });

        }

        // 규칙3 뷰에 데이터넣기
        public void setContent(InfoDto communityDto) {
        }
    }

    // 로딩 뷰홀더
    public static class LoadingViewHolder extends RecyclerView.ViewHolder {

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
        }


    }

}