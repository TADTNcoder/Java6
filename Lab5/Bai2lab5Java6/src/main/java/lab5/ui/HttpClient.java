package lab5.ui;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpClient {

    public static HttpURLConnection openConnection(String method, String url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setRequestMethod(method);
        conn.setRequestProperty("Content-Type", "application/json");
        return conn;
    }

    public static byte[] readData(HttpURLConnection conn) throws IOException {
        InputStream is = conn.getInputStream();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int n;
        while ((n = is.read(buffer)) != -1) {
            out.write(buffer, 0, n);
        }
        conn.disconnect();
        return out.toByteArray();
    }

    public static byte[] writeData(HttpURLConnection conn, byte[] data) throws IOException {
        conn.setDoOutput(true);
        conn.getOutputStream().write(data);
        return readData(conn);
    }
}