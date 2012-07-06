/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.smorg;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import java.util.ArrayList;
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

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.events_view);
        ListView listView = (ListView) findViewById(R.id.e_list);
        listView.setAdapter(new ArrayAdapter<EventView>(this,
                android.R.layout.simple_list_item_1, getEvents(SplashActivity.calendarIdChosen)));
        listView.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
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
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
