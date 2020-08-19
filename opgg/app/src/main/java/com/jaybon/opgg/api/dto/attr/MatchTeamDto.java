package com.jaybon.opgg.api.dto.attr;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchTeamDto {

    //MachteamModel
    private String win;
    private int towerKills;
    private int baronKills;
    private int dragonkils;
}