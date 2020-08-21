package com.jaybon.opgg.search;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jaybon.opgg.R;
import com.jaybon.opgg.info.InfoActivity;

import org.w3c.dom.Text;

public class SearchFragment extends Fragment {

    private static final String TAG = "SearchFragment";

    private ImageView ivSearchButton;

    private TextView etSearchInput;

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

        enterKeyDown = false;
        enterKeyUp = false;

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_search, container, false);

        etSearchInput = rootView.findViewById(R.id.et_search_input);

        // 엔터로 검색
        etSearchInput.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.d(TAG, "onKey: " + keyCode);
                if (keyCode == event.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP && !enterKeyUp) {
                    Log.d(TAG, "onKey: 엔터키업");
                    enterKeyUp = true;
                    // 액티비티 이동
                    moveToNext();
                    return true;
                } else if (keyCode == event.KEYCODE_ENTER) {
                    Log.d(TAG, "onKey: 엔터키다운");
                    enterKeyDown = true;
                    return true;
                }
                return false;
            }
        });

        etSearchInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterKeyUp = false;
            }
        });

        ivSearchButton = rootView.findViewById(R.id.iv_search_button);
        // 터치로 검색
        ivSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 액티비티 이동
                moveToNext();
            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }

    private void moveToNext() {
        if (etSearchInput.getText().toString() == null || etSearchInput.getText().toString().equals("")) {
//            Toast.makeText(getContext(), "소환사 이름을 입력하세요", Toast.LENGTH_SHORT).show();
            Toast toast = Toast.makeText(getContext(), "소환사 이름을 입력하세요", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, -600);
            toast.show();
            return;
        }
        Intent intent = new Intent(getActivity(), InfoActivity.class);
        intent.putExtra("summonerName", etSearchInput.getText().toString());
        startActivity(intent);
    }
}