/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.smorg;

import android.util.Log;
import com.google.api.client.extensions.android2.AndroidHttp;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.json.JsonHttpRequest;
import com.google.api.client.http.json.JsonHttpRequestInitializer;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarRequest;
import java.io.IOException;

/**
 *
 * @author Tarek
 */
public class GoogleCalendarConnection {
    
    
    
    static Calendar connect(final SplashActivity parentActivity,final String token){
       Log.d("MyApp", "will build calendar service");
        HttpTransport transport= AndroidHttp.newCompatibleTransport();
        GoogleCredential googleCredential = new GoogleCredential().setAccessToken(token);
        JacksonFactory jacksonFactory = new JacksonFactory();
        Calendar  calendarService = Calendar.builder(transport,jacksonFactory)
                .setApplicationName("SmOrg/1.0 Beta")
                .setJsonHttpRequestInitializer(new JsonHttpRequestInitializer() { 
                    
                    @Override
                    public void initialize(JsonHttpRequest request) throws IOException {
                        CalendarRequest calendarRequest = (CalendarRequest) request;
                        calendarRequest.setKey(ControlCodes.API_KEY)
                                .setOauthToken(token);
                    }
                })
                .setHttpRequestInitializer(googleCredential)
                .build();
        Log.d("MyApp", "Built");
        return calendarService;
        
        
        
    }
}
