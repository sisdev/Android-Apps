/**
 * @author Akshay kumar
 */
package com.epch.efair.delhifair;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import org.json.JSONObject;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;

import com.assusoft.eFairEmall.databaseMaster.DBInitializer;
import com.assusoft.eFairEmall.databaseMaster.DatabaseHelper;
import com.assusoft.eFairEmall.entities.Appointment;
import com.assusoft.eFairEmall.entities.Favourite;
import com.assusoft.eFairEmall.entities.PhotoShoot;
import com.assusoft.eFairEmall.entities.VenueMap;
import com.assusoft.eFairEmall.internetAndGpsMaster.InternetConnectionDetector;
import com.assusoft.eFairEmall.soapWebService.WebService;
import com.assusoft.eFairEmall.util.MySharedPreferences;
import com.assusoft.efair.epchfair.Fragments.FirstViewFragment;
import com.assusoft.efair.epchfair.Fragments.SplashFragment;
import com.assusoft.efair.epchfair.gcm.CommonUtilities;

public class MainActivity extends FragmentActivity {
	Context context;
	public static Activity activity;
	public static ArrayList<VenueMap> updatingVenueMapFile;
	public static FragmentManager fm;
	public static boolean activityInBackGround;
	MySharedPreferences mySharedPreferences;
	boolean isBackBtnPressed;
	public static long startTime;
	private DatabaseHelper dbHelper;
	private ArrayList<Favourite> favourites;
	private ArrayList<PhotoShoot> photoShoots;
	private ArrayList<Appointment> appointment; 
	public static long startTimeDownload;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		activity=this;
		
		dbHelper=EFairEmallApplicationContext.getDatabaseHelper();
		
		fm = MainActivity.this.getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		
		SplashFragment fragment= new SplashFragment();
        ft.add(R.id.content_frame_registration, fragment,"splash");
        ft.commit();
        
        ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        int memoryClass = am.getMemoryClass();
        Log.v("onCreate", "memoryClass:" + Integer.toString(memoryClass));
        Runtime rt = Runtime.getRuntime();
        long maxMemory = rt.maxMemory();
        Log.v("onCreate", "maxMemory:" + Long.toString(maxMemory));
        new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				
				/**Checking for application version mismatch**/
				 if(checkForVersionMismatch()){
					 
					 Log.i("EFAIR","checkForVersionMismatch -: "+checkForVersionMismatch());
					 		
					 float appVersion;
			 		 final SharedPreferences prefs =getSharedPreferences(MainActivity.class.getSimpleName(),
			                Context.MODE_PRIVATE);
			 		 
			 	     try{
			 	    	 appVersion = prefs.getFloat(CommonUtilities.PROPERTY_APP_VERSION,0.0f);
			 	     }catch(Exception e){
			 	    	Log.i("EFAIR","Exception "+e);
			 	    	 appVersion = (float) prefs.getInt(CommonUtilities.PROPERTY_APP_VERSION,0);
			 	     }
			 	     
			 	     if(appVersion>5.6f)
						 retrieveLocalDatbase();
					 
					 activity.deleteDatabase(DatabaseHelper.DBNAME);						
					 DatabaseHelper databaseHelper=new DatabaseHelper(activity);
					 EFairEmallApplicationContext.setDatabaseHelper(databaseHelper);
					 //copy the local database
					 
					 try {
						DBInitializer.copy("EPCH_DB.db","data/data/"+getPackageName()+"/databases/", activity,true);
					} catch (IOException e) {
						e.printStackTrace();
					}
					 
			 	     File folder = new File(Environment.getExternalStorageDirectory(),ImageAsyncTask.FOLDER_NAME);
					 if(folder.exists())
					 {
						 folder.delete(); 
					 }
					 try {
						copyAllLayout();
					} catch (Exception e) {
						e.printStackTrace();
					}
					 if(appVersion>5.6f)
						 submitLocalData();
				 }
				 /**Storing application version**/
				 storeAppVersion(activity);
				
