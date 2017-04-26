package com.assusoft.eFairEmall.notificationMaster;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.assusoft.eFairEmall.entities.Appointment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;
/**
 * When the alarm fires, this WakefulBroadcastReceiver receives the broadcast Intent 
 * and then starts the IntentService {@code SampleSchedulingService} to do some work.
 */
public class AlarmReceiver extends WakefulBroadcastReceiver {
    // The app's AlarmManager, which provides access to the system alarm services.
    private AlarmManager alarmMgr;
    // The pending intent that is triggered when the alarm fires.
    private PendingIntent alarmIntent;
  
    public void onReceive(Context context, Intent intent) {   
       Log.i("ALARM","alarm is received");
    	// BEGIN_INCLUDE(alarm_onreceive)
        /* 
         * If your receiver intent includes extras that need to be passed along to the
         * service, use setComponent() to indicate that the service should handle the
         * receiver's intent. For example:
         * 
         * ComponentName comp = new ComponentName(context.getPackageName(), 
         *      MyService.class.getName());
         *
         * // This intent passed in this call will include the wake lock extra as well as 
         * // the receiver intent contents.
         * startWakefulService(context, (intent.setComponent(comp)));
         * 
         * In this example, we simply create a new intent to deliver to the service.
         * This intent holds an extra identifying the wake lock.
         */
    	ComponentName comp = new ComponentName(context.getPackageName(), 
    	              ReminderService.class.getName());
    	         
    	          // This intent passed in this call will include the wake lock extra as well as 
    	          // the receiver intent contents.
    	          startWakefulService(context, (intent.setComponent(comp)));
       /* Intent service = new Intent(context, ReminderService.class);
        
        // Start the service, keeping the device awake while it is launching.
        startWakefulService(context, service);*/
        // END_INCLUDE(alarm_onreceive)
    }

    // BEGIN_INCLUDE(set_alarm)
    /**
     * Sets a repeating alarm that runs once a day at approximately 8:30 a.m. When the
     * alarm fires, the app broadcasts an Intent to this WakefulBroadcastReceiver.
     * @param context
     */
    public void setAlarm(Context context,Appointment apt) {
       
    	alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        //parse the information from Appointment object
        String date=apt.getDate().trim();
        Log.i("ALARM","DATE is "+date);
        /*Date dateObj=Date.valueOf(date);*/
        String splitDate[]=date.split("-");
        int day=Integer.parseInt(splitDate[2]);
        int month=Integer.parseInt(splitDate[1]);
        int year=Integer.parseInt(splitDate[0]);//dateObj.getYear();
        String time=apt.getStartTime().trim();
        String[] splitTime=time.split(":");
        int hour=Integer.parseInt(splitTime[0]);
        int minute=Integer.parseInt(splitTime[1]);
        //make 24 hour formate
        if(hour<9){ hour+=12;}
        //set the notification time 5 minute early than start time
        if(minute==0){
        	hour=hour-1;
        	minute=55;
        }else {
        	minute=minute-5;
        }
        if(apt.getType()==0){
        	 intent.putExtra("EXHIBITORNAME",apt.getExhibitorName());
        }else{
        	 intent.putExtra("EXHIBITORNAME",apt.getEventName());
        }
        intent.putExtra("TYPE",apt.getType());
        //put the extra data to intent
        intent.putExtra("DATE",apt.getDate());
        intent.putExtra("STARTTIME",apt.getStartTime());
        intent.putExtra("ENDTIME",apt.getEndTime());
        //pending intent request code must be unique .If requestCode is the same,
        //then the new alarm will overwrite the old one. hence request code day+hour+minute hour must 24-formate
        alarmIntent = PendingIntent.getBroadcast(context, day+hour+minute, intent,PendingIntent.FLAG_ONE_SHOT);
//make the date formate
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date alarmDate = null;
        try {
			alarmDate=dateFormat.parse(date.replace("-","/"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Calendar calendar = Calendar.getInstance();
      //  calendar.setTimeInMillis(System.currentTimeMillis());
        //set the alarm's trigger date
        calendar.setTimeInMillis(alarmDate.getTime());
        
        // Set the alarm's trigger time 
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        Log.i("ALARM","year="+year+" month="+month+" day="+day+" hour="+hour+" minute= "+minute);
        Log.i("ALARM","alarm is set at "+calendar.getTime());
        
  
        /* 
         * If you don't have precise time requirements, use an inexact repeating alarm
         * the minimize the drain on the device battery.
         * 
         * The call below specifies the alarm type, the trigger time, the interval at
         * which the alarm is fired, and the alarm's associated PendingIntent.
         * It uses the alarm type RTC_WAKEUP ("Real Time Clock" wake up), which wakes up 
         * the device and triggers the alarm according to the time of the device's clock. 
         * 
         * Alternatively, you can use the alarm type ELAPSED_REALTIME_WAKEUP to trigger 
         * an alarm based on how much time has elapsed since the device was booted. This 
         * is the preferred choice if your alarm is based on elapsed time--for example, if 
         * you simply want your alarm to fire every 60 minutes. You only need to use 
         * RTC_WAKEUP if you want your alarm to fire at a particular date/time. Remember 
         * that clock-based time may not translate well to other locales, and that your 
         * app's behavior could be affected by the user changing the device's time setting.
         * 
         * Here are some examples of ELAPSED_REALTIME_WAKEUP:
         * 
         * // Wake up the device to fire a one-time alarm in one minute.
         * alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, 
         *         SystemClock.elapsedRealtime() +
         *         60*1000, alarmIntent);
         *        
         * // Wake up the device to fire the alarm in 30 minutes, and every 30 minutes
         * // after that.
         * alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, 
         *         AlarmManager.INTERVAL_HALF_HOUR, 
         *         AlarmManager.INTERVAL_HALF_HOUR, alarmIntent);
         */
        
        // Set the alarm to fire at approximately 8:30 a.m., according to the device's
        // clock, and to repeat once a day.
        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP,  
                calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, alarmIntent);
        
        // Enable {@code BootReceiver} to automatically restart the alarm when the
        // device is rebooted.
        ComponentName receiver = new ComponentName(context, BootReceiver.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);           
    }
    // END_INCLUDE(set_alarm)

    /**
     * Cancels the alarm.
     * @param context
     */
    // BEGIN_INCLUDE(cancel_alarm)
    public void cancelAlarm(Context context) {
        // If the alarm has been set, cancel it.
        if (alarmMgr!= null) {
            alarmMgr.cancel(alarmIntent);
        }
        
        // Disable {@code SampleBootReceiver} so that it doesn't automatically restart the 
        // alarm when the device is rebooted.
        ComponentName receiver = new ComponentName(context, BootReceiver.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }
    // END_INCLUDE(cancel_alarm)
}
