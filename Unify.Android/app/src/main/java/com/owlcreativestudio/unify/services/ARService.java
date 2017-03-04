package com.owlcreativestudio.unify.services;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.owlcreativestudio.unify.interfaces.ARStateGetSetter;
import com.owlcreativestudio.unify.interfaces.AdjacentPeopleSetter;
import com.owlcreativestudio.unify.models.ARState;
import com.owlcreativestudio.unify.models.AdjacentPerson;
import com.owlcreativestudio.unify.models.UnifyLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ARService implements ARStateGetSetter, AdjacentPeopleSetter {
    private final int EARTH_RADIUS = 6371;
    private final double MAXIMUM_DISTANCE;
    private final double CAMERA_ANGLE;
    private final FrameLayout LAYOUT;
    private final Context CONTEXT;

    private List<AdjacentPerson> adjacentPeople = new ArrayList<>();
    private HashMap<String, TextView> adjacentObjects = new HashMap<>();
    private ARState arState;

    public ARService(Context context, FrameLayout layout, double cameraAngle, double maximumDistance) {
        CONTEXT = context;
        LAYOUT = layout;
        CAMERA_ANGLE = cameraAngle;
        MAXIMUM_DISTANCE = maximumDistance;
    }

    public ARState getArState() {
        return arState;
    }

    public void setArState(ARState arState) {
        arState.setXyRadians(roundAngle(arState.getXyRadians()));
        if (!this.arState.equals(arState)) {
            this.arState = arState;
            processScene();
        }
    }

    public void setAdjacentPeople(List<AdjacentPerson> adjacentPeople) {
        HashMap<String, TextView> adjacentObjects = new HashMap<>();
        for (AdjacentPerson adjacentPerson : adjacentPeople) {
            adjacentObjects.put(adjacentPerson.getId(), getAdjacentObject(adjacentPerson));
        }

        this.adjacentPeople = adjacentPeople;
        this.adjacentObjects = adjacentObjects;

        processScene();
    }

    private TextView getAdjacentObject(AdjacentPerson adjacentPerson) {
        double distance = getDistance(arState.getLocation(), adjacentPerson.getLocation());
        int iconSize = (int) (distance / MAXIMUM_DISTANCE) * arState.getIconSize();

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(iconSize, iconSize);

        TextView textView = new TextView(CONTEXT);
        textView.setText(adjacentPerson.getName());
        textView.setLayoutParams(layoutParams);

        return textView;
    }

    //Distance in meters
    private double getDistance(UnifyLocation sourceLocation, UnifyLocation targetLocation) {
        double latitudeDistance = Math.toRadians(targetLocation.getLatitude() - sourceLocation.getLatitude());
        double longitudeDistance = Math.toRadians(targetLocation.getLongitude() - sourceLocation.getLongitude());
        double height = sourceLocation.getElevation() - targetLocation.getElevation();

        Double a = Math.sin(latitudeDistance / 2) * Math.sin(latitudeDistance / 2)
                + Math.cos(Math.toRadians(sourceLocation.getLatitude())) * Math.cos(Math.toRadians(targetLocation.getLatitude()))
                * Math.sin(longitudeDistance / 2) * Math.sin(longitudeDistance / 2);

        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = EARTH_RADIUS * c * 1000;
        distance = Math.pow(distance, 2) + Math.pow(height, 2);
        distance = Math.sqrt(distance);

        return distance;
    }

    private void processScene() {
        //todo implement
    }

    private double roundAngle(double val) {
        return Math.round(val * 10.0) / 10.0;
    }


//        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(200, 200);
//        layoutParams.setMargins(200, 200, 0, 0);
//
//        ImageButton iButton = new ImageButton(this);
//        iButton.setLayoutParams(layoutParams);
//        contentLayout.addView(iButton);
//
//        new DownloadImageTask(iButton).execute("https://www.linkedin.com/mpr/mpr/p/4/005/029/3f0/2d6a311.jpg");
}
