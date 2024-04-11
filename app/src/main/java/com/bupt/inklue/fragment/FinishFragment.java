package com.bupt.inklue.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bupt.inklue.R;

//完成碎片
public class FinishFragment extends Fragment {

    private View root;//根视图
    private String text;//文字

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if (root == null) {
            root = inflater.inflate(R.layout.fragment_finish, container, false);
            if (text != null) {
                TextView textview_finish = root.findViewById(R.id.textview_finish);
                textview_finish.setText(text);
            }
        }
        return root;
    }

    //设置文字
    public void setText(String text) {
        this.text = text;
    }
}
