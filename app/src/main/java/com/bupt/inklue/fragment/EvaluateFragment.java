package com.bupt.inklue.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bupt.inklue.R;
import com.bupt.inklue.data.CharData;
import com.github.chrisbanes.photoview.PhotoView;

//评价查看碎片
public class EvaluateFragment extends Fragment {

    public PhotoView photoView;//可缩放的图像视图
    private View root;//根视图
    private final CharData charData;//汉字数据

    public EvaluateFragment(CharData charData) {
        this.charData = charData;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if (root == null) {
            root = inflater.inflate(R.layout.fragment_evaluate, container, false);

            //设置图像
            photoView = root.findViewById(R.id.photoview);
            Bitmap bitmap = BitmapFactory.decodeFile(charData.getFeedbackImgPath());
            photoView.setImageBitmap(bitmap);

            //设置评分
            TextView textview_score = root.findViewById(R.id.textview_score);
            textview_score.setText(charData.getScore());

            //设置建议
            TextView textview_advice = root.findViewById(R.id.textview_advice);
            textview_advice.setText(charData.getAdvice());
        }
        return root;
    }
}
