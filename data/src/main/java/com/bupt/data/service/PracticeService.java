package com.bupt.data.service;

import android.content.Context;

import com.bupt.data.network.PracticeNetwork;
import com.bupt.data.pojo.HanZi;
import com.bupt.data.pojo.Practice;
import com.bupt.data.repository.PracticeRepository;
import com.bupt.data.resource.PracticeResource;

import org.json.JSONArray;

import java.util.ArrayList;

public class PracticeService {

    public static void initPracticeData(Context context) {
        JSONArray PracticeJSONArray = PracticeNetwork.getPracticeJSONArray();
        PracticeRepository.putInitialPracticeData(context, PracticeJSONArray);
    }

    public static void initPracticeResource(Context context) {
        PracticeResource.initPracticesCover(context);
    }

    public static ArrayList<Practice> getPracticeList(Context context) {
        return PracticeRepository.getPracticeList(context);
    }

    public static boolean createPractice(Context context, Practice practice) {
        return PracticeResource.createPracticeCover(context, practice) &&
                PracticeRepository.createPractice(context, practice);
    }

    public static boolean renamePractice(Context context, Practice practice) {
        return PracticeRepository.renamePractice(context, practice);
    }

    public static boolean addHanZiIntoPractice(Context context, Practice practice, HanZi hanZi) {
        return PracticeResource.updatePracticeCover(context, practice, hanZi) &&
                PracticeRepository.addHanZiIntoPractice(context, practice, hanZi);
    }

    public static boolean deletePractice(Context context, Practice practice) {
        return PracticeResource.deletePracticeCover(practice) &&
                PracticeRepository.deletePractice(context, practice);
    }
}
