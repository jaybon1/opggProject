package com.jaybon.opgg.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.jaybon.opgg.R;
import com.jaybon.opgg.databinding.FragmentCommunityBinding;
import com.jaybon.opgg.model.dto.CommunityDto;
import com.jaybon.opgg.model.dto.RespDto;
import com.jaybon.opgg.view.adapter.CommunityAdapter;
import com.jaybon.opgg.view.callback.CommunityCallback;
import com.jaybon.opgg.viewmodel.CommunityViewModel;

import java.util.ArrayList;
import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

public class CommunityFragment extends Fragment implements CommunityCallback {

    private static final String TAG = "CommunityFragment";

    // 데이터바인딩
    private FragmentCommunityBinding fragmentCommunityBinding;

    // 리사이클러뷰 어댑터
    private CommunityAdapter adapter;

    // 리사이클러뷰 데이터
    private List<CommunityDto> communityDtos;

    // 뷰모델
    private CommunityViewModel communityViewModel;

    // 페이지
    private int page;
    private int position;

//    private ImageView ivNavHeader;
//    private Button btnNavHeader;

    // 생성자
    public CommunityFragment() {
    }

    public CommunityFragment(int page, int position) {
        this.page = page;
        this.position = position;
    }

    // 미사용
    public static CommunityFragment newInstance(String param1, String param2) {
        CommunityFragment fragment = new CommunityFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // 리스트 초기화
        communityDtos = new ArrayList<>();

        // 액티비티와 프래그먼트는 바인딩 방식이 다름 (확인)
        fragmentCommunityBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_community, container, false);

        // 리스너들 등록
        initListener();

        // 리사이클러뷰 세팅
        fragmentCommunityBinding.rvCommunity.setLayoutManager(new LinearLayoutManager(getActivity()));

        // 사이클러뷰 어댑터 세팅
        adapter = new CommunityAdapter(CommunityFragment.this, 0, page);
        fragmentCommunityBinding.rvCommunity.setAdapter(adapter);

        // 리사이클러뷰 데이터 초기화
        adapter.addContents(communityDtos);

        // 뷰모델 초기화
        communityViewModel = ViewModelProviders.of(this).get(CommunityViewModel.class);

        // 뷰모델 구독
        communityViewModel.subscribe().observe(this, new Observer<RespDto<List<CommunityDto>>>() {

            // 라이브데이터 값이 변경되면 자동실행
            @Override
            public void onChanged(RespDto<List<CommunityDto>> respDto) {

                // 페이지 비우기
                communityDtos.clear();

                adapter.setPage(page);

                if (respDto.getStatusCode() == 201) { // 첫페이지 일경우

                    adapter.setButtonType(0);

                    //리사이클러뷰에 데이터 넣기
                    adapter.addContents(respDto.getData());

                    // 리사이클러뷰 데이터 다시넣을 경우 호출함수
                    adapter.notifyDataSetChanged();

                    // 디테일 내용을 보다가 다시 돌아올 경우 해당위치로이동
                    fragmentCommunityBinding.rvCommunity.scrollToPosition(position);

                    // 페이지 이동시 맨위로 올리기위해서 포지션0
                    position = 0;

                    // 로딩 화면 없애기
                    fragmentCommunityBinding.pgCommunityLoading.setVisibility(View.GONE);

                } else if (respDto.getStatusCode() == 200) { // 0페이지나 끝페이지가 아닐경우

                    adapter.setButtonType(1);

                    //리사이클러뷰에 데이터 넣기
                    adapter.addContents(respDto.getData());

                    // 리사이클러뷰 데이터 다시넣을 경우 호출함수
                    adapter.notifyDataSetChanged();

                    // 디테일 내용을 보다가 다시 돌아올 경우 해당위치로이동
                    fragmentCommunityBinding.rvCommunity.scrollToPosition(position);

                    // 페이지 이동시 맨위로 올리기위해서 포지션0
                    position = 0;

                    // 로딩 화면 없애기
                    fragmentCommunityBinding.pgCommunityLoading.setVisibility(View.GONE);

                } else if (respDto.getStatusCode() == 204) { // 끝페이지 일경우

                    adapter.setButtonType(2);

                    //리사이클러뷰에 데이터넣기
                    adapter.addContents(respDto.getData());

                    // 리사이클러뷰 데이터 다시넣을 경우 호출함수
                    adapter.notifyDataSetChanged();

                    // 디테일 내용을 보다가 다시 돌아올 경우 해당위치로이동
                    fragmentCommunityBinding.rvCommunity.scrollToPosition(position);

                    // 페이지 이동시 맨위로 올리기위해서 포지션0
                    position = 0;

                    // 로딩 화면 없애기
                    fragmentCommunityBinding.pgCommunityLoading.setVisibility(View.GONE);

                } else {

                    // dto 메시지 출력
                    Toast.makeText(getContext(), respDto.getMessage(), Toast.LENGTH_SHORT).show();

                    // 로딩 화면 없애기
                    fragmentCommunityBinding.pgCommunityLoading.setVisibility(View.GONE);
                }

            }

        });

