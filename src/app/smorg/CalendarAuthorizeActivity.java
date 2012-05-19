/*
 * User authorized! so let's access the calendar
 */

package app.smorg;

import android.app.*;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
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

public class CalendarAuthorizeActivity extends Activity {
	
	final static private String API_KEY = "AIzaSyCNkqhXpMIaQc8A6idFNRXHQYWsaPVNKBY";
	private ArrayList<String> userCalendar = new ArrayList<String>();
	private ArrayList<String> userCalendarId= new ArrayList<String>();
	private Calendar calendarService ;
        private GoogleCredential googleCredential;
        private JacksonFactory jacksonFactory;
	private HttpTransport transport;
        private String accessToken;
        private ProgressDialog progressDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
           
            super.onCreate(savedInstanceState);
            Log.d("MyAPP","Access Calendar");
            //Getting the token back from authorization process
            accessToken = getIntent().getExtras().getString("token");
            // Setting up Calendar API Service
            setupCalendarConnection();
            getUserCalendars();
           
	}
	
	
        protected  String getAccessToken(){
            return accessToken ;
        }

	private void showDialog() {
		// TODO Auto-generated method stub
		Log.d("MyAPP","choose a cal");

		DialogFragment newFragment = new CalendarNamesDialog().instance("Choose Calendar:");
		newFragment.show(getFragmentManager(), "dialog");
		
	}

	private void setupCalendarConnection() {
	
                Log.d("MyAPP","Setup calendar connection");
                progressDialog= ProgressDialog.show(this, "Please Wait", "Connecting to Google Calendar", true, false);

		transport = AndroidHttp.newCompatibleTransport();
		googleCredential = new GoogleCredential().setAccessToken(getAccessToken());
		jacksonFactory = new JacksonFactory();
		calendarService = Calendar.builder(transport, jacksonFactory).setApplicationName("SmOrg/1.0 beta").setJsonHttpRequestInitializer(new JsonHttpRequestInitializer(){

			@Override
			public void initialize(JsonHttpRequest request) throws IOException {
				CalendarRequest calendarRequest = (CalendarRequest) request;
                                calendarRequest.setKey(API_KEY);
                                calendarRequest.setOauthToken(getAccessToken());
			}
		}).setHttpRequestInitializer(googleCredential).build();
                
                progressDialog.dismiss();
                Log.d("MyAPP","Lets get user calendars");
                
		
	}


	private void getUserCalendars() {
            
            Log.d("MyAPP","Inside the get user calendars function");
            new UserGoogleCalendars().execute();
        }
	
	private void quickAddEvent(int  calendar) {
	
            // TODO Auto-generated method stub
            Log.d("MyAPP","quick event ya sidi");
            setContentView(R.layout.quick_add_event);
            final int index = calendar;
            final EditText editText = (EditText) findViewById(R.id.quickadd);
            Button button = (Button) findViewById(R.id.setEvent);
            button.setOnClickListener(new OnClickListener(){
                
                @Override
                public void onClick(View arg0) {
                    String calendarId = userCalendarId.get(index);
                    String text = editText.getText().toString();
                    // TODO Auto-generated method stub
                    new QuickAddEvent().execute(calendarId,text);   
                }
            });
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
                String[] userCalendarString= userCalendar.toArray(new String[userCalendar.size()]);
                return new AlertDialog.Builder(getActivity()).setTitle(getArguments().getString("title")).setItems(userCalendarString, new DialogInterface.OnClickListener() {
		
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
		
                        // TODO Auto-generated method stub
                        quickAddEvent(which);	
                    }
                }).create();
            }

	}
        
        
       
        private class UserGoogleCalendars extends AsyncTask <Void,Void,Void> {

      
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog= ProgressDialog.show(CalendarAuthorizeActivity.this, "Please Wait", "Getting Calendar..", true, false);
            }

            @Override
            protected Void doInBackground(Void... params) {
         

                try {
                    CalendarList calendarList = calendarService.calendarList().list().execute();
                    Log.d("MyAPP","before while");
                    while(true){
                    
                        for (CalendarListEntry calendarListEntry : calendarList.getItems()){
                        // do something
                        Log.d("MyAPP","looping on calendars ");
                        Log.d("MyAPP",calendarListEntry.getSummary());
                        Log.d("MyAPP",calendarListEntry.getId());
                        
                        userCalendar.add(calendarListEntry.getSummary());
                        userCalendarId.add(calendarListEntry.getId());
			
                    }
                    String nextPageToken = calendarList.getNextPageToken();
                    if (nextPageToken != null && !nextPageToken.equalsIgnoreCase(""))
                        calendarList = calendarService.calendarList().list().setPageToken(nextPageToken).execute();	
                    break;
                }  } catch (IOException e) {
                // TODO Auto-generated catch block
                    Log.d("MyAPP", "Caught Exception @ getting calendars");
                }	
                 return null ;
            }

        
            @Override
            protected void onPostExecute(Void result) {
                Log.d("MyAPP", "Calendars Retrieved; Dialog should appear now y3ne");
                showDialog();
                progressDialog.dismiss();
            }
        }
        
        private class QuickAddEvent extends AsyncTask<String,Void,Void>{

       
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
               progressDialog= ProgressDialog.show(CalendarAuthorizeActivity.this, "Please Wait", "Updating Google Calendar..", true, false);
                
            }

            
            @Override
            protected Void doInBackground(String... params) {
                

                try {
                    calendarService.events().quickAdd(params[0] , params[1] ).execute();
                    Log.d("MyAPP","Event added!!!! ");
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    
                }
                return null;
           }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
        }
        }
        
}
