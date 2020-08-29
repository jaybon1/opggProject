package com.jaybon.opgg.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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
import com.jaybon.opgg.model.dto.GoogleProfileObjDto;
import com.jaybon.opgg.model.dto.LoginDto;
import com.jaybon.opgg.model.dto.RespDto;
import com.jaybon.opgg.model.dto.TokenDto;
import com.jaybon.opgg.model.network.OpggRetrofitHelper;
import com.jaybon.opgg.model.network.OpggService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final int RC_SIGN_IN = 0;

    private ActivityLoginBinding activityLoginBinding;

    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 바인딩 연결
        activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        // 창종료
        activityLoginBinding.ivLoginBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 구글로그인
        activityLoginBinding.btnGoogleLogin.setSize(SignInButton.SIZE_STANDARD);
        activityLoginBinding.btnGoogleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_google_login:
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

        // 회원가입버튼
        activityLoginBinding.tvLoginJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, JoinActivity.class);
                intent.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
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
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, android.R.anim.slide_out_right);
    }

    private void alert(String value) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(value);
        builder.setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (value.equals("로그인에 성공하였습니다.")) {
//                            finish();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("frag", 1);
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                        }
                    }
                });
        builder.show();
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

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            activityLoginBinding.pgLoginLoading.setVisibility(View.VISIBLE);

            GoogleProfileObjDto googleProfileObjDto = GoogleProfileObjDto.builder()
                    .googleId(account.getId())
                    .email(account.getEmail())
                    .name(account.getFamilyName()+" "+account.getGivenName())
                    .build();

            GoogleLoginDto googleLoginDto = GoogleLoginDto.builder()
                    .profileObj(googleProfileObjDto)
                    .build();

            Retrofit opggRetrofit = OpggRetrofitHelper.getRetrofit();
            OpggService opggService = opggRetrofit.create(OpggService.class);
            Call<RespDto<TokenDto>> call = opggService.googleLogin(googleLoginDto);

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

//            // Signed in successfully, show authenticated UI.
//            Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
//            startActivity(intent);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());

        }
    }
}