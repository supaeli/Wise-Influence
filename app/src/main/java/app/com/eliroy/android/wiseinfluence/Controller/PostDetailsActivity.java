package app.com.eliroy.android.wiseinfluence.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import app.com.eliroy.android.wiseinfluence.R;

public class PostDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);
        Bundle extras = getIntent().getExtras();

        TextView topic_txt_view = (TextView) findViewById(R.id.topic_txt_view);
        TextView date_txt_view = (TextView) findViewById(R.id.date_txt_view);
        TextView content_txt_view = (TextView) findViewById(R.id.content_txt_view);

        //topic_txt_view.setText(extras.getString("TOPIC"));
        topic_txt_view.setText(
                extras.getString("TOPIC")
        );

        date_txt_view.setText(extras.getString("DATE"));
        content_txt_view.setText(extras.getString("CONTENT"));
        setTitle(extras.getString("CONTENT"));
    }


}
