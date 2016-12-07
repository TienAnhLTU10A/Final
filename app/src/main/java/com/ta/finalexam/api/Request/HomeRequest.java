package com.ta.finalexam.api.Request;

import com.android.volley.Request;
import com.ta.finalexam.Bean.HomeBean.HomeBean;
import com.ta.finalexam.Constant.ApiConstance;
import com.ta.finalexam.api.HomeResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vn.app.base.api.volley.core.ObjectApiRequest;
import vn.app.base.util.SharedPrefUtils;

/**
 * Created by kooryy2 on 10/31/2016.
 */

public class HomeRequest extends ObjectApiRequest<HomeResponse> {
    private String last_query_timestamp;
    private int type;
    private int num;

    public HomeRequest(int type , String last_query_timestamp, int num) {
        this.last_query_timestamp = last_query_timestamp;
        this.type = type;
        this.num = num;
    }

    @Override
    public boolean isRequiredAuthorization() {
        return false;
    }

    @Override
    public String getRequestURL() {
        return ApiConstance.URL_HOME;
    }

    @Override
    public boolean isRequiredAccessToken() {
        return false;
    }

    @Override
    public Map<String, String> getRequestParams() {
        Map<String, String> params = new HashMap<>();
        params.put(ApiConstance.TYPE, String.valueOf(type));
        params.put(ApiConstance.LAST_QUERY_TIMESTAMP , last_query_timestamp);
        params.put(ApiConstance.NUM, String.valueOf(num));
        return params;
    }

    @Override
    public Map<String, String> getRequestHeaders() {
        Map<String, String> header = new HashMap<>();
        header.put(ApiConstance.TOKEN, SharedPrefUtils.getAccessToken());
        return header;
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
