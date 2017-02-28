package com.owlcreativestudio.unify.Services;

import android.content.Context;
import android.location.LocationManager;

import com.owlcreativestudio.unify.Helpers.UnifyLocationListener;

public class LocationService {
    public void startGPSTracking(Context context) throws Exception {
        LocationManager locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
        UnifyLocationListener locationListener = new UnifyLocationListener();
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, locationListener);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 10, locationListener);
        } catch (SecurityException ex) {
            throw new Exception("Location permission required", ex);
        }
    }
}
