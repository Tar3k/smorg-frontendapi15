/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.smorg;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
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

    public GetEventsHandler(Context context) {
   
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
            return SplashActivity.calendarService.events().list(params[0]).execute();
        } catch (IOException ex) {
            Logger.getLogger(GetEventsHandler.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    @Override
    protected void onPostExecute(Events result) {
        progressDialog.dismiss();
        super.onPostExecute(result);
        
    }
    
}
