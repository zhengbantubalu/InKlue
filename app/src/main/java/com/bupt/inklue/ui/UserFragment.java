package com.bupt.inklue.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.bupt.inklue.R;

//“我的”页面
public class UserFragment extends Fragment implements View.OnClickListener {
    private View root;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (root == null) {
            root = inflater.inflate(R.layout.fragment_user, container, false);
        }
        LinearLayout user_card = root.findViewById(R.id.card_user);
        user_card.setOnClickListener(this);
        return root;
    }

    public void onClick(View view) {

    }
}
