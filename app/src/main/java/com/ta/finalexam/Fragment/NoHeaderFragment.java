package com.ta.finalexam.Fragment;

import android.os.Bundle;
import android.view.View;

import com.ta.finalexam.Bean.HeaderControlBean;
import com.ta.finalexam.Constant.HeaderOption;

import vn.app.base.fragment.CommonFragment;

/**
 * Created on 7/21/2016.
 */
public abstract class NoHeaderFragment extends CommonFragment {

    @Override
    protected void initView(View root) {
        if (commonListener != null) {
            commonListener.onCommonUIHandle(hideHeaderBundle());
        }
    }

    private Bundle hideHeaderBundle() {
        Bundle bundle = new Bundle();
        HeaderControlBean headerControlBean = new HeaderControlBean(getScreenTitle(), HeaderOption.HIDE_HEADER);
        bundle.putParcelable(HeaderOption.HEADER_CONTROL, headerControlBean);
        return bundle;
    }
}
