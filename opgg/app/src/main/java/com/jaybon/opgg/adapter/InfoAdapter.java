package com.jaybon.opgg.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.jaybon.opgg.R;
import com.jaybon.opgg.api.dto.InfoDto;
import com.jaybon.opgg.databinding.InfoItemBinding;

import java.util.ArrayList;
import java.util.List;

public class InfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "InfoAdapter";

    private List<InfoDto> infoDtos = new ArrayList<>();

    public void addContent(InfoDto infoDto) {
        infoDtos.add(infoDto);
        Log.d(TAG, "addContent: 아이템 추가됨");
    }

    public void addContents(List<InfoDto> infoDtos){
        this.infoDtos = infoDtos;
    }

    @Override
    public int getItemViewType(int position) {
        return infoDtos.get(position).getType();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        InfoItemBinding infoItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),R.layout.info_item,parent,false
        );

        // type 0 = header / 2 = footer
        if(viewType == 1)
        {
            return new MyViewHolder(infoItemBinding);
        } else {
            return new HeaderViewHolder(infoItemBinding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        InfoDto infoDto = infoDtos.get(position);
        if(holder instanceof MyViewHolder) {
            ((MyViewHolder) holder).infoItemBinding.setInfoDto(infoDto);
        } else {
            ((HeaderViewHolder) holder).infoItemBinding.setInfoDto(infoDto);
        }
    }

    @Override
    public int getItemCount() {
        return infoDtos.size();
    }


    // ViewHolder (뷰들의 책꽂이)
    public static class MyViewHolder extends RecyclerView.ViewHolder { // 뷰홀더

        // 규칙1 (xml이 들고있는 뷰)
//        private LinearLayout layoutInfoItemWinOrLose;
//        private TextView tvInfoItemWinOrLose;
//        private TextView tvInfoItemGameTime;
//        private ImageView ivInfoItemProfileIcon;
//        private ImageView ivInfoItemSpell1;
//        private ImageView ivInfoItemSpell2;
//        private ImageView ivInfoItemRune1;
//        private ImageView ivInfoItemRune2;
//        private TextView tvInfoItemKill;
//        private TextView tvInfoItemDeath;
//        private TextView tvInfoItemAssist;
//        private TextView tvInfoItemKillConcerned;
//        private TextView tvInfoItemGameDate;
//        private ImageView ivInfoItemSkill1;
//        private ImageView ivInfoItemSkill2;
//        private ImageView ivInfoItemSkill3;
//        private ImageView ivInfoItemSkill4;
//        private ImageView ivInfoItemSkill5;
//        private ImageView ivInfoItemSkill6;
//        private ImageView ivInfoItemAccessory;

        private InfoItemBinding infoItemBinding;

        public MyViewHolder(@NonNull InfoItemBinding infoItemBinding) {
//            super(itemView);

            // 규칙2 뷰들을 변수에 연결
//            layoutInfoItemWinOrLose = itemView.findViewById(R.id.layout_info_item_win_or_lose);
//            tvInfoItemWinOrLose = itemView.findViewById(R.id.tv_info_item_win_or_lose);
//            tvInfoItemGameTime = itemView.findViewById(R.id.tv_info_item_game_time);
//            ivInfoItemProfileIcon = itemView.findViewById(R.id.iv_info_item_profile_icon);
//            ivInfoItemSpell1 = itemView.findViewById(R.id.iv_info_item_spell1);
//            ivInfoItemSpell2 = itemView.findViewById(R.id.iv_info_item_spell2);
//            ivInfoItemRune1 = itemView.findViewById(R.id.iv_info_item_rune1);
//            ivInfoItemRune2 = itemView.findViewById(R.id.iv_info_item_rune2);
//            tvInfoItemKill = itemView.findViewById(R.id.tv_info_item_kill);
//            tvInfoItemDeath = itemView.findViewById(R.id.tv_info_item_death);
//            tvInfoItemAssist = itemView.findViewById(R.id.tv_info_item_assist);
//            tvInfoItemKillConcerned = itemView.findViewById(R.id.tv_info_item_kill_concerned);
//            tvInfoItemGameDate = itemView.findViewById(R.id.tv_info_item_game_date);
//            ivInfoItemSkill1 = itemView.findViewById(R.id.iv_info_item_skill1);
//            ivInfoItemSkill2 = itemView.findViewById(R.id.iv_info_item_skill2);
//            ivInfoItemSkill3 = itemView.findViewById(R.id.iv_info_item_skill3);
//            ivInfoItemSkill4 = itemView.findViewById(R.id.iv_info_item_skill4);
//            ivInfoItemSkill5 = itemView.findViewById(R.id.iv_info_item_skill5);
//            ivInfoItemSkill6 = itemView.findViewById(R.id.iv_info_item_skill6);
//            ivInfoItemAccessory = itemView.findViewById(R.id.iv_info_item_accessory);

            super(infoItemBinding.getRoot());
            this.infoItemBinding = infoItemBinding;

        }

        // 규칙3 뷰에 데이터 넣기
        public void setContent(InfoDto infoDto) {
        }
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder { // 뷰홀더

        // 규칙1 (xml이 들고있는 뷰)
        private InfoItemBinding infoItemBinding;

        public HeaderViewHolder(@NonNull InfoItemBinding infoItemBinding) {
            super(infoItemBinding.getRoot());
            this.infoItemBinding = infoItemBinding;
        }

        // 규칙3
        public void setContent(InfoDto communityDto) {
        }

    }
}




