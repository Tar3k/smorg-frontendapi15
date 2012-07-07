/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.smorg;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author Tarek
 */
public class EditEventActivity extends Activity implements OnClickListener {

  
     private EditText eventTitle;
    private EditText eventLocation;
    private DatePicker eventStartDate;
    private TimePicker eventStartTime;
    private DatePicker eventEndDate;
    private TimePicker eventEndTime;
    private EditText eventDescription;
    private Button updateEventButton;
    private Event event;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        Log.d("MyApp", "onCreate : CreateEventActivity");
        setContentView(R.layout.event_create);
        
        eventTitle = (EditText) findViewById(R.id.e_title);
        eventLocation = (EditText) findViewById(R.id.e_loc);
        eventStartDate = (DatePicker) findViewById(R.id.e_dateStart);
        eventStartTime = (TimePicker) findViewById(R.id.e_timeStart);
        eventEndDate = (DatePicker) findViewById(R.id.e_dateEnd);
        eventEndTime = (TimePicker) findViewById(R.id.e_timeEnd);
        eventDescription = (EditText) findViewById(R.id.e_description);
        updateEventButton = (Button) findViewById(R.id.create_e_button);
        updateEventButton.setText("Update");
        updateEventButton.setOnClickListener(this);
        eventTitle.setText(getIntent().getExtras().getString("summary"));
        eventLocation.setText(getIntent().getExtras().getString("location"));
        eventDescription.setText(getIntent().getExtras().getString("description"));
        event = new Event();
        event.setId(getIntent().getExtras().getString("id"));
        
       // DateTime start = new DateTime(new Date(getIntent().getExtras().getLong("startTime")));
        
       // DateTime end = new DateTime(new Date(getIntent().getExtras().getLong("endTime")));
        
        
    }

    public void onClick(View v) {
              Log.d("MyApp", "onClick : CreateEventActivity");
        
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.set(eventStartDate.getYear(), eventStartDate.getMonth(), eventStartDate.getDayOfMonth(), eventStartTime.getCurrentHour(), eventStartTime.getCurrentMinute());

        event.setStart(new EventDateTime().setDateTime(new DateTime(calendar.getTime())).setTimeZone("UTC"));

        calendar.set(eventEndDate.getYear(), eventEndDate.getMonth(), eventEndDate.getDayOfMonth(), eventEndTime.getCurrentHour(), eventEndTime.getCurrentMinute());

        event.setEnd(new EventDateTime().setDateTime(new DateTime(calendar.getTime())).setTimeZone("UTC"));

        event.setSummary(eventTitle.getText().toString());
        event.setLocation(eventLocation.getText().toString());
        event.setDescription(eventDescription.getText().toString());


        new UpdateEventHandler(this).execute(event);
        
    }
}
