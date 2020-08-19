package com.jaybon.opgg.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SummonerModel {

    private int id;
    private String accountId;
    private String puuid;
    private String name;
    private int profileIconId;
    private int revisionDate;
    private int summonerLevel;

}