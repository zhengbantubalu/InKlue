package com.bupt.data.network;

import com.bupt.data.util.Constants;
import com.bupt.data.util.NetworkHelper;

import org.json.JSONArray;

public class PracticeNetwork {

    public static JSONArray getPracticeJSONArray() {
        String urlString = Constants.URL_SERVER + Constants.URL_PRACTICE;
        return NetworkHelper.getJSONArray(urlString);
    }
}
