/*
 * User authorized! so let's access the calendar
 */

package app.smorg;

import java.io.IOException;
import java.util.ArrayList;

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
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CalendarAccess extends Activity {
	
	final static private String API_KEY = "AIzaSyCNkqhXpMIaQc8A6idFNRXHQYWsaPVNKBY";
	
	private ArrayList<String> userCalendar = new ArrayList<String>();
	private ArrayList<String> userCalendarId= new ArrayList<String>();
	
	private Calendar calendarService ;
	private HttpTransport transport;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
               Log.d("MyAPP","Access Calendar");

		//Getting the token back from authorization process
		String accessToken = getIntent().getExtras().getString("token");
		
		// Setting up Calendar API Service
		try {
			setupCalendarConnection(accessToken);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	


	private void showDialog() {
		// TODO Auto-generated method stub
		                Log.d("MyAPP","choose a cal");

		DialogFragment newFragment = new CalendarNamesDialog().instance("Choose Calendar:");
		newFragment.show(getFragmentManager(), "dialog");
		
	}

	private void setupCalendarConnection(final String accessToken) throws IOException {
	
                Log.d("MyAPP","Setup calendar connection");

		 transport = AndroidHttp.newCompatibleTransport();
		GoogleCredential googleCredential = new GoogleCredential().setAccessToken(accessToken);
		JacksonFactory jacksonFactory = new JacksonFactory();
		
		calendarService = Calendar.builder(transport, jacksonFactory).setApplicationName("SmOrg/1.0 beta").setJsonHttpRequestInitializer(new JsonHttpRequestInitializer(){

			@Override
			public void initialize(JsonHttpRequest request) throws IOException {
				CalendarRequest calendarRequest = (CalendarRequest) request;
                                calendarRequest.setKey(API_KEY);
                                calendarRequest.setOauthToken(accessToken);
			}
			
		}).setHttpRequestInitializer(googleCredential).build();
                
                Log.d("MyAPP","Lets get user calendars");

		getUserCalendars(calendarService);
		
	}


	private void getUserCalendars(Calendar calendarService) {
		                Log.d("MyAPP","Inside the function");

		try {
			CalendarList calendarList = calendarService.calendarList().list().execute();
			                Log.d("MyAPP","before while");

			while(true){
				for (CalendarListEntry calendarListEntry : calendarList.getItems()){
					// do something
                                                        Log.d("MyAPP","looping on calendars ");

					System.out.print(calendarListEntry.getSummary());
					System.out.print(calendarListEntry.getId());
					
					userCalendar.add(calendarListEntry.getSummary());
					userCalendarId.add(calendarListEntry.getId());
					
				}
				String nextPageToken = calendarList.getNextPageToken();
				if (nextPageToken != null && !nextPageToken.equalsIgnoreCase(""))
					calendarList = calendarService.calendarList().list().setPageToken(nextPageToken).execute();
				break;
			}	
			
			
			showDialog();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void addEventToCalendar(int which) {
		// TODO Auto-generated method stub
                Log.d("MyAPP","quick event ya sidi");

                setContentView(R.layout.quick_add_event);
		final int index = which;
		TextView textView = (TextView) findViewById(R.id.textView);
		final EditText editText = (EditText) findViewById(R.id.quickadd);
		Button button = (Button) findViewById(R.id.setEvent);
		
		textView.setText(userCalendar.get(index));
		
		button.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				String calendarId = userCalendarId.get(index);
				String text = editText.getText().toString();
				// TODO Auto-generated method stub
				try {
					calendarService.events().quickAdd(calendarId , text ).execute();
                                        Log.d("MyAPP","Event added!!!! ");

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			}
			
		});

		
		
	}

	 class CalendarNamesDialog extends DialogFragment {

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
					addEventToCalendar(which);
				}
			}).create();
			
		
		}

		
	 }


	
}
