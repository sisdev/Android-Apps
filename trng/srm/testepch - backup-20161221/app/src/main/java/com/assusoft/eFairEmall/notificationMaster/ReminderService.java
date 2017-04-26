package com.assusoft.eFairEmall.notificationMaster;

import java.util.Random;

import com.epch.efair.delhifair.R;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.util.Log;


public class ReminderService extends IntentService {
    private int NOTIF_ID = 1;

    public ReminderService(){
        super("ReminderService");
    }

    @Override
      protected void onHandleIntent(Intent intent) {
    	Log.i("ALARM","in handle intent in reminderservice");
    	
    	Random random = new Random(System.nanoTime() % 100000);

    	NOTIF_ID = random.nextInt(1000000000);
    	
        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                
		   android.support.v4.app.NotificationCompat.Builder mBuilder =
				    new android.support.v4.app.NotificationCompat.Builder(this)
				    .setSmallIcon(R.drawable.appicon)
				    .setContentTitle("E-Fair Schedule Reminder")
				    .setContentText("Your schedule alert.")
				    .setWhen(System.currentTimeMillis())    // notification time
				    .setAutoCancel(true);
		   mBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
		  /** If your onhandledIntent  intent includes extras that need to be passed along to the
	         * activity, use setComponent() to indicate that the activity should handle the
	         * handleintent intent. For example:
	         * 
	         * ComponentName comp = new ComponentName(context.getPackageName(), 
	         *     ReminderActivity.class.getName());
	         *      PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent.setComponent(comp) ,
	         *      PendingIntent.FLAG_CANCEL_CURRENT);
	         */      
		  /* //create an intent for activity
		   Intent notificationIntent = new Intent(this, ReminderActivity.class);
		   PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent ,PendingIntent.FLAG_CANCEL_CURRENT);*/
		   ComponentName comp = new ComponentName(getApplicationContext(), 
			             ReminderActivity.class.getName());
	       PendingIntent contentIntent = PendingIntent.getActivity(this,(int) Math.random(), intent.setComponent(comp) ,
			               PendingIntent.FLAG_ONE_SHOT);
	       
		   // Puts the PendingIntent into the notification builder
		   mBuilder.setContentIntent(contentIntent);
		   Notification noty= mBuilder.build();
		   // Builds the notification and issues it.
		   nm.notify(NOTIF_ID, noty);
		   
		// Release the wake lock provided by the BroadcastReceiver.
	        AlarmReceiver.completeWakefulIntent(intent);
	        // END_INCLUDE(service_onhandle)
    }
    
  
}
