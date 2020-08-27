package com.jaybon.opgg.viewmodel.communitydetail;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.jaybon.opgg.model.dto.CommunityDto;
import com.jaybon.opgg.model.dto.RespDto;

public class CommunityDetailViewModel extends AndroidViewModel {

    // 라이브 데이터
    private LiveData<RespDto<CommunityDto>> liveRespDto;

    // 라이브데이터 리파지토리
    private CommunityDetailRepository communityDetailRepository;

    // 생성자
    public CommunityDetailViewModel(@NonNull Application application) {
        super(application);
        communityDetailRepository = new CommunityDetailRepository();
        liveRespDto = communityDetailRepository.initData();
    }

    // 데이터 초기화
    public void initLiveData(long page){
        communityDetailRepository.getDto(page);
    }

    // 구독
    public LiveData<RespDto<CommunityDto>> subscribe(){
        return liveRespDto;
    }

}