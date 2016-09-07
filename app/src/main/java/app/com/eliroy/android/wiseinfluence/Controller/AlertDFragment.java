package app.com.eliroy.android.wiseinfluence.Controller;

import android.annotation.TargetApi;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import app.com.eliroy.android.wiseinfluence.R;

public class AlertDFragment extends DialogFragment {


    String[] comNames = {
            "כספים",
            "כלכלה",
            "חוץ ובטחון",
            "ועדת הכנסת",
            "פנים והגנת הסביבה",
            "עלייה קליטה ותפוצות",
            "חינוך תרבות וספורט",
            "עבודה, רווחה ובריאות",
            "ביקורת המדינה",
            "מעמד האישה",
            "מדע וטכנולוגיה"
    };

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
       return new AlertDialog.Builder(getActivity())//get activity is a long shot
        .setItems(comNames, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        })
       .create();
    }
}

