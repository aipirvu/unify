package com.owlcreativestudio.unify.services;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.owlcreativestudio.unify.R;
import com.owlcreativestudio.unify.helpers.MetricsHelper;
import com.owlcreativestudio.unify.models.AdjacentPerson;
import com.owlcreativestudio.unify.models.FacebookProfile;
import com.owlcreativestudio.unify.models.LinkedInProfile;
import com.owlcreativestudio.unify.models.TwitterProfile;
import com.owlcreativestudio.unify.tasks.DownloadImageTask;

public class ProfileDetailsService {
    private final Context CONTEXT;
    private final FrameLayout AR_LAYOUT;
    private final LinearLayout APP_PROFILE_LAYOUT;
    private final LinearLayout FACEBOOK_PROFILE_LAYOUT;
    private final LinearLayout LINKEDIN_PROFILE_LAYOUT;
    private final LinearLayout TWITTER_PROFILE_LAYOUT;
    private final LinearLayout NAVIGATION_PROFILE_LAYOUT;

    private final int marginTop;
    private final int labelPadding;

    public ProfileDetailsService(
            Context context,
            FrameLayout arLayout,
            LinearLayout appProfileLayout,
            LinearLayout facebookProfileLayout,
            LinearLayout linkedinProfileLayout,
            LinearLayout twitteProfileLayout,
            LinearLayout navigationProfileLayout) {
        CONTEXT = context;
        AR_LAYOUT = arLayout;
        APP_PROFILE_LAYOUT = appProfileLayout;
        FACEBOOK_PROFILE_LAYOUT = facebookProfileLayout;
        LINKEDIN_PROFILE_LAYOUT = linkedinProfileLayout;
        TWITTER_PROFILE_LAYOUT = twitteProfileLayout;
        NAVIGATION_PROFILE_LAYOUT = navigationProfileLayout;

        marginTop = MetricsHelper.getPixels(20, CONTEXT);
        labelPadding = MetricsHelper.getPixels(5, CONTEXT);

        APP_PROFILE_LAYOUT.setVisibility(View.GONE);
        FACEBOOK_PROFILE_LAYOUT.setVisibility(View.GONE);
        LINKEDIN_PROFILE_LAYOUT.setVisibility(View.GONE);
        TWITTER_PROFILE_LAYOUT.setVisibility(View.GONE);
        NAVIGATION_PROFILE_LAYOUT.setVisibility(View.GONE);
    }

    public void showDetails(AdjacentPerson adjacentPerson) {
        AR_LAYOUT.setVisibility(View.GONE);
        boolean makeFacebook = false;
        boolean makeLinkedIn = false;
        boolean makeTwitter = false;

        buildAppProfile(adjacentPerson);
        if (null != adjacentPerson.getFacebookProfile()) {
            makeFacebook = true;
            buildFacebookProfile(adjacentPerson.getFacebookProfile());
        }
        if (null != adjacentPerson.getLinkedInProfile()) {
            makeLinkedIn = true;
            buildLinkedInProfile(adjacentPerson.getLinkedInProfile());
        }
        if (null != adjacentPerson.getTwitterProfile()) {
            makeTwitter = true;
            buildTwitterProfile(adjacentPerson.getTwitterProfile());
        }

        buildNavigation(adjacentPerson, makeFacebook, makeLinkedIn, makeTwitter);

        APP_PROFILE_LAYOUT.setVisibility(View.VISIBLE);
        NAVIGATION_PROFILE_LAYOUT.setVisibility(View.VISIBLE);
    }

