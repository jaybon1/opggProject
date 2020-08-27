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
import com.jaybon.opgg.databinding.CommunityFooterBinding;
import com.jaybon.opgg.databinding.CommunityItemBinding;
import com.jaybon.opgg.model.dto.CommunityDto;
import com.jaybon.opgg.view.communitydetail.CommunityDetailActivity;

import java.util.ArrayList;
import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

public class CommunityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "ContentAdapter";

    private int buttonType;

    // 콜백 레퍼런스
    private ItemClickCallback itemClickCallback;

    // 리스트 데이터
    private List<CommunityDto> communityDtos = new ArrayList<>();

    public void setButtonType(int buttonType){
        this.buttonType = buttonType;
    }

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
    public CommunityAdapter(ItemClickCallback itemClickCallback, int buttonType) {
        this.itemClickCallback = itemClickCallback;
        this.buttonType = buttonType;
    }

    // 뷰홀더 생성
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        // type 0 = header / 2 = footer
        if (viewType == 1) {
            CommunityItemBinding communityItemBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.getContext()), R.layout.community_item, parent, false
            );

            return new MyViewHolder(communityItemBinding, itemClickCallback);
        } else {
            CommunityFooterBinding communityFooterBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.getContext()), R.layout.community_footer, parent, false
            );

            return new FooterViewHolder(communityFooterBinding, buttonType);
        }
    }

    // 뷰홀더에 데이터 연결
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CommunityDto communityDto = communityDtos.get(position);

        Log.d(TAG, "onBindViewHolder: "+communityDto.getPost());

        if (holder instanceof MyViewHolder) {
            ((MyViewHolder) holder).communityItemBinding.setCommunityDto(communityDto);
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

        public MyViewHolder(@NonNull CommunityItemBinding communityItemBinding, ItemClickCallback itemClickCallback) {
//            super(itemView);

            // 규칙2 뷰들을 변수에 연결
            super(communityItemBinding.getRoot());
            this.communityItemBinding = communityItemBinding;

            communityItemBinding.layoutCommunity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // 리사이클러뷰에서 액티비티 전환하기
                    Intent intent = new Intent(communityItemBinding.getRoot().getContext(), CommunityDetailActivity.class);
                    intent.putExtra("postId", communityItemBinding.getCommunityDto().getPost().getId());
                    // 이전화면을 없애고 새화면을 띄운다
                    intent.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
                    communityItemBinding.getRoot().getContext().startActivity(intent);

                }
            });

        }

        // 규칙3 뷰에 데이터 넣기
        public void setContent(CommunityDto communityDto) {
        }
    }

    public static class FooterViewHolder extends RecyclerView.ViewHolder { // 뷰홀더

        // 규칙1 (xml이 들고있는 뷰)
        private CommunityFooterBinding communityFooterBinding;
        int buttonType;

        public FooterViewHolder(@NonNull CommunityFooterBinding communityFooterBinding, int buttonType) {
//            super(itemView);
            super(communityFooterBinding.getRoot());
            this.communityFooterBinding = communityFooterBinding;
            this.buttonType = buttonType;

            Log.d(TAG, "FooterViewHolder: "+buttonType);

            if(buttonType == 0){

                communityFooterBinding.btnCommunityFooterPrev.setVisibility(View.GONE);

            } else if(buttonType == 2){

                communityFooterBinding.btnCommunityFooterNext.setVisibility(View.GONE);

            }


        }
        // 규칙3
        public void setContent(CommunityDto communityDto) {
        }
    }
}

