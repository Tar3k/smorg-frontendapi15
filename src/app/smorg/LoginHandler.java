package app.smorg;

import android.accounts.Account;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

/**
 *
 * @author Tarek
 */
public class LoginHandler extends AsyncTask<Void, Void, Void> {
    Account account;
    ProgressDialog progressDialog;
    String token;
    SplashActivity parent;

    public LoginHandler(SplashActivity parent, Account account) {
        this.parent = parent;
        this.account = account;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = ProgressDialog.show(parent,
                "Connecting..","Authorizing your Google account, Please wait...",
                true,
                false);
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... params) {
        token = GoogleAuthorize.authorize(parent, account);
        Log.d("MyAPP",token);
        return null;
    }
    
    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        progressDialog.dismiss();
        parent.googleAuthorizationFinished(token);
    }
    
}
