package com.jaybon.opgg.adapter;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.jaybon.opgg.api.model.Summoner;

// 이미지 바인딩을 위한함수

public class ImageBindAdapter {

    @BindingAdapter({"getImg"})
    public static void getImg(ImageView imageView, String imgURI) {
        Glide.with(imageView.getContext()).load(imgURI).into(imageView);
    }

    @BindingAdapter({"getProfile"})
    public static void getProfile(ImageView imageView, long profileId) {
        Glide.with(imageView.getContext()).load("http://ddragon.leagueoflegends.com/cdn/10.16.1/img/profileicon/"+profileId+".png").into(imageView);
    }

    @BindingAdapter({"getItem"})
    public static void getItem(ImageView imageView, long itemId) {
        Glide.with(imageView.getContext()).load("http://ddragon.leagueoflegends.com/cdn/10.16.1/img/item/"+itemId+".png").into(imageView);
    }

    @BindingAdapter({"getPerk"})
    public static void getPerk(ImageView imageView, long perkId) {
        Glide.with(imageView.getContext()).load("https://opgg-static.akamaized.net/images/lol/perkStyle/"+perkId+".png").into(imageView);
    }


    @BindingAdapter({"getSpell"})
    public static void getSpell(ImageView imageView, long spellId) {

        String spellName = null;

        if (spellId == 21){
            spellName = "SummonerBarrier";
        } else if (spellId == 1){
            spellName = "SummonerBoost";
        } else if (spellId == 14){
            spellName = "SummonerDot";
        } else if (spellId == 3){
            spellName = "SummonerExhaust";
        } else if (spellId == 4){
            spellName = "SummonerFlash";
        } else if (spellId == 6){
            spellName = "SummonerHaste";
        } else if (spellId == 7){
            spellName = "SummonerHeal";
        } else if (spellId == 13){
            spellName = "SummonerMana";
        } else if (spellId == 30){
            spellName = "SummonerPoroRecall";
        } else if (spellId == 31){
            spellName = "SummonerPoroThrow";
        } else if (spellId == 11){
            spellName = "SummonerSmite";
        } else if (spellId == 39){
            spellName = "SummonerSnowURFSnowball_Mark";
        } else if (spellId == 32){
            spellName = "SummonerSnowball";
        } else if (spellId == 12){
            spellName = "SummonerTeleport";
        }

        Glide.with(imageView.getContext()).load("http://ddragon.leagueoflegends.com/cdn/10.16.1/img/spell/"+spellName+".png").into(imageView);
    }

}


