package com.ta.finalexam.Bean.ImageListBean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ta.finalexam.Bean.HomeBean.Image;
import com.ta.finalexam.Bean.HomeBean.User;

/**
 * Created by kooryy2 on 11/21/2016.
 */

public class ImageListBean {
    @SerializedName("user")
    @Expose
    public User user;
    @SerializedName("image")
    @Expose
    public Image image;
}
