package com.ta.finalexam.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ta.finalexam.Constant.HomeType;
import com.ta.finalexam.Fragment.FragmentItemHome;


/**
 * Created by kooryy2 on 10/31/2016.
 */

public class HomePagerAdapter extends FragmentPagerAdapter {

    public HomePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return FragmentItemHome.newInstance(HomeType.NEW);
            case 1:
                return FragmentItemHome.newInstance(HomeType.FOLLOW);
            default:
                return FragmentItemHome.newInstance(HomeType.NEW);
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "New";
            case 1:
                return "Follow";
        }
        return super.getPageTitle(position);
    }
}
