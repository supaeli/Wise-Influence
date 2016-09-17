package app.com.eliroy.android.wiseinfluence.Support.Network;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import app.com.eliroy.android.wiseinfluence.Controller.CustomAdapter;
import app.com.eliroy.android.wiseinfluence.Controller.PostDetailsActivity;
import app.com.eliroy.android.wiseinfluence.Model.Post;
import app.com.eliroy.android.wiseinfluence.R;

/**
 * Created by elay1_000 on 14/08/2016.
 */
public class ApiClient {

    private PostDownloadAsyncTask postTask;

    public void reloadPostWithURL(String url, CallBack handler){
        postTask = new PostDownloadAsyncTask(handler);
        postTask.execute(url);
    }

    //============================ load posts to listview ==============================//

    private class PostDownloadAsyncTask extends AsyncTask<String,Void, ArrayList<Post>> {

        private CallBack handler = null;

        public PostDownloadAsyncTask(CallBack handler) {
            this.handler = handler;
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
            if (result == null){
                Log.e("DEBUG","result is null");
                return;
            }
            this.handler.execute(result);
        }
    }
}