        // 뷰모델 데이터 초기화
        communityViewModel.initLiveData(page);

        // 둘중아무거나 되는듯?
        return fragmentCommunityBinding.getRoot();
//        return inflater.inflate(R.layout.fragment_community, container, false);
    }

    @Override
    public void getPage(int page) {

        // 로딩 화면 생성
        fragmentCommunityBinding.pgCommunityLoading.setVisibility(View.VISIBLE);

        communityViewModel.initLiveData(page);
        this.page = page;
    }

    private void initListener(){

        // 네비게이션바 헤더 엑스버튼
        fragmentCommunityBinding.navCommunity.getHeaderView(0).findViewById(R.id.iv_nav_header).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentCommunityBinding.drawerLayoutCommunity.closeDrawer(Gravity.RIGHT);
            }
        });

        // 네비게이션바 메뉴버튼
        fragmentCommunityBinding.navCommunity.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.bottom_nav_search_button:
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new SearchFragment()).commit();
                        ((BottomNavigationView)getActivity().findViewById(R.id.bottom_nav_search)).setSelectedItemId(R.id.bottom_nav_search_button);
                        break;
                    case R.id.bottom_nav_community_button:
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new CommunityFragment()).commit();
                        ((BottomNavigationView)getActivity().findViewById(R.id.bottom_nav_search)).setSelectedItemId(R.id.bottom_nav_community_button);
                        break;
                    case R.id.bottom_nav_ranking_button:
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new RankFragment()).commit();
                        ((BottomNavigationView)getActivity().findViewById(R.id.bottom_nav_search)).setSelectedItemId(R.id.bottom_nav_ranking_button);
                        break;
                }
                return false;
            }
        });

        // 툴바
        fragmentCommunityBinding.ivCommunityToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentCommunityBinding.drawerLayoutCommunity.openDrawer(Gravity.RIGHT);
            }
        });


        // 서치버튼
        fragmentCommunityBinding.btnCommunitySearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // 글쓰기 버튼
        initWriteButton();

        // 로그인 버튼
        initLoginButton();

    }

    private void initWriteButton(){
        // 글쓰기버튼

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("com.jaybon.opgg.jwt", getActivity().MODE_PRIVATE);    // test 이름의 기본모드 설정, 만약 test key값이 있다면 해당 값을 불러옴.
        String jwtToken = sharedPreferences.getString("jwtToken","");

        if(jwtToken.equals("")){

            fragmentCommunityBinding.btnCommunityWrite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alert("로그인이 필요합니다.");
                }
            });

        }else {

            fragmentCommunityBinding.btnCommunityWrite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), WriteActivity.class);
                    intent.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });
        }
    }

    private void initLoginButton(){
        // 네비게이션바 헤더 로그인버튼
        // SharedPreferences에 값이 없으면 로그인, 있으면 로그아웃
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("com.jaybon.opgg.jwt", getActivity().MODE_PRIVATE);    // test 이름의 기본모드 설정, 만약 test key값이 있다면 해당 값을 불러옴.
        String jwtToken = sharedPreferences.getString("jwtToken","");

        if(jwtToken.equals("")){
            ((Button)fragmentCommunityBinding.navCommunity.getHeaderView(0).findViewById(R.id.btn_nav_header)).setText("로그인");
            fragmentCommunityBinding.navCommunity.getHeaderView(0).findViewById(R.id.btn_nav_header).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    intent.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    fragmentCommunityBinding.drawerLayoutCommunity.closeDrawer(Gravity.RIGHT);
                }
            });
        } else{
            ((Button)fragmentCommunityBinding.navCommunity.getHeaderView(0).findViewById(R.id.btn_nav_header)).setText("로그아웃");
            fragmentCommunityBinding.navCommunity.getHeaderView(0).findViewById(R.id.btn_nav_header).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getContext());
                    if (acct != null) {

                        // 구글 인증 관련
                        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                .requestEmail()
                                .build();

                        // 구글 로그인
                        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);

                        // 구글 로그아웃
                        mGoogleSignInClient.signOut();
                    }

                    // SharedPreferences 내용삭제
                    SharedPreferences.Editor editor= sharedPreferences.edit(); //sharedPreferences를 제어할 editor를 선언
                    editor.putString("jwtToken", null); // key,value 형식으로 저장
                    editor.putString("userId", null); // key,value 형식으로 저장
                    editor.putString("nickname", null); // key,value 형식으로 저장
                    editor.commit();    //최종 커밋. 커밋을 해야 저장이 된다.

                    fragmentCommunityBinding.drawerLayoutCommunity.closeDrawer(Gravity.RIGHT);

                    initLoginButton();
                    initWriteButton();

                    alert("로그아웃 되었습니다.");
                }
            });
        }
    }

    private void alert(String value){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(value);
        builder.setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if(value.equals("로그인이 필요합니다.")){
//                            finish();
                            Intent intent = new Intent(getContext(), LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    }
                });
        builder.show();
    }
}
