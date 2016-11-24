package com.ta.finalexam.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ta.finalexam.Bean.UpdateProfileBean;

import vn.app.base.api.response.BaseResponse;

/**
 * Created by kooryy2 on 11/22/2016.
 */

public class UpdateProfileResponse  extends BaseResponse{
    @SerializedName("data")
    @Expose
    public UpdateProfileBean data;

}
