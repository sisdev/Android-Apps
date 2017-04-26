package com.assusoft.efair.epchfair.Fragments;

import java.util.ArrayList;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.assusoft.eFairEmall.databaseMaster.DatabaseHelper;
import com.assusoft.eFairEmall.entities.EventInfo;
import com.epch.efair.delhifair.EFairEmallApplicationContext;
import com.epch.efair.delhifair.HomeActivity;
import com.epch.efair.delhifair.R;

public class ConclaveFirstFragment extends Fragment{
	DatabaseHelper dbHelper;
    ConclaveAdapter adapter;
	Button btnConclaveSecond;
	ListView lvConclave;
	public static String DATE="2014-06-11";
	int eventId;//this id is set or get it from intent
	ArrayList<EventInfo> eventInfoList = new ArrayList<EventInfo>();
	 Context context;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	
		View v = inflater.inflate(R.layout.conclave_first_fragment, container, false);
		
		// Getting reference to the TextView of the Fragment
		btnConclaveSecond= (Button) v.findViewById(R.id.btnConclaveSecond);
		lvConclave=(ListView) v.findViewById(R.id.lvConclaveFirst);
		/*AdView adView = new AdView(getActivity(), AdSize.SMART_BANNER, Commons.admobToken);    
		FrameLayout layout = (FrameLayout)v.findViewById(R.id.AdsFrameLayout);    
		layout.addView(adView); 
		AdRequest request = Commons.GetAds(getActivity());
		adView.loadAd(request);*/
		dbHelper=EFairEmallApplicationContext.getDatabaseHelper();
		dbHelper.openDatabase(DatabaseHelper.READMODE);
		
		eventInfoList=dbHelper.getAllEventInfo(DATE);
		Log.i("EFAIR","size of eventInfoList "+Integer.toString(eventInfoList.size()));
	    adapter=new ConclaveAdapter(getActivity(),eventInfoList);
		// Binds the Adapter to the ListView
	    try{
	    	lvConclave.setAdapter(adapter);
	    }catch(Exception e){ e.printStackTrace();}
        //Toast.makeText(getActivity(), "Page is loading...",Toast.LENGTH_SHORT).show();
	    btnConclaveSecond.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				HomeActivity.goForEventsDetails=1;
				Context fm =(v.getContext());
				FragmentTransaction ft = ((FragmentActivity)fm).getSupportFragmentManager().beginTransaction();
		        
		        ConclaveSecondFragment fragment = new  ConclaveSecondFragment();
		        ft.replace(R.id.content_frame, fragment);
		        ft.commit();
			}
		});
		
		return v;
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
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
		// TODO Auto-generated method stub
		super.onStart();
//		Commons.getMyTracker(getActivity(), "ConclaveFirstFragment");
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
