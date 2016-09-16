package app.com.eliroy.android.wiseinfluence.Controller;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import app.com.eliroy.android.wiseinfluence.Model.Politician;
import app.com.eliroy.android.wiseinfluence.R;

public class PostDetailsActivity extends FragmentActivity {

    FragmentManager fm = getSupportFragmentManager();
    ArrayList<Politician> politicians;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);
        politicians = (ArrayList<Politician>)getIntent()
                .getSerializableExtra("POLITICIANS");
        Log.v("Debug",politicians+"");
        Bundle extras = getIntent().getExtras();

        TextView topic_txt_view = (TextView) findViewById(R.id.topic_txt_view);
        TextView date_txt_view = (TextView) findViewById(R.id.date_txt_view);
        TextView content_txt_view = (TextView) findViewById(R.id.content_txt_view);
        // TODO: 14/09/2016 decide how to use TOPIC on the fragment as claim subject
        topic_txt_view.setText(extras.getString("TOPIC"));
        date_txt_view.setText(extras.getString("DATE"));
        content_txt_view.setText(extras.getString("CONTENT"));
        setTitle(extras.getString("TOPIC"));

    }


    /*
    * onClick for claim button
    * */
    public void claimOption(View view) {
        AlertDialogFragmentClaimOptions alertDialogFragmentClaimOptions = new AlertDialogFragmentClaimOptions();
        alertDialogFragmentClaimOptions.show(fm,"Alert Dialog Fragment");
    }
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
        }

