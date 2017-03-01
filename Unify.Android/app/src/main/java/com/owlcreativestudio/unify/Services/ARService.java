package com.owlcreativestudio.unify.Services;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.owlcreativestudio.unify.Models.AdjacentPerson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ARService {
    private Context context;
    private FrameLayout arLayout;
    private List<AdjacentPerson> adjacentPeople = new ArrayList<>();
    private HashMap<String, TextView> adjacentObjects = new HashMap<>();
    private int previewHeight;
    private int previewWidth;
    private int iconStandardSize;
    private double latitude;
    private double longitude;
    private double maximumDistance;
    private double radiansXY;
    private double cameraAngle;

    public ARService(Context context, FrameLayout arLayout, double maximumDistance) {
        this.context = context;
        this.arLayout = arLayout;
        this.maximumDistance = maximumDistance;
    }

    public void setPreviewSize(int previewHeight, int previewWidth) {
        this.previewHeight = previewHeight;
        this.previewWidth = previewWidth;
        this.iconStandardSize = previewWidth / 3;
    }

    public void setRadiansXY(float newRadiansXY) throws Exception {
        double roundedNewRadiansXY = roundAngle(newRadiansXY);
        if (roundedNewRadiansXY != radiansXY) {
            radiansXY = roundedNewRadiansXY;
            processScene();
        }
    }

    public void setLocation(double longitude, double latitude, List<AdjacentPerson> adjacentPeople) throws Exception {
        this.longitude = longitude;
        this.latitude = latitude;
        setAdjacentPeople(adjacentPeople);
    }

    public void setAdjacentPeople(List<AdjacentPerson> adjacentPeople) throws Exception {
        HashMap<String, TextView> adjacentObjects = new HashMap<>();
        for (AdjacentPerson adjacentPerson : adjacentPeople) {
            adjacentObjects.put(adjacentPerson.getId(), getAdjacentObject(adjacentPerson));
        }

        this.adjacentPeople = adjacentPeople;
        this.adjacentObjects = adjacentObjects;

        processScene();
    }

    private TextView getAdjacentObject(AdjacentPerson adjacentPerson) throws Exception {
        double distanceToObject = getDistance(adjacentPerson.getLongitude(), adjacentPerson.getLatitude());
        int iconSize = (int) (distanceToObject / maximumDistance) * iconStandardSize;

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(iconSize, iconSize);

        TextView textView = new TextView(context);
        textView.setText(adjacentPerson.getName());
        textView.setLayoutParams(layoutParams);

        return textView;
    }

    private double getDistance(double targetLongitude, double targetLatitude) throws Exception {
        throw new UnsupportedOperationException("Not implemented");

    }

    private void processScene() throws Exception {
        throw new UnsupportedOperationException("Not implemented");
    }

    private double roundAngle(float val) {
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
