package com.epch.efair.delhifair;

import java.io.File;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;

import com.assusoft.eFairEmall.databaseMaster.DatabaseHelper;

public class ImageUtils {
	Context context;
	DatabaseHelper dbHelper;
	public  String[] layoutes;
	public ImageUtils(Context context)
	{
		this.context=context;
		dbHelper=EFairEmallApplicationContext.getDatabaseHelper();
		//get the readable database
		dbHelper.openDatabase(DatabaseHelper.READMODE);
		layoutes=dbHelper.getAllLayoutsImage();
	}
	public void checkFile(Context con,String filename)
	{
	String path=con.getFilesDir().getAbsolutePath()+"/"+filename;
	File file = new File ( path ); 

	if ( file.exists() ) 
	{
	    Log.i("EFAIR", "File exist");
	}
	else
	{
		 Log.i("EFAIR", "File not exist");
	}
	}
	public String getImageUrl(){     
		  SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		  String name = preferences.getString("ImgUrl","");
		  if(!name.equalsIgnoreCase("")){
		      /* Edit the value here*/
		  }
		return name;
	}
	public boolean isFileAvailabeInExternalStorage()
	{   
		File folder = new File(Environment.getExternalStorageDirectory(),ImageAsyncTask.FOLDER_NAME);
		for(int i=0;i<layoutes.length;i++)
		{   
			File file = new File (folder, layoutes[i]);
		   if ((!file.exists())) 
		   { Log.i("WEB_DATA","isFileAvailabe call..and file is not available");
			return false;
	       }
		}
		return true;
	}
	public boolean isFileAvailabeInExternalStorage(String name)
	{   
		File folder = new File(Environment.getExternalStorageDirectory(),ImageAsyncTask.FOLDER_NAME);
		for(int i=0;i<layoutes.length;i++)
		{   
			File file = new File (folder, layoutes[i]);
			Log.i("EXPO", "layoutes[i]:"+layoutes[i]+",name:"+name);
		   if ((!file.exists())&&(layoutes[i].equalsIgnoreCase(name))) 
		   { 
			return false;
	       }
		}
		return true;
	}
	public boolean isFileAvailabeInInternalStorage(){
		for(int i=0;i<layoutes.length;i++){
			String path=context.getFilesDir().getAbsolutePath()+"/"+layoutes[i];
			File file = new File ( path );
			if(!file.exists())
			{
				return false;
			}
		}
		return true;
	}
	public boolean isFileAvailabeInInternalStorage(String name)
	{   for(int i=0;i<layoutes.length;i++)
	    {
		String path=context.getFilesDir().getAbsolutePath()+"/"+layoutes[i];
	    File file = new File ( path ); 
	      if(!file.exists()&&(layoutes[i].equalsIgnoreCase(name)))
	      {
		  return false;
	      }
	    }
	return true;
	}
	
	/*public boolean updateVenueMapFile(String image){   
		File folder = new File(Environment.getExternalStorageDirectory(),ImageAsyncTask.FOLDER_NAME);
		for(int i=0;i<layoutes.length;i++){   
			File file = new File (folder, layoutes[i]);
		   if ((file.exists())&&(layoutes[i].equalsIgnoreCase(image))) { 
		     file.delete();
		     Log.i("WEB_DATA","venue map file  is updated");
			 return true;
	       }
		}
		return false;
	}*/
}
