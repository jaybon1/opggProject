package com.jaybon.opgg.network;


import com.jaybon.opgg.api.model.EntryModel;
import com.jaybon.opgg.api.model.MatchModel;
import com.jaybon.opgg.api.model.MatchListModel;
import com.jaybon.opgg.api.model.SummonerModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface InfoService {

    @GET("summoner/v4/summoners/by-name/{summoner_name}?api_key="+ Riot.apikey)
    Call<SummonerModel> getSummonerByName(@Path(value = "summoner_name", encoded = true) String summonerName);

    @GET("league/v4/entries/by-summoner/{summoner_id}?api_key="+ Riot.apikey)
    Call<EntryModel> getEntryBySummonerId(@Path(value = "summoner_id", encoded = true) String summonerId);

    @GET("match/v4/matchlists/by-account/{account_id}?api_key="+ Riot.apikey)
    Call<MatchListModel> getMatchListByAccountId(@Path(value = "account_id", encoded = true) String accountId);

    @GET("match/v4/matches/{match_id}?api_key="+ Riot.apikey)
    Call<MatchModel> getMatchSpecByMatchId(@Path(value = "match_id", encoded = true) String matchId);

}

