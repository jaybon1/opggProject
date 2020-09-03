package com.jaybon.opgg.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.jaybon.opgg.model.dto.CommunityDto;
import com.jaybon.opgg.model.dto.RespDto;
import com.jaybon.opgg.model.network.CommunityRepository;

import java.util.List;

public class CommunityViewModel extends AndroidViewModel {

    // 라이브 데이터
    private LiveData<RespDto<List<CommunityDto>>> liveRespDto;

    // 라이브데이터 리파지토리
    private CommunityRepository communityRepository;

    // 생성자
    public CommunityViewModel(@NonNull Application application) {
        super(application);
        communityRepository = new CommunityRepository();
        liveRespDto = communityRepository.initData();
    }

    // 데이터 초기화
    public void initLiveData(int page){
        communityRepository.getDto(page);
    }

    // 구독
    public LiveData<RespDto<List<CommunityDto>>> subscribe(){
        return liveRespDto;
    }

    public void updateViewCount(int postId, String jwtToken) {

        communityRepository.updateViewCount(postId, jwtToken);

    }

    public void getPostByContent(String content) {

        communityRepository.getPostByContent(content);

    }

}