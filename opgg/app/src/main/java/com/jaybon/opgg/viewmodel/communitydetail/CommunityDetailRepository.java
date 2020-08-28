package com.jaybon.opgg.viewmodel.communitydetail;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.jaybon.opgg.model.dao.Reply;
import com.jaybon.opgg.model.dto.CommunityDto;
import com.jaybon.opgg.model.dto.RespDto;
import com.jaybon.opgg.model.network.OpggRetrofitHelper;
import com.jaybon.opgg.model.network.OpggService;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CommunityDetailRepository {

    private static final String TAG = "CommunityRepository";

    // 레트로핏 레퍼런스 생성
    Retrofit opggRetrofit;

    // 뮤터블 라이브데이터 레퍼런스 생성
    private MutableLiveData<RespDto<CommunityDto>> liveRespDto;

    // 생성자에서 레퍼런스 초기화
    public CommunityDetailRepository(){
        opggRetrofit = OpggRetrofitHelper.getRetrofit();
        liveRespDto = new MutableLiveData<>();
    }

    // 라이브데이터를 넘겨주는 메서드
    public LiveData<RespDto<CommunityDto>> initData(){
        return liveRespDto;
    }

    // 라이브데이터에 초기데이터를 입력해주는 메서드
    public void getDto(long postId) {

        // 레트로핏 비동기
        OpggService opggService = opggRetrofit.create(OpggService.class);
        Call<RespDto<CommunityDto>> call = opggService.getPostById(postId);

        call.enqueue(new Callback<RespDto<CommunityDto>>() {
            @Override
            public void onResponse(Call<RespDto<CommunityDto>> call, Response<RespDto<CommunityDto>> response) {

                if (!response.isSuccessful()) {
                    Log.d(TAG, "onResponse: " + response.code());
                    return;
                }
                RespDto<CommunityDto> respDto = response.body();

                liveRespDto.setValue(respDto);
            }

            @Override
            public void onFailure(Call<RespDto<CommunityDto>> call, Throwable t) {
                Log.d(TAG, "onFailure: 통신에 실패하였읍니다.");
            }
        });
    }

    public void writeReply(Reply reply) {

        OpggService opggService = opggRetrofit.create(OpggService.class);
        Call<RespDto<CommunityDto>> call = opggService.writeReply(reply);

        call.enqueue(new Callback<RespDto<CommunityDto>>() {
            @Override
            public void onResponse(Call<RespDto<CommunityDto>> call, Response<RespDto<CommunityDto>> response) {
                if (!response.isSuccessful()) {
                    Log.d(TAG, "onResponse: " + response.code());
                    return;
                }
                RespDto<CommunityDto> respDto = response.body();

                liveRespDto.setValue(respDto);
            }

            @Override
            public void onFailure(Call<RespDto<CommunityDto>> call, Throwable t) {
                Log.d(TAG, "onFailure: 통신에 실패하였읍니다.");
            }
        });


    }

}
