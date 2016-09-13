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

import java.util.List;
import java.util.Stack;

import app.com.eliroy.android.wiseinfluence.R;

public class AlertDFragmentClaimOptions extends DialogFragment{

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
                        parent.getJSONWithURL();
                        switch (i){
                            case 0:{

                                Intent intent = new Intent(Intent.ACTION_SEND);
                                intent.setType("text/plain");
                                intent.putExtra(Intent.EXTRA_EMAIL, emails);//from db
                                intent.putExtra(Intent.EXTRA_SUBJECT,"בדיקה אילאי");// from db
                                intent.putExtra(Intent.EXTRA_TEXT,"בדיקה תוכן המייל");// from db
                                startActivity(parent.createEmailIntentChooser(intent, "שליחת מייל"));
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


