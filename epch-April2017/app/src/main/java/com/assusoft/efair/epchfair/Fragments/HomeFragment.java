package com.assusoft.efair.epchfair.Fragments;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.epch.efair.delhifair.CircularPagerAdapter;
import com.epch.efair.delhifair.R;
import com.google.android.gms.analytics.Tracker;

public class HomeFragment extends Fragment{
	ImageButton imgBtn;
	View v;
	Tracker tracker;
	ViewPager homePager;
	private ImageView imgView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		        v = inflater.inflate(R.layout.home_pager, container, false);
		        homePager=(ViewPager)v.findViewById(R.id.pagerEvent);
				imgView=(ImageView)v.findViewById(R.id.imgSelectIndicator);
		// Getting reference to the TextView of the Fragment
		imgBtn= (ImageButton) v.findViewById(R.id.imgBtnExhibitors);
		
		PagerAdapter eventPagerAdapter=new CircularPagerAdapter(getActivity(),homePager,new int[]{
				R.layout.home_fragment_first,R.layout.home_fragment_second });
		
			homePager.setAdapter(eventPagerAdapter);
			homePager.setCurrentItem(0);
			
			homePager.setOnPageChangeListener(new OnPageChangeListener() {
				
				@Override
				public void onPageSelected(int id) {
					// TODO Auto-generated method stub
					Resources res = getResources();
		        	Drawable draw; // need this to fetch the drawable;
					Log.i("EXPO", "onPageScrollStateChanged="+id);
					if(id==0)
					{
						 draw = res.getDrawable( R.drawable.first );
			            	imgView.setImageDrawable(draw);
			            	//Log.i("EXPO", "fistScreen="+draw);
						
					}else if(id==1)
					{
						draw = res.getDrawable( R.drawable.second);
		            	imgView.setImageDrawable(draw);
		            	//Log.i("EXPO", "secondScreen="+draw);
						
					}
				}
				
				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onPageScrollStateChanged(int id) {
					// TODO Auto-generated method stub
					
				}
			});
		
		return v;
	}
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
//		Commons.getMyTracker(getActivity(), "Home Fragment");
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