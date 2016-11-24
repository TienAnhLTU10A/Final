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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.user, flags);
        dest.writeParcelable(this.image, flags);
    }

    public HomeBean() {
    }

    protected HomeBean(Parcel in) {
        this.user = in.readParcelable(User.class.getClassLoader());
        this.image = in.readParcelable(Image.class.getClassLoader());
    }

    public static final Parcelable.Creator<HomeBean> CREATOR = new Parcelable.Creator<HomeBean>() {
        @Override
        public HomeBean createFromParcel(Parcel source) {
            return new HomeBean(source);
        }

        @Override
        public HomeBean[] newArray(int size) {
            return new HomeBean[size];
        }
    };
}
