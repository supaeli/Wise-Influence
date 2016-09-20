package app.com.eliroy.android.wiseinfluence.Controller;

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

import java.util.List;
import java.util.Stack;

import app.com.eliroy.android.wiseinfluence.R;

public class AlertDialogFragmentClaimOptions extends DialogFragment{

    String[] emails = {"knizer.nehama@gmail.com, roycn90@gmail.com"}; // dummy data - later from db


    @NonNull
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
                        switch (i){
                            case 0:{
                                Log.v("DEBUG",mail);
                                Intent intent = new Intent(Intent.ACTION_SENDTO);
                                intent.setAction(Intent.ACTION_SENDTO);
                                intent.setType("message/rfc822");
                                intent.setData(Uri.parse("mailto:"));
                                intent.putExtra(intent.EXTRA_EMAIL,parent
                                        .politicians.get(0)
                                        .getEmail());
                                intent.putExtra(intent.EXTRA_SUBJECT,"test subject");
                                intent.putExtra(intent.EXTRA_TEXT,"test letter content");

                                try {
                                    startActivity(intent);
                                } catch(Exception e){
                                    e.printStackTrace();
                                }
                                //========= prepare uri to pass email data =========//
                                /*
                                String uriText = "mailto:" + Uri.encode(mail) +
                                        "?subject=" + Uri.encode("subject") +
                                        "&body=" + Uri.encode("body");
                                Uri uri = Uri.parse(uriText);
                                intent.setData(uri);
                                startActivity(parent.createEmailIntentChooser(intent, "שליחת מייל"));
                                */
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


