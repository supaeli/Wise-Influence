package app.com.eliroy.android.wiseinfluence.Controller;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

import app.com.eliroy.android.wiseinfluence.Model.Post;
import app.com.eliroy.android.wiseinfluence.R;

public class PostsFeedActivity extends AppCompatActivity {

    public static final String TOPIC = "com.eliroy.android.wiseinfluence.TOPIC";
    public static final String DATE = "com.eliroy.android.wiseinfluence.DATE";
    public static final String CONTENT = "com.eliroy.android.wiseinfluence.CONTENT";

    private HTTPDownloadTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts_feed);

        //test call the HTTPDownloadTask
        task = new HTTPDownloadTask(this);
        task.execute("http://main.knesset.gov.il/Activity/committees/Finance/News"
                + "/_layouts/15/listfeedkns.aspx?List=9559f688-b470-4701-a379-fdd168efea09&View=404e1bcf-"
                + "c1cc-4911-ba46-aeb4b783f9c3");
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
                 doc = Jsoup.connect("http://main.knesset.gov.il/Activity/committees/Finance/" +
                         "News/_layouts/15/listfeedkns.aspx?List=9559f688-b470-4701-a379-fdd168efea09&" +
                         "View=404e1bcf-c1cc-4911-ba46-aeb4b783f9c3").get();


            }
            catch (IOException e) {
                e.printStackTrace();
                return null;//to prevent doc from being null(below)
            }

            items = doc.getElementsByTag("item");

            for (Element item : items){
                //since there is always only 1 decsription tag on item tag:
                description = item.getElementsByTag("description").first();
                innerDoc = Jsoup.parse(description.text());
                Elements divs = innerDoc.body().getElementsByTag("div");
                //map each div content to specific String
                String topic = divs.get(0).text();
                String date = divs.get(1).text();
                String content = divs.get(2).text();
                //String manipulation - consider doing earlier
                int i = topic.indexOf(":");
                topic = topic.substring(i+2);
                posts.add(new Post(topic, date ,content));
            }
            return posts;
        }

        /*
        * this method uses doInBackground's return value
        */
        @Override
        protected void onPostExecute(final ArrayList<Post> result) {
            /*for (int i = 0; i < result.size(); i++){
                posts.add(result.get(i));
            }*/
            //// TODO: 29/08/2016 get rid of prefix english string prior to post's topc,date,content
           CustomAdapter adapter = new CustomAdapter(this.context,R.layout.list_item_template,result);
            ListView list = (ListView) findViewById(R.id.feed_list_view);
            list.setAdapter(adapter);
            //adapter.notifyDataSetChanged();//update data on adapter

            list.setOnItemClickListener(
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
