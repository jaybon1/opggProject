package com.jaybon.opgg.viewmodel.info;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.jaybon.opgg.model.dto.InfoDto;
import com.jaybon.opgg.model.network.OpggRetrofitHelper;
import com.jaybon.opgg.model.network.OpggService;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class InfoRepository {

    private static final String TAG = "InfoRepository";

    // 레트로핏 레퍼런스 생성
    Retrofit opggRetrofit;

    // 뮤터블 라이브데이터 레퍼런스 생성
    private MutableLiveData<List<InfoDto>> liveInfoDtos;

    // 생성자에서 레퍼런스 초기화
    public InfoRepository(){
        opggRetrofit = OpggRetrofitHelper.getRetrofit();
        liveInfoDtos = new MutableLiveData<>();
    }

    // 라이브데이터를 넘겨주는 메서드
    public LiveData<List<InfoDto>> initData(){
        return liveInfoDtos;
    }

    // 라이브데이터에 초기데이터를 입력해주는 메서드
    public void getInfoDtos(String summonerName) {
        OpggService opggService = opggRetrofit.create(OpggService.class);
        Call<List<InfoDto>> call = opggService.getInfoByName(summonerName);

        call.enqueue(new Callback<List<InfoDto>>() {
            @Override
            public void onResponse(Call<List<InfoDto>> call, Response<List<InfoDto>> response) {

                if (!response.isSuccessful()) {
                    Log.d(TAG, "onResponse: " + response.code());
                    return;
                }
                Log.d(TAG, "onResponse: " + response.body());

                List<InfoDto> infoDtos = response.body();

                Log.d(TAG, "onResponse: " + infoDtos);

                liveInfoDtos.setValue(infoDtos);
            }

            @Override
            public void onFailure(Call<List<InfoDto>> call, Throwable t) {
                Log.d(TAG, "onFailure: 통신에 실패하였읍니다.");
            }
        });

    }
}
