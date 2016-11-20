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
    public static final String TOKEN_CODE = "1fc4747d2677949478c144ab5324787c";
    public static final String TOKEN = "TOKEN";

    //Login + register
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String API_BASE_URL ="https://polar-plains-86888.herokuapp.com/";
    public static final String LOGIN = API_BASE_URL + "api/login";
    public static final String REGISTER = API_BASE_URL + "api/regist";

    public static final int CAMERA_REQUEST_CODE = 977;
    public static final int CAMERA_REQQUEST_CROP = 927;
    public static final String PHOTO_FILE_NAME = "photo.jpg";

    public static final int REQUEST_CODE_PICKPHOTO = 99;
    public static final int REQUEST_CODE_TAKEPHOTO= 100;
    public static final String AVATAR_PHOTO = "photo_avatar.jpg";

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

    //Tutorial
    public static final String URLTUT = "https://polar-plains-86888.herokuapp.com/api/tutorial";

    //Nearby
    public static final String URLNEARBY = "https://polar-plains-86888.herokuapp.com/api/nearby";
    public static final String LATNEAR = "lat";
    public static final String LONGNEAR = "long";
    //Detail
    public static final String URLCOMMENTLIST = "https://polar-plains-86888.herokuapp.com/api/commentlist";
    public static final String URLCOMMENT = "https://polar-plains-86888.herokuapp.com/api/comment";
    public static final String IMAGEID = "imageId";
    public static final String COMMENT = "comment";

}




