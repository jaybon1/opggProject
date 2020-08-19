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
    private int teamId;
    private String win;
    private int towerKills;
    private int baronKills;
    private int dragonkils;
}