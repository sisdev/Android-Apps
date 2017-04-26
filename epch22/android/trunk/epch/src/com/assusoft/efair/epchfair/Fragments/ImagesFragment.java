package com.assusoft.efair.epchfair.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.epch.efair.delhifair.R;

public class ImagesFragment extends Fragment{
	WebView webview;
	TextView tvLoading;
	static String imagesLink="https://plus.google.com/app/basic/photos/108310466816228041608/album/5938198293208903297?banner=pwa&sort=1&source=apppromo";
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	
		View v = inflater.inflate(R.layout.images_fragment, container, false);
		
		// Getting reference to the TextView of the Fragment
		webview=(WebView) v.findViewById(R.id.webviewVideo);
		tvLoading=(TextView) v.findViewById(R.id.tvLoading);
		
		/*//admob
    	AdView adView = new AdView(getActivity(), AdSize.SMART_BANNER, Commons.admobToken);    
		FrameLayout layout = (FrameLayout)v.findViewById(R.id.AdsFrameLayout);    
		layout.addView(adView); 
		AdRequest request = Commons.GetAds(getActivity());
		adView.loadAd(request);
		*/
		webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl(imagesLink);
        webview.setWebViewClient(new WebViewClient() {

			   public void onPageFinished(WebView view, String url) {
				   tvLoading.setVisibility(View.GONE);
			    }
			});
        
		
		return v;
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
//		Commons.getMyTracker(getActivity(), "ImagesFragment");
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
}
