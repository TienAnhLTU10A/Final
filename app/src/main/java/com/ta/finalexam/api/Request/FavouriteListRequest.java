package com.ta.finalexam.api.Request;

import com.android.volley.Request;
import com.ta.finalexam.Constant.ApiConstance;
import com.ta.finalexam.api.HomeResponse;

import java.util.HashMap;
import java.util.Map;

import vn.app.base.api.volley.core.ObjectApiRequest;
import vn.app.base.constant.ApiParam;
import vn.app.base.util.SharedPrefUtils;

/**
 * Created by kooryy2 on 12/3/2016.
 */

public class FavouriteListRequest extends ObjectApiRequest<HomeResponse> {

    String userId;

    public FavouriteListRequest(String userId) {
        this.userId = userId;
    }

    @Override
    public boolean isRequiredAuthorization() {
        return false;
    }

    @Override
    public String getRequestURL() {
        return ApiConstance.FAVOURITE_LIST;
    }

    @Override
    public boolean isRequiredAccessToken() {
        return false;
    }

    @Override
    public Map<String, String> getRequestParams() {
        Map<String,String> param = new HashMap<>();
        if (userId != null){
            param.put(ApiConstance.USERID , userId);
            return param;
        }else {
            return null;
        }
    }

    @Override
    public Map<String, String> getRequestHeaders() {
        Map<String,String> newHeader = new HashMap<>();
        newHeader.put(ApiParam.TOKEN, SharedPrefUtils.getAccessToken());
        return newHeader;
    }

    @Override
    public Class<HomeResponse> getResponseClass() {
        return HomeResponse.class;
    }

    @Override
    public int getMethod() {
        return Request.Method.POST;
    }
}
