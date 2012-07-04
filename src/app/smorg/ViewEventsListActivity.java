/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.smorg;

import android.app.Activity;
import android.os.Bundle;
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
public class ViewEventsListActivity extends Activity implements OnItemClickListener{
    private Events events;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.events_view);       
        ListView listView = (ListView) findViewById(R.id.e_list);
		listView.setAdapter(new ArrayAdapter<Event>(this,
				android.R.layout.simple_list_item_1,getEvents(
                getIntent().getExtras().getInt("chosenYear")              
                ,getIntent().getExtras().getInt("chosenMonth")
                ,getIntent().getExtras().getInt("chosenDay")
                ,getIntent().getExtras().getString("cid"))));
		listView.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
			}

    private ArrayList<Event> getEvents(int year, int month, int day, String cid){
        
        try {
            events = new GetEventsHandler(this).execute(cid).get();
            return (ArrayList<Event>) events.getItems();
         
        } catch (InterruptedException ex) {
            Logger.getLogger(ViewEventsListActivity.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(ViewEventsListActivity.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
    
