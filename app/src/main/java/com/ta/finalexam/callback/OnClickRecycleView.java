package com.ta.finalexam.callback;

import com.ta.finalexam.Bean.HomeBean.HomeBean;

/**
 * Created by kooryy2 on 11/16/2016.
 */

public interface OnClickRecycleView {
   void onFollowResponse(String userId , int status);
   void onFavouriteResponse(String imageId , int status);
}
