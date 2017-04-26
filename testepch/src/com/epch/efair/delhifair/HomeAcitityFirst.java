package com.epch.efair.delhifair;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.assusoft.eFairEmall.databaseMaster.DatabaseHelper;
import com.assusoft.eFairEmall.entities.FileSettings;
import com.assusoft.eFairEmall.util.AdsLoader;
import com.assusoft.eFairEmall.util.MySharedPreferences;
import com.assusoft.eFairEmall.util.Util;
import com.assusoft.efair.epchfair.Fragments.UserRegistrationFragment;
import com.assusoft.efair.qrcode.CaptureActivity1;
import com.google.android.gms.analytics.Tracker;




public class HomeAcitityFirst extends FragmentActivity{
	
	ImageButton imgBtn;
	View v;
	Tracker tracker;
	ViewPager homePager;
	private ImageView imgView;
	private ImageButton	btninfo;
	private FrameLayout banner;
	public static AdsLoader adLoader;
	public static AnimationDrawable animatin;
	public static int lowMemoryIndicator=0;
	
	private DatabaseHelper helper;
	
	
	@SuppressLint("ShowToast")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_pager);
		helper = EFairEmallApplicationContext.getDatabaseHelper();
		helper.openDatabase(DatabaseHelper.READMODE);
		homePager=(ViewPager)findViewById(R.id.pagerEvent);
		imgView=(ImageView)findViewById(R.id.imgSelectIndicator);
	// Getting reference to the TextView of the Fragment
	   imgBtn	= (ImageButton) findViewById(R.id.imgBtnExhibitors);
	   banner	=(FrameLayout) findViewById(R.id.AdsFrameLayout);
	   adLoader= new AdsLoader(this);
	   animatin	=	adLoader.getAnimation();
	   HomeAcitityFirst.adLoader.showBanner(banner);
	   btninfo	=(ImageButton) findViewById(R.id.homeInfo);
	   
