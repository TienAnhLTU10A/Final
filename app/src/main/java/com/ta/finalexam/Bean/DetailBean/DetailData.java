package com.ta.finalexam.Bean.DetailBean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ta.finalexam.Bean.MemberBean;

/**
 * Created by Veteran Commander on 11/18/2016.
 */

public class DetailData {
    @SerializedName("user")
    @Expose
    public MemberBean user;
    @SerializedName("comment")
    @Expose
    public String comment;
    @SerializedName("created_at")
    @Expose
    public Integer createdAt;

    public String username;

    public DetailData(String username, String comment) {
        this.username = username;
        this.comment = comment;
    }

}
