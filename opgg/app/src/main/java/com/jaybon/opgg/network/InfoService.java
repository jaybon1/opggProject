package com.jaybon.opgg.network;


import com.jaybon.opgg.api.model.Entry;
import com.jaybon.opgg.api.model.MatchSpec;
import com.jaybon.opgg.api.model.Matchlist;
import com.jaybon.opgg.api.model.Summoner;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface InfoService {

    @GET("summoner/v4/summoners/by-name/{summoner_name}?api_key="+Apikey.apikey)
    Call<Summoner> getSummonerByName(@Path(value = "summoner_name", encoded = true) String summonerName);

    @GET("league/v4/entries/by-summoner/{summoner_id}?api_key="+Apikey.apikey)
    Call<Entry> getEntryBySummonerId(@Path(value = "summoner_id", encoded = true) String summonerId);

    @GET("match/v4/matchlists/by-account/{account_id}?api_key="+Apikey.apikey)
    Call<Matchlist> getMatchListByAccountId(@Path(value = "account_id", encoded = true) String accountId);

    @GET("match/v4/matches/{match_id}?api_key="+Apikey.apikey)
    Call<MatchSpec> getMatchSpecByMatchId(@Path(value = "match_id", encoded = true) String matchId);

}

