package com.bupt.inklue.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bupt.inklue.R;
import com.bupt.inklue.data.CardData;

//图像显示碎片
public class EvaluateFragment extends Fragment {

    private View root;//根视图
    private final CardData cardData;//图片数据

    public EvaluateFragment(CardData cardData) {
        this.cardData = cardData;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if (root == null) {
            root = inflater.inflate(R.layout.fragment_evaluate, container, false);

            //设置图像
            ImageView imageview_image = root.findViewById(R.id.imageview_image);
            Bitmap bitmap = BitmapFactory.decodeFile(cardData.getWrittenImgPath());
            imageview_image.setImageBitmap(bitmap);

            //设置评分
            TextView textview_score = root.findViewById(R.id.textview_score);
            textview_score.setText(cardData.getScore());

            //设置建议
            TextView textview_advice = root.findViewById(R.id.textview_advice);
            textview_advice.setText(cardData.getAdvice());
        }
        return root;
    }
}
