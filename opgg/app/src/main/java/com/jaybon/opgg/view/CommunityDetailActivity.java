package com.jaybon.opgg.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.navigation.NavigationView;
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

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

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

    // 해당글 번호
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
        jwtToken = sharedPreferences.getString("jwtToken", "");
        userId = sharedPreferences.getString("userId", "0");
        nickname = sharedPreferences.getString("nickname", "");


        // 바인딩 연결
        activityCommunityDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_community_detail);

        initListener();

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

                if (respDto.getStatusCode() == 201) {


                    Post post = respDto.getData().getPost();

                    List<Reply> replies = post.getReplies();

                    CommunityDetailDto communityDetailDtoContent = CommunityDetailDto.builder()
                            .type(0)
                            .post(post)
                            .build();

                    communityDetailDtos.add(communityDetailDtoContent);

                    if (replies == null || replies.size() == 0) {
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

                } else if (respDto.getStatusCode() == 200) {

                    communityDetailDtos.clear();

                    Post post = respDto.getData().getPost();

                    List<Reply> replies = post.getReplies();

                    CommunityDetailDto communityDetailDtoContent = CommunityDetailDto.builder()
                            .type(0)
                            .post(post)
                            .build();

                    communityDetailDtos.add(communityDetailDtoContent);

                    if (replies == null || replies.size() == 0) {
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

                } else if(respDto.getStatusCode() == 204 ){

                    Intent intent = new Intent(CommunityDetailActivity.this, MainActivity.class);
                    intent.putExtra("frag", 1);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);

                } else {
                    Toast.makeText(CommunityDetailActivity.this, respDto.getMessage(), Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
            }
        });

        String jwtToken = sharedPreferences.getString("jwtToken", "");

        // 뷰모델 데이터 초기화
        communityDetailViewModel.initLiveData(postId, jwtToken);


    }

    private void initListener() {

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

        // 네비게이션바 헤더 엑스버튼
        activityCommunityDetailBinding.navCommunityDetail.getHeaderView(0).findViewById(R.id.iv_nav_header).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityCommunityDetailBinding.drawerLayoutCommunityDetail.closeDrawer(Gravity.RIGHT);
            }
        });

        // 네비게이션바 메뉴버튼
        activityCommunityDetailBinding.navCommunityDetail.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bottom_nav_search_button:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new SearchFragment()).commit();
//                        ((BottomNavigationView)findViewById(R.id.bottom_nav_search)).setSelectedItemId(R.id.bottom_nav_search_button);
                        Intent intent = new Intent(CommunityDetailActivity.this, MainActivity.class);
                        intent.putExtra("frag", 0);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                        break;
                    case R.id.bottom_nav_community_button:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new CommunityFragment()).commit();
//                        ((BottomNavigationView)findViewById(R.id.bottom_nav_search)).setSelectedItemId(R.id.bottom_nav_community_button);
                        Intent intent1 = new Intent(CommunityDetailActivity.this, MainActivity.class);
                        intent1.putExtra("frag", 1);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent1);
                        break;
                    case R.id.bottom_nav_ranking_button:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new RankFragment()).commit();
