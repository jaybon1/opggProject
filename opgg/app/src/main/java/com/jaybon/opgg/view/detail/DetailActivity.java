package com.jaybon.opgg.view.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.jaybon.opgg.R;
import com.jaybon.opgg.databinding.ActivityDetailBinding;
import com.jaybon.opgg.model.apidao.MatchSummonerModel;
import com.jaybon.opgg.model.dto.DetailDto;
import com.jaybon.opgg.model.dto.RespDto;
import com.jaybon.opgg.view.adapter.DetailAdapter;
import com.jaybon.opgg.view.adapter.ItemClickCallback;
import com.jaybon.opgg.view.adapter.XmlAdapter;
import com.jaybon.opgg.view.info.InfoActivity;
import com.jaybon.opgg.viewmodel.detail.DetailViewModel;

import java.util.ArrayList;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

public class DetailActivity extends AppCompatActivity implements ItemClickCallback {

    private static final String TAG = "DetailActivity";

    private long gameId;

    private DetailAdapter winAdapter;
    private DetailAdapter loseAdapter;

    private String nowSummoner;

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

        // 리사이클러뷰 세팅
        activityDetailBinding.rvDetailWin.setLayoutManager(new LinearLayoutManager(DetailActivity.this));
        activityDetailBinding.rvDetailLose.setLayoutManager(new LinearLayoutManager(DetailActivity.this));

        // 리사이클러뷰 어댑터 초기화
        winAdapter = new DetailAdapter();
        loseAdapter = new DetailAdapter();
        activityDetailBinding.rvDetailWin.setAdapter(winAdapter);
        activityDetailBinding.rvDetailLose.setAdapter(loseAdapter);

        // 리사이클러뷰 데이터 초기화
        winAdapter.addContents(new ArrayList<>());
        loseAdapter.addContents(new ArrayList<>());

        activityDetailBinding.ivInfoDetailBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // 인텐트에서 데이터 가져오기
        gameId = getIntent().getLongExtra("gameId", 0);
        nowSummoner = getIntent().getStringExtra("nowSummoner");

        // 뷰모델생성
        detailViewModel = ViewModelProviders.of(this).get(DetailViewModel.class);

