package com.ta.finalexam.Bean.FollowlistBean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ta.finalexam.Bean.MemberBean;

/**
 * Created by Veteran Commander on 12/5/2016.
 */

public class User {
    @SerializedName("user")
    @Expose
    public MemberBean user;
}