    private void buildAppProfile(AdjacentPerson profile) {
        APP_PROFILE_LAYOUT.removeAllViews();
        LinearLayout.LayoutParams appLabelParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        TextView appLabel = new TextView(CONTEXT);
        appLabel.setText("Unify app");
        appLabel.setTextAppearance(CONTEXT, android.R.style.TextAppearance_Large);
        appLabel.setTypeface(null, Typeface.BOLD);
        appLabel.setLayoutParams(appLabelParams);
        appLabel.setPadding(labelPadding, labelPadding, 0, labelPadding);
        appLabel.setBackgroundColor(Color.parseColor("#FFA02D"));
        appLabel.setTextColor(Color.WHITE);
        APP_PROFILE_LAYOUT.addView(appLabel);

        if (null != profile.getImageUrl()) {
            int pictureSize = MetricsHelper.getPixels(150, CONTEXT);
            LinearLayout.LayoutParams pictureParams = new LinearLayout.LayoutParams(pictureSize, pictureSize);
            pictureParams.setMargins(0, marginTop, 0, 0);
            pictureParams.gravity = Gravity.CENTER_HORIZONTAL;

            ImageView pictureView = new ImageView(CONTEXT);
            pictureView.setScaleType(ImageView.ScaleType.FIT_XY);
            pictureView.setLayoutParams(pictureParams);

            new DownloadImageTask(pictureView).execute(profile.getImageUrl());
            APP_PROFILE_LAYOUT.addView(pictureView);
        }


        LinearLayout.LayoutParams nameLabelParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        nameLabelParams.setMargins(0, marginTop, 0, 0);

        TextView nameLabel = new TextView(CONTEXT);
        nameLabel.setText("NAME");
        nameLabel.setTextAppearance(CONTEXT, android.R.style.TextAppearance_Large);
        nameLabel.setTypeface(null, Typeface.BOLD);
        nameLabel.setGravity(Gravity.CENTER);
        nameLabel.setLayoutParams(nameLabelParams);
        APP_PROFILE_LAYOUT.addView(nameLabel);


        LinearLayout.LayoutParams nameParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        nameParams.gravity = Gravity.CENTER_HORIZONTAL;

        TextView name = new TextView(CONTEXT);
        name.setText(profile.getDisplayName());
        name.setTextAppearance(CONTEXT, android.R.style.TextAppearance_Medium);
        name.setGravity(Gravity.CENTER);
        name.setLayoutParams(nameParams);
        APP_PROFILE_LAYOUT.addView(name);
    }

    private void buildFacebookProfile(FacebookProfile profile) {
        FACEBOOK_PROFILE_LAYOUT.removeAllViews();
        LinearLayout.LayoutParams appLabelParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        TextView appLabel = new TextView(CONTEXT);
        appLabel.setText("facebook");
        appLabel.setTextAppearance(CONTEXT, android.R.style.TextAppearance_Large);
        appLabel.setTypeface(null, Typeface.BOLD);
        appLabel.setLayoutParams(appLabelParams);
        appLabel.setPadding(labelPadding, labelPadding, 0, labelPadding);
        appLabel.setBackgroundColor(Color.parseColor("#3B5998"));
        appLabel.setTextColor(Color.WHITE);
        FACEBOOK_PROFILE_LAYOUT.addView(appLabel);

        if (null != profile.getPictureLink()) {
            int pictureSize = MetricsHelper.getPixels(150, CONTEXT);
            LinearLayout.LayoutParams pictureParams = new LinearLayout.LayoutParams(pictureSize, pictureSize);
            pictureParams.setMargins(0, marginTop, 0, 0);
            pictureParams.gravity = Gravity.CENTER_HORIZONTAL;

            ImageView pictureView = new ImageView(CONTEXT);
            pictureView.setScaleType(ImageView.ScaleType.FIT_XY);
            pictureView.setLayoutParams(pictureParams);

            new DownloadImageTask(pictureView).execute(profile.getPictureLink());
            FACEBOOK_PROFILE_LAYOUT.addView(pictureView);
        }


        LinearLayout.LayoutParams nameLabelParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        nameLabelParams.setMargins(0, marginTop, 0, 0);

        TextView nameLabel = new TextView(CONTEXT);
        nameLabel.setText("NAME");
        nameLabel.setTextAppearance(CONTEXT, android.R.style.TextAppearance_Large);
        nameLabel.setTypeface(null, Typeface.BOLD);
        nameLabel.setGravity(Gravity.CENTER);
        nameLabel.setLayoutParams(nameLabelParams);
        FACEBOOK_PROFILE_LAYOUT.addView(nameLabel);


        LinearLayout.LayoutParams nameParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        nameParams.gravity = Gravity.CENTER_HORIZONTAL;

        TextView name = new TextView(CONTEXT);
        name.setText(profile.getName());
        name.setTextAppearance(CONTEXT, android.R.style.TextAppearance_Medium);
        name.setGravity(Gravity.CENTER);
        name.setLayoutParams(nameParams);
        FACEBOOK_PROFILE_LAYOUT.addView(name);
    }

    private void buildLinkedInProfile(LinkedInProfile profile) {

    }

    private void buildTwitterProfile(TwitterProfile profile) {

    }

