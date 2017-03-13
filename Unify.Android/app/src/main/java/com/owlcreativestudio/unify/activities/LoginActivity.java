package com.owlcreativestudio.unify.activities;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.ProfileTracker;
import com.facebook.login.widget.LoginButton;
import com.owlcreativestudio.unify.R;
import com.owlcreativestudio.unify.models.FacebookAccountState;
import com.owlcreativestudio.unify.services.FacebookService;
import com.owlcreativestudio.unify.helpers.ProgressHelper;
import com.owlcreativestudio.unify.tasks.UserLoginTask;

import java.util.Arrays;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    private static final int REQUEST_CAMERA_SERVICE = 0;
    private static final int REQUEST_FINE_LOCATION = 1;

    private View masterLayout;
    private ProgressHelper progressHelper;
    private CallbackManager callbackManager;
    private FacebookAccountState facebookAccountState;
    private AccessTokenTracker facebookAccessTokenTracker;
    private ProfileTracker facebookProfileTracker;

    public void signIn(View view) {
        EditText emailEditText = (EditText) findViewById(R.id.email);
        EditText passwordEditText = (EditText) findViewById(R.id.password);

        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        Intent intent = new Intent(this, ARActivity.class);

        progressHelper.showProgress(true);
        UserLoginTask loginTask = new UserLoginTask(this, progressHelper, email, password, intent);
        loginTask.execute();
    }

    public void register(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        checkFacebookLoginToken();

        setupFacebookLogin();

        View loginLayout = findViewById(R.id.login_layout);
        View loginProgress = findViewById(R.id.login_progress);
        masterLayout = findViewById(R.id.master_layout);


        progressHelper = new ProgressHelper(loginProgress, loginLayout);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!hasHardwareCamera(this)) {
            Log.d("ERROR", "No camera detected");
        }
        if (!hasCameraAccess()) {
            Log.d("ERROR", "Camera access required");
        }
        if (!hasGPSAccess()) {
            Log.d("ERROR", "Location access required");
        }
        //todo display a warning and close the application
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    private void setupFacebookLogin() {
        callbackManager = CallbackManager.Factory.create();
        LoginButton facebookLoginButton = (LoginButton) findViewById(R.id.facebook_sign_in_button);
        facebookLoginButton.setReadPermissions(Arrays.asList("public_profile", "user_friends", "email"));
        facebookLoginButton.registerCallback(callbackManager, FacebookService.getFacebookLoginCallback());
    }

    /* CHECK EXISTECE OF ACCESS TOKENS */
    private void checkFacebookLoginToken() {
        AccessToken facebookToken = AccessToken.getCurrentAccessToken();
        if (null != facebookToken && !facebookToken.isExpired()) {
//            advanceToARActivity();
        }
    }

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

    private void advanceToARActivity() {
        startActivity(new Intent(this, ARActivity.class));
        finish();
    }
}