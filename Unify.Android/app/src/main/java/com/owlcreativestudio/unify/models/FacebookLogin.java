package com.owlcreativestudio.unify.models;

public class FacebookLogin {
    private String facebookId;

    public FacebookLogin(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }
}
