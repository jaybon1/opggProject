package com.jaybon.opgg.model.dto;

import com.jaybon.opgg.model.apidao.EntryModel;
import com.jaybon.opgg.model.apidao.MatchSummonerModel;
import com.jaybon.opgg.model.apidao.SummonerModel;

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

    private long radder;

    private SummonerModel summonerModel;

    private List<EntryModel> entryModels;

    private MatchSummonerModel matchSummonerModel;

}