//                        ((BottomNavigationView)findViewById(R.id.bottom_nav_search)).setSelectedItemId(R.id.bottom_nav_ranking_button);
                        Intent intent2 = new Intent(CommunityDetailActivity.this, MainActivity.class);
                        intent2.putExtra("frag", 2);
                        intent2.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent2);
                        break;
                }
                return false;
            }
        });

        //로그인 버튼 리스너
        initLoginButton();
    }

    private void initLoginButton() {
        // 네비게이션바 헤더 로그인버튼
        // SharedPreferences에 값이 없으면 로그인, 있으면 로그아웃
        SharedPreferences sharedPreferences = getSharedPreferences("com.jaybon.opgg.jwt", MODE_PRIVATE);    // test 이름의 기본모드 설정, 만약 test key값이 있다면 해당 값을 불러옴.
        String jwtToken = sharedPreferences.getString("jwtToken", "");

        if (jwtToken.equals("")) {
            ((Button) activityCommunityDetailBinding.navCommunityDetail.getHeaderView(0).findViewById(R.id.btn_nav_header)).setText("로그인");
            activityCommunityDetailBinding.navCommunityDetail.getHeaderView(0).findViewById(R.id.btn_nav_header).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(CommunityDetailActivity.this, LoginActivity.class);
                    intent.putExtra("activity", "communityDetail");
                    intent.putExtra("postId", postId);
                    intent.putExtra("page", page);
                    intent.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    activityCommunityDetailBinding.drawerLayoutCommunityDetail.closeDrawer(Gravity.RIGHT);
                }
            });
        } else {
            ((Button) activityCommunityDetailBinding.navCommunityDetail.getHeaderView(0).findViewById(R.id.btn_nav_header)).setText("로그아웃");
            activityCommunityDetailBinding.navCommunityDetail.getHeaderView(0).findViewById(R.id.btn_nav_header).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(CommunityDetailActivity.this);
                    if (acct != null) {

                        // 구글 인증 관련
                        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                .requestEmail()
                                .build();

                        // 구글 로그인
                        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(CommunityDetailActivity.this, gso);

                        // 구글 로그아웃
                        mGoogleSignInClient.signOut();
                    }

                    // SharedPreferences 내용삭제
                    SharedPreferences.Editor editor = sharedPreferences.edit(); //sharedPreferences를 제어할 editor를 선언
                    editor.putString("jwtToken", null); // key,value 형식으로 저장
                    editor.putString("userId", null); // key,value 형식으로 저장
                    editor.putString("nickname", null); // key,value 형식으로 저장
                    editor.commit();    //최종 커밋. 커밋을 해야 저장이 된다.

                    activityCommunityDetailBinding.drawerLayoutCommunityDetail.closeDrawer(Gravity.RIGHT);

                    initLoginButton();

                    alert("로그아웃 되었습니다.");

                }
            });
        }
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
    public void deletePost(int postId) {

        activityCommunityDetailBinding.pgCommunityDetailLoading.setVisibility(View.VISIBLE);
        communityDetailViewModel.deletePost(postId, jwtToken);

}

    @Override
    public void deleteReply(int replyId) {

        communityDetailViewModel.deleteReply(replyId, jwtToken);

    }

    @Override
    public void updateLikeCount(int postId) {

        SharedPreferences sharedPreferences = getSharedPreferences("com.jaybon.opgg.jwt", MODE_PRIVATE);    // test 이름의 기본모드 설정
        // sharedPreferences의 viewedPages에 현재페이지가 없으면 저장
        Log.d(TAG, "updateLikeCount: "+sharedPreferences.getString("likedPages",""));
        Log.d(TAG, "updateLikeCount: "+postId+",");

        if(sharedPreferences.getString("nickname","").equals("")){
            Toast.makeText(this, "로그인이 필요합니다.", Toast.LENGTH_SHORT).show();
        } else if(!sharedPreferences.getString("likedPages","").contains(String.valueOf(postId))){
            SharedPreferences.Editor editor = sharedPreferences.edit(); //sharedPreferences를 제어할 editor를 선언
            editor.putString("likedPages", sharedPreferences.getString("likedPages","")+postId+","); // key,value 형식으로 저장
            editor.commit();
            communityDetailViewModel.updateLikeCount(postId, jwtToken);
        } else{
            Toast.makeText(this, "이미 좋아요를 누른 페이지 입니다.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(CommunityDetailActivity.this, MainActivity.class);
        intent.putExtra("frag", 1);
        intent.putExtra("page", getIntent().getIntExtra("page", 0));
        intent.putExtra("position", getIntent().getIntExtra("position", 0));
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(0, android.R.anim.slide_out_right);
        finish();
    }

    private void alert(String value) {
        AlertDialog.Builder builder = new AlertDialog.Builder(CommunityDetailActivity.this);
        builder.setMessage(value);
        builder.setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (value.equals("로그인이 필요합니다.")) {
//                            finish();
                            Intent intent = new Intent(CommunityDetailActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        } else if (value.equals("로그아웃 되었습니다.")) {
                            Intent intent = new Intent(CommunityDetailActivity.this, CommunityDetailActivity.class);
                            intent.putExtra("postId", postId);
                            intent.putExtra("page", page);
                            // 이전화면을 없애고 새화면을 띄운다
                            intent.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    }
                });
        builder.show();
    }
}