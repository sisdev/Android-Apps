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

public class PackPlusEventReportFragment extends Fragment{
	WebView webview;
	TextView tvLoading;
	static String pdfLink="http://www.packplus.in/Conclave%202013%20Post%20Event%20Report.pdf";
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	
		View v = inflater.inflate(R.layout.packplus_report_fragment, container, false);
		
		// Getting reference to the TextView of the Fragment
		webview=(WebView) v.findViewById(R.id.webviewVideo);
		tvLoading=(TextView) v.findViewById(R.id.tvLoading);
		
		/*//admob
    	AdView adView = new AdView(getActivity(), AdSize.SMART_BANNER, Commons.admobToken);    
		FrameLayout layout = (FrameLayout)v.findViewById(R.id.AdsFrameLayout);    
		layout.addView(adView); 
		AdRequest request = Commons.GetAds(getActivity());
		adView.loadAd(request);*/
		
		webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl(pdfLink);
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
//		Commons.getMyTracker(getActivity(), "PackPlusEventReportFragment");
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
