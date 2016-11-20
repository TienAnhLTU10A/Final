package com.ta.finalexam.Bean.LoginBean;

import com.google.gson.annotations.SerializedName;
import com.ta.finalexam.Bean.UserBean;

/**
 * Created by 3543 on 10/21/2016.
 */

public class DataBean {
    @SerializedName("status")
    public Integer status;
    @SerializedName("message")
    public String message;
    @SerializedName("data")
    public UserBean data;
}