				//Calling AsyncTask for loading data from server
				//check Internet connection 
				InternetConnectionDetector internetConnection=new InternetConnectionDetector(MainActivity.this);
				if(!internetConnection.isConnectingToInternet()){
					gotoNextActivity();
				}else{
					 Log.i("EXPO", "isBackBtnPressed:"+isBackBtnPressed);
					 if(!isBackBtnPressed){
						 startTime=System.currentTimeMillis();
						 dbHelper.openDatabase(DatabaseHelper.READMODE);
						 updatingVenueMapFile = dbHelper.getAllUpdatingVenuemap(1);
						 new CheckForUpdate().execute();
					 }
					 
				}		        
			}
		}, 3000);
		
	}

		
  	
  	
  	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	  	
	/** Retrieve Favorite list add in bookmarks*/
	private void retrieveLocalDatbase(){
		Log.i("retrieve","retrieveLocalDatbase");
  		dbHelper.openDatabase(DatabaseHelper.READMODE);
  		favourites=dbHelper.getAllFavourites();
  		photoShoots=dbHelper.getAllPhotoShoots();
  		appointment=dbHelper.getAllAppointment();
  		dbHelper.close();
  	}
	
	/** Submit retrieved data into tables*/
  	private void submitLocalData(){
  		dbHelper.openDatabase(DatabaseHelper.WRITEMODE);
  		if(favourites.size()>0)
  			dbHelper.submitBookMark(favourites);
  		if(photoShoots.size()>0)
  			dbHelper.insertPhotoShoot(photoShoots, true);
  		if(appointment.size()>0)
  			dbHelper.insertAppointment(appointment);
  		dbHelper.close();
  	}
	
	
  	private boolean checkForVersionMismatch(){
  		float appVersion;
 		 final SharedPreferences prefs =getSharedPreferences(MainActivity.class.getSimpleName(),
                Context.MODE_PRIVATE);
 		 
 	     try{
 	    	 appVersion = prefs.getFloat(CommonUtilities.PROPERTY_APP_VERSION,0.0f);
 	     }catch(Exception e){
 	    	Log.i("EFAIR","Exception "+e);
 	    	 appVersion = (float) prefs.getInt(CommonUtilities.PROPERTY_APP_VERSION,0);
 	     }
 	     float currentAppVersion=HomeActivity.getAppVersion(getApplicationContext());
 	     
 	     Log.i("EFAIR","Installed app version -: "+appVersion);
 	     Log.i("EFAIR","Current  app  version -: "+currentAppVersion);
 	     
 	     if(appVersion!=currentAppVersion){
 	    	 return true;
 	     }
 		 return false;
  	}
  	
  	 private void storeAppVersion(Context context) {
         final SharedPreferences prefs = getSharedPreferences(MainActivity.class.getSimpleName(),
                 Context.MODE_PRIVATE);;
         float appVersion = HomeActivity.getAppVersion(context);
         Log.i(CommonUtilities.TAG, "Saving regId on app version " + appVersion);
         SharedPreferences.Editor editor = prefs.edit();
        
         editor.putFloat(CommonUtilities.PROPERTY_APP_VERSION, appVersion);
         editor.commit();
     }
  	 @Override
  	protected void onResume() {
  		// TODO Auto-generated method stub
  		super.onResume();
  		mySharedPreferences=new MySharedPreferences(this);
  		boolean isDone;
  		activityInBackGround=false;
  		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
  		isDone=preferences.getBoolean(DownloadingData.ON_POST_EXECUTE_DOWNLOAD_KEY, false);
   	     
  		Log.i("EXPO", "isDone:"+isDone);
  		 Log.i("EXPO", "ImageAsyncTask.onPostExecuteFlag:"+ImageAsyncTask.onPostExecuteFlag);
  		if(isDone)
  		{ 
  		 SharedPreferences.Editor editor = preferences.edit();
	     editor.putBoolean(DownloadingData.ON_POST_EXECUTE_DOWNLOAD_KEY,false);
	     editor.commit();	
  		 new ImageDownloading(this);
  		}else if(ImageAsyncTaskSoap.onPostExecuteFlag)
  		{  
  			ImageAsyncTaskSoap.onPostExecuteFlag=false;
  		   DownloadingData.mProgressDialog.dismiss();
  		/*if(mySharedPreferences.isUserRegistered(WebService.REGISTERED_STATUS)){
	    	   Intent homepage=new Intent(context,HomeAcitityFirst.class);
		       MainActivity.activity.startActivity(homepage);
		       MainActivity.activity.finish();
		   }else{
	       UserRegistrationFragment fragment= new UserRegistrationFragment();
	        FragmentTransaction ft =MainActivity.fm.beginTransaction();
	        ft.replace(R.id.content_frame_registration, fragment,"Home");
	        ft.commit();
		   }*/
  		   	FirstViewFragment fragment= new FirstViewFragment();
	        FragmentTransaction ft =MainActivity.fm.beginTransaction();
	        ft.replace(R.id.content_frame_registration, fragment,"Home");
	        ft.commit();
  		}
  		
  	}
  	@Override
  	protected void onPause() {
  		super.onPause();
  		activityInBackGround=true;
  		Log.i("EXPO", "activity gone in background");
  	}
  	@Override
  	public void onBackPressed() {
  		super.onBackPressed();
  		isBackBtnPressed=true;
  		
  	}
  	
  	//copy image files from assets to sd cord
  	public void copyAllLayout(){
	    //if status is true app version is changed and copy the database
	    Log.i("EFAIR","in database copy ");
	  	File CheckDirectory;
	  	CheckDirectory = new File(Environment.getExternalStorageDirectory(),ImageAsyncTask.FOLDER_NAME);
	  	if (!CheckDirectory.exists()){ 
	  	   CheckDirectory.mkdir();
	  	}
  		copyLayout("App_Feature_Intro.html");
  		copyLayout("IHGF_Intro.html");
  		copyLayout("ContactUsIHGF.html");
	  	copyLayout("fairFacilites.html");
	  	copyLayout("Help_desk_number.html");
	  	
	  	copyLayout("seminar_16-04-15.html");
	  	copyLayout("seminar_17-04-15.html");
	  	copyLayout("seminar_18-04-15.html");
//	  	copyLayout("seminar_23-02-16.html");
//	  	copyLayout("seminar_18-10-15.html");
	  	
	  	//copyLayout("EmpaneledHotelsIFJAS.pdf");
	  	copyLayout("EmpaneledHotelsIHGF.pdf");
	  	copyLayout("ExhibitorListIHGF.pdf");
	  	copyLayout("FreeShuttleIHGF.pdf");
	  	copyLayout("IHGF  DELHI FAIR (AUTUMN) 2015  SHUTTLE PLAN.pdf");
	  	
	  	copyLayout("FirstFloor.jpg");
	  	copyLayout("FirstFloor.assus");
	  	copyLayout("GroundFloor.jpg");
	  	copyLayout("GroundFloor.assus");;
	  	copyLayout("route_tileset.png");
	  	copyLayout("SecondFloor.jpg");
	  	copyLayout("SecondFloor.assus");
	  	copyLayout("ThirdFloor.jpg");
	  	copyLayout("ThirdFloor.assus");
	  	copyLayout("venuemap.jpg");
	  	copyLayout("venuemap.assus");
	  	
	  	//copyLayout("venuemapifjas.jpg");
	  	
//	  	copyLayout("hall-1-3-5-7.jpg");
//	  	copyLayout("hall-1-3-5-7.assus");
//	  	copyLayout("hall-2-4-6-8.jpg");
//	  	copyLayout("hall-2-4-6-8.assus");
//	  	copyLayout("hall-9-10-11-12.jpg");
//	  	copyLayout("hall-9-10-11-12.assus");
//	  	copyLayout("hall-14-15.jpg");
//	  	copyLayout("hall-14-15.assus");
	  	copyLayout("hall1_3.jpg");
	  	copyLayout("hall5_7.jpg");
	  	copyLayout("hall1_3.assus");
	  	copyLayout("hall5_7.assus");
  	}
  	
  	 void copyLayout(String file){
  		try{
  			InputStream in = getAssets().open(file);
  		  	 OutputStream out = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+ImageAsyncTaskSoap.FOLDER_NAME+File.separator+file);
  		  	
  		  	 // Transfer bytes from in to out
  		  	 byte[] buf = new byte[1024];
  		  	 int len; 
  		  	 while ((len = in.read(buf)) > 0) 
  		  		 out.write(buf, 0, len);
  		  	 in.close(); out.close();  		  	 
  		  	 Log.i("EFAIR","Image copied successfully "+file);
  		}catch(Exception e){
  			e.printStackTrace();
  		}
  	}
  	 
  	 
  	 class CheckForUpdate extends AsyncTask<Void,Void,Void>{
  		ProgressDialog progressDialog;
		String response;
		@Override
		protected Void doInBackground(Void... params) {
			
		   DatabaseHelper dbHelper=EFairEmallApplicationContext.getDatabaseHelper();
		   dbHelper.openDatabase(DatabaseHelper.WRITEMODE);
			String timeStamp = dbHelper.getLattestTimeStam();
    		Log.i("WEB_DATA", "timeStamp:"+timeStamp);
    		Log.i("Check_update","timeStamp:"+timeStamp);
    		String name=WebService.randomString(WebService.RENDOM_STR_LENGTH);
    	    String key = null;
			try {
				key = WebService.getHmac(name,WebService.SALT);
			} catch (Exception e) {
				e.printStackTrace();
			}
    	    Log.i("WEB_DATA", "name:"+name+"key:"+key);
    	 if(timeStamp!=null){	
    		String param="{\"greatestTimeStamp\":"+"\""+timeStamp+"\","+
     	             "\"name\":"+"\""+name+"\","+
     	             "\"key\":"+"\""+key+"\""+"}";
	    		Log.i("WEB_DATA","Updates service param:"+param);
				response=WebService.checkForUpdates(param);
				Log.i("WEB_DATA","Updates service resp:"+response);
    		}
			
			return null;
		}
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = new ProgressDialog(activity);
	    	progressDialog.setMessage("Please wait...");
	    	progressDialog.setIndeterminate(true);
	    	progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	    	progressDialog.setCancelable(false);
	    	progressDialog.show();
		}
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if(progressDialog!=null && progressDialog.isShowing()){
	    	     progressDialog.dismiss();
	    	}
	    	try {
				JSONObject jData=new JSONObject(response);
				String status=jData.getString(WebService.STATUS);
				Log.i("EXPO","updates status : "+status);
				if(status.equalsIgnoreCase(WebService.RESPONSE_STATUS_PASS)){   
			        String statusCode=jData.getString("recordStatus");
			       
			        if(statusCode.equalsIgnoreCase("updates is available")||!updatingVenueMapFile.isEmpty()){
			        	 Log.i("EXPO","updates status : "+statusCode.equalsIgnoreCase("updates is available")); 
			        	//show alert dialog
			        	showDialog();
			        }else{
			        	gotoNextActivity();
			        }
			        
					
			        
				}else if(status.equalsIgnoreCase(WebService.RESPONSE_ERROR))
				{ 
					String error=jData.getString(WebService.RESPONSE_ERROR);
					gotoNextActivity();
					
				}
			} catch (Exception e) {
				e.printStackTrace();
				gotoNextActivity();
				
			}
	    	
	    	
		}
  	 }
  	public void showDialog() {
  		Log.i("EXPO","showDialog()"); 
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

		// Setting Dialog Title
		alertDialog.setTitle("Data Update!");
		alertDialog.setCancelable(false);

		// Setting Dialog Message
			alertDialog
			.setMessage("Please update for latest information!");
    
	// On pressing Wifi button
	alertDialog.setPositiveButton("Update",
			new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					//update the database
					dialog.dismiss();
					startTimeDownload=System.currentTimeMillis();
					Log.i("StartTime",""+System.currentTimeMillis());
					new DownloadingData(MainActivity.this); 
				}
			});
	
	// on pressing cancel button
	alertDialog.setNegativeButton("Cancel",
			new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
					gotoNextActivity();
				}
			});
		// Showing Alert Message
		alertDialog.show();
	}
  	
  	void gotoNextActivity(){
  		
  		FirstViewFragment fragment= new FirstViewFragment();
        FragmentTransaction ft =MainActivity.fm.beginTransaction();
        ft.replace(R.id.content_frame_registration, fragment,"Home");
        ft.commit();
        
  		/*if(mySharedPreferences.isUserRegistered(WebService.REGISTERED_STATUS)){
	    	   Intent homepage=new Intent(activity,HomeAcitityFirst.class);
		       MainActivity.activity.startActivity(homepage);
		       MainActivity.activity.finish();
		   }else{
				InternetConnectionDetector internetConnection=new InternetConnectionDetector(MainActivity.this);
				if(!internetConnection.isConnectingToInternet()){
					internetConnection.showInternetSettingsAlert();
					return;
				}
		        UserRegistrationFragment fragment= new UserRegistrationFragment();
		        FragmentTransaction ft =MainActivity.fm.beginTransaction();
		        ft.replace(R.id.content_frame_registration, fragment,"Home");
		        ft.commit();
		   }*/
  	}
}
