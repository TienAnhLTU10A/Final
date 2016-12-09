package com.ta.finalexam.callback;

import com.ta.finalexam.Bean.HomeBean.HomeBean;

/**
 * Created by kooryy2 on 11/16/2016.
 */

public interface OnClickRecycleView {
   void onFollowResponse(String userId , int status);
   void onFavouriteResponse(String imageId , int status);
   void onGoToProfile(String userId);
   void onGoToDetail(HomeBean homeBean);
   void onMapClick(HomeBean homeBean);
   void onPhotoClick(HomeBean homeBean);
}
