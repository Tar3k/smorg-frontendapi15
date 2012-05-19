/*
 * Splash screen activity ; this is where our app opens its eyes
 */

package app.smorg;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;



public class SplashActivity extends Activity {
   
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.login_menu, menu);
                Log.d("MyAPP","Here we start");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	
            
		switch (item.getItemId()) {
                    // Start the WebViewActivity to handle the authentication.
                    case R.id.login:
                   
                        Intent intent = new Intent().setClass(this,GoogleAccountPrompt.class);
                        startActivity(intent);
                        finish();
                        return true;
	      // Exit.
                    case R.id.exit:
                        finish();
                        return true;
	    }
		return super.onOptionsItemSelected(item);
	}

	

}