package com.jaybon.opgg.api.dto;

import com.jaybon.opgg.api.dto.attr.EntryDto;
import com.jaybon.opgg.api.dto.attr.MatchDto;

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

    // SummonoerModel
    private String name;
    private int summonoerLevel;
    private int profileIconId;

    private List<EntryDto> entryDtos;

    private List<MatchDto> matchDtos;

}