//	   AnimationDrawable amin=(AnimationDrawable) banner.getBackground();
//	   amin.start();
	if(lowMemoryIndicator==1){
		Toast.makeText(this,"Memory is too low",Toast.LENGTH_LONG);
		lowMemoryIndicator=0;
	}
	   
	PagerAdapter eventPagerAdapter=new CircularPagerAdapter(this,homePager,new int[]{
			R.layout.home_fragment_first,R.layout.home_fragment_second });
	
		homePager.setAdapter(eventPagerAdapter);
		homePager.setCurrentItem(0);
		
		homePager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int id) {
				// TODO Auto-generated method stub
				Resources res = getResources();
	        	Drawable draw; // need this to fetch the drawable;
				Log.i("EXPO", "onPageScrollStateChanged="+id);
				if(id==0)
				{
					 draw = res.getDrawable( R.drawable.first );
		            	imgView.setImageDrawable(draw);
		            	//Log.i("EXPO", "fistScreen="+draw);
					
				}else if(id==1)
				{
					draw = res.getDrawable( R.drawable.second);
	            	imgView.setImageDrawable(draw);
	            	//Log.i("EXPO", "secondScreen="+draw);
					
				}
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int id) {
				// TODO Auto-generated method stub
				
			}
		});
		
		btninfo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
				Fragments a=Fragments.INFO;
				intent.putExtra("FLAG", a.toString());
				startActivity(intent);
				//Toast.makeText(getApplicationContext(), "info", Toast.LENGTH_SHORT).show();
			}
		});
	
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		banner.setBackgroundDrawable(animatin);
		   animatin.setOneShot(false);
		   animatin.start();
		HomeActivity.isDelhifair=false;
	}

	public void exhibitors(View v){
		if(helper.getAllExhibNameFromExhibTable().isEmpty()){
			String fileName = null, filePath = null;
			ArrayList<FileSettings> data=helper.getAllFileSettings(3);
			if(data.size()>0){
				fileName=data.get(0).getFileName();
				filePath=data.get(0).getFilePath();
			}
			Log.i("NUM","File Name :"+fileName+", File Path :"+filePath);
			Util.launchFile(fileName, filePath, this);
		}else{
			Intent intent=new Intent(this,HomeActivity.class);
			Fragments a=Fragments.EXHIBITORS;
			intent.putExtra("FLAG",a.toString());
			startActivity(intent);
		}
		
	}
	public void nearBy(View v){
		Intent intent=new Intent(this,HomeActivity.class);
		Fragments a=Fragments.NEARBY;
		intent.putExtra("FLAG",a.toString());
		startActivity(intent);
	}
	
	public void gotoMyQR(View v){
		
		//Toast.makeText(this,"This feature is not available",Toast.LENGTH_LONG).show();
		
		//actionBarTitle.setText("My QR");
		 
		   Intent intent=new Intent(this,CaptureActivity1.class);
			intent.putExtra("SCANFLAG", true);
			startActivity(intent);
	}
	
	
	public void gotoVenuMap(View v){
		Intent intent=new Intent(this,HomeActivity.class);
		Fragments a=Fragments.VENUEMAP;
		intent.putExtra("FLAG",a.toString());
		startActivity(intent);
	}
	public void map(View v){
		Intent intent=new Intent(this,HomeActivity.class);
		Fragments a=Fragments.MAP;
		intent.putExtra("FLAG",a.toString());
		startActivity(intent);
	}
	public void locate(View v){
		if(helper.getAllExhibNameFromExhibTable().isEmpty()){
			Toast.makeText(this,"This feature will available soon",Toast.LENGTH_LONG).show();
		}else{
			Intent intent=new Intent(this,HomeActivity.class);
			Fragments a=Fragments.LOCATE;
			intent.putExtra("FLAG",a.toString());
			startActivity(intent);
		}
		
	}
	public void favorites(View v){
		Intent intent=new Intent(this,HomeActivity.class);
		Fragments a=Fragments.FAVORITES;
		intent.putExtra("FLAG",a.toString());
		startActivity(intent);
	}
	public void planner(View v){
		Intent intent=new Intent(this,HomeActivity.class);
		Fragments a=Fragments.PLANNER;
		intent.putExtra("FLAG",a.toString());
		startActivity(intent);
	}
	public void socialMedia(View v){
		Intent intent=new Intent(this,HomeActivity.class);
		Fragments a=Fragments.SOCIALMEDIA;
		intent.putExtra("FLAG",a.toString());
		startActivity(intent);
	}
	public void productSelection(View v){
		Intent intent=new Intent(this,HomeActivity.class);
		Fragments a=Fragments.PRODUCTSELECTION;
		intent.putExtra("FLAG",a.toString());
		startActivity(intent);
		//Toast.makeText(this,"This feature is not available",Toast.LENGTH_LONG).show();
	}
	public void feedback(View v){
		MySharedPreferences pref=new MySharedPreferences(this);
		String status=pref.getSavedValue(UserRegistrationFragment.FEEDBACK_STATUS);
		if(status.equalsIgnoreCase("yes")){
			Toast.makeText(this,"You can give feedback only one time", Toast.LENGTH_LONG).show();
			return;
		}
		Intent intent=new Intent(this,HomeActivity.class);
		Fragments a=Fragments.FEEDBACK;
		intent.putExtra("FLAG",a.toString());
		startActivity(intent);
	}
	public void usefulInfo(View v){
		Intent intent=new Intent(this,HomeActivity.class);
		Fragments a=Fragments.USEFULINFO;		
		intent.putExtra("FLAG",a.toString());
		startActivity(intent);
	}
	/*public void info(View v){
		Intent intent = new Intent(this, HomeActivity.class);
		Fragments a=Fragments.INFO;
		intent.putExtra("FLAG", a.toString());
		startActivity(intent);
	}*/
	
	public static  enum Fragments {

	    EXHIBITORS,
	    VENUEMAP,
	    NEARBY,
	    QRCODE,
	    MAP,
	    LOCATE,
	    FAVORITES,
	    PLANNER,
	    PRODUCTSELECTION,
	    SOCIALMEDIA,
	    FEEDBACK,
	    USEFULINFO,
	    PHOTOSHOOT,
	    VENUEMAPQR,
	    EXHIBITORDETAILS,
	    INFO,
	    ABOUTIHGF,
	    CONTACTUS,
	    VISITOR_REGIS,
	    USEFUL_INFO_DELHIFAIR
	  };
	

}
