package com.ta.finalexam.Fragment;


import android.os.Bundle;
import android.os.Debug;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.ta.finalexam.Activity.MainActivity;
import com.ta.finalexam.Adapter.HomePagerAdapter;
import com.ta.finalexam.Constant.HeaderOption;
import com.ta.finalexam.R;
import com.ta.finalexam.Ulities.animation.ZoomOutPageTransformer;

import butterknife.BindView;
import butterknife.OnClick;
import vn.app.base.util.FragmentUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentHome extends HeaderFragment {
    @BindView(R.id.tab)
    TabLayout tabLayout;

    @BindView(R.id.fap)
    FloatingActionButton fap;

    @BindView(R.id.vpHome)
    ViewPager vpHome;
    private HomePagerAdapter homePagerAdapter;

    public FragmentHome() {
    }

    public static FragmentHome newInstance() {
        FragmentHome fragmentHome = new FragmentHome();
        return fragmentHome;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void getArgument(Bundle bundle) {

    }

    @Override
    protected void initData() {
        handleViewPager();
    }

    @Override
    protected int getLeftIcon() {
        return HeaderOption.LEFT_MENU;
    }

    @Override
    public String getScreenTitle() {
        return "Home";
    }

    private void handleViewPager() {
        homePagerAdapter = new HomePagerAdapter(getChildFragmentManager());
        vpHome.setAdapter(homePagerAdapter);
        tabLayout.setupWithViewPager(vpHome);
        vpHome.setPageTransformer(true, new ZoomOutPageTransformer());
    }

    @OnClick(R.id.fap)
    public void onFap() {
        FragmentUtil.pushFragment(getActivity(), FragmentImageUpload.newInstance(), null);
    }

}
