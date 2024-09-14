package com.bupt.inklue.data.api;

import android.content.Context;

import com.bupt.inklue.data.pojo.HanZi;
import com.bupt.inklue.data.pojo.Practice;
import com.bupt.inklue.data.service.PracticeService;

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

    public static void createPractice(Context context, Practice practice) {
        PracticeService.createPractice(context, practice);
    }

    public static void renamePractice(Context context, Practice practice) {
        PracticeService.renamePractice(context, practice);
    }

    public static void addHanZiIntoPractice(Context context, Practice practice, HanZi hanZi) {
        PracticeService.addHanZiIntoPractice(context, practice, hanZi);
    }

    public static void deletePractice(Context context, Practice practice) {
        PracticeService.deletePractice(context, practice);
    }
}
