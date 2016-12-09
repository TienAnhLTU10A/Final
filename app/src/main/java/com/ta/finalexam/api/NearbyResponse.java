package com.ta.finalexam.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ta.finalexam.Bean.HomeBean.HomeBean;
import com.ta.finalexam.Bean.NearbyBean.DataNearby;

import java.util.ArrayList;
import java.util.List;

import vn.app.base.api.response.BaseResponse;

/**
 * Created by Veteran Commander on 11/18/2016.
 */

public class NearbyResponse extends BaseResponse {
    @SerializedName("data")
    @Expose
    public List<HomeBean> data = new ArrayList<>();
}
