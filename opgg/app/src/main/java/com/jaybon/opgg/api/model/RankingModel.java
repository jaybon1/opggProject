package com.jaybon.opgg.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RankingModel {

    private String summonerName;
    private String tier;
    private int tierRank;
    private int lp;
    private String win;
    private String lose;
    private String queueType;

}