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
    private Toast toast;
    public UpdateEventHandler(Context context) {
        toast = new Toast(context);
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
            toast.setText("Event Updated");
        } catch (IOException ex) {
            Logger.getLogger(CreateEventHandler.class.getName()).log(Level.SEVERE, null, ex);
            toast.setText("Couldn't Update");
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        Log.d("MyApp", "PostExecute : uptadeEventHandler");
        progressDialog.dismiss();
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
        
        super.onPostExecute(result);
    }
}
