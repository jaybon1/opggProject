package com.jaybon.opgg.view.community;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;
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
import com.jaybon.opgg.databinding.FragmentCommunityBinding;
import com.jaybon.opgg.model.dto.CommunityDto;
import com.jaybon.opgg.model.dto.RespDto;
import com.jaybon.opgg.view.adapter.CommunityAdapter;
import com.jaybon.opgg.view.adapter.ItemClickCallback;
import com.jaybon.opgg.view.info.InfoActivity;
import com.jaybon.opgg.view.write.WriteActivity;
import com.jaybon.opgg.viewmodel.community.CommunityViewModel;

import java.util.ArrayList;
import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

public class CommunityFragment extends Fragment implements ItemClickCallback {

    private static final String TAG = "CommunityFragment";

    // 데이터바인딩
    private FragmentCommunityBinding fragmentCommunityBinding;

    // 리사이클러뷰 어댑터
    private CommunityAdapter adapter;

    // 리사이클러뷰 데이터
    private List<CommunityDto> CommunityDtos;

    // 뷰모델
    private CommunityViewModel communityViewModel;

    // 페이지
    private int page;

    // 생성자
    public CommunityFragment() {
    }

    public CommunityFragment(int page) {
        this.page = page;
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

        // 서치버튼
        fragmentCommunityBinding.btnCommunitySearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // 글쓰기버튼
        fragmentCommunityBinding.btnCommunityWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WriteActivity.class);
                intent.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        // 툴바
        fragmentCommunityBinding.ivCommunityToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentCommunityBinding.drawerLayoutCommunity.openDrawer(Gravity.RIGHT);
            }
        });

        // 리사이클러뷰 세팅
        fragmentCommunityBinding.rvCommunity.setLayoutManager(new LinearLayoutManager(getActivity()));

        // 사이클러뷰 어댑터 세팅
        adapter = new CommunityAdapter(CommunityFragment.this, 0);
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

                CommunityDtos.clear();

//                Log.d(TAG, "onChanged: "+respDto.getData().get(0).getPost());

                if(respDto.getStatusCode() == 201){ // 첫페이지 일경우

                    adapter.setButtonType(0);

                    //리사이클러뷰에 데이터 넣기
                    adapter.addContents(respDto.getData());

                    // 리사이클러뷰 데이터 다시넣을 경우 호출함수
                    adapter.notifyDataSetChanged();

                } else if(respDto.getStatusCode() == 200){ // 0페이지나 끝페이지가 아닐경우

                    adapter.setButtonType(1);

                    //리사이클러뷰에 데이터 넣기
                    adapter.addContents(respDto.getData());

                    // 리사이클러뷰 데이터 다시넣을 경우 호출함수
                    adapter.notifyDataSetChanged();

                } else if(respDto.getStatusCode() == 204){ // 끝페이지 일경우

                    adapter.setButtonType(2);

                    //리사이클러뷰에 데이터넣기
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
        communityViewModel.initLiveData(page);

        // 둘중아무거나 되는듯?
        return fragmentCommunityBinding.getRoot();
//        return inflater.inflate(R.layout.fragment_community, container, false);
    }

    @Override
    public void onClick() {

    }

    @Override
    public void onClick(String value) {

    }

    @Override
    public void sendReply(int postId, String value) {

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