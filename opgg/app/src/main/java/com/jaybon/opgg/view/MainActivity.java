package com.jaybon.opgg.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.jaybon.opgg.R;
import com.jaybon.opgg.view.callback.DialogCallback;

import static android.view.WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main_Activity";

    // 바텀네비게이션
    private BottomNavigationView bottomNavSearch;
    private NavigationView nav;
    private long backBtnTime = 0;


    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavSearch = findViewById(R.id.bottom_nav_search);

        // frag 엑스트라에 담긴 값으로 포커싱
        switch (getIntent().getIntExtra("frag", 0)){
            case 0:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new SearchFragment()).commit();
                bottomNavSearch.setSelectedItemId(R.id.bottom_nav_search_button);
                break;
            case 1:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                        new CommunityFragment(getIntent().getIntExtra("page", 0), getIntent().getIntExtra("position", 0))).commit();
                bottomNavSearch.setSelectedItemId(R.id.bottom_nav_community_button);
                break;
            case 2:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new RankFragment()).commit();
                bottomNavSearch.setSelectedItemId(R.id.bottom_nav_ranking_button);
                break;
        }

        // 네비게이션 셀렉터
        bottomNavSearch.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.bottom_nav_search_button:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new SearchFragment()).commit();
                        break;
                    case R.id.bottom_nav_community_button:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new CommunityFragment(getIntent().getIntExtra("page", 0), getIntent().getIntExtra("position", 0))).commit();
                        break;
                    case R.id.bottom_nav_ranking_button:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new RankFragment()).commit();
                        break;
                }

                return true;
            }
        });

    }

    // 뒤로가기 두번누르면 종료
    @Override
    public void onBackPressed() {
        long curTime = System.currentTimeMillis();
        long gapTime = curTime - backBtnTime;

        if(gapTime >= 0 && gapTime <=2000) {
            super.onBackPressed();
        } else {
            backBtnTime = curTime;

            Toast toast = Toast.makeText(MainActivity.this, "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 600);
            toast.show();
        }
    }

}