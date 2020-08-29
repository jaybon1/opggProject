package com.jaybon.opgg.model.network;


import com.jaybon.opgg.model.dao.Reply;
import com.jaybon.opgg.model.dto.CommunityDto;
import com.jaybon.opgg.model.dto.DetailDto;
import com.jaybon.opgg.model.dto.GoogleLoginDto;
import com.jaybon.opgg.model.dto.InfoDto;
import com.jaybon.opgg.model.dto.JoinDto;
import com.jaybon.opgg.model.dto.LoginDto;
import com.jaybon.opgg.model.dto.RankingDto;
import com.jaybon.opgg.model.dto.RespDto;
import com.jaybon.opgg.model.dto.TokenDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface OpggService {

    // rankingDto 가져오기 아이디 검색
    @GET("test/ranking/name/{summonerName}")
    Call<RespDto<List<RankingDto>>> getRankingBySummonerName(@Path(value = "summonerName", encoded = true) String summonerName);

    // rankingDto 가져오기
    @GET("test/ranking/page/{page}")
    Call<RespDto<List<RankingDto>>> getRankingByPage(@Path(value = "page", encoded = true) long page);

    // detailDto 가져오기
    @GET("test/detail/gameid/{gameId}")
    Call<RespDto<DetailDto>> getDetailByGameId(@Path(value = "gameId", encoded = true) long gameId);

    // infoDto 가져오기
    @GET("test/info/name/{summonerName}")
    Call<RespDto<List<InfoDto>>> getInfoByName(@Path(value = "summonerName", encoded = true) String summonerName);

    // infoDto 가져오기
    @GET("test/info/update/name/{summonerName}")
    Call<RespDto<List<InfoDto>>> updateInfoByName(@Path(value = "summonerName", encoded = true) String summonerName);

    // communityDto 가져오기
    @GET("post/{page}")
    Call<RespDto<List<CommunityDto>>> getPostByPage(@Path(value = "page", encoded = true) int page);

    // 회원가입
    @POST("test/join")
    Call<RespDto<String>> join(@Body JoinDto joinDto);

    // 로그인
    @POST("oauth/jwt/common")
    Call<RespDto<TokenDto>> login(@Body LoginDto loginDto);

    // 구글로그인
    @POST("oauth/jwt/google")
    Call<RespDto<TokenDto>> googleLogin(@Body GoogleLoginDto googleLoginDto);

    // 글 상세보기
    @GET("post/detail/{id}")
    Call<RespDto<CommunityDto>> getPostById(@Path(value = "id", encoded = true) long id, @Header("Authorization") String bearerToken);

    // 댓글쓰기
    @POST("reply/writeProc")
    Call<RespDto<CommunityDto>> writeReply(@Body Reply reply, @Header("Authorization") String bearerToken);

}

