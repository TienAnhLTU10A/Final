package com.ta.finalexam.Bean.DetailBean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ta.finalexam.Bean.HomeBean.HomeBean;
import com.ta.finalexam.Bean.HomeBean.Image;
import com.ta.finalexam.Bean.HomeBean.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Veteran Commander on 11/18/2016.
 */

public class ImageDetailBean  {
    @SerializedName("user")
    @Expose
    public User user;
    @SerializedName("image")
    @Expose
    public Image image;

    public ImageDetailBean(HomeBean homeBean) {
        this.user = homeBean.user;
        this.image = homeBean.image;
    }
}
