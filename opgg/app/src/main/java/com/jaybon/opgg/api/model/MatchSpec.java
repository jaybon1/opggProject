package com.jaybon.opgg.api.model;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


// https://kr.api.riotgames.com/lol/match/v4/matches/4561055619?api_key=RGAPI-8f2ab161-b201-4d25-a846-17abf656e8e7


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchSpec {

    public long gameId;
    public String platformId;
    public long gameCreation;
    public long gameDuration;
    public long queueId;
    public long mapId;
    public long seasonId;
    public String gameVersion;
    public String gameMode;
    public String gameType;
    public List<Team> teams;
    public List<Participant> participants;
    public List<ParticipantIdentity> participantIdentities;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Team {

        public long teamId;
        public String win;
        public boolean firstBlood;
        public boolean firstTower;
        public boolean firstInhibitor;
        public boolean firstBaron;
        public boolean firstDragon;
        public boolean firstRiftHerald;
        public long towerKills;
        public long inhibitorKills;
        public long baronKills;
        public long dragonKills;
        public long vilemawKills;
        public long riftHeraldKills;
        public long dominionVictoryScore;
        public List<Ban> bans = null;

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public class Ban {

            public long championId;
            public long pickTurn;

        }


    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Participant {

        public long participantId;
        public long teamId;
        public long championId;
        public long spell1Id;
        public long spell2Id;
        public Stats stats;

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public class Stats {

            public long participantId;
            public boolean win;
            public long item0;
            public long item1;
            public long item2;
            public long item3;
            public long item4;
            public long item5;
            public long item6;
            public long kills;
            public long deaths;
            public long assists;
            public long largestKillingSpree;
            public long largestMultiKill;
            public long killingSprees;
            public long longestTimeSpentLiving;
            public long doubleKills;
            public long tripleKills;
            public long quadraKills;
            public long pentaKills;
            public long unrealKills;
            public long totalDamageDealt;
            public long magicDamageDealt;
            public long physicalDamageDealt;
            public long trueDamageDealt;
            public long largestCriticalStrike;
            public long totalDamageDealtToChampions;
            public long magicDamageDealtToChampions;
            public long physicalDamageDealtToChampions;
            public long trueDamageDealtToChampions;
            public long totalHeal;
            public long totalUnitsHealed;
            public long damageSelfMitigated;
            public long damageDealtToObjectives;
            public long damageDealtToTurrets;
            public long visionScore;
            public long timeCCingOthers;
            public long totalDamageTaken;
            public long magicalDamageTaken;
            public long physicalDamageTaken;
            public long trueDamageTaken;
            public long goldEarned;
            public long goldSpent;
            public long turretKills;
            public long inhibitorKills;
            public long totalMinionsKilled;
            public long neutralMinionsKilled;
            public long neutralMinionsKilledTeamJungle;
            public long neutralMinionsKilledEnemyJungle;
            public long totalTimeCrowdControlDealt;
            public long champLevel;
            public long visionWardsBoughtInGame;
            public long sightWardsBoughtInGame;
            public long wardsPlaced;
            public long wardsKilled;
            public boolean firstBloodKill;
            public boolean firstBloodAssist;
            public boolean firstTowerKill;
            public boolean firstTowerAssist;
            public boolean firstInhibitorKill;
            public boolean firstInhibitorAssist;
            public long combatPlayerScore;
            public long objectivePlayerScore;
            public long totalPlayerScore;
            public long totalScoreRank;
            public long playerScore0;
            public long playerScore1;
            public long playerScore2;
            public long playerScore3;
            public long playerScore4;
            public long playerScore5;
            public long playerScore6;
            public long playerScore7;
            public long playerScore8;
            public long playerScore9;
            public long perk0;
            public long perk0Var1;
            public long perk0Var2;
            public long perk0Var3;
            public long perk1;
            public long perk1Var1;
            public long perk1Var2;
            public long perk1Var3;
            public long perk2;
            public long perk2Var1;
            public long perk2Var2;
            public long perk2Var3;
            public long perk3;
            public long perk3Var1;
            public long perk3Var2;
            public long perk3Var3;
            public long perk4;
            public long perk4Var1;
            public long perk4Var2;
            public long perk4Var3;
            public long perk5;
            public long perk5Var1;
            public long perk5Var2;
            public long perk5Var3;
            public long perkPrimaryStyle;
            public long perkSubStyle;
            public long statPerk0;
            public long statPerk1;
            public long statPerk2;

        }

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class ParticipantIdentity {

        public long participantId;
        public Player player;

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Player {

        public String platformId;
        public String accountId;
        public String summonerName;
        public String summonerId;
        public String currentPlatformId;
        public String currentAccountId;
        public String matchHistoryUri;
        public long profileIcon;

    }
}