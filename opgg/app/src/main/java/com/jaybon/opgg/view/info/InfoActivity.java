package com.jaybon.opgg.view.info;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.jaybon.opgg.R;
import com.jaybon.opgg.databinding.ActivityInfoBinding;
import com.jaybon.opgg.model.dto.InfoDto;
import com.jaybon.opgg.view.adapter.InfoAdapter;
import com.jaybon.opgg.viewmodel.info.InfoViewModel;

import java.util.ArrayList;
import java.util.List;

public class InfoActivity extends AppCompatActivity {

    private static final String TAG = "InfoActivity";

    // 리사이클러뷰 어댑터
    private InfoAdapter adapter;

    // 리사이클러뷰 데이터
    private List<InfoDto> infoDtos;

    // 뷰모델
    private InfoViewModel infoViewModel;

    // 데이터 바인딩
    private ActivityInfoBinding activityInfoBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        // 리스트 초기화
        infoDtos = new ArrayList<>();

        // 바인딩 연결
        activityInfoBinding = DataBindingUtil.setContentView(this,R.layout.activity_info);

        // 로딩화면 없애기
        activityInfoBinding.pgLoading.setVisibility(View.GONE);

        // 뷰모델생성
        infoViewModel = ViewModelProviders.of(this).get(InfoViewModel.class);

        // 뷰모델 구독
        infoViewModel.subscribe().observe(this, new Observer<List<InfoDto>>() {
            @Override
            public void onChanged(List<InfoDto> infoDtos) {

                Log.d(TAG, "onChanged: "+infoDtos.get(1).getType());

                adapter.addContents(infoDtos);
                adapter.notifyDataSetChanged();
            }
        });

        // 뷰모델 데이터 초기화
        infoViewModel.initLiveData(getIntent().getStringExtra("summonerName"));

        // 뒤로가기 버튼
        activityInfoBinding.ivInfoDetailBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // 리사이클러 뷰 세팅
        activityInfoBinding.rvInfo.setLayoutManager(new LinearLayoutManager(this));

        // 리사이클러뷰 어댑터 세팅
        adapter = new InfoAdapter();
        activityInfoBinding.rvInfo.setAdapter(adapter);

        // 리사이클러뷰 데이터 초기화
        adapter.addContents(infoDtos);

    }
}