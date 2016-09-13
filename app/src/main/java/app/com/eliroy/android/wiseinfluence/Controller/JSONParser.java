package app.com.eliroy.android.wiseinfluence.Controller;


import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class JSONParser { //from https://www.learn2crack.com/2013/10/android-asynctask-json-parsing-example.html

    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";

    public JSONParser() {

    }
    public JSONObject getJSONFromUrl(String url) {

        // Making HTTP request, using url creation twice ,revise?
        try {
            URL url1 = new URL(url);
            HttpURLConnection urlConnection = (HttpURLConnection) url1.openConnection();
             is = new BufferedInputStream(urlConnection.getInputStream());
            readStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "n");
            }
            is.close();
            json = sb.toString();
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

        // try parse the string to a JSON object
        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        // return JSON String
        return jObj;

    }
    // copy paste http://stackoverflow.com/questions/8376072/whats-the-readstream-method-i-just-can-not-find-it-anywhere
    private  String readStream(InputStream is) throws IOException{
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(is),1000);
        for (String line = r.readLine(); line != null; line =r.readLine()){
            sb.append(line);
        }
        is.close();
        return sb.toString();
    }
}

