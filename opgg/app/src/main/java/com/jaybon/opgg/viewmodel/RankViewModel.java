package com.jaybon.opgg.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.jaybon.opgg.model.dto.RankingDto;
import com.jaybon.opgg.model.dto.RespDto;

import java.util.List;

public class RankViewModel extends AndroidViewModel {

    // 라이브 데이터
    private LiveData<RespDto<List<RankingDto>>> liveRespDto;

    // 라이브데이터 리파지토리
    private RankRepository rankRepository;

    // 생성자
    public RankViewModel(@NonNull Application application) {
        super(application);
        rankRepository = new RankRepository();
        liveRespDto = rankRepository.initData();
    }

    // 데이터 초기화
    public void initLiveData(long page){
        rankRepository.getDto(page);
    }

    // 이름으로 랭킹 정보 검색
    public void getLiveDataByName(String summonerName){
        rankRepository.getDto(summonerName);
    }

    // 페이지로 랭킹 정보 검색
    public void getLiveDataByPage(long page){
        rankRepository.getDto(page);
    }

    // 구독
    public LiveData<RespDto<List<RankingDto>>> subscribe(){
        return liveRespDto;
    }

}
