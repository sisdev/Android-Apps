package com.assusoft.efair.epchfair.Fragments;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import com.assusoft.eFairEmall.databaseMaster.DatabaseHelper;
import com.assusoft.eFairEmall.util.Util;
import com.epch.efair.delhifair.EFairEmallApplicationContext;
import com.epch.efair.delhifair.HomeAcitityFirst;
import com.epch.efair.delhifair.HomeActivity;
import com.epch.efair.delhifair.R;

public class UtilitiesFragment extends Fragment{
	
	private ImageButton btnFb,btnTwitter,btnYouTube,btnPinterest;
	private String linkUrl;
	private Bundle bundle;
	private DatabaseHelper helper;;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		 View view = inflater.inflate(R.layout.utilities, container, false);
		 
		 btnFb			=(ImageButton) view.findViewById(R.id.btnUtilitiesFb);
		 btnTwitter		=(ImageButton) view.findViewById(R.id.btnUtilitiesTwitter);		 
		 btnYouTube		=(ImageButton) view.findViewById(R.id.btnUtilitiesyouTube);
		 btnPinterest	=(ImageButton) view.findViewById(R.id.btnUtilitiesPinterest);
		 
		 FrameLayout banner=(FrameLayout) view.findViewById(R.id.AdsFrameLayout);
		 HomeAcitityFirst.adLoader.showBanner(banner);
		 
		 helper = EFairEmallApplicationContext.getDatabaseHelper();
		 helper.openDatabase(DatabaseHelper.READMODE);
		 
	 	   /*AnimationDrawable amin=(AnimationDrawable) banner.getBackground();
	 	   amin.start();*/
		
//		 Util.copyDatabase(getActivity(), DatabaseHelper.DBNAME);
		 
	 	 bundle = new Bundle();
	 	 final HelpDesk fragment = new HelpDesk();
	 	   
		 btnFb.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 Log.i("NUM","DB Copied Successfully");
				linkUrl = helper.getLinkURLByLinkName("Facebook");
				if(linkUrl.isEmpty()){
					Toast.makeText(getActivity(), "More information will be available soon.", Toast.LENGTH_SHORT).show();
					return;
				}
				bundle.clear();
				bundle.putString("URL", linkUrl);
				fragment.setArguments(bundle);
				getActivity().getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, fragment)
				.addToBackStack(null)
				.commit();	
				HomeActivity.setTitle("Facebook");
			}
		});
		 
		 btnTwitter.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					linkUrl = helper.getLinkURLByLinkName("Twitter");
					if(linkUrl.isEmpty()){
						Toast.makeText(getActivity(), "More information will be available soon.", Toast.LENGTH_SHORT).show();
						return;
					}
					bundle.clear();
					bundle.putString("URL", linkUrl);
					fragment.setArguments(bundle);
					getActivity().getSupportFragmentManager().beginTransaction()
					.replace(R.id.content_frame, fragment)
					.addToBackStack(null)
					.commit();
					HomeActivity.setTitle("Twitter");
				}
			});
		 
		 btnYouTube.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				linkUrl = helper.getLinkURLByLinkName("YouTube");
				if(linkUrl.isEmpty()){
					Toast.makeText(getActivity(), "More information will be available soon.", Toast.LENGTH_SHORT).show();
					return;
				}
				bundle.clear();
				bundle.putString("URL", linkUrl);
				fragment.setArguments(bundle);
				getActivity().getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, fragment)
				.addToBackStack(null)
				.commit();
				HomeActivity.setTitle("YouTube");	
			}
		});
		 btnPinterest.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				linkUrl = helper.getLinkURLByLinkName("PinInterest");
				if(linkUrl.isEmpty()){
					Toast.makeText(getActivity(), "More information will be available soon.", Toast.LENGTH_SHORT).show();
					return;
				}
				bundle.clear();
				bundle.putString("URL", linkUrl);
				fragment.setArguments(bundle);
				getActivity().getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, fragment)
				.addToBackStack(null)
				.commit();
				HomeActivity.setTitle("Pinterest");	
			}
		});
		 
		return view;
	}
	@Override
	public void onResume() {
		super.onResume();
		HomeActivity.setTitle("Social Media");
		ConnectivityManager cm =(ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
         
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                              activeNetwork.isConnectedOrConnecting();
        if(!isConnected)
        {
        	Toast.makeText(getActivity(), "Internet is not connected", Toast.LENGTH_LONG).show();
        }
	}
	@Override
	public void onStart() {
		super.onStart();
//		Commons.getMyTracker(getActivity(), "UtilitiesFragment");
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}
}
