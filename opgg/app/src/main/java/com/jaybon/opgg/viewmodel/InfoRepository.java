package com.jaybon.opgg.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.jaybon.opgg.model.dto.InfoDto;
import com.jaybon.opgg.model.dto.RespDto;
import com.jaybon.opgg.model.network.OpggRetrofitHelper;
import com.jaybon.opgg.model.network.OpggService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class InfoRepository {

    private static final String TAG = "InfoRepository";

    // 레트로핏 레퍼런스 생성
    Retrofit opggRetrofit;

    // 뮤터블 라이브데이터 레퍼런스 생성
    private MutableLiveData<RespDto<List<InfoDto>>> liveRespDto;

    // 생성자에서 레퍼런스 초기화
    public InfoRepository(){
        opggRetrofit = OpggRetrofitHelper.getRetrofit();
        liveRespDto = new MutableLiveData<>();
    }

    // 라이브데이터를 넘겨주는 메서드
    public LiveData<RespDto<List<InfoDto>>> initData(){
        return liveRespDto;
    }

    public void updateLiveData(String summonerName){

        // 레트로핏 비동기
        OpggService opggService = opggRetrofit.create(OpggService.class);
        Call<RespDto<List<InfoDto>>> call = opggService.updateInfoByName(summonerName);

        call.enqueue(new Callback<RespDto<List<InfoDto>>>() {
            @Override
            public void onResponse(Call<RespDto<List<InfoDto>>> call, Response<RespDto<List<InfoDto>>> response) {

                if (!response.isSuccessful()) {
                    Log.d(TAG, "onResponse: " + response.code());
                    return;
                }

                    RespDto<List<InfoDto>> respListDto = response.body();
                    liveRespDto.setValue(respListDto);

            }

            @Override
            public void onFailure(Call<RespDto<List<InfoDto>>> call, Throwable t) {
                Log.d(TAG, "onFailure: 통신에 실패하였읍니다.");
            }
        });

    }

    // 라이브데이터에 초기데이터를 입력해주는 메서드
    public void getDto(String summonerName) {

        // 레트로핏 비동기
        OpggService opggService = opggRetrofit.create(OpggService.class);
        Call<RespDto<List<InfoDto>>> call = opggService.getInfoByName(summonerName);

        call.enqueue(new Callback<RespDto<List<InfoDto>>>() {
            @Override
            public void onResponse(Call<RespDto<List<InfoDto>>> call, Response<RespDto<List<InfoDto>>> response) {

                if (!response.isSuccessful()) {
                    Log.d(TAG, "onResponse: " + response.code());
                    return;
                }

                RespDto<List<InfoDto>> respListDto = response.body();

                liveRespDto.setValue(respListDto);
            }

            @Override
            public void onFailure(Call<RespDto<List<InfoDto>>> call, Throwable t) {
                Log.d(TAG, "onFailure: 통신에 실패하였읍니다.");
            }
        });
    }
}
