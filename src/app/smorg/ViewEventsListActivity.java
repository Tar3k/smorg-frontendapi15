/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.smorg;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tarek
 */
public class ViewEventsListActivity extends Activity implements OnItemClickListener {

    private Events events;
    public ArrayList<Event> event;
    private int whichEvent;
    private ArrayList<EventView> eventToView;


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setup();

    }

    private void setup() {

        setContentView(R.layout.events_view);

        ListView listView = (ListView) findViewById(R.id.e_list);
        eventToView = getEvents(SplashActivity.calendarIdChosen);
        if (eventToView != null && eventToView.size() > 0) {
            Date now = GregorianCalendar.getInstance().getTime();
            Toast.makeText(this,"Last Sync: "+ now.toString(), Toast.LENGTH_SHORT).show();
            listView.setAdapter(new ArrayAdapter<EventView>(this,
                    android.R.layout.simple_list_item_1, eventToView));
            listView.setOnItemClickListener(this);
            
        }else {
            Toast.makeText(this, "Could not Retrieve Events", Toast.LENGTH_SHORT).show();
        }
       
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        whichEvent = arg2;
        DialogFragment frag = EventTasksDialog.newInstance(this);
        frag.show(this.getFragmentManager(), "dialog");
    }

    private ArrayList<EventView> getEvents(String cid) {

        ArrayList<EventView> eventView = new ArrayList<EventView>();
        try {
            events = new GetEventsHandler(this).execute(cid).get();
            event = new ArrayList<Event>(events.getItems());
            Log.d("MyAPP", event.toString());
            for (Event e : event) {
                eventView.add(new EventView(e));
            }
            Log.d("MyAPP", eventView.toString());
            return eventView;

        } catch (InterruptedException ex) {
            Logger.getLogger(ViewEventsListActivity.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(ViewEventsListActivity.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    void actionChosen(int which) {
        Intent intent = new Intent();
        intent.putExtra("id", event.get(whichEvent).getId());
        switch (which) {
            case (0):
                //intent.setClass(this, GoalEventActivity.class);
                break;
            case (1):
                intent.putExtra("summary", event.get(whichEvent).getSummary());
                intent.putExtra("location", event.get(whichEvent).getLocation());
                intent.putExtra("startTime", event.get(whichEvent).getStart().getDateTime().getValue());
                intent.putExtra("endTime", event.get(whichEvent).getEnd().getDateTime().getValue());
                intent.putExtra("description", event.get(whichEvent).getDescription());
                intent.setClass(this, EditEventActivity.class);
                break;
        }
        startActivity(intent);

    }

    @Override
    protected void onResume() {
        setup();
        super.onResume();
    }
}
