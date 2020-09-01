package com.jaybon.opgg.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.jaybon.opgg.R;
import com.jaybon.opgg.databinding.ActivityLoginBinding;
import com.jaybon.opgg.model.dto.GoogleLoginDto;
import com.jaybon.opgg.model.dto.LoginDto;
import com.jaybon.opgg.model.dto.RespDto;
import com.jaybon.opgg.model.dto.TokenDto;
import com.jaybon.opgg.model.network.GoogleLoginRepository;
import com.jaybon.opgg.model.network.KakaoLoginRepository;
import com.jaybon.opgg.model.network.OpggRetrofitHelper;
import com.jaybon.opgg.model.network.OpggService;
import com.jaybon.opgg.view.callback.OAuthLoginCallback;
import com.kakao.auth.IApplicationConfig;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.KakaoAdapter;
import com.kakao.auth.KakaoSDK;
import com.kakao.auth.Session;
import com.kakao.common.KakaoPhase;
import com.kakao.common.PhaseInfo;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.Utility;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

public class LoginActivity extends AppCompatActivity implements OAuthLoginCallback {

    private static final String TAG = "LoginActivity";
    private static final int RC_SIGN_IN = 0;

    private ActivityLoginBinding activityLoginBinding;

    private GoogleSignInClient mGoogleSignInClient;

    private ISessionCallback sessionCallback;

    private GoogleLoginRepository googleLoginRepository;
    private KakaoLoginRepository kakaoLoginRepository;

    MeV2Response kakaoResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        kakaoResult = null;

        // 로그인 리파지토리 가져오기
        googleLoginRepository = new GoogleLoginRepository(this);
        kakaoLoginRepository = new KakaoLoginRepository(this);

        // 바인딩 연결
        activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        // 구글 로그인 버튼 초기화
        initGoogleLoginButton();

        // 카카오 로그인 버튼 초기화
        initKakaoLoginButton();

