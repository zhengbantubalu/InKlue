package com.bupt.inklue.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bupt.inklue.R;
import com.bupt.inklue.data.CardData;

//图像检查碎片
public class CheckFragment extends Fragment {

    private View root;//根视图
    private CardData cardData;//图片数据

    public CheckFragment(CardData cardData) {
        this.cardData = cardData;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if (root == null) {
            root = inflater.inflate(R.layout.fragment_image, container, false);

            //显示图像
            ImageView imageView = root.findViewById(R.id.imageview_image);
            Bitmap bitmap = BitmapFactory.decodeFile(cardData.getWrittenImgPath());
            imageView.setImageBitmap(bitmap);
        }
        return root;
    }

    //更新数据
    public void update(CardData cardData) {
        this.cardData = cardData;
        ImageView imageView = root.findViewById(R.id.imageview_image);
        Bitmap bitmap = BitmapFactory.decodeFile(cardData.getWrittenImgPath());
        imageView.setImageBitmap(bitmap);
    }
}
