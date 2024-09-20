package com.bupt.data.service;

import android.content.Context;

import com.bupt.data.pojo.HanZi;
import com.bupt.data.pojo.Practice;
import com.bupt.data.repository.HanZiLogRepository;
import com.bupt.data.resource.HanZiLogResource;

import java.util.ArrayList;

public class HanZiLogService {

    public static ArrayList<HanZi> getPracticeLogHanZiList(Context context, Practice practice) {
        return HanZiLogRepository.getPracticeLogHanZiList(context, practice);
    }

    public static boolean savePracticeLogHanZi(Context context, Practice practiceLog) {
        return HanZiLogResource.movePracticeLogHanZiImg(context, practiceLog) &&
                HanZiLogRepository.savePracticeLogHanZi(context, practiceLog);
    }

    public static boolean deletePracticeLogHanZi(Context context, Practice practiceLog) {
        return HanZiLogResource.deletePracticeLogHanZiImg(practiceLog) &&
                HanZiLogRepository.deletePracticeLogHanZi(context, practiceLog);
    }
}
