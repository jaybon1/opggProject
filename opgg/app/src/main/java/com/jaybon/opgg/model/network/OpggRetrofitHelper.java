package com.jaybon.opgg.model.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OpggRetrofitHelper {

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://59.20.79.42:58002/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private OpggRetrofitHelper(){};

    public static Retrofit getRetrofit() {
        return retrofit;
    }
}
