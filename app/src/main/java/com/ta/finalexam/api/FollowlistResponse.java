package com.ta.finalexam.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ta.finalexam.Bean.FollowlistBean.User;
import com.ta.finalexam.Bean.MemberBean;

import java.util.ArrayList;
import java.util.List;

import vn.app.base.api.response.BaseResponse;

/**
 * Created by Veteran Commander on 11/16/2016.
 * last fix : 5/12/2016 by TA
 */

public class FollowlistResponse extends BaseResponse {
    @SerializedName("data")
    @Expose
    public List<User> data = new ArrayList<User>();
}
