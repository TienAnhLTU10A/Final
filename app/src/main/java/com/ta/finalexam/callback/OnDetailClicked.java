package com.ta.finalexam.callback;

import com.ta.finalexam.Bean.HomeBean.HomeBean;

/**
 * Created by Veteran Commander on 11/25/2016.
 */

public interface OnDetailClicked {
    void onFollowDetailClick(HomeBean homeBean);
    void onFavouriteDetailClick(HomeBean homeBean);
}
