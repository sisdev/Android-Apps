package com.assusoft.efair.epchfair.Fragments;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.assusoft.eFairEmall.databaseMaster.DatabaseHelper;
import com.assusoft.eFairEmall.entities.EventInfo;
import com.epch.efair.delhifair.EFairEmallApplicationContext;
import com.epch.efair.delhifair.HomeActivity;
import com.epch.efair.delhifair.R;

public class EventDetailsFragment extends Fragment{
    TextView tvName,tvTime,tvModerater,tvPanelist,tvDescription;
    DatabaseHelper dbHelper;
    Bundle bundle;
    ImageButton imgButton;
    int eventId;
    String eventName,eventTime,moderater,panelist;
    List<EventInfo> info;
   
    EventInfo eventInfo;
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
    	View v=inflater.inflate(R.layout.events_details,container,false);
    	tvName=(TextView) v.findViewById(R.id.tvEventName);
    	tvTime=(TextView) v.findViewById(R.id.tvTime);
    	tvDescription=(TextView) v.findViewById(R.id.tvDescription);
    	tvModerater=(TextView) v.findViewById(R.id.tvModerater);
    	tvPanelist=(TextView) v.findViewById(R.id.tvPanel);
    	
    	/*//admob
    	AdView adView = new AdView(getActivity(), AdSize.SMART_BANNER, Commons.admobToken);    
		FrameLayout layout = (FrameLayout)v.findViewById(R.id.AdsFrameLayout);    
		layout.addView(adView); 
		AdRequest request = Commons.GetAds(getActivity());
		adView.loadAd(request);*/
		
    	bundle=getArguments();
    	eventId=bundle.getInt("EVENTID");
    	//hold eventId for backbutton handling
    	HomeActivity.eventId=eventId;
    	
    	
    	dbHelper=EFairEmallApplicationContext.getDatabaseHelper();
    	dbHelper.openDatabase(DatabaseHelper.READMODE);
    	info=dbHelper.getAllEventInfoById(Integer.toString(eventId));
    	tvName.setText(info.get(0).getEventName());
    	tvTime.setText(info.get(0).getStartTime()+" - "+info.get(0).getEndTime());
    	tvDescription.setText(info.get(0).getDescription());
    	tvModerater.setText(info.get(0).getModerater());
    	tvPanelist.setText(info.get(0).getPanelLists());
    	
    	return v;
    }
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
//		Commons.getMyTracker(getActivity(), "ConclaveDetailsFragment");
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
