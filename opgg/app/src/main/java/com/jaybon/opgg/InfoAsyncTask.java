package com.jaybon.opgg;

import android.content.Context;
import android.icu.text.IDNA;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

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
    Context context;

    public InfoAsyncTask(String name, Context context) {
        this.name = name;
        this.context = context;
    }

    @Override
    protected void onProgressUpdate(List<InfoDto>... values) {
        super.onProgressUpdate(values);
        Toast.makeText(context, "로딩중", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected List<InfoDto> doInBackground(String... strings) {

        // 여기들어가기 직전에 프로그레바를 돌려
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://kr.api.riotgames.com/lol/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final RiotService service = retrofit.create(RiotService.class);

        Gson gson = new Gson();

        List<InfoDto> infoDtos = new ArrayList<>();


        // 유저정보
        final Call<ApiSummoner> call = service.getSummonerByName(name);

        ApiSummoner apiSummoner = ApiSummoner.builder().build();

        try {
            apiSummoner = call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 소환사가 없을 경우 빈 리스트 리턴
        if(apiSummoner == null){
            return infoDtos;
        }

        SummonerModel summonerModel = SummonerModel.builder()
                .summonerLevel(apiSummoner.getSummonerLevel())
                .summonerId(apiSummoner.getId())
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

        if(apiEntries.size() == 0){

            InfoDto infoDtoHeader = InfoDto.builder()
                    .type(0)
                    .summonerModel(summonerModel)
                    .build();
            infoDtos.add(infoDtoHeader);
            return infoDtos;
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



//        for(int i=0; i<apiMatchEntry.getMatches().size(); i++){
        for (int i = 0; i < 5; i++) {
            // 매치 정보
            final Call<ApiMatch> call3 = service.getMatchSpecByMatchId(apiMatchEntry.getMatches().get(i).getGameId());

            ApiMatch apiMatch = ApiMatch.builder().build();

            try {
                apiMatch = call3.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }

            int playerNum = 1;

            for (ParticipantIdentity participantIdentity : apiMatch.getParticipantIdentities()) {
                if (participantIdentity.getPlayer().getSummonerName().equals(apiSummoner.getName())) {
                    Log.d(TAG, "doInBackground: " + participantIdentity.getPlayer().getSummonerName() + " 의 번호는 " + participantIdentity.getParticipantId());
                    playerNum = (int) participantIdentity.getParticipantId() - 1;
                }
            }

            // 참가자 정보
            Player player = apiMatch.getParticipantIdentities().get(playerNum).getPlayer();

            // 참가자 게임정보
            Participant participant = apiMatch.getParticipants().get(playerNum);

            MatchSummonerModel matchSummonerModel = MatchSummonerModel.builder()
                    .queueId(apiMatch.getQueueId())
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

            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return infoDtos;
    }
}
