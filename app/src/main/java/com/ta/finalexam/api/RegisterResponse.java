package com.ta.finalexam.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ta.finalexam.Bean.UserBean;

import vn.app.base.api.response.BaseResponse;

/**
 * Created by Veteran Commander on 11/21/2016.
 */

public class RegisterResponse extends BaseResponse {
    @SerializedName("data")
    @Expose
    public UserBean data;

}
