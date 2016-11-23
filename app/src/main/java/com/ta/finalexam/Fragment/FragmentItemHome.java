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
import vn.app.base.util.FragmentUtil;
import vn.app.base.util.IntentUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentItemHome extends BaseHeaderListFragment {
    public static final int FOLLOWING = 1;
    public static final int UN_FOLLOWING = 0;
    public int maxItem = 10;
    public int FOLLOWSTATUS;

    public static final int FAVOURITE = 1;
    public static final int UN_FAVOURITE = 0;
    public int FAVOURITESTATUS;

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
                for (int i = 0; i < maxItem; i++) {
                    homeBeanList.add(data.data.get(i));
                }
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
            public void onFollowResponse(HomeBean homeBean) {
                if (FOLLOWSTATUS == FOLLOWING) {
                    getFollow(FOLLOWSTATUS, homeBean);
                } else if (FOLLOWSTATUS == UN_FOLLOWING) {
                    getFollow(FOLLOWSTATUS, homeBean);
                }
            }

            @Override
            public void onFavouriteResponse(HomeBean homeBean) {
                if (FAVOURITESTATUS == FAVOURITE) {
                    getFavourites(FAVOURITESTATUS, homeBean);
                } else if (FAVOURITESTATUS == UN_FAVOURITE) {
                    getFavourites(FAVOURITESTATUS, homeBean);
                }
            }

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

    private void getFollow(final int mStatus, HomeBean homeBean) {
        showCoverNetworkLoading();
        FollowRequest followRequest = new FollowRequest(homeBean.user.id, mStatus);
        followRequest.setRequestCallBack(new ApiObjectCallBack<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse data) {
                hideCoverNetworkLoading();
                FOLLOWSTATUS = mStatus;
                vAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFail(int failCode, String message) {
                hideCoverNetworkLoading();
            }
        });
        followRequest.execute();
    }

    private void getFavourites(final int fStatus, HomeBean homeBean) {
        showCoverNetworkLoading();
        FavouritesRequest favouritesRequest = new FavouritesRequest(homeBean.image.id, fStatus);
        favouritesRequest.setRequestCallBack(new ApiObjectCallBack<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse data) {
                hideCoverNetworkLoading();
                FAVOURITESTATUS = fStatus;
                if (data.status == 1) {
                    vAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFail(int failCode, String message) {
                hideCoverNetworkLoading();
            }
        });
        favouritesRequest.execute();
    }
}