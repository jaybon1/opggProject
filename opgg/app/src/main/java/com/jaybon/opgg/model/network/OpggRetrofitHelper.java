package com.jaybon.opgg.model.network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OpggRetrofitHelper {

    // 레트로핏 연결시간 세팅
    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(2, TimeUnit.MINUTES)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build();

    // 레트로핏 세팅
    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://59.20.79.42:58002/") // 루트주소
            .addConverterFactory(GsonConverterFactory.create()) // 지선
            .client(okHttpClient) // 연결시간 세팅
            .build();

    private OpggRetrofitHelper(){};

    public static Retrofit getRetrofit() {
        return retrofit;
    }
}
