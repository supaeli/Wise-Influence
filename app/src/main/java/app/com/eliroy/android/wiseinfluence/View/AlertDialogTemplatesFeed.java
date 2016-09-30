package app.com.eliroy.android.wiseinfluence.View;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import java.util.ArrayList;

import app.com.eliroy.android.wiseinfluence.Controller.PostDetailsActivity;
import app.com.eliroy.android.wiseinfluence.Model.Template;
import app.com.eliroy.android.wiseinfluence.R;

public class AlertDialogTemplatesFeed extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String[] templatesNames = getTemplatesNames();
        return new AlertDialog.Builder(getActivity()).setItems(templatesNames, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                PostDetailsActivity parent = (PostDetailsActivity) getActivity();
                Template template = parent.templates.get(i);
                parent.onDialogSelectTemplate(dialogInterface, template);
            }
        }).create();
    }

    private String[] getTemplatesNames() {
        PostDetailsActivity parent = (PostDetailsActivity) getActivity();
        ArrayList<Template> templates = parent.templates;
        if(templates != null) {
            String[] templatesNames = new String[templates.size()];

            for (int i = 0; i < templates.size(); i++) {
                templatesNames[i] = templates.get(i).getName();
            }

            return templatesNames;
        }
        return new String[0];
    }
}
