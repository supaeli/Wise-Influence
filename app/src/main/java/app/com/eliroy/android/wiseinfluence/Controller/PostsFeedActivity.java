package app.com.eliroy.android.wiseinfluence.Controller;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import app.com.eliroy.android.wiseinfluence.Model.Post;
import app.com.eliroy.android.wiseinfluence.R;

public class PostsFeedActivity extends FragmentActivity {

    public static final String TOPIC = "com.eliroy.android.wiseinfluence.TOPIC";
    public static final String DATE = "com.eliroy.android.wiseinfluence.DATE";
    public static final String CONTENT = "com.eliroy.android.wiseinfluence.CONTENT";

    private HTTPDownloadTask task;
    FragmentManager fm = getSupportFragmentManager();// is this executed? the fragment parent issue




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts_feed);

        String[] URLS = getResources().getStringArray(R.array.RSS_channels_URL);
        reloadFeedWithURL(URLS[0]);
    }

    public void reloadFeedWithURL(String url){
        task = new HTTPDownloadTask(this);
        task.execute(url);
    }

    /*
    * onClick method for category button
    * */
    public void showCategoriesListView(View view) {
        AlertDFragmentCategories alertDFragmentCategories = new AlertDFragmentCategories();
        alertDFragmentCategories.show(fm,"Alert Dialog Fragment");
        }

     private class HTTPDownloadTask extends AsyncTask<String,Void, ArrayList<Post>> {

        private Context context = null;

        public HTTPDownloadTask(Context context) {
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
                String topic = divs.get(0).text();
                String date = divs.get(1).text();
                 String content = divs.get(2).text();
                //======String manipulation - consider doing earlier=======//
                int i = topic.indexOf(":");
                topic = topic.substring(i+2);
                i = date.indexOf(":");
                date = date.substring(i+2);
                i = content.indexOf(":");
                content = content.substring(i+2);
                posts.add(new Post(topic, date ,content));
            }
            return posts;
        }

        /*
        * this method uses doInBackground's return value
        */
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
                            intent.putExtras(extras);
                            startActivity(intent);
                        }
                    }
            );
        }
    }
}
