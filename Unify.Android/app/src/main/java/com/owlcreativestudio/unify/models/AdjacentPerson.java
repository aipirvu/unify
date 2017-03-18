package com.owlcreativestudio.unify.models;

public class AdjacentPerson {
    private String id;
    private String name;
    private UnifyLocation location;
    private String imageUrl;
    private FacebookProfile facebookProfile;
    private LinkedInProfile linkedInProfile;
    private TwitterProfile twitterProfile;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UnifyLocation getLocation() {
        return location;
    }

    public void setLocation(UnifyLocation location) {
        this.location = location;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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
