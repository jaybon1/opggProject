//package com.jaybon.opgg.model.network;
//
//import android.content.Context;
//import android.os.AsyncTask;
//import android.util.Log;
//import android.widget.Toast;
//
//import com.google.gson.Gson;
//import com.jaybon.opgg.model.apidao.apimodel.ApiEntry;
//import com.jaybon.opgg.model.apidao.apimodel.ApiMatch;
//import com.jaybon.opgg.model.apidao.apimodel.ApiMatchEntry;
//import com.jaybon.opgg.model.apidao.apimodel.ApiSummoner;
//import com.jaybon.opgg.model.apidao.apimodel.attr.match.Participant;
//import com.jaybon.opgg.model.apidao.apimodel.attr.match.ParticipantIdentity;
//import com.jaybon.opgg.model.apidao.apimodel.attr.match.Player;
//import com.jaybon.opgg.model.dto.InfoDto;
//import com.jaybon.opgg.model.apidao.EntryModel;
//import com.jaybon.opgg.model.apidao.MatchSummonerModel;
//import com.jaybon.opgg.model.apidao.SummonerModel;
//import com.jaybon.opgg.model.dto.RespDto;
//import com.jaybon.opgg.model.network.RiotService;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
//public class InfoAsyncTask extends AsyncTask<String, List<InfoDto>, List<InfoDto>> {
//
//    private static final String TAG = "InfoAsyncTask";
//
//    String name;
//    Context context;
//
//    public InfoAsyncTask(String name, Context context) {
//        this.name = name;
//        this.context = context;
//    }
//
//    @Override
//    protected void onProgressUpdate(List<InfoDto>... values) {
//        super.onProgressUpdate(values);
//        Toast.makeText(context, "로딩중", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    protected List<InfoDto> doInBackground(String... strings) {
//
//        return null;
//    }
//}
