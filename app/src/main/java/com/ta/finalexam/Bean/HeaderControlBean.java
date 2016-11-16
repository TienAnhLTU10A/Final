package com.ta.finalexam.Bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created on 7/21/2016.
 */
public class HeaderControlBean implements Parcelable {

    public String title;
    public Integer[] headerOptions;

    public HeaderControlBean(String title, Integer... headerOptions) {
        this.headerOptions = headerOptions;
        this.title = title;
    }

    public HeaderControlBean(String title) {
        this.title = title;
    }

    public void setHeaderOption(Integer... headerOptions) {
        this.headerOptions = headerOptions;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeArray(this.headerOptions);
    }

    protected HeaderControlBean(Parcel in) {
        this.title = in.readString();
        this.headerOptions = (Integer[]) in.readArray(Integer[].class.getClassLoader());
    }

    public static final Creator<HeaderControlBean> CREATOR = new Creator<HeaderControlBean>() {
        @Override
        public HeaderControlBean createFromParcel(Parcel source) {
            return new HeaderControlBean(source);
        }

        @Override
        public HeaderControlBean[] newArray(int size) {
            return new HeaderControlBean[size];
        }
    };
}
