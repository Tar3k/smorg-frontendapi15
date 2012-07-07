/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.smorg;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import com.google.api.services.calendar.model.Events;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tarek
 */
public class GetEventsHandler extends AsyncTask <String,Void,Events>{
    private final ProgressDialog progressDialog;
    private Events events;
    private Toast toast;

    public GetEventsHandler(Context context) {
        toast = new Toast(context);
        progressDialog = new ProgressDialog(context);
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.setTitle("Please Wait..");
        progressDialog.show();
    }


    
    @Override
    protected Events doInBackground(String... params) {
        try {
            Log.d("MyApp", "Getting Events");
            events = SplashActivity.calendarService.events().list(params[0]).execute();
            toast.setText("Done");
            return events;
        } catch (IOException ex) {
            Logger.getLogger(GetEventsHandler.class.getName()).log(Level.SEVERE, null, ex);
            toast.setText("Unable to get events from Google Calendar");
            return null;
        }
    }
    
    
    @Override
    protected void onPostExecute(Events result) {
        progressDialog.dismiss();
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
        super.onPostExecute(result);
        
    }
    
}
