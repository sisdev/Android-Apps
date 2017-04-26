package com.assusoft.eFairEmall.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class MySharedPreferences {
	Context context;
	private static final String REGISTERED_YES="yes";
	private static final String REGISTERED_NO="no";
	public static final String STATIC_PAGE_URL = "static_pageurl";
	SharedPreferences preferences;
	public MySharedPreferences(Context context)
	{
		this.context=context;
		preferences = PreferenceManager.getDefaultSharedPreferences(context);
	}
	public void saveFollowing(String key,String value)
	{     
		  
		  SharedPreferences.Editor editor = preferences.edit();
		  editor.putString(key,value);
		  editor.commit();
	}
	public void saveUserData(String key,String value)
	{     
		  
		  SharedPreferences.Editor editor = preferences.edit();
		  editor.putString(key,value);
		  editor.commit();
	}
	public void saveFollowingSize(int size)
	{     
		  
		  SharedPreferences.Editor editor = preferences.edit();
		  editor.putInt("fSize",size);
		  editor.commit();
	}
	public boolean isFollowingDataSaved()
	{     
		  
		  int size = preferences.getInt("fSize",0);
		  if(size>0)
		  {
			  return true; 
		  }
		return false;
	}
	public void saveUserRegistrationStatus(String key,String value)
	{     
		  
		  SharedPreferences.Editor editor = preferences.edit();
		  editor.putString(key,value);
		  editor.commit();
	}
	public boolean isUserRegistered(String key)
	{     
		  
		  String yes = preferences.getString(key,REGISTERED_NO);
		  if(yes.equalsIgnoreCase(REGISTERED_YES)){
			  return true; 
		  }
		return false;
	}
	public String getSavedValue(String key)
	{     
		  
		  String yes = preferences.getString(key,REGISTERED_NO);
		  
		return yes;
	}
}
