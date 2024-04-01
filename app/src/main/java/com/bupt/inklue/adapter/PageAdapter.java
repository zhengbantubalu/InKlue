package com.bupt.inklue.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

//ViewPager的页面适配器
public class PageAdapter extends FragmentStateAdapter {
    List<Fragment> fragmentList;

    public PageAdapter(FragmentManager fragmentManager, Lifecycle lifecycle,
                       List<Fragment> fragments) {
        super(fragmentManager, lifecycle);
        fragmentList = fragments;
    }

    @NonNull
    public Fragment createFragment(int position) {
        return fragmentList.get(position);
    }

    public int getItemCount() {
        return fragmentList.size();
    }
}
