package app.com.eliroy.android.wiseinfluence.Controller;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import app.com.eliroy.android.wiseinfluence.Model.Politician;
import app.com.eliroy.android.wiseinfluence.Model.Post;
import app.com.eliroy.android.wiseinfluence.R;

public class PostsFeedActivity extends FragmentActivity {

    private ArrayList<Politician> politicians;
    private PoliticianDownloadAsyncTask politicianTask;
    private PostDownloadAsyncTask postTask;

    FragmentManager fragmentManager = getSupportFragmentManager();// is this executed? the fragment parent issue

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts_feed);
        String[] URLS = getResources().getStringArray(R.array.RSS_channels_URL);
        reloadFeedWithURL(URLS[0]);
        //loadPoliticians();
    }

    public void reloadFeedWithURL(String url){
        postTask = new PostDownloadAsyncTask(this);
        postTask.execute(url);
    }

    //always go to same static json
    private void loadPoliticians(){
        politicianTask = new PoliticianDownloadAsyncTask();
        politicianTask.execute();
    }

    /*
    * onClick method for category button
    * */
    public void showCategoriesListView(View view) {
        AlertDFragmentCategories alertDFragmentCategories = new AlertDFragmentCategories();
        alertDFragmentCategories.show(fragmentManager,"Alert Dialog Fragment");
        }

    //============================ load posts to listview ==============================//

     private class PostDownloadAsyncTask extends AsyncTask<String,Void, ArrayList<Post>> {

        private Context context = null;

        public PostDownloadAsyncTask(Context context) {
            this.context = context;
        }

        @Override
        protected ArrayList<Post> doInBackground(String... params) {

            //enable debug here
           // android.os.Debug.waitForDebugger();

            Elements items = null;
            ArrayList<Post> posts = new ArrayList<Post>();
            String urlString = params[0];
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

                posts.add(new Post(topic, date ,content));
            }
            return posts;
        }

        @Override
        protected void onPostExecute(final ArrayList<Post> result) {
           CustomAdapter adapter = new CustomAdapter(this.context,R.layout.list_item_template,result);
            ListView feedList = (ListView) findViewById(R.id.feed_list_view);
            feedList.setAdapter(adapter);
            adapter.notifyDataSetChanged();//update data on adapter

            feedList.setOnItemClickListener(
                    new AdapterView.OnItemClickListener(){
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                            Intent intent = new Intent(PostsFeedActivity.this, PostDetailsActivity.class);
                            Bundle extras = new Bundle();
                            extras.putString("TOPIC",result.get(position).getTopic());
                            extras.putString("DATE",result.get(position).getDate());
                            extras.putString("CONTENT",result.get(position).getContent());
                            intent.putExtra("POLITICIANS",politicians);
                            intent.putExtras(extras);
                            startActivity(intent);
                        }
                    }
            );
        }
    }

    //================================= load politicians info ============================//

    private class PoliticianDownloadAsyncTask extends AsyncTask<String, String, ArrayList<Politician>>{

        private String url = "https://dl.dropboxusercontent.com/u/14989930/politicians.json";

        @Override
        protected ArrayList<Politician> doInBackground(String... urls) {

            ArrayList<Politician> result = new ArrayList<Politician>();
            try {
                String jsonString = getJson(this.url);

                JSONArray politiciansJSON = new JSONArray(jsonString);

                for (int i = 0; i < politiciansJSON.length(); i++) {
                    JSONObject politicianJSON = politiciansJSON.getJSONObject(i);
                    String name = politicianJSON.getString("name");
                    String id = politicianJSON.getString("id");
                    String email = politicianJSON.getString("email");
                    String facebook = politicianJSON.getString("facebook_page");
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
            politicians = result;
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
}
