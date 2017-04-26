package com.assusoft.efair.epchfair.Fragments;

import java.util.ArrayList;
import java.util.Locale;

import com.assusoft.eFairEmall.databaseMaster.DatabaseHelper;
import com.assusoft.eFairEmall.entities.ExhibitorCategory;
import com.epch.efair.delhifair.EFairEmallApplicationContext;
import com.epch.efair.delhifair.HomeAcitityFirst;
import com.epch.efair.delhifair.HomeActivity;
import com.epch.efair.delhifair.R;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
 
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

public class ExhibitorCategoryFragment extends Fragment{
	
	EditText listFilter;
	ListView productListView;
	ArrayList<ExhibitorCategory> productList = new ArrayList<ExhibitorCategory>();
	DatabaseHelper dbHelper;
    CategoryAdapter adapter;
    FrameLayout banner;
    
	@Override
	public View onCreateView(LayoutInflater inflater,
			  ViewGroup container,   Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v=inflater.inflate(R.layout.productlist1,container,false);
		listFilter=(EditText) v.findViewById(R.id.productSearchBox);
		productListView=(ListView) v.findViewById(R.id.productListView);
		
		
		banner=(FrameLayout) v.findViewById(R.id.AdsFrameLayout);
		HomeAcitityFirst.adLoader.showBanner(banner);
//		AnimationDrawable amin=(AnimationDrawable) banner.getBackground();
//		   amin.start();
		//get the products list in arraylist
		dbHelper=EFairEmallApplicationContext.getDatabaseHelper();
		dbHelper.openDatabase(DatabaseHelper.READMODE);
		productList=dbHelper.getExhibitorCategory();
	    adapter=new CategoryAdapter(getActivity(), productList,listFilter);
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
                // TODO Auto-generated method stub
            }

			
        });
		
		final View activityRootView = (LinearLayout) v.findViewById(R.id.cityListID);
		 activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			
			@Override
			public void onGlobalLayout() {
				int heightDiff = activityRootView.getRootView()
                       .getHeight() - activityRootView.getHeight();
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
		HomeActivity.setTitle("Products Category");
	}

}
