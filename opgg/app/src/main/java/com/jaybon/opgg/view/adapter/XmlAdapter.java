package com.jaybon.opgg.view.adapter;


import android.util.Log;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

//xml에 데이터 바인딩 하여 값을 원하는대로 변경
public class XmlAdapter {
    private static final String TAG = "XmlAdapter";

    // 큐타입
    public static String solo = "솔랭";
    public static String flex = "자유 5:5 랭크";

    // 조건에 맟춰서 승 패 데이터
    public static String win = "승";
    public static String lose = "패";

    // 댓글수 리턴 [300] 모양
    public static String parseReplySize(int replySize){
        return "["+replySize+"]";
    }

    // 롱 값을 인트값으로 변경
    public static int longToint(long longNum){
        int intNum = Integer.parseInt(String.valueOf(longNum));
        return intNum;
    }

    // 골드입력
    public static String getGold(long gold){
        String goldK = String.format("%.1fK", ((double)gold/1000.0));
        return goldK;
    }

    // CS 입력
    public static String getCS(long cs, long duration){

        String csData = ""+cs+"("+(cs/(duration/60))+")";
        return csData;
    }

    // 평점 계산
    public static String getGrade (long kill, long death, long assist) {

        if(death == 0){
            return "Perfect 평점";
        }

        double killDouble = (double) kill;
        double deathDouble = (double) death;
        double assistDouble = (double) assist;

        double grade = (killDouble + assistDouble) / deathDouble;

        return String.format("%.2f : 1 평점", grade);
    }

    // 숫자 시간을 분과 초로 변경
    public static String getDuration(long duration) {

        long minutes = duration / 60;
        long seconds = duration % 60;

        return String.format("%02d:%02d",minutes,seconds);
    }

    // 티어랭크를 합치기
    public static String getTierRank(String tier, String rank) {

        StringBuilder sb = new StringBuilder();

        if(tier.equals("CHALLENGER")){
            sb.append("C");
        } else if(tier.equals("GRANDMASTER")){
            sb.append("GM");
        } else if(tier.equals("MASTER")){
            sb.append("M");
        } else if(tier.equals("DIAMOND")) {
            sb.append("D");
        } else if(tier.equals("PLATINUM")){
            sb.append("P");
        } else if(tier.equals("GOLD")){
            sb.append("G");
        } else if(tier.equals("SILVER")){
            sb.append("S");
        } else if(tier.equals("BRONZE")){
            sb.append("B");
        } else {
            sb.append("I");
        }

        if(rank.equals("I")){
            sb.append("1");
        } else if(rank.equals("II")){
            sb.append("2");
        } else if(rank.equals("III")){
            sb.append("3");
        } else if(rank.equals("IIIV")){
            sb.append("4");
        }

        return sb.toString();
    }

    // 타임스탬프 시간을 날짜로 변경
    public static String getDate(Timestamp timestamp) {
        if(timestamp == null){
            return "9999.12.31";
        }

        long ts = timestamp.getTime();

        if(ts + 86400000 > System.currentTimeMillis()){
            long temp = System.currentTimeMillis() - ts;
            if(temp < 60000){
                return temp/1000+"초 전";
            } else if(temp < 3600000){
                return temp/60000+"분 전";
            } else{
                return temp/3600000+"시간 전";
            }
        }

        Date date = new Date(ts);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        String dateString = sdf.format(date);

        return dateString;
    }

    // 롱 시간을 날짜로 변경
    public static String getCreation(long creation) {

        if(creation + 86400000 > System.currentTimeMillis()){
            long temp = System.currentTimeMillis() - creation;
            if(temp < 60000){
                return temp/1000+"초 전";
            } else if(temp < 3600000){
                return temp/60000+"분 전";
            } else{
                return temp/3600000+"시간 전";
            }
        }

        Date date = new Date(creation);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        String creationString = sdf.format(date);

        return creationString;
    }

