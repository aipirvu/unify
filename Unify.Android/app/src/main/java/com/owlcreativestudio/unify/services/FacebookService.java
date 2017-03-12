package com.owlcreativestudio.unify.services;

import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestBatch;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.owlcreativestudio.unify.models.FacebookAccountState;

import org.json.JSONArray;
import org.json.JSONObject;

public class FacebookService {
    public static FacebookCallback<LoginResult> getFacebookLoginCallback() {
        return new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //todo create new account if email does not match
                //todo get data and send to server.

                getinfo(loginResult.getAccessToken());
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

    private static void getinfo(AccessToken accessToken) {
        GraphRequestBatch batch = new GraphRequestBatch(
                GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject jsonObject, GraphResponse response) {
                        Log.d("profile", "");
                    }
                }),
                GraphRequest.newMyFriendsRequest(accessToken, new GraphRequest.GraphJSONArrayCallback() {
                    @Override
                    public void onCompleted(JSONArray jsonArray, GraphResponse response) {
                        Log.d("friends", "");
                    }
                })
        );
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id, cover, name, first_name, last_name, age_range, link, gender, locale, picture, timezone, updated_time, verified, email");
        batch.get(0).setParameters(parameters);
        batch.get(1).setParameters(parameters);

        batch.addCallback(new GraphRequestBatch.Callback() {
            @Override
            public void onBatchCompleted(GraphRequestBatch graphRequests) {
                Log.d("batch", "");
            }
        });
        batch.executeAsync();

//        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
//            @Override
//            public void onCompleted(JSONObject object, GraphResponse response) {
//                // Application code
//                Log.d("done", "done");
//            }
//        });
//
//        GraphRequest graphRequest = GraphRequest.newMyFriendsRequest(accessToken, new GraphRequest.GraphJSONArrayCallback() {
//            @Override
//            public void onCompleted(JSONArray jsonArray, GraphResponse response) {
//                // Application code for users friends
//                Log.d("done", "done");
//            }
//        });
//        Bundle parameters = new Bundle();
//        parameters.putString("fields", "id, cover, name, first_name, last_name, age_range, link, gender, locale, picture, timezone, updated_time, verified");
//        request.setParameters(parameters);
//        request.executeAsync();
    }
}
