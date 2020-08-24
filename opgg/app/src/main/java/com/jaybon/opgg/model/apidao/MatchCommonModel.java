package com.jaybon.opgg.model.apidao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchCommonModel {

    private int id;

    private long gameId;
    private long queueId;
    private String platformId;
    private long gameCreation;
    private long gameDuration;
    private long mapId;
    private long seasonId;
    private String gameMode;

}