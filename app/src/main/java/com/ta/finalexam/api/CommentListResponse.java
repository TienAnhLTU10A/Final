package com.ta.finalexam.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ta.finalexam.Bean.DetailBean.DetailData;

import java.util.ArrayList;
import java.util.List;

import vn.app.base.api.response.BaseResponse;

/**
 * Created by Veteran Commander on 11/18/2016.
 */

public class CommentListResponse extends BaseResponse {
    @SerializedName("data")
    @Expose
    public List<DetailData> data = new ArrayList<DetailData>();
}
