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
import com.bupt.inklue.data.CharData;

//图像显示碎片
public class ImageFragment extends Fragment {

    private View root;//根视图
    private final CharData charData;//汉字数据

    public ImageFragment(CharData charData) {
        this.charData = charData;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if (root == null) {
            root = inflater.inflate(R.layout.fragment_image, container, false);

            //设置图像
            ImageView imageView = root.findViewById(R.id.imageview_image);
            Bitmap bitmap = BitmapFactory.decodeFile(charData.getStdImgPath());
            imageView.setImageBitmap(bitmap);
        }
        return root;
    }
}
