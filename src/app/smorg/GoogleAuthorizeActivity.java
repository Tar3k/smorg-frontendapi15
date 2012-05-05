package app.smorg;



import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.webkit.CookieSyncManager;

public class GoogleAuthorizeActivity extends Activity {
	
	
	private static final String AUTH_TOKEN_TYPE = "https://www.googleapis.com/auth/calendar";
	AccountManager accountManager ;
	Account account;
	  
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		accountManager= AccountManager.get(this);
		//Remember user's login state
	    CookieSyncManager.createInstance(getApplicationContext());

	}		
	
	protected void showDialog(){
		DialogFragment newFragment = new AccountsDialog().instance(R.string.app_name);
		newFragment.show(getFragmentManager(), "dialog");
	}
	
	@Override
	  protected void onPause() {
	    super.onPause();
	    CookieSyncManager.getInstance().stopSync();
	  }

	  @Override
	  protected void onResume() {
	    super.onResume();
	    CookieSyncManager.getInstance().startSync();
	  }
	  

	  
	  protected void getAuthorization(Account account) {
			// TODO Auto-generated method stub
			this.account=account;
			
			
			accountManager.getAuthToken(account, AUTH_TOKEN_TYPE, null, this, new AccountManagerCallback<Bundle>() {
			    
				public void run(AccountManagerFuture<Bundle> future) {
			        try {
			          // If the user has authorized your application to use the tasks API
			          // a token is available.
			          String token = future.getResult().getString(AccountManager.KEY_AUTHTOKEN);
			          // Now you can use the  API...
			         
			          
			        } catch (OperationCanceledException e) {
			          // TODO: The user has denied you access to the API, you should handle that
			        } catch (Exception e) {
			          
			        }
			      }
			    }, null);
			
			
	  }
		  
	  
	  // DialogFragment classs
	  
	   class AccountsDialog extends DialogFragment {
		  
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
			      final int size = accounts.length;
			      String[] names = new String[size];
			      for (int i = 0; i < size; i++) 
			        names[i] = accounts[i].name;
			        
			      return new AlertDialog.Builder(getActivity()).setTitle(title).setItems(names,new DialogInterface.OnClickListener() {
			          public void onClick(DialogInterface dialog, int which) {
			              // Stuff to do when the account is selected by the user
			        	  getAuthorization(accounts[which]);
			            }
			          }).create();
			       
			}

			
			  
			  
		  }
  
	   }
