package com.owlcreativestudio.unify.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIAuthError;
import com.linkedin.platform.listeners.AuthListener;
import com.linkedin.platform.utils.Scope;
import com.owlcreativestudio.unify.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        LISessionManager.getInstance(getApplicationContext()).onActivityResult(this, requestCode, resultCode, data);
    }

    public void connectWithLinkedIn(View view){
        final Activity thisActivity = this;
        LISessionManager.getInstance(getApplicationContext()).init(thisActivity, buildScope(), new AuthListener() {
            @Override
            public void onAuthSuccess() {
                Log.d("LinkedIn", "auth success");
            }

            @Override
            public void onAuthError(LIAuthError error) {
                //todo notify user
            }
        }, true);
    }

    private static Scope buildScope() {
        return Scope.build(Scope.R_BASICPROFILE);
    }
}
