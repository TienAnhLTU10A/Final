package com.ta.finalexam.api.Request;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Environment;

import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.ta.finalexam.Constant.ApiConstance;
import com.ta.finalexam.api.RegisterResponse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import vn.app.base.api.volley.callback.SimpleRequestCallBack;
import vn.app.base.api.volley.core.UploadBinaryApiRequest;
import vn.app.base.util.DebugLog;
import vn.app.base.util.SharedPrefUtils;

/**
 * Created by Veteran Commander on 11/21/2016.
 */

public class RegisterRequest extends UploadBinaryApiRequest<RegisterResponse> {

    String username;
    String password;
    String email;
    File imageAvatar;

    public SimpleRequestCallBack simpleRequestCallBack;

//    public RegisterRequest(String username, String password, String email,SimpleRequestCallBack simpleRequestCallBack) {
//        this.username = username;
//        this.password = password;
//        this.email = email;
//        this.simpleRequestCallBack = simpleRequestCallBack;
//
//
//    }

    public RegisterRequest(String username, String password, String email, File imageAvatar, SimpleRequestCallBack simpleRequestCallBack) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.imageAvatar = imageAvatar;
        this.simpleRequestCallBack = simpleRequestCallBack;

        Map<String, File> fileMap = new HashMap<>();
        fileMap.put("avatar", imageAvatar);
        setRequestFiles(fileMap);
    }

    @Override
    public String getRequestURL() {
        return ApiConstance.REGISTER;
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
        params.put("username",username);
        params.put("password",password);
        params.put("email",email);
        return params;
    }

    @Override
    public Class<RegisterResponse> getResponseClass() {
        return RegisterResponse.class;
    }

    @Override
    public int getMethod() {
        return Request.Method.POST;
    }

    @Override
    public void onRequestSuccess(RegisterResponse response) {
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
