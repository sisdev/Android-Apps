package com.assusoft.efair.epchfair.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.epch.efair.delhifair.R;

public class NewsFragment extends Fragment{
	WebView webViewNews;
	ProgressDialog progressDialog;
	static final String url="http://print-packagingblog.com/";
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.news_fragment, container, false);
		webViewNews=(WebView) view.findViewById(R.id.webViewNews);
		
		/*//admob
    	AdView adView = new AdView(getActivity(), AdSize.SMART_BANNER, Commons.admobToken);    
		FrameLayout layout = (FrameLayout)view.findViewById(R.id.AdsFrameLayout);    
		layout.addView(adView); 
		AdRequest request = Commons.GetAds(getActivity());
		adView.loadAd(request);
		*/
		webViewNews.loadUrl("file:///android_asset/packPlus_news.html");
		/*progressDialog = new ProgressDialog(getActivity());
    	progressDialog.setMessage("Loading...");
    	progressDialog.setIndeterminate(true);
    	progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    	progressDialog.setCancelable(false);
    	progressDialog.show();*/
		webViewNews.setWebViewClient(new WebViewClient() {

			   public void onPageFinished(WebView view, String url) {
				   //progressDialog.dismiss();
			    }
			});
		return view;
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
//		Commons.getMyTracker(getActivity(), "NewsFragment");
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
