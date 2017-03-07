package com.owlcreativestudio.unify.helpers;

import android.view.View;

public class ProgressHelper {
    private final View loginProcessView;
    private final View loginLayout;

    public ProgressHelper(View loginProcessView, View loginLayout) {
        this.loginProcessView = loginProcessView;
        this.loginLayout = loginLayout;
    }

    public void showProgress(final boolean show) {
        loginProcessView.setVisibility(show ? View.VISIBLE : View.GONE);
        loginLayout.setVisibility(show ? View.GONE : View.VISIBLE);
    }
}
