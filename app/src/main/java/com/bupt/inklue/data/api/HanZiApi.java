package com.bupt.inklue.data.api;

import android.content.Context;
import android.widget.ProgressBar;

import com.bupt.inklue.data.pojo.HanZi;
import com.bupt.inklue.data.pojo.Practice;
import com.bupt.inklue.data.service.HanZiService;

import java.util.ArrayList;

public class HanZiApi {

    public static void initHanZiData(Context context) {
        HanZiService.initHanZiData(context);
    }

    public static void initHanZiResource(Context context, ProgressBar progressBar) {
        HanZiService.initHanZiResource(context, progressBar);
    }

    public static ArrayList<HanZi> getPracticeHanZiList(Context context, Practice practice) {
        return HanZiService.getPracticeHanZiList(context, practice);
    }

    public static ArrayList<HanZi> getMatchedHanZiList(Context context, HanZi filter) {
        return HanZiService.getMatchedHanZiList(context, filter);
    }

    public static ArrayList<HanZi> getHanZiListForDownloadImg(Context context) {
        return HanZiService.getHanZiListForDownloadImg(context);
    }
}
