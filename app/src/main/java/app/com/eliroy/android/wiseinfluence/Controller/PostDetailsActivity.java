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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Stack;

import app.com.eliroy.android.wiseinfluence.R;

public class PostDetailsActivity extends FragmentActivity {

    FragmentManager fm = getSupportFragmentManager();

    private static String url = "https://www.dropbox.com/s/75k38pu2fyetbkd/minidbtest.json?dl=0";

    //JSON Node Names
    private static final String TAG_POLITICIAN = "politicians";
    private static final String TAG_NAME = "polName";
    private static final String TAG_EMAIL = "polMail";

    private JSONArray politicians;

    private jsonDownload task;
    public String jsonString = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);
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
    //always go to same static json
    public void getJsonObj(){
        task = new jsonDownload();
        task.execute(this.url);
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
    private class jsonDownload extends AsyncTask<String, String, JSONObject>{



        InputStream inputStream = null;
        String res = "";
        JSONObject json;
        private ProgressDialog progressDialog = new ProgressDialog(PostDetailsActivity.this);

        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Getting Json");
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    jsonDownload.this.cancel(true);
                }
            });
        }

        @Override
        protected JSONObject doInBackground(String... urls) {

            android.os.Debug.waitForDebugger();

            try {
                json = getJson(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            // TODO: 14/09/2016 dismiss also when user click soft back button
            progressDialog.dismiss();
            //extract json relevant contents to the global string variable
            try {
                // Getting JSON Array
                politicians = json.getJSONArray(TAG_POLITICIAN);
                JSONObject c = politicians.getJSONObject(0);

                // Storing  JSON item in a Variable
                String name = c.getString(TAG_NAME);
                String email = c.getString(TAG_EMAIL);

                //Set JSON Data in global string var
                // TODO: 14/09/2016 iterate throught all emails of politicians on json, later just those with correct criteria
                jsonString += email;
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

        private JSONObject getJson(String urlString) throws IOException {
            JSONObject json = null;
            InputStream is = null;
            URL url = null;
            String jsonStr= null;

            try {
                url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                is = connection.getInputStream();
                //create here the json object from input stream
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        is, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }

                is.close();
                jsonStr = sb.toString();

                // try parse the string to a JSON object
                try {
                    json = new JSONObject(jsonStr);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } catch (MalformedURLException e){
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                is.close();
            }

            return json;
        }
    }

