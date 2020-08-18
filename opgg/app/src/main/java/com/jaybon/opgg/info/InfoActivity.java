package com.jaybon.opgg.info;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.icu.text.IDNA;
import android.os.Bundle;
import android.util.Log;

import com.jaybon.opgg.R;
import com.jaybon.opgg.adapter.InfoAdapter;
import com.jaybon.opgg.api.dto.InfoDto;
import com.jaybon.opgg.api.model.Summoner;
import com.jaybon.opgg.network.InfoService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InfoActivity extends AppCompatActivity {

    private static final String TAG = "InfoActivity";

    private InfoAdapter adapter;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        recyclerView = findViewById(R.id.rv_info);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new InfoAdapter();
        recyclerView.setAdapter(adapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://kr.api.riotgames.com/lol/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        InfoService service = retrofit.create(InfoService.class);

        Call<Summoner> call = service.getSummonerByName("포식베이가");
        call.enqueue(new Callback<Summoner>() {
            @Override
            public void onResponse(Call<Summoner> call, Response<Summoner> response) {

                if (!response.isSuccessful()) {
                    Log.d(TAG, "onResponse: 응답 불량");
                    return;
                }

                Summoner summoner = response.body();
            }

            @Override
            public void onFailure(Call<Summoner> call, Throwable t) {

                Log.d(TAG, "onFailure: " + t.getMessage());

            }
        });




//        adapter.addContents();


    }
}