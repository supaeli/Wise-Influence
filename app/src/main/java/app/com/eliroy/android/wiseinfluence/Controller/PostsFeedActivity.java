package app.com.eliroy.android.wiseinfluence.Controller;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import app.com.eliroy.android.wiseinfluence.Model.Post;
import app.com.eliroy.android.wiseinfluence.R;

public class PostsFeedActivity extends AppCompatActivity {

    private Post[] posts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts_feed);

        generatePseudoData();
        ListView myList = (ListView)findViewById(R.id.feed_list_view);
        CustomAdapter myArrayAdapter = new CustomAdapter(this,R.layout.list_item_template, posts);
        myList.setAdapter(myArrayAdapter);

        //test call the HTTPDownloadTask
        new HTTPDownloadTask().execute("http://main.knesset.gov.il/Activity/committees/Finance/News"
          + "/_layouts/15/listfeedkns.aspx?List=9559f688-b470-4701-a379-fdd168efea09&View=404e1bcf-"
         + "c1cc-4911-ba46-aeb4b783f9c3");
    }

    private void generatePseudoData(){
        Post data = null;
        posts = new Post[10];

        for (int i = 0; i < 10; i++){
            data = new Post();
            posts[i] = data;
        }
    }
    private class HTTPDownloadTask extends AsyncTask<String,Integer, Post[]>{

        @Override
        protected Post[] doInBackground(String... params) {

            String urlString = params[0];
            InputStream inputStream = null;
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

                //read string
                final int bufferSize = 1024;
                byte[] buffer = new byte[1024];
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                while (true){
                    int count = inputStream.read(buffer, 0, bufferSize);
                    if (count == -1){
                        break;
                    }
                    outputStream.write(buffer);
                }
                outputStream.close();

                String res = new String(outputStream.toByteArray(), "UTF-8");
                Log.d("debug", res);
            }catch (MalformedURLException e){
                e.printStackTrace();
            }
            catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }

    }

}
