package com.jaybon.opgg.view.community;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.jaybon.opgg.R;
import com.jaybon.opgg.databinding.CommunityToolbarBinding;
import com.jaybon.opgg.databinding.FragmentCommunityBinding;
import com.jaybon.opgg.model.dto.CommunityDto;
import com.jaybon.opgg.model.dto.RespDto;
import com.jaybon.opgg.view.adapter.CommunityAdapter;
import com.jaybon.opgg.view.adapter.ItemClickCallback;
import com.jaybon.opgg.viewmodel.community.CommunityViewModel;

import java.util.ArrayList;
import java.util.List;

public class CommunityFragment extends Fragment implements ItemClickCallback {

    private static final String TAG = "CommunityFragment";

    // 데이터바인딩
    private FragmentCommunityBinding fragmentCommunityBinding;
    private CommunityToolbarBinding communityToolbarBinding;

    // 리사이클러뷰 어댑터
    private CommunityAdapter adapter;

    // 리사이클러뷰 데이터
    private List<CommunityDto> CommunityDtos;

    // 뷰모델
    private CommunityViewModel communityViewModel;

    // 생성자
    public CommunityFragment() {
    }

    // 미사용
    public static CommunityFragment newInstance(String param1, String param2) {
        CommunityFragment fragment = new CommunityFragment();
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
        CommunityDtos = new ArrayList<>();

        // 액티비티와 프래그먼트는 바인딩 방식이 다름 (확인)
        fragmentCommunityBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_community, container, false);

        // 툴바
        communityToolbarBinding = DataBindingUtil.inflate(inflater, R.layout.community_toolbar, container, false);

        communityToolbarBinding.ivCommunityToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentCommunityBinding.drawerLayoutCommunity.openDrawer(Gravity.RIGHT);
            }
        });

        // 리사이클러뷰 세팅
        fragmentCommunityBinding.rvCommunity.setLayoutManager(new LinearLayoutManager(getActivity()));

        // 사이클러뷰 어댑터 세팅
        adapter = new CommunityAdapter(CommunityFragment.this);
        fragmentCommunityBinding.rvCommunity.setAdapter(adapter);

        // 리사이클러뷰 데이터 초기화
        adapter.addContents(CommunityDtos);

        // 뷰모델 초기화
        communityViewModel = ViewModelProviders.of(this).get(CommunityViewModel.class);

        // 뷰모델 구독
        communityViewModel.subscribe().observe(this, new Observer<RespDto<List<CommunityDto>>>() {

            // 라이브데이터 값이 변경되면 자동실행
            @Override
            public void onChanged(RespDto<List<CommunityDto>> respDto) {

                // dto 상태값 확인
                if (respDto.getStatusCode() == 200) {

                    //리사이클러뷰에 데이터 넣기
                    adapter.addContents(respDto.getData());

                    // 리사이클러뷰 데이터 다시넣을 경우 호출함수
                    adapter.notifyDataSetChanged();

                } else {

                    // dto 메시지 출력
                    Toast.makeText(getContext(), respDto.getMessage(), Toast.LENGTH_SHORT).show();

                    // 로딩 화면 없애기
//                    fragmentCommunityBinding.pgRankLoading.setVisibility(View.GONE);
                }

            }

        });

        // 뷰모델 데이터 초기화
        communityViewModel.initLiveData(1);

        // 둘중아무거나 되는듯?
        return fragmentCommunityBinding.getRoot();
//        return inflater.inflate(R.layout.fragment_community, container, false);
    }

    // 콜백
    @Override
    public void onClick() {

    }

    // 콜백
    @Override
    public void onClick(String value) {

    }
}

//
//    ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_community, container, false);
//        ivCommunityToolbar = rootView.findViewById(R.id.iv_community_toolbar);
//                drawerLayoutCommunity = rootView.findViewById(R.id.drawer_layout_community);
//
//                ivCommunityToolbar.setOnClickListener(new View.OnClickListener() {
//@Override
//public void onClick(View v) {
//        drawerLayoutCommunity.openDrawer(Gravity.RIGHT);
//        }
//        });
//
//        rvCommunity = rootView.findViewById(R.id.rv_community);
//
//        CommunityAdapter adapter = new CommunityAdapter();
//
//        Post post = Post.builder()
//        .like(123)
//        .title("테스트 제목입니다.")
//        .createDate(Timestamp.valueOf("2020-08-01 23:59:59"))
//        .build();
//
//        CommunityDto communityDto = CommunityDto.builder()
//        .post(post)
//        .nickname("테스트유저")
//        .replyCount(21)
//        .type(1)
//        .build();
//
//        CommunityDto communityDto1 = CommunityDto.builder()
//        .page(1)
//        .type(0)
//        .build();
//
//        adapter.addContent(communityDto);
//        adapter.addContent(communityDto);
//        adapter.addContent(communityDto);
//        adapter.addContent(communityDto);
//        adapter.addContent(communityDto);
//        adapter.addContent(communityDto);
//        adapter.addContent(communityDto);
//        adapter.addContent(communityDto);
//        adapter.addContent(communityDto);
//        adapter.addContent(communityDto);
//        adapter.addContent(communityDto);
//        adapter.addContent(communityDto1);
//
//        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
//        rvCommunity.setLayoutManager(layoutManager1);
//        rvCommunity.setAdapter(adapter);