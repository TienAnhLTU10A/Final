package com.ta.finalexam.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ta.finalexam.Bean.HeaderControlBean;
import com.ta.finalexam.Constant.HeaderOption;
import com.ta.finalexam.R;

import vn.app.base.fragment.BaseSwipeRefreshFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseHeaderListFragment extends BaseSwipeRefreshFragment {

    @Override
    protected void initView(View root) {
        if (commonListener != null) {
            commonListener.onCommonUIHandle(showHeaderBundle());
        }
    }

    private Bundle showHeaderBundle() {
        Bundle bundle = new Bundle();
        HeaderControlBean headerControlBean = new HeaderControlBean(getScreenTitle());
        headerControlBean.setHeaderOption(HeaderOption.SHOW_HEADER, getLeftIcon(), getRightIcon());
        bundle.putParcelable(HeaderOption.HEADER_CONTROL, headerControlBean);
        return bundle;
    }

    protected int getLeftIcon() {
        return 0;
    }

    protected int getRightIcon() {
        return 0;
    }

    protected boolean isHeaderTransparent() {
        return false;
    }
}
