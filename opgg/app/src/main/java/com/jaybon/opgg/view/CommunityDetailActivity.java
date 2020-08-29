package com.jaybon.opgg.view;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.jaybon.opgg.model.dto.RespDto;
import com.jaybon.opgg.view.adapter.CommunityDetailAdapter;
import com.jaybon.opgg.view.callback.CommunityDetailCallback;
import com.jaybon.opgg.viewmodel.CommunityDetailViewModel;

import java.util.ArrayList;
import java.util.List;

public class CommunityDetailActivity extends AppCompatActivity implements CommunityDetailCallback {

    private static final String TAG = "CommunityDetailActivity";

    // 해당글의 페이지
    int page;

    // 데이터바인딩
    private ActivityCommunityDetailBinding activityCommunityDetailBinding;

    // 뷰모델
    private CommunityDetailViewModel communityDetailViewModel;

    // 리사이클러뷰 어댑터
    private CommunityDetailAdapter adapter;

    // 리사이클러뷰 데이터
    private List<CommunityDetailDto> communityDetailDtos;

    private SharedPreferences sharedPreferences;

    private int postId;

    private String jwtToken;
    private String userId;
    private String nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_detail);

        communityDetailDtos = new ArrayList<>();

        // 포스트 아이디
        postId = getIntent().getIntExtra("postId", 1);

        // 현재 유저정보
        sharedPreferences = getSharedPreferences("com.jaybon.opgg.jwt", MODE_PRIVATE);    // test 이름의 기본모드 설정, 만약 test key값이 있다면 해당 값을 불러옴.
        jwtToken = sharedPreferences.getString("jwtToken","");
        userId = sharedPreferences.getString("userId","0");
        nickname = sharedPreferences.getString("nickname","");

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
        adapter = new CommunityDetailAdapter(CommunityDetailActivity.this, page, Integer.parseInt(userId), nickname);
        activityCommunityDetailBinding.rvCommunityDetail.setAdapter(adapter);

        // 리사이클러뷰 데이터 초기화
        adapter.addContents(communityDetailDtos);

        // 뷰모델생성
        communityDetailViewModel = ViewModelProviders.of(this).get(CommunityDetailViewModel.class);

        // 뷰모델 구독
        communityDetailViewModel.subscribe().observe(this, new Observer<RespDto<CommunityDto>>() {
            @Override
            public void onChanged(RespDto<CommunityDto> respDto) {

                communityDetailDtos.clear();

                if(respDto.getStatusCode() == 201){


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
                        // 로딩화면 없애기
                        activityCommunityDetailBinding.pgCommunityDetailLoading.setVisibility(View.GONE);
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
                    activityCommunityDetailBinding.pgCommunityDetailLoading.setVisibility(View.GONE);

                } else if(respDto.getStatusCode() == 200){

                    communityDetailDtos.clear();

                    Log.d(TAG, "onChanged: "+respDto.getData().getPost());

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
                        // 로딩화면 없애기
                        activityCommunityDetailBinding.pgCommunityDetailLoading.setVisibility(View.GONE);
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
                    activityCommunityDetailBinding.pgCommunityDetailLoading.setVisibility(View.GONE);

                } else{
                    Toast.makeText(CommunityDetailActivity.this, respDto.getMessage(), Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
            }
        });

        String jwtToken = sharedPreferences.getString("jwtToken","");

        // 뷰모델 데이터 초기화
        communityDetailViewModel.initLiveData(postId, jwtToken);


    }

    // 댓글 보내기
    @Override
    public void sendReply(int postId, String replyContent) {
        activityCommunityDetailBinding.pgCommunityDetailLoading.setVisibility(View.VISIBLE);

        // 유저아이디는 넣을필요 없고, 토큰으로
//        User user = User.builder().id().build();

        Post post = Post.builder().id(postId).build();

        Reply reply = Reply.builder()
                .reply(replyContent)
                .post(post)
                .build();

        communityDetailViewModel.writeReplyRefresh(reply, jwtToken);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(CommunityDetailActivity.this, MainActivity.class);
        intent.putExtra("frag", 1);
        intent.putExtra("page", getIntent().getIntExtra("page",0));
        intent.putExtra("position", getIntent().getIntExtra("position",0));
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        overridePendingTransition(0,android.R.anim.slide_out_right);
    }
}