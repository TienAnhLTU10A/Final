package com.ta.finalexam.Bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Veteran Commander on 11/16/2016.
 */

public class UserBean {
    @SerializedName("_id")
    @Expose
    public String id;
    @SerializedName("token")
    @Expose
    public String token;
    @SerializedName("username")
    @Expose
    public String username;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("avatar")
    @Expose
    public String avatar;
    @SerializedName("create_at")
    @Expose
    public long createAt;
}
