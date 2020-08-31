package com.jaybon.opgg.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.jaybon.opgg.R;
import com.jaybon.opgg.databinding.ActivityWriteBinding;
import com.jaybon.opgg.model.dao.Post;
import com.jaybon.opgg.model.dao.User;
import com.jaybon.opgg.model.dto.CommunityDto;
import com.jaybon.opgg.model.dto.RankingDto;
import com.jaybon.opgg.model.dto.RespDto;
import com.jaybon.opgg.model.network.OpggRetrofitHelper;
import com.jaybon.opgg.model.network.OpggService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

public class WriteActivity extends AppCompatActivity {

    private static final String TAG = "WriteActivity";

    private ActivityWriteBinding activityWriteBinding;

    private String writeType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        // 바인딩 연결
        activityWriteBinding = DataBindingUtil.setContentView(this,R.layout.activity_write);

        // 글 수정인지 확인
        if(getIntent().getIntExtra("postId",0) != 0){
            writeType = "수정";
            activityWriteBinding.tvWriteTitle.setText(getIntent().getStringExtra("title"));
            activityWriteBinding.tvWriteContent.setText(getIntent().getStringExtra("content"));
        } else {
            writeType = "작성";
        }




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

                // 제목 내용이 없는경우
                if(activityWriteBinding.tvWriteTitle.getText() == null ||
                        activityWriteBinding.tvWriteTitle.getText().toString().equals("")){

                    AlertDialog.Builder builder = new AlertDialog.Builder(WriteActivity.this);
                    builder.setMessage("제목을 작성해주세요.");
                    builder.show();

                } else if(activityWriteBinding.tvWriteContent.getText() == null ||
                        activityWriteBinding.tvWriteContent.getText().toString().equals("")){

                    AlertDialog.Builder builder = new AlertDialog.Builder(WriteActivity.this);
                    builder.setMessage("내용을 작성해주세요.");
                    builder.show();

                } else {

                    if(writeType.equals("작성")){

                        SharedPreferences sharedPreferences = getSharedPreferences("com.jaybon.opgg.jwt", MODE_PRIVATE);    // test 이름의 기본모드 설정, 만약 test key값이 있다면 해당 값을 불러옴.
                        String jwtToken = sharedPreferences.getString("jwtToken","");

                        Post post = Post.builder()
                                .title(activityWriteBinding.tvWriteTitle.getText().toString())
                                .content(activityWriteBinding.tvWriteContent.getText().toString())
                                .build();

                        writePost(post, jwtToken);

                    } else if(writeType.equals("수정")){

                        SharedPreferences sharedPreferences = getSharedPreferences("com.jaybon.opgg.jwt", MODE_PRIVATE);    // test 이름의 기본모드 설정, 만약 test key값이 있다면 해당 값을 불러옴.
                        String jwtToken = sharedPreferences.getString("jwtToken","");

                        Post post = Post.builder()
                                .id(getIntent().getIntExtra("postId",0))
                                .title(activityWriteBinding.tvWriteTitle.getText().toString())
                                .content(activityWriteBinding.tvWriteContent.getText().toString())
                                .build();

                        updatePost(post, jwtToken);

                    }
                    activityWriteBinding.pgWriteLoading.setVisibility(View.VISIBLE);



                }


            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0,android.R.anim.slide_out_right);
    }

    public void writePost(Post post, String jwtToken){

        // 레트로핏 비동기
        Retrofit opggRetrofit = OpggRetrofitHelper.getRetrofit();
        OpggService opggService = opggRetrofit.create(OpggService.class);
        Call<RespDto<String>> call = opggService.writePost(post, "Bearer "+jwtToken);

        call.enqueue(new Callback<RespDto<String>>() {
            @Override
            public void onResponse(Call<RespDto<String>> call, Response<RespDto<String>> response) {

                if (!response.isSuccessful()) {
                    Log.d(TAG, "onResponse: "+response.code());
                    return;
                }

                if(response.body().getStatusCode() == 200){

                    AlertDialog.Builder builder = new AlertDialog.Builder(WriteActivity.this);
                    builder.setMessage("글 "+writeType+"에 성공하였습니다.");
                    builder.setPositiveButton("확인",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    if(writeType.equals("작성")){
                                        Intent intent = new Intent(WriteActivity.this, MainActivity.class);
                                        intent.putExtra("frag", 1);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                    } else if(writeType.equals("수정")){
                                        // 리사이클러뷰에서 액티비티 전환하기
                                        Intent intent = new Intent(WriteActivity.this, CommunityDetailActivity.class);
                                        intent.putExtra("postId", getIntent().getIntExtra("postId", 0));
                                        intent.putExtra("page", getIntent().getIntExtra("page", 0));
                                        intent.putExtra("position", getIntent().getIntExtra("position", 0));
                                        // 이전화면을 없애고 새화면을 띄운다
                                        intent.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                    }

                                }
                            });
                    builder.show();

                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(WriteActivity.this);
                    builder.setMessage(response.body().getMessage());
                    builder.show();
                }
            }

            @Override
            public void onFailure(Call<RespDto<String>> call, Throwable t) {
                Log.d(TAG, "onFailure: 통신에 실패하였읍니다.");
            }
        });

    }

    public void updatePost(Post post, String jwtToken){

        // 레트로핏 비동기
        Retrofit opggRetrofit = OpggRetrofitHelper.getRetrofit();
        OpggService opggService = opggRetrofit.create(OpggService.class);
        Call<RespDto<String>> call = opggService.updatePost(post, "Bearer "+jwtToken);

        call.enqueue(new Callback<RespDto<String>>() {
            @Override
            public void onResponse(Call<RespDto<String>> call, Response<RespDto<String>> response) {

                if (!response.isSuccessful()) {
                    Log.d(TAG, "onResponse: "+response.code());
                    return;
                }

                if(response.body().getStatusCode() == 200){

                    AlertDialog.Builder builder = new AlertDialog.Builder(WriteActivity.this);
                    builder.setMessage("글 "+writeType+"에 성공하였습니다.");
                    builder.setPositiveButton("확인",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    if(writeType.equals("작성")){
                                        Intent intent = new Intent(WriteActivity.this, MainActivity.class);
                                        intent.putExtra("frag", 1);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                        startActivity(intent);
                                    } else if(writeType.equals("수정")){
                                        // 리사이클러뷰에서 액티비티 전환하기
                                        Intent intent = new Intent(WriteActivity.this, CommunityDetailActivity.class);
                                        intent.putExtra("postId", getIntent().getIntExtra("postId", 0));
                                        intent.putExtra("page", getIntent().getIntExtra("page", 0));
                                        intent.putExtra("position", getIntent().getIntExtra("position", 0));
                                        // 이전화면을 없애고 새화면을 띄운다
                                        intent.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                    }

                                }
                            });
                    builder.show();

                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(WriteActivity.this);
                    builder.setMessage(response.body().getMessage());
                    builder.show();
                }
            }

            @Override
            public void onFailure(Call<RespDto<String>> call, Throwable t) {
                Log.d(TAG, "onFailure: 통신에 실패하였읍니다.");
            }
        });

    }
}