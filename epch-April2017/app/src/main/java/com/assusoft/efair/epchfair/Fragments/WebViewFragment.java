package com.assusoft.efair.epchfair.Fragments;

import java.io.File;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.epch.efair.delhifair.HomeAcitityFirst;
import com.epch.efair.delhifair.HomeActivity;
import com.epch.efair.delhifair.ImageAsyncTask;
import com.epch.efair.delhifair.R;

public class WebViewFragment extends Fragment{

	private View view;
	private WebView webView;
	private String base;
	private RelativeLayout rootLayout;
	private String linkUrl,title;
	
	@SuppressLint({ "SetJavaScriptEnabled", "NewApi" }) 
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.help_desk, container, false);
		rootLayout = (RelativeLayout) view.findViewById(R.id.rootLayout);
		
		webView = (WebView) view.findViewById(R.id.webVieweventInfo);
		
		FrameLayout banner  = (FrameLayout) view.findViewById(R.id.AdsFrameLayout);
		HomeAcitityFirst.adLoader.showBanner(banner);
        /*AnimationDrawable amin=(AnimationDrawable) banner.getBackground();
	 	   amin.start();*/
		
		linkUrl = getArguments().getString("URL");
		title = getArguments().getString("TITLE");
		
		if(linkUrl.isEmpty()||linkUrl==null){
			Toast.makeText(getActivity(), "More information will be availabe soon.", Toast.LENGTH_SHORT).show();
			getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
			getActivity().finish();
			return view;
		}
		
		if(HomeActivity.isDelhifair){
			rootLayout.setBackground(getResources().getDrawable(R.drawable.bg_delhifair));
		}
		
		webView.getSettings().setJavaScriptEnabled(true);
		
		base = "file://"+Environment.getExternalStorageDirectory().getAbsolutePath()
				   +File.separator+ImageAsyncTask.FOLDER_NAME
				   +File.separator;
		
		webView.setWebViewClient(new WebViewClient(){

			ProgressDialog dialog = new ProgressDialog(getActivity());
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				dialog.setMessage("Loading, please wait...");
				dialog.show();
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				dialog.dismiss();
			}

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				if (url.indexOf("tel:") > -1) {
		            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(url)));
		            return true;
		        } else if(url.indexOf("mailto:")>-1) {
		        	try{
						Intent intent=new Intent();
						intent.setAction(Intent.ACTION_SENDTO);
					    intent.setData(Uri.parse("mailto:"+Uri.parse(url)));
						startActivity(intent);
						}catch(Exception e){
							e.printStackTrace();
						}
		            return true;
		        }
				webView.loadUrl(url);
				return true;
			}
			
		});
		webView.loadUrl(base+linkUrl);
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		HomeActivity.setTitle(title);
	}

}
