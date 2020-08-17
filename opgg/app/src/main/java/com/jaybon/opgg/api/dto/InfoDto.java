package com.jaybon.opgg.api.dto;

import com.jaybon.opgg.api.model.Entry;
import com.jaybon.opgg.api.model.MatchSpec;
import com.jaybon.opgg.api.model.Summoner;

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
    private Summoner summoner;
    private List<Entry> entries;
    private String queueType;
    private String gameDate;
    private long gameCreation;
    private long gameDuration;
    private long spell1;
    private long spell2;
    private long perk1;
    private long perk2;
    private long kills;
    private long deaths;
    private long assists;
    private String killConcerned;
    private long item1;
    private long item2;
    private long item3;
    private long item4;
    private long item5;
    private long item6;
    private long accessory;

    // type 0 = header / 2 = footer
}
