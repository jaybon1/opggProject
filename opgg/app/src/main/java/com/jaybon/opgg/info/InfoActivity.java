package com.jaybon.opgg.info;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jaybon.opgg.InfoAsyncTask;
import com.jaybon.opgg.R;
import com.jaybon.opgg.adapter.InfoAdapter;
import com.jaybon.opgg.api.dto.InfoDto;
import com.jaybon.opgg.network.Riot;
import com.merakianalytics.orianna.Orianna;
import com.merakianalytics.orianna.types.common.Region;
import com.merakianalytics.orianna.types.core.summoner.Summoner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class InfoActivity extends AppCompatActivity {

    private static final String TAG = "InfoActivity";

    private InfoAdapter adapter;

    private RecyclerView recyclerView;

    private List<InfoDto> infoDtos;

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        recyclerView = findViewById(R.id.rv_info);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new InfoAdapter();
        recyclerView.setAdapter(adapter);


//        private int type;
//        private Summoner summoner;
//        private String queueType;
//        private String gameDate;
//        private long gameCreation;
//        private long gameDuration;
//        private long spell1;
//        private long spell2;
//        private long perk1;
//        private long perk2;
//        private long kills;
//        private long deaths;
//        private long assists;
//        private String killConcerned;
//        private long item1;
//        private long item2;
//        private long item3;
//        private long item4;
//        private long item5;
//        private long item6;
//        private long accessory;

        infoDtos = new ArrayList<>();

        InfoAsyncTask asyncTask = new InfoAsyncTask();

        InfoDto infoDto = null;
        try {
            infoDto = asyncTask.execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        infoDtos.add(infoDto);
        adapter.addContents(infoDtos);

        Log.d(TAG, "onCreate: 노티파이");
    }
}