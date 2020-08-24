package com.jaybon.opgg.viewmodel.info;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.jaybon.opgg.model.dto.InfoDto;
import com.jaybon.opgg.model.dto.RankingDto;
import com.jaybon.opgg.model.dto.RespDto;

import java.util.List;

public class InfoViewModel extends AndroidViewModel {

    private LiveData<RespDto<List<InfoDto>>> liveRespDto;
    private InfoRepository infoRepository;

    public InfoViewModel(@NonNull Application application) {
        super(application);
        infoRepository = new InfoRepository();
        liveRespDto = infoRepository.initData();
    }

    public void initLiveData(String summonerName){
        infoRepository.getDto(summonerName);
    }

    public LiveData<RespDto<List<InfoDto>>> subscribe(){
        return liveRespDto;
    }

}
