/**
 * @author UMESH SINGH KUSHWAHA
 */
package com.assusoft.eFairEmall.databaseMaster;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.StringTokenizer;

import com.assusoft.efair.epchfair.gcm.CommonUtilities;
import com.epch.efair.delhifair.EFairEmallApplicationContext;
import com.epch.efair.delhifair.HomeActivity;
import com.epch.efair.delhifair.MainActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Checkable;

public class DBInitializer {
	public static void createTables(SQLiteDatabase db, String file) {
		try {
			Log.i("EFAIR","database creation start");

			StringTokenizer str = new StringTokenizer(file, "/");
			while (str.hasMoreTokens()) {
				Log.i("EFAIR","StringTokenizer:"+str.hasMoreTokens());
				String query = str.nextToken();
				db.execSQL(query);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	//Copy Paste this function in the class where you used above part
 public static 	void copy(String file, String folder,Context context,boolean status) throws IOException 
  	{
	    //if status is true app version is changed and copy the database
	    Log.i("EFAIR","in database copy ");
	  	File CheckDirectory;
	  	CheckDirectory = new File(folder);
	  	if (!CheckDirectory.exists() || status)
	  	{ 
	  	   CheckDirectory.mkdir();
	  	}
	  	else{
	  		
	  		    Log.i("EFAIR","database exist");
	  		return;
	  	
	  	}
	  
	  	 InputStream in = context.getAssets().open(file);
	  	 OutputStream out = new FileOutputStream(folder+DatabaseHelper.DBNAME);
	  	
	  	 // Transfer bytes from in to out
	  	 byte[] buf = new byte[1024];
	  	 int len; 
	  	 while ((len = in.read(buf)) > 0) 
	  		 out.write(buf, 0, len);
	  	 in.close(); out.close();
	  	 
	  	 Log.i("EFAIR","database copied successfully");
	  	 
  	}
 
 private static boolean checkAppVersion(Context context){
	 HomeActivity home=new HomeActivity();
    
	 final SharedPreferences prefs = home.getGcmPreferences(context);
     float appVersion = prefs.getFloat(CommonUtilities.PROPERTY_APP_VERSION,1);
     float currentAppVersion=HomeActivity.getAppVersion(context);
     if(appVersion!=currentAppVersion){
    	 return true;
     }
	 return false;
 }



 private SharedPreferences getGcmPreferences(Context context) {
     HomeActivity home=new HomeActivity();
     return home.getGcmPreferences(context);
 }
}
