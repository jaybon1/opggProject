package com.jaybon.opgg.api;


//xml에 데이터 바인딩 하여 값을 원하는대로 변경
public class XmlSupport {

    public static String win = "승";
    public static String lose = "패";

    public static String getDuration(long duration) {

        long minutes = duration / 60;
        long seconds = duration % 60;

        return minutes+":"+seconds;
    }
}
