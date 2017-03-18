package com.owlcreativestudio.unify.tasks;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;

import com.owlcreativestudio.unify.helpers.ProgressHelper;
import com.owlcreativestudio.unify.models.FacebookLogin;
import com.owlcreativestudio.unify.models.FacebookProfile;
import com.owlcreativestudio.unify.models.UserAccount;
import com.owlcreativestudio.unify.services.SharedPreferencesService;
import com.owlcreativestudio.unify.services.UserAccountService;

public class UserFacebookLoginTask extends AsyncTask<Void, Void, Boolean> {
    private final Intent startRegisterIntent;
    private final Intent startARIntent;
    private final ProgressHelper progressHelper;
    private final Activity activity;
    private final FacebookProfile facebookProfile;
    private final UserAccountService userAccountService;
    private final SharedPreferencesService sharedPreferencesService;

    public UserFacebookLoginTask(Activity activity, ProgressHelper progressHelper, FacebookProfile facebookProfile, Intent startRegisterIntent, Intent startARIntent) {
        this.activity = activity;
        this.facebookProfile = facebookProfile;
        this.startRegisterIntent = startRegisterIntent;
        this.startARIntent = startARIntent;
        this.progressHelper = progressHelper;
        this.userAccountService = new UserAccountService(activity);
        this.sharedPreferencesService = new SharedPreferencesService(activity);
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        boolean successful = true;
        UserAccount userAccount = userAccountService.facebookLogin(new FacebookLogin(facebookProfile.getId()));
        if (null == userAccount) {
            successful = false;
            userAccount = new UserAccount();
            userAccount.setFacebookProfile(facebookProfile);
            userAccount.setDisplayName(facebookProfile.getName());
            if (null != facebookProfile.getEmail() && !facebookProfile.getEmail().isEmpty()) {
                userAccount.setEmail(facebookProfile.getEmail());
            }
            this.sharedPreferencesService.setUserAccount(userAccount);
        }
        return successful;
    }

    @Override
    protected void onPostExecute(final Boolean success) {
        progressHelper.showProgress(false);
        if (success) {
            activity.startActivity(startARIntent);
        } else {
            activity.startActivity(startRegisterIntent);
        }
        activity.finish();
    }

    @Override
    protected void onCancelled() {
        progressHelper.showProgress(false);
    }
}
