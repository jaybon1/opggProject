package com.jaybon.opgg.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.jaybon.opgg.view.MainActivity;

// 스플래시 (앱 켰을시 보이는 로고)
public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // MainActivity.class 자리에 다음에 넘어갈 액티비티를 넣어주기
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("state", "launch");
        startActivity(intent);
        finish();
    }
}