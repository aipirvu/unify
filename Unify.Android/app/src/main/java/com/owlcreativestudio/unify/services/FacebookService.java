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
import com.owlcreativestudio.unify.helpers.HttpHelper;
import com.owlcreativestudio.unify.helpers.UrlHelper;
import com.owlcreativestudio.unify.models.FacebookLogin;
import com.owlcreativestudio.unify.models.FacebookProfile;
import com.owlcreativestudio.unify.models.UserAccount;

import org.json.JSONObject;

public class FacebookService {
    private final SharedPreferencesService sharedPreferencesService;
    private final Activity activity;

    public FacebookService(Activity activity) {
        this.activity = activity;
        this.sharedPreferencesService = new SharedPreferencesService(activity);
    }

    public FacebookCallback<LoginResult> getFacebookLoginCallback() {
        return new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
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

//    public static ProfileTracker getProfileTracker(FacebookAccountState facebookAccountState) {
//        final FacebookAccountState accountState = facebookAccountState;
//        return new ProfileTracker() {
//            @Override
//            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
//                accountState.setProfile(currentProfile);
//            }
//        };
//    }

//    public static AccessTokenTracker getAccessTokenTracker(FacebookAccountState facebookAccountState) {
//        final FacebookAccountState accountState = facebookAccountState;
//        return new AccessTokenTracker() {
//            @Override
//            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
//                accountState.setAccessToken(currentAccessToken);
//            }
//        };
//    }

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
                String errorMessage = null;
                Exception error = null;
                boolean requiresRegistration = false;

                if (null != response.getError()) {
                    error = response.getError().getException();
                    errorMessage = "An error occurred while obtaining facebook profile information. ";
                } else {
                    try {
                        facebookProfile.setId(object.getString(ID));
                        facebookProfile.setName(object.getString(NAME));
                        facebookProfile.setFirstName(object.getString(FIRST_NAME));
                        facebookProfile.setLastName(object.getString(LAST_NAME));
//                        age range is a jason serialized object. We don't depend on it to deserialize it and obtain the data at the moment.
//                        facebookProfile.setAgeRange(object.getInt(AGE_RANGE));
                        facebookProfile.setProfileLink(object.getString(LINK));
                        facebookProfile.setGender(object.getString(GENDER));
                        facebookProfile.setLocale(object.getString(LOCALE));
                        facebookProfile.setPictureLink(object.getString(PICTURE));
                        facebookProfile.setTimezone(object.getInt(TIMEZONE));
                        facebookProfile.setEmail(object.getString(EMAIL));

                        UserAccount userAccount = sharedPreferencesService.getUserAccount();

                        if (null == userAccount) {
                            UserAccountService userAccountService = new UserAccountService();
                            userAccount = userAccountService.facebookLogin(new FacebookLogin(facebookProfile.getId()));
                        }

                        if (null == userAccount) {
                            requiresRegistration = true;
                            userAccount = new UserAccount();
                            userAccount.setName(facebookProfile.getName());
                            if (null != facebookProfile.getEmail() && !facebookProfile.getEmail().isEmpty()) {
                                userAccount.setEmail(facebookProfile.getEmail());
                            }
                        }

                        userAccount.setFacebookProfile(facebookProfile);


                        if (requiresRegistration) {
                            activity.startActivity(new Intent(activity, RegisterActivity.class));
                        } else {
                            //todo update account;
                            activity.startActivity(new Intent(activity, ARActivity.class));
                        }
                        activity.finish();

                    } catch (Exception ex) {
                        error = ex;
                        errorMessage = "An error occurred while obtaining facebook profile information. ";
                    }
                }

                if (null != errorMessage) {
                    //todo display this error message;
                }
                if (null != error) {
                    //todo log this error
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
