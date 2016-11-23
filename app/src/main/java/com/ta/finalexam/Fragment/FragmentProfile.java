package com.ta.finalexam.Fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ta.finalexam.Adapter.UserProfileListAdapter;
import com.ta.finalexam.Bean.ImageListBean.ImageListBean;
import com.ta.finalexam.Constant.HeaderOption;
import com.ta.finalexam.R;
import com.ta.finalexam.api.ProfileResponse;
import com.ta.finalexam.api.Request.ProfileUserRequest;
import com.ta.finalexam.api.Request.UpdateProfileRequest;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import vn.app.base.api.volley.callback.ApiObjectCallBack;
import vn.app.base.api.volley.callback.SimpleRequestCallBack;
import vn.app.base.util.FragmentUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentProfile extends BaseHeaderListFragment {
    UpdateProfileCallBack mCallBack;
    @BindView(R.id.recycerList)
    RecyclerView rvList;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipe_refresh_layout;

    @BindView(R.id.fabCamera)
    FloatingActionButton fabCamera;

    List<ImageListBean> imageListBean;

    UserProfileListAdapter mAdapter;

    String userId;

    public FragmentProfile() {
    }

    public static FragmentProfile newInstance() {
        FragmentProfile fragmentProfile = new FragmentProfile();
        return fragmentProfile;
    }

    @Override
    protected void getArgument(Bundle bundle) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_profile_user;
    }

    @Override
    protected int getLeftIcon() {
        return HeaderOption.LEFT_BACK;
    }

    @Override
    protected int getRightIcon() {
        if (userId == null) {
            return HeaderOption.RIGHT_UPDATE;
        } else {
            return 0;
        }
    }

    @Override
    public String getScreenTitle() {
        if (userId != null) {
            return "User";
        } else {
            return "Profile";
        }

    }

    @Override
    protected void initView(View root) {
        super.initView(root);
        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        setCanRefresh(false);
    }

    @Override
    protected boolean isStartWithLoading() {
        return imageListBean == null;
    }

    @Override
    protected void onRefreshData() {

    }

    @Override
    protected void initData() {

    }

    private void getProfileUser() {
        ProfileUserRequest profileUserRequest = new ProfileUserRequest(userId);
        profileUserRequest.setRequestCallBack(new ApiObjectCallBack<ProfileResponse>() {
            @Override
            public void onSuccess(ProfileResponse data) {
                initialResponseHandled();
            }

            @Override
            public void onFail(int failCode, String message) {
                initialNetworkError();
            }
        });
        profileUserRequest.execute();
    }

    private void handleProfileUser() {

    }

    public void updateProfile(File avatar) {
        showCoverNetworkLoading();
        new UpdateProfileRequest(avatar, new SimpleRequestCallBack() {
            @Override
            public void onResponse(boolean success, String message) {
                hideCoverNetworkLoading();
            }
        }).execute();
    }

    @OnClick(R.id.fabCamera)
    public void goToUpload() {
        FragmentUtil.pushFragment(getActivity(), FragmentImageUpload.newInstance(), null);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = context instanceof Activity ? (Activity) context : null;
        if (activity != null) {
            try {
                mCallBack = (UpdateProfileCallBack) activity;
            } catch (ClassCastException e) {
                throw new ClassCastException("Activity must implement UpdateProfileCallBack");
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallBack = null;
    }

    /**
     * Callbacks interface that all activities using this fragment must implement.
     */
    public interface UpdateProfileCallBack {
        /**
         * Called when an item in the navigation drawer is selected.
         */
        void onClickUpdate(File avatar);
    }
}