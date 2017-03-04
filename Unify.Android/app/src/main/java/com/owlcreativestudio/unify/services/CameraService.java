package com.owlcreativestudio.unify.services;

import android.content.Context;
import android.hardware.Camera;
import android.view.View;
import android.widget.FrameLayout;

import com.owlcreativestudio.unify.helpers.CameraPreview;

public class CameraService {
    private final Context CONTEXT;
    private final FrameLayout LAYOUT;

    private Camera camera;
    private CameraPreview preview;

    public CameraService(Context context, FrameLayout layout) {
        CONTEXT = context;
        LAYOUT = layout;
    }

    public void initializeCamera() throws Exception {
        camera = Camera.open();
        if (null == camera) {
            throw new Exception("Camera could not be accessed.");
        }

        camera.setDisplayOrientation(90);

        if (null == preview) {
            preview = new CameraPreview(CONTEXT, camera);
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
}
