package com.ta.finalexam.api.Request;

import com.android.volley.Request;
import com.ta.finalexam.Constant.ApiConstance;

import java.util.HashMap;
import java.util.Map;

import vn.app.base.api.response.BaseResponse;
import vn.app.base.api.volley.core.ObjectApiRequest;
import vn.app.base.constant.ApiParam;
import vn.app.base.util.SharedPrefUtils;

/**
 * Created by kooryy2 on 11/18/2016.
 */

public class FavouritesRequest extends ObjectApiRequest<BaseResponse> {
    String imageId;
    int isFavourite;

    public FavouritesRequest(String imageId, int isFavourite) {
        this.imageId = imageId;
        this.isFavourite = isFavourite;
    }

    @Override
    public boolean isRequiredAuthorization() {
        return false;
    }

    @Override
    public String getRequestURL() {
        return ApiConstance.URL_FAVOURITES;
    }

    @Override
    public boolean isRequiredAccessToken() {
        return false;
    }

    @Override
    public Map<String, String> getRequestParams() {
        Map<String, String> param = new HashMap<>();
        param.put(ApiConstance.IMAGEID, imageId);
        param.put(ApiConstance.FAVOURITE_STATUS, String.valueOf(isFavourite));
        return param;
    }

    @Override
    public Map<String, String> getRequestHeaders() {
        Map<String,String> newHeader = new HashMap<>();
        newHeader.put(ApiParam.TOKEN, SharedPrefUtils.getAccessToken());
        return newHeader;
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
