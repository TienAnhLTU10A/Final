package com.ta.finalexam.api;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ta.finalexam.Bean.UserBean;

import vn.app.base.api.response.BaseResponse;

/**
 * Created by 3543 on 10/21/2016.
 */

public class LoginResponse extends BaseResponse {
    @SerializedName("data")
    @Expose
    public UserBean data;
}
