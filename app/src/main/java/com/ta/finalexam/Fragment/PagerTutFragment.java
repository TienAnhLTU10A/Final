package com.ta.finalexam.Fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ta.finalexam.Bean.TutorialBean.TutorialBean;
import com.ta.finalexam.Bean.TutorialBean.User;
import com.ta.finalexam.R;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import vn.app.base.imageloader.ImageLoader;
import vn.app.base.util.StringUtil;

/**
 * Created by Veteran Commander on 10/21/2016.
 */

public class PagerTutFragment extends NoHeaderFragment {

    @BindView(R.id.iv_tut_ava)
    CircleImageView ivAvatar;

    @BindView(R.id.tv_tut_text)
    TextView tvTutorial;

    @BindView(R.id.iv_tut_bg)
    ImageView imBg;

    String userAva;

    String title;
    String image;
    boolean showAva;

    public static final String TUT_TITLE = "Title";
    public static final String TUT_AVA = "Avatar";
    public static final String TUT_SHOWAVA = "ShowAva";
    public static final String TUT_IMG = "Image";

    public static PagerTutFragment newInstance(TutorialBean tutorialBean, User user) {
        PagerTutFragment newFragment = new PagerTutFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TUT_AVA, user.avatar);
        bundle.putString(TUT_TITLE, tutorialBean.title);
        bundle.putString(TUT_IMG, tutorialBean.image);
        bundle.putBoolean(TUT_SHOWAVA, tutorialBean.showAvatar);
        newFragment.setArguments(bundle);
        return newFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.tut_screen_pager;
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
    }

    @Override
    protected void getArgument(Bundle bundle) {
        title = bundle.getString(TUT_TITLE);
        image = bundle.getString(TUT_IMG);
        showAva = bundle.getBoolean(TUT_SHOWAVA);
        userAva = bundle.getString(TUT_AVA);
    }

    @Override
    protected void initData() {
        if (showAva == false) {
            ivAvatar.setVisibility(View.GONE);
        } else if (showAva == true) {
            if (userAva.equals(null)) {
                ivAvatar.setVisibility(View.GONE);
            } else {
                ImageLoader.loadImage(getContext(), userAva, ivAvatar);
            }
        }
        StringUtil.displayText(title, tvTutorial);
        ImageLoader.loadImage(getContext(), image, imBg);

    }
}
