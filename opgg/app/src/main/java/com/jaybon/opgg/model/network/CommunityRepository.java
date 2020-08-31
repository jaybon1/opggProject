package com.jaybon.opgg.model.network;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.jaybon.opgg.model.dto.CommunityDto;
import com.jaybon.opgg.model.dto.RespDto;
import com.jaybon.opgg.model.network.OpggRetrofitHelper;
import com.jaybon.opgg.model.network.OpggService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CommunityRepository {

    private static final String TAG = "CommunityRepository";

    // 레트로핏 레퍼런스 생성
    Retrofit opggRetrofit;

    // 뮤터블 라이브데이터 레퍼런스 생성
    private MutableLiveData<RespDto<List<CommunityDto>>> liveRespDto;

    // 생성자에서 레퍼런스 초기화
    public CommunityRepository(){
        opggRetrofit = OpggRetrofitHelper.getRetrofit();
        liveRespDto = new MutableLiveData<>();
    }

    // 라이브데이터를 넘겨주는 메서드
    public LiveData<RespDto<List<CommunityDto>>> initData(){
        return liveRespDto;
    }

    // 라이브데이터에 초기데이터를 입력해주는 메서드
    public void getDto(int page) {

        // 레트로핏 비동기
        OpggService opggService = opggRetrofit.create(OpggService.class);
        Call<RespDto<List<CommunityDto>>> call = opggService.getPostByPage(page);

        call.enqueue(new Callback<RespDto<List<CommunityDto>>>() {
            @Override
            public void onResponse(Call<RespDto<List<CommunityDto>>> call, Response<RespDto<List<CommunityDto>>> response) {

                if (!response.isSuccessful()) {
                    Log.d(TAG, "onResponse: " + response.code());
                    return;
                }
                RespDto<List<CommunityDto>> respDto = response.body();

                liveRespDto.setValue(respDto);
            }

            @Override
            public void onFailure(Call<RespDto<List<CommunityDto>>> call, Throwable t) {
                Log.d(TAG, "onFailure: 통신에 실패하였읍니다.");
            }
        });
    }

    public void getPostByContent(String content) {

        // 레트로핏 비동기
        OpggService opggService = opggRetrofit.create(OpggService.class);
        Call<RespDto<List<CommunityDto>>> call = opggService.getPostByContent(content);

        call.enqueue(new Callback<RespDto<List<CommunityDto>>>() {
            @Override
            public void onResponse(Call<RespDto<List<CommunityDto>>> call, Response<RespDto<List<CommunityDto>>> response) {

                if (!response.isSuccessful()) {
                    Log.d(TAG, "onResponse: " + response.code());
                    return;
                }
                RespDto<List<CommunityDto>> respDto = response.body();

                liveRespDto.setValue(respDto);
            }

            @Override
            public void onFailure(Call<RespDto<List<CommunityDto>>> call, Throwable t) {
                Log.d(TAG, "onFailure: 통신에 실패하였읍니다.");
            }
        });
    }

    public void updateViewCount(int postId) {

        // 레트로핏 비동기
        OpggService opggService = opggRetrofit.create(OpggService.class);
        Call<RespDto<String>> call = opggService.updateViewCount(postId);

        call.enqueue(new Callback<RespDto<String>>() {
            @Override
            public void onResponse(Call<RespDto<String>> call, Response<RespDto<String>> response) {

                if (!response.isSuccessful()) {
                    Log.d(TAG, "onResponse: " + response.code());
                    return;
                }
            }

            @Override
            public void onFailure(Call<RespDto<String>> call, Throwable t) {
                Log.d(TAG, "onFailure: 통신에 실패하였읍니다.");
            }
        });

    }
}
