package com.assusoft.eFairEmall.util;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;

import com.assusoft.eFairEmall.databaseMaster.DatabaseHelper;
import com.assusoft.eFairEmall.entities.AdService;
import com.epch.efair.delhifair.EFairEmallApplicationContext;
import com.epch.efair.delhifair.HomeAcitityFirst;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

@SuppressLint("NewApi")
@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class AdsLoader  {

	private AnimationDrawable drawable;
	public Drawable checkFrame, currentFrame;
	private DatabaseHelper dbHelper;
	private ArrayList<AdService> adURL;
	private Target saveFileTarget;
	private Context mContext;
	private String webURL;
	public static int adIndex;
	
//	String []a = new String[]{"http://i.imgur.com/fhZmmDj.png","http://foodpages.ca/coupon/wbadge?s=184104","http://humber.ca/brand/sites/default/files/hum_leaderboard_728x90_pink_static.gif"};	
	
	@SuppressLint("NewApi")
	public AdsLoader(Context mContext){
		dbHelper=EFairEmallApplicationContext.getDatabaseHelper();
		dbHelper.openDatabase(DatabaseHelper.READMODE);
		this.mContext=mContext;
		adURL	=	new ArrayList<AdService>();
			
	}
	
	public AnimationDrawable getAnimation(){
		adURL	=	dbHelper.getAdsURL();
		drawable = new AnimationDrawable();
		try {
//			for(String a1:adURL){
			for(int i=0;i<adURL.size();i++){
				String URL = adURL.get(i).getUrl();
				Picasso.with(mContext)
			    .load(URL)
			    .into(new Target() {
				    @Override
				    public void onBitmapLoaded (final Bitmap bitmap, Picasso.LoadedFrom from){
				    	drawable.addFrame(new BitmapDrawable(bitmap), 1500);
				    }

					@Override
					public void onBitmapFailed(Drawable arg0) {						
					}

					@Override
					public void onPrepareLoad(Drawable arg0) {
					}
					});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return drawable;		
	}
	
	public void showBanner(FrameLayout banner){
		int sdk = android.os.Build.VERSION.SDK_INT;
		if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {	//Or: if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN){
			banner.setBackgroundDrawable(HomeAcitityFirst.animatin);
		} else {
			banner.setBackground(HomeAcitityFirst.animatin);
		}
		banner.setBackgroundDrawable(HomeAcitityFirst.animatin);
		HomeAcitityFirst.animatin.setOneShot(false);
		HomeAcitityFirst.animatin.start();
		banner.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stubcurrentFrame = anim.getCurrent();
				HomeAcitityFirst.adLoader.currentFrame=HomeAcitityFirst.animatin.getCurrent();
				for (int i = 0; i < HomeAcitityFirst.animatin.getNumberOfFrames(); i++) {
					HomeAcitityFirst.adLoader.checkFrame = HomeAcitityFirst.animatin.getFrame(i);
//					Log.i("NUM","drawable.getNumberOfFrames()maincurrent: "+HomeAcitityFirst.adLoader.currentFrame+"\ncheck frame : "+HomeAcitityFirst.adLoader.checkFrame);
				    if (HomeAcitityFirst.adLoader.checkFrame == HomeAcitityFirst.adLoader.currentFrame) {
//				    	Log.i("NUM","drawable.getNumberOfFrames()maincurrent:"+HomeAcitityFirst.adLoader.currentFrame+i);
//				    	HomeAcitityFirst.adLoader.openBannerURL(i);
				    	Log.i("NUM","position in adsLoader"+i);
						webURL = adURL.get(i).getHashCode();//"http://www.google.com";
						Log.i("NUM","Website URL"+webURL);
						if (!webURL.startsWith("http://") && !webURL.startsWith("https://"))
							   webURL = "http://" + webURL;
						try{
//							final Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(url));
//							mContext.startActivity(intent);
				        	Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(webURL));
				        	mContext.startActivity(intent);
				    	}catch(Exception e){
				    		e.printStackTrace();
				    	}
				        break;
				    }
				
			}
				
			}
		});
	}
	
	public void openBannerURL(int position){
		/*Drawable currntFrame = null;
		for (int i = 0; i < HomeAcitityFirst.animatin.getNumberOfFrames(); i++) {
			HomeAcitityFirst.adLoader.checkFrame = HomeAcitityFirst.animatin.getFrame(i);
		    if (HomeAcitityFirst.adLoader.checkFrame == currntFrame) {
		    	Log.i("NUM","drawable.getNumberOfFrames()maincurrent:"+HomeAcitityFirst.adLoader.currentFrame+i);
		    	HomeAcitityFirst.adLoader.openBannerURL(Integer.parseInt(""+currntFrame));
		        break;
		    }
	}*/
		Log.i("NUM","position in adsLoader"+position);
		webURL = adURL.get(position).getHashCode();//"http://www.google.com";
		Log.i("NUM","Website URL"+webURL);
		if (!webURL.startsWith("http://") && !webURL.startsWith("https://"))
			   webURL = "http://" + webURL;
		try{
//			final Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(url));
//			mContext.startActivity(intent);
        	Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(webURL));
        	mContext.startActivity(intent);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    /*	Log.i("EXPO","Frame position "+position);
    	String url ="";
    	switch(position){
    	case 0:
    		url="http://www.packplus.in/";
    		break;
    	case 1:
    		url="http://www.packplussouth.in/";
    		break;
    	case 2:
    		url="http://www.packplus.in/";
    		break;
    	}
    	try{
        	Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        	startActivity(intent);
    	}catch(Exception e){
    		e.printStackTrace();
    	}*/
    }
	
}

/**
 * Clicked event handle of animation banner
 * */	
	/*banner.setOnClickListener(new OnClickListener() {
		
		@SuppressLint("NewApi")
		@Override
		public void onClick(View v) {
			currentFrame = amin.getCurrent();
			for (int i = 0; i < amin.getNumberOfFrames(); i++) {
			    checkFrame = amin.getFrame(i);
			    if (checkFrame == currentFrame) {
			        openBannerURL(i);
			        break;
			    }
			}
		}
	});
*/
/*public void openBannerURL(int position){
    	Log.i("EXPO","Frame position "+position);
    	String url ="";
    	switch(position){
    	case 0:
    		url="http://www.packplus.in/";
    		break;
    	case 1:
    		url="http://www.packplussouth.in/";
    		break;
    	case 2:
    		url="http://www.packplus.in/";
    		break;
    	}
    	try{
        	Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        	startActivity(intent);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
*/