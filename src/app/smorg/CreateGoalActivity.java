package app.smorg;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.smorg.data.Goal;

public class CreateGoalActivity extends Activity implements OnClickListener {

    private EditText goalTitle;
	private DatePicker goalStartDate;
	private TimePicker goalStartTime;
	private DatePicker goalEndDate;
	private TimePicker goalEndTime;
	private EditText goalDescription;
	private Calendar calendar;
	private Goal goal;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goal_create);
        goalTitle = (EditText) findViewById(R.id.g_title);
        goalStartDate = (DatePicker) findViewById(R.id.g_dateStart);
        goalStartTime = (TimePicker) findViewById(R.id.g_timeStart);
        goalEndDate = (DatePicker) findViewById(R.id.g_dateEnd);
        goalEndTime = (TimePicker) findViewById(R.id.g_timeEnd);
        goalDescription = (EditText) findViewById(R.id.g_description);

        Button button = (Button) findViewById(R.id.create_g_button);
        button.setOnClickListener(this);
    }

	@Override
	public void onClick(View arg0) {
		goal = new Goal();
		
		calendar = GregorianCalendar.getInstance();
		calendar.set(goalStartDate.getYear()
				, goalStartDate.getMonth()
				, goalStartDate.getDayOfMonth()
				, goalStartTime.getCurrentHour()
				, goalStartTime.getCurrentMinute());
		goal.setStartDate(calendar.getTime());
		
		calendar.set(goalEndDate.getYear()
				, goalEndDate.getMonth()
				, goalEndDate.getDayOfMonth()
				, goalEndTime.getCurrentHour()
				, goalEndTime.getCurrentMinute());
		goal.setDeadline(calendar.getTime());
		goal.setTitle(goalTitle.getText().toString());
		goal.setDescription(goalDescription.getText().toString());
		goal.setUserId(AccountsDialog.account.name);
		
		new AppEngineDAO(this).addGoal(goal);
        
        
        

		
	}
    void end(){
        
    }
}
