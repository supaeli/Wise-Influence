package app.com.eliroy.android.wiseinfluence.Controller;

import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import app.com.eliroy.android.wiseinfluence.R;

public class AlertDialogFragmentCategories extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String[] committeeNames = getResources().getStringArray(R.array.committee_hebrew_names);

        return new AlertDialog.Builder(getActivity())
                .setItems(committeeNames, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String[] URLS = getResources().getStringArray(R.array.RSS_channels_URL);

                        PostsFeedActivity parent = (PostsFeedActivity) getActivity();
                        parent.reloadFeedWithURL(parent.categories[i]);
                    }
                })
                .create();
    }

}

