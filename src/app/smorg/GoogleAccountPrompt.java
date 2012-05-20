/*	
 * 	
 *  Offering Authorization with Google to use Google Calendar API (Read/Write)
 *  "Yea this process took me hours to figure"
 */

package app.smorg;

import android.accounts.*;
import android.app.*;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

public class GoogleAccountPrompt extends Activity {
	
        private static final String AUTH_TOKEN_TYPE = "oauth2:https://www.googleapis.com/auth/calendar";
        private static final String PREFS_ACC_NAME = "accountName";
        private static final String PREFS_ACC_TOKEN= "authToken";
        private String token;
	private AccountManager accountManager ;
	private Account account;
        private ProgressDialog progressDialog;
        private SharedPreferences sharedPrefs;
	  
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
                Log.d("MyAPP","Google Authorization Activity created");
		accountManager= AccountManager.get(this);
		showDialog();
	}		
	
        protected Account getUserAccount(){
            return account;
        }
        
        
	protected void showDialog(){
                Log.d("MyAPP","Show dialog to choose your Google account");
		DialogFragment newFragment = new AccountsDialog().instance(R.string.app_name);
		newFragment.show(getFragmentManager(), "dialog");
	}

	  
	  protected void authorizeWithGoogle() {
			// TODO Auto-generated method stub
              Log.d("MyAPP","Get Authorization");
              
              progressDialog= ProgressDialog.show(GoogleAccountPrompt.this,"Connecting","Authorizing your Google account, Please wait...",true,false);
              accountManager.getAuthToken(account, AUTH_TOKEN_TYPE, null, this, new AccountManagerCallback<Bundle>() {

                  public void run(AccountManagerFuture<Bundle> future) {
		
                      try {
                            // If the user has authorized your application to use the calendar API
                            // a token is available.
                          token = future.getResult().getString(AccountManager.KEY_AUTHTOKEN);
                          // Now you can use the  API...
                          Log.d("MyAPP","Token is ready");
                          Intent intent = new Intent().setClass(GoogleAccountPrompt.this,CalendarTasks.class);
                          intent.putExtra("token", token);
                          progressDialog.dismiss();
                          startActivity(intent);
                          finish();
			
                      } catch (OperationCanceledException e) {
			    // TODO: The user has denied you access to the API, you should handle that
	
                      } catch (Exception e) {
	
                      }
	          }
	      }, null);
	  }
	  
          // DialogFragment classs
	  
	  private class AccountsDialog extends DialogFragment {
		  
		   AccountsDialog instance(int title){
				  AccountsDialog dialogFragment = new AccountsDialog();
				  Bundle arguments = new Bundle();
				  arguments.putInt("title", title);
				  dialogFragment.setArguments(arguments);
				  return dialogFragment;
			  }
                          
			@Override
			public Dialog onCreateDialog(Bundle savedInstanceState) {
			
				int title = getArguments().getInt("title");
				final Account[] accounts = accountManager.getAccountsByType("com.google");
				
                                if (accounts.length != 0){  
                                	final int size = accounts.length;
					String[] names = new String[size];
					for (int i = 0; i < size; i++) 
						names[i] = accounts[i].name;
			        	return new AlertDialog.Builder(getActivity()).setTitle(title).setItems(names,new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
                                                 // Stuff to do when the account is selected by the user
							account = accounts[which];
                                                        authorizeWithGoogle();
			            }
			          }).create();
			    }
                                //Handling case where no google accounts registered to the device
			    return new AlertDialog.Builder(getActivity()).setTitle("Error").setMessage("No google account found on your android").create();
			       
			}

		  }
       
}
