package eus.ehu.tta.aupapp.modelo;

import android.util.Base64;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

    public String getAuthorization(){return this.properties.get(AUTH);}

    public void setAuthorization(String auth){ this.properties.put(AUTH, auth);}

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

    public String getString(String path) throws IOException{
        HttpURLConnection connection = null;
        try {
            connection = getConnection(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String prueba = br.readLine();
            return prueba;

        } finally {
            disconnect(connection);
        }
    }

    public JSONObject getJson(String path) throws IOException, JSONException{
        return new JSONObject(getString(path));
    }

    public JSONArray getJsonArray(String path) throws IOException, JSONException{
        return new JSONArray(getString(path));
    }

    public int postFile(String path, InputStream is, String fileName) throws IOException{
        String boundary = Long.toString(System.currentTimeMillis());
        String newLine = "\r\n";
        String prefix = "--";
        HttpURLConnection connection = null;
        try {
            connection = getConnection(path);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" +
                    boundary);
            connection.setDoOutput(true);
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.writeBytes(prefix + boundary + newLine);
            out.writeBytes("Content-Disposition: form-data; name=\"filetype\""+newLine);
            out.writeBytes(newLine);
            out.writeBytes("img"+newLine);
            out.writeBytes(prefix+boundary+newLine);
            out.writeBytes("Content-Disposition: form-data; name=\"file\";filename=\""+fileName+"\""+newLine);
            out.writeBytes("Content-Type: image/jpg"+newLine);
            out.writeBytes(newLine);
            byte[] data = new byte[1024*1024];
            int len;
            while ((len = is.read(data))>0){
                out.write(data, 0 ,len);
            }
            out.writeBytes(newLine);
            out.writeBytes(prefix + boundary + prefix + newLine);
            out.close();
            return  connection.getResponseCode();
        } finally {
            disconnect(connection);
        }
    }

    public String postJson(final JSONObject json, String path) throws IOException {

        HttpURLConnection conn = null;

        try {
            String login = null;
            conn = getConnection(path);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);
            PrintWriter printWriter = new PrintWriter(conn.getOutputStream());
            printWriter.print(json.toString());
            printWriter.close();
            
            if(conn.getResponseCode()==200){
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                login = br.readLine();
            }
            
            return login;
        } finally {
            if (conn != null)
                conn.disconnect();
        }
    }

    public int postJson2(final JSONObject json, String path) throws IOException {

        HttpURLConnection conn = null;

        try {
            String login = null;
            conn = getConnection(path);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);
            PrintWriter printWriter = new PrintWriter(conn.getOutputStream());
            printWriter.print(json.toString());
            printWriter.close();

            return conn.getResponseCode();
        } finally {
            if (conn != null)
                conn.disconnect();
        }
    }

    private void disconnect(HttpURLConnection connection){
        if(connection != null){
            connection.disconnect();
        }
    }



}
