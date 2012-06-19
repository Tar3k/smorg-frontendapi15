/*
 * Splash screen activity ; this is where our app opens its eyes
 */
package app.smorg;

import android.accounts.Account;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class SplashActivity extends Activity {

    Account account;
    ProgressDialog progressDialog;
    String token;
    boolean authorized;
    public static final int TOKEN_REQUEST = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.login_menu, menu);
        Log.d("MyAPP", "Here we start");
        return true;
    }

    void accountSelected(Account account) {
        this.account = account;
        Log.d("MyApp", "account chosen: " + account.toString());
        new LoginHandler(this, account).execute();
    }

    void authorizationFinished(String token) {
        Log.d("MyApp", "authorization finished " + token);
        this.token = token;
        this.authorized = true;
        Intent intent = new Intent().setClass(this, CalendarViewActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.login:
                DialogFragment newFragment =
                        AccountsDialog.newInstance("Choose an account", this);
                newFragment.show(this.getFragmentManager(), "dialog");
                return true;
            case R.id.exit:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}