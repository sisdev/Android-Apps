package com.assusoft.efair.epchfair.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.epch.efair.delhifair.R;

public class PrintAndPackFragment extends Fragment{
	WebView webView;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.print_and_packing, container, false);
		webView=(WebView) view.findViewById(R.id.webViewPrintAndPackaging);
		 webView.loadUrl("file:///android_asset/PrintAndPackaging.html");
		return view;
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
//		Commons.getMyTracker(getActivity(), "PrintAndPackFragment");
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
