package com.example.summercoding.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.summercoding.Fragment.Daily;
import com.example.summercoding.Fragment.Monthly;
import com.example.summercoding.Fragment.Weekly;

public class TabPagerAdapter extends FragmentStatePagerAdapter {

    private int tabCount;

    public TabPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Monthly tabFragment1 = new Monthly();
                return tabFragment1;
            case 1:
                Weekly tabFragment2 = new Weekly();
                return tabFragment2;
            case 2:
                Daily tabFragment3 = new Daily();
                return tabFragment3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}