package com.ta.finalexam.Fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ta.finalexam.Adapter.UserProfileListAdapter;
import com.ta.finalexam.Bean.ImageListBean;
import com.ta.finalexam.Bean.ProfileBean;
import com.ta.finalexam.Bean.UserBean;
import com.ta.finalexam.Constant.FragmentActionConstant;
import com.ta.finalexam.Constant.HeaderOption;
import com.ta.finalexam.R;
import com.ta.finalexam.Ulities.manager.UserManager;
import com.ta.finalexam.api.ImageListResponse;
import com.ta.finalexam.api.ProfileResponse;
import com.ta.finalexam.api.Request.ImageListProfileUserRequest;
import com.ta.finalexam.api.Request.ProfileUserRequest;
import com.ta.finalexam.api.Request.UpdateProfileRequest;
import com.ta.finalexam.callback.OnUserEdit;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import vn.app.base.api.volley.callback.ApiObjectCallBack;
import vn.app.base.api.volley.callback.SimpleRequestCallBack;
import vn.app.base.util.FragmentUtil;
import vn.app.base.util.ImagePickerUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentProfile extends BaseHeaderListFragment implements OnUserEdit {
    public static final String USER_ID = "USER_ID";

    @BindView(R.id.recycerList)
    RecyclerView rvList;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipe_refresh_layout;

    @BindView(R.id.fabCamera)
    FloatingActionButton fabCamera;

    private ProfileBean profileBean;
    private List<ImageListBean> imageListBean;
    private String userId;
    String userPhotoPath;
    UserBean currentUser = UserManager.getCurrentUser();
    ImagePickerUtil imagePickerUtil = new ImagePickerUtil();
    UserProfileListAdapter mAdapter = new UserProfileListAdapter();

    public FragmentProfile() {
    }

    public static FragmentProfile newInstance(String userId) {
        FragmentProfile fragmentProfile = new FragmentProfile();
        Bundle bundle = new Bundle();
        bundle.putString(USER_ID, userId);
        fragmentProfile.getArgument(bundle);
        return fragmentProfile;
    }

    @Override
    protected void getArgument(Bundle bundle) {
        userId = bundle.getString(USER_ID);
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
        if (userId.equals(currentUser.id)) {
            return HeaderOption.RIGHT_UPDATE;
        } else {
            return 0;
        }
    }

    @Override
    public String getScreenTitle() {
        if (userId.equals(currentUser.id)) {
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
        getProfileUserHeader(true);
    }

    @Override
    protected void initData() {
        if (profileBean == null && imageListBean == null) {
            getProfileUserHeader(false);
        } else {
            setUpData();
        }
    }

    private void getProfileUserHeader(boolean isRefresh) {
        ProfileUserRequest profileUserRequest;
        if (userId.equalsIgnoreCase(currentUser.id)) {
            profileUserRequest = new ProfileUserRequest("");
        } else {
            profileUserRequest = new ProfileUserRequest(userId);
        }
        profileUserRequest.setRequestCallBack(new ApiObjectCallBack<ProfileResponse>() {
            @Override
            public void onSuccess(ProfileResponse data) {
                initialResponseHandled();
                profileBean = data.profileBean;
                getImageListProfileUser();

            }

            @Override
            public void onFail(int failCode, String message) {
                initialNetworkError();
            }
        });
        profileUserRequest.execute();
    }

    private void getImageListProfileUser() {
        ImageListProfileUserRequest imageListProfileUserRequest;
        if (userId.equalsIgnoreCase(currentUser.id)) {
            imageListProfileUserRequest = new ImageListProfileUserRequest("");
        } else {
            imageListProfileUserRequest = new ImageListProfileUserRequest(userId);
        }
        imageListProfileUserRequest.setRequestCallBack(new ApiObjectCallBack<ImageListResponse>() {
            @Override
            public void onSuccess(ImageListResponse data) {
                initialResponseHandled();
                imageListBean = data.data;
                setUpData();
            }

            @Override
            public void onFail(int failCode, String message) {
                initialNetworkError();
            }
        });
        imageListProfileUserRequest.execute();
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

    private void setUpData() {
        mAdapter.setHeader(profileBean);
        mAdapter.setItems(imageListBean);
        rvList.setAdapter(mAdapter);
        mAdapter.setOnUserEdit(this);
    }

    @Override
    public void onFragmentDataHandle(Bundle bundle) {
        super.onFragmentDataHandle(bundle);
        if (bundle != null) {
            userPhotoPath = bundle.getString(USER_ID, null);
            if (userPhotoPath != null) {
                updateProfile(imagePickerUtil.createFileUri(getActivity()));
            }
        }
    }

    @Override
    public void OnChangePhoto(int position) {
        if (commonListener != null) {
            Bundle bundle = new Bundle();
            bundle.putInt(FragmentActionConstant.FRAGMENT_ACTION, FragmentActionConstant.PICK_IMAGE);
            commonListener.onCommonUIHandle(bundle);
        }
    }

    @Override
    public void onUpdateProfile(File avatar) {

    }

    @OnClick(R.id.fabCamera)
    public void goToUpload() {
        FragmentUtil.pushFragment(getActivity(), FragmentImageUpload.newInstance(), null);
    }
}
