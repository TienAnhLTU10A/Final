package com.ta.finalexam.api.Request;

import com.android.volley.Request;
import com.ta.finalexam.Constant.ApiConstance;
import com.ta.finalexam.api.ImageListResponse;

import java.util.HashMap;
import java.util.Map;

import vn.app.base.api.volley.core.ObjectApiRequest;
import vn.app.base.util.SharedPrefUtils;

/**
 * Created by kooryy2 on 11/21/2016.
 */

public class ImageListProfileUserRequest extends ObjectApiRequest<ImageListResponse> {
    String userId;

    public ImageListProfileUserRequest(String userId) {
        this.userId = userId;
    }

    @Override
    public boolean isRequiredAuthorization() {
        return false;
    }

    @Override
    public String getRequestURL() {
        return ApiConstance.IMAGE_LIST;
    }

    @Override
    public boolean isRequiredAccessToken() {
        return false;
    }

    @Override
    public Map<String, String> getRequestParams() {
        Map<String, String> param = new HashMap<>();
        if (userId != null) {
            param.put(ApiConstance.USERID, userId);
            return param;
        }else {
            return null;
        }
    }

    @Override
    public Map<String, String> getRequestHeaders() {
        Map<String, String> header = new HashMap<>();
        header.put(ApiConstance.TOKEN, SharedPrefUtils.getAccessToken());
        return header;
    }

    @Override
    public Class<ImageListResponse> getResponseClass() {
        return ImageListResponse.class;
    }

    @Override
    public int getMethod() {
        return Request.Method.POST;
    }
}
