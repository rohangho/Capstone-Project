package com.example.android.contact_share;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ROHAN on 30-12-2017.
 */

public class CustomClass_forStudent implements Parcelable{

    private String roll;
    private String name;
    private String bool;

    public CustomClass_forStudent(String roll_number, String original_name, String bool) {
        this.roll = roll_number;
        this.name = original_name;
        this.bool = bool;
    }

    public CustomClass_forStudent(int customClass) {

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

    private CustomClass_forStudent(Parcel in) {
        roll=in.readString();
        name=in.readString();
        bool=in.readString();
    }

    public CustomClass_forStudent() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<CustomClass_forStudent> CREATOR
            = new Parcelable.Creator<CustomClass_forStudent>() {

        @Override
        public CustomClass_forStudent createFromParcel(Parcel in) {
            return new CustomClass_forStudent(in);
        }

        @Override
        public CustomClass_forStudent[] newArray(int size) {
            return new CustomClass_forStudent[size];
        }
    };

}