package app.smorg;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.ArrayAdapter;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
/**
 *
 * @author Tarek
 */
public class TasksDialog extends DialogFragment
        implements DialogInterface.OnClickListener {

    private static final String title = "Choose an action";
    private static CalendarViewActivity parentClass;

    public static TasksDialog newInstance(CalendarViewActivity parentClass) {
        TasksDialog.parentClass = parentClass;
        TasksDialog tasksDialog = new TasksDialog();
        return tasksDialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        return new AlertDialog.Builder(
                getActivity()).setTitle(title).setAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1,
                getActivity().getResources().getStringArray(R.array.calendarTasks)),
                this).create();

    }

    public void onClick(DialogInterface dialog, int which) {
        parentClass.actionChosen(which);

    }
}
