package com.linjiawei.msdemo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by : 林嘉伟  Date：2016/10/24
 */

public class TabFragmentAdapter extends FragmentPagerAdapter {
    ArrayList<TabFragment> fragments;

    public TabFragmentAdapter(FragmentManager fm, ArrayList<TabFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "第"+(position+1)+"页";
    }
}
