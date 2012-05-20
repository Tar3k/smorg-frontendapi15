/*
 * 
 * 
 */
package app.smorg;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 *
 * @author Tarek
 */
public class CalendarTasks extends ListActivity {

        private static final int CREATE_EVENT = 100 ;
        private static final int QUICKADD_EVENT = 200;
        private static final int VIEW_EVENTS = 300;
        private static final int CREATE_CALENDAR= 400;
        private static final int EDIT_CALENDAR= 500;
        
        private int listIndex;

   /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        // ToDo add your GUI initialization code here      
        setContentView(R.layout.cal_demo);
        ListView listView = getListView();
        setListAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.calendarTasks)));
        listView.setTextFilterEnabled(true);
        listView.setOnItemClickListener(new OnItemClickListener(){

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                
                listIndex=position;
                Log.d("MyAPP", ""+position);

                switch (listIndex){
                    case 0:
                        Log.d("MyAPP","case 0"+listIndex);
                        setResult(CREATE_EVENT);
                        finish();
                        break;
                    case 1:{
                        Log.d("MyAPP","henaaa"+listIndex);
                        Log.d("MyAPP","case 1"+listIndex);
                        setResult(QUICKADD_EVENT);
                        finish();
                        break;
                    }
                    case 2:
                        Log.d("MyAPP","case 2"+listIndex);
                        setResult(VIEW_EVENTS);
                        finish();
                        break;
                    case 3:
                        Log.d("MyAPP","case 3"+listIndex);
                        setResult(CREATE_CALENDAR);
                        finish();
                        break;
                    case 4:
                        Log.d("MyAPP","case 4"+listIndex);
                        setResult(EDIT_CALENDAR);  
                        finish();
                        break;
                }
                
            }
            
        });
    }

    @Override
    public void finish() {
        
        Log.d("MyAPP", "finish");
        super.finish();
    }
    
    
}
