package com.jaybon.opgg.viewmodel.info;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.jaybon.opgg.model.dto.InfoDto;
import com.jaybon.opgg.model.dto.RespListDto;

public class InfoViewModel extends AndroidViewModel {

    private LiveData<RespListDto<InfoDto>> liveRespListDto;
    private InfoRepository infoRepository;

    public InfoViewModel(@NonNull Application application) {
        super(application);
        infoRepository = new InfoRepository();
        liveRespListDto = infoRepository.initData();
    }

    public void initLiveData(String summonerName){
        infoRepository.getDto(summonerName);
    }

    public LiveData<RespListDto<InfoDto>> subscribe(){
        return liveRespListDto;
    }

}
