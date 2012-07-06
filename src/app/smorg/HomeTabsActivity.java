/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.smorg;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

/**
 *
 * @author Tarek
 */
public class HomeTabsActivity extends TabActivity {

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.tab_host);
 
        TabHost tabHost = getTabHost();
 
        // Tab for Calendar
        TabSpec calendarSpec = tabHost.newTabSpec("Calendar");
        // setting Title and Icon for the Tab
        calendarSpec.setIndicator("Calendar");
        Intent calendarIntent = new Intent(this, CalendarViewActivity.class);
        calendarSpec.setContent(calendarIntent);
 
        // Tab for Events
        TabSpec eventsSpec = tabHost.newTabSpec("Events");
        eventsSpec.setIndicator("Events");
        Intent eventsIntent = new Intent(this, ViewEventsListActivity.class);
        eventsSpec.setContent(eventsIntent);
 
        // Tab for Calendar
        TabSpec goalSpec = tabHost.newTabSpec("Goals");
        goalSpec.setIndicator("Goals");
        Intent videosIntent = new Intent(this, GoalViewActivity.class);
        goalSpec.setContent(videosIntent);
 
        // Adding all TabSpec to TabHost
        tabHost.addTab(calendarSpec); 
        tabHost.addTab(eventsSpec);
        tabHost.addTab(goalSpec);        
    }
}
