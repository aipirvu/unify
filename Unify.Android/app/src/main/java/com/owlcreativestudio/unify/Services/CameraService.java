package com.owlcreativestudio.unify.Services;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.FrameLayout;

import com.owlcreativestudio.unify.Helpers.CameraPreview;
import com.owlcreativestudio.unify.R;

public class CameraService {
    private Camera mCamera;
    private CameraPreview mCameraPreview;

    public void initializeCamera(Context context, FrameLayout previewLayout) throws Exception {
        mCamera = Camera.open();
        if (null == mCamera) {
            throw new Exception("Camera could not be accessed.");
        }

        mCamera.setDisplayOrientation(90);

        if (null == mCameraPreview) {
            mCameraPreview = new CameraPreview(context, mCamera);
            previewLayout.addView(mCameraPreview);
        } else {
            mCameraPreview.setCamera(mCamera);
            mCameraPreview.setVisibility(View.VISIBLE);
        }
    }

    public void releaseCamera() {
        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
        if (mCameraPreview != null) {
            mCameraPreview.setVisibility(View.GONE);
        }
    }
}
