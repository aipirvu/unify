package com.owlcreativestudio.unify.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.linkedin.platform.APIHelper;
import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIApiError;
import com.linkedin.platform.errors.LIAuthError;
import com.linkedin.platform.listeners.ApiListener;
import com.linkedin.platform.listeners.ApiResponse;
import com.linkedin.platform.listeners.AuthListener;
import com.linkedin.platform.utils.Scope;
import com.owlcreativestudio.unify.R;
import com.owlcreativestudio.unify.helpers.ProgressHelper;
import com.owlcreativestudio.unify.models.LinkedInProfile;
import com.owlcreativestudio.unify.services.LinkedInService;

public class SettingsActivity extends AppCompatActivity {
    private LinkedInService linkedInService;
    private ProgressHelper progressHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        View loginProgress = findViewById(R.id.progress_circle);
        View controlsLayout = findViewById(R.id.controls_layout);
        progressHelper = new ProgressHelper(loginProgress, controlsLayout);
        this.linkedInService = new LinkedInService(this, progressHelper);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        LISessionManager.getInstance(getApplicationContext()).onActivityResult(this, requestCode, resultCode, data);
    }

    public void connectWithLinkedIn(View view) {
        progressHelper.showProgress(true);
        linkedInService.connectWithLinkedIn(getApplicationContext());
    }

}
