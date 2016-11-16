package com.ta.finalexam.api.Request;

import com.android.volley.Request;
import com.ta.finalexam.Bean.HomeBean.HomeBean;
import com.ta.finalexam.Constant.ApiConstance;
import com.ta.finalexam.api.HomeResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vn.app.base.api.volley.core.ObjectApiRequest;

/**
 * Created by kooryy2 on 10/31/2016.
 */

public class HomeRequest extends ObjectApiRequest<HomeResponse> {
    HomeBean homeBean;
    private int type;
    private long lastQuerryTime;
    private int num;

    public HomeRequest(int type) {
        this.type = type;
//        this.lastQuerryTime = lastQuerryTime;
//        this.num = num;
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
        params.put(ApiConstance.LAST_QUERY_TIMESTAMP, String.valueOf(lastQuerryTime));
        params.put(ApiConstance.NUM, String.valueOf(num));
        return params;
    }

    @Override
    public Map<String, String> getRequestHeaders() {
        Map<String, String> header = new HashMap<>();
        header.put(ApiConstance.TOKEN, ApiConstance.TOKEN_CODE);
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
