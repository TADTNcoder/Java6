package com.lab5;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpClient {

    public static HttpURLConnection openConnection(String method, String url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        connection.setRequestMethod(method);
        return connection;
    }

    public static byte[] readData(HttpURLConnection connection) throws IOException {
        int status = connection.getResponseCode();

        InputStream is;
        if (status >= 200 && status < 300) {
            is = connection.getInputStream();
        } else {
            is = connection.getErrorStream();
            if (is == null) {
                connection.disconnect();
                throw new IOException("HTTP Error: " + status);
            }
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int n;

        while ((n = is.read(buffer)) != -1) {
            out.write(buffer, 0, n);
        }

        connection.disconnect();
        return out.toByteArray();
    }

    public static byte[] writeData(HttpURLConnection connection, byte[] data) throws IOException {
        connection.setDoOutput(true);
        connection.getOutputStream().write(data);
        connection.getOutputStream().flush();
        return readData(connection);
    }
}