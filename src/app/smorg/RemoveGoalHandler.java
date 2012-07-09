package app.smorg;

import android.app.ProgressDialog;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.SerializableEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;


public class RemoveGoalHandler extends AsyncTask<Long, Void, Void> {

    private DefaultHttpClient client;
    private final ProgressDialog progressDialog;
    private boolean isDone = false;

    public RemoveGoalHandler (ProgressDialog progressDialog) {
        this.progressDialog = progressDialog;
        client = new DefaultHttpClient();

    }

    @Override
    protected void onPreExecute() {
        progressDialog.setTitle("Connecting to Cloud");
        progressDialog.show();
    }

    @Override
    protected Void doInBackground(Long... args) {
        HttpPost postRequest = new HttpPost(Url.BASE_URL + Url.RM_SERVLET);
        try {
            postRequest.setEntity(new SerializableEntity(args[0], true));
            HttpResponse response = client.execute(postRequest);
            Log.d("App", response.getEntity().toString());
            isDone = true;

        } catch (IOException e) {
            isDone = false;
            e.printStackTrace();

        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {

        progressDialog.dismiss();
        if (isDone = true) {
            Toast.makeText(progressDialog.getContext(), "Goal Removed", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(progressDialog.getContext(), "Unable to Remove Goal", Toast.LENGTH_LONG).show();
        }
    }
}
