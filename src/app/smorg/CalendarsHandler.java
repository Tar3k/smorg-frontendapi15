/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.smorg;

import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import java.util.ArrayList;

/**
 *
 * @author Tarek
 */
public class CalendarsHandler extends AsyncTask<Void, Void, Void>{
    private final SplashActivity parentActivity;
    private ProgressDialog progressDialog;
    private ArrayList<String> calendarId;
    private ArrayList<String> calendarName;

    public CalendarsHandler(SplashActivity parentActivity) {
    
        this.parentActivity = parentActivity;
    }

   
    
    @Override
    protected void onPreExecute() {
        progressDialog = ProgressDialog.show(parentActivity
                ,"Connected"
                ,"Fetching your Google Calendars"
                ,true
                ,false);
        super.onPreExecute();
    }

    
    
    @Override
    protected Void doInBackground(Void... params) {
        calendarId = CalendarsRetrieval.retrieveId(SplashActivity.calendarService);
        calendarName= CalendarsRetrieval.retrieveName(SplashActivity.calendarService);
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
       
        progressDialog.dismiss();
        DialogFragment newFragment = CalendarsDialog.newInstance(parentActivity
                , calendarId, calendarName);
        newFragment.show(parentActivity.getFragmentManager(), "dialog");
        super.onPostExecute(result);
    }
    
    
    
    
}
