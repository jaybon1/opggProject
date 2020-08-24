package com.jaybon.opgg.model.dto;

import com.jaybon.opgg.model.apidao.MatchCommonModel;
import com.jaybon.opgg.model.apidao.MatchSummonerModel;
import com.jaybon.opgg.model.apidao.MatchTeamModel;

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

    private int type;

    private MatchCommonModel matchCommonModel;

    private MatchTeamModel winTeam;
    private MatchTeamModel loseTeam;

    private List<MatchSummonerModel> winSummonerModels;
    private List<MatchSummonerModel> loseSummonerModels;

}