/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.smorg;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import com.google.api.services.calendar.Calendar;

/**
 *
 * @author Tarek
 */
public class GoogleCalendarHandler extends AsyncTask<Void, Void, Void> {

    private final SplashActivity parentActivity;
    private final String token;
    private ProgressDialog progressDialog;
    private Calendar calendarService;

    public GoogleCalendarHandler(SplashActivity parentActivity, String token) {
        this.parentActivity = parentActivity;
        this.token = token;
    }

    @Override
    protected void onPreExecute() {
        progressDialog= ProgressDialog.show(parentActivity
                ,"Connecting.."
                ,"Contacting Google Services.."
                ,true
                ,false);
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... params) {
        calendarService = GoogleCalendarConnection.connect(token);
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        progressDialog.dismiss();
        parentActivity.googleCalendarConnected(calendarService);
    }
}
