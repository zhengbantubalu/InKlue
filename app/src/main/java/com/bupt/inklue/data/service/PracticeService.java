package com.bupt.inklue.data.service;

import android.content.Context;
import android.widget.Toast;

import com.bupt.inklue.R;
import com.bupt.inklue.data.network.PracticeNetwork;
import com.bupt.inklue.data.pojo.HanZi;
import com.bupt.inklue.data.pojo.Practice;
import com.bupt.inklue.data.repository.PracticeRepository;
import com.bupt.inklue.data.resource.PracticeResource;

import org.json.JSONArray;

import java.util.ArrayList;

public class PracticeService {

    public static void initPracticeData(Context context) {
        JSONArray PracticeJSONArray = PracticeNetwork.getPracticeJSONArray(context);
        PracticeRepository.putInitialPracticeData(context, PracticeJSONArray);
    }

    public static void initPracticeResource(Context context) {
        PracticeResource.initPracticesCover(context);
    }

    public static ArrayList<Practice> getPracticeList(Context context) {
        return PracticeRepository.getPracticeList(context);
    }

    public static void createPractice(Context context, Practice practice) {
        PracticeResource.createPracticeCover(context, practice);
        if (PracticeRepository.createPractice(context, practice)) {
            Toast.makeText(context, R.string.create_success, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, R.string.create_error, Toast.LENGTH_SHORT).show();
        }
    }

    public static void renamePractice(Context context, Practice practice) {
        if (PracticeRepository.renamePractice(context, practice)) {
            Toast.makeText(context, R.string.rename_success, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, R.string.rename_error, Toast.LENGTH_SHORT).show();
        }
    }

    public static void addHanZiIntoPractice(Context context, Practice practice, HanZi hanZi) {
        PracticeResource.updatePracticeCover(context, practice, hanZi);
        if (PracticeRepository.addHanZiIntoPractice(context, practice, hanZi)) {
            Toast.makeText(context, R.string.add_success, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, R.string.add_error, Toast.LENGTH_SHORT).show();
        }
    }

    public static void deletePractice(Context context, Practice practice) {
        if (PracticeResource.deletePracticeCover(practice) &&
                PracticeRepository.deletePractice(context, practice)) {
            Toast.makeText(context, R.string.delete_success, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, R.string.delete_error, Toast.LENGTH_SHORT).show();
        }
    }
}
