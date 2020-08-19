package com.jaybon.opgg.api.dto;

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

    // SummonoerModel
    private String name;
    private int summonoerLevel;
    private int profileIconId;

    //RankingModel
    private String tier;
    private int tierRank;
    private int lp;
    private String win;
    private String lose;

}