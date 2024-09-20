package com.bupt.data.network;

import android.content.Context;
import android.widget.ProgressBar;

import com.bupt.data.api.HanZiApi;
import com.bupt.data.pojo.HanZi;
import com.bupt.data.util.Constants;
import com.bupt.data.util.DirectoryHelper;
import com.bupt.data.util.NetworkHelper;

import org.json.JSONArray;

import java.util.ArrayList;

public class HanZiNetwork {

    public static JSONArray getHanZiJSONArray() {
        String urlString = Constants.URL_SERVER + Constants.URL_HAN_ZI;
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
