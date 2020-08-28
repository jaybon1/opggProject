package com.jaybon.opgg.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.jaybon.opgg.model.dto.InfoDto;
import com.jaybon.opgg.model.dto.RespDto;

import java.util.List;

public class InfoViewModel extends AndroidViewModel {

    // 라이브 데이터
    private LiveData<RespDto<List<InfoDto>>> liveRespDto;

    // 라이브데이터 리파지토리
    private InfoRepository infoRepository;

    // 생성자
    public InfoViewModel(@NonNull Application application) {
        super(application);
        infoRepository = new InfoRepository();
        liveRespDto = infoRepository.initData();
    }

    // 데이터 초기화
    public void initLiveData(String summonerName){
        infoRepository.getDto(summonerName);
    }

    // 구독
    public LiveData<RespDto<List<InfoDto>>> subscribe(){
        return liveRespDto;
    }

    // 라이브데이터 업데이트
    public void updateLiveData(String summonerName){
        infoRepository.updateLiveData(summonerName);
    };

}
