package app.smorg;

import android.app.ProgressDialog;
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
public class CreateEventHandler extends AsyncTask<Event, Void, Void> {

    private final CreateEventActivity parentActivity;
    private ProgressDialog progressDialog;
    private final String calendarId;
    private boolean isDone = false;

    public CreateEventHandler(CreateEventActivity parentActivity, String calendarId) {
        Log.d("MyApp", "Constructor : CreateEventHandler");
        this.parentActivity = parentActivity;
        this.calendarId = calendarId;

    }

    @Override
    protected void onPreExecute() {
        Log.d("MyApp", "PreExecute : CreateEventHandler");
        progressDialog = ProgressDialog.show(parentActivity,
                "Synchronizing...", "", true, false);
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Event... params) {
        Log.d("MyApp", "doInBackground : CreateEventHandler");
        try {
            SplashActivity.calendarService.events().insert(calendarId, params[0]).execute();
            isDone = true;

        } catch (IOException ex) {
            Logger.getLogger(CreateEventHandler.class.getName()).log(Level.SEVERE, null, ex);
            isDone = false;

        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        Log.d("MyApp", "PostExecute : CreateEventHandler");
        progressDialog.dismiss();

        if (isDone = true) {
            Toast.makeText(parentActivity, "Event Added", Toast.LENGTH_LONG).show();
            parentActivity.eventAdded();
        } else {
            Toast.makeText(parentActivity, "Unable to add event", Toast.LENGTH_LONG).show();
            
        }
        super.onPostExecute(result);
    }
}
