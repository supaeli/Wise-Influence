package app.com.eliroy.android.wiseinfluence.Controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import app.com.eliroy.android.wiseinfluence.Model.Politician;
import app.com.eliroy.android.wiseinfluence.Model.Post;
import app.com.eliroy.android.wiseinfluence.Model.Template;
import app.com.eliroy.android.wiseinfluence.R;
import app.com.eliroy.android.wiseinfluence.View.AlertDialogTemplatesFeed;

public class PostDetailsActivity extends FragmentActivity {

    FragmentManager fragmentManager = getSupportFragmentManager();
    public ArrayList<Politician> politicians;
    public ArrayList<Template> templates;
    public Post post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);
        // TODO: 18/09/2016 take care of unchecked cast
        politicians = (ArrayList<Politician>)getIntent().getSerializableExtra("POLITICIANS");
        templates = (ArrayList<Template>)getIntent().getSerializableExtra("TEMPLATES");
        TextView topic_txt_view = (TextView) findViewById(R.id.topic_txt_view);
        TextView date_txt_view = (TextView) findViewById(R.id.date_txt_view);
        TextView content_txt_view = (TextView) findViewById(R.id.content_txt_view);

        post = (Post)getIntent().getSerializableExtra("POST");

        topic_txt_view.setText(post.getTopic());
        date_txt_view.setText(post.getDate());
        content_txt_view.setText(post.getContent());
        //setter for Post instance, check later
        setTitle(post.getTopic());

    }


    /*
    * onClick for claim button
    * */
    public void claimTemplateOptions(View view) {
        AlertDialogTemplatesFeed alertDialogTemplatesFeed = new AlertDialogTemplatesFeed();
        alertDialogTemplatesFeed.show(fragmentManager, "Alert Dialog Templates Feed Fragment");
    }

    /*
    *
    * */
    public Intent createEmailIntentChooser(Intent source, CharSequence chooserTitle){
        Stack<Intent> intents = new Stack<Intent>();
        Intent i = new Intent(Intent.ACTION_SENDTO
                , Uri.fromParts("mailto:","elay17@live.com",null));
        List<ResolveInfo> activities = getPackageManager().queryIntentActivities(i, 0);

        for(ResolveInfo resolveInfo : activities){
            Intent target = new Intent(source);
            //target = target.setPackage(); - study this one further
            intents.add(target);
        }

        if(!intents.isEmpty()){
            Intent chooserIntent = Intent.createChooser(intents.remove(0), chooserTitle);
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS
                    , intents.toArray(new Parcelable[intents.size()]));

            return chooserIntent;
        }
        else{
            return Intent.createChooser(source, chooserTitle);
        }
    }
    //=================== Actions ======================//

    public void onDialogSelectTemplate(DialogInterface dialogInterface, Template template) {

        Intent viewTemplateIntent = new Intent(PostDetailsActivity.this, TemplateDetailsActivity.class);
        Bundle extras = new Bundle();
        extras.putSerializable("TEMPLATE", template);
        extras.putSerializable("POST", post);
        viewTemplateIntent.putExtras(extras);
        startActivity(viewTemplateIntent);
    }
}

