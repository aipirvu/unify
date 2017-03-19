package com.owlcreativestudio.unify.services;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.Profile;
import com.owlcreativestudio.unify.helpers.MetricsHelper;
import com.owlcreativestudio.unify.tasks.DownloadImageTask;
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
    private final double MINIMUM_DISTANCE;
    private final double MINIMUM_ICON_SIZE_MODIFIER;
    private final double MAXIMUM_ICON_SIZE_MODIFIER;
    private final FrameLayout AR_LAYOUT;
    private final Context CONTEXT;
    private final ProfileDetailsService PROFILE_SERVICE;
    private ARState arState;

    private List<AdjacentPerson> adjacentPeople = new ArrayList<>();
    private HashMap<String, FrameLayout> adjacentObjects = new HashMap<>();

    private TextView logView;
    private double previousProcessedXY;

    public ARService(Context context, double minimumDistance, double maximumDistance, FrameLayout arLayout, ProfileDetailsService profileDetailsService) {
        CONTEXT = context;
        AR_LAYOUT = arLayout;
        MAXIMUM_DISTANCE = maximumDistance;
        MINIMUM_DISTANCE = minimumDistance;
        PROFILE_SERVICE = profileDetailsService;

        MINIMUM_ICON_SIZE_MODIFIER = 0.3;
        MAXIMUM_ICON_SIZE_MODIFIER = 1.0;
        arState = new ARState();
    }

    public ARState getArState() {
        return arState.clone();
    }

    public void setArState(ARState arState) {
        arState.setXyRadians(roundAngle(arState.getXyRadians()));
        if (!this.arState.equals(arState)) {
            this.arState = arState.clone();
            if (isPassingXYThreshold(arState.getXyRadians())) {
                processScene();
            }
        }
    }

    public void setAdjacentPeople(List<AdjacentPerson> adjacentPeople) {
        HashMap<String, FrameLayout> adjacentObjects = new HashMap<>();

        AR_LAYOUT.removeAllViews();

        for (AdjacentPerson adjacentPerson : adjacentPeople) {
            FrameLayout adjacentObject = getAdjacentObject(adjacentPerson);
            adjacentObjects.put(adjacentPerson.getId(), adjacentObject);
            AR_LAYOUT.addView(adjacentObject);
            adjacentObject.setVisibility(View.GONE);
        }

        this.adjacentPeople = adjacentPeople;
        this.adjacentObjects = adjacentObjects;
    }

    private FrameLayout getAdjacentObject(final AdjacentPerson adjacentPerson) {
        FrameLayout.LayoutParams pictureParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        ImageView pictureView = new ImageView(CONTEXT);
        pictureView.setScaleType(ImageView.ScaleType.FIT_XY);
        pictureView.setLayoutParams(pictureParams);
        if (null != adjacentPerson.getImageUrl()) {
            new DownloadImageTask(pictureView).execute(adjacentPerson.getImageUrl());
        }

        FrameLayout.LayoutParams textParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView nameView = new TextView(CONTEXT);
        nameView.setPadding(10, 10, 10, 10);
        nameView.setText(adjacentPerson.getDisplayName());
        nameView.setBackgroundColor(Color.BLACK);
        nameView.setTextColor(Color.WHITE);
        nameView.setLayoutParams(textParams);

        FrameLayout personLayout = new FrameLayout(CONTEXT);
        personLayout.setBackgroundColor(Color.GRAY);
        personLayout.addView(pictureView);
        personLayout.addView(nameView);
        personLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PROFILE_SERVICE.showDetails(adjacentPerson);
            }
        });
        return personLayout;
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
        if (arState.getLocation().getLatitude() == 0 && arState.getLocation().getLongitude() == 0) {
            log("Waiting for location...");
            return;
        }

        List<FrameLayout> viewsToShow = new ArrayList<>();
        List<FrameLayout> viewsToHide = new ArrayList<>();

        for (AdjacentPerson adjacentPerson : adjacentPeople) {
            FrameLayout personLayout = adjacentObjects.get(adjacentPerson.getId());

            double position = getPosition(arState.getLocation(), adjacentPerson.getLocation());
            boolean isInView = isInView(position);
            if (!isInView) {
                viewsToHide.add(personLayout);
                continue;
            }
            viewsToShow.add(personLayout);

            double distance = getDistance(arState.getLocation(), adjacentPerson.getLocation());
            double distanceModifier = getDistanceModifier(distance);
            int iconSize = getIconSize(distanceModifier);
            int leftMargin = getLeftMargin(position, iconSize);
            int topMargin = getTopMargin(iconSize, distanceModifier);

            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(iconSize, iconSize);
            layoutParams.setMargins(leftMargin, topMargin, 0, 0);
            personLayout.setLayoutParams(layoutParams);
        }

        for (FrameLayout personLayout : viewsToShow) {
            personLayout.setVisibility(View.VISIBLE);
        }
        for (FrameLayout personLayout : viewsToHide) {
            personLayout.setVisibility(View.GONE);
        }

        log();
    }

    private boolean isPassingXYThreshold(double current) {
        final double ANGLE_THRESHOLD = 0.2;
        boolean isPassing = Math.abs(previousProcessedXY - current) > ANGLE_THRESHOLD;
        if (isPassing) {
            previousProcessedXY = current;
        }
        return isPassing;
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

    private int getTopMargin(int iconSize, double distanceModifier) {
        int height = arState.getPreviewHeight();
        double minimMargin = height * 0.05;
        double maximumMargin = height - iconSize - 2 * minimMargin;
        return (int) (maximumMargin * distanceModifier);
    }

    private double roundAngle(double val) {
        return Math.round(val * 100.0) / 100.0;
    }

    private double getDistanceModifier(double distance) {
        if (distance > MAXIMUM_DISTANCE) {
            distance = MAXIMUM_DISTANCE;
        }
        if (distance < MINIMUM_DISTANCE) {
            distance = MINIMUM_DISTANCE;
        }

        return MINIMUM_DISTANCE / distance;
    }

    private int getIconSize(double iconSizeModifier) {
        if (iconSizeModifier > MAXIMUM_ICON_SIZE_MODIFIER) {
            iconSizeModifier = MAXIMUM_ICON_SIZE_MODIFIER;
        }
        if (iconSizeModifier < MINIMUM_ICON_SIZE_MODIFIER) {
            iconSizeModifier = MINIMUM_ICON_SIZE_MODIFIER;
        }
        return (int) (arState.getPreviewWidth() / 2 * iconSizeModifier);
    }

    private void log() {
        log(getLogMessage());
    }

    private void log(String message) {
        if (null == logView) {
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            logView = new TextView(CONTEXT);
            logView.setLayoutParams(layoutParams);
            logView.setBackgroundColor(Color.BLACK);
            logView.setTextColor(Color.WHITE);
            logView.setPadding(10, 10, 10, 10);

            AR_LAYOUT.addView(logView);
        }

        logView.setText(message);
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

    /* DETAILS LAYOUT */
}
