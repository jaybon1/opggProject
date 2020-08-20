package com.jaybon.opgg;

import android.icu.text.IDNA;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.jaybon.opgg.api.apimodel.ApiEntry;
import com.jaybon.opgg.api.apimodel.ApiMatch;
import com.jaybon.opgg.api.apimodel.ApiMatchEntry;
import com.jaybon.opgg.api.apimodel.ApiSummoner;
import com.jaybon.opgg.api.apimodel.attr.match.Participant;
import com.jaybon.opgg.api.apimodel.attr.match.ParticipantIdentity;
import com.jaybon.opgg.api.apimodel.attr.match.Player;
import com.jaybon.opgg.api.dto.InfoDto;
import com.jaybon.opgg.api.model.EntryModel;
import com.jaybon.opgg.api.model.MatchSummonerModel;
import com.jaybon.opgg.api.model.SummonerModel;
import com.jaybon.opgg.network.RiotService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InfoAsyncTask extends AsyncTask<String, List<InfoDto>, List<InfoDto>> {

    private static final String TAG = "InfoAsyncTask";

    String name;

    public InfoAsyncTask(String name) {
        this.name = name;
    }

    @Override
    protected List<InfoDto> doInBackground(String... strings) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://kr.api.riotgames.com/lol/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final RiotService service = retrofit.create(RiotService.class);

        Gson gson = new Gson();

        List<InfoDto> infoDtos = new ArrayList<>();

        // 유저정보
        final Call<ApiSummoner> call = service.getSummonerByName("hideonbush");

        ApiSummoner apiSummoner = ApiSummoner.builder().build();

        try {
            apiSummoner = call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        SummonerModel summonerModel = SummonerModel.builder()
                .summonerLevel(apiSummoner.getSummonerLevel())
                .id(apiSummoner.getId())
                .accountId(apiSummoner.getAccountId())
                .profileIconId(apiSummoner.getProfileIconId())
                .name(apiSummoner.getName())
                .puuid(apiSummoner.getPuuid())
                .build();


        // 엔트리
        List<ApiEntry> apiEntries = new ArrayList<>();
        List<EntryModel> entryModels = new ArrayList<>();

        final Call<List<ApiEntry>> call1 = service.getEntryBySummonerId(apiSummoner.getId());

        try {
            apiEntries = call1.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (apiEntries.get(0).getQueueType().equals("RANKED_SOLO_5x5")) {

            ApiEntry apiEntry = apiEntries.get(0);

            String division = "1";
            if (apiEntry.getRank().equals("I")) {
                division = "1";
            } else if (apiEntry.getRank().equals("II")) {
                division = "2";
            } else if (apiEntry.getRank().equals("III")) {
                division = "3";
            } else if (apiEntry.getRank().equals("IV")) {
                division = "4";
            }

            EntryModel entryModel = EntryModel.builder()
                    .leaguePoints(apiEntry.getLeaguePoints())
                    .queueType(apiEntry.getQueueType())
                    .leagueId(apiEntry.getLeagueId())
                    .summonerId(apiEntry.getSummonerId())
                    .summonerName(apiEntry.getSummonerName())
                    .tier(apiEntry.getTier())
                    .rank(apiEntry.getRank())
                    .tierRankId(apiEntry.getTier().toLowerCase() + "_" + division)
                    .wins(apiEntry.getWins())
                    .losses(apiEntry.getLosses())
                    .build();

            entryModels.add(entryModel);
            // 더미데이터
            entryModels.add(EntryModel.builder().build());
        } else {
            for (int i = 1; i >= 0; i--) {
                ApiEntry apiEntry = apiEntries.get(i);

                String division = "1";
                if (apiEntry.getRank().equals("I")) {
                    division = "1";
                } else if (apiEntry.getRank().equals("II")) {
                    division = "2";
                } else if (apiEntry.getRank().equals("III")) {
                    division = "3";
                } else if (apiEntry.getRank().equals("IV")) {
                    division = "4";
                }

                EntryModel entryModel = EntryModel.builder()
                        .leaguePoints(apiEntry.getLeaguePoints())
                        .queueType(apiEntry.getQueueType())
                        .leagueId(apiEntry.getLeagueId())
                        .summonerId(apiEntry.getSummonerId())
                        .summonerName(apiEntry.getSummonerName())
                        .tier(apiEntry.getTier())
                        .rank(apiEntry.getRank())
                        .tierRankId(apiEntry.getTier().toLowerCase() + "_" + division)
                        .wins(apiEntry.getWins())
                        .losses(apiEntry.getLosses())
                        .build();

                entryModels.add(entryModel);
            }
        }


        InfoDto infoDtoHeader = InfoDto.builder()
                .type(0)
                .summonerModel(summonerModel)
                .entryModels(entryModels)
                .build();

        // 헤더 입력
        infoDtos.add(infoDtoHeader);


        // 경기 리스트
        final Call<ApiMatchEntry> call2 = service.getMatchListByAccountId(apiSummoner.getAccountId());

        ApiMatchEntry apiMatchEntry = ApiMatchEntry.builder().build();

        try {
            apiMatchEntry = call2.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 매치 정보
        final Call<ApiMatch> call3 = service.getMatchSpecByMatchId(apiMatchEntry.getMatches().get(0).getGameId());

//        MatchSummonerModel matchSummonerModel = MatchSummonerModel.builder().build();

        ApiMatch apiMatch = ApiMatch.builder().build();

        try {
            apiMatch = call3.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int playerNum = 1;

        for (ParticipantIdentity participantIdentity : apiMatch.getParticipantIdentities()){
            if(participantIdentity.getPlayer().getSummonerName().equals(apiSummoner.getName())){
                playerNum = (int) participantIdentity.getParticipantId();
            }
        }

        // 참가자 정보
        Player player = apiMatch.getParticipantIdentities().get(playerNum).getPlayer();

        // 참가자 게임정보
        Participant participant = apiMatch.getParticipants().get(playerNum);

        MatchSummonerModel matchSummonerModel = MatchSummonerModel.builder()
                .gameId(apiMatch.getGameId())
                .teamId(participant.getTeamId())
                .participantId(participant.getParticipantId())
                .gameCreation(apiMatch.getGameCreation())
                .gameDuration(apiMatch.getGameDuration())
                .summonerName(player.getSummonerName())
                .win(participant.getStats().isWin())
                .championId(participant.getChampionId())
                .champLevel(participant.getStats().getChampLevel())
                .item0(participant.getStats().getItem0())
                .item1(participant.getStats().getItem1())
                .item2(participant.getStats().getItem2())
                .item3(participant.getStats().getItem3())
                .item4(participant.getStats().getItem4())
                .item5(participant.getStats().getItem5())
                .item6(participant.getStats().getItem6())
                .kills(participant.getStats().getKills())
                .deaths(participant.getStats().getDeaths())
                .assists(participant.getStats().getAssists())
                .goldEarned(participant.getStats().getGoldEarned())
                .perkPrimaryStyle(participant.getStats().getPerkPrimaryStyle())
                .perkSubStyle(participant.getStats().getPerkSubStyle())
                .sightWardsBoughtInGame(participant.getStats().getSightWardsBoughtInGame())
                .wardsPlaced(participant.getStats().getWardsPlaced())
                .wardsKilled(participant.getStats().getWardsKilled())
                .spell1Id(participant.getSpell1Id())
                .spell2Id(participant.getSpell2Id())
                .totalMinionsKilled(participant.getStats().getTotalMinionsKilled())
                .totalDamageDealtToChampions(participant.getStats().getTotalDamageDealtToChampions())
                .build();

        InfoDto infoDto = InfoDto.builder()
                .type(1)
                .matchSummonerModel(matchSummonerModel)
                .build();

        infoDtos.add(infoDto);

        return infoDtos;
    }
}
