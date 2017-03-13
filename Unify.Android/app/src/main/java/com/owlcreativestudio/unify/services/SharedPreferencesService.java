package com.owlcreativestudio.unify.services;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.owlcreativestudio.unify.helpers.Constants;
import com.owlcreativestudio.unify.models.UserAccount;

public class SharedPreferencesService {
    private final Activity activity;
    private final Gson serializer = new Gson();

    public SharedPreferencesService(Activity activity) {
        this.activity = activity;
    }

    public UserAccount getUserAccount() {
        SharedPreferences sharedPreferences = this.activity.getPreferences(Context.MODE_PRIVATE);
        String jsonObject = "";
        sharedPreferences.getString(Constants.SharedPreferences.USER_ACCOUNT, jsonObject);

        if (jsonObject.isEmpty()) {
            return null;
        }
        return serializer.fromJson(jsonObject, UserAccount.class);
    }

    public void setUserAccount(UserAccount userAccount) {
        SharedPreferences sharedPreferences = this.activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.SharedPreferences.USER_ACCOUNT, serializer.toJson(userAccount));
    }
}
