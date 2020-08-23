package com.jaybon.opgg.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jaybon.opgg.R;
import com.jaybon.opgg.view.community.CommunityFragment;
import com.jaybon.opgg.view.rank.RankFragment;
import com.jaybon.opgg.view.search.SearchFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main_Activity";
    private BottomNavigationView bottomNavSearch;
    private long backBtnTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavSearch = findViewById(R.id.bottom_nav_search);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new SearchFragment()).commit();

        bottomNavSearch.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Log.d(TAG, "onNavigationItemSelected: " + item.getItemId());

                switch (item.getItemId()){
                    case R.id.bottom_nav_search_button:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new SearchFragment()).commit();
                        break;
                    case R.id.bottom_nav_community_button:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new CommunityFragment()).commit();
                        break;
                    case R.id.bottom_nav_ranking_button:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new RankFragment()).commit();
                        break;
                }

                return true;
            }
        });

    }

    @Override
    public void onBackPressed() {
        long curTime = System.currentTimeMillis();
        long gapTime = curTime - backBtnTime;

        if(gapTime >= 0 && gapTime <=2000) {
            super.onBackPressed();
        } else {
            backBtnTime = curTime;
            Toast.makeText(this,"한번 더 누르면 종료됩니다.",Toast.LENGTH_SHORT).show();
        }
    }

}