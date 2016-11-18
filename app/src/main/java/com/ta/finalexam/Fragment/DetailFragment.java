package com.ta.finalexam.Fragment;

import android.os.Bundle;

import com.ta.finalexam.Bean.HomeBean.Image;
import com.ta.finalexam.Constant.HeaderOption;

/**
 * Created by Veteran Commander on 10/19/2016.
 */

public class DetailFragment extends BaseHeaderListFragment {
    Image slImage;

    public static DetailFragment newInstance(Image image){
        DetailFragment newFragment = new DetailFragment();
        newFragment.slImage = image;
        return newFragment;

    }

    @Override
    protected void onRefreshData() {

    }

    @Override
    protected void getArgument(Bundle bundle) {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLeftIcon() {
        return HeaderOption.LEFT_BACK;
    }

    @Override
    protected int getRightIcon() {
        return HeaderOption.RIGHT_DELETE;
    }

    @Override
    public String getScreenTitle() {
        return "Detail";
    }
}
