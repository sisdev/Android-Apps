package com.assusoft.efair.epchfair.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.epch.efair.delhifair.R;

public class AboutPackPlusFragment extends Fragment {
	WebView webView;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.about_pack_plus, container, false);
		 webView=(WebView) view.findViewById(R.id.webViewAboutPackplus);
		 webView.loadUrl("file:///android_asset/AboutUs.html");
		/*AdView adView = new AdView(getActivity(), AdSize.SMART_BANNER, Commons.admobToken);    
			FrameLayout layout = (FrameLayout)view.findViewById(R.id.AdsFrameLayout);    
			layout.addView(adView); 
			AdRequest request = Commons.GetAds(getActivity());
			adView.loadAd(request);*/
		return view;
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
public void onResume() {
	// TODO Auto-generated method stub
	super.onResume();
//	Commons.getMyTracker(getActivity(), "AboutPackPlusFragmen");
}
}