        // 버튼들에 리스너 달기
        initListener();


    }

    // 리스너
    public void initListener(){

        // 창종료
        activityLoginBinding.ivLoginBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 로그인버튼
        activityLoginBinding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (activityLoginBinding.etLoginEmail.getText() == null ||
                        activityLoginBinding.etLoginEmail.getText().toString().equals("") ||
                        activityLoginBinding.etLoginPassword.getText() == null ||
                        activityLoginBinding.etLoginPassword.getText().toString().equals("")) {

                    Toast.makeText(LoginActivity.this, "값을 입력해주세요", Toast.LENGTH_SHORT).show();

                } else {

                    LoginDto loginDto = LoginDto.builder()
                            .email(activityLoginBinding.etLoginEmail.getText().toString())
                            .password(activityLoginBinding.etLoginPassword.getText().toString())
                            .build();

                    retroLoginProc(loginDto);

                }
            }
        });


        // 회원가입버튼
        activityLoginBinding.tvLoginJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, JoinActivity.class);
                intent.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }



    // 구글로그인
    public void initGoogleLoginButton(){
        activityLoginBinding.btnLoginGoogle.setSize(SignInButton.SIZE_STANDARD);
        activityLoginBinding.btnLoginGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_login_google:
                        signIn();
                        break;
                    // ...
                }
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    // 카카오 로그인 버튼 초기화
    public void initKakaoLoginButton(){

        //키 해시 구하기(구글 디벨로퍼 사이트에 등록해야됨)
        String keyHash = Utility.getKeyHash(this);
        Log.d(TAG, "onCreate: "+keyHash);

        // Kakao Sdk 초기화
        try {

            KakaoSDK.init(new KakaoAdapter() {
                @Override
                public IApplicationConfig getApplicationConfig() {
                    return new IApplicationConfig() {
                        @Override
                        public Context getApplicationContext() {
                            return LoginActivity.this.getApplicationContext();
                        }
                    };
                }
            }, new PhaseInfo() {
                @NonNull
                @Override
                public KakaoPhase phase() {
                    return null;
                }

                @Nullable
                @Override
                public String appKey() { // 앱키 등록
                    return "e15eb652eb92a95b3d13100542732956";
                }

                @Nullable
                @Override
                public String clientSecret() {
                    return null;
                }
            });
        }catch (KakaoSDK.AlreadyInitializedException e){
            Log.d(TAG, "initKakaoLoginButton: 이미 초기화 되어있습니다.");
        }

        //카카오 세션 콜백 구현
        sessionCallback = new ISessionCallback() {
            @Override
            public void onSessionOpened() {
                Log.i("KAKAO_SESSION", "로그인 성공");

                // 로그인 성공시 유저 정보를 받아서
                UserManagement.getInstance().me(new MeV2ResponseCallback() {
                    @Override
                    public void onSessionClosed(ErrorResult errorResult) {
                        Log.e("SessionCallback :: ", "onSessionClosed : " + errorResult.getErrorMessage());
                    }

                    @Override
                    public void onSuccess(MeV2Response result) {
                        Log.d(TAG, "onSuccess: "+result);

                        kakaoResult = result;

                        String kakaoEamil = result.getKakaoAccount().getEmail();
                        if(kakaoEamil == null || kakaoEamil.equals("")){
                            alert("이메일을 입력해주세요.");
                        } else {
                            kakaoLoginRepository.serverLogin(LoginActivity.this, kakaoResult);
                        }
                    }
                });
            }

            @Override
            public void onSessionOpenFailed(KakaoException exception) {
                Log.e("KAKAO_SESSION", "로그인 실패", exception);
                alert("카카오 로그인 실패");
            }
        };

        // 카카오 세션 콜백 등록
        Session.getCurrentSession().addCallback(sessionCallback);
    }

    public void retroLoginProc(LoginDto loginDto){

        activityLoginBinding.pgLoginLoading.setVisibility(View.VISIBLE);

        Retrofit opggRetrofit = OpggRetrofitHelper.getRetrofit();
        OpggService opggService = opggRetrofit.create(OpggService.class);
        Call<RespDto<TokenDto>> call = opggService.login(loginDto);

        call.enqueue(new Callback<RespDto<TokenDto>>() {


            @Override
            public void onResponse(Call<RespDto<TokenDto>> call, Response<RespDto<TokenDto>> response) {
                if (response.body().getStatusCode() == 200) {

                    SharedPreferences sharedPreferences = getSharedPreferences("com.jaybon.opgg.jwt", MODE_PRIVATE);    // test 이름의 기본모드 설정
                    SharedPreferences.Editor editor = sharedPreferences.edit(); //sharedPreferences를 제어할 editor를 선언
                    editor.putString("jwtToken", response.body().getData().getJwtToken()); // key,value 형식으로 저장
                    editor.putString("userId", String.valueOf(response.body().getData().getUserId())); // key,value 형식으로 저장
                    editor.putString("nickname", response.body().getData().getNickname()); // key,value 형식으로 저장

                    editor.commit();    //최종 커밋. 커밋을 해야 저장이 된다.

                    alert("로그인에 성공하였습니다.");
                    activityLoginBinding.pgLoginLoading.setVisibility(View.GONE);
                } else {
                    alert(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<RespDto<TokenDto>> call, Throwable t) {
                alert("통신에 실패하였습니다.");
                activityLoginBinding.pgLoginLoading.setVisibility(View.GONE);
            }
        });
    }



    // 구글 로그인
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    // 구글 로그인 후 서버에 값 전송하여 토큰 받기
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            activityLoginBinding.pgLoginLoading.setVisibility(View.VISIBLE);

            googleLoginRepository.serverLogin(LoginActivity.this, account);

        } catch (ApiException e) {

        }
    }

    private void alert(String value) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(value);
        final EditText edittext = new EditText(this);

        if(value.equals("이메일을 입력해주세요.")){
            edittext.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
            builder.setView(edittext);
        }

        builder.setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (value.equals("로그인에 성공하였습니다.")) {

                            if(getIntent().getStringExtra("activity") == null) {

                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("frag", 1);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);

                            } else if(getIntent().getStringExtra("activity").equals("community")){
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("frag", 1);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);

                            } else if(getIntent().getStringExtra("activity").equals("communityDetail")){

                                Intent intent = new Intent(LoginActivity.this, CommunityDetailActivity.class);
                                intent.putExtra("postId", getIntent().getIntExtra("postId",0));
                                intent.putExtra("page", getIntent().getIntExtra("page",0));
                                // 이전화면을 없애고 새화면을 띄운다
                                intent.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);

                            }
                        } else if(value.equals("이메일을 입력해주세요.")) {
                            if(edittext.getText() == null ||
                                    edittext.getText().toString().equals("") ||
                                    !edittext.getText().toString().contains("@")){

                                alert("로그인에 실패하였습니다.\n 이메일을 확인해주세요.");

                            } else {
                                kakaoLoginRepository.serverLogin(LoginActivity.this, kakaoResult, edittext.getText().toString());
                            }

                        } else if(value.equals("에러가 발생하였습니다.")) {
                            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(LoginActivity.this);
                            if (acct != null) {

                                // 구글 인증 관련
                                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                        .requestEmail()
                                        .build();

                                // 구글 클라이언트
                                GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(LoginActivity.this, gso);

                                // 구글 로그아웃
                                mGoogleSignInClient.signOut();
                            }

                            // 카카오 로그아웃
                            UserManagement.getInstance()
                                    .requestLogout(new LogoutResponseCallback() {
                                        @Override
                                        public void onCompleteLogout() {
//                                    Log.i("KAKAO_API", "로그아웃 완료");
                                        }
                                    });
                        }
                        activityLoginBinding.pgLoginLoading.setVisibility(View.GONE);
                    }
                });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 구글
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }

        // 카카오톡|스토리 간편로그인 실행 결과를 받아서 SDK로 전달
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 카카오 세션 콜백 삭제
        Session.getCurrentSession().removeCallback(sessionCallback);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, android.R.anim.slide_out_right);
    }

    // 스프링 서버로 로그인 한 결과처리
    @Override
    public void onResult(String value) {
        alert(value);
    }
}