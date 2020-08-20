package com.jaybon.opgg.adapter;

import android.util.Log;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

// 이미지 바인딩을 위한함수

public class ImageBindAdapter {

    private static final String TAG = "ImageBindAdapter";

    @BindingAdapter({"getImg"})
    public static void getImg(ImageView imageView, String imgURI) {
        Glide.with(imageView.getContext()).load(imgURI).into(imageView);
    }

    @BindingAdapter({"getChampImg"})
    public static void getChampImg(ImageView imageView, String champId) {

        if(champId != null && !champId.equals("") && !champId.equals("null")){
            Glide.with(imageView.getContext()).load("https://ddragon.leagueoflegends.com/cdn/10.6.1/img/champion/Shyvana.png").into(imageView);
        } else {
            Glide.with(imageView.getContext()).load("https://ddragon.leagueoflegends.com/cdn/10.6.1/img/champion/Shyvana.png").into(imageView);
        }
    }

    @BindingAdapter({"getTierIcon"})
    public static void getTierIcon(ImageView imageView, String tierRankId) {

        if(tierRankId != null && !tierRankId.equals("") && !tierRankId.equals("null")){
            Glide.with(imageView.getContext()).load("https://opgg-static.akamaized.net/images/medals/"+tierRankId+".png").into(imageView);
        } else {
            Glide.with(imageView.getContext()).load("https://opgg-static.akamaized.net/images/medals/default.png").into(imageView);
        }
    }

    @BindingAdapter({"getProfile"})
    public static void getProfile(ImageView imageView, String profileId) {
        if(profileId != null && !profileId.equals("") && !profileId.equals("null")){
            Glide.with(imageView.getContext()).load("http://ddragon.leagueoflegends.com/cdn/10.16.1/img/profileicon/"+profileId+".png").into(imageView);
        }
    }

    @BindingAdapter({"getItem"})
    public static void getItem(ImageView imageView, String itemId) {
        if(itemId != null && !itemId.equals("")&& !itemId.equals("null")) {
            Glide.with(imageView.getContext()).load("http://ddragon.leagueoflegends.com/cdn/10.16.1/img/item/" + itemId + ".png").into(imageView);
        }
    }

    @BindingAdapter({"getPerk"})
    public static void getPerk(ImageView imageView, String perkId) {
        if(perkId != null && !perkId.equals("")&& !perkId.equals("null")) {
            Glide.with(imageView.getContext()).load("https://opgg-static.akamaized.net/images/lol/perkStyle/" + perkId + ".png").into(imageView);
        }
    }


    @BindingAdapter({"getSpell"})
    public static void getSpell(ImageView imageView, String spellId) {

        if(spellId == null || spellId.equals("") || spellId.equals("null")){
            return;
        }

        String spellName = null;

        if (spellId.equals("21")){
            spellName = "SummonerBarrier";
        } else if (spellId.equals("1")){
            spellName = "SummonerBoost";
        } else if (spellId.equals("14")){
            spellName = "SummonerDot";
        } else if (spellId.equals("3")){
            spellName = "SummonerExhaust";
        } else if (spellId.equals("4")){
            spellName = "SummonerFlash";
        } else if (spellId.equals("6")){
            spellName = "SummonerHaste";
        } else if (spellId.equals("7")){
            spellName = "SummonerHeal";
        } else if (spellId.equals("13")){
            spellName = "SummonerMana";
        } else if (spellId.equals("30")){
            spellName = "SummonerPoroRecall";
        } else if (spellId.equals("31")){
            spellName = "SummonerPoroThrow";
        } else if (spellId.equals("11")){
            spellName = "SummonerSmite";
        } else if (spellId.equals("39")){
            spellName = "SummonerSnowURFSnowball_Mark";
        } else if (spellId.equals("32")){
            spellName = "SummonerSnowball";
        } else if (spellId.equals("12")){
            spellName = "SummonerTeleport";
        }

        Glide.with(imageView.getContext()).load("http://ddragon.leagueoflegends.com/cdn/10.16.1/img/spell/"+spellName+".png").into(imageView);
    }

}


