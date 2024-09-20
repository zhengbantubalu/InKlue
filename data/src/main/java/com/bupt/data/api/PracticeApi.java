package com.bupt.data.api;

import android.content.Context;

import com.bupt.data.pojo.HanZi;
import com.bupt.data.pojo.Practice;
import com.bupt.data.service.PracticeService;

import java.util.ArrayList;

public class PracticeApi {

    public static void initPracticeData(Context context) {
        PracticeService.initPracticeData(context);
    }

    public static void initPracticeResource(Context context) {
        PracticeService.initPracticeResource(context);
    }

    public static ArrayList<Practice> getPracticeList(Context context) {
        return PracticeService.getPracticeList(context);
    }

    public static boolean createPractice(Context context, Practice practice) {
        return PracticeService.createPractice(context, practice);
    }

    public static boolean renamePractice(Context context, Practice practice) {
        return PracticeService.renamePractice(context, practice);
    }

    public static boolean addHanZiIntoPractice(Context context, Practice practice, HanZi hanZi) {
        return PracticeService.addHanZiIntoPractice(context, practice, hanZi);
    }

    public static boolean deletePractice(Context context, Practice practice) {
        return PracticeService.deletePractice(context, practice);
    }
}
