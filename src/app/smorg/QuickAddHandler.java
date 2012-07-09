/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.smorg;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tarek
 */
public class QuickAddHandler extends AsyncTask<String, Void, Void> {

    private final QuickAddActivity parentActivity;
    private ProgressDialog progressDialog; 
    private boolean isDone = false;

    public QuickAddHandler(QuickAddActivity parentActivity) {
        
        this.parentActivity = parentActivity;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = ProgressDialog.show(parentActivity, "Synchronizing"
                ,"", true, false);
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(String... params) {
        try {
            SplashActivity.calendarService.events().quickAdd(params[0], params[1]).execute();
            isDone = true;
        } catch (IOException ex) {
            Logger.getLogger(QuickAddHandler.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        progressDialog.dismiss();
        
        if ( isDone = true){
            Toast.makeText(parentActivity, "Event added", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(parentActivity, "Unable to add event", Toast.LENGTH_SHORT).show();
            parentActivity.eventAdded();
        }
        super.onPostExecute(result);
    }
}
