package com.assusoft.efair.epchfair.Fragments;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.epch.efair.delhifair.R;
import com.epch.efair.delhifair.VideoEnabledWebChromeClient;
import com.epch.efair.delhifair.VideoEnabledWebView;

public class VideosFragment extends Fragment{
	Button btnImage,btnVideos;
	LinearLayout llNonVideoView;
	FrameLayout videoView;
	private  VideoEnabledWebView webview;
	VideoEnabledWebChromeClient chromclient;
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	
		View v = inflater.inflate(R.layout.video_fragment1, container, false);
		/*
		//admob
     	AdView adView = new AdView(getActivity(), AdSize.SMART_BANNER, Commons.admobToken);    
 		FrameLayout layout = (FrameLayout)v.findViewById(R.id.AdsFrameLayout);    
 		layout.addView(adView); 
 		AdRequest request = Commons.GetAds(getActivity());
 		adView.loadAd(request);*/
		
		// Getting reference to the TextView of the Fragment
		
		webview=(VideoEnabledWebView) v.findViewById(R.id.webviewImage);
		
		llNonVideoView=(LinearLayout) v.findViewById(R.id.llNonVideoView);
		videoView=(FrameLayout) v.findViewById(R.id.frameVideoView);
		View loadingView = getActivity().getLayoutInflater().inflate(R.layout.view_loading_video, null); // Your own view, read class comments
		
		 chromclient=new VideoEnabledWebChromeClient(llNonVideoView,videoView,loadingView,webview)
		 {
		        // Subscribe to standard events, such as onProgressChanged()...
		        @Override
		        public void onProgressChanged(WebView view, int progress)
		        {
		            // Your code...
		        }
		    };
		    
		    chromclient.setOnToggledFullscreen(new VideoEnabledWebChromeClient.ToggledFullscreenCallback()
		    {
		       
				@SuppressLint("NewApi")
				@Override
		        public void toggledFullscreen(boolean fullscreen)
		        {
		            // Your code to handle the full-screen change, for example showing and hiding the title bar. Example:
		            if (fullscreen)
		            {
		                WindowManager.LayoutParams attrs = getActivity().getWindow().getAttributes();
		                attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
		                attrs.flags |= WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
		                getActivity().getWindow().setAttributes(attrs);
		                if (android.os.Build.VERSION.SDK_INT >= 14)
		                {
		                	getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
		                }
		            }
		            else
		            {
		                WindowManager.LayoutParams attrs = getActivity().getWindow().getAttributes();
		                attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
		                attrs.flags &= ~WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
		                getActivity().getWindow().setAttributes(attrs);
		                if (android.os.Build.VERSION.SDK_INT >= 14)
		                {
		                	getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
		                }
		            }

		        }
		    });
		   
		    webview.getSettings().setJavaScriptEnabled(true);
            if (Build.VERSION.SDK_INT < 8) {
            	 //webview.getSettings().setPluginsEnabled(true);
            } else {
            	 webview.getSettings().setPluginState(PluginState.ON);
            }
		  
		    webview.getSettings().setSupportMultipleWindows(true);
		  
		webview.setWebChromeClient(chromclient);
		
		
		
		webview.loadUrl("https://m.youtube.com/#/user/PackPlusShow/videos");
        //Toast.makeText(getActivity(), "Page is loading...",Toast.LENGTH_SHORT).show();
		
		return v;
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		ConnectivityManager cm =(ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
         
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                              activeNetwork.isConnectedOrConnecting();
        if(!isConnected)
        {
        	Toast.makeText(getActivity(), "Internet is not connected", Toast.LENGTH_LONG).show();
        }
	}
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
//		Commons.getMyTracker(getActivity(), "VideosFragment");
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
	}
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.i("ELECT","new fragment onpause  ");
		
		try{
			Class.forName("android.webkit.WebView").getMethod("onPause",(Class[] ) null)
			.invoke(webview, (Object[])null);
			Log.i("ELECT","new fragment onpause after reflection  ");
		}catch(Exception e){
			e.printStackTrace();
			Log.i("ELECT","new fragment onpause after error ");
		}
	}
	
	public void backPressed()
	{
		try{
			//webview.loadUrl("file:///android_asset/nonexistent.html");
			
			chromclient.onCompletion(null);
			VideoEnabledWebChromeClient.videoViewContainer=null;
			Class.forName("android.webkit.WebView").getMethod("onPause",(Class[] ) null)
			.invoke(webview, (Object[])null);
			Log.i("ELECT","new fragment onpause after reflection  ");
		}catch(Exception e){
			e.printStackTrace();
			Log.i("ELECT","new fragment onpause after error ");
		}
		
		
	}
	
}