package com.jaybon.opgg.model.network;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.jaybon.opgg.model.dto.DetailDto;
import com.jaybon.opgg.model.dto.RespDto;
import com.jaybon.opgg.model.network.OpggRetrofitHelper;
import com.jaybon.opgg.model.network.OpggService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DetailRepository {

    private static final String TAG = "DetailRepository";

    // 레트로핏 레퍼런스 생성
    Retrofit opggRetrofit;

    // 뮤터블 라이브데이터 레퍼런스 생성
    private MutableLiveData<RespDto<DetailDto>> liveRespDto;

    // 생성자에서 레퍼런스 초기화
    public DetailRepository(){
        opggRetrofit = OpggRetrofitHelper.getRetrofit();
        liveRespDto = new MutableLiveData<>();
    }

    // 라이브데이터를 넘겨주는 메서드
    public LiveData<RespDto<DetailDto>> initData(){
        return liveRespDto;
    }

    // 라이브데이터에 초기데이터를 입력해주는 메서드
    public void getDto(long gameId) {
        // 레트로핏 비동기
        OpggService opggService = opggRetrofit.create(OpggService.class);
        Call<RespDto<DetailDto>> call = opggService.getDetailByGameId(gameId);

        call.enqueue(new Callback<RespDto<DetailDto>>() {
            @Override
            public void onResponse(Call<RespDto<DetailDto>> call, Response<RespDto<DetailDto>> response) {

                if (!response.isSuccessful()) {
                    Log.d(TAG, "onResponse: " + response.code());
                    return;
                }
                RespDto<DetailDto> respDto = response.body();

                liveRespDto.setValue(respDto);
            }

            @Override
            public void onFailure(Call<RespDto<DetailDto>> call, Throwable t) {
                Log.d(TAG, "onFailure: 통신에 실패하였읍니다.");
            }
        });

    }
}
