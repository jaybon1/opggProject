package com.jaybon.opgg.viewmodel.detail;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.jaybon.opgg.model.dto.DetailDto;
import com.jaybon.opgg.model.dto.RespDto;

public class DetailViewModel extends AndroidViewModel {

    private LiveData<RespDto<DetailDto>> liveRespDto;
    private DetailRepository detailRepository;

    public DetailViewModel(@NonNull Application application) {
        super(application);
        detailRepository = new DetailRepository();
        liveRespDto = detailRepository.initData();
    }

    public void initLiveData(long gameId){
        detailRepository.getDto(gameId);
    }

    public LiveData<RespDto<DetailDto>> subscribe(){
        return liveRespDto;
    }

}
