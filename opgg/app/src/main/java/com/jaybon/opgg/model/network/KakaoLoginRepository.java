package com.jaybon.opgg.model.network;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.jaybon.opgg.model.dto.GoogleLoginDto;
import com.jaybon.opgg.model.dto.KakaoLoginDto;
import com.jaybon.opgg.model.dto.RespDto;
import com.jaybon.opgg.model.dto.TokenDto;
import com.jaybon.opgg.view.callback.OAuthLoginCallback;
import com.kakao.usermgmt.response.MeV2Response;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class KakaoLoginRepository {

    private static final String TAG = "KakaoLoginRepository";

    // 레트로핏 레퍼런스 생성
    Retrofit opggRetrofit;
    OAuthLoginCallback oAuthLoginCallback;

    // 생성자에서 레퍼런스 초기화
    public KakaoLoginRepository(OAuthLoginCallback oAuthLoginCallback){
        opggRetrofit = OpggRetrofitHelper.getRetrofit();
        this.oAuthLoginCallback = oAuthLoginCallback;
    }

    public void serverLogin(Activity activity, MeV2Response result){

        KakaoLoginDto kakaoLoginDto = KakaoLoginDto.builder()
                .kakaoId(Long.toString(result.getId()))
                .email(result.getKakaoAccount().getEmail())
                .name(result.getKakaoAccount().getProfile().getNickname())
                .build();

        Retrofit opggRetrofit = OpggRetrofitHelper.getRetrofit();
        OpggService opggService = opggRetrofit.create(OpggService.class);
        Call<RespDto<TokenDto>> call = opggService.kakaoLogin(kakaoLoginDto);

        call.enqueue(new Callback<RespDto<TokenDto>>() {
            @Override
            public void onResponse(Call<RespDto<TokenDto>> call, Response<RespDto<TokenDto>> response) {

                Log.d(TAG, "onResponse: "+response);

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

    public void serverLogin(Activity activity, MeV2Response result, String email){

        KakaoLoginDto kakaoLoginDto = KakaoLoginDto.builder()
                .kakaoId(Long.toString(result.getId()))
                .email(email)
                .name(result.getKakaoAccount().getProfile().getNickname())
                .build();

        Retrofit opggRetrofit = OpggRetrofitHelper.getRetrofit();
        OpggService opggService = opggRetrofit.create(OpggService.class);
        Call<RespDto<TokenDto>> call = opggService.kakaoLogin(kakaoLoginDto);

        call.enqueue(new Callback<RespDto<TokenDto>>() {
            @Override
            public void onResponse(Call<RespDto<TokenDto>> call, Response<RespDto<TokenDto>> response) {

                Log.d(TAG, "onResponse: "+response);

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

