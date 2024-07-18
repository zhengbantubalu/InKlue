package com.bupt.inklue.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;

import androidx.core.content.ContextCompat;

import org.opencv.core.Scalar;

//资源解码器
public class ResourceDecoder {

    //根据颜色资源ID取得int类型color
    public static int getColorInt(Context context, int resourceID) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(resourceID, typedValue, true);
        int colorResId = typedValue.resourceId;
        return ContextCompat.getColor(context, colorResId);
    }

    //根据颜色资源ID取得OpenCV的Scalar对象
    public static Scalar getScalar(Context context, int resourceID) {
        int color = getColorInt(context, resourceID);
        return new Scalar(Color.red(color), Color.green(color), Color.blue(color));
    }

    //获取状态栏高度
    @SuppressLint({"DiscouragedApi", "InternalInsetResource"})
    public static int getStatusBarHeight(Context context) {
        int resourceId = context.getResources().getIdentifier(
                "status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return context.getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }
}
