package com.ta.finalexam.api.Request;

import com.android.volley.Request;
import com.ta.finalexam.Constant.ApiConstance;

import java.util.HashMap;
import java.util.Map;

import vn.app.base.api.response.BaseResponse;
import vn.app.base.api.volley.core.ObjectApiRequest;
import vn.app.base.util.SharedPrefUtils;

/**
 * Created by Veteran Commander on 11/24/2016.
 */

public class DeleteRequest extends ObjectApiRequest<BaseResponse> {

    String imageId;

    public DeleteRequest(String imageId) {
        this.imageId = imageId;
    }

    @Override
    public boolean isRequiredAuthorization() {
        return false;
    }

    @Override
    public String getRequestURL() {
        return ApiConstance.URLDELETE;
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
        params.put(ApiConstance.IMAGEID,imageId);
        return params;
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
