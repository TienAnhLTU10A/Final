package com.ta.finalexam.Fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ta.finalexam.Bean.UserBean;
import com.ta.finalexam.Constant.ApiConstance;
import com.ta.finalexam.R;
import com.ta.finalexam.Ulities.manager.UserManager;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import vn.app.base.fragment.CommonFragment;
import vn.app.base.imageloader.ImageLoader;

public class FragmentMenu extends CommonFragment {

    @BindView(R.id.tvUserName)
    TextView tvUserName_Nav;

    @BindView(R.id.menu_avatar)
    CircleImageView menu_Avatar;


    private NavigationDrawerCallbacks mCallbacks;

    private static final String STATE_SELECT_POSITION = "select_navigation_drawer_position";

    private static final String PREF_USER_LEARN_DRAWER = "nav_learn_drawer";

    private int mCurrentSelectPosition = 1;
    private boolean mUserLearnDrawer;
    private DrawerLayout mDrawerLayout;

    View contentView;

    private View mFragmentContainerView;
    private RelativeLayout menu_1;
    private LinearLayout menu_2, menu_3, menu_4, menu_5, menu_6, menu_7;

    private int currentmenupos = 0;
    private float lastTranslate = 0.0f;

    public FragmentMenu() {
    }

    public static FragmentMenu newInstance() {
        FragmentMenu fragment = new FragmentMenu();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mUserLearnDrawer = sp.getBoolean(PREF_USER_LEARN_DRAWER, false);
        if (savedInstanceState != null) {
            mCurrentSelectPosition = savedInstanceState.getInt(STATE_SELECT_POSITION);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_menu;
    }

    @Override
    protected void initView(View root) {

    }

    @OnClick(R.id.menu_1)
    public void onProfile() {
        selectItem(0);
    }

    @OnClick(R.id.menu_2)
    public void onHome() {
        selectItem(1);
    }

    @OnClick(R.id.menu_3)
    public void onUpload() {
        selectItem(2);
    }

    @OnClick(R.id.menu_4)
    public void onFavourite() {
        selectItem(3);
    }

    @OnClick(R.id.menu_5)
    public void onNearby() {
        selectItem(4);
    }

    @OnClick(R.id.menu_6)
    public void onFollow() {
        selectItem(5);
    }

    @OnClick(R.id.menu_7)
    public void onLogout() {
        selectItem(6);
    }

    @Override
    protected void getArgument(Bundle bundle) {

    }

    @Override
    protected void initData() {

    }

    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    public void setCurrentMenu(int position) {
        View currentmenu = getMenuByPosition(currentmenupos);
        if (currentmenu != null) {
            currentmenu.setSelected(false);
        }
        this.currentmenupos = position;
        currentmenu = getMenuByPosition(currentmenupos);
        if (currentmenu != null) {
            currentmenu.setSelected(true);
        }
    }

    public View getMenuByPosition(int position) {
        switch (position) {
            case 0:
                return menu_1;
            case 1:
                return menu_2;
            case 2:
                return menu_3;
            case 3:
                return menu_4;
            case 4:
                return menu_5;
            case 5:
                return menu_6;
            case 6:
                return menu_7;
            default:
                return null;
        }
    }

    public void setUp(final int fragmentId, DrawerLayout drawerLayout) {

        if (UserManager.getCurrentUser() != null) {
            tvUserName_Nav.setText(UserManager.getCurrentUser().username);
            ImageLoader.loadImage(getActivity(), UserManager.getCurrentUser().avatar, menu_Avatar);
        }

        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        contentView = getActivity().findViewById(R.id.container);
        mDrawerLayout.setScrimColor(Color.TRANSPARENT);

        ImageView imageDrawer = (ImageView) getActivity().findViewById(R.id.headerMenu);
        imageDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                    mDrawerLayout.closeDrawers();
                } else {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
                getActivity().invalidateOptionsMenu();
            }
        });
        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                float moveFactor = (drawerView.getWidth() * slideOffset);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    contentView.setTranslationX(moveFactor);
                } else {
                    TranslateAnimation anim = new TranslateAnimation(lastTranslate, moveFactor, 0.0f, 0.0f);
                    anim.setDuration(0);
                    anim.setFillAfter(true);
                    contentView.startAnimation(anim);

                    lastTranslate = moveFactor;
                }
//              mDrawerLayout.bringChildToFront(drawerView);
                mDrawerLayout.requestLayout();

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                if (!isAdded()) {
                    return;
                }
                if (!mUserLearnDrawer) {
                    // Nguoi Dung Tu mo Drawer . Tranh Viec auto-show
                    // the navigation drawer automatically in the future.
                    mUserLearnDrawer = true;
                    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    sp.edit().putBoolean(PREF_USER_LEARN_DRAWER, true).apply();
                }
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                if (!isAdded()) {
                    return;
                }
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        setCurrentMenu(0);

    }


    private void selectItem(int position) {
//        if (position != 2 && position != 4 && position != 5) {
//            setCurrentMenu(position);
//        }
        mCurrentSelectPosition = position;
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        }
        if (mCallbacks != null) {
            mCallbacks.onNavigationDrawerItemSelected(position);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity activity = context instanceof Activity ? (Activity) context : null;

        if (activity != null) {
            try {
                mCallbacks = (NavigationDrawerCallbacks) activity;
            } catch (ClassCastException e) {
                throw new ClassCastException("Activity must implement NavigationDraweraCallbacks");
            }
        }


    }


    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECT_POSITION, mCurrentSelectPosition);
    }


    /**
     * Callbacks interface that all activities using this fragment must implement.
     */
    public interface NavigationDrawerCallbacks {
        /**
         * Called when an item in the navigation drawer is selected.
         */
        void onNavigationDrawerItemSelected(int position);
    }


}
