package com.bupt.inklue.data.network;

import android.content.Context;

import com.bupt.inklue.R;

import org.json.JSONArray;

public class PracticeNetwork {

    public static JSONArray getPracticeJSONArray(Context context) {
        String urlString = context.getString(R.string.server_url) + context.getString(R.string.practice_url);
        return NetworkHelper.getJSONArray(urlString);
    }
}
