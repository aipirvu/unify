package com.owlcreativestudio.unify.services;

import android.app.Activity;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.linkedin.platform.APIHelper;
import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIApiError;
import com.linkedin.platform.errors.LIAuthError;
import com.linkedin.platform.listeners.ApiListener;
import com.linkedin.platform.listeners.ApiResponse;
import com.linkedin.platform.listeners.AuthListener;
import com.linkedin.platform.utils.Scope;
import com.owlcreativestudio.unify.helpers.ProgressHelper;
import com.owlcreativestudio.unify.models.LinkedInProfile;
import com.owlcreativestudio.unify.models.UserAccount;
import com.owlcreativestudio.unify.tasks.UpdateUserAccountTask;

public class LinkedInService {
    private final SharedPreferencesService sharedPreferencesService;
    private final Activity activity;
    private final ProgressHelper progressHelper;

    public LinkedInService(Activity activity, ProgressHelper progressHelper) {
        this.activity = activity;
        this.progressHelper = progressHelper;
        this.sharedPreferencesService = new SharedPreferencesService(activity);
    }

    public void connectWithLinkedIn(final Context applicationContext) {
        LISessionManager.getInstance(applicationContext).init(activity, buildScope(), new AuthListener() {
            @Override
            public void onAuthSuccess() {
                updateProfile(applicationContext);
            }

            @Override
            public void onAuthError(LIAuthError error) {
                //todo notify user
                progressHelper.showProgress(false);
            }
        }, true);
    }

    private static Scope buildScope() {
        return Scope.build(Scope.R_BASICPROFILE);
    }

    private void updateProfile(Context applicationContext) {
        String url = "https://api.linkedin.com/v1/people/~";
        final APIHelper apiHelper = APIHelper.getInstance(applicationContext);
        apiHelper.getRequest(activity, url, new ApiListener() {
            @Override
            public void onApiSuccess(ApiResponse apiResponse) {
                Gson serializer = new GsonBuilder().create();
                LinkedInProfile linkedInProfile = serializer.fromJson(apiResponse.getResponseDataAsString(), LinkedInProfile.class);
                UserAccount userAccount = sharedPreferencesService.getUserAccount();
                userAccount.setLinkedInProfile(linkedInProfile);
                sharedPreferencesService.setUserAccount(userAccount);

                UpdateUserAccountTask updateAccount = new UpdateUserAccountTask(activity, progressHelper, userAccount);
                updateAccount.execute();
            }

            @Override
            public void onApiError(LIApiError liApiError) {
                //todo notify user
                progressHelper.showProgress(false);
            }
        });
    }
}
