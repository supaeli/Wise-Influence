package app.com.eliroy.android.wiseinfluence.Controller;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
import org.json.JSONObject;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import app.com.eliroy.android.wiseinfluence.Model.Politician;
import app.com.eliroy.android.wiseinfluence.Model.Post;
import app.com.eliroy.android.wiseinfluence.R;
import app.com.eliroy.android.wiseinfluence.Support.Network.ApiClient;
import app.com.eliroy.android.wiseinfluence.Support.Network.CallBack;

public class PostsFeedActivity extends FragmentActivity {

    private ApiClient client = new ApiClient();
    private ArrayList<Politician> politicians;



    FragmentManager fragmentManager = getSupportFragmentManager();// is this executed? the fragment parent issue

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts_feed);
        String[] URLS = getResources().getStringArray(R.array.RSS_channels_URL);
        reloadFeedWithURL(URLS[0]);
        loadPoliticians();
    }

    public void reloadFeedWithURL(String url){
        final Context context = this;
        client.reloadPostWithURL(url, new CallBack<ArrayList<Post>>() {
            @Override
            public void execute(final ArrayList<Post> result) {
                CustomAdapter adapter = new CustomAdapter(context, R.layout.list_item_template,result);
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
                                extras.putSerializable("POLITICIANS", politicians);
                                intent.putExtras(extras);
                                startActivity(intent);
                            }
                        }
                );
            }
        });
    }

    private void loadPoliticians() {
        client.loadPoliticians(new CallBack<ArrayList<Politician>>() {
            @Override
            public void execute(final ArrayList<Politician> result) {
                politicians = result;
            }
        });
    }

    /*
    * onClick method for category button
    * */
    public void showCategoriesListView(View view) {
        AlertDialogFragmentCategories alertDialogFragmentCategories = new AlertDialogFragmentCategories();
        alertDialogFragmentCategories.show(fragmentManager,"Alert Dialog Fragment");
    }
}
