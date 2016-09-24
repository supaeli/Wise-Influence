package app.com.eliroy.android.wiseinfluence.Controller;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v4.app.DialogFragment;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import app.com.eliroy.android.wiseinfluence.Model.Politician;
import app.com.eliroy.android.wiseinfluence.R;

public class AlertDialogFragmentClaimOptions extends DialogFragment{

    PostDetailsActivity parent = null;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String[] claimOptions = getResources().getStringArray(R.array.claim_options);
        parent =(PostDetailsActivity) getActivity();

        return new AlertDialog.Builder(getActivity())
                .setItems(claimOptions, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // TODO: 23/09/2016 define logic to use certain politician
                        switch (i){
                            case 0: {
                                showEmailClaim();
                                break;
                            }
                            case 1:{
                                showFacebookClaim();
                                break;
                            }
                            case 2:{
                                // TODO: 09/09/2016 call phone call intent creator
                                showPhoneCallClaim();
                                break;
                            }
                        }
                    }
                })
                .create();
    }

    private void showEmailClaim() {
        // add dialog to choose which politician is to be contacted
        String email = parent.politicians.size() > 0 ? parent.politicians.get(0).getEmail() : "";
        String topic = parent.post.getTopic();

        String templateContent = "";


        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        String uriText = "mailto:" + Uri.encode(email) +
                "?subject=" + Uri.encode(topic) +
                "&body=" + Uri.encode(templateContent);
        Uri uri = Uri.parse(uriText);
        emailIntent.setData(uri);
        startActivity(Intent.createChooser(emailIntent,"send mail"));

        try {
            startActivity(Intent.createChooser(emailIntent,"send mail"));
        } catch(ActivityNotFoundException e){
            e.printStackTrace();
            Log.i("INFO", "No email client found");
            Toast.makeText(parent.getApplicationContext(), "No email app found, lease install and try again", Toast.LENGTH_LONG).show();
        }
    }
    /*
    * Open politician FB page
    * */
    private void showFacebookClaim() {

        String facebookURL = parent.politicians.size() > 0 ? parent.politicians.get(0).getFacebook() : "";
        if (facebookURL == null || facebookURL.length() == 0){
            Toast.makeText(parent.getApplicationContext(), "לא נמצא חשבון פייסבוק לחבר הכנסת המבוקש",Toast.LENGTH_LONG).show();
        }
        else {
            Intent facebookIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(facebookURL));
            startActivity(facebookIntent);
        }
    }

    private void showPhoneCallClaim() {
        Politician politician = getPolitician();
        if(politician == null || politician.getPhone().length() == 0) {
            Toast.makeText(parent.getApplicationContext(), "לא נמצא מספר טלפון לחבר הכנסת המבוקש", Toast.LENGTH_LONG).show();
        }
        else {
            String phoneString = politician.getPhone();
            String uri = "tel:" + phoneString;
            Intent phoneCallIntent = new Intent(Intent.ACTION_DIAL);
            phoneCallIntent.setData(Uri.parse(uri));
            startActivity(phoneCallIntent);
        }
    }
    
    /*
    * get the relevant politician object according to category - later
    * */
    // TODO: 24/09/2016 add method call to all claim options 
    private Politician getPolitician(){
        Politician politician = parent.politicians.size() > 0 ? parent.politicians.get(0) : null;
        return politician;
    }
}


