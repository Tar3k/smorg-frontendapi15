/*
 * User authorized! so let's access the calendar
 */
package app.smorg;

import android.app.*;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.google.api.client.extensions.android2.AndroidHttp;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.json.JsonHttpRequest;
import com.google.api.client.http.json.JsonHttpRequestInitializer;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarRequest;
import com.google.api.services.calendar.model.CalendarList;
import com.google.api.services.calendar.model.CalendarListEntry;
import java.io.IOException;
import java.util.ArrayList;

public class GoogleCalendarActivity extends Activity {

    private final static int REQUEST_CAL_TASK = 10;
    private final static int REQUEST_CAL_ACT = 30;
    private static final int CREATE_EVENT = 100;
    private static final int QUICKADD_EVENT = 200;
    private static final int VIEW_EVENTS = 300;
    private static final int CREATE_CALENDAR = 400;
    private static final int EDIT_CALENDAR = 500;
    private ArrayList<String> userCalendar = new ArrayList<String>();
    private ArrayList<String> userCalendarId = new ArrayList<String>();
    private Calendar calendarService;
    private GoogleCredential googleCredential;
    private JacksonFactory jacksonFactory;
    private HttpTransport transport;
    private String accessToken;
    private ProgressDialog progressDialog;
    private int calendarIndex = -1;
    private Intent intent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Log.d("MyAPP", "Access Calendar");
        setContentView(R.layout.cal);
        //Getting the token back from authorization process
        accessToken = getIntent().getExtras().getString("token");
        // Setting up Calendar API Service
        setupCalendarConnection();
        startActivityForResult(intent, REQUEST_CAL_TASK);

    }

    protected String getAccessToken() {
        return accessToken;
    }

    private void showDialog() {
        // TODO Auto-generated method stub
        Log.d("MyAPP", "choose a cal");

        DialogFragment newFragment = new CalendarNamesDialog().instance("Choose a Calendar:");
        newFragment.show(getFragmentManager(), "dialog");

    }

    private void setupCalendarConnection() {

        Log.d("MyAPP", "Setup calendar connection");
        progressDialog = ProgressDialog.show(GoogleCalendarActivity.this, "Please Wait", "Connecting to Google Calendar", true, false);

        transport = AndroidHttp.newCompatibleTransport();
        googleCredential = new GoogleCredential().setAccessToken(getAccessToken());
        jacksonFactory = new JacksonFactory();
        calendarService = Calendar.builder(transport, jacksonFactory).setApplicationName("SmOrg/1.0 beta").setJsonHttpRequestInitializer(new JsonHttpRequestInitializer() {

            @Override
            public void initialize(JsonHttpRequest request) throws IOException {
                CalendarRequest calendarRequest = (CalendarRequest) request;
                calendarRequest.setOauthToken(getAccessToken());
            }
        }).setHttpRequestInitializer(googleCredential).build();

        progressDialog.dismiss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.d("MyAPP", "result arrived");

        if (requestCode == REQUEST_CAL_TASK) {
            switch (resultCode) {
                case CREATE_EVENT:
                    getUserCalendars();
                    break;
                case QUICKADD_EVENT:
                    getUserCalendars();
                    //startActivityForResult(intent.setClass(this, CalendarQuickAddActivity.class),REQUEST_CAL_ACT);
                    break;
                case VIEW_EVENTS:
                    getUserCalendars();
                    break;
                case CREATE_CALENDAR:
                    getUserCalendars();
                    break;
                case EDIT_CALENDAR:
                    getUserCalendars();
                    break;
            }
        } else if (requestCode == REQUEST_CAL_ACT) {
            switch (resultCode) {
                case CREATE_EVENT:
                    break;

                case QUICKADD_EVENT:
                    quickAddEvent(data.getExtras().getString("event"));
                    break;
                case VIEW_EVENTS:
                    break;

                case CREATE_CALENDAR:
                    break;
                case EDIT_CALENDAR:
                    break;
            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void getUserCalendars() {

        Log.d("MyAPP", "Inside the get user calendars function");
        new UserGoogleCalendars().execute();
    }

    private void quickAddEvent(String text) {


        new QuickAddEvent().execute(userCalendarId.get(calendarIndex), text);
    }

    private class CalendarNamesDialog extends DialogFragment {

        public CalendarNamesDialog instance(String title) {
            // TODO Auto-generated method stub
            CalendarNamesDialog calendarNamesDialog = new CalendarNamesDialog();
            Bundle arguments = new Bundle();
            arguments.putString("title", title);
            calendarNamesDialog.setArguments(arguments);
            return calendarNamesDialog;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // TODO Auto-generated method stub
            String[] userCalendarString = userCalendar.toArray(new String[userCalendar.size()]);
            return new AlertDialog.Builder(getActivity()).setTitle(getArguments().getString("title")).setItems(userCalendarString, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    // TODO Auto-generated method stub
                    calendarIndex = which;

                }
            }).create();
        }
    }

    private class UserGoogleCalendars extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(GoogleCalendarActivity.this, "Please Wait", "Getting Calendar..", true, false);
        }

        @Override
        protected Void doInBackground(Void... params) {


            try {
                CalendarList calendarList = calendarService.calendarList().list().execute();
                Log.d("MyAPP", "before while");

                while (true) {

                    for (CalendarListEntry calendarListEntry : calendarList.getItems()) {
                        // do something
                        Log.d("MyAPP", "looping on calendars ");
                        Log.d("MyAPP", calendarListEntry.getSummary());
                        Log.d("MyAPP", calendarListEntry.getId());

                        userCalendar.add(calendarListEntry.getSummary());
                        userCalendarId.add(calendarListEntry.getId());

                    }
                    String nextPageToken = calendarList.getNextPageToken();
                    if (nextPageToken != null && !nextPageToken.equalsIgnoreCase("")) {
                        calendarList = calendarService.calendarList().list().setPageToken(nextPageToken).execute();
                    }
                    break;
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                Log.d("MyAPP", "Caught Exception @ getting calendars");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.d("MyAPP", "Calendars Retrieved; Dialog should appear now y3ne");
            showDialog();
            progressDialog.dismiss();
        }
    }

    private class QuickAddEvent extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(GoogleCalendarActivity.this, "Please Wait", "Updating Google Calendar..", true, false);

        }

        @Override
        protected Void doInBackground(String... params) {


            try {
                calendarService.events().quickAdd(params[0], params[1]).execute();
                Log.d("MyAPP", "Event added!!!! ");
            } catch (IOException e) {
                // TODO Auto-generated catch block
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            Toast.makeText(GoogleCalendarActivity.this, "Event Added", Toast.LENGTH_SHORT).show();
        }
    }
}
