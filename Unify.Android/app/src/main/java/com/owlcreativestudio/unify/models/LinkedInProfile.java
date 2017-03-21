package com.owlcreativestudio.unify.models;

public class LinkedInProfile {
    private String id;
    private String firstName;
    private String lastName;
    private String headline;
    private String profileLink;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public SiteStandardProfileRequest getSiteStandardProfileRequest() {
        return new SiteStandardProfileRequest() {{
            setUrl(profileLink);
        }};
    }

    public void setSiteStandardProfileRequest(SiteStandardProfileRequest siteStandardProfileRequest) {
        this.profileLink = siteStandardProfileRequest.getUrl();
    }

    public String getProfileLink() {
        return profileLink;
    }

    public void setProfileLink(String profileLink) {
        this.profileLink = profileLink;
    }
}

