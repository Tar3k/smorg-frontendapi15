/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.smorg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import com.smorg.data.Goal;
import com.smorg.data.GoalDAO;
import java.util.ArrayList;

/**
 *
 * @author Tarek
 */
public class GoalViewActivity extends Activity implements OnItemClickListener
    ,OnClickListener {

    private GoalDAO goalDAO;
	private ArrayList<Goal> goals;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.goals_view);
        ListView listView = (ListView) findViewById(R.id.g_list);
        Button createButton = (Button) findViewById(R.id.g_create);
        createButton.setOnClickListener(this);
        Button syncButton = (Button) findViewById(R.id.g_sync);
        syncButton.setOnClickListener(this);
        
        goalDAO = new AppEngineDAO(this);
        goals = goalDAO.getAllGoals(AccountsDialog.account.name);
		
        listView.setAdapter(new ArrayAdapter<Goal>(this,
				android.R.layout.simple_list_item_1,goals));
		listView.setOnItemClickListener(this);
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
       Goal goal = goals.get(position);
    }

    public void onClick(View v) {
       
        if(v.getId()== R.id.g_create){
           Intent intent = new Intent ();
           intent.setClass(this,CreateGoalActivity.class);
           startActivity(intent);
       }
       else {
           
       }
        
    }
}
