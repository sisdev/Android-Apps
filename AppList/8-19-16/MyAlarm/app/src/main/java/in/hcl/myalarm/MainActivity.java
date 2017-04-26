package in.hcl.myalarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void AlarmElapsed(View v)
    {
        AlarmManager almMgr = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent i1 = new Intent(this,Main2Activity.class);
        PendingIntent pi = PendingIntent.getActivity(this,10, i1,0);
        almMgr.set(AlarmManager.ELAPSED_REALTIME,2*60*1000,pi);
    }

    public void AlarmRTC(View v)
    {
        AlarmManager almMgr = (AlarmManager) getSystemService(ALARM_SERVICE);
        Calendar c1 = Calendar.getInstance();
        c1.add(Calendar.MINUTE, 1);
        Intent i1 = new Intent(this,Main2Activity.class);
        PendingIntent pi = PendingIntent.getActivity(this,10, i1,0);
        almMgr.set(AlarmManager.RTC,c1.getTimeInMillis(),pi);
    }
}
