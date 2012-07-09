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
import com.google.api.services.calendar.model.Event;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tarek
 */
public class UpdateEventHandler extends AsyncTask<Event, Void, Void> {

    private final ProgressDialog progressDialog;
    private Toast finishToast;
    private Toast errorToast;
    private boolean isDone = false ;
    public UpdateEventHandler(Context context) {
        finishToast = Toast.makeText(context, "Done..", Toast.LENGTH_LONG);
        errorToast =Toast.makeText(context, "Unable to update", Toast.LENGTH_LONG);
        progressDialog = new ProgressDialog(context);
    }

    @Override
    protected void onPreExecute() {
        Log.d("MyApp", "PreExecute : UpdateEventHandler");
        progressDialog.setTitle("Updating");
        progressDialog.show();
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Event... params) {
        Log.d("MyApp", "doInBackground : UpdateEventHandler");
        try {
            SplashActivity.calendarService.events().update(SplashActivity.calendarIdChosen,
                    params[0].getId(),
                    params[0]).execute();
            isDone= true;
            
        } catch (IOException ex) {
            Logger.getLogger(CreateEventHandler.class.getName()).log(Level.SEVERE, null, ex);
            isDone = false;
         
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        Log.d("MyApp", "PostExecute : uptadeEventHandler");
        if(isDone = true){
                finishToast.show();
        }else{
               errorToast.show();
        }
        progressDialog.dismiss();
        super.onPostExecute(result);
    }
}
