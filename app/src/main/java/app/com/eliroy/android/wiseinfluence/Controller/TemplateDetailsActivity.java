package app.com.eliroy.android.wiseinfluence.Controller;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.content.ClipboardManager;
import android.content.ClipData;

import java.io.InputStreamReader;
import java.util.ArrayList;

import app.com.eliroy.android.wiseinfluence.Model.Politician;
import app.com.eliroy.android.wiseinfluence.Model.Post;
import app.com.eliroy.android.wiseinfluence.Model.Template;
import app.com.eliroy.android.wiseinfluence.R;

/**
 * Display the template before contact method is decided (e.g. facebook, email).
 */
public class TemplateDetailsActivity extends Activity {

    private Politician politician;
    private Post post;
    private Template template;

    //====================== Lifecycle ================//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template_details);

        TextView topicTextView = (TextView) findViewById(R.id.topic_txt_view);
        TextView contentTextView = (TextView) findViewById(R.id.content_txt_view);

        template = (Template)getIntent().getSerializableExtra("TEMPLATE");
        post = (Post)getIntent().getSerializableExtra("POST");
        politician = (Politician)getIntent().getSerializableExtra("POLITICIAN");

        if (post != null && post.getTopic() != null && post.getTopic().length() != 0) {
            String topic = "בנושא" + " " + post.getTopic();
            topicTextView.setText(topic);
        }

        if(template != null && template.getContent() != null && template.getContent().length() != 0) {
            contentTextView.setText(template.getContent());
        }
    }

    //====================== Actions ================//

    public void onPhoneButtonClick(View view) {
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

        if (politician == null || politician.getFacebook() == null || politician.getFacebook().equals("null") || politician.getFacebook().length() == 0){
            Toast.makeText(getApplicationContext(), "לא נמצא חשבון פייסבוק לחבר הכנסת המבוקש",Toast.LENGTH_LONG).show();
        }
        else {
            Intent facebookIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(politician.getFacebook()));
            //copying the selected template to phone's clipboard
            Uri.encode(template.getContent());
            String topic = post.getTopic();
            String uriText = Uri.encode(topic)+ Uri.encode(template.getContent());
            //Uri uri = Uri.parse(uriText);
            uriText = post.getTopic() + " " +template.getContent();

            ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Facebook Post", uriText);
            clipboard.setPrimaryClip(clip);

            startActivity(facebookIntent);
            startActivity(facebookIntent);
        }
    }

    public void onMailButtonClick(View view) {

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
}
