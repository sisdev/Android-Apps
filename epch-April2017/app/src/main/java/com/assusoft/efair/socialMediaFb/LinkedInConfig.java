package com.assusoft.efair.socialMediaFb;

import com.epch.efair.delhifair.R;

import android.content.Context;
import android.util.Log;

public class LinkedInConfig {
	public String linkedInAPIKey;
	public String linkedSecretKey;
	public static String scopeParams = "rw_nus+r_basicprofile+r_fullprofile";
	
	public static String OAUTH_CALLBACK_SCHEME = "x-oauthflow-linkedin";
	public static String OAUTH_CALLBACK_HOST = "callback";
	public static String OAUTH_CALLBACK_URL = OAUTH_CALLBACK_SCHEME + "://" + OAUTH_CALLBACK_HOST;
	//public static String OAUTH_CALLBACK_URL="oob";
	public LinkedInConfig(Context con) {
	      linkedInAPIKey=con.getResources().getString(R.string.LinkedInAPIKey);
		  linkedSecretKey=con.getResources().getString(R.string.LinkedSecretKey);
		Log.i("EFAIR", "LinkedSecretKey:"+linkedSecretKey+",linkedInAPIKey:"+linkedInAPIKey);
	}
}
