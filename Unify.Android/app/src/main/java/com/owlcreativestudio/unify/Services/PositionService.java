package com.owlcreativestudio.unify.Services;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;

import com.owlcreativestudio.unify.Helpers.UnifySensorEventListener;

public class PositionService {
    private SensorManager sensorManager;
    private UnifySensorEventListener positionListener;

    public PositionService(Context context) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
    }

    public void startPositionSensors() {
        positionListener = new UnifySensorEventListener();

        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor magnetic = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        sensorManager.registerListener(positionListener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(positionListener, magnetic, SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI);
    }

    public void stopListening() {
        sensorManager.unregisterListener(positionListener);
    }
}
