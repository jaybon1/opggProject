package com.jaybon.opgg.info;

import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jaybon.opgg.InfoAsyncTask;
import com.jaybon.opgg.R;
import com.jaybon.opgg.adapter.InfoAdapter;
import com.jaybon.opgg.api.dto.InfoDto;

import java.util.ArrayList;
import java.util.List;

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


        infoDtos = new ArrayList<>();

        InfoAsyncTask asyncTask = new InfoAsyncTask("hide on bush");

        InfoDto infoDto = null;
        try {
            infoDto = asyncTask.execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }


        adapter.addContents(infoDtos);

    }
}