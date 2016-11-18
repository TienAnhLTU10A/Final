package com.ta.finalexam.Bean.TutorialBean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ta.finalexam.Bean.UserBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Veteran Commander on 10/21/2016.
 */

public class DataTut {
    @SerializedName("tutorial")
    @Expose
    public List<TutorialBean> tutorial = new ArrayList<TutorialBean>();
    @SerializedName("user")
    @Expose
    public UserBean user;
}
