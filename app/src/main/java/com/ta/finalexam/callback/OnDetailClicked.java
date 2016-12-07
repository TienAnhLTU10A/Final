package com.ta.finalexam.callback;

import com.ta.finalexam.Bean.HomeBean.HomeBean;

/**
 * Created by Veteran Commander on 11/25/2016.
 * last fix : 5/12/2016 by TA
 */

public interface OnDetailClicked {
    void onFollowDetailClick(HomeBean homeBean);
    void onFavouriteDetailClick(HomeBean homeBean);
    void onMapClick(HomeBean homeBean);
    void onAvatarClicked(HomeBean homeBean);
}
