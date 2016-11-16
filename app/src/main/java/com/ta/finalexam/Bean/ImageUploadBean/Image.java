package com.ta.finalexam.Bean.ImageUploadBean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kooryy2 on 11/1/2016.
 */

public class Image {
    @SerializedName("public_id")
    @Expose
    public String publicId;
    @SerializedName("version")
    @Expose
    public Integer version;
    @SerializedName("width")
    @Expose
    public Integer width;
    @SerializedName("height")
    @Expose
    public Integer height;
    @SerializedName("format")
    @Expose
    public String format;
    @SerializedName("resource_type")
    @Expose
    public String resourceType;
    @SerializedName("created_at")
    @Expose
    public Integer createdAt;
    @SerializedName("bytes")
    @Expose
    public Integer bytes;
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("secure_url")
    @Expose
    public String secureUrl;
    @SerializedName("user_id")
    @Expose
    public String userId;
    @SerializedName("caption")
    @Expose
    public String caption;
    @SerializedName("location")
    @Expose
    public String location;
    @SerializedName("lat")
    @Expose
    public String lat;
    @SerializedName("long")
    @Expose
    public String _long;
    @SerializedName("hashtag")
    @Expose
    public List<String> hashtag = new ArrayList<String>();
    @SerializedName("_id")
    @Expose
    public String id;
    @SerializedName("is_favourite")
    @Expose
    public Boolean isFavourite;

}

