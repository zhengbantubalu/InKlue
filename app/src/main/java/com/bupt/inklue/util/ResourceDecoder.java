package com.bupt.inklue.util;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;

import androidx.core.content.ContextCompat;

import org.opencv.core.Scalar;

//资源解码器
public class ResourceDecoder {

    //根据颜色资源ID取得OpenCV的Scalar对象
    public static Scalar getScalar(Context context, int resourceID) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(resourceID, typedValue, true);
        int colorResId = typedValue.resourceId;
        int color = ContextCompat.getColor(context, colorResId);
        return new Scalar(Color.red(color), Color.green(color), Color.blue(color));
    }

    //根据颜色资源ID取得颜色
    public static int getColor(Context context, int resourceID) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(resourceID, typedValue, true);
        int colorResId = typedValue.resourceId;
        return ContextCompat.getColor(context, colorResId);
    }
}
