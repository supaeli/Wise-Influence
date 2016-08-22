package app.com.eliroy.android.wiseinfluence.Controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
    }

    private void generatePseudoData(){
        Post data = null;
        posts = new Post[10];

        for (int i = 0; i < 10; i++){
            data = new Post();
            posts[i] = data;
        }
    }

}
