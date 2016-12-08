package com.ta.finalexam.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ta.finalexam.Activity.MainActivity;
import com.ta.finalexam.Bean.HomeBean.HomeBean;
import com.ta.finalexam.Bean.UserBean;
import com.ta.finalexam.Constant.HeaderOption;
import com.ta.finalexam.R;
import com.ta.finalexam.Ulities.manager.UserManager;
import com.ta.finalexam.api.HomeResponse;
import com.ta.finalexam.api.Request.FavouriteListRequest;

import java.util.List;

import butterknife.BindView;
import vn.app.base.api.volley.callback.ApiObjectCallBack;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentFavourite extends BaseHeaderListFragment {
    private static final String USER_ID = "USER_ID";

    @BindView(R.id.recycerList)
    RecyclerView rvList;

    private List<HomeBean> homeBeanList;
    String userId;
    UserBean currentUser = UserManager.getCurrentUser();

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

    @Override
    protected void onLoadingMore(int currentPage) {
        super.onLoadingMore(currentPage);
    }

    private void handleFavouriteListData(List<HomeBean> inHomeBeanList) {
        homeBeanList = inHomeBeanList;

    }

    private void getFavouriteList(boolean isRefresh) {
        FavouriteListRequest favouriteListRequest;
        if (userId.equals(currentUser.id)) {
            favouriteListRequest = new FavouriteListRequest("");
        } else {
            favouriteListRequest = new FavouriteListRequest(userId);
        }
        favouriteListRequest.setRequestCallBack(new ApiObjectCallBack<HomeResponse>() {
            @Override
            public void onSuccess(HomeResponse data) {
                initialResponseHandled();
            }

            @Override
            public void onFail(int failCode, String message) {
                initialNetworkError();
            }
        });
        favouriteListRequest.execute();
    }
}
