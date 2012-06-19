/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.smorg;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 *
 * @author Tarek
 */
public class QuickAddActivity extends Activity {

   
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
                }
            });
    }
    
}
