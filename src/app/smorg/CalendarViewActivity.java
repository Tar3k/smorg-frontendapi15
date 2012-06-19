/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.smorg;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CalendarView;

/**
 *
 * @author Tarek
 */
public class CalendarViewActivity extends Activity 
    implements CalendarView.OnDateChangeListener {

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.cal);
        CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(this);
    }

    public void onSelectedDayChange(CalendarView view, int year, int month,
            int dayOfMonth) {
        
       DialogFragment frag = TasksDialog.newInstance(year, month, year, this);
       frag.show(this.getFragmentManager(), "dialog");
    }

    void actionChosen(int which) {
        Intent intent = new Intent();   
        if (which == 0 ) //QuickAdd
            intent.setClass(this,QuickAddActivity.class);
        //else if (which == 1) //Create event
            
         
            
            
            
        
    }
}
