package com.jaybon.opgg.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchSummonerModel {

    private long gameId;
    private long queueId;

    private long gameCreation;
    private long gameDuration;

    private String summonerName;
    private long participantId;
    private long teamId;
    private long championId;
    private long spell1Id;
    private long spell2Id;
    private boolean win;
    private long item0;
    private long item1;
    private long item2;
    private long item3;
    private long item4;
    private long item5;
    private long item6;
    private long kills;
    private long deaths;
    private long assists;
    private long totalDamageDealtToChampions;
    private long goldEarned;
    private long totalMinionsKilled;
    private long champLevel;
    private long sightWardsBoughtInGame;
    private long wardsPlaced;
    private long wardsKilled;
    private long perkPrimaryStyle;
    private long perkSubStyle;

}