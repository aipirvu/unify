package com.owlcreativestudio.unify.models;

public class UserAccount {
    String id = "";
    String username = "";
    String email = "";
    String name = "";
    String pictureLink = "";
    FacebookProfile facebookProfile;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPictureLink() {
        return pictureLink;
    }

    public void setPictureLink(String pictureLink) {
        this.pictureLink = pictureLink;
    }

    public FacebookProfile getFacebookProfile() {
        return facebookProfile;
    }

    public void setFacebookProfile(FacebookProfile facebookProfile) {
        this.facebookProfile = facebookProfile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
