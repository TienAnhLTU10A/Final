package com.ta.finalexam.api.Request;

import com.android.volley.Request;
import com.ta.finalexam.Constant.ApiConstance;
import com.ta.finalexam.api.NearbyResponse;

import java.util.HashMap;
import java.util.Map;

import vn.app.base.api.volley.core.ObjectApiRequest;
import vn.app.base.util.SharedPrefUtils;

/**
 * Created by Veteran Commander on 11/18/2016.
 */

public class NearbyRequest extends ObjectApiRequest<NearbyResponse> {

    String lat;
    String mlong;

    public NearbyRequest(String lat, String mlong) {
        this.lat = lat;
        this.mlong = mlong;
    }

    @Override
    public boolean isRequiredAuthorization() {
        return false;
    }

    @Override
    public String getRequestURL() {
        return ApiConstance.URLNEARBY;
    }

    @Override
    public boolean isRequiredAccessToken() {
        return false;
    }

    @Override
    public Map<String, String> getRequestHeaders() {
        Map<String, String> header = new HashMap<>();
        header.put(ApiConstance.TOKEN, SharedPrefUtils.getAccessToken());
        return header;
    }

    @Override
    public Map<String, String> getRequestParams() {
        Map<String, String> params = new HashMap<>();
        params.put(ApiConstance.LATNEAR,lat);
        params.put(ApiConstance.LONGNEAR,mlong);
        return params;
    }

    @Override
    public Class<NearbyResponse> getResponseClass() {
        return NearbyResponse.class;
    }

    @Override
    public int getMethod() {
        return Request.Method.POST;
    }
}
