package com.ta.finalexam.Ulities.manager;

import com.google.gson.Gson;
import com.ta.finalexam.Constant.ApiConstance;

import vn.app.base.util.SharedPrefUtils;

/**
 * Created by kooryy2 on 11/10/2016.
 */

public class UserManager {
    private static Gson gson = new Gson();
    public static final String USER_DATA = "USER_DATA";

    public static void clearUserData() {
        SharedPrefUtils.removeKey(USER_DATA);
        SharedPrefUtils.removeKey(ApiConstance.TOKEN);
    }
}
