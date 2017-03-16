package com.owlcreativestudio.unify.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Handler;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.owlcreativestudio.unify.R;
import com.owlcreativestudio.unify.models.UserAccount;
import com.owlcreativestudio.unify.services.SharedPreferencesService;

import java.security.MessageDigest;

public class SplashScreenActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        final int SPLASH_SCREEN_DISPLAY_DURATION = 500;
        final Class redirectActivity;

        if (isLogged()) {
            redirectActivity = ARActivity.class;
        } else {
            redirectActivity = LoginActivity.class;
        }


        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.owlcreativestudio.unify", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (Exception ex) {
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(SplashScreenActivity.this, redirectActivity);
                SplashScreenActivity.this.startActivity(mainIntent);
                SplashScreenActivity.this.finish();
            }
        }, SPLASH_SCREEN_DISPLAY_DURATION);
    }


    private boolean isLogged() {
        SharedPreferencesService sharedPreferencesService = new SharedPreferencesService(this);
        sharedPreferencesService.clearUserAccount();
        UserAccount userAccount = sharedPreferencesService.getUserAccount();
        return null != userAccount && null != userAccount.getDisplayName() && !userAccount.getDisplayName().isEmpty();
    }
}
