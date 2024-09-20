package com.bupt.data.resource;

import android.content.Context;
import android.graphics.Bitmap;

import com.bupt.data.pojo.Practice;
import com.bupt.data.util.BitmapHelper;
import com.bupt.data.util.DirectoryHelper;

import java.io.File;

public class PracticeLogResource {

    public static boolean createPracticeLogCover(Context context, Practice practiceLog) {
        String coverPath = DirectoryHelper.generatePracticeLogCoverJPG(context);
        practiceLog.setCoverPath(coverPath);
        Bitmap coverBitmap = BitmapHelper.createCover(practiceLog, false);
        return BitmapHelper.saveBitmap(coverBitmap, coverPath);
    }

    public static boolean deletePracticeLogCoverImg(Practice practiceLog) {
        File cover = new File(practiceLog.getCoverPath());
        return cover.delete();
    }
}
