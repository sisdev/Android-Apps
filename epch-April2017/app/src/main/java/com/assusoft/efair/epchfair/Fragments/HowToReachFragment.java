package com.assusoft.efair.epchfair.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.assusoft.efair.epchfair.googleMap.HowToReachGMap;
import com.epch.efair.delhifair.HomeActivity;
import com.epch.efair.delhifair.R;

public class HowToReachFragment extends Fragment{

	Button btnMap;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v=inflater.inflate(R.layout.how_to_reach_home,container,false);
		
		/*//admob
		AdView adView = new AdView(getActivity(), AdSize.SMART_BANNER, Commons.admobToken);    
		FrameLayout layout = (FrameLayout)v.findViewById(R.id.AdsFrameLayout);    
		layout.addView(adView); 
		AdRequest request = Commons.GetAds(getActivity());
		adView.loadAd(request);*/
		
		btnMap=(Button) v.findViewById(R.id.routesOnMap);
		
		btnMap.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FragmentTransaction ft = ((FragmentActivity)getActivity()).getSupportFragmentManager().beginTransaction();
		       
		        HowToReachGMap fragment = new HowToReachGMap();	        
		      
		        ft.replace(R.id.content_frame, fragment,"HowToReachGMap");
		        ft.addToBackStack(null);
		       
		        ft.commit();
			}
		});
		return v;
	}
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
//		Commons.getMyTracker(getActivity(), "HowToReachFragment");
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		HomeActivity.setTitle("How To Reach");
	}
}
