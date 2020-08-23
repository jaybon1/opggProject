package com.jaybon.opgg.view.adapter;

import android.graphics.Color;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.jaybon.opgg.R;

// 이미지 바인딩을 위한함수

public class ImageBindAdapter {

    private static final String TAG = "ImageBindAdapter";

    @BindingAdapter({"getImg"})
    public static void getImg(ImageView imageView, String imgURI) {
        Glide.with(imageView.getContext()).load(imgURI).into(imageView);
    }

    @BindingAdapter({"getBackgroundColor"})
    public static void getBackgroundColor(TextView textView, String tier) {

        if(tier != null && !tier.equals("") && !tier.equals("null")){
            if(tier.equals("CHALLENGER")){
                textView.setBackgroundResource(R.color.tierMaster);
            } else if(tier.equals("GRANDMASTER")){
                textView.setBackgroundResource(R.color.tierMaster);
            } else if(tier.equals("MASTER")){
                textView.setBackgroundResource(R.color.tierMaster);
            } else if(tier.equals("DIAMOND")) {
                textView.setBackgroundResource(R.color.tierDiamond);
            } else if(tier.equals("PLATINUM")){
                textView.setBackgroundResource(R.color.tierPlatinum);
            } else if(tier.equals("GOLD")){
                textView.setBackgroundResource(R.color.tierGold);
            } else if(tier.equals("SILVER")){
                textView.setBackgroundResource(R.color.tierSilver);
            } else if(tier.equals("BRONZE")){
                textView.setBackgroundResource(R.color.tierBronze);
            } else {
                textView.setBackgroundResource(R.color.white);
            }
        } else {
            textView.setBackgroundResource(R.color.opGray);
        }
    }

    @BindingAdapter({"getChampImg"})
    public static void getChampImg(ImageView imageView, String champId) {

        if(champId != null && !champId.equals("") && !champId.equals("null")){
            Glide.with(imageView.getContext()).load("https://ddragon.leagueoflegends.com/cdn/10.6.1/img/champion/"+champId+".png").into(imageView);
        } else {
            imageView.setImageResource(R.color.opGray);
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
        } else {
            imageView.setImageResource(R.color.itemBack);
        }
    }

    @BindingAdapter({"getItem"})
    public static void getItem(ImageView imageView, String itemId) {

        if(itemId.equals("0")){
            imageView.setImageResource(R.color.itemBack);
            return;
        } else if(itemId != null && !itemId.equals("")&& !itemId.equals("null")) {
            Glide.with(imageView.getContext()).load("http://ddragon.leagueoflegends.com/cdn/10.16.1/img/item/" + itemId + ".png").into(imageView);
        }
    }

    @BindingAdapter({"getPerk"})
    public static void getPerk(ImageView imageView, String perkId) {

        if(perkId != null && !perkId.equals("")&& !perkId.equals("null")) {
            Glide.with(imageView.getContext()).load("https://opgg-static.akamaized.net/images/lol/perkStyle/" + perkId + ".png").into(imageView);
        } else {
            imageView.setImageResource(R.color.itemBack);
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


