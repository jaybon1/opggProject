package com.jaybon.opgg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main_Activity";
    private BottomNavigationView bottomNavSearch;

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
                    case R.id.bottom_nav_ranking_button:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new RankFragment()).commit();
                        break;
                }

                return true;
            }
        });

    }
}