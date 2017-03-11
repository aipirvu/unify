package com.owlcreativestudio.unify.models;


public class ARState {
    private int previewHeight;
    private int previewWidth;
    private double cameraAngle;
    private double xyRadians;
    private UnifyLocation location;

    public ARState() {
        location = new UnifyLocation();
    }

    public boolean equals(ARState target) {
        return previewHeight == target.getPreviewHeight()
                && previewWidth == target.getPreviewWidth()
                && location.equals(target.getLocation())
                && xyRadians == target.getXyRadians()
                && cameraAngle == target.getCameraAngle();
    }

    public ARState clone() {
        ARState clone = new ARState();
        clone.setPreviewHeight(previewHeight);
        clone.setPreviewWidth(previewWidth);
        clone.setCameraAngle(cameraAngle);
        clone.setXyRadians(xyRadians);
        clone.setLocation(location.clone());
        return clone;
    }

    public int getPreviewHeight() {
        return previewHeight;
    }

    public void setPreviewHeight(int previewHeight) {
        this.previewHeight = previewHeight;
    }

    public int getPreviewWidth() {
        return previewWidth;
    }

    public void setPreviewWidth(int previewWidth) {
        this.previewWidth = previewWidth;
    }

    public UnifyLocation getLocation() {
        return location;
    }

    public void setLocation(UnifyLocation location) {
        this.location = location;
    }

    public double getXyRadians() {
        return xyRadians;
    }

    public void setXyRadians(double xyRadians) {
        this.xyRadians = xyRadians;
    }

    public double getCameraAngle() {
        return cameraAngle;
    }

    public void setCameraAngle(double cameraAngle) {
        this.cameraAngle = cameraAngle;
    }
}
