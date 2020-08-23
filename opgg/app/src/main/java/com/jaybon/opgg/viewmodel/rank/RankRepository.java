package com.jaybon.opgg.viewmodel.rank;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.jaybon.opgg.model.dto.InfoDto;
import com.jaybon.opgg.model.dto.RankingDto;
import com.jaybon.opgg.model.network.OpggRetrofitHelper;
import com.jaybon.opgg.model.network.OpggService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RankRepository {

    private static final String TAG = "InfoRepository";

    // 레트로핏 레퍼런스 생성
    Retrofit opggRetrofit;

    // 뮤터블 라이브데이터 레퍼런스 생성
    private MutableLiveData<List<RankingDto>> liveRankingDtos;

    // 생성자에서 레퍼런스 초기화
    public RankRepository(){
        opggRetrofit = OpggRetrofitHelper.getRetrofit();
        liveRankingDtos = new MutableLiveData<>();
    }

    // 라이브데이터를 넘겨주는 메서드
    public LiveData<List<RankingDto>> initData(){
        return liveRankingDtos;
    }

    // 라이브데이터에 초기데이터를 입력해주는 메서드
    public void getRankingDtos(long page){
        OpggService opggService = opggRetrofit.create(OpggService.class);
        Call<List<RankingDto>> call = opggService.getRankingByPage(page);

        call.enqueue(new Callback<List<RankingDto>>() {
            @Override
            public void onResponse(Call<List<RankingDto>> call, Response<List<RankingDto>> response) {

                if (!response.isSuccessful()) {
                    Log.d(TAG, "onResponse: "+response.code());
                    return;
                }
                Log.d(TAG, "onResponse: "+response.body());

                List<RankingDto> rankingDtos = response.body();

                Log.d(TAG, "onResponse: "+ rankingDtos);

                liveRankingDtos.setValue(rankingDtos);
            }

            @Override
            public void onFailure(Call<List<RankingDto>> call, Throwable t) {
                Log.d(TAG, "onFailure: 통신에 실패하였읍니다.");
            }
        });

    }


}
