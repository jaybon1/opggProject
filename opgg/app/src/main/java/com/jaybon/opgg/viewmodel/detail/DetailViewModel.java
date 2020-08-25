package com.jaybon.opgg.viewmodel.detail;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.jaybon.opgg.model.dto.CommunityDto;
import com.jaybon.opgg.model.dto.DetailDto;
import com.jaybon.opgg.model.dto.RespDto;
import com.jaybon.opgg.viewmodel.community.CommunityRepository;

import java.util.List;

public class DetailViewModel extends AndroidViewModel {

    // 라이브 데이터
    private LiveData<RespDto<DetailDto>> liveRespDto;

    // 라이브데이터 리파지토리
    private DetailRepository detailRepository;

    // 생성자
    public DetailViewModel(@NonNull Application application) {
        super(application);
        detailRepository = new DetailRepository();
        liveRespDto = detailRepository.initData();
    }

    // 데이터 초기화
    public void initLiveData(long gameId){
        detailRepository.getDto(gameId);
    }

    // 구독
    public LiveData<RespDto<DetailDto>> subscribe(){
        return liveRespDto;
    }

}
