package com.owlcreativestudio.unify.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.linkedin.platform.LISessionManager;
import com.owlcreativestudio.unify.R;
import com.owlcreativestudio.unify.helpers.ProgressHelper;
import com.owlcreativestudio.unify.services.LinkedInService;
import com.owlcreativestudio.unify.services.TwitterService;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

public class SettingsActivity extends AppCompatActivity {
    private LinkedInService linkedInService;
    private ProgressHelper progressHelper;
    private TwitterLoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        View loginProgress = findViewById(R.id.progress_circle);
        View controlsLayout = findViewById(R.id.controls_layout);
        progressHelper = new ProgressHelper(loginProgress, controlsLayout);
        this.linkedInService = new LinkedInService(this, progressHelper);
        TwitterService twitterService = new TwitterService(this, progressHelper);

        loginButton = (TwitterLoginButton) findViewById(R.id.connect_with_twitter);
        loginButton.setCallback(twitterService.getCallback());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        LISessionManager.getInstance(getApplicationContext()).onActivityResult(this, requestCode, resultCode, data);
        loginButton.onActivityResult(requestCode, resultCode, data);

    }

    public void connectWithLinkedIn(View view) {
        progressHelper.showProgress(true);
        linkedInService.connectWithLinkedIn(getApplicationContext());
    }

}
