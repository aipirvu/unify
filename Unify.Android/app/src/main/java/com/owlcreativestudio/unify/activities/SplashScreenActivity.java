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

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import io.fabric.sdk.android.Fabric;
import java.security.MessageDigest;

public class SplashScreenActivity extends Activity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "7OIleWASvn1WmEf96ijAA1G54";
    private static final String TWITTER_SECRET = "zXZSoAAtEstNOL6VOwvQURE2ol3jpN62HW40YS4E1MnsjS6FHD";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
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
//        sharedPreferencesService.clearUserAccount();
        UserAccount userAccount = sharedPreferencesService.getUserAccount();
        return null != userAccount && null != userAccount.getDisplayName() && !userAccount.getDisplayName().isEmpty();
    }
}
