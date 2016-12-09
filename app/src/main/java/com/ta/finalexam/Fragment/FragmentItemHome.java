package com.ta.finalexam.Fragment;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;
import com.ta.finalexam.Adapter.HomeAdapter;
import com.ta.finalexam.Bean.HomeBean.HomeBean;
import com.ta.finalexam.Constant.ApiConstance;
import com.ta.finalexam.R;
import com.ta.finalexam.Ulities.animation.EndlessRecyclerOnScrollListener;
import com.ta.finalexam.api.HomeResponse;
import com.ta.finalexam.api.Request.FavouritesRequest;
import com.ta.finalexam.api.Request.FollowRequest;
import com.ta.finalexam.api.Request.HomeRequest;
import com.ta.finalexam.callback.OnClickRecycleView;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import butterknife.BindView;
import vn.app.base.api.response.BaseResponse;
import vn.app.base.api.volley.callback.ApiObjectCallBack;
import vn.app.base.util.DebugLog;
import vn.app.base.util.FragmentUtil;
import vn.app.base.util.IntentUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentItemHome extends BaseHeaderListFragment {
    private List<HomeBean> homeBeanList;
    private HomeAdapter vAdapter;
    private String last_time_stamp;
    private int type;

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
        fragmentItemHome.setArguments(bundle);
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
    }

    @Override
    protected void onRefreshData() {
        getHome(true);
    }

    @Override
    protected void initData() {
        if (homeBeanList == null) {
            getHome(false);
        } else {
            handleHomeFirstData(homeBeanList);
        }

    }

    private void getHome(final boolean isRefresh) {
        HomeRequest homeRequest = new HomeRequest(type);
        homeRequest.setRequestCallBack(new ApiObjectCallBack<HomeResponse>() {
            @Override
            public void onSuccess(HomeResponse data) {
                initialResponseHandled();
                handleHomeFirstData(data.data);
            }

            @Override
            public void onFail(int failCode, String message) {
                initialNetworkError();
            }
        });
        homeRequest.execute();
    }

    public void getLastTimeStamp(List<HomeBean> inHomeBeanList) {
        if (inHomeBeanList != null) {
            int size = inHomeBeanList.size();
            if (size > 0) {
                HomeBean homeBeanLast = inHomeBeanList.get(size - 1);
                last_time_stamp = String.valueOf(homeBeanLast.image.createdAt);
            }
        }
    }


    private void handleHomeFirstData(List<HomeBean> firstHomeBeanList) {
        this.homeBeanList = firstHomeBeanList;
        vAdapter = new HomeAdapter(homeBeanList);
        rvList.setAdapter(vAdapter);
        rvList.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore(int currentPage) {
                DebugLog.e("onLoadMore==:" + currentPage);
                final HomeRequest homeRequest2 = new HomeRequest(type, last_time_stamp, 10);
                homeRequest2.setRequestCallBack(new ApiObjectCallBack<HomeResponse>() {
                    @Override
                    public void onSuccess(HomeResponse data) {
                        initialResponseHandled();
                        if (data.data.size() != 0) {
                            int count = data.data.size();
                            for (int i = 0; i < count; i++) {
                                homeBeanList.add(data.data.get(i));
                            }
                            vAdapter.notifyDataSetChanged();
                            getLastTimeStamp(data.data);
                        }
                    }

                    @Override
                    public void onFail(int failCode, String message) {
                        initialNetworkError();
                    }
                });
                homeRequest2.execute();
            }
        });
        vAdapter.setOnClickCallBack(new OnClickRecycleView() {
            @Override
            public void onFollowResponse(final String userId, final int status) {
                FollowRequest followRequest = new FollowRequest(userId, status);
                followRequest.setRequestCallBack(new ApiObjectCallBack<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse data) {
                        if (data.status == 1) {
                            changeFollowLocal(userId, status);
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
            public void onFavouriteResponse(final String imageId, final int status) {
                FavouritesRequest favouritesRequest = new FavouritesRequest(imageId, status);
                favouritesRequest.setRequestCallBack(new ApiObjectCallBack<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse data) {
                        hideCoverNetworkLoading();
                        if (data.status == 1) {
                            changeFavouriteLocal(imageId, status);
                            vAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFail(int failCode, String message) {

                    }
                });
                favouritesRequest.execute();
            }

            @Override
            public void onGoToProfile(String userId) {
                FragmentUtil.pushFragment(getActivity(), FragmentProfile.newInstance(userId), null, "ProfileUser");
            }

            @Override
            public void onGoToDetail(HomeBean homeBean) {
                FragmentUtil.pushFragment(getActivity(), FragmentDetail.newInstance(homeBean), null, "DetailImage");
            }

            @Override
            public void onMapClick(HomeBean homeBean) {
                goToMapAddress(homeBean);
            }

            @Override
            public void onPhotoClick(HomeBean homeBean) {
                FragmentUtil.pushFragmentWithAnimation(getActivity(), FragmentDetail.newInstance(homeBean), null);
            }
        });
        getLastTimeStamp(homeBeanList);
    }

    private void changeFollowLocal(String userId, int status) {
        Log.e("changeFollowLocal", "===>" + status);
        int size = homeBeanList.size();
        for (int i = 0; i < size; i++) {
            homeBean = homeBeanList.get(i);
            if (homeBean != null && homeBean.user.id.equals(userId)) {
                if (status == ApiConstance.FOLLOW) {
                    homeBeanList.get(i).user.isFollowing = true;
                } else {
                    homeBeanList.get(i).user.isFollowing = false;
                }
            }
        }
    }

    private void changeFavouriteLocal(String imageId, int status) {
        int size = homeBeanList.size();
        for (int i = 0; i < size; i++) {
            homeBean = homeBeanList.get(i);
            if (homeBean != null && homeBean.image.id.equals(imageId)) {
                if (status == ApiConstance.UN_FAVOURITE) {
//                    homeBeanList.get(i).image.isFavourite = false;
                    homeBean.image.isFavourite = false;
                } else {
//                    homeBeanList.get(i).image.isFavourite = true;
                    homeBean.image.isFavourite = true;
                }

            }
        }
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

//    private void removeUserId(List<HomeBean> inHomeBeanList) {
//        int size = inHomeBeanList.size();
//        for (int i = 0; i < size ; i++){
//            inHomeBeanList.remove(inHomeBeanList.get(i).user.id)
//        }
//    }
}