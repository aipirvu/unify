package com.owlcreativestudio.unify.tasks;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;

import com.owlcreativestudio.unify.helpers.ProgressHelper;
import com.owlcreativestudio.unify.models.Register;
import com.owlcreativestudio.unify.models.UserAccount;
import com.owlcreativestudio.unify.services.SharedPreferencesService;
import com.owlcreativestudio.unify.services.UserAccountService;

public class UpdateUserAccountTask extends AsyncTask<Void, Void, Boolean> {
    private final UserAccount userAccount;
    private final ProgressHelper progressHelper;
    private final UserAccountService userAccountService;
    private final SharedPreferencesService sharedPreferencesService;

    public UpdateUserAccountTask(Activity activity, ProgressHelper progressHelper, UserAccount userAccount) {
        this.userAccount = userAccount;
        this.progressHelper = progressHelper;
        this.sharedPreferencesService = new SharedPreferencesService(activity);
        this.userAccountService = new UserAccountService(activity);
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        return this.userAccountService.update(this.userAccount);
    }

    @Override
    protected void onPostExecute(final Boolean success) {
        if (success) {
            sharedPreferencesService.setUserAccount(userAccount);
        }
        progressHelper.showProgress(false);
    }

    @Override
    protected void onCancelled() {
        this.progressHelper.showProgress(false);
    }
}
