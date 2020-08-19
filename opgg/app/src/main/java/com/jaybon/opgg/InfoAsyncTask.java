package com.jaybon.opgg;

import android.os.AsyncTask;

import com.jaybon.opgg.api.dto.InfoDto;
import com.jaybon.opgg.api.dto.attr.EntryDto;
import com.jaybon.opgg.network.Riot;
import com.merakianalytics.orianna.Orianna;
import com.merakianalytics.orianna.types.common.Region;
import com.merakianalytics.orianna.types.core.summoner.Summoner;
import com.merakianalytics.orianna.types.core.league.LeagueEntry;

import java.util.ArrayList;
import java.util.List;

public class InfoAsyncTask extends AsyncTask<String, List<InfoDto>, List<InfoDto>> {

    String name;

    public InfoAsyncTask(String name) {
        this.name = name;
    }

    @Override
    protected List<InfoDto> doInBackground(String... strings) {

        List<InfoDto> infoDtos = new ArrayList<>();

        Orianna.setRiotAPIKey(Riot.apikey);
        Orianna.setDefaultRegion(Region.KOREA);

        Summoner summoner = Orianna.summonerNamed("hide on bush").get();

        LeagueEntry leagueEntry1 = summoner.getLeaguePositions().get(0);

        EntryDto entryDto = EntryDto.builder()
                .leaguePoints(summoner.getLeaguePositions().get(0).getLeaguePoints())
                .build();



        InfoDto infoDto = InfoDto.builder()
                .type(0)
                .name(summoner.getName())
                .summonoerLevel(summoner.getLevel())
                .build();

        return infoDtos;
    }
}
