package com.ta.finalexam.Fragment;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;
import com.ta.finalexam.Adapter.HomeAdapter;
import com.ta.finalexam.Bean.HomeBean.HomeBean;
import com.ta.finalexam.Constant.ApiConstance;
import com.ta.finalexam.Constant.HeaderOption;
import com.ta.finalexam.R;
import com.ta.finalexam.Ulities.manager.UserManager;
import com.ta.finalexam.api.HomeResponse;
import com.ta.finalexam.api.Request.FavouriteListRequest;
import com.ta.finalexam.api.Request.FavouritesRequest;
import com.ta.finalexam.api.Request.FollowRequest;
import com.ta.finalexam.callback.OnClickRecycleView;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import butterknife.BindView;
import vn.app.base.api.response.BaseResponse;
import vn.app.base.api.volley.callback.ApiObjectCallBack;
import vn.app.base.util.FragmentUtil;
import vn.app.base.util.IntentUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentFavourite extends BaseHeaderListFragment {
    private static final String USER_ID = "USER_ID";

    @BindView(R.id.recycerList)
    RecyclerView rvList;

    private List<HomeBean> homeBeanList;
    String userId;
    private HomeBean homeBean;
    private HomeAdapter mAdapter;
    private LatLng pictureLocation;

    public FragmentFavourite() {

    }

    public static FragmentFavourite newInstance(String userId) {
        FragmentFavourite fragmentFavourite = new FragmentFavourite();
        Bundle bundle = new Bundle();
        bundle.putString(USER_ID, userId);
        fragmentFavourite.setArguments(bundle);
        return fragmentFavourite;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.swipe_refresh_layout;
    }

    @Override
    protected int getLeftIcon() {
        return HeaderOption.LEFT_BACK;
    }

    @Override
    public String getScreenTitle() {
        return "Favourite";
    }

    @Override
    protected void getArgument(Bundle bundle) {
        userId = bundle.getString(USER_ID);
    }

    @Override
    protected void initView(View root) {
        super.initView(root);
        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    protected void initData() {
        if (homeBeanList == null) {
            getFavouriteList(false);
        } else {
            handleFavouriteListData(homeBeanList);
        }
    }

    @Override
    protected boolean isStartWithLoading() {
        return homeBeanList == null;
    }

    @Override
    protected void onRefreshData() {
        getFavouriteList(true);
    }

    private void getFavouriteList(boolean isRefresh) {
        FavouriteListRequest favouriteListRequest;
        if (userId.equals(UserManager.getCurrentUser().id)) {
            favouriteListRequest = new FavouriteListRequest("");
        } else {
            favouriteListRequest = new FavouriteListRequest(userId);
        }
        favouriteListRequest.setRequestCallBack(new ApiObjectCallBack<HomeResponse>() {
            @Override
            public void onSuccess(HomeResponse data) {
                initialResponseHandled();
                handleFavouriteListData(data.data);

            }

            @Override
            public void onFail(int failCode, String message) {
                initialNetworkError();
            }
        });
        favouriteListRequest.execute();
    }

    private void handleFavouriteListData(List<HomeBean> inHomeBeanList) {
        this.homeBeanList = inHomeBeanList;
        mAdapter = new HomeAdapter(homeBeanList);
        rvList.setAdapter(mAdapter);
        mAdapter.setOnClickCallBack(new OnClickRecycleView() {
            @Override
            public void onFollowResponse(final String userId, final int status) {
                FollowRequest followRequest = new FollowRequest(userId, status);
                followRequest.setRequestCallBack(new ApiObjectCallBack<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse data) {
                        if (data.status == 1) {
                            changeFollowLocal(userId, status);
                            mAdapter.notifyDataSetChanged();
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
                            mAdapter.notifyDataSetChanged();
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
    }
    private void changeFollowLocal(String userId, int status) {
        Log.e("changeFollowLocal", "===>" + status);
        int size = homeBeanList.size();
        for (int i = 0; i < size; i++) {
            homeBean = homeBeanList.get(i);
            if (homeBean != null && homeBean.user.id.equals(userId)) {
                if (status == ApiConstance.FOLLOW) {
                    homeBean.user.isFollowing = true;
                } else {
                    homeBean.user.isFollowing = false;
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
                    homeBean.image.isFavourite = false;
                } else {
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
}
