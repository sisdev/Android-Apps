package com.assusoft.efair.epchfair.Fragments;

import java.util.ArrayList;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.assusoft.eFairEmall.databaseMaster.DatabaseHelper;
import com.assusoft.eFairEmall.entities.Exhibitors;
import com.epch.efair.delhifair.EFairEmallApplicationContext;
import com.epch.efair.delhifair.HomeAcitityFirst;
import com.epch.efair.delhifair.HomeActivity;
import com.epch.efair.delhifair.R;

public class CityList extends Fragment{
	
	EditText listFilter;
	ListView productListView;
	ArrayList<Exhibitors> productList = new ArrayList<Exhibitors>();
	DatabaseHelper dbHelper;
    CitylistAdapter adapter;
    FrameLayout banner;
    LinearLayout layout;
	@SuppressLint("CutPasteId")
	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container,  Bundle savedInstanceState) {
		View v=inflater.inflate(R.layout.productlist1,container,false);
    	listFilter=(EditText) v.findViewById(R.id.productSearchBox);
		productListView=(ListView) v.findViewById(R.id.productListView);
		layout		= (LinearLayout)v.findViewById(R.id.cityListID);
		
		banner=(FrameLayout) v.findViewById(R.id.AdsFrameLayout);
		HomeAcitityFirst.adLoader.showBanner(banner);
//		AnimationDrawable amin=(AnimationDrawable) banner.getBackground();
//		   amin.start();
		   
		//get the products list in arraylist
		dbHelper=EFairEmallApplicationContext.getDatabaseHelper();
		dbHelper.openDatabase(DatabaseHelper.READMODE);
		productList=dbHelper.getAllCity();
	    adapter=new CitylistAdapter(getActivity(), productList,listFilter);
		// Binds the Adapter to the ListView
        productListView.setAdapter(adapter);
		listFilter.addTextChangedListener(new TextWatcher() {
 
            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                String text = listFilter.getText().toString().toLowerCase(Locale.getDefault());
                adapter.filter(text);
            }
 
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                    int arg2, int arg3) {
                // TODO Auto-generated method stub
            }
 
            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                    int arg3) {
                
            }

			
        });
		final View activityRootView = (LinearLayout) v.findViewById(R.id.cityListID);
		 activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			
			@Override
			public void onGlobalLayout() {
				int heightView = activityRootView.getHeight();
                 int widthView = activityRootView.getWidth();
                 if (1.0 * widthView / heightView > 1) {
                //if (heightDiff > 100) { // if more than 100 pixels, its
                	if(banner.getVisibility()==View.VISIBLE)
      					 banner.setVisibility(View.GONE);
                } else {
                	if(banner.getVisibility()==View.GONE)
     					 banner.setVisibility(View.VISIBLE);
                }
			}
		});
		 productListView.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				HomeActivity.hideSoftKeyboard(getActivity());
				return false;
			}
		});
		 
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		return v;
	}
	
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
	}


	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		HomeActivity.setTitle("City List");
	}

}
