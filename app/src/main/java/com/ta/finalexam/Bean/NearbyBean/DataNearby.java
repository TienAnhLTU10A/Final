package com.ta.finalexam.Bean.NearbyBean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ta.finalexam.Bean.HomeBean.Image;
import com.ta.finalexam.Bean.MemberBean;

/**
 * Created by Veteran Commander on 11/18/2016.
 */

public class DataNearby {
    @SerializedName("user")
    @Expose
    public MemberBean user;
    @SerializedName("image")
    @Expose
    public Image image;
}
