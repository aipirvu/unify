package com.owlcreativestudio.unify.services;

import android.content.Context;
import android.location.LocationManager;

import com.owlcreativestudio.unify.interfaces.ARStateGetSetter;
import com.owlcreativestudio.unify.interfaces.LocationSender;
import com.owlcreativestudio.unify.listeners.UnifyLocationListener;
import com.owlcreativestudio.unify.models.UnifyLocation;

public class LocationService implements LocationSender {
    private final Context CONTEXT;
    private final ARStateGetSetter arStateGetSetter;

    public LocationService(Context context, ARStateGetSetter arStateGetSetter) {
        CONTEXT = context;
        this.arStateGetSetter = arStateGetSetter;
    }

    public void startGPSTracking() throws Exception {
        LocationManager locationManager = (LocationManager) CONTEXT.getSystemService(Context.LOCATION_SERVICE);
        UnifyLocationListener locationListener = new UnifyLocationListener(arStateGetSetter, this);
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, locationListener);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 10, locationListener);
        } catch (SecurityException ex) {
            throw new Exception("Location permission required", ex);
        }
    }

    public void send(UnifyLocation location) {
        //todo implement;
    }
}
