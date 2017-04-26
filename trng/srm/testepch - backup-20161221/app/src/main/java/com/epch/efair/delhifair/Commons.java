package com.epch.efair.delhifair;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;

import com.assusoft.eFairEmall.util.MySharedPreferences;
import com.assusoft.eFairEmall.util.WebDataFile;
import com.google.ads.AdRequest;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Logger.LogLevel;
import com.google.android.gms.analytics.Tracker;

public class Commons
{
	static Tracker tracker;
	Context con;
	private static GoogleAnalytics analytics;
	public static SQLiteDatabase db;
	//Prevent hits from being sent to reports, i.e. during testing.
	public static final boolean GA_IS_DRY_RUN = false;
	public static AdRequest ads;
	// GA Logger verbosity.
//	private static final LogLevel GA_LOG_VERBOSITY = LogLevel.INFO;
//	public static final String admobToken="a153857519b5385";
//	private static String GA_PROPERTY_ID="UA-47860678-2";
/*	public static void getMyTracker(Context con,String screenname)
	{   
	// Initialize a tracker using a Google Analytics property ID.
	analytics=GoogleAnalytics.getInstance(con);
	tracker=analytics.getTracker(GA_PROPERTY_ID);
	// Set dryRun flag.
	///analytics.setDryRun(GA_IS_DRY_RUN);
	 // Set Logger verbosity.
	analytics.getLogger().setLogLevel(GA_LOG_VERBOSITY);
	//register screens
	tracker.set(Fields.SCREEN_NAME,screenname);
	tracker.send(MapBuilder
		    .createAppView()
		    .build()
		);
	}*/
public static WebDataFile webDataFile;
public static ImageUtils imageUtils;
public static MySharedPreferences mySharedPreferences ;
public static AdRequest GetAds(Context context)
{
	if (Commons.ads == null)
	{
		/*AdRequest request = new AdRequest();
		TelephonyManager tm =(TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
		if (tm != null)
		{
			String deviceId = tm.getDeviceId();
			request.addTestDevice(deviceId);
		}
		else
		{
			request.addTestDevice(AdRequest.TEST_EMULATOR);	
			request.addTestDevice("test"); 	
		}
		Commons.ads = request;*/
	}
	return Commons.ads;
}
public void MyAlertDialog(Context context)
{
	new AlertDialog.Builder(context)
    .setTitle("Map is not downloaded")
    .setMessage("Currently location data not available Please try again later")
    .setCancelable(true)
    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) { 
        	dialog.dismiss();
        }
     })
    /*.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) { 
        	dialog.dismiss();
        	Intent intent=new Intent(getActivity(), MainActivity.class);
        	MainActivity.activity.startActivity(intent);
        	MainActivity.activity.finish();
        }
     })*/
    .setIcon(android.R.drawable.ic_dialog_alert)
     .show();	
}
}
