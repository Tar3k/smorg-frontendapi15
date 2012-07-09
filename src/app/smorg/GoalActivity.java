/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.smorg;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

/**
 *
 * @author Tarek
 */
public class GoalActivity extends Activity {
    private String title;
    private String start;
    private String end;
    private long id;
    private String[] events;
   

    
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.goal);
        title = getIntent().getExtras().getString("title");
        start = getIntent().getExtras().getString("start");
        
        end = getIntent().getExtras().getString("end");
        Log.d("MyAPP",start + "ends: " + end);
        id = getIntent().getExtras().getLong("id");
        events = getIntent().getExtras().getStringArray("events");
        Log.d("MyAPP","Event ids recieved: " +events.length);
        TextView titleField = (TextView) findViewById(R.id.g_titleView);
        titleField.setText(title);
        TextView startField = (TextView) findViewById(R.id.g_startView);
        startField.setText("Starts on: "+ start);
        TextView endField = (TextView) findViewById(R.id.g_endView);
        endField.setText("Deadline: "+ end);
        TextView noEventField = (TextView) findViewById(R.id.no_events_found);
        noEventField.setVisibility(View.INVISIBLE);
        if(events.length > 0 ){
            
        }else{
            noEventField.setVisibility(View.VISIBLE);
            
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.goalview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.goal_remove:
                new AppEngineDAO(this).removeGoal(id);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

   
    
    
    
}
