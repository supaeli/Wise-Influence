package app.com.eliroy.android.wiseinfluence.Controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import app.com.eliroy.android.wiseinfluence.R;

public class PostsFeedActivity extends AppCompatActivity {

    private String[] items = {"Post1", "Post2", "Post3", "Post4", "Post5", "Post6"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts_feed);

        ListView myList = (ListView)findViewById(R.id.feed_list_view);
        ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>(this,R.layout.simple_post_item_template, items);
        myList.setAdapter(myArrayAdapter);
    }
}
