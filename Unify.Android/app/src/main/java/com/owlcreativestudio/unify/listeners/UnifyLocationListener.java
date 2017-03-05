package com.owlcreativestudio.unify.listeners;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import com.owlcreativestudio.unify.interfaces.ARStateGetSetter;
import com.owlcreativestudio.unify.interfaces.LocationSender;
import com.owlcreativestudio.unify.models.ARState;
import com.owlcreativestudio.unify.models.UnifyLocation;

public class UnifyLocationListener implements LocationListener {
    private final ARStateGetSetter arStateGetSetter;
    private final LocationSender locationSender;

    public UnifyLocationListener(ARStateGetSetter arStateGetSetter, LocationSender locationSender) {
        this.arStateGetSetter = arStateGetSetter;
        this.locationSender = locationSender;
    }

    @Override
    public void onLocationChanged(Location location) {
        UnifyLocation unifyLocation = new UnifyLocation();
        unifyLocation.setLatitude(location.getLatitude());
        unifyLocation.setLongitude(location.getLongitude());
        unifyLocation.setElevation(location.getAltitude());

//        unifyLocation.setLatitude(0.1);
//        unifyLocation.setLongitude(0.1);
//        unifyLocation.setElevation(0.1);

        ARState arState = arStateGetSetter.getArState();
        arState.setLocation(unifyLocation);

        locationSender.send(unifyLocation);
        arStateGetSetter.setArState(arState);
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
