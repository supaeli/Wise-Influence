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
            //tryout - items was'nt initialized, it is init! now prog crashes somewhere on asynctask
            // , moved the below lines down now it doens't crash on items = doc.get...
            items = doc.getElementsByTag("item");

            for (Element item : items){
                //since there is always only 1 decsription tag on item tag:
                description = item.getElementsByTag("description").first();
                innerDoc = Jsoup.parse(description.text());
                Elements divs = innerDoc.body().getElementsByTag("div");
                //map each div to the relevant TextView on list item
                //change to add! not get
                posts.add(new Post(divs.get(0).text(),divs.get(1).text(),divs.get(2).text()));
            }
            //check later if this is suitable
            return posts;
        }

        /*
        *
        * this method uses doInBackground's return value
        * */
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
/*
    private class RssDataController extends AsyncTask<String, Integeron, ArrayList<Post>>{

        @Override
        protected ArrayList<Post> doInBackground(String... params) {

            String urlString = params[0];
            InputStream inputStream = null;
            ArrayList<Post> postDataList = new ArrayList<Post>();
            try{
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(10*1000);
                connection.setConnectTimeout(10*1000);
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.connect();
                int response = connection.getResponseCode();
                Log.d("debug","The response is: " + response);
                inputStream = connection.getInputStream();

                //got the data, now parsing
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(inputStream, null);

                int eventType = xpp.getEventType();
                Post pData = null;
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, DD MMM yyyy HH:mm:ss");
                while (eventType != XmlPullParser.END_DOCUMENT){
                    if (eventType == XmlPullParser.START_DOCUMENT){ //empty statement
                    }else if(eventType == XmlPullParser.START_TAG) {
                        if (xpp.getName().equals("item")) {
                            pData = new Post();
                            currTag = RSSXMLTag.IGNORETAG;
                        } else if (xpp.getName().equals("title")) {//title contains String: "Message
                            // from [dd month] from some reason
                            currTag = RSSXMLTag.DATE;
                        } else if (xpp.getName().equals("link")) {
                            currTag = RSSXMLTag.LINK;
                        } else if (xpp.getName().equals("description")) {//// TODO: 24/08/2016 -parse content of
                            // description to extract title
                            currTag = RSSXMLTag.TITLE;
                        }
                    } else if (eventType == XmlPullParser.END_TAG){
                        if (xpp.getName().equals("item")){
                            //format data here or in adapter - consider revision
                           /* Date postDate = dateFormat.parse(pData.getDate());
                             pData.setDate(dateFormat.format(postDate));*/
                           /* pData.setDate(xpp.getText());//test later, not sure it behave the same
                            // as "pData.date = some date" (if date was public variable on Post class)
                            postDataList.add(pData);
                        } else{//if its any other close tag than item's ignore
                        currTag = RSSXMLTag.IGNORETAG;
                        }
                    }else if(eventType == XmlPullParser.TEXT){
                        String content = xpp.getText();
                        content = content.trim();
                        Log.d("debug",content);
                        if (pData != null){
                            switch(currTag){
                                case TITLE:
                                    if (content.length() != 0){
                                        if (pData != null){
                                            pData.setTopic(content);
                                        }
                                    }
                            }
                        }
                    }
            return null;
        }
    }

}*/
