package com.bupt.data.api;

import android.content.Context;

import com.bupt.data.pojo.HanZi;
import com.bupt.data.pojo.Practice;
import com.bupt.data.service.HanZiLogService;

import java.util.ArrayList;

public class HanZiLogApi {

    public static ArrayList<HanZi> getPracticeLogHanZiList(Context context, Practice practice) {
        return HanZiLogService.getPracticeLogHanZiList(context, practice);
    }
}
