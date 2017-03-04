package com.owlcreativestudio.unify.services;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.owlcreativestudio.unify.helpers.DownloadImageTask;
import com.owlcreativestudio.unify.interfaces.ARStateGetSetter;
import com.owlcreativestudio.unify.interfaces.AdjacentPeopleSetter;
import com.owlcreativestudio.unify.models.ARState;
import com.owlcreativestudio.unify.models.AdjacentPerson;
import com.owlcreativestudio.unify.models.UnifyLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ARService implements ARStateGetSetter, AdjacentPeopleSetter {
    private final double MAXIMUM_DISTANCE;
    private final FrameLayout LAYOUT;
    private final Context CONTEXT;

    private List<AdjacentPerson> adjacentPeople = new ArrayList<>();
    private HashMap<String, ImageView> adjacentObjects = new HashMap<>();
    private ARState arState;

    private TextView logView;

    public ARService(Context context, FrameLayout layout, double maximumDistance) {
        CONTEXT = context;
        LAYOUT = layout;
        MAXIMUM_DISTANCE = maximumDistance;

        arState = new ARState();
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
        HashMap<String, ImageView> adjacentObjects = new HashMap<>();
        for (AdjacentPerson adjacentPerson : adjacentPeople) {
            adjacentObjects.put(adjacentPerson.getId(), getAdjacentObject(adjacentPerson));
        }

        this.adjacentPeople = adjacentPeople;
        this.adjacentObjects = adjacentObjects;
    }

    private ImageView getAdjacentObject(AdjacentPerson adjacentPerson) {
        ImageView imageView = new ImageView(CONTEXT);
        if (null != adjacentPerson.getImageUrl()) {
            new DownloadImageTask(imageView).execute(adjacentPerson.getImageUrl());
        }

        return imageView;
    }

    //Distance in meters
    private double getDistance(UnifyLocation sourceLocation, UnifyLocation targetLocation) {
        final int EARTH_RADIUS = 6371;
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

    //Relative positions in radians
    private double getPosition(UnifyLocation sourceLocation, UnifyLocation targetLocation) {
        double sourceX = sourceLocation.getLatitude();
        double sourceY = sourceLocation.getLongitude();
        double targetX = targetLocation.getLatitude();
        double targetY = targetLocation.getLongitude();

        double deltaX = targetX - sourceX;
        double deltaY = targetY - sourceY;

//        double degrees =  Math.atan2(deltaY, deltaX) * 180 / Math.PI;
        return Math.atan2(deltaY, deltaX);
    }

    private void processScene() {
        List<ImageView> viewsToShow = new ArrayList<>();
        List<ImageView> viewsToHide = new ArrayList<>();

        for (AdjacentPerson adjacentPerson : adjacentPeople) {
            ImageView imageView = adjacentObjects.get(adjacentPerson.getId());

            double position = getPosition(arState.getLocation(), adjacentPerson.getLocation());
            if (!isInView(position)) {
                viewsToHide.add(imageView);
                continue;
            }
            viewsToShow.add(imageView);

            double distance = getDistance(arState.getLocation(), adjacentPerson.getLocation());
            int iconSize = (int) (distance / MAXIMUM_DISTANCE) * arState.getIconSize();
            int leftMargin = getLeftMargin(position, iconSize);

            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(iconSize, iconSize);
            layoutParams.setMargins(leftMargin, 400, 0, 0);
            imageView.setLayoutParams(layoutParams);
        }

        for (ImageView imageView : viewsToShow) {
            imageView.setVisibility(View.VISIBLE);
        }
        for (ImageView imageView : viewsToHide) {
            imageView.setVisibility(View.GONE);
        }

        log();
    }

    private boolean isInView(double position) {
        double left = arState.getXyRadians() - arState.getCameraAngle() / 2;
        double right = arState.getXyRadians() + arState.getCameraAngle() / 2;
        //todo must check when the visible area is negative on the right and positive on the left and take that into consideration
        return left < position && position < right;
    }

    private int getLeftMargin(double position, int iconSize) {
        double left = arState.getXyRadians() - arState.getCameraAngle() / 2;
        double right = arState.getXyRadians() + arState.getCameraAngle() / 2;

        double leftRelativePosition = (position - left) / (right - left);
        return (int) (arState.getPreviewWidth() * leftRelativePosition - iconSize / 2);
    }

    private double roundAngle(double val) {
        return Math.round(val * 10.0) / 10.0;
    }

    private void log() {
        if (null == logView) {
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            logView = new TextView(CONTEXT);
            logView.setLayoutParams(layoutParams);
            logView.setBackgroundColor(Color.BLACK);
            logView.setTextColor(Color.WHITE);

            LAYOUT.addView(logView);
        }
        logView.setText(getLogMessage());
    }

    private String getLogMessage() {
        String logMessage = "";
        logMessage += "H:";
        logMessage += arState.getPreviewHeight();
        logMessage += "W:";
        logMessage += arState.getPreviewHeight();
        logMessage += "A:";
        logMessage += arState.getXyRadians();
        logMessage += "LAT:";
        logMessage += arState.getLocation().getLatitude();
        logMessage += "LON:";
        logMessage += arState.getLocation().getLongitude();
        logMessage += "ALT:";
        logMessage += arState.getLocation().getElevation();

        return logMessage;
    }
}
