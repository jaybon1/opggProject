package com.jaybon.opgg.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.jaybon.opgg.model.dao.Reply;
import com.jaybon.opgg.model.dto.CommunityDto;
import com.jaybon.opgg.model.dto.RespDto;
import com.jaybon.opgg.model.network.CommunityDetailRepository;

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
    public void initLiveData(long page, String jwtToken){
        communityDetailRepository.getDto(page, jwtToken);
    }

    // 구독
    public LiveData<RespDto<CommunityDto>> subscribe(){
        return liveRespDto;
    }

    public void writeReplyRefresh(Reply reply, String jwtToken){
        communityDetailRepository.writeReply(reply, jwtToken);
    }

    public void deletePost(int postId, String jwtToken){

        communityDetailRepository.deletePost(postId,jwtToken);

    }

    public void deleteReply(int replyId, String jwtToken){

        communityDetailRepository.deleteReply(replyId, jwtToken);

    }

    public void updateLikeCount(int postId, String jwtToken) {

        communityDetailRepository.updateLikeCount(postId, jwtToken);

    }

}