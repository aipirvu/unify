package com.owlcreativestudio.unify;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;

/**
 * Created by aipir on 31-Jan-17.
 */

public class UnifyCommonHelpers {
    public static void simulateWait() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }
    }
}
