package com.bupt.data.resource;

import android.content.Context;
import android.graphics.Bitmap;

import com.bupt.data.api.HanZiApi;
import com.bupt.data.api.PracticeApi;
import com.bupt.data.pojo.HanZi;
import com.bupt.data.pojo.Practice;
import com.bupt.data.util.BitmapHelper;
import com.bupt.data.util.DirectoryHelper;

import java.io.File;
import java.util.ArrayList;

public class PracticeResource {

    public static void initPracticesCover(Context context) {
        ArrayList<Practice> practiceList = PracticeApi.getPracticeList(context);
        for (int i = 0; i < practiceList.size(); i++) {
            Practice practice = practiceList.get(i);
            practice.hanZiList = HanZiApi.getPracticeHanZiList(context, practice);
            Bitmap coverBitmap = BitmapHelper.createCover(practice, true);
            BitmapHelper.saveBitmap(coverBitmap, practice.getCoverPath());
        }
    }

    public static boolean createPracticeCover(Context context, Practice practice) {
        practice.setCoverPath(DirectoryHelper.generatePracticeCoverJPG(context));
        Bitmap coverBitmap = BitmapHelper.createCover(practice, true);
        return BitmapHelper.saveBitmap(coverBitmap, practice.getCoverPath());
    }

    public static boolean updatePracticeCover(Context context, Practice practice, HanZi hanZi) {
        practice.hanZiList = HanZiApi.getPracticeHanZiList(context, practice);
        practice.hanZiList.add(hanZi);
        Bitmap coverBitmap = BitmapHelper.createCover(practice, true);
        return BitmapHelper.saveBitmap(coverBitmap, practice.getCoverPath());
    }

    public static boolean deletePracticeCover(Practice practice) {
        File cover = new File(practice.getCoverPath());
        return cover.delete();
    }
}
