package com.jaybon.opgg.community;

import android.os.Bundle;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jaybon.opgg.R;
import com.jaybon.opgg.adapter.CommunityAdapter;
import com.jaybon.opgg.dao.dto.CommunityDto;
import com.jaybon.opgg.dao.model.Post;

import java.sql.Timestamp;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CommunityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CommunityFragment extends Fragment {

    private static final String TAG = "CommunityFragment";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ImageView ivCommunityToolbar;
    private DrawerLayout drawerLayoutCommunity;
    private RecyclerView rvCommunity;

    public CommunityFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CommunityFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CommunityFragment newInstance(String param1, String param2) {
        CommunityFragment fragment = new CommunityFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_community, container, false);
        ivCommunityToolbar = rootView.findViewById(R.id.iv_community_toolbar);
        drawerLayoutCommunity = rootView.findViewById(R.id.drawer_layout_community);

        ivCommunityToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayoutCommunity.openDrawer(Gravity.RIGHT);
            }
        });

        rvCommunity = rootView.findViewById(R.id.rv_community);

        CommunityAdapter adapter = new CommunityAdapter();

        Post post = Post.builder()
                .like(123)
                .title("테스트 제목입니다.")
                .createDate(Timestamp.valueOf("2020-08-01 23:59:59"))
                .build();

        CommunityDto communityDto = CommunityDto.builder()
                .post(post)
                .nickname("테스트유저")
                .replyCount(21)
                .type(1)
                .build();

        CommunityDto communityDto1 = CommunityDto.builder()
                .page(1)
                .type(0)
                .build();

        adapter.addContent(communityDto);
        adapter.addContent(communityDto);
        adapter.addContent(communityDto);
        adapter.addContent(communityDto);
        adapter.addContent(communityDto);
        adapter.addContent(communityDto);
        adapter.addContent(communityDto);
        adapter.addContent(communityDto);
        adapter.addContent(communityDto);
        adapter.addContent(communityDto);
        adapter.addContent(communityDto);
        adapter.addContent(communityDto1);

        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rvCommunity.setLayoutManager(layoutManager1);
        rvCommunity.setAdapter(adapter);

        // Inflate the layout for this fragment
        return rootView;

    }
}