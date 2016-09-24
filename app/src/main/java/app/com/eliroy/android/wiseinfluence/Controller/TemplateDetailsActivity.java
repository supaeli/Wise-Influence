package app.com.eliroy.android.wiseinfluence.Controller;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import app.com.eliroy.android.wiseinfluence.Model.Post;
import app.com.eliroy.android.wiseinfluence.Model.Template;
import app.com.eliroy.android.wiseinfluence.R;

/**
 * Created by elay1_000 on 24/09/2016.
 */
public class TemplateDetailsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template_details);

        TextView topicTextView = (TextView) findViewById(R.id.topic_txt_view);
        TextView contentTextView = (TextView) findViewById(R.id.content_txt_view);

        Template template = (Template)getIntent().getSerializableExtra("TEMPLATE");
        Post post = (Post)getIntent().getSerializableExtra("POST");
        String topic = "בנושא" + " " + post.getTopic();
        topicTextView.setText(topic);
        contentTextView.setText( template.getContent());
    }

    public void onPhoneButtonClick(View view) {
    }

    public void onFacebookButtonClick(View view) {
    }

    public void onMailButtonClick(View view) {
    }
}
