/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.smorg;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerFuture;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tarek
 */
public class GoogleAuthorize {
private static final String AUTH_TOKEN_TYPE = "Manage your calendars";
			//"oauth2:https://www.googleapis.com/auth/calendar";

	public static String authorize(Activity parent, Account account) {
		AccountManager accountManager = AccountManager.get(parent);
		Bundle options= new Bundle();
		
		Log.d("MyAPP", "Get Authorization");
		try {
			AccountManagerFuture<Bundle> accountFuture = accountManager.getAuthToken(
                    account
                    , AUTH_TOKEN_TYPE
                    , options,
                    parent, null, null);
					
			Bundle authTokenBundle = accountFuture.getResult();
			String authToken = authTokenBundle.get(AccountManager.KEY_AUTHTOKEN).toString();
			accountManager.invalidateAuthToken("com.google",authToken);
			accountFuture=accountManager.getAuthToken ( account, AUTH_TOKEN_TYPE,  options, true, null, null);
			authTokenBundle = accountFuture.getResult();
			authToken = authTokenBundle.get(AccountManager.KEY_AUTHTOKEN).toString();
			
			Log.d("MyAPP","Token= "+authToken);
			return authToken;
			
		
		} catch (Exception ex) {
			Logger.getLogger(GoogleAuthorize.class.getName()).log(Level.SEVERE,
					null, ex);
		}
		return null;
	}

}
