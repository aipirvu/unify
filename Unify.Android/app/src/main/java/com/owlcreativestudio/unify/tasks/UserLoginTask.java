package com.owlcreativestudio.unify.tasks;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;

import com.owlcreativestudio.unify.helpers.ProgressHelper;
import com.owlcreativestudio.unify.models.AppLogin;
import com.owlcreativestudio.unify.models.UserAccount;
import com.owlcreativestudio.unify.services.SharedPreferencesService;
import com.owlcreativestudio.unify.services.UserAccountService;

public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
    private final Intent postExecuteSuccessIntent;
    private final ProgressHelper progressHelper;
    private final Activity activity;
    private final AppLogin login;
    private final UserAccountService userAccountService;
    private final SharedPreferencesService sharedPreferencesService;

    public UserLoginTask(Activity activity, ProgressHelper progressHelper, AppLogin login, Intent postExecuteSuccessIntent) {
        this.activity = activity;
        this.login = login;
        this.postExecuteSuccessIntent = postExecuteSuccessIntent;
        this.progressHelper = progressHelper;
        this.userAccountService = new UserAccountService();
        this.sharedPreferencesService = new SharedPreferencesService(activity);
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            UserAccount userAccount = userAccountService.appLogin(login);
            if (null == userAccount) {
                return false;
            }
            this.sharedPreferencesService.setUserAccount(userAccount);
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
