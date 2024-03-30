package com.bupt.inklue.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

//主页面ViewPager的页面适配器
public class HomepageAdapter extends FragmentStateAdapter {
    List<Fragment> fragmentList;

    public HomepageAdapter(FragmentManager fragmentManager, Lifecycle lifecycle,
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
