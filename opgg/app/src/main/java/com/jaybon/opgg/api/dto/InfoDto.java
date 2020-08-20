package com.jaybon.opgg.api.dto;

import com.jaybon.opgg.api.model.EntryModel;
import com.jaybon.opgg.api.model.MatchSummonerModel;
import com.jaybon.opgg.api.model.SummonerModel;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InfoDto {

    private int type;

    private SummonerModel summonerModel;

    private List<EntryModel> entryModels;

    private MatchSummonerModel matchSummonerModel;
}