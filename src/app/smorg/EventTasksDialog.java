/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.smorg;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.ArrayAdapter;

/**
 *
 * @author Tarek
 */
public class EventTasksDialog extends DialogFragment
        implements DialogInterface.OnClickListener {

    private static ViewEventsListActivity parentClass;

    public static EventTasksDialog newInstance(ViewEventsListActivity parentClass) {
        EventTasksDialog.parentClass = parentClass;
        EventTasksDialog eventTasksDialog = new EventTasksDialog();
        return eventTasksDialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        return new AlertDialog.Builder(
                getActivity()).setAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1,
                getActivity().getResources().getStringArray(R.array.event_tasks)),
                this).create();

    }

    public void onClick(DialogInterface dialog, int which) {
        parentClass.actionChosen(which);
        dialog.dismiss();

    }
}
