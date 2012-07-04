package app.smorg;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import android.util.Log;

import com.smorg.data.Goal;
import com.smorg.data.GoalDAO;

public class AppEngineDAO  implements GoalDAO {
	
	

	@Override
	public void addGoal(Goal goal) {
	  new AddGoalHandler(goal).execute();
	}

	@Override
	public ArrayList<Goal> getAllGoals(String userId) {
		Log.d("MyAPP", "getting all goals");
		try {
			return new GetGoalsHandler().execute(userId).get();
			//Log.d("MyAPP", "finished ASYNC");
			//return GetGoalsHandler.goal;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void removeGoal(Long goalId) {
		// TODO Auto-generated method stub
		
	}
}