package com.ta.finalexam.api.Request;

import com.android.volley.Request;
import com.ta.finalexam.Bean.HomeBean.HomeBean;
import com.ta.finalexam.Constant.ApiConstance;

import java.util.HashMap;
import java.util.Map;

import vn.app.base.api.response.BaseResponse;
import vn.app.base.api.volley.core.ObjectApiRequest;
import vn.app.base.util.SharedPrefUtils;

/**
 * Created by kooryy2 on 11/16/2016.
 */

public class FollowRequest extends ObjectApiRequest<BaseResponse> {
    private String userID;
    private int isFollow ;

    public FollowRequest(String userID, int isFollow ) {
        this.userID = userID;
        this.isFollow = isFollow;
    }

    @Override
    public boolean isRequiredAuthorization() {
        return false;
    }

    @Override
    public String getRequestURL() {
        return ApiConstance.URL_FOLLOW;
    }

    @Override
    public boolean isRequiredAccessToken() {
        return false;
    }

    @Override
    public Map<String, String> getRequestParams() {
        Map<String,String> param = new HashMap<>();
        param.put(ApiConstance.USERID , userID );
        param.put(ApiConstance.FOLLOW_STATUS , String.valueOf(isFollow));
        return param;
    }

    @Override
    public Map<String, String> getRequestHeaders() {
        Map<String , String> header = new HashMap<>();
        header.put(ApiConstance.TOKEN, SharedPrefUtils.getAccessToken());
        return header;
    }

    @Override
    public Class<BaseResponse> getResponseClass() {
        return BaseResponse.class;
    }

    @Override
    public int getMethod() {
        return Request.Method.POST;
    }
}
