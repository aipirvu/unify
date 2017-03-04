package com.owlcreativestudio.unify.services;

import android.content.Context;
import android.hardware.Camera;
import android.view.View;
import android.widget.FrameLayout;

import com.owlcreativestudio.unify.helpers.CameraPreview;
import com.owlcreativestudio.unify.interfaces.ARStateGetSetter;
import com.owlcreativestudio.unify.models.ARState;

public class CameraService {
    private final Context CONTEXT;
    private final FrameLayout LAYOUT;
    private final ARStateGetSetter arStateGetSetter;

    private Camera camera;
    private CameraPreview preview;

    public CameraService(Context context, FrameLayout layout, ARStateGetSetter arStateGetSetter) {
        CONTEXT = context;
        LAYOUT = layout;
        this.arStateGetSetter = arStateGetSetter;
    }

    public void initializeCamera() throws Exception {
        camera = Camera.open();
        if (null == camera) {
            throw new Exception("Camera could not be accessed.");
        }

        reportViewAngle();
        camera.setDisplayOrientation(90);

        if (null == preview) {
            preview = new CameraPreview(CONTEXT, camera, arStateGetSetter);
            LAYOUT.addView(preview);
        } else {
            preview.setCamera(camera);
            preview.setVisibility(View.VISIBLE);
        }
    }

    public void releaseCamera() {
        if (camera != null) {
            camera.release();
            camera = null;
        }
        if (preview != null) {
            preview.setVisibility(View.GONE);
        }
    }

    private void reportViewAngle() {
        Camera.Parameters parameters = camera.getParameters();
        double viewAngle = parameters.getHorizontalViewAngle();
        if (viewAngle > parameters.getVerticalViewAngle()) {
            viewAngle = parameters.getVerticalViewAngle();
        }

        ARState arState = arStateGetSetter.getArState();
        arState.setCameraAngle(Math.toRadians(viewAngle));
        arStateGetSetter.setArState(arState);
    }
}
