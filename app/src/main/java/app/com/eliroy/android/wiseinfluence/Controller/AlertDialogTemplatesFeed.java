package app.com.eliroy.android.wiseinfluence.Controller;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import java.util.ArrayList;

import app.com.eliroy.android.wiseinfluence.Model.Template;
import app.com.eliroy.android.wiseinfluence.R;

/**
 * Created by elay1_000 on 24/09/2016.
 */
public class AlertDialogTemplatesFeed extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String[] templatesNames = getTemplatesNames();
        return new AlertDialog.Builder(getActivity()).setItems(templatesNames, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // TODO: 24/09/2016  start activity with template info


            }
        }).create();
    }

    @NonNull
    private String[] getTemplatesNames() {
        PostDetailsActivity parent = (PostDetailsActivity) getActivity();
        ArrayList<Template> templates = parent.templates;
        String[] templatesNames = new String[templates.size()];
        for (int i = 0; i < templates.size(); i++){
            templatesNames[i] = templates.get(i).getName();
        }
        return templatesNames;
    }
}
