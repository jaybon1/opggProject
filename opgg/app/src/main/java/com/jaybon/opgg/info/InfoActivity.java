package com.jaybon.opgg.info;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jaybon.opgg.InfoAsyncTask;
import com.jaybon.opgg.R;
import com.jaybon.opgg.adapter.InfoAdapter;
import com.jaybon.opgg.api.dto.InfoDto;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class InfoActivity extends AppCompatActivity {

    private static final String TAG = "InfoActivity";

    private InfoAdapter adapter;

    private RecyclerView recyclerView;

    private List<InfoDto> infoDtos;

    private ProgressBar pgLoading;

    private ImageView ivInfoDetailBack;


    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        ivInfoDetailBack = findViewById(R.id.iv_info_detail_back);
        ivInfoDetailBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        pgLoading = findViewById(R.id.pg_loading);

        recyclerView = findViewById(R.id.rv_info);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new InfoAdapter();
        recyclerView.setAdapter(adapter);

        infoDtos = new ArrayList<>();
        adapter.addContents(infoDtos);

        InfoAsyncTask asyncTask = new InfoAsyncTask(getIntent().getStringExtra("summonerName"), InfoActivity.this);

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    infoDtos = asyncTask.execute().get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        adapter.addContents(infoDtos);
                        adapter.notifyDataSetChanged();
                        pgLoading.setVisibility(View.GONE);
                    }
                });
            }
        }).start();
    }
}