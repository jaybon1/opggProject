package com.jaybon.opgg.model.network;


import com.jaybon.opgg.model.dto.InfoDto;
import com.jaybon.opgg.model.dto.RankingDto;
import com.jaybon.opgg.model.dto.RespDto;
import com.jaybon.opgg.model.dto.RespListDto;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface OpggService {

    // rankingDto 가져오기 아이디 검색
    @GET("test/ranking/name/{summonerName}")
    Call<RespDto<?>> getRankingBySummonerName(@Path(value = "summonerName", encoded = true) String summonerName);

    // rankingDto 가져오기
    @GET("test/ranking/page/{page}")
    Call<RespListDto<RankingDto>> getRankingByPage(@Path(value = "page", encoded = true) long page);

    // detailDto 가져오기
    @GET("test/detail/gameid/{gameId}")
    Call<RespDto<?>> getDetailByGameId(@Path(value = "gameId", encoded = true) long gameId);

    // infoDto 가져오기
    @GET("test/info/name/{summonerName}")
    Call<RespListDto<InfoDto>> getInfoByName(@Path(value = "summonerName", encoded = true) String summonerName);

}

