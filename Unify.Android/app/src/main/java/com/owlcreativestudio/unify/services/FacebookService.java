package com.owlcreativestudio.unify.services;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.owlcreativestudio.unify.models.FacebookAccountState;

public class FacebookService {
    public static FacebookCallback<LoginResult> getFacebookLoginCallback() {
        return new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //todo create new account if email does not match
                //todo get data and send to server.
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

    public static ProfileTracker getProfileTracker(FacebookAccountState facebookAccountState) {
        final FacebookAccountState accountState = facebookAccountState;
        return new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                accountState.setProfile(currentProfile);
            }
        };
    }

    public static AccessTokenTracker getAccessTokenTracker(FacebookAccountState facebookAccountState) {
        final FacebookAccountState accountState = facebookAccountState;
        return new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                accountState.setAccessToken(currentAccessToken);
            }
        };
    }
}
