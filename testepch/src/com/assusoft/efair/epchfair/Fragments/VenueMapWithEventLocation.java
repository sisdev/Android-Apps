package com.assusoft.efair.epchfair.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.epch.efair.delhifair.R;

public class VenueMapWithEventLocation extends Fragment{

	WebView wv;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v=inflater.inflate(R.layout.venue_map_layout,container,false);

		wv=(WebView) v.findViewById(R.id.webView_venue_map_layout);
		/*
		//admob
     	AdView adView = new AdView(getActivity(), AdSize.SMART_BANNER, Commons.admobToken);    
 		FrameLayout layout = (FrameLayout)v.findViewById(R.id.AdsFrameLayout);    
 		layout.addView(adView); 
 		AdRequest request = Commons.GetAds(getActivity());
 		adView.loadAd(request);*/
		
		final MyJavaScriptInterface myJavascriptInterface=new MyJavaScriptInterface(getActivity());
		//add the javascript interface
		wv.addJavascriptInterface(myJavascriptInterface,"AndroidFunction");
		WebSettings ws=wv.getSettings();//get the object of web settings
		ws.setJavaScriptEnabled(true);//enable javascript
		ws.setBuiltInZoomControls(true);//set the buildin zoom control for zoomin and zoomout
				
		wv.loadUrl("file:///android_asset/VenueMap.html");
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		final int cPosX = displaymetrics.widthPixels/2;
		final int cPosY = displaymetrics.heightPixels/2;
		
		Bundle bundle=getArguments();
		String xloc=bundle.getString("XLOCATION"),yloc=bundle.getString("YLOCATION");
		int x1,y1;
		if(xloc.contains(".")){
			
			 x1=(int) Float.parseFloat(xloc);
			 Log.i("TIMEV","xloc "+x1);
			
		}else{
			x1=Integer.parseInt(xloc);
			Log.i("TIMEV","xloc "+x1);
		}
		if(yloc.contains(".")){
			y1=(int) Float.parseFloat(yloc);
			Log.i("TIMEV","yloc "+y1);
		}else{
			y1=Integer.parseInt(yloc);
			Log.i("TIMEV","yloc "+y1);
		}
		final int  x=x1,y=y1;
		
		final String fileName=bundle.getString("FILEPATH");
		
        new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				wv.loadUrl("javascript:setMarkerLocation(\""+x+"\",\""+y+"\",\""+cPosX+"\",\""+cPosY+"\")"); 
				//wv.loadUrl("javascript:setMarkerLocation(\""+x+"\",\""+y+"\")"); 
				wv.loadUrl("javascript:setImageSrc(\""+fileName+"\")"); 
			}
		}, 3000);
		return v;
	}
	
	
	//is used as interface between android and javascript
		 public class MyJavaScriptInterface {
			   Context mContext;

			      MyJavaScriptInterface(Context c) {
			          mContext = c;
			      }
			      @JavascriptInterface
			      public void showToast(String msg,String msg1){
			         Toast.makeText(mContext,"called from javascript "+msg+"  "+msg1,Toast.LENGTH_LONG).show();
			      // wv.loadUrl("javascript:callFromActivity("+" message"+ ");");
			      
			          
			      }
		 }  
		 @Override
		public void onResume() {
			// TODO Auto-generated method stub
			super.onResume();
//			Commons.getMyTracker(getActivity(), "VenueMapWithEventLocation");
		}
}
