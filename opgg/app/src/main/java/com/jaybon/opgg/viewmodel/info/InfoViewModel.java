package com.jaybon.opgg.viewmodel.info;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.jaybon.opgg.model.dto.InfoDto;

import java.util.List;

public class InfoViewModel extends AndroidViewModel {

    private LiveData<List<InfoDto>> liveInfoDtos;
    private InfoRepository infoRepository;

    public InfoViewModel(@NonNull Application application) {
        super(application);
        infoRepository = new InfoRepository();
        liveInfoDtos = infoRepository.initData();
    }

    public void initLiveData(String summonerName){
        infoRepository.getInfoDtos(summonerName);
    }

    public LiveData<List<InfoDto>> subscribe(){
        return liveInfoDtos;
    }

}
