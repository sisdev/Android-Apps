package com.assusoft.efair.epchfair.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.assusoft.eFairEmall.databaseMaster.DatabaseHelper;
import com.assusoft.eFairEmall.util.MySharedPreferences;
import com.assusoft.eFairEmall.util.Util;
import com.epch.efair.delhifair.HomeActivity;
import com.epch.efair.delhifair.HomeActivityDelhiFair;
import com.epch.efair.delhifair.MainActivity;
import com.epch.efair.delhifair.R;

public class FirstViewFragment extends Fragment{

	private Button btnSpring, btnHomeExpo, btnFashionJewellery, btnAutomn;
	private MySharedPreferences pref;
	private View view;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		view	= inflater.inflate(R.layout.first_activity, container, false);
	
		btnSpring			= (Button) view.findViewById(R.id.btnSpring);
		btnHomeExpo			= (Button) view.findViewById(R.id.btnHomeExpo);
		btnFashionJewellery	= (Button) view.findViewById(R.id.btnFashionJewellery);
		btnAutomn			= (Button) view.findViewById(R.id.btnAutomn);
		
		pref	= new MySharedPreferences(getActivity());
		
		btnSpring.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				gotoNextActivity();
			}
		});
		btnHomeExpo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				gotoNextActivity();
			}
		});
		btnFashionJewellery.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//HomeActivity.isDelhifair=false;
				gotoNextActivity();
			}
		});
		btnAutomn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				HomeActivity.isDelhifair=false;
				gotoNextActivity();
				//Util.copyDatabase(getActivity(), DatabaseHelper.DBNAME);
				//Util.writeKeyHash(getActivity());
			}
		});
		
		return  view;
	}	
	
	@Override
	public void onResume() {
		super.onResume();
		btnHomeExpo.setEnabled(false);
		btnFashionJewellery.setEnabled(false);
		//btnAutomn.setEnabled(true);
		btnSpring.setEnabled(false);
	}

	void gotoNextActivity(){
		//SplashFragment splashFragmentSecond =new SplashFragment();
		SplashFragmentSecond splashFragmentSecond=new SplashFragmentSecond();
  		FragmentTransaction fragT=MainActivity.fm.beginTransaction();
  		fragT.replace(R.id.content_frame_registration, splashFragmentSecond);
  		fragT.commit();
  	}
}
