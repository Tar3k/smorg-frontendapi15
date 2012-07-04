/*	
 * 	
 *  Offering Authorization with Google to use Google Calendar API (Read/Write)
 *  "Yea this process took me hours to figure"
 */

package app.smorg;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
	  
/**
 * 
 * @author Tarek
 */
public class AccountsDialog extends DialogFragment {
    
	public static Account account;
    private static SplashActivity callback;
    
    public static AccountsDialog newInstance(String title, 
            SplashActivity callback){
        
        AccountsDialog.callback = callback;
        AccountsDialog frag = new AccountsDialog();
        Bundle arguments = new Bundle();
        arguments.putString("title", title);
        frag.setArguments(arguments);
        return frag;
    }
	
    @Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = getArguments().getString("title");
        AccountManager accountManager = AccountManager.get(getActivity());
        final Account[] accounts = accountManager.getAccountsByType("com.google");
        if (accounts.length != 0){
            final int size = accounts.length;
            String[] names = new String[size];
            
            for (int i = 0; i < size; i++) {
                names[i] = accounts[i].name;
            }
            
            return new AlertDialog.Builder(
                    getActivity()).setTitle(title).setItems(names, new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    account = accounts[which];
                    callback.accountSelected(account);
                }
            }).create();
        }
                    //Handling case where no google accounts registered to the device
        return new AlertDialog.Builder(
                getActivity())
                .setTitle("Error")
                .setMessage("No google accounts found on your android")
                .create();
    }
}

       


