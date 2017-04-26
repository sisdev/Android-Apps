package com.assusoft.efair.epchfair.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.epch.efair.delhifair.HomeAcitityFirst;
import com.epch.efair.delhifair.R;

public class HelpDesk extends Fragment{
	
	private ProgressBar proBar;
	private WebView webview;
	
	@SuppressLint("SetJavaScriptEnabled") 
	@Override
	public View onCreateView(LayoutInflater inflater,
			  ViewGroup container,   Bundle savedInstanceState) {
		View v=inflater.inflate(R.layout.help_desk,container,false);
		proBar = (ProgressBar) v.findViewById(R.id.progressBar1);
		webview=(WebView) v.findViewById(R.id.webVieweventInfo);
		
		FrameLayout banner=(FrameLayout) v.findViewById(R.id.AdsFrameLayout);
		HomeAcitityFirst.adLoader.showBanner(banner);
//	 	   AnimationDrawable amin=(AnimationDrawable) banner.getBackground();
//	 	   amin.start();
		Bundle bundle=getArguments();
		
		webview.getSettings().setBuiltInZoomControls(false);
		webview.getSettings().setJavaScriptEnabled(true);
		
	//adding progress dialog
		webview.setWebViewClient(new WebViewClient(){
			
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				proBar.setVisibility(View.VISIBLE);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				proBar.setVisibility(View.GONE);
			}

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				 if (url.indexOf("tel:") > -1) {
			            startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(url)));
			            return true;
			        } else if(url.indexOf("mailto:")>-1) {
			        	Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND); 
			        	emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{ Uri.parse(url).toString()});
			        	emailIntent.setType("plain/text");  
			        	startActivity(emailIntent); 
			            return true;
			        }
				 webview.loadUrl(url);
				 return true;
			}
			
			
		});
	 	webview.loadUrl(bundle.getString("URL"));
		return v;
	}

	@Override
	public void onResume() {
		super.onResume();
	}
	
	public static void getWebView(){
		
	}

}
