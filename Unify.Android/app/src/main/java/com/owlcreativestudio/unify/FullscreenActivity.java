package com.owlcreativestudio.unify;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.owlcreativestudio.unify.Helpers.CameraPreview;
import com.owlcreativestudio.unify.Helpers.DownloadImageTask;
import com.owlcreativestudio.unify.Helpers.UnifyLocationListener;
import com.owlcreativestudio.unify.Services.CameraService;

import java.io.InputStream;

public class FullscreenActivity extends AppCompatActivity implements SensorEventListener {
    FrameLayout contentLayout;
    private boolean isVisible;
    private final float[] mAccelerometerReading = new float[3];
    private final float[] mMagnetometerReading = new float[3];
    private final float[] mOrientationAngles = new float[3];
    private final float[] mRotationMatrix = new float[9];
    private final Handler mHideHandler = new Handler();
    private FrameLayout cameraLayout;
    private SensorManager sensorManager;
    private static final boolean AUTO_HIDE = true;
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;
    private static final int UI_ANIMATION_DELAY = 300;
    private static final String TAG = "FullscreenActivity";
    private View masterLayout;
    private View controlsLayout;

    private CameraService cameraService;

    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            masterLayout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };

    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            controlsLayout.setVisibility(View.VISIBLE);
        }
    };
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };

    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);

        //layouts
        masterLayout = findViewById(R.id.master_layout);
        cameraLayout = (FrameLayout) findViewById(R.id.camera_layout);
        contentLayout = (FrameLayout) findViewById(R.id.content_layout);
        controlsLayout = findViewById(R.id.controls_layout);

        isVisible = true;

        //sensors
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        masterLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });

        findViewById(R.id.settings_button).setOnTouchListener(mDelayHideTouchListener);


        cameraService = new CameraService();

        //test section
        setARElements();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraService.releaseCamera();

        // Don't receive any more updates from either sensor.
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            cameraService.initializeCamera(this, cameraLayout);
        } catch (Exception ex) {
            Log.d(TAG, ex.getMessage());
        }

        try {
            startGPSTracking();
        } catch (Exception ex) {
            Log.d(TAG, ex.getMessage());
        }

        startPositionSensors();
    }

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

    public void updateOrientationAngles() {
        sensorManager.getRotationMatrix(mRotationMatrix, null, mAccelerometerReading, mMagnetometerReading);
        sensorManager.getOrientation(mRotationMatrix, mOrientationAngles);

//
//        String rotationMatrixLog = "matrix: "
//                + roundup(mRotationMatrix[0]) + roundup(mRotationMatrix[1]) + roundup(mRotationMatrix[2])
//                + roundup(mRotationMatrix[3]) + roundup(mRotationMatrix[4]) + roundup(mRotationMatrix[5])
//                + roundup(mRotationMatrix[6]) + roundup(mRotationMatrix[7]) + roundup(mRotationMatrix[8]);
//
//        Log.d(TAG, rotationMatrixLog);
//
//        String orientationLog = "orientation: " + roundup(mOrientationAngles[0]) + roundup(mOrientationAngles[1]) + roundup(mOrientationAngles[2]);
//
//        Log.d(TAG, orientationLog);

    }

    public void settings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    private void toggle() {
        if (isVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        controlsLayout.setVisibility(View.GONE);
        isVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        masterLayout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        isVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    private void startGPSTracking() throws Exception {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        UnifyLocationListener locationListener = new UnifyLocationListener();
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, locationListener);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 10, locationListener);
        } catch (SecurityException ex) {
            throw new Exception("Location permission required", ex);
        }
    }

    private void startPositionSensors() {
        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor magentic = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        sensorManager.registerListener(this, accelerometer,
                SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(this, magentic,
                SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI);
    }

    //utils
    private String roundup(float val) {
        return "\t|\t" + Math.round(val * 100.0) / 100.0;
    }

    private void setARElements() {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(200, 200);
        layoutParams.setMargins(200, 200, 0, 0);

        ImageButton iButton = new ImageButton(this);
        iButton.setLayoutParams(layoutParams);
        contentLayout.addView(iButton);

        new DownloadImageTask(iButton).execute("https://www.linkedin.com/mpr/mpr/p/4/005/029/3f0/2d6a311.jpg");
    }
}
