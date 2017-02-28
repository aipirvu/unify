package com.owlcreativestudio.unify.Helpers;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
//import android.util.Log;

public class UnifySensorEventListener implements SensorEventListener {
    private final float[] mAccelerometerReading = new float[3];
    private final float[] mMagnetometerReading = new float[3];
    private final float[] mOrientationAngles = new float[3];
    private final float[] mRotationMatrix = new float[9];

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
        // You must implement this callback in your code.
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            System.arraycopy(event.values, 0, mAccelerometerReading,
                    0, mAccelerometerReading.length);
        } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            System.arraycopy(event.values, 0, mMagnetometerReading,
                    0, mMagnetometerReading.length);
        }

        updateOrientationAngles();
    }

    private void updateOrientationAngles() {
        SensorManager.getRotationMatrix(mRotationMatrix, null, mAccelerometerReading, mMagnetometerReading);
        SensorManager.getOrientation(mRotationMatrix, mOrientationAngles);

//        String rotationMatrixLog = "matrix: "
//                + roundup(mRotationMatrix[0]) + roundup(mRotationMatrix[1]) + roundup(mRotationMatrix[2])
//                + roundup(mRotationMatrix[3]) + roundup(mRotationMatrix[4]) + roundup(mRotationMatrix[5])
//                + roundup(mRotationMatrix[6]) + roundup(mRotationMatrix[7]) + roundup(mRotationMatrix[8]);

//        Log.d(TAG, rotationMatrixLog);

//        String orientationLog = "orientation: " + roundup(mOrientationAngles[0]) + roundup(mOrientationAngles[1]) + roundup(mOrientationAngles[2]);

//        Log.d(TAG, orientationLog);
    }

//    private String roundup(float val) {
//        return "\t|\t" + Math.round(val * 100.0) / 100.0;
//    }
}
