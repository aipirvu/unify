package com.owlcreativestudio.unify.Helpers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.owlcreativestudio.unify.Entities.User;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpHelper {
    private static String METHOD_GET = "GET";
    private static String METHOD_POST = "POST";
    private static String METHOD_PUT = "PUT";
    private static String METHOD_DELETE = "DELETE";
    private static int HTTP_CODE_OK = 200;

    public static <T> T Get(String url) throws Exception {
        return SendGetRequest(url);
    }

    public static <T> void Post(String url, T content) throws Exception {
        SendRequest(METHOD_POST, url, content);
    }

    public static <T> void Put(String url, T content) throws Exception {
        SendRequest(METHOD_PUT, url, content);
    }

    public static <T> void Delete(String url) throws Exception {
        SendRequest(METHOD_DELETE, url, null);
    }

    private static <T> void SendRequest(String method, String url, T content) throws Exception {
        Gson gson = new GsonBuilder().create();
        String serializedContent = gson.toJson(content);

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod(method);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setUseCaches(false);
        connection.setDoOutput(true);
//
//        DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
//        outputStream.writeBytes(serializedContent);
//        outputStream.flush();
//        outputStream.close();

        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(connection.getOutputStream());
        outputStreamWriter.write(serializedContent);
        outputStreamWriter.flush();
        outputStreamWriter.close();

        int responseCode = connection.getResponseCode();

        if (responseCode != HTTP_CODE_OK) {
            throw new Exception("An error occurred.");
        }
    }

    private static <T> T SendGetRequest(String url) throws Exception {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod(METHOD_GET);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setUseCaches(false);
        connection.setDoOutput(true);

        int responseCode = connection.getResponseCode();

        if (responseCode != HTTP_CODE_OK) {
            throw new Exception("An error occurred.");
        }

        BufferedReader inputStream = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = inputStream.readLine()) != null) {
            response.append(inputLine);
        }
        inputStream.close();

        Gson gson = new GsonBuilder().create();
//        return gson.fromJson(inputStream, User.class);

        throw new Exception("Not implemented");
    }

//
//    private static User SendGetRequest(String url) throws Exception {
//        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
//        connection.setRequestMethod(METHOD_GET);
//        connection.setRequestProperty("Content-Type", "application/json");
//        connection.setUseCaches(false);
//        connection.setDoOutput(true);
//
//        int responseCode = connection.getResponseCode();
//
//        if (responseCode != HTTP_CODE_OK) {
//            throw new Exception("An error occurred.");
//        }
//
//        BufferedReader inputStream = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//        String inputLine;
//        StringBuffer response = new StringBuffer();
//
//        while ((inputLine = inputStream.readLine()) != null) {
//            response.append(inputLine);
//        }
//        inputStream.close();
//
//        Gson gson = new GsonBuilder().create();
//        return gson.fromJson(inputStream, User.class);
//    }
}
