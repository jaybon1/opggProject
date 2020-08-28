package com.jaybon.opgg.view.write;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jaybon.opgg.R;
import com.jaybon.opgg.databinding.ActivityWriteBinding;
import com.jaybon.opgg.view.MainActivity;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

public class WriteActivity extends AppCompatActivity {

    private ActivityWriteBinding activityWriteBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        // 바인딩 연결
        activityWriteBinding = DataBindingUtil.setContentView(this,R.layout.activity_write);

        // 뒤로가기 버튼
        activityWriteBinding.ivWriteBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // 작성완료
        activityWriteBinding.tvWriteSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if(false){
//                    // 제목 내용이 없는경우
//                }
//
//                //입력성공시 메인액티비티로 이동
//
//                activityWriteBinding.tvWriteTitle.getText();
//                activityWriteBinding.tvWriteContent.getText();

                Intent intent = new Intent(WriteActivity.this, MainActivity.class);
                intent.putExtra("frag", 1);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0,android.R.anim.slide_out_right);
    }
}