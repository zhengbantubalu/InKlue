package com.bupt.inklue.data.resource;

import android.content.Context;

import com.bupt.inklue.data.pojo.Practice;
import com.bupt.inklue.util.DirectoryHelper;

import java.io.File;

public class PracticeLogResource {

    public static boolean movePracticeLogCoverImg(Context context, Practice practice) {
        File coverSrcFile = new File(practice.getCoverPath());
        practice.setCoverPath(DirectoryHelper.getPracticeLogCoverDir(context) +
                "/" + coverSrcFile.getName());
        File coverDstFile = new File(practice.getCoverPath());
        return coverSrcFile.renameTo(coverDstFile);
    }

    public static boolean deletePracticeLogCoverImg(Practice practiceLog) {
        File cover = new File(practiceLog.getCoverPath());
        return cover.delete();
    }
}
