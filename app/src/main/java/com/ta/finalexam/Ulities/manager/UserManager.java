package com.ta.finalexam.Ulities.manager;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.ta.finalexam.Bean.UserBean;

import vn.app.base.constant.AppConstant;
import vn.app.base.util.SharedPrefUtils;
import vn.app.base.util.StringUtil;

/**
 * Created by 3543 on 10/21/2016.
 */

public class UserManager {
    private static Gson gson = new Gson();
    public static final String USER_DATA = "USER_DATA";

    public static void saveCurrentUser(UserBean userBean){
        String userData = gson.toJson(userBean,UserBean.class);
        SharedPrefUtils.putString(USER_DATA,userData);
    }

    public static UserBean getCurrentUser(){
        String userData = SharedPrefUtils.getString(USER_DATA,null);
        if (StringUtil.checkStringValid(userData)){
            try {
                return gson.fromJson(userData,UserBean.class);
            } catch (JsonSyntaxException e){
                return null;
            }
        } else {
            return null;
        }
    }

    public static void clearUserData(){
        SharedPrefUtils.removeKey(USER_DATA);
        SharedPrefUtils.removeKey(AppConstant.ACCESS_TOKEN);
    }
}
