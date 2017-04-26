package com.assusoft.efair.epchfair.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.assusoft.eFairEmall.soapWebService.WebService;
import com.assusoft.eFairEmall.util.MySharedPreferences;
import com.epch.efair.delhifair.HomeAcitityFirst;
import com.epch.efair.delhifair.HomeActivity;
import com.epch.efair.delhifair.HomeActivityDelhiFair;
import com.epch.efair.delhifair.MainActivity;
import com.epch.efair.delhifair.R;

public class SplashFragmentSecond extends Fragment{
	private Handler handler;
	private Runnable runnable;
	
	LinearLayout rootLayout;
	@SuppressLint("NewApi") 
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.splashsecond, container, false);
		
		rootLayout = (LinearLayout) v.findViewById(R.id.rootLayout);
		
		if(HomeActivity.isDelhifair){
			rootLayout.setBackground(getResources().getDrawable(R.drawable.splash_delhifair));
		}
		
		
		handler=new Handler();
		runnable=new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub

				if(HomeActivity.isDelhifair){
					Intent intent = new Intent(getActivity(), HomeActivityDelhiFair.class);
					startActivity(intent);
					getActivity().finish();
				}else{

					MySharedPreferences pref=new MySharedPreferences(getActivity());
					if(pref.isUserRegistered(WebService.REGISTERED_STATUS)){
				    	   Intent homepage=new Intent(getActivity(),HomeAcitityFirst.class);
					       MainActivity.activity.startActivity(homepage);
					       MainActivity.activity.finish();
					   }else{
							/*InternetConnectionDetector internetConnection=new InternetConnectionDetector(getActivity());
							if(!internetConnection.isConnectingToInternet()){
								internetConnection.showInternetSettingsAlert();
								//return;
							}*/
					        UserRegistrationFragment fragment= new UserRegistrationFragment();
					        FragmentTransaction ft =MainActivity.fm.beginTransaction();
					        ft.replace(R.id.content_frame_registration, fragment,"Home");
					        //ft.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up, R.anim.slide_in_down, R.anim.slide_out_down);
					        ft.commit();
					   }
				}
			
			}
			
		};
		handler.postDelayed(runnable, 1500);
		
		/*new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {}
		}, 1500);*/
		
		return v;
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
//		Commons.getMyTracker(getActivity(), "SplashFragmentSecond");
	}
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		
		
		super.onPause();
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		try{
			if(handler!=null)
				handler.removeCallbacks(runnable);
		}catch(Exception e){e.printStackTrace();}
	}
	
	

	
}
