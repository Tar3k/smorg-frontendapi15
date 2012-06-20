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
  
    
    private int chosenYear;
    private int chosenMonth;
    private int chosenDay;
    private String calendarId;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        calendarId= getIntent().getExtras().getString("cid");
        setContentView(R.layout.cal);
        CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(this);
    }

    public void onSelectedDayChange(CalendarView view, int year, int month,
            int dayOfMonth) {

        this.chosenYear = year; 
        this.chosenMonth = month;
        this.chosenDay= dayOfMonth;
        DialogFragment frag = TasksDialog.newInstance(this);
        frag.show(this.getFragmentManager(), "dialog");
    }

    void actionChosen(int which) {
        Intent intent = new Intent();
        intent.putExtra("cid", calendarId);
        if (which == 0) //QuickAdd
        {
            intent.setClass(this, QuickAddActivity.class);
            startActivity(intent);
        }
        //else if (which == 1) //Create event






    }
}
