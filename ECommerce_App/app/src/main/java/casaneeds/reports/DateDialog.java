package casaneeds.reports;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

import java.util.Calendar;

/**
 * Created by Sunita on 15-Jul-2016.
 */
public class DateDialog extends DialogFragment
{
    DatePickerDialog.OnDateSetListener ondateSet;

    public DateDialog() {
    }

    public void setCallBack(DatePickerDialog.OnDateSetListener ondate) {
        ondateSet = ondate;
    }


    @Override
    public Dialog onCreateDialog(Bundle b) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int date = c.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(),ondateSet,year,month,date);
    }


}
