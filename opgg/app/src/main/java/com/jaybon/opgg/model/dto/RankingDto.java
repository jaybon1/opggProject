package com.jaybon.opgg.model.dto;

import com.jaybon.opgg.model.apidao.RankingModel;
import com.jaybon.opgg.model.apidao.SummonerModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RankingDto {

    private int type;
    private int statusCode;
    private String message;

    private long allUser;

    private SummonerModel summonerModel;

    private RankingModel rankingModel;

}