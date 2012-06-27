/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.smorg;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
public class CreateEventActivity extends Activity implements Button.OnClickListener {

    private EditText eventTitle;
    private EditText eventLocation;
    private DatePicker eventStartDate;
    private TimePicker eventStartTime;
    private DatePicker eventEndDate;
    private TimePicker eventEndTime;
    private EditText eventDescription;
    private Event event;
    private Button createEventButton;
    private Calendar calendar;
    private String calendarId;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        Log.d("MyApp", "onCreate : CreateEventActivity");
        setContentView(R.layout.event_create);
        calendarId = getIntent().getExtras().getString("cid");
        eventTitle = (EditText) findViewById(R.id.e_title);
        eventLocation = (EditText) findViewById(R.id.e_loc);
        eventStartDate = (DatePicker) findViewById(R.id.e_dateStart);

        eventStartDate.updateDate(getIntent().getExtras().getInt("chosenYear"), getIntent().getExtras().getInt("chosenMonth"), getIntent().getExtras().getInt("chosenDay"));

        eventStartTime = (TimePicker) findViewById(R.id.e_timeStart);
        eventEndDate = (DatePicker) findViewById(R.id.e_dateEnd);
        eventEndTime = (TimePicker) findViewById(R.id.e_timeEnd);
        eventDescription = (EditText) findViewById(R.id.e_description);
        createEventButton = (Button) findViewById(R.id.create_e_button);

        createEventButton.setOnClickListener(this);
    }

    public void onClick(View v) {
        Log.d("MyApp", "onClick : CreateEventActivity");
        event = new Event();
        calendar = GregorianCalendar.getInstance();
        calendar.set(eventStartDate.getYear(), eventStartDate.getMonth(), eventStartDate.getDayOfMonth(), eventStartTime.getCurrentHour(), eventStartTime.getCurrentMinute());

        event.setStart(new EventDateTime().setDateTime(new DateTime(calendar.getTime())).setTimeZone("UTC"));

        calendar.set(eventEndDate.getYear(), eventEndDate.getMonth(), eventEndDate.getDayOfMonth(), eventEndTime.getCurrentHour(), eventEndTime.getCurrentMinute());

        event.setEnd(new EventDateTime().setDateTime(new DateTime(calendar.getTime())).setTimeZone("UTC"));

        event.setSummary(eventTitle.getText().toString());
        event.setLocation(eventLocation.getText().toString());
        event.setDescription(eventDescription.getText().toString());


        new CreateEventHandler(this, calendarId).execute(event);

    }

    void eventAdded() {
        Log.d("MyApp", "eventAdded : CreateEventActivity");
        finish();
    }
}
