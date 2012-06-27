/*
 * Splash screen activity ; this is where our app opens its eyes
 */
package app.smorg;

import android.accounts.Account;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.google.api.services.calendar.Calendar;
import java.util.TimeZone;

public class SplashActivity extends Activity {

    private Account account;
    private String token;
    private boolean authorized;
    public static Calendar calendarService;
    private String calendarIdChosen;
    private String calendarNameChosen;

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

    void googleAuthorizationFinished(String token) {
        Log.d("MyApp", "authorization finished " + token);
        this.token = token;
        this.authorized = true;
        new GoogleCalendarHandler(this,token).execute();
    }

    public String getToken() {
        return token;
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

    void googleCalendarConnected(Calendar calendarService) {
     SplashActivity.calendarService = calendarService;
     Log.d("MyApp", "Calendar Service is ready");
     new CalendarsHandler(this).execute();
    
    }

    void googleCalendarChosen(String calendarId, String calendarName) {
        this.calendarIdChosen= calendarId;   
        this.calendarNameChosen= calendarName;
        Log.d("MyApp", calendarIdChosen +":" + calendarNameChosen);
        startAction();
    }

    void startAction (){
         Intent intent = new Intent()
             .setClass(this, CalendarViewActivity.class)
             .putExtra("cid", calendarIdChosen);
     startActivity(intent);
     finish();
     
    }
}