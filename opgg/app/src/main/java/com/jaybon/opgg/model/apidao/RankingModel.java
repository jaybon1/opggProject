package com.jaybon.opgg.model.apidao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RankingModel {

    private int id;
    private String summonerId;
    private String summonerName;
    private String tier;
    private String rank;
    private long leaguePoints;
    private long win;
    private long lose;

}