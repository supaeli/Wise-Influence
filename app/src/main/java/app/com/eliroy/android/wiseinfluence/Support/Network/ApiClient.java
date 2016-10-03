package app.com.eliroy.android.wiseinfluence.Support.Network;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import app.com.eliroy.android.wiseinfluence.Model.Category;
import app.com.eliroy.android.wiseinfluence.Model.Politician;
import app.com.eliroy.android.wiseinfluence.Model.Post;
import app.com.eliroy.android.wiseinfluence.Model.Template;

/**
 * Created by elay1_000 on 14/08/2016.
 */
public class ApiClient {

    private final static String APP_TOKEN = "ASDGDFDCE1234214DVDV";
    private PostDownloadAsyncTask postTask;
    private PoliticianDownloadAsyncTask politicianTask;
    private TemplateDownloadAsyncTask templateTask;

    public void reloadPostWithCategory(Category category, CallBack handler){
        postTask = new PostDownloadAsyncTask(handler);
        postTask.execute(category);
    }

    //always go to same static json
    public void loadPoliticians(Category category, CallBack handler){
        politicianTask = new PoliticianDownloadAsyncTask(handler);
        politicianTask.execute(category);
    }

    public void loadTemplates(CallBack handler){
        templateTask = new TemplateDownloadAsyncTask(handler);
        templateTask.execute();
    }

    //============================ load posts to ListView ==============================//

    private class PostDownloadAsyncTask extends AsyncTask<Category,Void, ArrayList<Post>> {

        private CallBack handler = null;

        public PostDownloadAsyncTask(CallBack handler) {
            this.handler = handler;
        }

        @Override
        protected ArrayList<Post> doInBackground(Category... categories) {



            //enable debug here
            ArrayList<Post> result = new ArrayList<Post>();
            String urlString = "https://wise-influence-server.herokuapp.com/api/posts?app_token=" + APP_TOKEN + "&limit=100&category_name=" + categories[0].getEnglishName();
            try {
                String jsonString = getJson(urlString);
                JSONArray postsJSON = new JSONArray(jsonString);

                for (int i = 0; i < postsJSON.length(); i++) {
                    JSONObject postJSON = postsJSON.getJSONObject(i);
                    String topic = postJSON.getString("topic");
                    String date = postJSON.getString("date");
                    String content =postJSON.getString("content");
                    Post post = new Post(topic, date, content);
                    result.add(post);
                }
            } catch (JSONException e){
                e.printStackTrace();
            }
             catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(final ArrayList<Post> result) {
            if (result == null){
                Log.e("DEBUG","result is null");
                return;
            }
            this.handler.handle(result);
        }

        private String getJson(String urlString) throws IOException {

            String jsonString= null;

            try {
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream is = connection.getInputStream();
                //create here the json object from input stream
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        is, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }


                jsonString = sb.toString();
                is.close();
                // try parse the string to a JSON object


            } catch (MalformedURLException e){
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return jsonString;
        }
    }
    //================================= load politicians info ============================//

    private class PoliticianDownloadAsyncTask extends AsyncTask<Category, String, ArrayList<Politician>>{

        private CallBack handler = null;
        private ProgressDialog progressDialog;

        public PoliticianDownloadAsyncTask(CallBack handler){
            this.handler = handler;
        }

        @Override
        protected ArrayList<Politician> doInBackground(Category... categories) {

            ArrayList<Politician> result = new ArrayList<Politician>();
            String urlString = "https://wise-influence-server.herokuapp.com/api/politicians?app_token=" + APP_TOKEN + "&limit=20&category_name=" + categories[0].getEnglishName();
            try {
                String jsonString = getJson(urlString);
                JSONArray politiciansJSON = new JSONArray(jsonString);

                for (int i = 0; i < politiciansJSON.length(); i++) {
                    JSONObject politicianJSON = politiciansJSON.getJSONObject(i);
                    String name = politicianJSON.getString("name");
                    String email = politicianJSON.getString("email");
                    String facebook = politicianJSON.getString("facebook_url");
                    String phone = politicianJSON.getString("phone");
                    Politician politician = new Politician(name, email, facebook, phone);
                    result.add(politician);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(ArrayList<Politician> result) {
            //android.os.Debug.waitForDebugger();
            this.handler.handle(result);
        }

        private String getJson(String urlString) throws IOException {

            String jsonString= null;

            try {
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream is = connection.getInputStream();
                //create here the json object from input stream
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        is, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }


                jsonString = sb.toString();
                is.close();
                // try parse the string to a JSON object


            } catch (MalformedURLException e){
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return jsonString;
        }
    }

    //================================= load templates ============================//

    private class TemplateDownloadAsyncTask extends AsyncTask<String, String, ArrayList<Template>>{

        private CallBack handler = null;

        public TemplateDownloadAsyncTask(CallBack handler){
            this.handler = handler;
        }


        @Override
        protected ArrayList<Template> doInBackground(String... urls) {

            ArrayList<Template> result = new ArrayList<Template>();
            String urlString = "https://wise-influence-server.herokuapp.com/api/templates?app_token=" + APP_TOKEN;
            try {
                String jsonString = getJson(urlString);

                JSONArray templatesJSON = new JSONArray(jsonString);

                for (int i = 0; i < templatesJSON.length(); i++) {
                    JSONObject templateJSON = templatesJSON.getJSONObject(i);
                    String name = templateJSON.getString("name");
                    String content = templateJSON.getString("content");
                    int likesCount = templateJSON.getInt("likes_count");
                    Template template = new Template(name, content, likesCount);
                    result.add(template);
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(ArrayList<Template> result) {
            //waitForDebugger();
            this.handler.handle(result);
        }

        private String getJson(String urlString) throws IOException {

            String jsonString= null;

            try {
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream is = connection.getInputStream();
                //create here the json object from input stream
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        is, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }

                jsonString = sb.toString();
                is.close();

            } catch (MalformedURLException e){
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return jsonString;
        }
    }
}
