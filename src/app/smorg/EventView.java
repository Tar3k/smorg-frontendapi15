/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.smorg;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import java.util.Date;

/**
 *
 * @author Tarek
 */
public class EventView {
    
    private String id;
    private String title;
    private String location;
    private DateTime start;
    private DateTime end;

    public EventView (Event event){
        this.title = event.getSummary();
        this.location = event.getLocation();
        this.id = event.getId();
        this.start= event.getStart().getDateTime();
        this.end = event.getEnd().getDateTime();
    }

    public DateTime getEnd() {
        return end;
    }

    public void setEnd(DateTime end) {
        this.end = end;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public DateTime getStart() {
        return start;
    }

    public void setStart(DateTime start) {
        this.start = start;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    
    
    @Override
    public String toString() {
        return getTitle() 
                +"\n"+ getLocation()
                +"\nStart: " + new Date(getStart().getValue()).toString()
                +"\nEnd: " +  new Date(getEnd().getValue()).toString();
                        
    }
    
    
    
}
