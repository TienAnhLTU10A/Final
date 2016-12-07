package com.ta.finalexam.Constant;

/**
 * Created by kooryy2 on 10/31/2016.
 */

public class ApiConstance {
    public static final String TYPE = "type";
    public static final String LAST_QUERY_TIMESTAMP = "last_query_timestamp";
    public static final String NUM = "num";
    public static final int NUMS = 10;

    // API Url && TOKEN
    public static final String API_BASE_URL = "https://polar-plains-86888.herokuapp.com/";
    public static final String LOGIN = API_BASE_URL + "api/login";
    public static final String FAVOURITE_LIST = API_BASE_URL + "/api/favouritelist";
    public static final String REGISTER = API_BASE_URL + "api/regist";
    public static final String PROFILE_USER = API_BASE_URL + "api/profile";
    public static final String IMAGE_LIST = API_BASE_URL + "api/image/list";
    public static final String UPDATE_PROFILE = API_BASE_URL + "api/profileupdate";
    public static final String URL_HOME = API_BASE_URL + "api/home";
    public static final String URL_UPLOAD = API_BASE_URL + "api/image/upload";
    public static final String URL_FOLLOW = API_BASE_URL + "api/follow";
    public static final String URL_FAVOURITES = API_BASE_URL + "api/favourite";
    public static final String URLTUT = API_BASE_URL + "api/tutorial";
    public static final String TOKEN_CODE = "1fc4747d2677949478c144ab5324787c";
    public static final String TOKEN = "token";

    //Login + register
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final int CAMERA_REQUEST_CODE = 977;
    public static final int CAMERA_REQQUEST_CROP = 927;
    public static final int REQUEST_CODE_TAKEPHOTO = 100;
    public static final String PHOTO_FILE_NAME = "photo.jpg";
    public static final String AVATAR_PHOTO = "photo_avatar.jpg";

    //ImageUpload Request
    public static final String UNKNOW_ERROR = "UNKNOW_ERROR";
    public static final String NO_CONNECTION_ERROR = "NO_CONNECTION_ERROR";
    public static final String CAPTION = "caption";
    public static final String LOCATION = "location";
    public static final String LAT = "lat";
    public static final String LONG = "long";
    public static final String HASHTAG = "hashtag";

    //Nearby
    public static final String URLNEARBY = "https://polar-plains-86888.herokuapp.com/api/nearby";
    public static final String LATNEAR = "lat";
    public static final String LONGNEAR = "long";

    //Detail
    public static final String URLCOMMENTLIST = "https://polar-plains-86888.herokuapp.com/api/commentlist";
    public static final String URLCOMMENT = "https://polar-plains-86888.herokuapp.com/api/comment";
    public static final String IMAGEID = "imageId";
    public static final String COMMENT = "comment";
    public static final String USERID = "userId";

    //Follow & Favourite
    public static final String FAVOURITE_STATUS = "isFavourite";
    public static final String FOLLOW_STATUS = "isFollow";
    public static final int FOLLOW = 1;
    public static final int UN_FOLLOW = 0;
    public static final int FAVOURITE = 1;
    public static final int UN_FAVOURITE = 0;


    //UpdateProfile
    public static final String AVATAR = "avatar";

}




