package com.jaybon.opgg.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.jaybon.opgg.R;
import com.jaybon.opgg.model.dto.InfoDto;
import com.jaybon.opgg.databinding.InfoHeaderBinding;
import com.jaybon.opgg.databinding.InfoItemBinding;
import com.jaybon.opgg.view.detail.DetailActivity;

import java.util.ArrayList;
import java.util.List;

public class InfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "InfoAdapter";

    private Context context;
    private OnItemClick contextListener;
    private String nowSummoner;

    private List<InfoDto> infoDtos = new ArrayList<>();

    public InfoAdapter(Context context, String nowSummoner,OnItemClick contextListener) {
        this.context = context;
        this.contextListener = contextListener;
        this.nowSummoner = nowSummoner;
    }

    public void addContent(InfoDto infoDto) {
        infoDtos.add(infoDto);
        Log.d(TAG, "addContent: 아이템 추가됨");
    }

    public void addContents(List<InfoDto> infoDtos){
        Log.d(TAG, "addContents: 호출됨");
        this.infoDtos = infoDtos;
    }

    public String getNowSummoner(){
        return nowSummoner;
    }

    @Override
    public int getItemViewType(int position) {
        return infoDtos.get(position).getType();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        // type 0 = header / 2 = footer
        if(viewType == 1)
        {

            InfoItemBinding infoItemBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.getContext()),R.layout.info_item,parent,false
            );
            return new MyViewHolder(infoItemBinding, nowSummoner);

        } else {

            InfoHeaderBinding infoHeaderBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.getContext()),R.layout.info_header,parent,false
            );
            return new HeaderViewHolder(infoHeaderBinding, contextListener);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        InfoDto infoDto = infoDtos.get(position);
        if(holder instanceof MyViewHolder) {
            ((MyViewHolder) holder).infoItemBinding.setInfoDto(infoDto);
        } else {
            ((HeaderViewHolder) holder).infoHeaderBinding.setInfoDto(infoDto);
        }
    }

    @Override
    public int getItemCount() {
        return infoDtos.size();
    }


    // ViewHolder (뷰들의 책꽂이)
    public static class MyViewHolder extends RecyclerView.ViewHolder { // 뷰홀더

        private InfoItemBinding infoItemBinding;

        public MyViewHolder(@NonNull InfoItemBinding infoItemBinding, String nowSummoner) {
//            super(itemView);

            super(infoItemBinding.getRoot());
            this.infoItemBinding = infoItemBinding;

            this.infoItemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.d(TAG, "onClick: "+infoItemBinding.getInfoDto().getMatchSummonerModel().getGameId());

                    // 리사이클러뷰에서 액티비티 전환하기
                    Intent intent = new Intent(infoItemBinding.getRoot().getContext(), DetailActivity.class);
                    intent.putExtra("gameId", infoItemBinding.getInfoDto().getMatchSummonerModel().getGameId());
                    intent.putExtra("nowSummoner", nowSummoner);
                    infoItemBinding.getRoot().getContext().startActivity(intent);

                }
            });

        }

        // 규칙3 뷰에 데이터 넣기
        public void setContent(InfoDto infoDto) {
        }
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder { // 뷰홀더

        // 규칙1 (xml이 들고있는 뷰)
        private InfoHeaderBinding infoHeaderBinding;

        public HeaderViewHolder(@NonNull InfoHeaderBinding infoHeaderBinding, OnItemClick contextListener) {
            super(infoHeaderBinding.getRoot());
            this.infoHeaderBinding = infoHeaderBinding;

            this.infoHeaderBinding.btnInfoHeaderUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    contextListener.onClick();
                    Toast.makeText(infoHeaderBinding.getRoot().getContext(), "로딩중", Toast.LENGTH_SHORT).show();
                    infoHeaderBinding.btnInfoHeaderUpdate.setBackgroundResource(R.drawable.radius_button_gray);
                    infoHeaderBinding.btnInfoHeaderUpdate.setOnClickListener(null);
                }
            });
        }

        // 규칙3 뷰에 데이터넣기
        public void setContent(InfoDto communityDto) {
        }
    }
}
