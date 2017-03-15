package com.owlcreativestudio.unify.services;

import android.util.Log;

import com.owlcreativestudio.unify.helpers.HttpHelper;
import com.owlcreativestudio.unify.helpers.UrlHelper;
import com.owlcreativestudio.unify.models.AppLogin;
import com.owlcreativestudio.unify.models.FacebookLogin;
import com.owlcreativestudio.unify.models.Register;
import com.owlcreativestudio.unify.models.UserAccount;

public class UserAccountService {
    public UserAccount appLogin(AppLogin login) {
        UserAccount userAccount = null;
        try {
            userAccount = HttpHelper.postWithResponse(UrlHelper.getAppLoginUrl(), login, UserAccount.class);
        } catch (Exception ex) {
            //todo notify user
            Log.d("HTTP", ex.getMessage());
        }

        return userAccount;
    }

    public UserAccount facebookLogin(FacebookLogin login) {
        UserAccount userAccount = null;
        try {
            userAccount = HttpHelper.postWithResponse(UrlHelper.getFacebookLoginUrl(), login, UserAccount.class);
        } catch (Exception ex) {
            //todo notify user
            Log.d("HTTP", ex.getMessage());
        }

        return userAccount;
    }

    public String register(Register register) {
        String message = "";
        try {
            HttpHelper.post(UrlHelper.getRegisterUrl(), register);
        } catch (Exception ex) {
            message = ex.getMessage();
            if (ex.getMessage().contains("409")) {
                message = "The email address is already used.";
            }
        }
        return  message;
    }
}
