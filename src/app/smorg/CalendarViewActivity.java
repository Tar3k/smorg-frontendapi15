package app.smorg;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author Tarek
 */
public class CalendarViewActivity extends Activity
        implements CalendarView.OnDateChangeListener {

    private int chosenYear;
    private int chosenMonth;
    private int chosenDay;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.cal);
        CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);
        Calendar calendar = GregorianCalendar.getInstance();
        calendarView.setDate(calendar.getTime().getTime());
        calendarView.setOnDateChangeListener(this);
    }

    public void onSelectedDayChange(CalendarView view, int year, int month,
            int dayOfMonth) {

        this.chosenYear = year;
        this.chosenMonth = month;
        this.chosenDay = dayOfMonth;
        Log.d("MyApp", chosenYear + ":" + chosenMonth + ":" + chosenDay);
        DialogFragment frag = TasksDialog.newInstance(this);
        frag.show(this.getFragmentManager(), "dialog");
    }

    void actionChosen(int which) {
        Intent intent = new Intent();
        intent.putExtra("cid", SplashActivity.calendarIdChosen);
        intent.putExtra("chosenYear", chosenYear);
        intent.putExtra("chosenMonth", chosenMonth);
        intent.putExtra("chosenDay", chosenDay);
        if (which == 0) //QuickAdd
        {
            intent.setClass(this, QuickAddActivity.class);
        } else if (which == 1) //Create event
        {
            intent.setClass(this, CreateEventActivity.class);
        }
        startActivity(intent);
    }
}
