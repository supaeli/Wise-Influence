package app.com.eliroy.android.wiseinfluence.Support.Network;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
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

import app.com.eliroy.android.wiseinfluence.Controller.CustomAdapter;
import app.com.eliroy.android.wiseinfluence.Controller.PostDetailsActivity;
import app.com.eliroy.android.wiseinfluence.Model.Category;
import app.com.eliroy.android.wiseinfluence.Model.Politician;
import app.com.eliroy.android.wiseinfluence.Model.Post;
import app.com.eliroy.android.wiseinfluence.Model.Template;
import app.com.eliroy.android.wiseinfluence.R;

import static android.os.Debug.waitForDebugger;

/**
 * Created by elay1_000 on 14/08/2016.
 */
public class ApiClient {

    private PostDownloadAsyncTask postTask;
    private PoliticianDownloadAsyncTask politicianTask;
    private TemplateDownloadAsyncTask templateTask;

    public void reloadPostWithURL(Category category, CallBack handler){
        postTask = new PostDownloadAsyncTask(handler);
        postTask.execute(category);
    }

    //always go to same static json
    public void loadPoliticians(CallBack handler){
        politicianTask = new PoliticianDownloadAsyncTask(handler);
        politicianTask.execute();
    }

    public void loadTemplates(CallBack handler){
        templateTask = new TemplateDownloadAsyncTask(handler);
        templateTask.execute();
    }

    //============================ load posts to listview ==============================//

    private class PostDownloadAsyncTask extends AsyncTask<Category,Void, ArrayList<Post>> {

        private CallBack handler = null;

        public PostDownloadAsyncTask(CallBack handler) {
            this.handler = handler;
        }

        @Override
        protected ArrayList<Post> doInBackground(Category... params) {

            //enable debug here


            Elements items = null;
            ArrayList<Post> posts = new ArrayList<Post>();
            String urlString = params[0].getCategoryUrl();
            Document doc = null;
            Document innerDoc = null;
            Element description = null;

            try {

                doc = Jsoup.connect(urlString).get();


            }
            catch (IOException e) {
                e.printStackTrace();
                return null;//to prevent doc from being null(below)
            }

            items = doc.getElementsByTag("item");

            for (Element item : items){
                //since there is always only 1 decsription tag on item tag:
                description = item.getElementsByTag("description").first();
                //set html on description
                String desc = description.text().replace("<p>&#160;</p>","<p></p>");// unwanted tabs
                desc =  desc.replace("<p style=\"text-align&#58;justify;\">&#160;</p>","<p></p>");
                desc =  desc.replace("<p style=\"text-align&#58;center;\">&#160;</p>","<p></p>");
                innerDoc = Jsoup.parse(desc);
                Elements divs = innerDoc.body().getElementsByTag("div");
                //=====map each div content to specific String==============//
                String topic = divs.size() > 0 ? divs.get(0).text() : "";
                String date = divs.size() > 1 ? divs.get(1).text() : "";
                String content = divs.size() > 2 ? divs.get(2).text() : "";
                //======String manipulation - consider doing earlier=======//
                int i = topic.indexOf(":");
                // TODO: 18/09/2016 shorten if statements
                if(i+2 < topic.length()){
                    topic = topic.substring(i+2);
                }

                i = date.indexOf(":");
                if(i+2 < date.length()){
                    date = date.substring(i+2);
                }

                i = content.indexOf(":");
                if(i+2 < content.length()){
                    content = content.substring(i+2);
                }

                String category = params[0].getName();//not sure about it

                posts.add(new Post(topic, date ,content,category));
            }
            return posts;
        }

        @Override
        protected void onPostExecute(final ArrayList<Post> result) {
            if (result == null){
                Log.e("DEBUG","result is null");
                return;
            }
            this.handler.handle(result);
        }
    }
    //================================= load politicians info ============================//

    private class PoliticianDownloadAsyncTask extends AsyncTask<String, String, ArrayList<Politician>>{

        private String url = "https://dl.dropboxusercontent.com/u/14989930/knesset/politicians.json";
        private CallBack handler = null;

        public PoliticianDownloadAsyncTask(CallBack handler){
            this.handler = handler;
        }


        @Override
        protected ArrayList<Politician> doInBackground(String... urls) {

            ArrayList<Politician> result = new ArrayList<Politician>();
            try {
                String jsonString = getJson(this.url);

                JSONArray politiciansJSON = new JSONArray(jsonString);

                for (int i = 0; i < politiciansJSON.length(); i++) {
                    JSONObject politicianJSON = politiciansJSON.getJSONObject(i);
                    String name = politicianJSON.getString("first_name") + " " + politicianJSON.getString("last_name");
                    String id = politicianJSON.getString("id");
                    String email = politicianJSON.getString("email");
                    String facebook = politicianJSON.getString("facebook_url");
                    String phone = politicianJSON.getString("phone");
                    Politician politician = new Politician(id, name, email, facebook, phone);
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

        private String url = "https://dl.dropboxusercontent.com/u/14989930/knesset/templates.json";
        private CallBack handler = null;

        public TemplateDownloadAsyncTask(CallBack handler){
            this.handler = handler;
        }


        @Override
        protected ArrayList<Template> doInBackground(String... urls) {

            ArrayList<Template> result = new ArrayList<Template>();
            try {
                String jsonString = getJson(this.url);

                JSONArray templatesJSON = new JSONArray(jsonString);

                for (int i = 0; i < templatesJSON.length(); i++) {
                    JSONObject templateJSON = templatesJSON.getJSONObject(i);
                    //
                    String id = templateJSON.getString("id");
                    String content = templateJSON.getString("content");
                    String category = templateJSON.getString("category");
                    Template template= new Template(id, category, content);
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
