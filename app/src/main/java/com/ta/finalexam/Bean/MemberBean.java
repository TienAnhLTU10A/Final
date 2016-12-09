package com.ta.finalexam.Bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Veteran Commander on 11/18/2016.
 */

public class MemberBean {
    @SerializedName("_id")
    @Expose
    public String id;
    @SerializedName("username")
    @Expose
    public String username;
    @SerializedName("avatar")
    @Expose
    public String avatar;
    @SerializedName("is_following")
    @Expose
    public Boolean isFollowing;
}
