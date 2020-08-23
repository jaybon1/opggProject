package com.jaybon.opgg.viewmodel.rank;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.jaybon.opgg.model.dto.InfoDto;
import com.jaybon.opgg.model.dto.RankingDto;

import java.util.List;

public class RankViewModel extends AndroidViewModel {

    private LiveData<List<RankingDto>> liveRankingDtos;
    private RankRepository rankRepository;

    public RankViewModel(@NonNull Application application) {
        super(application);
        rankRepository = new RankRepository();
        liveRankingDtos = rankRepository.initData();
    }

    public void initLiveData(long page){
        rankRepository.getRankingDtos(page);
    }

    public LiveData<List<RankingDto>> subscribe(){
        return liveRankingDtos;
    }

}
