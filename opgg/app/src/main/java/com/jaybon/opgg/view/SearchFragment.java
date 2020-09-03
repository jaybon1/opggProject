package com.jaybon.opgg.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jaybon.opgg.R;
import com.jaybon.opgg.databinding.FragmentSearchBinding;

public class SearchFragment extends Fragment {

    private static final String TAG = "SearchFragment";

    private FragmentSearchBinding fragmentSearchBinding;

    private boolean enterKeyDown;
    private boolean enterKeyUp;

    public SearchFragment() {
    }

    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentSearchBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_search,container,false);

        enterKeyDown = false;
        enterKeyUp = false;

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_search, container, false);

        // 엔터로 검색
        fragmentSearchBinding.etSearchInput.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == event.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP && !enterKeyUp) {
                    enterKeyUp = true;
                    // 액티비티 이동
                    moveToNext();
                    return true;
                } else if (keyCode == event.KEYCODE_ENTER) {
                    enterKeyDown = true;
                    return true;
                }
                return false;
            }
        });

        fragmentSearchBinding.etSearchInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterKeyUp = false;
            }
        });

        // 터치로 검색
        fragmentSearchBinding.ivSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                InputMethodManager imm = (InputMethodManager) getSystemService(getContext());

                // 액티비티 이동
                moveToNext();
            }
        });

        // Inflate the layout for this fragment
//        return rootView;
        return fragmentSearchBinding.getRoot();
    }

    private void moveToNext() {
        if (fragmentSearchBinding.etSearchInput.getText().toString() == null || fragmentSearchBinding.etSearchInput.getText().toString().equals("")) {
//            Toast.makeText(getContext(), "소환사 이름을 입력하세요", Toast.LENGTH_SHORT).show();
            Toast toast = Toast.makeText(getContext(), "소환사 이름을 입력하세요", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, -600);
            toast.show();
            return;
        }
        Intent intent = new Intent(getActivity(), InfoActivity.class);
        intent.putExtra("summonerName", fragmentSearchBinding.etSearchInput.getText().toString());
//        // 이전화면을 없애고 새화면을 띄운다
//        intent.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
        fragmentSearchBinding.etSearchInput.setText("");
        startActivity(intent);
    }
}