package app.smorg;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import com.smorg.data.Goal;
import com.smorg.data.GoalDAO;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class AppEngineDAO  implements GoalDAO {
    private  ProgressDialog progressDialog;
	
	
    public AppEngineDAO(Context context){
        progressDialog = new ProgressDialog(context);
        
        
    }
	@Override
	public void addGoal(Goal goal) {
	  new AddGoalHandler(progressDialog).execute(goal);
	}

	@Override
	public ArrayList<Goal> getAllGoals(String userId) {
		Log.d("MyAPP", "getting all goals");
		try {
			return new GetGoalsHandler(progressDialog).execute(userId).get();
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
		new RemoveGoalHandler(progressDialog).execute(goalId);
	}

    public void addEventToGoal(Long goalId, String eventId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ArrayList<String> getGoalEvents(Long goalId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}