package com.owlcreativestudio.unify.tasks;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;

import com.owlcreativestudio.unify.helpers.HttpHelper;
import com.owlcreativestudio.unify.helpers.ProgressHelper;
import com.owlcreativestudio.unify.helpers.UrlHelper;
import com.owlcreativestudio.unify.models.Register;
import com.owlcreativestudio.unify.services.SharedPreferencesService;
import com.owlcreativestudio.unify.services.UserAccountService;

public class UserRegisterTask extends AsyncTask<Void, Void, Boolean> {

    private final Register register;
    private final Intent postExecuteSuccessIntent;
    private final ProgressHelper progressHelper;
    private final Activity activity;
    private final UserAccountService userAccountService;
    private final SharedPreferencesService sharedPreferencesService;
    private String message;

    public UserRegisterTask(Activity activity, ProgressHelper progressHelper, Register register, Intent postExecuteSuccessIntent) {
        this.register = register;
        this.postExecuteSuccessIntent = postExecuteSuccessIntent;
        this.activity = activity;
        this.progressHelper = progressHelper;
        this.sharedPreferencesService = new SharedPreferencesService(activity);
        this.userAccountService = new UserAccountService();
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        this.message = this.userAccountService.register(this.register);
        return message.isEmpty();
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
        progressHelper.showProgress(false);
    }

    @Override
    protected void onCancelled() {
        this.progressHelper.showProgress(false);
    }
}
