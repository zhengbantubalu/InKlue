package com.bupt.inklue.data.service;

import android.content.Context;
import android.widget.ProgressBar;

import com.bupt.inklue.data.network.HanZiNetwork;
import com.bupt.inklue.data.pojo.HanZi;
import com.bupt.inklue.data.pojo.Practice;
import com.bupt.inklue.data.repository.HanZiRepository;

import org.json.JSONArray;

import java.util.ArrayList;

public class HanZiService {

    public static void initHanZiData(Context context) {
        JSONArray hanZiJSONArray = HanZiNetwork.getHanZiJSONArray(context);
        HanZiRepository.putInitialHanZiData(context, hanZiJSONArray);
    }

    public static void initHanZiResource(Context context, ProgressBar progressBar) {
        HanZiNetwork.downloadHanZiImg(context, progressBar);
    }

    public static ArrayList<HanZi> getPracticeHanZiList(Context context, Practice practice) {
        return HanZiRepository.getPracticeHanZiList(context, practice);
    }

    public static ArrayList<HanZi> getMatchedHanZiList(Context context, HanZi filter) {
        return HanZiRepository.getMatchedHanZiList(context, filter);
    }

    public static ArrayList<HanZi> getHanZiListForDownloadImg(Context context) {
        return HanZiRepository.getHanZiListForDownloadImg(context);
    }
}
