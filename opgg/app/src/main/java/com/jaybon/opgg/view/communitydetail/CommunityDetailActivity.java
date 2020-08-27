package com.jaybon.opgg.view.communitydetail;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.jaybon.opgg.R;
import com.jaybon.opgg.databinding.ActivityCommunityDetailBinding;
import com.jaybon.opgg.model.dao.Post;
import com.jaybon.opgg.model.dao.Reply;
import com.jaybon.opgg.model.dto.CommunityDetailDto;
import com.jaybon.opgg.model.dto.CommunityDto;
import com.jaybon.opgg.model.dto.InfoDto;
import com.jaybon.opgg.model.dto.RespDto;
import com.jaybon.opgg.view.adapter.CommunityDetailAdapter;
import com.jaybon.opgg.view.adapter.ItemClickCallback;
import com.jaybon.opgg.viewmodel.community.CommunityViewModel;
import com.jaybon.opgg.viewmodel.communitydetail.CommunityDetailViewModel;

import java.util.ArrayList;
import java.util.List;

public class CommunityDetailActivity extends AppCompatActivity implements ItemClickCallback {

    private static final String TAG = "CommunityDetailActivity";

    // 데이터바인딩
    private ActivityCommunityDetailBinding activityCommunityDetailBinding;

    // 뷰모델
    private CommunityDetailViewModel communityDetailViewModel;

    // 리사이클러뷰 어댑터
    private CommunityDetailAdapter adapter;

    // 리사이클러뷰 데이터
    private List<CommunityDetailDto> communityDetailDtos;

    private int postId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_detail);

        communityDetailDtos = new ArrayList<>();

        // 포스트 아이디
        postId = getIntent().getIntExtra("postId", 1);

        Log.d(TAG, "onCreate: "+postId);

        // 바인딩 연결
        activityCommunityDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_community_detail);

        // 뒤로가기 버튼
        activityCommunityDetailBinding.ivCommunityDetailBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // 툴바
        activityCommunityDetailBinding.ivCommunityDetailToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityCommunityDetailBinding.drawerLayoutCommunityDetail.openDrawer(Gravity.RIGHT);
            }
        });

        // 리사이클러 뷰 세팅
        activityCommunityDetailBinding.rvCommunityDetail.setLayoutManager(new LinearLayoutManager(this));

        // 리사이클러뷰 어댑터 세팅 (리스너 넘기기)
        adapter = new CommunityDetailAdapter(CommunityDetailActivity.this);
        activityCommunityDetailBinding.rvCommunityDetail.setAdapter(adapter);

        // 리사이클러뷰 데이터 초기화
        adapter.addContents(communityDetailDtos);

        // 뷰모델생성
        communityDetailViewModel = ViewModelProviders.of(this).get(CommunityDetailViewModel.class);

        // 뷰모델 구독
        communityDetailViewModel.subscribe().observe(this, new Observer<RespDto<CommunityDto>>() {
            @Override
            public void onChanged(RespDto<CommunityDto> respDto) {

                if(respDto.getStatusCode() == 200){

                    Log.d(TAG, "onChanged: "+respDto);

                    Post post = respDto.getData().getPost();

                    List<Reply> replies = post.getReplies();

                    CommunityDetailDto communityDetailDtoContent = CommunityDetailDto.builder()
                            .type(0)
                            .post(post)
                            .build();

                    communityDetailDtos.add(communityDetailDtoContent);

                    if(replies == null || replies.size() == 0){
                        adapter.addContents(communityDetailDtos);
                        adapter.notifyDataSetChanged();
                        return;
                    }

                    for (Reply reply : replies) {
                        CommunityDetailDto communityDetailDtoReply = CommunityDetailDto.builder()
                                .type(1)
                                .reply(reply)
                                .build();

                        communityDetailDtos.add(communityDetailDtoReply);
                    }

                    // 뷰가 변경되면 리사이클러뷰 어댑터에 데이터 새로 담기
                    adapter.addContents(communityDetailDtos);
                    adapter.notifyDataSetChanged();

                    // 로딩화면 없애기
//                    activityInfoBinding.pgInfoLoading.setVisibility(View.GONE);

                } else{
                    Toast.makeText(CommunityDetailActivity.this, respDto.getMessage(), Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
            }
        });

        // 뷰모델 데이터 초기화
        communityDetailViewModel.initLiveData(postId);


    }

    @Override
    public void onClick() {

    }

    @Override
    public void onClick(String value) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0,android.R.anim.slide_out_right);
    }
}