package com.owlcreativestudio.unify.tasks;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;

import com.owlcreativestudio.unify.helpers.HttpHelper;
import com.owlcreativestudio.unify.helpers.ProgressHelper;
import com.owlcreativestudio.unify.helpers.UrlHelper;
import com.owlcreativestudio.unify.models.Register;
import com.owlcreativestudio.unify.models.User;
import com.owlcreativestudio.unify.services.SharedPreferencesService;

public class UserRegisterTask extends AsyncTask<Void, Void, Boolean> {

    private final Register register;
    private final Intent postExecuteSuccessIntent;
    private final ProgressHelper progressHelper;
    private final Activity activity;
    private final SharedPreferencesService sharedPreferencesService;

    public UserRegisterTask(Activity activity, ProgressHelper progressHelper, Register register, Intent postExecuteSuccessIntent) {
        this.register = register;
        this.postExecuteSuccessIntent = postExecuteSuccessIntent;
        this.activity = activity;
        this.progressHelper = progressHelper;
        this.sharedPreferencesService = new SharedPreferencesService(activity);
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            HttpHelper.Post(UrlHelper.getUserUrl(), register);
            //todo might require to get back the userAccount
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    @Override
    protected void onPostExecute(final Boolean success) {
        if (success) {
            sharedPreferencesService.setUserAccount(this.register.getUserAccount());
            this.activity.startActivity(postExecuteSuccessIntent);
            this.activity.finish();
        } else {
            //todo message user;
        }
    }

    @Override
    protected void onCancelled() {
        this.progressHelper.showProgress(false);
    }
}
