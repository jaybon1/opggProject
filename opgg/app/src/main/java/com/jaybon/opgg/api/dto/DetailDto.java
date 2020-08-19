package com.jaybon.opgg.api.dto;

import com.jaybon.opgg.api.dto.attr.MatchSummonerDto;
import com.jaybon.opgg.api.dto.attr.MatchTeamDto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetailDto {
    //entryModel
    private String tier;
    private int tierRank;

    //MachteamModel
    private List<MatchTeamDto> matchTeamDtos;

    //MatchSummonerModel
    private List<MatchSummonerDto> matchSummonerDtos;
}