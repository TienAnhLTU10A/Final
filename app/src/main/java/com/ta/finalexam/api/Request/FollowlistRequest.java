package com.ta.finalexam.api.Request;

import com.android.volley.Request;
import com.ta.finalexam.Constant.ApiConstance;
import com.ta.finalexam.api.FollowlistResponse;

import java.util.HashMap;
import java.util.Map;

import vn.app.base.api.volley.core.ObjectApiRequest;
import vn.app.base.constant.ApiParam;

/**
 * Created by Veteran Commander on 11/16/2016.
 */

public class FollowlistRequest extends ObjectApiRequest<FollowlistResponse> {

    public static final String URLFOLLOWLIST = "https://polar-plains-86888.herokuapp.com/api/followlist";
    @Override
    public boolean isRequiredAuthorization() {
        return true;
    }

    @Override
    public String getRequestURL() {
        return URLFOLLOWLIST;
    }

    @Override
    public boolean isRequiredAccessToken() {
        return false;
    }

    @Override
    public Map<String, String> getRequestHeaders() {
        Map<String,String> newHeader = new HashMap<>();
        newHeader.put(ApiParam.TOKEN, ApiConstance.TOKEN_CODE);
        return newHeader;
    }

    @Override
    public Map<String, String> getRequestParams() {
        return null;
    }

    @Override
    public Class<FollowlistResponse> getResponseClass() {
        return FollowlistResponse.class;
    }

    @Override
    public int getMethod() {
        return Request.Method.GET;
    }
}
