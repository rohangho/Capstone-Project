package com.example.android.contact_share;

/**
 * Created by ROHAN on 30-12-2017.
 */

public class CustomClass  {

    private String roll;
    private String name;
    private String bool;

    public CustomClass(String roll_number,String original_name,String bool)
    {
        this.roll=roll_number;
        this.name=original_name;
        this.bool=bool;
    }

    public String getRoll()
    {
        return roll;
    }
    public String getName()
    {
        return name;
    }

    public String getBool() {
        return bool;
    }
}
