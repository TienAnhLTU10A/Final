package com.ta.finalexam.Constant;

import vn.app.base.util.StringUtil;

/**
 * Created by kooryy2 on 10/31/2016.
 */

public class ApiConstance {
    public static final String TYPE = "TYPE";
    public static final String LAST_QUERY_TIMESTAMP = "LAST_QUERY_TIMESTAMP";
    public static final String NUM = "NUM";
    public static final int NUMS = 10;

    // API Url && TOKEN
    public static final String URL_HOME = "https://polar-plains-86888.herokuapp.com/api/home";
    public static final String URL_UPLOAD = "https://polar-plains-86888.herokuapp.com/api/image/upload";
    public static final String URL_FOLLOW = "https://polar-plains-86888.herokuapp.com/api/follow";
    public static final String TOKEN_CODE = "1fc4747d2677949478c144ab5324787c";
    public static final String TOKEN = "TOKEN";

    public static final int CAMERA_REQUEST_CODE = 977;
    public static final int CAMERA_REQQUEST_CROP = 927;
    public static final String PHOTO_FILE_NAME = "photo.jpg";

    //ImageUpload Time
    public static final long UPDATE_INTERVAL = 10 * 1000;  /* 10 secs */
    public static final long FASTEST_INTERVAL = 2000; /* 2 sec */

    //ImageUpload Request
    public static final String UNKNOW_ERROR = "UNKNOW_ERROR";
    public static final String NO_CONNECTION_ERROR = "NO_CONNECTION_ERROR";
    public static final String CAPTION = "CAPTION";
    public static final String LOCATION = "LOCATION";
    public static final String LAT = "LAT";
    public static final String LONG = "LONG";
    public static final String HASHTAG = "HASHTAG";
    public static final String IMAGE = "IMAGE";

}




