package com.jaybon.opgg.view.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;

import com.jaybon.opgg.R;
import com.jaybon.opgg.databinding.ActivityDetailBinding;
import com.jaybon.opgg.model.dto.DetailDto;
import com.jaybon.opgg.model.dto.RespDto;
import com.jaybon.opgg.viewmodel.detail.DetailViewModel;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = "DetailActivity";

    private long gameId;

    private DetailDto detailDto;

    private DetailViewModel detailViewModel;

    private ActivityDetailBinding activityDetailBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        detailDto = new DetailDto();

        // 데이터 바인딩 연결
        activityDetailBinding = DataBindingUtil.setContentView(this,R.layout.activity_detail);

        // 인텐트에서 데이터 가져오기
        gameId = getIntent().getLongExtra("gameId", 0);

        // 뷰모델생성
        detailViewModel = ViewModelProviders.of(this).get(DetailViewModel.class);

        // 뷰모델 구독
        detailViewModel.subscribe().observe(this, new Observer<RespDto<DetailDto>>() {
            @Override
            public void onChanged(RespDto<DetailDto> respDto) {

                Log.d(TAG, "onChanged: "+respDto);

                activityDetailBinding.setDetailDto(respDto.getData());

                // 로딩화면 없애기
//                activityDetailBinding.pgDetailLoading.setVisibility(View.GONE);
            }
        });

        // 뷰모델 데이터 초기화
        detailViewModel.initLiveData(gameId);


    }
}