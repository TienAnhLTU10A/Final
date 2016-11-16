package com.ta.finalexam.api.Request;

import android.support.v7.widget.SwitchCompat;

import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.ta.finalexam.Constant.ApiConstance;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import vn.app.base.api.response.BaseResponse;
import vn.app.base.api.volley.callback.SimpleRequestCallBack;
import vn.app.base.api.volley.core.UploadBinaryApiRequest;

/**
 * Created by kooryy2 on 10/31/2016.
 */

public class ImageUploadRequest extends UploadBinaryApiRequest<BaseResponse> {
    public String caption;
    public String location;
    public String lat;
    public String mlong;
    public String hashtag;
    public SimpleRequestCallBack simpleRequestCallBack;

    public ImageUploadRequest(String caption, String mlong, String lat, String location,
                              String hashtag, File image, SimpleRequestCallBack simpleRequestCallBack) {
        this.caption = caption;
        this.mlong = mlong;
        this.lat = lat;
        this.location = location;
        this.hashtag = hashtag;
        this.simpleRequestCallBack = simpleRequestCallBack;

        Map<String, File> fileMap = new HashMap<>();
        fileMap.put("image", image);
        setRequestFiles(fileMap);

    }


    @Override
    public String getRequestURL() {
        return ApiConstance.URL_UPLOAD;
    }

    @Override
    public boolean isRequiredAccessToken() {
        return false;
    }

    @Override
    public Map<String, String> getRequestParams() {
        Map<String, String> params = new HashMap<>();
        if (!caption.isEmpty()) {
            params.put(ApiConstance.CAPTION, caption);
        }
        if (!hashtag.isEmpty()) {
            params.put(ApiConstance.HASHTAG, hashtag);

        }
        params.put(ApiConstance.LAT, lat);
        params.put(ApiConstance.LONG, mlong);
        params.put(ApiConstance.LOCATION, location);
        return params;
    }

    @Override
    public Map<String, String> getRequestHeaders() {
        Map<String, String> header = new HashMap<>();
        header.put(ApiConstance.TOKEN, ApiConstance.TOKEN_CODE);
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

    @Override
    public void onRequestSuccess(BaseResponse response) {
        simpleRequestCallBack.onResponse(true, response.message);
    }

    @Override
    public void onRequestError(VolleyError error) {
        String message = (error == null || error.getMessage() == null || error.networkResponse == null) ? ApiConstance.UNKNOW_ERROR : error.getMessage();
        if (error instanceof NoConnectionError || error instanceof NetworkError || error instanceof TimeoutError) {
            message = ApiConstance.NO_CONNECTION_ERROR;
        }
        simpleRequestCallBack.onResponse(false, message);
    }
}
