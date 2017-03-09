package com.owlcreativestudio.unify.tasks;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;

import com.owlcreativestudio.unify.helpers.HttpHelper;
import com.owlcreativestudio.unify.helpers.ProgressHelper;
import com.owlcreativestudio.unify.helpers.UrlHelper;
import com.owlcreativestudio.unify.models.Login;

public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
    private final String email;
    private final String password;
    private final Intent postExecuteSuccessIntent;
    private final ProgressHelper progressHelper;
    private final Activity activity;

    public UserLoginTask(Activity activity, ProgressHelper progressHelper, String email, String password, Intent postExecuteSuccessIntent) {
        this.activity = activity;
        this.email = email;
        this.password = password;
        this.postExecuteSuccessIntent = postExecuteSuccessIntent;
        this.progressHelper = progressHelper;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        Login login = new Login();
        login.setEmail(email);
        login.setPassword(password);

        try {
//            HttpHelper.Post(UrlHelper.getLoginUrl(), login);
        } catch (Exception ex) {
            return false;
        }

        return true;
    }

    @Override
    protected void onPostExecute(final Boolean success) {
        progressHelper.showProgress(false);
        if (success) {
            activity.startActivity(postExecuteSuccessIntent);
            activity.finish();
        }
    }

    @Override
    protected void onCancelled() {
        progressHelper.showProgress(false);
    }
}
