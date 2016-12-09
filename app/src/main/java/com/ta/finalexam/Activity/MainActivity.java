package com.ta.finalexam.Activity;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ta.finalexam.Bean.HeaderControlBean;
import com.ta.finalexam.Bean.UserBean;
import com.ta.finalexam.Constant.ApiConstance;
import com.ta.finalexam.Constant.FragmentActionConstant;
import com.ta.finalexam.Constant.HeaderOption;
import com.ta.finalexam.Fragment.FragmentFavourite;
import com.ta.finalexam.Fragment.FragmentFollowlist;
import com.ta.finalexam.Fragment.FragmentHome;
import com.ta.finalexam.Fragment.FragmentImageUpload;
import com.ta.finalexam.Fragment.FragmentLogin;
import com.ta.finalexam.Fragment.FragmentMenu;
import com.ta.finalexam.Fragment.FragmentNearby;
import com.ta.finalexam.Fragment.FragmentProfile;
import com.ta.finalexam.R;
import com.ta.finalexam.Ulities.manager.UserManager;

import butterknife.BindView;
import butterknife.OnClick;
import vn.app.base.activity.CommonActivity;
import vn.app.base.util.FragmentUtil;
import vn.app.base.util.ImagePickerUtil;
import vn.app.base.util.StringUtil;
import vn.app.base.util.UiUtil;

public class MainActivity extends CommonActivity implements FragmentMenu.NavigationDrawerCallbacks {

    @BindView(R.id.toolbar)
    RelativeLayout rlToolbar;

    @BindView(R.id.headerMenu)
    ImageView ivMenu;

    @BindView(R.id.headerBack)
    ImageView ivBack;

    @BindView(R.id.tv_delete)
    TextView tvDelete;

    @BindView(R.id.tv_update)
    TextView tvUpdate;

    @BindView(R.id.headerTitle)
    TextView tvTitle;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    FragmentMenu fragmentMenu;
    ImagePickerUtil imagePickerUtil = new ImagePickerUtil();
    UserBean currentUser;


    @Override
    protected String getNoConnectionMessage() {
        return getString(R.string.dialog_error_no_connection);
    }

    @Override
    protected String getErrorAPIMessage() {
        return getString(R.string.dialog_error_no_connection);
    }

    @Override
    protected String getTimeOutMessage() {
        return getString(R.string.dialog_error_timeout);
    }

