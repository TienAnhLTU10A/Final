package com.ta.finalexam.Bean.ImageUploadBean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ta.finalexam.Bean.HomeBean.User;

/**
 * Created by kooryy2 on 11/1/2016.
 */

public class ImageUpload {
    @SerializedName("user")
    @Expose
    public User user;
    @SerializedName("image")
    @Expose
    public Image image;
}
