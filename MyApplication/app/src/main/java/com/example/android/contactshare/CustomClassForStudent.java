package com.example.android.contactshare;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ROHAN on 30-12-2017.
 */

public class CustomClassForStudent implements Parcelable {

    private String roll;
    private String name;
    private String bool;

    public CustomClassForStudent(String rollNumber, String originalName, String bool) {
        this.roll = rollNumber;
        this.name = originalName;
        this.bool = bool;
    }

    public CustomClassForStudent(int customClass) {

    }


    public String getRoll() {
        return roll;
    }

    public String getName() {
        return name;
    }

    public String getBool() {
        return bool;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {

        out.writeString(roll);
        out.writeString(name);
        out.writeString(bool);
    }

    private CustomClassForStudent(Parcel in) {
        roll = in.readString();
        name = in.readString();
        bool = in.readString();
    }

    public CustomClassForStudent() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<CustomClassForStudent> CREATOR
            = new Parcelable.Creator<CustomClassForStudent>() {

        @Override
        public CustomClassForStudent createFromParcel(Parcel in) {
            return new CustomClassForStudent(in);
        }

        @Override
        public CustomClassForStudent[] newArray(int size) {
            return new CustomClassForStudent[size];
        }
    };

}