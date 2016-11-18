package com.ta.finalexam.api.Request;

import com.android.volley.Request;
import com.ta.finalexam.Constant.ApiConstance;
import com.ta.finalexam.api.CommentListResponse;

import java.util.HashMap;
import java.util.Map;

import vn.app.base.api.volley.core.ObjectApiRequest;

/**
 * Created by Veteran Commander on 11/18/2016.
 */

public class CommentListRequest extends ObjectApiRequest<CommentListResponse> {
    String imageID;

    public CommentListRequest(String imageID) {
        this.imageID = imageID;
    }

    @Override
    public boolean isRequiredAuthorization() {
        return false;
    }

    @Override
    public String getRequestURL() {
        return ApiConstance.URLCOMMENTLIST;
    }

    @Override
    public boolean isRequiredAccessToken() {
        return false;
    }

    @Override
    public Map<String, String> getRequestHeaders() {
        Map<String, String> header = new HashMap<>();
        header.put(ApiConstance.TOKEN, ApiConstance.TOKEN_CODE);
        return header;
    }

    @Override
    public Map<String, String> getRequestParams() {
        Map<String, String> params = new HashMap<>();
        params.put(ApiConstance.IMAGEID, imageID);
        return params;
    }

    @Override
    public Class<CommentListResponse> getResponseClass() {
        return CommentListResponse.class;
    }

    @Override
    public int getMethod() {
        return Request.Method.GET;
    }
}
