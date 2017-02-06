package com.owlcreativestudio.unify.Helpers;

/**
 * Created by aipir on 06-Feb-17.
 */

public class UrlHelper {
    private static String BASE_ADDRESS = "http://192.168.1.3:5000/api";

    public static String getUserUrl() {
        return BASE_ADDRESS + "/user";
    }

    public static String getUserUrl(String id) {
        return getUserUrl() + "/" + id;
    }
}