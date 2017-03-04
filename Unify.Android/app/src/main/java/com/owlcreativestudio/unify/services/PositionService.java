package com.owlcreativestudio.unify.services;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;

import com.owlcreativestudio.unify.interfaces.ARStateGetSetter;
import com.owlcreativestudio.unify.listeners.UnifySensorEventListener;

public class PositionService {
    private final ARStateGetSetter arStateGetSetter;

    private SensorManager sensorManager;
    private UnifySensorEventListener positionListener;

    public PositionService(Context context, ARStateGetSetter arStateGetSetter) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        this.arStateGetSetter = arStateGetSetter;
    }

    public void startPositionSensors() {
        positionListener = new UnifySensorEventListener(arStateGetSetter);

        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor magnetic = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        sensorManager.registerListener(positionListener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(positionListener, magnetic, SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI);
    }

    public void stopListening() {
        sensorManager.unregisterListener(positionListener);
    }
}
