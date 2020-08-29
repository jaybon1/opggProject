package com.jaybon.opgg.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.jaybon.opgg.R;
import com.jaybon.opgg.databinding.ActivityJoinBinding;
import com.jaybon.opgg.model.dto.JoinDto;
import com.jaybon.opgg.model.dto.RespDto;
import com.jaybon.opgg.model.network.OpggRetrofitHelper;
import com.jaybon.opgg.model.network.OpggService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class JoinActivity extends AppCompatActivity {

    private ActivityJoinBinding activityJoinBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        // 바인딩 연결
        activityJoinBinding = DataBindingUtil.setContentView(this, R.layout.activity_join);

        activityJoinBinding.ivJoinBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        activityJoinBinding.btnJoinSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (activityJoinBinding.etJoinEmail.getText() == null ||
                        activityJoinBinding.etJoinEmail.getText().toString().equals("") ||
                        activityJoinBinding.etJoinNickname.getText() == null ||
                        activityJoinBinding.etJoinNickname.getText().toString().equals("") ||
                        activityJoinBinding.etJoinPassword.getText() == null ||
                        activityJoinBinding.etJoinPassword.getText().toString().equals("")) {

                    Toast.makeText(JoinActivity.this, "값을 입력해주세요", Toast.LENGTH_SHORT).show();

                } else {

                    JoinDto joinDto = JoinDto.builder()
                            .email(activityJoinBinding.etJoinEmail.getText().toString())
                            .nickname(activityJoinBinding.etJoinNickname.getText().toString())
                            .password(activityJoinBinding.etJoinPassword.getText().toString())
                            .build();


                    Retrofit opggRetrofit = OpggRetrofitHelper.getRetrofit();
                    OpggService opggService = opggRetrofit.create(OpggService.class);
                    Call<RespDto<String>> call = opggService.join(joinDto);

                    call.enqueue(new Callback<RespDto<String>>() {


                        @Override
                        public void onResponse(Call<RespDto<String>> call, Response<RespDto<String>> response) {
                            if (response.body().getStatusCode() == 200) {
                                alert("회원가입에 성공 하였습니다.");
                            } else {
                                alert(response.body().getMessage());
                            }
                        }

                        @Override
                        public void onFailure(Call<RespDto<String>> call, Throwable t) {
                            alert("통신에 실패하였습니다.");
                        }
                    });
                }
            }
        });
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, android.R.anim.slide_out_right);

    }

    private void alert(String value){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(value);
        builder.setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if(value.equals("회원가입에 성공 하였습니다.")){
                            finish();
                        }
                    }
                });
        builder.show();
    }
}