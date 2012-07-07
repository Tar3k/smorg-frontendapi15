/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.smorg;

import android.util.Log;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.CalendarList;
import com.google.api.services.calendar.model.CalendarListEntry;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tarek
 */
public class CalendarsRetrieval {
    
    static ArrayList<String> retrieveId (final Calendar calendarService){
        
        ArrayList<String> calendarId = new ArrayList<String>();
        try {
            Log.d("MyAPP","retrieving id: CalendarsRetrieval");
            CalendarList calendarList = calendarService.calendarList().list().execute();
            
            while(true){
                for (CalendarListEntry calendarListEntry : calendarList.getItems()){
                    calendarId.add(calendarListEntry.getId());
                }
                String nextPageToken = calendarList.getNextPageToken();
                    if (nextPageToken != null && !nextPageToken.equalsIgnoreCase("")) {
                        calendarList = calendarService.calendarList().list().setPageToken(nextPageToken).execute();
                    }
                    break;
            }
        } catch (IOException ex) {
            Logger.getLogger(CalendarsRetrieval.class.getName()).log(Level.SEVERE, null, ex);
        }
        return calendarId;
        
    }
    
      static ArrayList<String> retrieveName (final Calendar calendarService){
        
        ArrayList<String> calendarName = new ArrayList<String>();
        try {
            Log.d("MyAPP","Retrieving names: CalendarsRetrieval");
            CalendarList calendarList = calendarService.calendarList().list().execute();
            
            while(true){
                for (CalendarListEntry calendarListEntry : calendarList.getItems()){
                    calendarName.add(calendarListEntry.getSummary());
                }
                String nextPageToken = calendarList.getNextPageToken();
                    if (nextPageToken != null && !nextPageToken.equalsIgnoreCase("")) {
                        calendarList = calendarService.calendarList().list().setPageToken(nextPageToken).execute();
                    }
                    break;
            }
        } catch (IOException ex) {
            Logger.getLogger(CalendarsRetrieval.class.getName()).log(Level.SEVERE, null, ex);
        }
        return calendarName;
        
    }
}