    private void buildNavigation(AdjacentPerson profile, boolean makeFacebook, boolean makeLinkedIn, boolean makeTwitter) {
        NAVIGATION_PROFILE_LAYOUT.removeAllViews();

        /* USER PHOTO */
        int profilePhotoIconSize = MetricsHelper.getPixels(90, CONTEXT);
        int profilePhotoIconMargin = MetricsHelper.getPixels(5, CONTEXT);
        LinearLayout.LayoutParams profilePhotoParams = new LinearLayout.LayoutParams(profilePhotoIconSize, profilePhotoIconSize);
        profilePhotoParams.setMargins(profilePhotoIconMargin, profilePhotoIconMargin, profilePhotoIconMargin, profilePhotoIconMargin);

        ImageView profilePhotoImageView = new ImageView(CONTEXT);
        profilePhotoImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        profilePhotoImageView.setLayoutParams(profilePhotoParams);
        if (null != profile.getImageUrl()) {
            new DownloadImageTask(profilePhotoImageView).execute(profile.getImageUrl());
        } else {
            //todo get a placeholder image here
            profilePhotoImageView.setImageResource(R.mipmap.unify_app_logo);
        }

        profilePhotoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                APP_PROFILE_LAYOUT.setVisibility(View.GONE);
                FACEBOOK_PROFILE_LAYOUT.setVisibility(View.GONE);
                LINKEDIN_PROFILE_LAYOUT.setVisibility(View.GONE);
                TWITTER_PROFILE_LAYOUT.setVisibility(View.GONE);
                NAVIGATION_PROFILE_LAYOUT.setVisibility(View.GONE);
                AR_LAYOUT.setVisibility(View.VISIBLE);
            }
        });

        NAVIGATION_PROFILE_LAYOUT.addView(profilePhotoImageView);

        /* PROFILES ICONS */
        int profileIconSize = MetricsHelper.getPixels(50, CONTEXT);
        LinearLayout.LayoutParams profileIconParams = new LinearLayout.LayoutParams(profileIconSize, profileIconSize);
        profileIconParams.setMargins(MetricsHelper.getPixels(25, CONTEXT), MetricsHelper.getPixels(10, CONTEXT), 0, 0);

        //app profile
        ImageView appProfileImageView = new ImageView(CONTEXT);
        appProfileImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        appProfileImageView.setLayoutParams(profileIconParams);
        appProfileImageView.setImageResource(R.mipmap.unify_app_logo);
        appProfileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeVisible(APP_PROFILE_LAYOUT);
            }
        });
        NAVIGATION_PROFILE_LAYOUT.addView(appProfileImageView);

        if (makeFacebook) {
            ImageView facebookProfileImageView = new ImageView(CONTEXT);
            facebookProfileImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            facebookProfileImageView.setLayoutParams(profileIconParams);
            facebookProfileImageView.setImageResource(R.mipmap.facebook_logo);
            facebookProfileImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    makeVisible(FACEBOOK_PROFILE_LAYOUT);
                }
            });
            NAVIGATION_PROFILE_LAYOUT.addView(facebookProfileImageView);
        }
        if (makeLinkedIn) {
            ImageView linkedInProfileImageView = new ImageView(CONTEXT);
            linkedInProfileImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            linkedInProfileImageView.setLayoutParams(profileIconParams);
            linkedInProfileImageView.setImageResource(R.mipmap.linkedin_logo);
            linkedInProfileImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    makeVisible(LINKEDIN_PROFILE_LAYOUT);
                }
            });
            NAVIGATION_PROFILE_LAYOUT.addView(linkedInProfileImageView);
        }
        if (makeLinkedIn) {
            ImageView twitterProfileImageView = new ImageView(CONTEXT);
            twitterProfileImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            twitterProfileImageView.setLayoutParams(profileIconParams);
            twitterProfileImageView.setImageResource(R.mipmap.twitter_logo);
            twitterProfileImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    makeVisible(TWITTER_PROFILE_LAYOUT);
                }
            });
            NAVIGATION_PROFILE_LAYOUT.addView(twitterProfileImageView);
        }
    }

    private void makeVisible(ViewGroup layout) {
        APP_PROFILE_LAYOUT.setVisibility(View.GONE);
        FACEBOOK_PROFILE_LAYOUT.setVisibility(View.GONE);
        LINKEDIN_PROFILE_LAYOUT.setVisibility(View.GONE);
        TWITTER_PROFILE_LAYOUT.setVisibility(View.GONE);
        layout.setVisibility(View.VISIBLE);
    }
}