        // 뷰모델 구독
        detailViewModel.subscribe().observe(this, new Observer<RespDto<DetailDto>>() {
            @Override
            public void onChanged(RespDto<DetailDto> respDto) {

                if(respDto.getStatusCode() == 200){

                    activityDetailBinding.setDetailDto(respDto.getData());

                    boolean nowSummonerWin = false;

                    for (MatchSummonerModel matchSummonerModel : respDto.getData().getWinSummonerModels()){
                        if(matchSummonerModel.getSummonerName().equals(nowSummoner)){
                            nowSummonerWin = true;
                            break;
                        }
                    }

                    if(nowSummonerWin){
                        activityDetailBinding.layoutDetailHeader1.setBackgroundResource(R.color.win);
                        activityDetailBinding.layoutDetailHeader2.setBackgroundResource(R.color.win);
                        activityDetailBinding.tvDetailHeaderWinorlose.setText("승리");

                    }else {
                        activityDetailBinding.layoutDetailHeader1.setBackgroundResource(R.color.lose);
                        activityDetailBinding.layoutDetailHeader2.setBackgroundResource(R.color.lose);
                        activityDetailBinding.tvDetailHeaderWinorlose.setText("패배");
                    }

                    if(respDto.getData().getMatchCommonModel().getQueueId() == 420){
                        activityDetailBinding.tvDetailHeaderQueuetype.setText("솔랭");
                    } else{
                        activityDetailBinding.tvDetailHeaderQueuetype.setText("자유");
                    }

                    activityDetailBinding.tvDetailHeaderCreatedate.setText(XmlAdapter.getCreation(respDto.getData().getMatchCommonModel().getGameCreation()));
                    activityDetailBinding.tvDetailHeaderDuration.setText(XmlAdapter.getDuration(respDto.getData().getMatchCommonModel().getGameDuration()));



                    String winTeamName;
                    String loseTeamName;

                    Log.d(TAG, "onChanged: "+respDto.getData().getWinTeam().getTeamId());
                    Log.d(TAG, "onChanged: "+respDto.getData().getLoseTeam().getTeamId());

                    if(respDto.getData().getWinTeam().getTeamId() == 100){
                        winTeamName = "(레드)";
                        loseTeamName = "(블루)";
                        activityDetailBinding.layoutDetailWinTeamHeader.setBackgroundResource(R.color.redTeam);
                        activityDetailBinding.layoutDetailLoseTeamHeader.setBackgroundResource(R.color.blueTeam);

                        activityDetailBinding.ivDetailWinKnife.setImageResource(R.drawable.red_knife);
                        activityDetailBinding.ivDetailWinTeamBaron.setImageResource(R.drawable.red_baron);
                        activityDetailBinding.ivDetailWinTeamDragon.setImageResource(R.drawable.red_dragon);
                        activityDetailBinding.ivDetailWinTeamTower.setImageResource(R.drawable.red_tower);

                        activityDetailBinding.ivDetailLoseKnife.setImageResource(R.drawable.blue_knife);
                        activityDetailBinding.ivDetailLoseTeamBaron.setImageResource(R.drawable.blue_baron);
                        activityDetailBinding.ivDetailLoseTeamDragon.setImageResource(R.drawable.blue_dragon);
                        activityDetailBinding.ivDetailLoseTeamTower.setImageResource(R.drawable.blue_tower);

                    } else{
                        winTeamName = "(블루)";
                        loseTeamName = "(레드)";
                        activityDetailBinding.layoutDetailWinTeamHeader.setBackgroundResource(R.color.blueTeam);
                        activityDetailBinding.layoutDetailLoseTeamHeader.setBackgroundResource(R.color.redTeam);

                        activityDetailBinding.ivDetailWinKnife.setImageResource(R.drawable.blue_knife);
                        activityDetailBinding.ivDetailWinTeamBaron.setImageResource(R.drawable.blue_baron);
                        activityDetailBinding.ivDetailWinTeamDragon.setImageResource(R.drawable.blue_dragon);
                        activityDetailBinding.ivDetailWinTeamTower.setImageResource(R.drawable.blue_tower);

                        activityDetailBinding.ivDetailLoseKnife.setImageResource(R.drawable.red_knife);
                        activityDetailBinding.ivDetailLoseTeamBaron.setImageResource(R.drawable.red_baron);
                        activityDetailBinding.ivDetailLoseTeamDragon.setImageResource(R.drawable.red_dragon);
                        activityDetailBinding.ivDetailLoseTeamTower.setImageResource(R.drawable.red_tower);

                    }

                    activityDetailBinding.tvDetailWinTeamName.setText(winTeamName);
                    activityDetailBinding.tvDetailLoseTeamName.setText(loseTeamName);

                    long winTeamKill = 0;
                    long winTeamDeath = 0;
                    long winTeamAssist = 0;

                    for(MatchSummonerModel matchSummonerModel :respDto.getData().getWinSummonerModels()){
                        winTeamKill = winTeamKill + matchSummonerModel.getKills();
                        winTeamDeath = winTeamDeath + matchSummonerModel.getDeaths();
                        winTeamAssist = winTeamAssist + matchSummonerModel.getAssists();
                    }

                    activityDetailBinding.tvDetailWinTeamKill.setText(winTeamKill+"");
                    activityDetailBinding.tvDetailWinTeamDeath.setText(winTeamDeath+"");
                    activityDetailBinding.tvDetailWinTeamAssist.setText(winTeamAssist+"");

                    long loseTeamKill = 0;
                    long loseTeamDeath = 0;
                    long loseTeamAssist = 0;

                    for(MatchSummonerModel matchSummonerModel :respDto.getData().getLoseSummonerModels()){
                        loseTeamKill = loseTeamKill + matchSummonerModel.getKills();
                        loseTeamDeath = loseTeamDeath + matchSummonerModel.getDeaths();
                        loseTeamAssist = loseTeamAssist + matchSummonerModel.getAssists();
                    }

                    activityDetailBinding.tvDetailLoseTeamKill.setText(loseTeamKill+"");
                    activityDetailBinding.tvDetailLoseTeamDeath.setText(loseTeamDeath+"");
                    activityDetailBinding.tvDetailLoseTeamAssist.setText(loseTeamAssist+"");

                    long maxDeal = 0;

                    for (MatchSummonerModel matchSummonerModel: respDto.getData().getWinSummonerModels()) {
                        if(matchSummonerModel.getTotalDamageDealtToChampions() > maxDeal){
                            maxDeal = matchSummonerModel.getTotalDamageDealtToChampions();
                        }
                    }
                    for (MatchSummonerModel matchSummonerModel: respDto.getData().getLoseSummonerModels()) {
                        if(matchSummonerModel.getTotalDamageDealtToChampions() > maxDeal){
                            maxDeal = matchSummonerModel.getTotalDamageDealtToChampions();
                        }
                    }

                    // 리사이클러뷰 어댑터 재배정
                    winAdapter = new DetailAdapter(respDto.getData().getMatchCommonModel(),maxDeal, nowSummoner, DetailActivity.this);
                    loseAdapter = new DetailAdapter(respDto.getData().getMatchCommonModel(),maxDeal, nowSummoner, DetailActivity.this);

                    activityDetailBinding.rvDetailWin.setAdapter(winAdapter);
                    activityDetailBinding.rvDetailLose.setAdapter(loseAdapter);

                    // 리사이클러뷰 데이터 재배정
                    winAdapter.addContents(respDto.getData().getWinSummonerModels());
                    loseAdapter.addContents(respDto.getData().getLoseSummonerModels());
                    winAdapter.notifyDataSetChanged();
                    loseAdapter.notifyDataSetChanged();

                    // 로딩화면 없애기
                    activityDetailBinding.pgDetailLoading.setVisibility(View.GONE);

                } else{

                    Toast.makeText(DetailActivity.this, respDto.getMessage(), Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }

            }
        });

        // 뷰모델 데이터 초기화
        detailViewModel.initLiveData(gameId);


    }

    @Override
    public void onClick() {

    }

    @Override
    public void onClick(String value) {
        Intent intent = new Intent(DetailActivity.this, InfoActivity.class);
        intent.putExtra("summonerName", value);
        // 이전화면을 없애고 새화면을 띄운다
        intent.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}