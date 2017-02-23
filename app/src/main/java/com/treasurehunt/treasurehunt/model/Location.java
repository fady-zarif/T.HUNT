package com.treasurehunt.treasurehunt.model;

/**
 * Created by foda_ on 2017-02-02.
 */

public class Location {
    private double latitude;
    private double longitude;

    public Location() {
    }

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }


    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
