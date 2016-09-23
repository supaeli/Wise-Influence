package app.com.eliroy.android.wiseinfluence.Controller;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.List;
import java.util.Stack;

import app.com.eliroy.android.wiseinfluence.R;

public class AlertDialogFragmentClaimOptions extends DialogFragment{


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String[] claimOptions = getResources().getStringArray(R.array.claim_options);

        return new AlertDialog.Builder(getActivity())
                .setItems(claimOptions, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        switch (i){
                            case 0: {
                                showEmailClaim();
                            }
                            case 1:{
                                // TODO: 09/09/2016 call facebook intent creator
                               // showFacebookClaim();
                            }
                            case 2:{
                                // TODO: 09/09/2016 call phone call intent creator
                                //showPhoneCallClaim();
                            }
                        }
                    }
                })
                .create();
    }

    private void showEmailClaim() {
        PostDetailsActivity parent = (PostDetailsActivity) getActivity();
        String email = parent.politicians.size() > 0 ? parent.politicians.get(0).getEmail() : "";
        String topic = parent.post.getTopic();

        String templateContent = "";
        //find and assign template content
        for (int templatesIndex = 0;
             templatesIndex < parent.templates.size(); templatesIndex++){

            if(parent.templates.get(templatesIndex).getCategory()
                    .equals(parent.post.getParentCategory())){
                templateContent = parent.templates.get(templatesIndex).getContent();
            }
        }

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
            Toast.makeText(parent.getApplicationContext(),
                    "No email app found, lease install and try again",
                    Toast.LENGTH_LONG)
                    .show();
        }
    }

}


