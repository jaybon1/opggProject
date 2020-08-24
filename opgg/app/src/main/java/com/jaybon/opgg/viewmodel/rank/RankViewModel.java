package com.jaybon.opgg.viewmodel.rank;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.jaybon.opgg.model.dto.RankingDto;
import com.jaybon.opgg.model.dto.RespListDto;

public class RankViewModel extends AndroidViewModel {

    private LiveData<RespListDto<RankingDto>> liveRespListDto;
    private RankRepository rankRepository;

    public RankViewModel(@NonNull Application application) {
        super(application);
        rankRepository = new RankRepository();
        liveRespListDto = rankRepository.initData();
    }

    public void initLiveData(long page){
        rankRepository.getDto(page);
    }

    public LiveData<RespListDto<RankingDto>> subscribe(){
        return liveRespListDto;
    }

}
