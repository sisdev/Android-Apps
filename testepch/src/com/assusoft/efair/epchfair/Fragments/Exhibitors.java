package com.assusoft.efair.epchfair.Fragments;

import java.util.ArrayList;

import com.assusoft.eFairEmall.databaseMaster.DatabaseHelper;
import com.assusoft.eFairEmall.entities.Products;
import com.assusoft.eFairEmall.util.Util;
import com.epch.efair.delhifair.EFairEmallApplicationContext;
import com.epch.efair.delhifair.HomeAcitityFirst;
import com.epch.efair.delhifair.R;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
  
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.epch.efair.delhifair.HomeActivity;

public class Exhibitors extends Fragment{
	ImageButton products,exhibitorName,city,stallNo,category;
	ImageView srhIcon;
	String searchText;
	AutoCompleteTextView srhText;
	
	ArrayList<Products> productList = new ArrayList<Products>();
	DatabaseHelper dbHelper;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			  ViewGroup container,   Bundle savedInstanceState) {
		View v =inflater.inflate(R.layout.exhibitors,container,false);
		category=(ImageButton) v.findViewById(R.id.btnProductCategory);
		srhIcon			= (ImageView) v.findViewById(R.id.imgViewSearchIcon);
		srhText			= (AutoCompleteTextView) v.findViewById(R.id.autoCompleteTextView);
		
		products		=(ImageButton) v.findViewById(R.id.btnexbproducts);
		exhibitorName	=(ImageButton) v.findViewById(R.id.btnexhibitorName);
		city			=(ImageButton) v.findViewById(R.id.btnexbcity);
		stallNo			=(ImageButton) v.findViewById(R.id.btnexbstallNo);
		
		FrameLayout banner=(FrameLayout) v.findViewById(R.id.AdsFrameLayoutSearch);
		
		HomeAcitityFirst.adLoader.showBanner(banner);
		
//		AnimationDrawable amin=(AnimationDrawable) banner.getBackground();
//		   amin.start();
		   dbHelper=EFairEmallApplicationContext.getDatabaseHelper();
			dbHelper.openDatabase(DatabaseHelper.READMODE);
			productList=dbHelper.getAllProducts();
		   
			if(!(productList.size()>0))
			{
				products.setVisibility(View.GONE);
			}
		   
		   srhIcon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				HomeActivity.hideSoftKeyboard(getActivity());
				
				Bundle bundle =new Bundle();
				searchText = srhText.getText().toString().trim();
				if(searchText.equals("")){
					Toast.makeText(getActivity(), "Please enter Exhibitor's name or Product name or Hall number", Toast.LENGTH_SHORT).show();
				}else{
					bundle.putString("searchText", searchText);
					Context fm = getActivity();
					FragmentTransaction ft = ((FragmentActivity)fm).getSupportFragmentManager().beginTransaction();
					SearchExhibitorsFragment sef =new SearchExhibitorsFragment();
					sef.setArguments(bundle);
					ft.replace(R.id.content_frame, sef,"");
					ft.addToBackStack(null);
					ft.commit();
				}
			}
		});
		
		products.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
 		        ProductsList fg=new ProductsList();
                ft.replace(R.id.content_frame,fg);
                ft.addToBackStack(null);
                ft.commit();
			}
		});
		exhibitorName.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Util.copyDatabase(getActivity(), DatabaseHelper.DBNAME);
				FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
 		        ExhibitorsName fg=new ExhibitorsName();
 		        Bundle bundle=new Bundle();
		     
		        bundle.putInt("FLAG",0);
		        fg.setArguments(bundle);
                ft.replace(R.id.content_frame,fg);
                ft.addToBackStack(null);
                ft.commit();
			}
		});
		city.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
 		        CityList fg=new CityList();
 		       
                ft.replace(R.id.content_frame,fg);
                ft.addToBackStack(null);
                ft.commit();
			}
		});
		stallNo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
 		        HallFragment fg=new HallFragment();
 		       
                ft.replace(R.id.content_frame,fg);
                ft.addToBackStack(null);
                ft.commit();
			}
		});
		category.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
 		        ExhibitorCategoryFragment fg=new ExhibitorCategoryFragment();
 		       
                ft.replace(R.id.content_frame,fg);
                ft.addToBackStack(null);
                ft.commit();
				
			}
		});
		
		return v;
	}
	
	@Override
	public void onPause() {
		super.onPause();
		HomeActivity.hideSoftKeyboard(getActivity());
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		HomeActivity.setTitle("Exhibitors");
		/*InputMethodManager inputManager = (InputMethodManager) getActivity()
	            .getSystemService(Context.INPUT_METHOD_SERVICE);
	    //check if no view has focus:
	    View v=getActivity().getCurrentFocus();
	    if(v==null)
	        return;
	    inputManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.SHOW_IMPLICIT);*/
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
	}

}
