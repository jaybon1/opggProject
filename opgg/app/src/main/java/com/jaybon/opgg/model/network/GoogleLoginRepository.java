package com.jaybon.opgg.model.network;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.jaybon.opgg.model.dto.DetailDto;
import com.jaybon.opgg.model.dto.GoogleLoginDto;
import com.jaybon.opgg.model.dto.RespDto;
import com.jaybon.opgg.model.dto.TokenDto;
import com.jaybon.opgg.view.callback.OAuthLoginCallback;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GoogleLoginRepository {

    private static final String TAG = "GoogleLoginRepository";

    // 레트로핏 레퍼런스 생성
    Retrofit opggRetrofit;
    OAuthLoginCallback oAuthLoginCallback;

    // 생성자에서 레퍼런스 초기화
    public GoogleLoginRepository(OAuthLoginCallback oAuthLoginCallback){
        opggRetrofit = OpggRetrofitHelper.getRetrofit();
        this.oAuthLoginCallback = oAuthLoginCallback;
    }

    public void serverLogin(Activity activity, GoogleSignInAccount account){

        GoogleLoginDto googleLoginDto = GoogleLoginDto.builder()
                .googleId(account.getId())
                .email(account.getEmail())
                .name(account.getFamilyName()+" "+account.getGivenName())
                .build();

        Retrofit opggRetrofit = OpggRetrofitHelper.getRetrofit();
        OpggService opggService = opggRetrofit.create(OpggService.class);
        Call<RespDto<TokenDto>> call = opggService.googleLogin(googleLoginDto);

        call.enqueue(new Callback<RespDto<TokenDto>>() {
            @Override
            public void onResponse(Call<RespDto<TokenDto>> call, Response<RespDto<TokenDto>> response) {
                if (response.body().getStatusCode() == 200) {

                    SharedPreferences sharedPreferences = activity.getSharedPreferences("com.jaybon.opgg.jwt", activity.MODE_PRIVATE);    // test 이름의 기본모드 설정
                    SharedPreferences.Editor editor = sharedPreferences.edit(); //sharedPreferences를 제어할 editor를 선언
                    editor.putString("jwtToken", response.body().getData().getJwtToken()); // key,value 형식으로 저장
                    editor.putString("userId", String.valueOf(response.body().getData().getUserId())); // key,value 형식으로 저장
                    editor.putString("nickname", response.body().getData().getNickname()); // key,value 형식으로 저장

                    editor.commit();    //최종 커밋. 커밋을 해야 저장이 된다.

//                    alert("로그인에 성공하였습니다.");
//                    activityLoginBinding.pgLoginLoading.setVisibility(View.GONE);
                    oAuthLoginCallback.onResult("로그인에 성공하였습니다.");
                } else {
//                    alert(response.body().getMessage());
                    oAuthLoginCallback.onResult(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<RespDto<TokenDto>> call, Throwable t) {
                oAuthLoginCallback.onResult("통신에 실패하였습니다.");
//                alert("통신에 실패하였습니다.");
//                activityLoginBinding.pgLoginLoading.setVisibility(View.GONE);
            }
        });
    }
}

