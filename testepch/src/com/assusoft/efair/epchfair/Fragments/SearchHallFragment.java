package com.assusoft.efair.epchfair.Fragments;

import java.util.ArrayList;
import java.util.Locale;

import com.epch.efair.delhifair.R;
import android.content.Context;
import android.os.Bundle;
  
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.assusoft.eFairEmall.databaseMaster.DatabaseHelper;
import com.assusoft.eFairEmall.entities.Exhibitors;
import com.assusoft.eFairEmall.entities.VenueMap;
import com.epch.efair.delhifair.EFairEmallApplicationContext;
import com.epch.efair.delhifair.HomeActivity;

public class SearchHallFragment extends Fragment{
	
	EditText listFilter;
	ListView exhibitorListView;
	ArrayList<Exhibitors> exhibitorList;
	ArrayList<VenueMap> venueMap;
	DatabaseHelper dbHelper;
	ExbListViewAdapter adapter;
	Button srhFragmentExhibitor, srhFragmentProduct;
	@Override
	public View onCreateView(LayoutInflater inflater,
			  ViewGroup container,   Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.search_fragment_hall,container,false);
		srhFragmentExhibitor = (Button) view.findViewById(R.id.btnExhibitors);
		srhFragmentProduct = (Button) view.findViewById(R.id.btnPoductName);
		exhibitorListView = (ListView) view.findViewById(R.id.lvSearchHall);
		
		/*//add
		AdView adView = new AdView(getActivity());
    	adView.setAdUnitId(Commons.admobToken);
    	adView.setAdSize(AdSize.SMART_BANNER);
    	FrameLayout layout = (FrameLayout)view.findViewById(R.id.AdsFrameLayout);    
    	layout.addView(adView); 
    	AdRequest request = Commons.GetAds(getActivity());
    	adView.loadAd(request);*/
    	
    	
    	//get the products list in arraylist
  		dbHelper=EFairEmallApplicationContext.getDatabaseHelper();
  		dbHelper.openDatabase(DatabaseHelper.READMODE);
  		exhibitorList = new ArrayList<Exhibitors>();
  		
  		exhibitorList=dbHelper.searchExhibitorsHallWise("%"+SearchExhibitorsFragment.searchString+"%");
  		if(exhibitorList.size()>0){
			displayResult();
		}
		else{
			noResultFound();
		}
    	
		srhFragmentExhibitor.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Context fm = getActivity();
				FragmentTransaction ft = ((FragmentActivity)fm).getSupportFragmentManager().beginTransaction();
				SearchExhibitorsFragment shf = new SearchExhibitorsFragment();
				Bundle bundle = new Bundle();
				bundle.putString("searchText",SearchExhibitorsFragment.searchString);
				shf.setArguments(bundle);
				ft.replace(((ViewGroup)getView().getParent()).getId(), shf, "");
				ft.commit();
			}
		});
		srhFragmentProduct.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Context fm = getActivity();
				FragmentTransaction ft = ((FragmentActivity)fm).getSupportFragmentManager().beginTransaction();
				SearchProductFragment shf = new SearchProductFragment();
				ft.replace(((ViewGroup)getView().getParent()).getId(), shf, "");
				ft.commit();
			}
		});
		return view;	// super.onCreateView(inflater, container, savedInstanceState);
	}
//++++++++++++++++++++++++++++++++++++++++++DisplayResult Method++++++++++++++++++++++++++++++++++++++++++
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
		HomeActivity.setTitle("Results: "+SearchExhibitorsFragment.searchString);
	}

}
