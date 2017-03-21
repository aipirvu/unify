package com.owlcreativestudio.unify.services;

import android.content.Context;
import android.util.Log;

import com.owlcreativestudio.unify.helpers.AlertHelper;
import com.owlcreativestudio.unify.helpers.HttpHelper;
import com.owlcreativestudio.unify.helpers.UrlHelper;
import com.owlcreativestudio.unify.models.AppLogin;
import com.owlcreativestudio.unify.models.FacebookLogin;
import com.owlcreativestudio.unify.models.Register;
import com.owlcreativestudio.unify.models.UserAccount;

public class UserAccountService {
    private final Context context;

    public UserAccountService(Context context) {
        this.context = context;
    }

    public UserAccount appLogin(AppLogin login) {
        UserAccount userAccount = null;
        try {
            userAccount = HttpHelper.postWithResponse(UrlHelper.getAppLoginUrl(), login, UserAccount.class);
        } catch (Exception ex) {
            handleError(ex);
        }

        return userAccount;
    }

    public UserAccount facebookLogin(FacebookLogin login) {
        UserAccount userAccount = null;
        try {
            userAccount = HttpHelper.postWithResponse(UrlHelper.getFacebookLoginUrl(), login, UserAccount.class);
        } catch (Exception ex) {
            handleError(ex);
        }

        return userAccount;
    }

    public boolean register(Register register) {
        boolean success = true;
        try {
            HttpHelper.post(UrlHelper.getRegisterUrl(), register);
        } catch (Exception ex) {
            success = false;
            if (ex.getMessage().contains("409")) {
                handleError(ex, "The email address is already used.");
            } else {
                handleError(ex);
            }
        }
        return success;
    }

    public boolean update(UserAccount userAccount) {
        boolean success = true;
        try {
            HttpHelper.put(UrlHelper.getUserUrl(), userAccount);
        } catch (Exception ex) {
            handleError(ex);
            success = false;
        }
        return success;
    }

    private void handleError(Exception ex) {
        AlertHelper.show(context, "Error", "A connection error occurred");
        //todo log exception;
    }

    private void handleError(Exception ex, String message) {
        AlertHelper.show(context, "Error", message);
        //todo log exception;
    }
}
