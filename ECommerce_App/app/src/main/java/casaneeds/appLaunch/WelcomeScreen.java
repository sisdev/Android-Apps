package casaneeds.appLaunch;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import casaneeds.reports.Task_SummaryActivity;
import casaneeds.taskactivity.MyTaskListActivity;
import casaneeds.unassignedTask.UnassignedTaskActivity;
import casaneeds.utils.LocationTracker;

public class WelcomeScreen extends AppCompatActivity implements View.OnClickListener {
    String name,password,uid;

    SharedPreferences sp;
    SharedPreferences.Editor editor;

    LocationTracker gps ;

    TextView tv;
    Button btnlogout, btntask, btntask_summary, btnunattendtask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tv = (TextView)findViewById(R.id.tvuser);
        btnlogout = (Button)findViewById(R.id.logout);
        btntask = (Button)findViewById(R.id.task);
        btntask_summary = (Button)findViewById(R.id.summary);
        btnunattendtask = (Button)findViewById(R.id.unattended);

        sp = getSharedPreferences(AppConstants.mypreference, Context.MODE_PRIVATE);


        name = sp.getString(AppConstants.User, "");
        password = sp.getString(AppConstants.Password, "");
        uid = sp.getString(AppConstants.USERID,"");


        tv.setText("Welcome" + "\n" + name);

        gps = LocationTracker.getInstance();
        gps.setContext(getApplicationContext());
        gps.init();

        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor = sp.edit();
                editor.remove(AppConstants.User);
                editor.remove(AppConstants.Password);
                editor.remove(AppConstants.USERID);
                editor.remove(AppConstants.IS_LOGIN);

                // Save the changes in SharedPreferences
                editor.commit(); // commit changes
                Toast.makeText(WelcomeScreen.this, "Logging Out, Please Login Again", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), LoginScreen.class);
                startActivity(i);

            }
        });

        btntask_summary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Task_SummaryActivity.class);
                startActivity(i);
            }
        });

        btntask.setOnClickListener(this);
        btnunattendtask.setOnClickListener(this);


 /*       btntask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), MyTaskListActivity.class);
                startActivity(i);
            }
        });


        btnunattendtask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),UnassignedTaskActivity.class);
                startActivity(i);
            }
        });
 */
    }

        @Override
        public void onBackPressed() {
            // TODO Auto-generated method stub
            new AlertDialog.Builder(this)
                    .setIcon(R.drawable.greenalert)
                    .setTitle("Exit !!")
                    .setMessage("Are you sure you want to exit ?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            //java.lang.System.exit(1);
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();
        }

    @Override
    public void onClick(View v) {
    if (!gps.CheckGPSStatus())
    {
        gps.showSettingsAlert(this);
        return;
    }
        switch (v.getId()){
            case R.id.task:
                Intent i1 = new Intent(getApplicationContext(), MyTaskListActivity.class);
                startActivity(i1);
                break ;
            case R.id.unattended:
                Intent i2 = new Intent(getApplicationContext(),UnassignedTaskActivity.class);
                startActivity(i2);
                break ;
        }


    }
}

