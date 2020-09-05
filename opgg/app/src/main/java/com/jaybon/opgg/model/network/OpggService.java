package com.jaybon.opgg.model.network;


import com.jaybon.opgg.model.dao.Post;
import com.jaybon.opgg.model.dao.Reply;
import com.jaybon.opgg.model.dto.CommunityDto;
import com.jaybon.opgg.model.dto.DetailDto;
import com.jaybon.opgg.model.dto.GoogleLoginDto;
import com.jaybon.opgg.model.dto.InfoDto;
import com.jaybon.opgg.model.dto.JoinDto;
import com.jaybon.opgg.model.dto.KakaoLoginDto;
import com.jaybon.opgg.model.dto.LoginDto;
import com.jaybon.opgg.model.dto.RankingDto;
import com.jaybon.opgg.model.dto.RespDto;
import com.jaybon.opgg.model.dto.TokenDto;

import java.util.List;

import lombok.Getter;
import lombok.experimental.Delegate;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface OpggService {

    // rankingDto 가져오기 아이디로 검색
    @GET("api/ranking/name/{summonerName}")
    Call<RespDto<List<RankingDto>>> getRankingBySummonerName(@Path(value = "summonerName", encoded = true) String summonerName);

    // rankingDto 가져오기 페이지로 검색
    @GET("api/ranking/page/{page}")
    Call<RespDto<List<RankingDto>>> getRankingByPage(@Path(value = "page", encoded = true) long page);

    // detailDto 가져오기 게임아이디로 검색
    @GET("api/detail/gameid/{gameId}")
    Call<RespDto<DetailDto>> getDetailByGameId(@Path(value = "gameId", encoded = true) long gameId);

    // infoDto 가져오기 아이디로 검색
    @GET("api/info/name/{summonerName}")
    Call<RespDto<List<InfoDto>>> getInfoByName(@Path(value = "summonerName", encoded = true) String summonerName);

    // infoDto 가져오기 전적갱신
    @POST("api/info/update/name/{summonerName}")
    Call<RespDto<List<InfoDto>>> updateInfoByName(@Path(value = "summonerName", encoded = true) String summonerName);

    // 회원가입
    @POST("join")
    Call<RespDto<String>> join(@Body JoinDto joinDto);

    // 로그인
    @POST("jwt/common")
    Call<RespDto<TokenDto>> login(@Body LoginDto loginDto);

    // 구글로그인
    @POST("jwt/oauth")
    Call<RespDto<TokenDto>> googleLogin(@Body GoogleLoginDto googleLoginDto);

    // 카카오로그인
    @POST("jwt/oauth")
    Call<RespDto<TokenDto>> kakaoLogin(@Body KakaoLoginDto kakaoLoginDto);

    // 글목록 가져오기 communityDto 가져오기
    @GET("post/{page}")
    Call<RespDto<List<CommunityDto>>> getPostByPage(@Path(value = "page", encoded = true) int page);

    // 글검색 communityDto 가져오기
    @GET("post/find/{content}")
    Call<RespDto<List<CommunityDto>>> getPostByContent(@Path(value = "content", encoded = true) String content);

    // 글 상세보기
    @GET("post/detail/{id}")
    Call<RespDto<CommunityDto>> getPostById(@Path(value = "id", encoded = true) long id, @Header("Authorization") String bearerToken);

    // 글쓰기
    @POST("post/writeProc")
    Call<RespDto<String>> writePost(@Body Post post, @Header("Authorization") String bearerToken);

    // 글수정
    @PUT("post/update")
    Call<RespDto<String>> updatePost(@Body Post post, @Header("Authorization") String bearerToken);

    // 글삭제
    @DELETE("post/delete/{id}")
    Call<RespDto<String>> deletePost(@Path(value = "id", encoded = true) int id, @Header("Authorization") String bearerToken);

    // 댓글쓰기
    @POST("reply/writeProc")
    Call<RespDto<String>> writeReply(@Body Reply reply, @Header("Authorization") String bearerToken);

    // 댓글삭제
    @DELETE("reply/delete/{id}")
    Call<RespDto<String>> deleteReply(@Path(value = "id", encoded = true) int id, @Header("Authorization") String bearerToken);

    //글 뷰카운트 올리기
    @PUT("post/update/view/{postId}")
    Call<RespDto<String>> updateViewCount(@Path(value = "postId", encoded = true) int postId, @Header("Authorization") String bearerToken);

    //글 좋아요 올리기
    @PUT("post/update/like/{postId}")
    Call<RespDto<CommunityDto>> updateLikeCount(@Path(value = "postId", encoded = true) int postId, @Header("Authorization") String bearerToken);

}

