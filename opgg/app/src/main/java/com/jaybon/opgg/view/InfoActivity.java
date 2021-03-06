package com.jaybon.opgg.view;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.jaybon.opgg.R;
import com.jaybon.opgg.databinding.ActivityInfoBinding;
import com.jaybon.opgg.model.dto.InfoDto;
import com.jaybon.opgg.model.dto.RespDto;
import com.jaybon.opgg.view.adapter.InfoAdapter;
import com.jaybon.opgg.view.callback.InfoCallback;
import com.jaybon.opgg.viewmodel.InfoViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InfoActivity extends AppCompatActivity implements InfoCallback {

    private static final String TAG = "InfoActivity";

    // 소환사명
    private String summonerName;

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

        summonerName = getIntent().getStringExtra("summonerName");

        // 리스트 초기화
        infoDtos = new ArrayList<>();

        // 바인딩 연결
        activityInfoBinding = DataBindingUtil.setContentView(this,R.layout.activity_info);

        // 뒤로가기 버튼
        activityInfoBinding.ivInfoDetailBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // 리사이클러 뷰 세팅
        activityInfoBinding.rvInfo.setLayoutManager(new LinearLayoutManager(this));

        // 리사이클러뷰 어댑터 세팅 (리스너 넘기기)
        adapter = new InfoAdapter( summonerName,InfoActivity.this);
        activityInfoBinding.rvInfo.setAdapter(adapter);

        // 리사이클러뷰 데이터 초기화
        adapter.addContents(infoDtos);

        // 뷰모델생성
        infoViewModel = ViewModelProviders.of(this).get(InfoViewModel.class);

        // 뷰모델 구독
        infoViewModel.subscribe().observe(this, new Observer<RespDto<List<InfoDto>>>() {
            @Override
            public void onChanged(RespDto<List<InfoDto>> respDto) {



                if(respDto.getStatusCode() == 200){

                    infoDtos = respDto.getData();
                    // 뷰가 변경되면 리사이클러뷰 어댑터에 데이터 새로 담기
                    adapter.addContents(new ArrayList<>(infoDtos));
                    adapter.notifyDataSetChanged();

                    // 로딩화면 없애기
                    activityInfoBinding.pgInfoLoading.setVisibility(View.GONE);

                } else if(respDto.getStatusCode() == 400){

                    Toast toast = Toast.makeText(InfoActivity.this, respDto.getMessage(), Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP, 0, 0);
                    toast.show();

                    // 로딩화면 없애기
                    activityInfoBinding.pgInfoLoading.setVisibility(View.GONE);
                } else{
                    Toast toast = Toast.makeText(InfoActivity.this, respDto.getMessage(), Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP, 0, 0);
                    toast.show();
                    onBackPressed();
                }

            }
        });

        // 뷰모델 데이터 초기화
        infoViewModel.initLiveData(summonerName);

    }

    @Override
    public void updateData() {
        infoViewModel.updateLiveData(summonerName);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0,android.R.anim.slide_out_right);
    }

}