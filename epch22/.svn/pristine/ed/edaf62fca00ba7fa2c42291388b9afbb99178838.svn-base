package com.assusoft.efair.epchfair.Fragments;

import java.util.ArrayList;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.assusoft.eFairEmall.databaseMaster.DatabaseHelper;
import com.assusoft.eFairEmall.entities.Exhibitors;
import com.assusoft.eFairEmall.entities.VenueMap;
import com.epch.efair.delhifair.EFairEmallApplicationContext;
import com.epch.efair.delhifair.HomeAcitityFirst;
import com.epch.efair.delhifair.HomeActivity;
import com.epch.efair.delhifair.R;

public class ExhibitorsName extends Fragment{
	
	View view;
	EditText listFilter;
	ListView exhibitorListView;
	ArrayList<Exhibitors> exhibitorList;
	ArrayList<VenueMap> venueMap;
	DatabaseHelper dbHelper;
	ExbListViewAdapter adapter;
    Intent intent;
	int flags;//if this fragment is called from productlist pass 1, citylist pass 2, hall list pass 3, from exhibitors pass 0
	boolean flag=false; 
	FrameLayout banner;
	
    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState){
		//initialize the myVisitflag
		//..EFairEmallApplicationContext.myvisitFlag=(byte)1;
    	view = inflater.inflate(R.layout.search_result, container, false);
		listFilter=(EditText) view.findViewById(R.id.productSearchBoxSearch);
		exhibitorListView=(ListView) view.findViewById(R.id.productListViewSearch);
		
		banner=(FrameLayout) view.findViewById(R.id.AdsFrameLayout);
		HomeAcitityFirst.adLoader.showBanner(banner);
//		AnimationDrawable amin=(AnimationDrawable) banner.getBackground();
//		   amin.start();
		/*
		//admob
    	AdView adView = new AdView(getActivity(), AdSize.SMART_BANNER, Commons.admobToken);    
		FrameLayout layout = (FrameLayout)view.findViewById(R.id.AdsFrameLayout);    
		layout.addView(adView); 
		AdRequest request = Commons.GetAds(getActivity());
		adView.loadAd(request);
		*/
		//get the products list in arraylist
		dbHelper=EFairEmallApplicationContext.getDatabaseHelper();
		dbHelper.openDatabase(DatabaseHelper.READMODE);
		exhibitorList = new ArrayList<Exhibitors>();
		Bundle bundle=getArguments();
	    flags=bundle.getInt("FLAG");
		switch (flags) {
		case 0:
			 exhibitorList=dbHelper.getAllExhibitorWiseSearchResult("");
			break;
		case 1:
			
			exhibitorList=dbHelper.getAllProductWiseSearchResult(bundle.getString("PRODUCTNAME"));
			
			break;
		case 2: 
			exhibitorList=dbHelper.searchExhibitorsCity(bundle.getString("CITY"));
			break;
		case 3:
			exhibitorList=dbHelper.searchExhibitorsHallWise(bundle.getString("HALLNO"));
			break;

		case 4:
			exhibitorList=dbHelper.getCategoryWiseSearchResult(""+bundle.getInt("CATEGORYID"));
			break;
		default:
			break;
		}
		Log.i("EFAIR","size of exhibitor list "+Integer.toString(exhibitorList.size()));
		if(exhibitorList.size()>0){
			displayResult();
		}
		else{
			noResultFound();
		}
		exhibitorListView.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				HomeActivity.hideSoftKeyboard(getActivity());
				return false;
			}
		});
		
		//setting visibility of view after resize layout
		final View rootLayout	= (LinearLayout) view.findViewById(R.id.llSearchResult);
		rootLayout.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			
			@Override
			public void onGlobalLayout() {
				 int heightDiff = rootLayout.getRootView()
	                        .getHeight() - rootLayout.getHeight();
				 int heightView = rootLayout.getHeight();
                 int widthView = rootLayout.getWidth();
                 if (1.0 * widthView / heightView > 1) {
	                //if (heightDiff > 100) { // if more than 100 pixels, its
	                    if(banner.getVisibility()==View.VISIBLE){
	                    	banner.setVisibility(View.GONE);
	                    }
	                } else {
	                   if(banner.getVisibility()==View.GONE)
	                	   banner.setVisibility(View.VISIBLE);
	                }
			}
		});
		
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
	  
      return view;
		
	}
	
	private void displayResult(){
		adapter=new ExbListViewAdapter(getActivity(),exhibitorList,listFilter,false);
		// Binds the Adapter to the ListView
	    try{
        exhibitorListView.setAdapter(adapter);
	    }catch(Exception e){ e.printStackTrace();}
        
        //add the textWatcher listener to search text box
        try{
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

			
        });}catch(Exception e){ e.printStackTrace();}
        
		
	}
	private void noResultFound(){
		String noresultFound[]=new String[]{"No Result Found"};
		
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),R.layout.exb_details_listview_item, noresultFound);
		exhibitorListView.setAdapter(adapter);
		
	}
	
	@SuppressLint("InlinedApi")
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
		HomeActivity.hideSoftKeyboard(getActivity());
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
//		Commons.getMyTracker(getActivity(), "SearchResultFragment");
		
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		HomeActivity.setTitle("Exhibitors Name");
		HomeActivity.hideSoftKeyboard(getActivity());
		InputMethodManager inputManager = (InputMethodManager) getActivity()
	            .getSystemService(Context.INPUT_METHOD_SERVICE);
	    //check if no view has focus:
	    View v=getActivity().getCurrentFocus();
	    if(v==null)
	        return;
	    inputManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
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
