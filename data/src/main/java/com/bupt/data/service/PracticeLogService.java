package com.bupt.data.service;

import android.content.Context;

import com.bupt.data.pojo.Practice;
import com.bupt.data.repository.PracticeLogRepository;
import com.bupt.data.resource.PracticeLogResource;

import java.util.ArrayList;

public class PracticeLogService {

    public static ArrayList<Practice> getPracticeLogList(Context context) {
        return PracticeLogRepository.getPracticeLogList(context);
    }

    public static boolean savePracticeLog(Context context, Practice practiceLog) {
        return HanZiLogService.savePracticeLogHanZi(context, practiceLog) &&
                PracticeLogResource.createPracticeLogCover(context, practiceLog) &&
                PracticeLogRepository.savePracticeLog(context, practiceLog);
    }

    public static boolean deletePracticeLog(Context context, Practice practiceLog) {
        return HanZiLogService.deletePracticeLogHanZi(context, practiceLog) &&
                PracticeLogResource.deletePracticeLogCoverImg(practiceLog) &&
                PracticeLogRepository.deletePracticeLog(context, practiceLog);
    }
}
