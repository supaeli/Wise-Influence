package app.com.eliroy.android.wiseinfluence.Controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import java.util.ArrayList;
import app.com.eliroy.android.wiseinfluence.Model.Politician;
import app.com.eliroy.android.wiseinfluence.Model.Post;
import app.com.eliroy.android.wiseinfluence.Model.Template;
import app.com.eliroy.android.wiseinfluence.R;
import app.com.eliroy.android.wiseinfluence.View.AlertDialogPoliticianFeed;
import app.com.eliroy.android.wiseinfluence.View.AlertDialogTemplatesFeed;

/*
* Display the selected post full info
* */

public class PostDetailsActivity extends FragmentActivity {

    public FragmentManager fragmentManager = getSupportFragmentManager();
    public ArrayList<Politician> politicians;
    public ArrayList<Template> templates;
    public Post post;
    public Template template;

    //====================== Lifecycle ================//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);
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

    public void onDialogSelectTemplate(DialogInterface dialogInterface, Template template) {

        this.template = template;
        dialogInterface.dismiss();
        AlertDialogPoliticianFeed alertDialogPoliticianFeed = new AlertDialogPoliticianFeed();
        alertDialogPoliticianFeed.show(fragmentManager,"Alert Dialog Politician Feed Fragment");
    }

    public void onDialogSelectPolitician(DialogInterface dialogInterface, Politician politician) {

        dialogInterface.dismiss();
        Intent viewTemplateIntent = new Intent(PostDetailsActivity.this, TemplateDetailsActivity.class);
        Bundle extras = new Bundle();
        extras.putSerializable("POST", post);
        extras.putSerializable("TEMPLATE", template);
        extras.putSerializable("POLITICIAN", politician);
        viewTemplateIntent.putExtras(extras);
        startActivity(viewTemplateIntent);
    }

    //=================== Actions ======================//

    /*
     * onClick for claim button
     * */
    public void claimTemplateOptions(View view) {
        AlertDialogTemplatesFeed alertDialogTemplatesFeed = new AlertDialogTemplatesFeed();
        alertDialogTemplatesFeed.show(fragmentManager, "Alert Dialog Templates Feed Fragment");
    }
}

