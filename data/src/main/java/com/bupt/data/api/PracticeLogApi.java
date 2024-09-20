package com.bupt.data.api;

import android.content.Context;

import com.bupt.data.pojo.Practice;
import com.bupt.data.service.PracticeLogService;

import java.util.ArrayList;

public class PracticeLogApi {

    public static ArrayList<Practice> getPracticeLogList(Context context) {
        return PracticeLogService.getPracticeLogList(context);
    }

    public static boolean savePracticeLog(Context context, Practice practiceLog) {
        return PracticeLogService.savePracticeLog(context, practiceLog);
    }

    public static boolean deletePracticeLog(Context context, Practice practiceLog) {
        return PracticeLogService.deletePracticeLog(context, practiceLog);
    }
}
