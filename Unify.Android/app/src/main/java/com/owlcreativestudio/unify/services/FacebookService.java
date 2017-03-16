package com.owlcreativestudio.unify.services;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.owlcreativestudio.unify.activities.ARActivity;
import com.owlcreativestudio.unify.activities.RegisterActivity;
import com.owlcreativestudio.unify.helpers.ProgressHelper;
import com.owlcreativestudio.unify.models.FacebookLogin;
import com.owlcreativestudio.unify.models.FacebookProfile;
import com.owlcreativestudio.unify.models.UserAccount;
import com.owlcreativestudio.unify.tasks.UserFacebookLoginTask;

import org.json.JSONObject;

public class FacebookService {
    private final SharedPreferencesService sharedPreferencesService;
    private final Activity activity;
    private final ProgressHelper progressHelper;

    public FacebookService(Activity activity, ProgressHelper progressHelper) {
        this.activity = activity;
        this.progressHelper = progressHelper;
        this.sharedPreferencesService = new SharedPreferencesService(activity);
    }

    public FacebookCallback<LoginResult> getFacebookLoginCallback() {
        return new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                progressHelper.showProgress(true);
                processAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {
                //todo notify user
            }
        };
    }

    private void processAccessToken(final AccessToken accessToken) {
        final String ID = "id";
        final String NAME = "name";
        final String FIRST_NAME = "first_name";
        final String LAST_NAME = "last_name";
        final String AGE_RANGE = "age_range";
        final String LINK = "link";
        final String GENDER = "gender";
        final String LOCALE = "locale";
        final String PICTURE = "picture";
        final String TIMEZONE = "timezone";
        final String EMAIL = "email";

        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                FacebookProfile facebookProfile = new FacebookProfile();

                try {
                    if (null != response.getError()) {
                        throw response.getError().getException();
                    }

                    facebookProfile.setId(object.getString(ID));
                    facebookProfile.setName(object.getString(NAME));
                    facebookProfile.setFirstName(object.getString(FIRST_NAME));
                    facebookProfile.setLastName(object.getString(LAST_NAME));
                    facebookProfile.setProfileLink(object.getString(LINK));
                    facebookProfile.setGender(object.getString(GENDER));
                    facebookProfile.setLocale(object.getString(LOCALE));
                    facebookProfile.setPictureLink(object.getString(PICTURE));
                    facebookProfile.setTimezone(object.getInt(TIMEZONE));
                    facebookProfile.setEmail(object.getString(EMAIL));

                    UserAccount userAccount = sharedPreferencesService.getUserAccount();

                    Intent startAR = new Intent(activity, ARActivity.class);
                    Intent startRegister = new Intent(activity, RegisterActivity.class);

                    if (null == userAccount) {
                        UserFacebookLoginTask userTask = new UserFacebookLoginTask(activity, progressHelper, facebookProfile, startRegister, startAR);
                        userTask.execute();
                    } else {
                        userAccount.setFacebookProfile(facebookProfile);
                        activity.startActivity(startAR);
                        activity.finish();
                    }
                } catch (Exception ex) {
                    //todo display error message;
                    progressHelper.showProgress(false);
                }
            }
        });

        Bundle parameters = new Bundle();
        String fields = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s", ID, NAME, FIRST_NAME, LAST_NAME, AGE_RANGE, LINK, GENDER, LOCALE, PICTURE, TIMEZONE, EMAIL);
        parameters.putString("fields", fields);
        request.setParameters(parameters);
        request.executeAsync();
    }
}
