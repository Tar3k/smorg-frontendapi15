/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.smorg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.smorg.data.Goal;
import com.smorg.data.GoalDAO;
import java.util.ArrayList;

/**
 *
 * @author Tarek
 */
public class GoalViewActivity extends Activity implements OnItemClickListener {

    private GoalDAO goalDAO;
    private ArrayList<Goal> goals;

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
    }

    private void setup() {
        setContentView(R.layout.goals_view);
        ListView listView = (ListView) findViewById(R.id.g_list);
        goalDAO = new AppEngineDAO(this);
        goals = goalDAO.getAllGoals(AccountsDialog.account.name);
        listView.setAdapter(new ArrayAdapter<Goal>(this,
                android.R.layout.simple_list_item_1, goals));
        listView.setOnItemClickListener(this);
    }
}
