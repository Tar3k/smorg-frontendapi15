package app.smorg;

import android.app.ProgressDialog;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.SerializableEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Log;

import com.smorg.data.Goal;

public class AddGoalHandler extends AsyncTask<Goal,Void,Void> {

	private DefaultHttpClient client;
    private final ProgressDialog progressDialog;
	
	
	public AddGoalHandler(ProgressDialog progressDialog){
        this.progressDialog = progressDialog;
     	client = new DefaultHttpClient();
	}

    @Override
    protected void onPreExecute() {
        progressDialog.setTitle("Contacting Back-End");
        progressDialog.show();
    }
    
	@Override
	protected Void doInBackground(Goal... args) {
		HttpPost postRequest = new HttpPost(Url.BASE_URL + Url.ADD_SERVLET);
		try {
			postRequest.setEntity(new SerializableEntity(args[0], true));
			HttpResponse response = client.execute(postRequest);
			Log.d("App", response.getEntity().toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

    @Override
    protected void onPostExecute(Void result) {
        progressDialog.dismiss();
    }
    
    

}
