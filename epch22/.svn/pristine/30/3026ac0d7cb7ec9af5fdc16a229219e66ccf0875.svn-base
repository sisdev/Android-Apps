/**
 * @author UMESH SINGH KUSHWAHA
 */
package com.assusoft.eFairEmall.internetAndGpsMaster;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;

public class InternetConnectionDetector {
	 private Context context;
	 
	    public InternetConnectionDetector(Context context){
	        this.context = context;
	    }
	 
	    /**
	     * Checking for all possible Internet providers
	     * **/
	    public boolean isConnectingToInternet(){
	        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	          if (connectivity != null)
	          {
	              NetworkInfo[] info = connectivity.getAllNetworkInfo();
	              if (info != null)
	                  for (int i = 0; i < info.length; i++)
	                      if (info[i].getState() == NetworkInfo.State.CONNECTED)
	                      {
	                          return true;
	                      }
	 
	          }
	          return false;
	    }
	    /**
		 * Function to show settings alert dialog On pressing Settings button will
		 * lauch Settings Options
		 * */
		public void showInternetSettingsAlert() {
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

			// Setting Dialog Title
			alertDialog.setTitle("Internet  settings");

			// Setting Dialog Message
			alertDialog
					.setMessage("Internet connection is not enabled. Do you want to go to settings menu?");

			// On pressing Wifi button
			alertDialog.setPositiveButton("Settings",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							Intent intent = new Intent(
									Settings.ACTION_WIFI_SETTINGS);
							context.startActivity(intent);
						    //System.exit(0);
						}
					});
			//On pressing 3g button
			/*alertDialog.setNeutralButton("3G",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							Intent intent = new Intent(Intent.ACTION_MAIN);
							intent.setClassName("com.android.phone", "com.android.phone.NetworkSetting");
							context.startActivity(intent);
						}
					});*/
			// on pressing cancel button
			alertDialog.setNegativeButton("Cancel",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
							System.exit(0);
						}
					});

			// Showing Alert Message
			alertDialog.show();
		}
}
