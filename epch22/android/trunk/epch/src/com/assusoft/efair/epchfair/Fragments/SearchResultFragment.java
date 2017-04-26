package com.assusoft.efair.epchfair.Fragments;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.assusoft.eFairEmall.databaseMaster.DatabaseHelper;
import com.assusoft.eFairEmall.entities.Exhibitors;
import com.assusoft.eFairEmall.entities.VenueMap;
import com.epch.efair.delhifair.EFairEmallApplicationContext;
import com.epch.efair.delhifair.HomeActivity;
import com.epch.efair.delhifair.R;

public class SearchResultFragment extends Fragment{
	View view;
	EditText listFilter;
	ListView exhibitorListView;
	ArrayList<Exhibitors> exhibitorList;
	ArrayList<VenueMap> venueMap;
	DatabaseHelper dbHelper;
	ExbListViewAdapter adapter;
    Intent intent;
	String searchString;
	String searchOption;
	boolean flag=false; 
    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState){
		//initialize the myVisitflag
		//..EFairEmallApplicationContext.myvisitFlag=(byte)1;
    	view = inflater.inflate(R.layout.search_result, container, false);
		listFilter=(EditText) view.findViewById(R.id.productSearchBoxSearch);
		exhibitorListView=(ListView) view.findViewById(R.id.productListViewSearch);
		
		/*//admob
    	AdView adView = new AdView(getActivity(), AdSize.SMART_BANNER, Commons.admobToken);    
		FrameLayout layout = (FrameLayout)view.findViewById(R.id.AdsFrameLayout);    
		layout.addView(adView); 
		AdRequest request = Commons.GetAds(getActivity());
		adView.loadAd(request);
		*/
		//get the intent reference and obtain the value of selected productId
        searchString=this.getArguments().getString("SEARCH_STRING").trim();
        searchOption=this.getArguments().getString("SEARCH_OPTION");
		//get the products list in arraylist
		dbHelper=EFairEmallApplicationContext.getDatabaseHelper();
		dbHelper.openDatabase(DatabaseHelper.READMODE);
		exhibitorList = new ArrayList<Exhibitors>();
		if(searchOption.equalsIgnoreCase("Hall Number")){
			exhibitorList=dbHelper.searchExhibitorsHallWise(searchString);
		}
		else if(searchOption.equalsIgnoreCase("Product Name")){
			exhibitorList=dbHelper.getAllProductWiseSearchResult(searchString);
		}else if(searchOption.equalsIgnoreCase("Language Wise")){
			exhibitorList=dbHelper.searchExhibitorsLanguageWise(searchString);
			Log.i("EFAIR","langugat wise"+searchString);
		}else if(searchOption.equalsIgnoreCase("Exhibitor Name")){
			exhibitorList=dbHelper.getAllExhibitorWiseSearchResult(searchString);
		}
		Log.i("EFAIR","size of exhibitor list "+Integer.toString(exhibitorList.size()));
		if(exhibitorList.size()>0){
			displayResult();
		}
		else{
			noResultFound();
		}
	  
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
	public List<String> getFollowing()
	{     List<String> list=new ArrayList<String>();
	      String sortName=null;
		  SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
		  int fSize=getFollowingSize();
		  for(int i=0;i<fSize;i++)
		  {
			  String name = preferences.getString("companyName"+i,""); 
			  if(name.length()>10)
			  {
				  sortName = name.substring(0,8);
			  }else{
				  sortName = name.substring(0,3);
			  }
			  Log.i("EFAIR", "sortName:"+sortName);
			  list.add(sortName);
		  }
		 
		return list;
	}
	public int getFollowingSize()
	{     
		  SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
		  int size = preferences.getInt("fSize",0);
		return size;
	}
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
//		Commons.getMyTracker(getActivity(), "SearchResultFragment");
		if(HomeActivity.isMatchMaking)
		{
		  HomeActivity.goForExhibitorDatails=3;	
		  HomeActivity.isMatchMaking=false;
		}else{
			HomeActivity.isMatchMaking=false;
		}
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
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
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
	}
	
}



