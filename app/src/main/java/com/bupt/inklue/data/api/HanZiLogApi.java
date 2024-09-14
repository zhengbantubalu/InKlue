package com.bupt.inklue.data.api;

import android.content.Context;

import com.bupt.inklue.data.pojo.HanZi;
import com.bupt.inklue.data.pojo.Practice;
import com.bupt.inklue.data.service.HanZiLogService;

import java.util.ArrayList;

public class HanZiLogApi {

    public static ArrayList<HanZi> getPracticeLogHanZiList(Context context, Practice practice) {
        return HanZiLogService.getPracticeLogHanZiList(context, practice);
    }
}
