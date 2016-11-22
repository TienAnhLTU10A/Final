package com.ta.finalexam.Fragment;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;
import com.ta.finalexam.Adapter.HomeAdapter;
import com.ta.finalexam.Bean.DetailBean.ImageDetailBean;
import com.ta.finalexam.Bean.HomeBean.HomeBean;
import com.ta.finalexam.Constant.ApiConstance;
import com.ta.finalexam.Constant.HomeType;
import com.ta.finalexam.R;
import com.ta.finalexam.api.HomeResponse;
import com.ta.finalexam.api.Request.HomeRequest;
import com.ta.finalexam.callback.GoToDetail;
import com.ta.finalexam.callback.OnMapClick;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import butterknife.BindView;
import vn.app.base.api.volley.callback.ApiObjectCallBack;
import vn.app.base.util.FragmentUtil;
import vn.app.base.util.IntentUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentItemHome extends BaseHeaderListFragment {
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.recycerList)
    RecyclerView rvList;

    LatLng pictureLocation;

    private List<HomeBean> homeBeanList;
    private HomeAdapter vAdapter;
    int type;

    public FragmentItemHome() {

    }

    public static FragmentItemHome newInstance(int type) {
        FragmentItemHome fragmentItemHome = new FragmentItemHome();
        Bundle bundle = new Bundle();
        bundle.putInt(ApiConstance.TYPE, HomeType.NEW);
        bundle.putInt(ApiConstance.TYPE, HomeType.FOLLOW);
        return fragmentItemHome;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.swipe_refresh_layout;
    }

    @Override
    protected void initView(View root) {
        super.initView(root);
        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvList.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void getArgument(Bundle bundle) {
        type = bundle.getInt(ApiConstance.TYPE);
        if (type == HomeType.NEW) {
            getHome(true, type);
        } else if (type == HomeType.FOLLOW) {
            getHome(true, type);
        }
    }

    @Override
    protected void onRefreshData() {
        getHome(true, type);
    }

    @Override
    protected void initData() {
        if (homeBeanList == null) {
            getHome(false, type);
        }
    }

    private void getHome(final boolean isRefresh, int type) {
        HomeRequest homeRequest = new HomeRequest(type);
        homeRequest.setRequestCallBack(new ApiObjectCallBack<HomeResponse>() {
            @Override
            public void onSuccess(HomeResponse data) {
                initialResponseHandled();
                homeBeanList = data.data;
                handleHome();
            }

            @Override
            public void onFail(int failCode, String message) {
                initialNetworkError();
            }
        });
        homeRequest.execute();
    }

    private void handleHome() {
        vAdapter = new HomeAdapter(homeBeanList);
        rvList.setAdapter(vAdapter);
        vAdapter.setOnMapCallBack(new OnMapClick() {
            @Override
            public void onMapClick(HomeBean homeBean) {

                goToMapAddress(homeBean);
            }
        });
        vAdapter.setImageClicked(new GoToDetail() {
            @Override
            public void onImageClicked(HomeBean homeBean) {
                FragmentUtil.pushFragment(getActivity(), FragmentDetail.newInstance(homeBean), null);
            }
        });
    }

    private void goToMapAddress(HomeBean homeBean) {
        Uri mapUri;
        if (pictureLocation != null) {
            mapUri = Uri.parse("geo:" + pictureLocation.latitude + "," + pictureLocation.longitude + "?q=" + +pictureLocation.latitude + "," + pictureLocation.longitude + "&z=15");
        } else {
            mapUri = Uri.parse("geo:0,0?z=15&q=" + getDecodeAddress(homeBean.image.location));
        }
        IntentUtil.openMap(getActivity(), mapUri);
    }

    private String getDecodeAddress(String location) {
        try {
            return URLDecoder.decode(location, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return location.replace(" ", "+");
        }
    }

}