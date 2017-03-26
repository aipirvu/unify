package com.owlcreativestudio.unify.services;

import android.app.Activity;

import com.owlcreativestudio.unify.helpers.ProgressHelper;
import com.owlcreativestudio.unify.helpers.UnifyTwitterApiClient;
import com.owlcreativestudio.unify.interfaces.ProfileService;
import com.owlcreativestudio.unify.models.TwitterProfile;
import com.owlcreativestudio.unify.models.UserAccount;
import com.owlcreativestudio.unify.tasks.UpdateUserAccountTask;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.User;
import com.twitter.sdk.android.core.services.StatusesService;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;

import retrofit2.Call;

public class TwitterService {
    private final SharedPreferencesService sharedPreferencesService;
    private final Activity activity;
    private final ProgressHelper progressHelper;

    public TwitterService(Activity activity, ProgressHelper progressHelper) {
        this.activity = activity;
        this.progressHelper = progressHelper;
        this.sharedPreferencesService = new SharedPreferencesService(activity);
    }

    public Callback<TwitterSession> getCallback() {
        return new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                progressHelper.showProgress(true);
                TwitterSession session = result.data;
                updateProfile(session);
            }

            @Override
            public void failure(TwitterException exception) {
                //todo notify user
            }
        };
    }

    private void updateProfile(final TwitterSession session) {
        final UnifyTwitterApiClient twitterApiClient = new UnifyTwitterApiClient(session);
        ProfileService profileService = twitterApiClient.getProfileService();
        Call<User> call = profileService.profile(session.getUserId());
        call.enqueue(new Callback<User>() {
            @Override
            public void success(Result<User> result) {
                TwitterProfile twitterProfile = new TwitterProfile();
                twitterProfile.setId(result.data.id);
                twitterProfile.setLanguage(result.data.lang);
                twitterProfile.setLocation(result.data.location);
                twitterProfile.setName(result.data.name);
                twitterProfile.setProfileBackgroundColor(result.data.profileBackgroundColor);
                twitterProfile.setProfileBackgroundImageUrl(result.data.profileBackgroundImageUrl);
                twitterProfile.setProfileImageUrl(result.data.profileImageUrl);
                twitterProfile.setScreenName(result.data.screenName);

                UserAccount userAccount = sharedPreferencesService.getUserAccount();
                userAccount.setTwitterProfile(twitterProfile);
                sharedPreferencesService.setUserAccount(userAccount);

                UpdateUserAccountTask updateAccount = new UpdateUserAccountTask(activity, progressHelper, userAccount);
                updateAccount.execute();
            }

            public void failure(TwitterException exception) {
                //todo notify user
            }
        });
    }
}
