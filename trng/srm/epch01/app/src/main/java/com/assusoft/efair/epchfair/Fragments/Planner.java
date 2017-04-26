package com.assusoft.efair.epchfair.Fragments;

import java.util.ArrayList;

import com.epch.efair.delhifair.R;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import com.assusoft.eFairEmall.databaseMaster.DatabaseHelper;
import com.assusoft.eFairEmall.entities.Appointment;
import com.epch.efair.delhifair.CircularPagerAdapter;
import com.epch.efair.delhifair.EFairEmallApplicationContext;
import com.epch.efair.delhifair.HomeAcitityFirst;
import com.epch.efair.delhifair.HomeActivity;

public class Planner extends Fragment{
	
	ViewPager datePager,eventPager;
	private static final int NO_OF_DAYS=5;
	public static ArrayList<Appointment> allscheduleInfo=null;
	//public static ArrayList<Appointment> firstDayInfoAdapter;
	public static ArrayList<ArrayList<Appointment>> dayInfo;
	private int position=0;
	DatabaseHelper dbHelper;
	ImageButton btnLeft,btnRight;
	Button btnScheduleExhibitor,btnScheduleEvent;
	private String dates[]=new String[]{"2016-04-16","2016-04-17","2016-04-18"/*,"2016-02-23"/*,"2015-10-18"*/};
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View v=inflater.inflate(R.layout.event_scheduler,container,false);

		btnLeft=(ImageButton) v.findViewById(R.id.btnPagerDateLeftscheduler);
		btnRight=(ImageButton) v.findViewById(R.id.btnPagerDateRightscheduler);
		
	
		btnScheduleExhibitor=(Button) v.findViewById(R.id.btnAddExhibitorSchedule);
		
		datePager=(ViewPager) v.findViewById(R.id.pagerDatescheduler);
		eventPager=(ViewPager) v.findViewById(R.id.pagerEventScheduler);
		
		FrameLayout banner=(FrameLayout) v.findViewById(R.id.AdsFrameLayout);
		HomeAcitityFirst.adLoader.showBanner(banner);
//	 	   AnimationDrawable amin=(AnimationDrawable) banner.getBackground();
//	 	   amin.start();
		return v;
	}
	
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		HomeActivity.setTitle("Planner");
		//initialize the arraylist info 
				allscheduleInfo=new ArrayList<Appointment>();
						//create the 
			    dbHelper=EFairEmallApplicationContext.getDatabaseHelper();
			    dbHelper.openDatabase(DatabaseHelper.READMODE);
				try{
					allscheduleInfo=dbHelper.getAllSchedule();
					Log.i("NBTI","Size of allscheduleInfo: "+allscheduleInfo.size());
					}catch(Exception e){
					  e.printStackTrace();
			         }				
				
				
				//create the pagerAdapter
				final PagerAdapter datePagerAdapter=new CircularPagerAdapter(getActivity(),datePager,eventPager, new int[]{
						R.layout.day_one_date,R.layout.day_second_date,R.layout.day_three_date});
				try{
					datePager.setAdapter(datePagerAdapter);
					datePager.setCurrentItem(position);
				}catch(Exception e){
					e.printStackTrace();
				}
				
				PagerAdapter eventPagerAdapter=new EventSchedulerPagerAdapter(getActivity(),eventPager,datePager,new int[]{
						R.layout.event_first_day,R.layout.event_day_second,R.layout.event_day_three/*, R.layout.event_day_four/*, R.layout.event_day_five*/});
				try{
					eventPager.setAdapter(eventPagerAdapter);
					eventPager.setCurrentItem(position);
					
				}catch(Exception e){
					e.printStackTrace();
				}
				
				SeminarCalendar.disableArrowButton(btnLeft, btnRight, position, datePagerAdapter.getCount());
				
				datePager.setOnPageChangeListener(new OnPageChangeListener() {
					
					@Override
					public void onPageSelected(int position) {
						
						eventPager.setCurrentItem(position);
						Planner.this.position=position;
						SeminarCalendar.disableArrowButton(btnLeft, btnRight, position, datePagerAdapter.getCount());
					}
					
					@Override
					public void onPageScrolled(int arg0, float arg1, int arg2) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onPageScrollStateChanged(int arg0) {
						// TODO Auto-generated method stub
						
					}
				});
				
				eventPager.setOnPageChangeListener(new OnPageChangeListener() {
					
					@Override
					public void onPageSelected(int position) {
						
						datePager.setCurrentItem(position);
						Planner.this.position=position;
						SeminarCalendar.disableArrowButton(btnLeft, btnRight, position, datePagerAdapter.getCount());
						
					}
					
					@Override
					public void onPageScrolled(int arg0, float arg1, int arg2) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onPageScrollStateChanged(int arg0) {
						// TODO Auto-generated method stub
						
					}
				});
				
				btnLeft.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						int position=datePager.getCurrentItem();
						if(position==0) return;
						datePager.setCurrentItem(position-1);
						eventPager.setCurrentItem(position-1);
						Planner.this.position=position-1;
					}
				});
				btnRight.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						int position=datePager.getCurrentItem();
						if(position==NO_OF_DAYS) return;
						datePager.setCurrentItem(position+1);
						eventPager.setCurrentItem(position+1);
						Planner.this.position=position+1;
						
					}
				});
				
				
				
				btnScheduleExhibitor.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						if(dbHelper.getAllExhibNameFromExhibTable().isEmpty()){
							Toast.makeText(getActivity(),"This feature will available soon",Toast.LENGTH_LONG).show();
							return;
						}else{
							FragmentTransaction ft = ((FragmentActivity)getActivity()).getSupportFragmentManager().beginTransaction();
			    	        
			    			Album fragment = new Album();
			    			Bundle bundle=new Bundle();
			    			bundle.putInt("FLAG",1);
			    			bundle.putString("DATE",dates[position]);
			    			fragment.setArguments(bundle);
			    			
			    	        ft.replace(R.id.content_frame, fragment,"");
			    	        ft.addToBackStack(null);
			    	        ft.commit();
						}
		              
					}
				});
				// data datewise 
				filterDataDateWise(allscheduleInfo);
	}
	
	
	private void filterDataDateWise(ArrayList<Appointment> data){
		dayInfo=new ArrayList<ArrayList<Appointment>>();
		int count=data.size();
		Log.i("NBTI","number of record "+count);
		for(int i=0;i<dates.length;i++){
			ArrayList<Appointment> apt=new ArrayList<Appointment>();
			for(int j=0;j<count;j++ ){
				if(data.get(j).getDate().equals(dates[i])){
					/*if(data.get(j).getEventName()==null || data.get(j).getExhibitorName()==null){
						continue;
					}*/
					apt.add(data.get(j));
					Log.i("NBTI"," Filte data "+data.get(j).getDate());
				}
			}
			
			dayInfo.add(apt);
		}
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
