package com.jaybon.opgg;

import android.icu.text.IDNA;
import android.os.AsyncTask;

import com.jaybon.opgg.adapter.InfoAdapter;
import com.jaybon.opgg.api.dto.InfoDto;
import com.jaybon.opgg.network.Riot;
import com.merakianalytics.orianna.Orianna;
import com.merakianalytics.orianna.types.common.Region;
import com.merakianalytics.orianna.types.core.summoner.Summoner;

import java.util.List;

public class InfoAsyncTask extends AsyncTask<String, InfoDto, InfoDto> {


    @Override
    protected InfoDto doInBackground(String... strings) {

        Orianna.setRiotAPIKey(Riot.apikey);
        Orianna.setDefaultRegion(Region.KOREA);

        Summoner summoner = Orianna.summonerNamed("hide on bush").get();

        // 헤더
        InfoDto infoDto = InfoDto.builder()
                .type(0)
//                        .summoner(summoner)
                .summonerLevel(summoner.getLevel())
                .summonerName(summoner.getName())
                .build();

        return infoDto;
    }
}
