package com.jaybon.opgg.viewmodel.rank;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.jaybon.opgg.model.dto.RankingDto;
import com.jaybon.opgg.model.dto.RespDto;

import java.util.List;

public class RankViewModel extends AndroidViewModel {

    private LiveData<RespDto<List<RankingDto>>> liveRespDto;
    private RankRepository rankRepository;

    public RankViewModel(@NonNull Application application) {
        super(application);
        rankRepository = new RankRepository();
        liveRespDto = rankRepository.initData();
    }

    public void initLiveData(long page){
        rankRepository.getDto(page);
    }

    public LiveData<RespDto<List<RankingDto>>> subscribe(){
        return liveRespDto;
    }

}
