package com.ta.finalexam.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ta.finalexam.Bean.HomeBean.HomeBean;

import java.util.ArrayList;
import java.util.List;

import vn.app.base.api.response.BaseResponse;

/**
 * Created by kooryy2 on 10/30/2016.
 */

public class HomeResponse extends BaseResponse {
    @SerializedName("data")
    @Expose
    public List<HomeBean> data = new ArrayList<HomeBean>();

}

