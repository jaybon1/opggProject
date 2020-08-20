package com.jaybon.opgg.api.dto;

import com.jaybon.opgg.api.model.RankingModel;
import com.jaybon.opgg.api.model.SummonerModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RankingDto {

    private int type;

    private SummonerModel summonerModel;

    private RankingModel rankingModel;

}