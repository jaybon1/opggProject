package com.jaybon.opgg;

import android.os.AsyncTask;

import com.jaybon.opgg.api.dto.InfoDto;
import com.jaybon.opgg.api.dto.attr.EntryDto;
import com.jaybon.opgg.network.Riot;
import com.merakianalytics.orianna.Orianna;
import com.merakianalytics.orianna.types.common.Region;
import com.merakianalytics.orianna.types.core.league.LeaguePositions;
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

        // 소환사정보
        Summoner summoner = Orianna.summonerNamed(name).get();

        // 엔트리
//        LeagueEntry leagueEntry0 = summoner.getLeaguePositions().get(0);
//        LeagueEntry leagueEntry1 = summoner.getLeaguePositions().get(1);

        LeaguePositions leaguePositions = summoner.getLeaguePositions();

        List<EntryDto> entryDtos = new ArrayList<>();

        // 엔트리 정보 저장
        // 가져온 첫번째 데이터가 솔랭이면 0번 저장
        if(leaguePositions.get(0).getQueue().getTag().equals("RANKED_SOLO_5x5")){

            LeagueEntry leagueEntry = leaguePositions.get(0);

            String division = "1";
            if (leagueEntry.getDivision().name().equals("I")){
                division = "1";
            } else if (leagueEntry.getDivision().name().equals("II")){
                division = "2";
            } else if (leagueEntry.getDivision().name().equals("III")){
                division = "3";
            } else if (leagueEntry.getDivision().name().equals("IV")){
                division = "4";
            }

            EntryDto entryDto = EntryDto.builder()
                    .leaguePoints(leagueEntry.getLeaguePoints())
                    .queueType(leagueEntry.getQueue().getTag())
                    .tier(leagueEntry.getTier().name())
                    .rank(leagueEntry.getDivision().name())
                    .wins(leagueEntry.getWins())
                    .losses(leagueEntry.getLosses())
                    .tierRankId(leagueEntry.getTier().name().toLowerCase()+"_"+division)
                    .build();


            // 더미데이터
            EntryDto entryDto1 = EntryDto.builder()
                    .build();


            entryDtos.add(entryDto);
            entryDtos.add(entryDto1);

        } else { // 첫번째 데이터가 솔랭이 아니면 1번부터 저장
            for (int i=1; i>=0; i--){
                LeagueEntry leagueEntry = leaguePositions.get(i);

                String division = "1";
                if (leagueEntry.getDivision().name().equals("I")){
                    division = "1";
                } else if (leagueEntry.getDivision().name().equals("II")){
                    division = "2";
                } else if (leagueEntry.getDivision().name().equals("III")){
                    division = "3";
                } else if (leagueEntry.getDivision().name().equals("IV")){
                    division = "4";
                }

                EntryDto entryDto = EntryDto.builder()
                        .leaguePoints(leagueEntry.getLeaguePoints())
                        .queueType(leagueEntry.getQueue().getTag())
                        .tier(leagueEntry.getTier().name())
                        .rank(leagueEntry.getDivision().name())
                        .wins(leagueEntry.getWins())
                        .losses(leagueEntry.getLosses())
                        .tierRankId(leagueEntry.getTier().name().toLowerCase()+"_"+division)
                        .build();
                entryDtos.add(entryDto);
            }
        }


        // 인포데이터에 추가
        InfoDto infoDto = InfoDto.builder()
                .type(0)
                .name(summoner.getName())
                .summonoerLevel(summoner.getLevel())
                .profileIconId(summoner.getProfileIcon().getId())
                .entryDtos(entryDtos)
                .build();

        infoDtos.add(infoDto);

        return infoDtos;
    }
}
