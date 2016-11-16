package com.ta.finalexam.Bean.TutorialBean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * Created by Veteran Commander on 11/16/2016.
 */

public class TutorialBean {
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("image")
    @Expose
    public String image;
    @SerializedName("show_avatar")
    @Expose
    public Boolean showAvatar;
}
