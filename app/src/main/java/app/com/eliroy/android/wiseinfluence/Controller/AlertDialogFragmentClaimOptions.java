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
       final PostDetailsActivity parent = (PostDetailsActivity) getActivity();

        return new AlertDialog.Builder(getActivity())
                .setItems(claimOptions, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        PostDetailsActivity parent = (PostDetailsActivity) getActivity();
                        String mail = parent.politicians.size() > 0 ? parent
                                .politicians.get(0).getEmail() : "";
                        Bundle postDetailsActivityIntentBundle = parent.getIntentBundle();
                        String subject  = postDetailsActivityIntentBundle.getString("TOPIC");
                        switch (i){
                            case 0: {
                                //========= according to SO question change to Uri ===========//
                                Intent intent = new Intent(Intent.ACTION_SENDTO);
                                String uriText = "mailto:" + Uri.encode(mail) +
                                        "?subject=" + Uri.encode("test subject") +
                                        "&body=" + Uri.encode("test content");
                                Uri uri = Uri.parse(uriText);
                                intent.setData(uri);
                                startActivity(Intent.createChooser(intent,"send mail"));

                                try {
                                    startActivity(Intent.createChooser(intent,"send mail"));
                                } catch(ActivityNotFoundException e){
                                    e.printStackTrace();
                                    Log.i("INFO", "No email client found");
                                    Toast.makeText(parent.getApplicationContext(),
                                            "No email app found, lease install and try again",
                                            Toast.LENGTH_LONG)
                                            .show();
                                }
                            }
                            case 1:{
                                // TODO: 09/09/2016 call facebook intent creator
                            }
                            case 2:{
                                // TODO: 09/09/2016 call phone call intent creator
                            }
                        }

                    }
                })
                .create();
    }

}


