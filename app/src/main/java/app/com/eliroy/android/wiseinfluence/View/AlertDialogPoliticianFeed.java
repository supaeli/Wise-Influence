package app.com.eliroy.android.wiseinfluence.View;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import java.util.ArrayList;

import app.com.eliroy.android.wiseinfluence.Controller.PostDetailsActivity;
import app.com.eliroy.android.wiseinfluence.Model.Politician;

/**
 * Display feed of politicians to contact
 */
public class AlertDialogPoliticianFeed extends DialogFragment{

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        String[] politiciansNames = getPoliticiansNames();
        return new AlertDialog.Builder(getActivity())
                .setTitle("בחירת ח\"כ")
                .setItems(politiciansNames, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                PostDetailsActivity parent = (PostDetailsActivity) getActivity();
                Politician politician = parent.politicians.get(i);
                parent.onDialogSelectPolitician(dialogInterface, politician);
            }
        }).create();
    }

    private String[] getPoliticiansNames() {
        PostDetailsActivity parent = (PostDetailsActivity) getActivity();
        ArrayList<Politician> politicians = parent.politicians;
        if (politicians != null) {
            String[] politiciansNames = new String[politicians.size()];
            for (int i = 0; i < politicians.size(); i++) {
                politiciansNames[i] = politicians.get(i).getName();
            }

            return politiciansNames;
        }
        return new String[0];
    }
}
