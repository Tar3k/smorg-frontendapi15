package app.smorg;





import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;



public class AnActivity extends Activity {
   
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.login_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {
	      // Start the WebViewActivity to handle the authentication.
	      case R.id.login:
	    	  Intent intent = new Intent().setClass(this,GoogleAuthorizeActivity.class);
	    	  startActivity(intent);
	    	  
	        return true;
	      // Exit.
	      case R.id.exit:
	        finish();
	        return true;
	    }
		return super.onOptionsItemSelected(item);
	}

	

}