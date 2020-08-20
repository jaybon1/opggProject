package com.jaybon.opgg.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchTeamModel {

    private long gameId;

    private long teamId;
    private String win;
    private long towerKills;
    private long baronKills;
    private long dragonkils;
}