package com.assusoft.efair.epchfair.Fragments;

import java.util.ArrayList;
import java.util.Locale;

import com.epch.efair.delhifair.R;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
  
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.assusoft.eFairEmall.databaseMaster.DatabaseHelper;
import com.assusoft.eFairEmall.entities.Exhibitors;
import com.assusoft.eFairEmall.entities.VenueMap;
import com.epch.efair.delhifair.EFairEmallApplicationContext;
import com.epch.efair.delhifair.HomeActivity;

public class SearchExhibitorsFragment extends Fragment{

	EditText listFilter;
	ListView exhibitorListView;
	ArrayList<Exhibitors> exhibitorList;
	ArrayList<VenueMap> venueMap;
	DatabaseHelper dbHelper;
	public static String searchString="";
	ExbListViewAdapter adapter;
	Button srhFragmentExhibitor, srhFragmentProduct, srhFragmentHall;
	@Override
	public View onCreateView(LayoutInflater inflater,
			  ViewGroup container,   Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.search_fragment_exhibitors,container, false);
		//srhFragmentExhibitor = (Button) view.findViewById(R.id.btnExhibitors);
		srhFragmentProduct = (Button) view.findViewById(R.id.btnPoductName);
		srhFragmentHall = (Button) view.findViewById(R.id.btnHallNumber);
        exhibitorListView = (ListView) view.findViewById(R.id.lvSearchExhibitor);
        FrameLayout banner=(FrameLayout) view.findViewById(R.id.AdsFrameLayout);
		AnimationDrawable amin=(AnimationDrawable) banner.getBackground();
		   amin.start();
    	
		//get the intent reference and obtain the value of selected productId
    	
           searchString= getArguments().getString("searchText");
           
    	
        
      //get the products list in arraylist
      		dbHelper=EFairEmallApplicationContext.getDatabaseHelper();
      		dbHelper.openDatabase(DatabaseHelper.READMODE);
      		exhibitorList = new ArrayList<Exhibitors>();
      		
      		exhibitorList=dbHelper.getAllExhibitorWiseSearchResult(searchString);
      		if(exhibitorList.size()>0){
    			displayResult();
    		}
    		else{
    			noResultFound();
    		}
      		
      		
      		srhFragmentProduct.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Context fm = getActivity();
					FragmentTransaction ft = ((FragmentActivity)fm).getSupportFragmentManager().beginTransaction();
					SearchProductFragment spf = new SearchProductFragment();
					ft.replace(((ViewGroup)getView().getParent()).getId(), spf, "");
					ft.commit();
				}
			});
      		srhFragmentHall.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Context fm = getActivity();
					FragmentTransaction ft = ((FragmentActivity)fm).getSupportFragmentManager().beginTransaction();
					SearchHallFragment shf = new SearchHallFragment();
					ft.replace(((ViewGroup)getView().getParent()).getId(), shf, "");
					ft.commit();
				}
			});
		exhibitorListView.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				HomeActivity.hideSoftKeyboard(getActivity());
				return false;
			}
		});
		return view;	// super.onCreateView(inflater, container, savedInstanceState);
	}
	
	
	private void displayResult(){
		//Log.i("SEARCH","within dispalyResult....");
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


	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		HomeActivity.setTitle("Results: "+searchString);
	}

}