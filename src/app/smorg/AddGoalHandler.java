package app.smorg;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.SerializableEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Log;

import com.smorg.data.Goal;

public class AddGoalHandler extends AsyncTask<Void,Void,Void> {

	private DefaultHttpClient client;
	private Goal goal;
	
	public AddGoalHandler(Goal goal){
		this.goal=  goal;
		client = new DefaultHttpClient();
	}
	@Override
	protected Void doInBackground(Void... arg0) {
		HttpPost postRequest = new HttpPost(Url.BASE_URL + Url.ADD_SERVLET);
		try {
			postRequest.setEntity(new SerializableEntity(goal, true));
			HttpResponse response = client.execute(postRequest);
			Log.d("App", response.getEntity().toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
