package eus.ehu.tta.aupapp.negocio;

import android.util.Base64;

import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tta on 14/01/18.
 */

public class ClienteRest {

    private final static String AUTH = "Authorization";
    private final String baseUrl;
    private final Map<String, String> properties = new HashMap<>();

    public ClienteRest(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void setHttpBasicAuth(String user, String password) {
        String basicAuth = Base64.encodeToString(String.format("%s:%s", user, password).getBytes(), Base64.DEFAULT);
        properties.put(AUTH, String.format("Basic %s", basicAuth));
    }

    public void setProperty(String name, String value) {
        properties.put(name, value);
    }

    private HttpURLConnection getConnection(String path) throws IOException {
        URL url = new URL(String.format("%s/%s", baseUrl, path));
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        for (Map.Entry<String, String> property : properties.entrySet()) {
            conn.setRequestProperty(property.getKey(), property.getValue());
        }
        conn.setUseCaches(false);

        return conn;
    }

    public int postJson(final JSONObject json, String path) throws IOException {

        HttpURLConnection conn = null;

        try {
            conn = getConnection(path);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            PrintWriter printWriter = new PrintWriter(conn.getOutputStream());
            printWriter.print(json.toString());
            printWriter.close();
            return conn.getResponseCode();
        } finally {
            if (conn != null)
                conn.disconnect();
        }
    }

}
