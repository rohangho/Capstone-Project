package com.example.android.contact_share;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ROHAN on 01-01-2018.
 */

public class TempJson {

    public static String gettemperaturefromjson(Context context, String JsonStr)
            throws JSONException {

        JSONObject forJson = new JSONObject(JsonStr);
        JSONObject getthedata=forJson.getJSONObject("main");
        String temperature=getthedata.getString("temp");
        return temperature;

    }

}
