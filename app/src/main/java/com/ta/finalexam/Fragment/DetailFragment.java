package com.ta.finalexam.Fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.ta.finalexam.Adapter.ImageDetailListAdapter;
import com.ta.finalexam.Bean.DetailBean.DetailData;
import com.ta.finalexam.Bean.DetailBean.ImageDetailBean;
import com.ta.finalexam.Bean.HomeBean.Image;
import com.ta.finalexam.Constant.HeaderOption;

import java.util.ArrayList;
import java.util.List;

import vn.app.base.adapter.DividerItemDecoration;

/**
 * Created by Veteran Commander on 10/19/2016.
 */

public class DetailFragment extends BaseHeaderListFragment {
    ImageDetailBean slImage;

    ImageDetailListAdapter imageDetailListAdapter;

    List<DetailData> commentList;

    public static DetailFragment newInstance(ImageDetailBean imageDetailBean){
        DetailFragment newFragment = new DetailFragment();
        newFragment.slImage = imageDetailBean;
        return newFragment;

    }

    @Override
    protected void onRefreshData() {

    }

    @Override
    protected void getArgument(Bundle bundle) {

    }

    @Override
    protected void initView(View root) {
        super.initView(root);
        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvList.addItemDecoration(new DividerItemDecoration(getActivity(), null));
        setCanRefresh(false);
    }

    @Override
    protected void initData() {
        commentList = new ArrayList<>();
        commentList.add(new DetailData("Thanh","Hay"));
        commentList.add(new DetailData("Thanh","Hay"));
        commentList.add(new DetailData("Thanh","Hay"));
        imageDetailListAdapter = new ImageDetailListAdapter();
        imageDetailListAdapter.setHeader(slImage);
        imageDetailListAdapter.setItems(commentList);
        rvList.setAdapter(imageDetailListAdapter);


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
