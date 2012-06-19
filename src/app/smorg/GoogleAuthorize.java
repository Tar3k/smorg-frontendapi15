/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.smorg;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.util.Log;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tarek
 */
public class GoogleAuthorize {

    private static final String AUTH_TOKEN_TYPE = "oauth2:https://www.googleapis.com/auth/calendar";

    static String authorize(final SplashActivity activity, Account account) {
        AccountManager accountManager = AccountManager.get(activity);
        Log.d("MyAPP", "Get Authorization");
        try {
            return accountManager.blockingGetAuthToken(account, AUTH_TOKEN_TYPE, false);
        } catch (Exception ex) {
            Logger.getLogger(GoogleAuthorize.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
