package com.owlcreativestudio.unify.models;

import com.facebook.AccessToken;
import com.facebook.Profile;

public class FacebookAccountState {
    private AccessToken accessToken;
    private Profile profile;

    public AccessToken getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(AccessToken accessToken) {
        this.accessToken = accessToken;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
