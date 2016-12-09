package com.ta.finalexam.api.Request;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.ta.finalexam.Constant.ApiConstance;
import com.ta.finalexam.api.UpdateProfileResponse;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import vn.app.base.api.volley.core.UploadBinaryApiRequest;
import vn.app.base.fragment.BaseFragment;
import vn.app.base.util.SharedPrefUtils;

/**
 * Created by kooryy2 on 11/22/2016.
 */

public class UpdateProfileRequest extends UploadBinaryApiRequest<UpdateProfileResponse> {
    Context mContext;

    public UpdateProfileRequest(Context mContext) {
        this.mContext = mContext;
    }

    public UpdateProfileRequest(File avatar) {

        Map<String, File> fileMap = new HashMap<>();
        fileMap.put(ApiConstance.AVATAR, avatar);
        setRequestFiles(fileMap);
    }

    @Override
    public String getRequestURL() {
        return ApiConstance.UPDATE_PROFILE;
    }

    @Override
    public boolean isRequiredAccessToken() {
        return false;
    }

    @Override
    public Map<String, String> getRequestParams() {
        return null;
    }

    @Override
    public Map<String, String> getRequestHeaders() {
        Map<String, String> header = new HashMap<>();
        header.put(ApiConstance.TOKEN, SharedPrefUtils.getAccessToken());
        return header;
    }

    @Override
    public Class<UpdateProfileResponse> getResponseClass() {
        return UpdateProfileResponse.class;
    }

    @Override
    public int getMethod() {
        return Request.Method.POST;
    }

    @Override
    public void onRequestSuccess(UpdateProfileResponse response) {
    }

    @Override
    public void onRequestError(VolleyError error) {

    }
}
