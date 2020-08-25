package com.jaybon.opgg.view.rank;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.jaybon.opgg.R;
import com.jaybon.opgg.databinding.FragmentRankBinding;
import com.jaybon.opgg.model.dto.InfoDto;
import com.jaybon.opgg.model.dto.RankingDto;
import com.jaybon.opgg.model.dto.RespDto;
import com.jaybon.opgg.view.adapter.ItemClickCallback;
import com.jaybon.opgg.view.adapter.RankAdapter;
import com.jaybon.opgg.view.detail.DetailActivity;
import com.jaybon.opgg.viewmodel.rank.RankViewModel;

import java.util.ArrayList;
import java.util.List;

public class RankFragment extends Fragment implements ItemClickCallback {

    private static final String TAG = "RankFragment";

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

        // 액티비티와 프래그먼트는 바인딩 방식이 다름 (확인)
        fragmentRankBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_rank,container,false);

        // 리사이클러뷰 세팅
        fragmentRankBinding.rvRank.setLayoutManager(new LinearLayoutManager(getActivity()));

        // 사이클러뷰 어댑터 세팅
        adapter = new RankAdapter(RankFragment.this);
        fragmentRankBinding.rvRank.setAdapter(adapter);

        // 리사이클러뷰 데이터 초기화
        adapter.addContents(rankingDtos);

        // 뷰모델 초기화
        rankViewModel = ViewModelProviders.of(this).get(RankViewModel.class);

        // 뷰모델 구독
        rankViewModel.subscribe().observe(this, new Observer<RespDto<List<RankingDto>>>() {
            @Override
            public void onChanged(RespDto<List<RankingDto>> respDto) {

                if(respDto.getStatusCode() == 200){

                    // 리사이클러뷰에 데이터가 있는지 확인하고 없으면 초기화 있으면 추가
                    if(adapter.getRankingDtos() == null || adapter.getRankingDtos().size() == 0){

                        // 뷰가 변경되면 리사이클러뷰 어댑터에 데이터 새로 담기
                        adapter.addContents(respDto.getData());
                        adapter.notifyDataSetChanged();

                        // 로딩이 다되었으므로 false
                        adapter.setNowLoading(false);

                        // 로딩 화면 없애기
                        fragmentRankBinding.pgRankLoading.setVisibility(View.GONE);

                        // 터치 되도록하기
                        RankFragment.this.getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                    } else if(adapter.getRankingDtos().size() > 1) {

                        // 로딩 아이템 삭제
                        adapter.getRankingDtos().remove(adapter.getRankingDtos().size()-1);
//                        adapter.getRankingDtos().addAll(respDto.getData());

                        // 아이템 추가
                        for (int i = 1; i < respDto.getData().size(); i++){
                            adapter.getRankingDtos().add(respDto.getData().get(i));
                        }
                        adapter.notifyDataSetChanged();
                        adapter.setNowLoading(false);

                    } else{
                        Toast.makeText(getContext(), "더 이상 가져올 목록이 없습니다.", Toast.LENGTH_SHORT).show();
                    }

                } else{
                    Toast.makeText(getContext(), respDto.getMessage(), Toast.LENGTH_SHORT).show();
                    // 로딩 화면 없애기
                    fragmentRankBinding.pgRankLoading.setVisibility(View.GONE);

                    // 화면 안눌러지게 하는 것 해제
                    RankFragment.this.getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                }

            }

        });

        // 뷰모델 데이터 초기화
        rankViewModel.initLiveData(1);


        // 둘중아무거나 되는듯?
        return fragmentRankBinding.getRoot();
//        return inflater.inflate(R.layout.fragment_rank, container, false);
    }

    // 스크롤 내리면 값 추가하기
    @Override
    public void onClick() {

        // 10개씩불러오니 10으로 나누면 몇페이지 인지 알 수 있다
        long count = adapter.getRankingDtos().size() / 10;
        rankViewModel.getLiveDataByPage(count+1);

    }

    // 추가데이터 가져오기
    @Override
    public void onClick(String value) {

        // 터치 막기
        RankFragment.this.getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        // 라이브데이터 다시가져오기
        rankViewModel.getLiveDataByName(value);

        // 로딩화면 띄우기
        fragmentRankBinding.pgRankLoading.setVisibility(View.VISIBLE);
    }
}