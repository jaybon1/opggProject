package com.jaybon.opgg.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchCommonModel {

    private long gameId;
    private String platformId;
    private long gameCreation;
    private int gameDuration;
    private int mapId;
    private int seasonId;
    private String gameMode;

}