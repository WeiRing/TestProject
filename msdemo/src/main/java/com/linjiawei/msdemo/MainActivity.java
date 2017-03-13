package com.linjiawei.msdemo;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;

public class MainActivity extends AutoLayoutActivity {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private ArrayList<TabFragment> tabFragments;
    private TabFragmentAdapter tabFragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabFragments = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            tabFragments.add(TabFragment.newInstance((i + 1) + "页数据"));
        }
        tabFragmentAdapter = new TabFragmentAdapter(getSupportFragmentManager(), tabFragments);
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mViewPager.setAdapter(tabFragmentAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
