package com.bupt.inklue.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bupt.inklue.R;
import com.bupt.inklue.data.CharData;
import com.github.chrisbanes.photoview.PhotoView;

//图像检查碎片
public class CheckFragment extends Fragment {

    public PhotoView photoView;//可缩放的图像视图
    private View root;//根视图
    private CharData charData;//汉字数据

    public CheckFragment(CharData charData) {
        this.charData = charData;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if (root == null) {
            root = inflater.inflate(R.layout.fragment_image, container, false);

            //设置图像
            photoView = root.findViewById(R.id.photoview);
            Bitmap bitmap = BitmapFactory.decodeFile(charData.getExtractImgPath());
            photoView.setImageBitmap(bitmap);
        }
        return root;
    }

    //更新数据
    public void update(CharData charData) {
        this.charData = charData;
        Bitmap bitmap = BitmapFactory.decodeFile(charData.getExtractImgPath());
        photoView.setImageBitmap(bitmap);
    }
}
