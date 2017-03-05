package com.owlcreativestudio.unify.listeners;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
//import android.util.Log;

import com.owlcreativestudio.unify.interfaces.ARStateGetSetter;
import com.owlcreativestudio.unify.models.ARState;

public class UnifySensorEventListener implements SensorEventListener {
    private final ARStateGetSetter arStateGetSetter;
    private final float[] accelerometerReading = new float[3];
    private final float[] magnetometerReading = new float[3];

    public UnifySensorEventListener(ARStateGetSetter arStateGetSetter) {
        this.arStateGetSetter = arStateGetSetter;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        switch (event.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                System.arraycopy(event.values, 0, accelerometerReading, 0, accelerometerReading.length);
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                System.arraycopy(event.values, 0, magnetometerReading, 0, magnetometerReading.length);
                break;
        }

        float[] rotationMatrix = new float[9];
        float[] remappedRotationMatrix = new float[9];
        final float[] orientationAngles = new float[3];

        SensorManager.getRotationMatrix(rotationMatrix, null, accelerometerReading, magnetometerReading);
        SensorManager.remapCoordinateSystem(rotationMatrix, SensorManager.AXIS_X, SensorManager.AXIS_Z, remappedRotationMatrix);
        SensorManager.getOrientation(remappedRotationMatrix, orientationAngles);

        double xyRadians = orientationAngles[0];

        ARState arState = arStateGetSetter.getArState();
        arState.setXyRadians(xyRadians);
        arStateGetSetter.setArState(arState);

//        logArray(orientationAngles);
    }

//    private void logArray(float[] orientationAngles) {
//        String message = "";
//        for (float val : orientationAngles) {
//            message += roundAngle(val);
//            message += "\t";
//        }
//
//        Log.d("POSITION SENSOR", message);
//    }
//
//    private double roundAngle(double val) {
//        return Math.round(Math.toDegrees(val) * 100.0) / 100;
//    }
}
