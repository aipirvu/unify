package com.owlcreativestudio.unify.activities;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.owlcreativestudio.unify.R;
import com.owlcreativestudio.unify.helpers.AlertHelper;
import com.owlcreativestudio.unify.models.AppLogin;
import com.owlcreativestudio.unify.services.FacebookService;
import com.owlcreativestudio.unify.helpers.ProgressHelper;
import com.owlcreativestudio.unify.tasks.UserAppLoginTask;

import java.util.Arrays;


public class LoginActivity extends AppCompatActivity {
    private static final int REQUEST_CAMERA_SERVICE = 0;
    private static final int REQUEST_FINE_LOCATION = 1;

    private View masterLayout;
    private ProgressHelper progressHelper;
    private CallbackManager callbackManager;
    private FacebookService facebookService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        View loginLayout = findViewById(R.id.login_layout);
        View loginProgress = findViewById(R.id.progress_circle);
        masterLayout = findViewById(R.id.master_layout);

        progressHelper = new ProgressHelper(loginProgress, loginLayout);

        this.facebookService = new FacebookService(this, progressHelper);
        setupFacebookLogin();
    }

    @Override
    protected void onResume() {
        super.onResume();
        DialogInterface.OnClickListener closeOnClick = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        };

        if (!hasHardwareCamera(this)) {
            AlertHelper.show(this, "Requirement unfulfilled", "The application requires the phone to have a camera.", closeOnClick);
        }
        if (!hasCameraAccess()) {
            AlertHelper.show(this, "Requirement unfulfilled", "The application requires access to phone's camera.", closeOnClick);
        }
        if (!hasGPSAccess()) {
            AlertHelper.show(this, "Requirement unfulfilled", "The application requires access to phone's location", closeOnClick);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void signIn(View view) {
        EditText emailEditText = (EditText) findViewById(R.id.email);
        EditText passwordEditText = (EditText) findViewById(R.id.password);

        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        AppLogin login = new AppLogin();
        login.setEmail(email);
        login.setPassword(password);

        Intent intent = new Intent(this, ARActivity.class);

        progressHelper.showProgress(true);
        UserAppLoginTask loginTask = new UserAppLoginTask(this, progressHelper, login, intent);
        loginTask.execute();
    }

    public void register(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    private void setupFacebookLogin() {
        callbackManager = CallbackManager.Factory.create();
        LoginButton facebookLoginButton = (LoginButton) findViewById(R.id.facebook_sign_in_button);
        facebookLoginButton.setReadPermissions(Arrays.asList("public_profile", "user_friends", "email"));
        facebookLoginButton.registerCallback(callbackManager, this.facebookService.getFacebookLoginCallback());
    }

    /* CHECK EXISTECE OF ACCESS TOKENS */
//    private void checkFacebookLoginToken() {
//        AccessToken facebookToken = AccessToken.getCurrentAccessToken();
//        if (null != facebookToken && !facebookToken.isExpired()) {
////            advanceToARActivity();
//        }
//    }

    /* CHECK ACCESS TO SERVICES */
    private boolean hasCameraAccess() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
            Snackbar.make(masterLayout, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_SERVICE);
                        }
                    });
        } else {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_SERVICE);
        }
        return false;
    }

    private boolean hasHardwareCamera(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    private boolean hasGPSAccess() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
            Snackbar.make(masterLayout, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_FINE_LOCATION);
                        }
                    });
        } else {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_FINE_LOCATION);
        }
        return false;
    }

//    private void advanceToARActivity() {
//        startActivity(new Intent(this, ARActivity.class));
//        finish();
//    }
}