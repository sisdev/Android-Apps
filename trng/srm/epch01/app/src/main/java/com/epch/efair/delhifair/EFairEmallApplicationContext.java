/**
 * @author UMESH SINGH KUSHWAHA
 */
package com.epch.efair.delhifair;



import java.util.HashMap;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.assusoft.eFairEmall.databaseMaster.DatabaseHelper;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

public class EFairEmallApplicationContext extends MultiDexApplication {
	Context context;
	public static GoogleAnalytics analytics;
	public static Tracker tracker;
	private static DatabaseHelper databaseHelper;
	private static EFairEmallApplicationContext eFairEmallApplicationContext;
	public enum TrackerName {
	    APP_TRACKER, // Tracker used only in this app.
	    GLOBAL_TRACKER, // Tracker used by all the apps from a company. eg: roll-up tracking.
	    ECOMMERCE_TRACKER, // Tracker used by all ecommerce transactions from a company.
	  }
	HashMap<TrackerName, Tracker> mTrackers = new HashMap<TrackerName, Tracker>();
	
	@Override
	public void onCreate() {
		
		super.onCreate();
		setEFairEmallApplicationContext(this);
		databaseHelper=new DatabaseHelper(this);
		Log.i("EFAIR","databasehelper object created successfully");
		this.registerReceiver(this.mConnReceiver,
                new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
		
		analytics = GoogleAnalytics.getInstance(this);
		analytics.setLocalDispatchPeriod(0);
		
		tracker = analytics.newTracker("UA-47860678-12");//UA-47860678-11 AL India key
		//tracker.enableExceptionReporting(true);
		tracker.enableAdvertisingIdCollection(true);
	}
	
	
	public static void setEFairEmallApplicationContext(
			EFairEmallApplicationContext eFairEmallApplicationContext2) {
		eFairEmallApplicationContext=eFairEmallApplicationContext2;
		
	}
	public static EFairEmallApplicationContext getEFairEmallApplicationContext()
	{
		return eFairEmallApplicationContext;
		
	}
	

	public static DatabaseHelper getDatabaseHelper(){
		return databaseHelper;
	}
	public static void setDatabaseHelper(DatabaseHelper db){
		EFairEmallApplicationContext.databaseHelper=db;
	}
	
//	private BroadcastReceiver mConnReceiver = new BroadcastReceiver() {
//        public void onReceive(Context context, Intent intent) {
//           /* boolean noConnectivity = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
//            String reason = intent.getStringExtra(ConnectivityManager.EXTRA_REASON);
//            boolean isFailover = intent.getBooleanExtra(ConnectivityManager.EXTRA_IS_FAILOVER, false);*/
//
//            /*ConnectivityManager connMgr =
//                    (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//
//            NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();*/
//           // NetworkInfo otherNetworkInfo = (NetworkInfo) intent.getParcelableExtra(ConnectivityManager.EXTRA_OTHER_NETWORK_INFO);
///*
//            if(activeInfo != null && activeInfo.isConnected()){
//                Toast.makeText(getApplicationContext(), "Connected", Toast.LENGTH_LONG).show();
//            }else{
//                Toast.makeText(getApplicationContext(), "Not Connected", Toast.LENGTH_LONG).show();
//            }*/
//        }
//    };
    private BroadcastReceiver mConnReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
        	
        }
    };
    
    public static Tracker tracker(){
    	return tracker;
    }
    
    public static GoogleAnalytics analytics(){
    	return analytics;
    }
   
    /*public synchronized Tracker getTracker(TrackerName trackerId) {
        if (!mTrackers.containsKey(trackerId)) {

          GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
          
          if(trackerId == TrackerName.APP_TRACKER) 
          {
        	 Tracker t=analytics.newTracker("UA-49709062-1");
          mTrackers.put(trackerId, t);
          }
        }
        return mTrackers.get(trackerId);
      }*/
    
   
    
}
