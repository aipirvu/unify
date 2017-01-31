package com.owlcreativestudio.unify.Helpers;

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
        simulateWait(2000);
    }

    public static void simulateWait(int miliseconds) {
        try {
            Thread.sleep(miliseconds);
        } catch (InterruptedException e) {
        }
    }
}
