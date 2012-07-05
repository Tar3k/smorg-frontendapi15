package app.smorg;

import android.app.ProgressDialog;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.SerializableEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Log;

import com.smorg.data.Goal;

public class GetGoalsHandler extends AsyncTask<String, Void, ArrayList<Goal>> {

	private DefaultHttpClient client;
	public  static ArrayList<Goal> goal;
    private  ProgressDialog progressDialog;

    public GetGoalsHandler(ProgressDialog progressDialog) {
        client = new DefaultHttpClient();
		Log.d("MyAPP", "getGoalsConstructor");
        this.progressDialog = progressDialog;
    }

    @Override
    protected void onPreExecute() {
        progressDialog.setTitle("Fetching Goals from Back-End");
        progressDialog.show();
    }
    
    
	@Override
	protected ArrayList<Goal> doInBackground(String... params) {
		Log.d("MyAPP", "Do in backgroudn : GetGoals");
		HttpPost postRequest = new HttpPost(Url.BASE_URL + Url.GET_SERVLET);
		try {
			
			postRequest.setEntity(new SerializableEntity(params, true));
			HttpResponse response = client.execute(postRequest);
			InputStream isr = response.getEntity().getContent();
			ObjectInputStream reader = new ObjectInputStream(isr);
			goal = (ArrayList<Goal>) reader.readObject();
			Log.d("MyAPP", goal.size()+ "");
			//Log.d("MyAPP", goal.toString());
			return goal;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Log.d("MyAPP", "end of do in background : getGoals");
		return null;
	}

	@Override
	protected void onPostExecute(ArrayList<Goal> result) {
		Log.d("MyAPP", "onPostReq : getGoals");
		progressDialog.dismiss();
		
	}
	
	

}
