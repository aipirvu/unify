package com.owlcreativestudio.unify.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

import com.owlcreativestudio.unify.R;

public class SplashScreenActivity extends Activity {
    private final int SPLASH_SCREEN_DISPLAY_DURATION = 500;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                SplashScreenActivity.this.startActivity(mainIntent);
                SplashScreenActivity.this.finish();
            }
        }, SPLASH_SCREEN_DISPLAY_DURATION);
    }
}