    @Override
    public int setContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        currentUser = UserManager.getCurrentUser();
        if (currentUser == null) {
            setUpInitScreen(FragmentLogin.newInstance(), null);
        } else {
            setUpInitScreen(FragmentHome.newInstance(), ApiConstance.TAGHOME);
        }
    }

    @Override
    public void initData() {
        currentUser = UserManager.getCurrentUser();
    }

    @Override
    public void onCommonDataHandle(Bundle bundle) {

    }

    @Override
    public void onCommonUIHandle(Bundle bundle) {
        if (bundle == null) {
            return;
        }
        if (bundle.containsKey(HeaderOption.HEADER_CONTROL)) {
            HeaderControlBean headerControlBean = bundle.getParcelable(HeaderOption.HEADER_CONTROL);
            if (headerControlBean != null) {
                handleHeaderUI(headerControlBean);
            }
        } else if (bundle.containsKey(FragmentActionConstant.FRAGMENT_ACTION)) {
            int fragmentAction = bundle.getInt(FragmentActionConstant.FRAGMENT_ACTION);
            if (fragmentAction == FragmentActionConstant.PICK_IMAGE) {
                handlePickPhoto();
            }
        }
    }

    private void handlePickPhoto() {
        imagePickerUtil.pickImage(this, false);
    }

    private void handleHeaderUI(HeaderControlBean headerControlBean) {
        if (headerControlBean.headerOptions != null && headerControlBean.headerOptions.length > 0) {
            if (headerControlBean.headerOptions[0] == HeaderOption.SHOW_HEADER) {
                UiUtil.showView(rlToolbar);
            } else if (headerControlBean.headerOptions[0] == HeaderOption.HIDE_HEADER) {
                UiUtil.hideView(rlToolbar, true);
            }
            if (headerControlBean.headerOptions.length > 1 && headerControlBean.headerOptions[1] > 0) {
                handleLeftIcon(headerControlBean.headerOptions[1]);
                if (headerControlBean.headerOptions.length > 2 && headerControlBean.headerOptions[2] > 0) {
                    handleRightIcon(headerControlBean.headerOptions[2]);
                } else {
                    showAndHideIconRight(null);
                }
            } else {
                showAndHideIconLeft(ivMenu);
            }
        }
        if (StringUtil.checkStringValid(headerControlBean.title)) {
            StringUtil.displayText(headerControlBean.title, tvTitle);
        }
    }

    private void handleLeftIcon(int value) {
        switch (value) {
            case HeaderOption.LEFT_NO_OPTION:
                showAndHideIconLeft(null);
                break;
            case HeaderOption.LEFT_MENU:
                showAndHideIconLeft(ivMenu);
                handleMenuSlide();
                break;
            case HeaderOption.LEFT_BACK:
                showAndHideIconLeft(ivBack);
                break;
        }
    }

    private void handleRightIcon(int value) {
        switch (value) {
            case HeaderOption.RIGHT_NO_OPTION:
                showAndHideIconRight(null);
                break;
            case HeaderOption.RIGHT_DELETE:
                showAndHideIconRight(tvDelete);
                break;
            case HeaderOption.RIGHT_UPDATE:
                showAndHideIconRight(tvUpdate);
                break;
        }
    }

    private void showAndHideIconLeft(View target) {
        showOrHide(ivMenu, target);
        showOrHide(ivBack, target);
    }

    private void showAndHideIconRight(View target) {
        showOrHide(tvDelete, target);
        showOrHide(tvUpdate, target);
    }

    protected void showOrHide(View subject, View target) {
        subject.setVisibility(subject == target ? View.VISIBLE : View.GONE);
    }

    private void handleMenuSlide() {
        fragmentMenu = (FragmentMenu) getSupportFragmentManager().findFragmentById(R.id.nagigation_drawer);
        fragmentMenu.setUp(R.id.nagigation_drawer, drawerLayout);
    }

    @OnClick(R.id.headerBack)
    public void Back() {
        FragmentUtil.pushFragmentAnimation(getSupportFragmentManager(),FragmentHome.newInstance(),null);
    }

    @OnClick(R.id.tv_update)
    public void updateProfile() {
        if (fragmentListener != null) {
            Bundle bundle = new Bundle();
            bundle.putString(ApiConstance.UPDATE_BUTTON , null );
            fragmentListener.onFragmentUIHandle(bundle);
        }
    }
    @OnClick(R.id.tv_delete)
    public void onDelete() {
        if (fragmentListener != null) {
            Bundle bundle = new Bundle();
            bundle.putBoolean(ApiConstance.ISDELCLICK, true);
            fragmentListener.onFragmentUIHandle(bundle);
        }
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        switch (position) {
            case 0:
                FragmentUtil.pushFragmentAnimation(getSupportFragmentManager(), FragmentProfile.newInstance(UserManager.getCurrentUser().id), null);
                break;
            case 1:
                //TODO home
                FragmentUtil.pushFragmentAnimation(getSupportFragmentManager(), FragmentHome.newInstance(), null);
                break;
            case 2:
                //TODO image upload
                FragmentUtil.pushFragmentAnimation(getSupportFragmentManager(), FragmentImageUpload.newInstance(), null);

                break;
            case 3:
                //TODO favourite
                FragmentUtil.pushFragmentAnimation(getSupportFragmentManager(), FragmentFavourite.newInstance(UserManager.getCurrentUser().id) , null);
                break;
            case 4:
                //TODO nearby
                FragmentUtil.pushFragmentAnimation(getSupportFragmentManager(), FragmentNearby.newInstance(), null);
                break;
            case 5:
                //TODO Follow
                FragmentUtil.pushFragmentAnimation(getSupportFragmentManager(), FragmentFollowlist.newInstance(), null);
                break;
            case 6:
                //TODO LOGOUT
                UserManager.clearUserData();
                FragmentUtil.pushFragmentAnimation(getSupportFragmentManager(), FragmentLogin.newInstance(), null);
                break;
        }
    }
    public void onLockDrawer(){
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }
}