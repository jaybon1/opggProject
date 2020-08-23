package com.jaybon.opgg.view.rank;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jaybon.opgg.R;
import com.jaybon.opgg.databinding.FragmentRankBinding;
import com.jaybon.opgg.model.dto.RankingDto;
import com.jaybon.opgg.view.MainActivity;
import com.jaybon.opgg.view.adapter.RankAdapter;
import com.jaybon.opgg.viewmodel.rank.RankViewModel;

import java.util.ArrayList;
import java.util.List;

public class RankFragment extends Fragment {

    // 리사이클러뷰 어댑터
    private RankAdapter adapter;

    // 뷰모델
    private RankViewModel rankViewModel;

    // 리사이클러뷰 데이터
    private List<RankingDto> rankingDtos;

    // 데이터 바인딩
    private FragmentRankBinding fragmentRankBinding;

    // 프래그먼트 생성자
    public RankFragment() {
    }

    public static RankFragment newInstance(String param1, String param2) {
        RankFragment fragment = new RankFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // 리스트 초기화
        rankingDtos = new ArrayList<>();

        // 뷰모델 초기화
        rankViewModel = ViewModelProviders.of(this).get(RankViewModel.class);

        // 뷰모델 구독
        rankViewModel.subscribe().observe(this, new Observer<List<RankingDto>>() {
            @Override
            public void onChanged(List<RankingDto> rankingDtos) {
                adapter.addContents(rankingDtos);
                adapter.notifyDataSetChanged();
            }
        });

        // 뷰모델 데이터 초기화
        rankViewModel.initLiveData(1);

        // 액티비티와 프래그먼트는 바인딩 방식이 다름 (확인)
        fragmentRankBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_rank,container,false);

        // 리사이클러뷰 세팅
        fragmentRankBinding.rvRank.setLayoutManager(new LinearLayoutManager(getActivity()));

        // 사이클러뷰 어댑터 세팅
        adapter = new RankAdapter();
        fragmentRankBinding.rvRank.setAdapter(adapter);

        // 리사이클러뷰 데이터 초기화
        adapter.addContents(rankingDtos);






        // 둘중아무거나 되는듯?
        return fragmentRankBinding.getRoot();
//        return inflater.inflate(R.layout.fragment_rank, container, false);
    }
}