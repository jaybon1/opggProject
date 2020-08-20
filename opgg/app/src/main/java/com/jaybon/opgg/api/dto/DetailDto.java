package com.jaybon.opgg.api.dto;

import com.jaybon.opgg.api.model.MatchCommonModel;
import com.jaybon.opgg.api.model.MatchSummonerModel;
import com.jaybon.opgg.api.model.MatchTeamModel;

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

    private MatchCommonModel matchCommonModel;

    private List<MatchTeamModel> matchTeamModels;

    private List<MatchSummonerModel> winSummonerModels;

    private List<MatchSummonerModel> loseSummonerModels;

}