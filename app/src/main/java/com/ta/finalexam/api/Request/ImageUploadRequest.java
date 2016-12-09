package com.ta.finalexam.api.Request;

import android.support.v4.app.FragmentActivity;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.ta.finalexam.Constant.ApiConstance;
import com.ta.finalexam.Fragment.FragmentHome;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import vn.app.base.api.response.BaseResponse;
import vn.app.base.api.volley.core.UploadBinaryApiRequest;
import vn.app.base.util.FragmentUtil;
import vn.app.base.util.SharedPrefUtils;

/**
 * Created by kooryy2 on 10/31/2016.
 */

public class ImageUploadRequest extends UploadBinaryApiRequest<BaseResponse> {
    public String caption;
    public String location;
    public String lat;
    public String mlong;
    public String hashtag;
    FragmentActivity mContext;

    public ImageUploadRequest(String caption, String mlong, String lat, String location,
                              String hashtag, File image, FragmentActivity mContext) {
        this.caption = caption;
        this.mlong = mlong;
        this.lat = lat;
        this.location = location;
        this.hashtag = hashtag;
        this.mContext = mContext;

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

    @Override
    public void onRequestSuccess(BaseResponse response) {
        FragmentUtil.pushFragmentWithAnimation(mContext, FragmentHome.newInstance(), null);
    }

    @Override
    public void onRequestError(VolleyError error) {

    }
}