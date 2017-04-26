package casaneeds.reports;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import casaneeds.appLaunch.R;
import casaneeds.entity.DatabaseHelper;

public class Task_SummaryActivity extends AppCompatActivity {

    Button btndate, submit;
    final Calendar c = Calendar.getInstance();
    DatabaseHelper dbHelper;
    String date2;
    DatePickerDialog.OnDateSetListener ondate;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setIcon(R.drawable.back_btn);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btndate = (Button) findViewById(R.id.btndate);
        submit = (Button) findViewById(R.id.btnsubmit);
        lv = (ListView) findViewById(R.id.list_myreport);

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        date2 = sdf1.format(c.getTime()).toString();
        btndate.setText(date2);


        btndate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateDialog d = new DateDialog();
                android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                d.setCallBack(ondate);
                d.show(ft, "DatePicker");

            }
        });

        ondate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                c.set(year, monthOfYear, dayOfMonth);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                date2 = sdf.format(c.getTime());
                btndate.setText(date2);
            }
        };


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    LoadData();
            }
        });
    }

//	**************************************************************		call onStart() to Load Data		***************************************************************************************************

    @Override
    public void onStart() {
        super.onStart();
        dbHelper = new DatabaseHelper(this);
      //  LoadData();
    }

    // 	**************************************************************		Load Data 		***************************************************************************************************

    public void LoadData() {

        try {
            Cursor c = dbHelper.getCompletedOrders(date2);


            // The desired columns to be bound
            String[] from = new String[]{DatabaseHelper.ORDERNO, DatabaseHelper.NAME, DatabaseHelper.AREA, DatabaseHelper.AMOUNT};

            // the XML defined views which the data will be bound to
            int[] to = new int[]{R.id.orderno, R.id.name, R.id.address, R.id.amount};

            // create the adapter using the cursor pointing to the desired data as well as the layout information
            SimpleCursorAdapter sca = new SimpleCursorAdapter(this, R.layout.tasksummary_row, c, from, to, 0);
            lv.setAdapter(sca);

        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_LONG).show();
            Log.d("Task_Summary_Report", ex.toString());
        }
    }
}
