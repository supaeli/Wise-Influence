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
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Stack;

import app.com.eliroy.android.wiseinfluence.R;

public class PostDetailsActivity extends FragmentActivity {

    FragmentManager fm = getSupportFragmentManager();

    private static String url = "https://www.dropbox.com/s/1bnnvaamyp62qeo/testdbjson.json?dl=0";

    //JSON Node Names
    private static final String TAG_POLITICIAN = "politicians";
    private static final String TAG_NAME = "polName";
    private static final String TAG_EMAIL = "polMail";

    private JsonDownload task;

    JSONArray politician = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);
        Bundle extras = getIntent().getExtras();

        TextView topic_txt_view = (TextView) findViewById(R.id.topic_txt_view);
        TextView date_txt_view = (TextView) findViewById(R.id.date_txt_view);
        TextView content_txt_view = (TextView) findViewById(R.id.content_txt_view);

        topic_txt_view.setText(extras.getString("TOPIC"));
        date_txt_view.setText(extras.getString("DATE"));
        content_txt_view.setText(extras.getString("CONTENT"));
        setTitle(extras.getString("TOPIC"));

    }
    //resolve ambiguity with JSONParser class
    public void getJSONWithURL(){
        try {
            URL url = new URL(this.url);
            task = new JsonDownload();
            task.execute();
        }
        catch (MalformedURLException e){
            e.printStackTrace();
        }
    }
    /*
    * onClick for claim button*/
    public void claimOption(View view) {
        AlertDFragmentClaimOptions alertDFragmentClaimOptions = new AlertDFragmentClaimOptions();
        alertDFragmentClaimOptions.show(fm,"Alert Dialog Fragment");
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
    private class JsonDownload extends AsyncTask<URL, String, JSONObject>{

        InputStream inputStream = null;
        String res = "";
        private ProgressDialog progressDialog = new ProgressDialog(PostDetailsActivity.this);

        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Getting Json");
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    JsonDownload.this.cancel(true);
                }
            });
        }

        // TODO: 14/09/2016 this method is parsing to json - revise
        @Override
        protected JSONObject doInBackground(URL... urls){
          JSONParser parser = new JSONParser();
          JSONObject json = parser.getJSONFromUrl(url);
            return json;
        }
        @Override
        protected void onPostExecute(JSONObject json) {
            progressDialog.dismiss();
            try {
                // Getting JSON Array
                politician = json.getJSONArray(TAG_POLITICIAN);
                JSONObject c = politician.getJSONObject(0);
                // maybe asyncTask will return jsonobjectarray and on intent "fire" we'll
                // resolve which element to extract

                // Storing  JSON item in a Variable
                String name = c.getString(TAG_NAME);
                String email = c.getString(TAG_EMAIL);

                /*//Set JSON Data in TextView
                uid.setText(id);
                name1.setText(name);
                email1.setText(email);*/

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
