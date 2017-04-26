package com.assusoft.efair.epchfair.Fragments;


import java.util.ArrayList;

import com.epch.efair.delhifair.R;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
  
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.assusoft.eFairEmall.databaseMaster.DatabaseHelper;
import com.assusoft.eFairEmall.entities.VenueMap;
import com.epch.efair.delhifair.EFairEmallApplicationContext;
import com.epch.efair.delhifair.HomeAcitityFirst;
import com.epch.efair.delhifair.HomeActivity;

public class Map extends Fragment{
	Button btnHall,btnMart,btnEHub;
	DatabaseHelper dbHelper;
	public static final int mart=1,hall=2;
	@Override
	public View onCreateView(LayoutInflater inflater,
			  ViewGroup container,   Bundle savedInstanceState) {
		View v=inflater.inflate(R.layout.map,container,false);
		btnHall=(Button) v.findViewById(R.id.btnMapHall);
		btnMart=(Button) v.findViewById(R.id.btnMapMart);
		btnEHub=(Button) v.findViewById(R.id.btnEhub);
		
		
		FrameLayout banner=(FrameLayout) v.findViewById(R.id.AdsFrameLayout);
		banner.setBackgroundDrawable(HomeAcitityFirst.animatin);
		HomeAcitityFirst.animatin.setOneShot(false);
		HomeAcitityFirst.animatin.start();
	 	   /*AnimationDrawable amin=(AnimationDrawable) banner.getBackground();
	 	   amin.start();*/
		dbHelper=EFairEmallApplicationContext.getDatabaseHelper();
		dbHelper.openDatabase(DatabaseHelper.READMODE);
		
		btnHall.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Bundle bundle=new Bundle();
				ArrayList<VenueMap> maplist=new ArrayList<VenueMap>();
				maplist=dbHelper.getVenueMapDetailByType(""+hall);
				if(maplist.size()<=0){
					Toast.makeText(getActivity(), "Halls data not available",Toast.LENGTH_SHORT).show();
					return;
				}
				bundle.putParcelableArrayList("DATA",maplist);
				FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
				LayoutsMap map=new LayoutsMap();
				map.setArguments(bundle);
				ft.replace(R.id.content_frame,map);
				ft.addToBackStack(null);
				ft.commit();
				
			}
		});
		
		btnMart.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Bundle bundle=new Bundle();
				ArrayList<VenueMap> maplist=new ArrayList<VenueMap>();
				maplist=dbHelper.getVenueMapDetailByType(""+mart);
				if(maplist.size()<=0){
					Toast.makeText(getActivity(), "Marts data not available",Toast.LENGTH_SHORT).show();
					return;
				}
				bundle.putParcelableArrayList("DATA",maplist);
				FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
				LayoutsMap map=new LayoutsMap();
				map.setArguments(bundle);
				ft.replace(R.id.content_frame,map);
				ft.addToBackStack(null);
				ft.commit();
			}
		});
		btnEHub.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(dbHelper.getFileNameOfVenueMap(3).isEmpty()){
					Toast.makeText(getActivity(), "Marts data not available",Toast.LENGTH_SHORT).show();
					return;
				}
				FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
				EHub_Image_View eHub=new EHub_Image_View();
				ft.replace(R.id.content_frame,eHub);
				ft.addToBackStack(null);
				ft.commit();
			}
		});
		return v;
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		HomeActivity.setTitle("Floor Plans");
	}

}
