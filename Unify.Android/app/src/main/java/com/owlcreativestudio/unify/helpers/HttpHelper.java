package com.owlcreativestudio.unify.helpers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpHelper {
    private static String METHOD_GET = "GET";
    private static String METHOD_POST = "POST";
    private static String METHOD_PUT = "PUT";
    private static String METHOD_DELETE = "DELETE";
    private static int HTTP_CODE_OK = 200;

    public static <R> R get(String url, Class<R> responseType) throws Exception {
        return sendRequestWithResponse(METHOD_GET, url, null, responseType);
    }

    public static <T> void post(String url, T content) throws Exception {
        sendRequest(METHOD_POST, url, content);
    }

    public static <T, R> R postWithResponse(String url, T content, Class<R> responseType) throws Exception {
        return sendRequestWithResponse(METHOD_POST, url, content, responseType);
    }

    public static <T> void put(String url, T content) throws Exception {
        sendRequest(METHOD_PUT, url, content);
    }

    public static <T> void delete(String url) throws Exception {
        sendRequest(METHOD_DELETE, url, null);
    }

    private static <T> void sendRequest(String method, String url, T content) throws Exception {
        Gson gson = new GsonBuilder().create();
        String serializedContent = gson.toJson(content);

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod(method);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setUseCaches(false);
        connection.setDoOutput(true);

        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(serializedContent.getBytes());
        outputStream.flush();
        outputStream.close();

        int responseCode = connection.getResponseCode();

        if (responseCode != HTTP_CODE_OK) {
            throw new Exception("An error occurred: " + responseCode);
        }
    }

    private static <T, R> R sendRequestWithResponse(String method, String url, T content, Class<R> responseType) throws Exception {
        Gson serializer = new GsonBuilder().create();

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod(method);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setUseCaches(false);
        connection.setDoOutput(true);

        if (null !=  content) {
            String serializedContent = serializer.toJson(content);
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(serializedContent.getBytes());
            outputStream.flush();
            outputStream.close();
        }

        int responseCode = connection.getResponseCode();

        if (responseCode != HTTP_CODE_OK) {
            throw new Exception("An error occurred: " + responseCode);
        }

        BufferedReader inputStream = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder serializedResponse = new StringBuilder();

        while ((inputLine = inputStream.readLine()) != null) {
            serializedResponse.append(inputLine);
        }
        inputStream.close();

        return serializer.fromJson(serializedResponse.toString(), responseType);
    }
}
