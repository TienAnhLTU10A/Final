package com.ta.finalexam.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ta.finalexam.Bean.ImageListBean;

import java.util.ArrayList;
import java.util.List;

import vn.app.base.api.response.BaseResponse;

/**
 * Created by kooryy2 on 11/21/2016.
 */

public class ImageListResponse extends BaseResponse {
    @SerializedName("data")
    @Expose
    public List<ImageListBean> data = new ArrayList<ImageListBean>();
}
