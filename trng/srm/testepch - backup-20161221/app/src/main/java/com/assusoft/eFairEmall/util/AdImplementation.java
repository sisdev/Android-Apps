package com.assusoft.eFairEmall.util;

import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.util.Log;
import android.widget.FrameLayout;

import com.adsdk.sdk.Ad;
import com.adsdk.sdk.AdListener;
import com.adsdk.sdk.AdManager;
import com.adsdk.sdk.banner.AdView;
import com.assusoft.eFairEmall.databaseMaster.DatabaseHelper;
import com.assusoft.eFairEmall.entities.AdService;
import com.epch.efair.delhifair.EFairEmallApplicationContext;

public class AdImplementation implements AdListener{

	private AdView mAdView;
	private AdManager mManager;
	
	private Context context;
	private FrameLayout banner;
	private String placementId;
	
	private ArrayList<AdService> adServices;
	private DatabaseHelper dbHelper;
	
	private String hashCode="";
	private String url="";
	
	
	public AdImplementation() {
		super();
	}

	public AdImplementation(Context context, FrameLayout banner,
			String placementId) {
		super();
		this.context		= context;
		this.banner			= banner;
		this.placementId	= placementId;
		
		adServices	= new ArrayList<AdService>();
		dbHelper	= EFairEmallApplicationContext.getDatabaseHelper();
		dbHelper.openDatabase(DatabaseHelper.READMODE);
		
		try{
			adServices	= dbHelper.getAdURLplacementIdWise(placementId);
			if(adServices.isEmpty()){
				adServices = dbHelper.getAdURLplacementIdWise(Util.DEFAULT);
			}
			if(!adServices.isEmpty()){
				Random random=new Random();
				int i=random.nextInt(adServices.size());
				hashCode	= adServices.get(i).getHashCode();
				url			= adServices.get(i).getUrl();

				Log.i("EXPO","HashCode "+hashCode);
				Log.i("EXPO","AD URL "+url);
				
			}
			mManager = new AdManager(context, url, hashCode, true);
			mManager.setListener(this);
		}catch(Exception e){
			
		}
	}
	
	public void onShowBanner() {		
			removeBanner();
		
		if(!adServices.isEmpty()){
			mAdView = new AdView(context, url, hashCode, true, true);
			mAdView.setAdListener(this);
			banner.addView(mAdView);
		}
		
	}
	private void removeBanner(){
		try{
			if(mAdView!=null){
				banner.removeView(mAdView);
				mAdView = null;
			}else{
				banner.removeAllViews();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void adClicked() {
		
	}

	@Override
	public void adClosed(Ad arg0, boolean arg1) {
		
	}

	@Override
	public void adLoadSucceeded(Ad arg0) {
		if (mManager != null && mManager.isAdLoaded())
			mManager.showAd();
	}

	@Override
	public void adShown(Ad arg0, boolean arg1) {
		
	}

	@Override
	public void noAdFound() {
		
	}

	@Override
	public void openInnerPage(String arg0) {
		
	}
	public void releaseAdBanner(){
		try{
			mManager.release();
			if(mAdView!=null)
				mAdView.release();
			
		}catch(NullPointerException e){
			e.printStackTrace();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
