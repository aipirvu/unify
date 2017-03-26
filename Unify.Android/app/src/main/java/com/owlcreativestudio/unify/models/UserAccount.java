package com.owlcreativestudio.unify.models;

public class UserAccount {
    String id = "";
    String displayName = "";
    String email = "";
    String pictureLink = "";
    FacebookProfile facebookProfile;
    LinkedInProfile linkedInProfile;
    TwitterProfile twitterProfile;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
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

    public LinkedInProfile getLinkedInProfile() {
        return linkedInProfile;
    }

    public void setLinkedInProfile(LinkedInProfile linkedInProfile) {
        this.linkedInProfile = linkedInProfile;
    }

    public TwitterProfile getTwitterProfile() {
        return twitterProfile;
    }

    public void setTwitterProfile(TwitterProfile twitterProfile) {
        this.twitterProfile = twitterProfile;
    }
}
