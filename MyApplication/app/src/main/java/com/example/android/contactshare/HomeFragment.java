package com.example.android.contactshare;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

import static android.support.v4.content.PermissionChecker.checkSelfPermission;

/**
 * Created by ROHAN on 27-10-2017.
 */

public class HomeFragment extends Fragment implements LocationListener {


    TextView temperature;
    Double latitude;
    Double longitude;
    LocationManager locationManager;
    Criteria criteria;
    String bstProvider;

    public HomeFragment() {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        temperature = (TextView) rootView.findViewById(R.id.temp);
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //  Toast.makeText(this,"hiiiiiiiii",Toast.LENGTH_LONG).show();
                getLocation();
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        } else {
            getLocation();
        }

        return rootView;
    }

    public static boolean isLocationEnabled(Context context) {
        return true;
    }

    protected void getLocation() {
        if (isLocationEnabled(getContext())) {
            locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
            criteria = new Criteria();
            bstProvider = String.valueOf(locationManager.getBestProvider(criteria, true)).toString();

            //You can still do this if you like, you might get lucky:
            locationManager.requestLocationUpdates(bstProvider, 0, 0, this);
            Location location = locationManager.getLastKnownLocation(bstProvider);

            if (location != null) {
                Log.e("TAG", "GPS is on");
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                Temp tem = new Temp();
                tem.execute("a");
                Toast.makeText(getContext(), "latitude:" + latitude + " longitude:" + longitude, Toast.LENGTH_SHORT).show();

            } else {
                //This is what you need:
                locationManager.requestLocationUpdates(bstProvider, 0, 0, this);

            }
        } else {
            Toast.makeText(getActivity(), "Permission denied", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onLocationChanged(Location location) {

        locationManager.removeUpdates(this);
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        Temp tem = new Temp();
        tem.execute("a");
        Toast.makeText(getContext(), "latitude:" + latitude + " longitude:" + longitude, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    public class Temp extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            String url = "http://samples.openweathermap.org/data/2.5/weather?lat=" + latitude.toString() + "&lon=" + longitude.toString() + "&appid=19c582ab57628fee373c6c741f78d8d8";

            Uri myuri = Uri.parse(url);
            URL requesturl = network.buildUrl(myuri);
            try {
                String jsonResponse = network
                        .getResponseFromHttpUrl(requesturl);
                String weather = null;
                try {
                    weather = TempJson.gettemperaturefromjson(getContext(), jsonResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return weather;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }


        }

        @Override
        protected void onPostExecute(String Datum) {
            if (Datum != null)
                temperature.setText(Datum + " K" + " Temperature");
        }

    }
}



