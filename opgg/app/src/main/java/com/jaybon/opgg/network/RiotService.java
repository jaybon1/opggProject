package com.jaybon.opgg.network;


import com.jaybon.opgg.api.apimodel.ApiEntry;
import com.jaybon.opgg.api.apimodel.ApiMatch;
import com.jaybon.opgg.api.apimodel.ApiMatchEntry;
import com.jaybon.opgg.api.apimodel.ApiSummoner;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RiotService {

    @GET("summoner/v4/summoners/by-name/{summoner_name}?api_key="+ Riot.apikey)
    Call<ApiSummoner> getSummonerByName(@Path(value = "summoner_name", encoded = true) String summonerName);

    @GET("league/v4/entries/by-summoner/{summoner_id}?api_key="+ Riot.apikey)
    Call<List<ApiEntry>> getEntryBySummonerId(@Path(value = "summoner_id", encoded = true) String summonerId);

    @GET("match/v4/matchlists/by-account/{account_id}?api_key="+ Riot.apikey)
    Call<ApiMatchEntry> getMatchListByAccountId(@Path(value = "account_id", encoded = true) String accountId);

    @GET("match/v4/matches/{match_id}?api_key="+ Riot.apikey)
    Call<ApiMatch> getMatchSpecByMatchId(@Path(value = "match_id", encoded = true) long matchId);

}

