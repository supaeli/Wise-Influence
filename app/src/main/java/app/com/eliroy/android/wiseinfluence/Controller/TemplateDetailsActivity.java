package app.com.eliroy.android.wiseinfluence.Controller;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import app.com.eliroy.android.wiseinfluence.Model.Politician;
import app.com.eliroy.android.wiseinfluence.Model.Post;
import app.com.eliroy.android.wiseinfluence.Model.Template;
import app.com.eliroy.android.wiseinfluence.R;

/**
 * Created by elay1_000 on 24/09/2016.
 */
public class TemplateDetailsActivity extends Activity {

    private ArrayList<Politician> politicians;
    private Post post;
    private Template template;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template_details);

        TextView topicTextView = (TextView) findViewById(R.id.topic_txt_view);
        TextView contentTextView = (TextView) findViewById(R.id.content_txt_view);

        template = (Template)getIntent().getSerializableExtra("TEMPLATE");
        post = (Post)getIntent().getSerializableExtra("POST");
        politicians = (ArrayList<Politician>)getIntent().getSerializableExtra("POLITICIANS");

        if (post != null && post.getTopic() != null && post.getTopic().length() != 0) {
            String topic = "בנושא" + " " + post.getTopic();
            topicTextView.setText(topic);
        }

        if(template != null && template.getContent() != null && template.getContent().length() != 0) {
            contentTextView.setText(template.getContent());
        }
    }

    public void onPhoneButtonClick(View view) {
        Politician politician = getPolitician();
        if(politician == null || politician.getPhone() == null || politician.getPhone().length() == 0) {
            Toast.makeText(getApplicationContext(), "לא נמצא מספר טלפון לחבר הכנסת המבוקש", Toast.LENGTH_LONG).show();
        }
        else {
            String phoneString = politician.getPhone();
            String uri = "tel:" + phoneString;
            Intent phoneCallIntent = new Intent(Intent.ACTION_DIAL);
            phoneCallIntent.setData(Uri.parse(uri));
            startActivity(phoneCallIntent);
        }

    }

    public void onFacebookButtonClick(View view) {
        Politician politician = getPolitician();

        if (politician == null || politician.getFacebook() == null || politician.getFacebook().length() == 0){
            Toast.makeText(getApplicationContext(), "לא נמצא חשבון פייסבוק לחבר הכנסת המבוקש",Toast.LENGTH_LONG).show();
        }
        else {
            Intent facebookIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(politician.getFacebook()));
            startActivity(facebookIntent);
        }
    }

    public void onMailButtonClick(View view) {
        Politician politician = getPolitician();

        if (politician == null  || politician.getEmail() == null || post == null || post.getTopic() == null
                || template == null  || template.getContent() == null ) {
           Toast.makeText(getApplicationContext(),"לא נמצא חשבון מייל לחבר הכנסת המבוקש",Toast.LENGTH_LONG).show();
        }
        else {
            String topic = post.getTopic();
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
            String uriText = "mailto:" + Uri.encode(politician.getEmail()) +
                    "?subject=" + Uri.encode(topic) +
                    "&body=" + Uri.encode(template.getContent());
            Uri uri = Uri.parse(uriText);
            emailIntent.setData(uri);
            startActivity(Intent.createChooser(emailIntent, "send mail"));

            try {
                startActivity(Intent.createChooser(emailIntent,"send mail"));
            } catch(ActivityNotFoundException e){
                e.printStackTrace();
                Log.i("INFO", "No email client found");
                Toast.makeText(getApplicationContext(), "No email app found, lease install and try again", Toast.LENGTH_LONG).show();
            }
        }
    }

    private Politician getPolitician(){
        if(politicians != null) {
            Politician politician = politicians.size() > 0 ? politicians.get(0) : null;
            return politician;
        }
        return null;
    }
}
