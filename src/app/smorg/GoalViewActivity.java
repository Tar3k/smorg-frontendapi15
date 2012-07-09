/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.smorg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.smorg.data.Goal;
import com.smorg.data.GoalDAO;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author Tarek
 */
public class GoalViewActivity extends Activity implements OnItemClickListener {

    private GoalDAO goalDAO;
    public ArrayList<Goal> goals;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setup();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.goal_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.goal_create:
                Intent intent = new Intent();
                intent.setClass(this, CreateGoalActivity.class);
                startActivity(intent);
                return true;
            case R.id.goal_sync:
                setup();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Goal goal = goals.get(position);
        Intent intent = new Intent();
        intent.setClass(this, GoalActivity.class);
        Log.d("MyAPP","Just clicked:"+
                "starts on: "+ goal.getStartDate() 
                + "ends: " +goal.getDeadline() 
                +"event ids:" + goal.getEventIds() );
        intent.putExtra("title", goal.getTitle());
        intent.putExtra("start", goal.getStartDate().toString());
        intent.putExtra("end", goal.getDeadline().toString());
        intent.putExtra("id", goal.getGoalId());
        intent.putExtra("events", goal.getEventIds()
                .toArray(new String[goal.getEventIds().size()]));
        startActivity(intent);
        
    }

    private void setup() {
        setContentView(R.layout.goals_view);
        ListView listView = (ListView) findViewById(R.id.g_list);
        TextView text = (TextView) findViewById(R.id.nogoals);
        text.setVisibility(View.INVISIBLE);
        goalDAO = new AppEngineDAO(this);
        goals = goalDAO.getAllGoals(AccountsDialog.account.name);
        Date now = GregorianCalendar.getInstance().getTime();
        if (goals != null) {
            listView.setAdapter(new ArrayAdapter<Goal>(this,
                    android.R.layout.simple_list_item_1, goals));
            listView.setOnItemClickListener(this);
             Toast.makeText(this,"Last Sync: "+ now.toString(), Toast.LENGTH_LONG);
            
        } else {
           
            text.setVisibility(View.VISIBLE);
             Toast.makeText(this,"Last Sync: "+ now.toString(), Toast.LENGTH_LONG);
        }
       

    }
    
    @Override
    protected void onResume() {
        setup();
        super.onResume();
    }
}