    // 챔피언 데이터
    public static String getChampName(long champId) {

        String champName = "";

        if (champId == 266) {
            champName = "Aatrox";
        } else if (champId == 103) {
            champName = "Ahri";
        } else if (champId == 84) {
            champName = "Akali";
        } else if (champId == 12) {
            champName = "Alistar";
        } else if (champId == 32) {
            champName = "Amumu";
        } else if (champId == 34) {
            champName = "Anivia";
        } else if (champId == 1) {
            champName = "Annie";
        } else if (champId == 523) {
            champName = "Aphelios";
        } else if (champId == 22) {
            champName = "Ashe";
        } else if (champId == 136) {
            champName = "AurelionSol";
        } else if (champId == 268) {
            champName = "Azir";
        } else if (champId == 432) {
            champName = "Bard";
        } else if (champId == 53) {
            champName = "Blitzcrank";
        } else if (champId == 63) {
            champName = "Brand";
        } else if (champId == 201) {
            champName = "Braum";
        } else if (champId == 51) {
            champName = "Caitlyn";
        } else if (champId == 164) {
            champName = "Camille";
        } else if (champId == 69) {
            champName = "Cassiopeia";
        } else if (champId == 31) {
            champName = "Chogath";
        } else if (champId == 42) {
            champName = "Corki";
        } else if (champId == 122) {
            champName = "Darius";
        } else if (champId == 131) {
            champName = "Diana";
        } else if (champId == 119) {
            champName = "Draven";
        } else if (champId == 36) {
            champName = "DrMundo";
        } else if (champId == 245) {
            champName = "Ekko";
        } else if (champId == 60) {
            champName = "Elise";
        } else if (champId == 28) {
            champName = "Evelynn";
        } else if (champId == 81) {
            champName = "Ezreal";
        } else if (champId == 9) {
            champName = "Fiddlesticks";
        } else if (champId == 114) {
            champName = "Fiora";
        } else if (champId == 105) {
            champName = "Fizz";
        } else if (champId == 3) {
            champName = "Galio";
        } else if (champId == 41) {
            champName = "Gangplank";
        } else if (champId == 86) {
            champName = "Garen";
        } else if (champId == 150) {
            champName = "Gnar";
        } else if (champId == 79) {
            champName = "Gragas";
        } else if (champId == 104) {
            champName = "Graves";
        } else if (champId == 120) {
            champName = "Hecarim";
        } else if (champId == 74) {
            champName = "Heimerdinger";
        } else if (champId == 420) {
            champName = "Illaoi";
        } else if (champId == 39) {
            champName = "Irelia";
        } else if (champId == 427) {
            champName = "Ivern";
        } else if (champId == 40) {
            champName = "Janna";
        } else if (champId == 59) {
            champName = "JarvanIV";
        } else if (champId == 24) {
            champName = "Jax";
        } else if (champId == 126) {
            champName = "Jayce";
        } else if (champId == 202) {
            champName = "Jhin";
        } else if (champId == 222) {
            champName = "Jinx";
        } else if (champId == 145) {
            champName = "KaiSa";
        } else if (champId == 429) {
            champName = "Kalista";
        } else if (champId == 43) {
            champName = "Karma";
        } else if (champId == 30) {
            champName = "Karthus";
        } else if (champId == 38) {
            champName = "Kassadin";
        } else if (champId == 55) {
            champName = "Katarina";
        } else if (champId == 10) {
            champName = "Kayle";
        } else if (champId == 141) {
            champName = "Kayn";
        } else if (champId == 85) {
            champName = "Kennen";
        } else if (champId == 121) {
            champName = "Khazix";
        } else if (champId == 203) {
            champName = "Kindred";
        } else if (champId == 240) {
            champName = "Kled";
        } else if (champId == 96) {
            champName = "KogMaw";
        } else if (champId == 7) {
            champName = "Leblanc";
        } else if (champId == 64) {
            champName = "LeeSin";
        } else if (champId == 89) {
            champName = "Leona";
        } else if (champId == 127) {
            champName = "Lissandra";
        } else if (champId == 236) {
            champName = "Lucian";
        } else if (champId == 117) {
            champName = "Lulu";
        } else if (champId == 99) {
            champName = "Lux";
        } else if (champId == 54) {
            champName = "Malphite";
        } else if (champId == 90) {
            champName = "Malzahar";
        } else if (champId == 57) {
            champName = "Maokai";
        } else if (champId == 11) {
            champName = "MasterYi";
        } else if (champId == 21) {
            champName = "MissFortune";
        } else if (champId == 62) {
            champName = "MonkeyKing";
        } else if (champId == 82) {
            champName = "Mordekaiser";
        } else if (champId == 25) {
            champName = "Morgana";
        } else if (champId == 267) {
            champName = "Nami";
        } else if (champId == 75) {
            champName = "Nasus";
        } else if (champId == 111) {
            champName = "Nautilus";
        } else if (champId == 518) {
            champName = "Neeko";
        } else if (champId == 76) {
            champName = "Nidalee";
        } else if (champId == 56) {
            champName = "Nocturne";
        } else if (champId == 20) {
            champName = "Nunu";
        } else if (champId == 2) {
            champName = "Olaf";
        } else if (champId == 61) {
            champName = "Orianna";
        } else if (champId == 516) {
            champName = "Ornn";
        } else if (champId == 80) {
            champName = "Pantheon";
        } else if (champId == 78) {
            champName = "Poppy";
        } else if (champId == 555) {
            champName = "Pyke";
        } else if (champId == 246) {
            champName = "Qiyana";
        } else if (champId == 133) {
            champName = "Quinn";
        } else if (champId == 497) {
            champName = "Rakan";
        } else if (champId == 33) {
            champName = "Rammus";
        } else if (champId == 421) {
            champName = "RekSai";
        } else if (champId == 58) {
            champName = "Renekton";
        } else if (champId == 107) {
            champName = "Rengar";
        } else if (champId == 92) {
            champName = "Riven";
        } else if (champId == 68) {
            champName = "Rumble";
        } else if (champId == 13) {
            champName = "Ryze";
        } else if (champId == 113) {
            champName = "Sejuani";
        } else if (champId == 235) {
            champName = "Senna";
        } else if (champId == 875) {
            champName = "Sett";
        } else if (champId == 35) {
            champName = "Shaco";
        } else if (champId == 98) {
            champName = "Shen";
        } else if (champId == 102) {
            champName = "Shyvana";
        } else if (champId == 27) {
            champName = "Singed";
        } else if (champId == 14) {
            champName = "Sion";
        } else if (champId == 15) {
            champName = "Sivir";
        } else if (champId == 72) {
            champName = "Skarner";
        } else if (champId == 37) {
            champName = "Sona";
        } else if (champId == 16) {
            champName = "Soraka";
        } else if (champId == 50) {
            champName = "Swain";
        } else if (champId == 517) {
            champName = "Sylas";
        } else if (champId == 134) {
            champName = "Syndra";
        } else if (champId == 223) {
            champName = "TahmKench";
        } else if (champId == 163) {
            champName = "Taliyah";
        } else if (champId == 91) {
            champName = "Talon";
        } else if (champId == 44) {
            champName = "Taric";
        } else if (champId == 17) {
            champName = "Teemo";
        } else if (champId == 412) {
            champName = "Thresh";
        } else if (champId == 18) {
            champName = "Tristana";
        } else if (champId == 48) {
            champName = "Trundle";
        } else if (champId == 23) {
            champName = "Tryndamere";
        } else if (champId == 4) {
            champName = "TwistedFate";
        } else if (champId == 29) {
            champName = "Twitch";
        } else if (champId == 77) {
            champName = "Udyr";
        } else if (champId == 6) {
            champName = "Urgot";
        } else if (champId == 110) {
            champName = "Varus";
        } else if (champId == 67) {
            champName = "Vayne";
        } else if (champId == 45) {
            champName = "Veigar";
        } else if (champId == 161) {
            champName = "VelKoz";
        } else if (champId == 254) {
            champName = "Vi";
        } else if (champId == 112) {
            champName = "Viktor";
        } else if (champId == 8) {
            champName = "Vladimir";
        } else if (champId == 106) {
            champName = "Volibear";
        } else if (champId == 19) {
            champName = "Warwick";
        } else if (champId == 498) {
            champName = "Xayah";
        } else if (champId == 101) {
            champName = "Xerath";
        } else if (champId == 5) {
            champName = "XinZhao";
        } else if (champId == 157) {
            champName = "Yasuo";
        } else if (champId == 83) {
            champName = "Yorick";
        } else if (champId == 350) {
            champName = "Yuumi";
        } else if (champId == 154) {
            champName = "Zac";
        } else if (champId == 238) {
            champName = "Zed";
        } else if (champId == 115) {
            champName = "Ziggs";
        } else if (champId == 26) {
            champName = "Zilean";
        } else if (champId == 142) {
            champName = "Zoe";
        } else if (champId == 143) {
            champName = "Zyra";
        } else if (champId == 777) {
            champName = "Yone";
        } else if (champId == 145) {
            champName = "Kaisa";
        } else{
            champName = "Garen";
        }

        return champName;
    }
}
