package com.assusoft.efair.epchfair.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.epch.efair.delhifair.Commons;
import com.epch.efair.delhifair.ImageUtils;
import com.epch.efair.delhifair.R;
import com.epch.efair.delhifair.StorageHelper;

public class FloorPlanWebViewFragment extends Fragment{
	 WebView webview; 
	    String imageFileName;
	    String mapName;
		@Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	    		Bundle savedInstanceState) {
	    	View v=inflater.inflate(R.layout.floor_plan_webview_fragment,container,false);
	    	webview=(WebView) v.findViewById(R.id.webViewFloorPlan);
	    	
	    	/*//admob
	    	AdView adView = new AdView(getActivity(), AdSize.SMART_BANNER, Commons.admobToken);    
			FrameLayout layout = (FrameLayout)v.findViewById(R.id.AdsFrameLayout);    
			layout.addView(adView); 
			AdRequest request = Commons.GetAds(getActivity());
			adView.loadAd(request);*/
			
	    	WebSettings webviewSettings=webview.getSettings();
			//enable the zoom control
			webviewSettings.setBuiltInZoomControls(true);
			
			Bundle bundle=getArguments();
			imageFileName=bundle.getString("FLOOR_PLAN_MAP");
			mapName=bundle.getString("MAP_NAME");
			Log.i("EXPO", "imageFileName"+imageFileName);
			webview.loadDataWithBaseURL("", 
					"<img src='"+imageFileName+"' />", "text/html", "utf-8", null);
			
	    	return v;
	    }
		@Override
		public void onResume() {
			// TODO Auto-generated method stub
			super.onResume();
//			Commons.getMyTracker(getActivity(), "FloorPlanWebViewFragment");
			checkMap();
		}
		public void checkMap()
		{  ImageUtils imageUtil=new ImageUtils(getActivity());
		   boolean isImageDownloaded=false;
		     if(StorageHelper.isExternalStorageAvailableAndWriteable())
				{
		    	 isImageDownloaded=imageUtil.isFileAvailabeInExternalStorage(mapName);
				}else{
				 isImageDownloaded=imageUtil.isFileAvailabeInInternalStorage(mapName);
				}
		     Log.i("EXPO","mapName:"+mapName);
		if(!isImageDownloaded)
		{
			new Commons().MyAlertDialog(getActivity());
		} 
			 
		}
}
