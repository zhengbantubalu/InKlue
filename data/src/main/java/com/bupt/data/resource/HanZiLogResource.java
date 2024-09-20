package com.bupt.data.resource;

import android.content.Context;

import com.bupt.data.pojo.HanZi;
import com.bupt.data.pojo.Practice;
import com.bupt.data.util.DirectoryHelper;

import java.io.File;

public class HanZiLogResource {

    public static boolean movePracticeLogHanZiImg(Context context, Practice practiceLog) {
        String practiceLogHanZiDir = DirectoryHelper.getPracticeLogHanZiDir(context);
        for (HanZi hanZi : practiceLog.hanZiList) {
            File srcFile = new File(hanZi.getWrittenPath());
            hanZi.setWrittenPath(practiceLogHanZiDir + "/" + srcFile.getName());
            File dstFile = new File(hanZi.getWrittenPath());
            if (!srcFile.renameTo(dstFile)) {
                return false;
            }
        }
        for (HanZi hanZi : practiceLog.hanZiList) {
            File srcFile = new File(hanZi.getFeedbackPath());
            hanZi.setFeedbackPath(practiceLogHanZiDir + "/" + srcFile.getName());
            File dstFile = new File(hanZi.getFeedbackPath());
            if (!srcFile.renameTo(dstFile)) {
                return false;
            }
        }
        return true;
    }

    public static boolean deletePracticeLogHanZiImg(Practice practiceLog) {
        for (HanZi hanZi : practiceLog.hanZiList) {
            File writtenImg = new File(hanZi.getWrittenPath());
            if (!writtenImg.delete()) {
                return false;
            }
        }
        for (HanZi hanZi : practiceLog.hanZiList) {
            File feedbackImg = new File(hanZi.getFeedbackPath());
            if (!feedbackImg.delete()) {
                return false;
            }
        }
        return true;
    }
}
