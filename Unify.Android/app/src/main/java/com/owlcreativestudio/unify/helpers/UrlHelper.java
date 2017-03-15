package com.owlcreativestudio.unify.helpers;

/**
 * Created by aipir on 06-Feb-17.
 */

public class UrlHelper {
    private static String BASE_ADDRESS = "http://192.168.1.3:5000/api";

    public static String getUserUrl() {
        return BASE_ADDRESS + "/user";
    }
    public static String getAppLoginUrl() {
        return BASE_ADDRESS + "/login/app";
    }
    public static String getFacebookLoginUrl() {
        return BASE_ADDRESS + "/login/facebook";
    }
    public static String getRegisterUrl() {
        return BASE_ADDRESS + "/register";
    }
}
