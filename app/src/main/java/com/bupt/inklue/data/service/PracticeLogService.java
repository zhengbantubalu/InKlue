package com.bupt.inklue.data.service;

import android.content.Context;
import android.widget.Toast;

import com.bupt.inklue.R;
import com.bupt.inklue.data.pojo.Practice;
import com.bupt.inklue.data.repository.PracticeLogRepository;
import com.bupt.inklue.data.resource.PracticeLogResource;

import java.util.ArrayList;

public class PracticeLogService {

    public static ArrayList<Practice> getPracticeLogList(Context context) {
        return PracticeLogRepository.getPracticeLogList(context);
    }

    public static void savePracticeLog(Context context, Practice practiceLog) {
        if (HanZiLogService.savePracticeLogHanZi(context, practiceLog) &&
                PracticeLogResource.movePracticeLogCoverImg(context, practiceLog) &&
                PracticeLogRepository.savePracticeLog(context, practiceLog)) {
            Toast.makeText(context, R.string.save_success, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, R.string.save_error, Toast.LENGTH_SHORT).show();
        }
    }

    public static void deletePracticeLog(Context context, Practice practiceLog) {
        if (HanZiLogService.deletePracticeLogHanZi(context, practiceLog) &&
                PracticeLogResource.deletePracticeLogCoverImg(practiceLog) &&
                PracticeLogRepository.deletePracticeLog(context, practiceLog)) {
            Toast.makeText(context, R.string.delete_success, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, R.string.delete_error, Toast.LENGTH_SHORT).show();
        }
    }
}
