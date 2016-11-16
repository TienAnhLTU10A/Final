package com.ta.finalexam.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ta.finalexam.Bean.TutorialBean.DataTut;

import vn.app.base.api.response.BaseResponse;

/**
 * Created by Veteran Commander on 10/21/2016.
 */

public class TutorialResponse extends BaseResponse {

    @SerializedName("data")
    @Expose
    public DataTut data;

}
