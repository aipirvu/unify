package com.owlcreativestudio.unify.models;

public class UnifyLocation {
    private double latitude;
    private double longitude;
    private double elevation;

    public boolean equals(UnifyLocation target) {
        return latitude == target.getLatitude() && longitude == target.getLongitude() && elevation == target.getElevation();
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getElevation() {
        return elevation;
    }

    public void setElevation(double elevation) {
        this.elevation = elevation;
    }
}
