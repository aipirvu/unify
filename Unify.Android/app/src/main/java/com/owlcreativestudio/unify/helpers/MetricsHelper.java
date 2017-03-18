package com.owlcreativestudio.unify.helpers;

import android.content.Context;
import android.util.DisplayMetrics;

public class MetricsHelper {
    public static int getPixels(int dp, Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        float pixels = metrics.density * dp;
        return (int) (pixels + 0.5f);
    }
}
