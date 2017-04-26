package com.assusoft.efair.epchfair.Fragments;

import java.io.File;

import com.epch.efair.delhifair.HomeActivity;
import com.epch.efair.delhifair.ImageAsyncTask;
import com.epch.efair.delhifair.R;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public class InfoFragment extends Fragment{

	private View	view;
	private WebView	webView;
	private String base;
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view		= inflater.inflate(R.layout.web_open, container, false);
		webView		= (WebView) view.findViewById(R.id.mybrowser);
		
		base = "file://"+Environment.getExternalStorageDirectory().getAbsolutePath()
				   +File.separator+ImageAsyncTask.FOLDER_NAME
				   +File.separator;
		
		webView.getSettings().setJavaScriptEnabled(true);
		webView.loadUrl(base+"App_Feature_Intro.html");
		
		return view;
	}
	@Override
	public void onResume() {
		super.onResume();
		HomeActivity.setTitle("App Help Guide");
	}

}
