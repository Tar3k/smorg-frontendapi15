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
    private Toast toast;

    public CreateEventHandler(CreateEventActivity parentActivity, String calendarId) {
        Log.d("MyApp", "Constructor : CreateEventHandler");
        this.parentActivity = parentActivity;
        this.calendarId = calendarId;
        toast = new Toast(parentActivity);

    }

    @Override
    protected void onPreExecute() {
        Log.d("MyApp", "PreExecute : CreateEventHandler");
        progressDialog = ProgressDialog.show(parentActivity, "Synchronizing...", "", true, false);
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Event... params) {
        Log.d("MyApp", "doInBackground : CreateEventHandler");
        try {
            SplashActivity.calendarService.events().insert(calendarId, params[0]).execute();
            toast.setText("Event Added");
        } catch (IOException ex) {
            Logger.getLogger(CreateEventHandler.class.getName()).log(Level.SEVERE, null, ex);
            toast.setText("Unable to add event");
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        Log.d("MyApp", "PostExecute : CreateEventHandler");
        progressDialog.dismiss();
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
        parentActivity.eventAdded();
        super.onPostExecute(result);
    }
}
