package com.ta.finalexam.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ta.finalexam.Bean.ProfileBean.UserBean.ProfileBean;

import vn.app.base.api.response.BaseResponse;

/**
 * Created by kooryy2 on 11/21/2016.
 */

public class ProfileResponse extends BaseResponse {

    @SerializedName("data")
    @Expose
    public ProfileBean profileBean;

}
