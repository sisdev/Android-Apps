package com.assusoft.efair.epchfair.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.epch.efair.delhifair.R;

public class SplashFragment extends Fragment{
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.splashscreen, container, false);
		return v;
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
//		Commons.getMyTracker(getActivity(), "SplashFragment");
	}
}
