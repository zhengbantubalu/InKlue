package com.bupt.inklue.data.api;

import android.content.Context;

import com.bupt.inklue.data.pojo.Practice;
import com.bupt.inklue.data.service.PracticeLogService;

import java.util.ArrayList;

public class PracticeLogApi {

    public static ArrayList<Practice> getPracticeLogList(Context context) {
        return PracticeLogService.getPracticeLogList(context);
    }

    public static void savePracticeLog(Context context, Practice practiceLog) {
        PracticeLogService.savePracticeLog(context, practiceLog);
    }

    public static void deletePracticeLog(Context context, Practice practiceLog) {
        PracticeLogService.deletePracticeLog(context, practiceLog);
    }
}
