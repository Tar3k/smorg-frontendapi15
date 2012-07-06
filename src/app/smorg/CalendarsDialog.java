/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.smorg;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import java.util.ArrayList;

/**
 *
 * @author Tarek
 */
public class CalendarsDialog extends DialogFragment
        implements DialogInterface.OnClickListener {
    
    private static SplashActivity parentActivity;
    private static final String title = "Choose a calendar";
    private static String[] calendarName;
    private static String[] calendarId;

    
    public static CalendarsDialog newInstance(SplashActivity parentActivity
            , ArrayList<String> calendarId, ArrayList<String> calendarName ){
        
        CalendarsDialog.calendarId = calendarId.toArray(new String[calendarId.size()]);
        CalendarsDialog.calendarName = calendarId.toArray(new String[calendarId.size()]);
        CalendarsDialog.parentActivity = parentActivity;
        CalendarsDialog calendarsDialog = new CalendarsDialog();
        return calendarsDialog;
        
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        
        return new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setItems(calendarName, this)
                .create();
    }
    
    

    public void onClick(DialogInterface dialog, int which) {
        dialog.dismiss();
        parentActivity.googleCalendarChosen(calendarId[which],calendarName[which]);
    }
    
}
