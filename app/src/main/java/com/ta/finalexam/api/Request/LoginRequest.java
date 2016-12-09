package com.ta.finalexam.api.Request;

import com.android.volley.Request;
import com.ta.finalexam.Constant.ApiConstance;
import com.ta.finalexam.api.LoginResponse;

import java.util.HashMap;
import java.util.Map;

import vn.app.base.api.volley.core.ObjectApiRequest;

/**
 * Created by 3543 on 10/21/2016.
 */

public class LoginRequest extends ObjectApiRequest<LoginResponse> {
    String userId;
    String pass;

    public LoginRequest(String userId, String pass) {
        this.userId = userId;
        this.pass = pass;
    }


    @Override
    public boolean isRequiredAuthorization() {
        return false;
    }

    @Override
    public String getRequestURL() {
        return ApiConstance.LOGIN;
    }

    @Override
    public boolean isRequiredAccessToken() {
        return false;
    }

    @Override
    public Map<String, String> getRequestParams() {
        Map<String, String>params = new HashMap<>();
        params.put(ApiConstance.USERNAME,userId);
        params.put(ApiConstance.PASSWORD,pass);
        return params;
    }

    @Override
    public Class<LoginResponse> getResponseClass() {
        return LoginResponse.class;
    }

    @Override
    public int getMethod() {
        return Request.Method.POST;
    }
}
