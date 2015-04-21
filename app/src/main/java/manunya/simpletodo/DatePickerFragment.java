package manunya.simpletodo;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

//import android.app.DialogFragment;

/**
 * Created by Manunya on 4/20/2015.
 */
// See http://stackoverflow.com/questions/24558835/how-can-i-pass-the-date-chosen-in-a-date-picker-to-the-activity-which-contains-t

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private DatePickerDialogListener datePickerListener;
    private static final String KEY_POS = "pos";
    private static final String KEY_ITEM = "item";

    public interface DatePickerDialogListener {
        public void onDateSet(int pos, long date);
    }

    public static DatePickerFragment newInstance(DatePickerDialogListener listener, int pos, TodoItem item) {
        Bundle args = new Bundle();
        args.putInt(KEY_POS, pos);
        args.putSerializable(KEY_ITEM, item);
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setDatePickerListener(listener);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        //Log.v("creating dpdialog", "");
        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public DatePickerDialogListener getDatePickerListener() {
        return this.datePickerListener;
    }

    public void setDatePickerListener(DatePickerDialogListener listener) {
        this.datePickerListener = listener;
    }

    public void notifyDatePickerListener(Date date) {
        if(this.datePickerListener != null) {
            this.datePickerListener.onDateSet(getArguments().getInt(KEY_POS), date.getTime());
        }
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        Calendar c = Calendar.getInstance();
        c.set(year, month, day);
        Date date = c.getTime();
        // Here we call the listener and pass the date back to it.
        notifyDatePickerListener(date);

    }
}
