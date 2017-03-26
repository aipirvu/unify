package com.owlcreativestudio.unify.helpers;


import com.owlcreativestudio.unify.interfaces.ProfileService;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterSession;

public class UnifyTwitterApiClient extends TwitterApiClient {
    public UnifyTwitterApiClient(TwitterSession session) {
        super(session);
    }

    public ProfileService getProfileService() {
        return getService(ProfileService.class);
    }
}

