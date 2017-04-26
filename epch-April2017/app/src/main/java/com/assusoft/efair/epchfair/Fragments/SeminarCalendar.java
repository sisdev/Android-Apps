package com.assusoft.efair.epchfair.Fragments;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.assusoft.eFairEmall.databaseMaster.DatabaseHelper;
import com.assusoft.eFairEmall.entities.Appointment;
import com.epch.efair.delhifair.CircularPagerAdapter;
import com.epch.efair.delhifair.HomeAcitityFirst;
import com.epch.efair.delhifair.HomeActivity;
import com.epch.efair.delhifair.R;

import android.annotation.SuppressLint;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;

public class SeminarCalendar extends Fragment{
	
	ViewPager datePager,eventPager;
	public static ArrayList<Appointment> allscheduleInfo=null;
	private int position=0;
	private SimpleDateFormat sdformat;
	private String closestDate="0";
	DatabaseHelper dbHelper;
	ImageButton btnLeft,btnRight;
	Button btnScheduleExhibitor,btnScheduleEvent;
	private List<String> dates;
	@SuppressLint({ "NewApi", "SimpleDateFormat" })
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View v=inflater.inflate(R.layout.seminar_calendar_web_view,container,false);

		btnLeft=(ImageButton) v.findViewById(R.id.btnPagerDateLeftscheduler);
		btnRight=(ImageButton) v.findViewById(R.id.btnPagerDateRightscheduler);
		
		datePager=(ViewPager) v.findViewById(R.id.pagerDatescheduler);
		eventPager=(ViewPager) v.findViewById(R.id.pagerEventScheduler);
		
		FrameLayout banner=(FrameLayout) v.findViewById(R.id.AdsFrameLayout);
		HomeAcitityFirst.adLoader.showBanner(banner);
	 	   /*AnimationDrawable amin=(AnimationDrawable) banner.getBackground();
	 	   amin.start();*/
	 	   
	 	 dates =new ArrayList<String>();
	 	 dates.add("00-00-00");
	 	 dates.add("00-00-00");
	 	 dates.add("00-00-00");
	 	// dates.add("2016-02-23");
	 	 sdformat	= new SimpleDateFormat("yyyy-MM-dd");
	 	   
	 	  /**Getting current date from calendar*/
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			
			Date date1 = calendar.getTime();
			
			for(int i=0;i<dates.size();i++){
				try {
					Date date2 = sdformat.parse(dates.get(i));
					Log.i("TABLE_DATA","Current Date: "+date1);
					Log.i("TABLE_DATA","Date 2: "+date2);
					int value=date2.compareTo(date1);
					Log.i("TABLE_DATA","Date Difference: "+value);
					
					if(value==0||value>0){
						closestDate=dates.get(i);
						break;
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}

			if(!closestDate.equals("0")){
				Log.i("TABLE_DATA","Closest Date in list: "+closestDate);
				position=dates.indexOf(closestDate);
				
			}else{
				Log.i("TABLE_DATA","Closest Date in list: "+closestDate);
				position=dates.size()-1;
			}
	 	   
	 	   
	 	   
	 	  v.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
	 	   
		return v;
	}
	@Override
	public void onResume() {
		super.onResume();
		HomeActivity.setTitle("Seminars");
		final PagerAdapter datePagerAdapter=new CircularPagerAdapter(getActivity(),datePager,eventPager, new int[]{
			R.layout.day_one_date,R.layout.day_second_date,R.layout.day_three_date,/*R.layout.day_four_date,*/
			/*R.layout.day_five_date*/});
		try{
			datePager.setAdapter(datePagerAdapter);
			datePager.setCurrentItem(position);
		}catch(Exception e){
			e.printStackTrace();
		}
		PagerAdapter eventPagerAdapter=new SeminarCalendarPager(getActivity(),eventPager,datePager,new int[]{
			R.layout.web_open,R.layout.web_open,R.layout.web_open,/*R.layout.web_open,*//*R.layout.web_open,*/});
		try{
			eventPager.setAdapter(eventPagerAdapter);
			eventPager.setCurrentItem(position);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		disableArrowButton(btnLeft, btnRight, position, datePagerAdapter.getCount());
		
		datePager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				
				eventPager.setCurrentItem(position);
				SeminarCalendar.this.position=position;
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
				SeminarCalendar.this.position=position;
				disableArrowButton(btnLeft, btnRight, position, datePagerAdapter.getCount());
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
		btnLeft.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int position=datePager.getCurrentItem();
				if(position==0) return;
				datePager.setCurrentItem(position-1);
				eventPager.setCurrentItem(position-1);
				SeminarCalendar.this.position=position-1;
			}
		});
		btnRight.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int position=datePager.getCurrentItem();
				if(position==dates.size()) return;
				datePager.setCurrentItem(position+1);
				eventPager.setCurrentItem(position+1);
				SeminarCalendar.this.position=position+1;
				
			}
		});
	}
	
	public static void disableArrowButton(ImageButton btnLeft, ImageButton btnRight, int position, int pagerSize){
		if(position==0){
			btnLeft.setVisibility(View.INVISIBLE);
		}else{
			btnLeft.setVisibility(View.VISIBLE);
		}
		
		if(position==pagerSize-1){
			btnRight.setVisibility(View.INVISIBLE);
		}else{
			btnRight.setVisibility(View.VISIBLE);
		}
	}

}
