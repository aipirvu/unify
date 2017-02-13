package com.owlcreativestudio.unify.Helpers;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

public class UnifyLocationListener implements LocationListener {
    private String TAG = "UnifyLocationListener";

    @Override
    public void onLocationChanged(Location location) {
        Log.v(TAG, "IN ON LOCATION CHANGE, lat=" + location.getLatitude() + ", lon=" + location.getLongitude());
        //todo send this to the server
        //todo update profiles based on this
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
}
