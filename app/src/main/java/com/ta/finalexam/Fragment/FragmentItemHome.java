package com.ta.finalexam.Fragment;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;
import com.ta.finalexam.Adapter.HomeAdapter;
import com.ta.finalexam.Bean.HomeBean.HomeBean;
import com.ta.finalexam.Constant.ApiConstance;
import com.ta.finalexam.R;
import com.ta.finalexam.api.HomeResponse;
import com.ta.finalexam.api.Request.FavouritesRequest;
import com.ta.finalexam.api.Request.FollowRequest;
import com.ta.finalexam.api.Request.HomeRequest;
import com.ta.finalexam.callback.OnClickRecycleView;
import com.ta.finalexam.callback.OnMapClick;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import butterknife.BindView;
import vn.app.base.api.response.BaseResponse;
import vn.app.base.api.volley.callback.ApiObjectCallBack;
import vn.app.base.util.IntentUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentItemHome extends BaseHeaderListFragment {
    public int maxItem = 10;
    private List<HomeBean> homeBeanList;
    private HomeAdapter vAdapter;
    int type;

    @BindView(R.id.recycerList)
    RecyclerView rvList;

    LatLng pictureLocation;
    HomeBean homeBean;

    public FragmentItemHome() {

    }

    public static FragmentItemHome newInstance(int type) {
        FragmentItemHome fragmentItemHome = new FragmentItemHome();
        Bundle bundle = new Bundle();
        bundle.putInt(ApiConstance.TYPE, type);
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
        getHome(true, type);
    }

    @Override
    protected void onRefreshData() {
        getHome(true, type);
    }

    @Override
    protected void initData() {
        if (homeBeanList == null) {
            getHome(false, type);
        } else {
            handleHomeData(homeBeanList);
        }
    }

    private void getHome(final boolean isRefresh, int type) {
        showCoverNetworkLoading();
        HomeRequest homeRequest = new HomeRequest(type, maxItem, homeBean);
        homeRequest.setRequestCallBack(new ApiObjectCallBack<HomeResponse>() {
            @Override
            public void onSuccess(HomeResponse data) {
                initialResponseHandled();
                handleHomeData(data.data);
//                homeBeanList = data.data;
                vAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFail(int failCode, String message) {
                initialNetworkError();
            }
        });
        homeRequest.execute();
    }

    @Override
    protected void onLoadingMore(int currentPage) {
        super.onLoadingMore(currentPage);
    }

    private void handleHomeData(List<HomeBean> inHomeBeanList) {
        this.homeBeanList = inHomeBeanList;
        vAdapter = new HomeAdapter(homeBeanList);
        rvList.setAdapter(vAdapter);
        vAdapter.setOnMapCallBack(new OnMapClick() {
            @Override
            public void onMapClick(HomeBean homeBean) {
                goToMapAddress(homeBean);
            }
        });
        vAdapter.setOnClickCallBack(new OnClickRecycleView() {
            @Override
            public void onFollowResponse(String userId, int status) {
                FollowRequest followRequest = new FollowRequest(userId, status);
                followRequest.setRequestCallBack(new ApiObjectCallBack<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse data) {
                        if (data.status == 1) {
                            vAdapter.notifyDataSetChanged();
                        }
                    }
                    @Override
                    public void onFail(int failCode, String message) {
                    }
                });
                followRequest.execute();
            }

            @Override
            public void onFavouriteResponse(String imageId, int status) {
                FavouritesRequest favouritesRequest = new FavouritesRequest(imageId, status);
                favouritesRequest.setRequestCallBack(new ApiObjectCallBack<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse data) {
                        hideCoverNetworkLoading();
                        if (data.status == 1) {
                            vAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFail(int failCode, String message) {

                    }
                });
                favouritesRequest.execute();
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