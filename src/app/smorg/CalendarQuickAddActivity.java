/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.smorg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 *
 * @author Tarek
 */
public class CalendarQuickAddActivity extends Activity {

    private static final int QUICKADD_EVENT = 200;
   
    private EditText editText;
    private Button button;
    private String event;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        // ToDo add your GUI initialization code here   
        
         setContentView(R.layout.quick_add_event);
         editText = (EditText) findViewById(R.id.quickadd);
         button = (Button) findViewById(R.id.setEvent);
         button.setOnClickListener(new OnClickListener(){
                
                @Override
                public void onClick(View arg0) {
                    
                    event = editText.getText().toString();
                    finish();
                }
            });
    }

    @Override
    public void finish() {
        Intent data = new Intent();
        data.putExtra("event", event);
        setResult(QUICKADD_EVENT,data);
        super.finish();
    }
    
    
}
