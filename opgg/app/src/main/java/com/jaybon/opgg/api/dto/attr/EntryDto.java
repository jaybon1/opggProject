package com.jaybon.opgg.api.dto.attr;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EntryDto {
    private String queueType;
    private String tier;
    private int rank;
    private int leaguePoints;
    private int wins;
    private int losses;
}
