package com.owlcreativestudio.unify.tasks;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;

import com.owlcreativestudio.unify.helpers.HttpHelper;
import com.owlcreativestudio.unify.helpers.ProgressHelper;
import com.owlcreativestudio.unify.helpers.UrlHelper;
import com.owlcreativestudio.unify.models.User;

public class UserRegisterTask extends AsyncTask<Void, Void, Boolean> {

    private final String email;
    private final String password;
    private final String username;
    private final Intent postExecuteSuccessIntent;
    private final ProgressHelper progressHelper;
    private final Activity activity;

    public UserRegisterTask(Activity activity, ProgressHelper progressHelper, String username, String email, String password, Intent postExecuteSuccessIntent) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.postExecuteSuccessIntent = postExecuteSuccessIntent;
        this.activity = activity;
        this.progressHelper = progressHelper;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);

        try {
            HttpHelper.Post(UrlHelper.getUserUrl(), user);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    @Override
    protected void onPostExecute(final Boolean success) {
        if (success) {
            this.activity.startActivity(postExecuteSuccessIntent);
            this.activity.finish();
        }
    }

    @Override
    protected void onCancelled() {
        this.progressHelper.showProgress(false);
    }
}
