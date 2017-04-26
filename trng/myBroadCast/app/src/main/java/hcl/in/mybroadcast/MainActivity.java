package hcl.in.mybroadcast;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void broadcast(View v)
    {
        Intent i1 = new Intent("in.hcl.myBroadcast");
        i1.putExtra("MyVal","Data for Broadcast REcevier");
        sendBroadcast(i1);
    }

    public void notifyUser(View v)
    {
        NotificationManager notiMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification.Builder notiBuild = new Notification.Builder(this);
        notiBuild.setContentTitle("My Notification");
        notiBuild.setContentText("My Detailed Notification");
        notiBuild.setSmallIcon(R.mipmap.ic_launcher);
        long[] vibrate={0,100,200,300,200,100,0};
        notiBuild.setVibrate(vibrate);
        Intent i2 = new Intent(this,MyReceiver.class);
        i2.putExtra("MyVal", "My Notification Manager");
        PendingIntent pi1 = PendingIntent.getBroadcast(this,10,i2,0);
        notiBuild.setContentIntent(pi1);
        Notification noti01 = notiBuild.build();
        notiMgr.notify(10,noti01);
    }

}
