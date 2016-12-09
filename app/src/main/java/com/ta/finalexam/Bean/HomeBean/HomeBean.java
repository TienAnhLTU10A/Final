package com.ta.finalexam.Bean.HomeBean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kooryy2 on 10/30/2016.
 */

public class HomeBean implements Parcelable {

    @SerializedName("user")
    @Expose
    public User user;
    @SerializedName("image")
    @Expose
    public Image image;

    protected HomeBean(Parcel in) {
        user = in.readParcelable(User.class.getClassLoader());
        image = in.readParcelable(Image.class.getClassLoader());
    }

    public static final Creator<HomeBean> CREATOR = new Creator<HomeBean>() {
        @Override
        public HomeBean createFromParcel(Parcel in) {
            return new HomeBean(in);
        }

        @Override
        public HomeBean[] newArray(int size) {
            return new HomeBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(user, flags);
        dest.writeParcelable(image, flags);
    }
}
