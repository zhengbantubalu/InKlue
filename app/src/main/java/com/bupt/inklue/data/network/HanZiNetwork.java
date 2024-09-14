package com.bupt.inklue.data.network;

import android.content.Context;
import android.widget.ProgressBar;

import com.bupt.inklue.R;
import com.bupt.inklue.data.api.HanZiApi;
import com.bupt.inklue.data.pojo.HanZi;
import com.bupt.inklue.util.DirectoryHelper;

import org.json.JSONArray;

import java.util.ArrayList;

public class HanZiNetwork {

    public static JSONArray getHanZiJSONArray(Context context) {
        String urlString = context.getString(R.string.server_url) + context.getString(R.string.han_zi_url);
        return NetworkHelper.getJSONArray(urlString);
    }

    public static void downloadHanZiImg(Context context, ProgressBar progressBar) {
        ArrayList<HanZi> hanZiList = HanZiApi.getHanZiListForDownloadImg(context);
        for (int i = 0; i < hanZiList.size(); i++) {
            String filePath = DirectoryHelper.getPracticeHanZiDir(context) +
                    "/" + hanZiList.get(i).getCode() + ".jpg";
            NetworkHelper.downloadImg(filePath, hanZiList.get(i).getUrl());
            progressBar.setProgress((i + 1) * 100 / hanZiList.size());
        }
    }
}
