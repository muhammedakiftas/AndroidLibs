package com.sinifdefterimpro.helper;

import android.os.Parcel;
import android.os.Parcelable;

public class CourseInfo implements Parcelable {

    private String id;
    private String ad;

    public CourseInfo(String id, String ad) {
        this.id = id;
        this.ad = ad;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public CourseInfo(Parcel in){
        id = in.readString();
        ad = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(ad);
    }

    public static final Creator<CourseInfo> CREATOR = new Creator<CourseInfo>() {
        @Override
        public CourseInfo createFromParcel(Parcel in) {
            return new CourseInfo(in);
        }

        @Override
        public CourseInfo[] newArray(int size) {
            return new CourseInfo[size];
        }
    };

}