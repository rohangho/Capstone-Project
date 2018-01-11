package com.example.android.contactshare;

/**
 * Created by ROHAN on 11-01-2018.
 */

public class CustomClassForAssignment {

    String message;
    String authur;

    CustomClassForAssignment()
    {

    }
    CustomClassForAssignment(String message,String authur)
    {
        this.message=message;
        this.authur=authur;
    }

    public String getMessage() {
        return message;
    }

    public String getAuthur() {
        return authur;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setAuthur(String authur) {
        this.authur = authur;
    }
}
