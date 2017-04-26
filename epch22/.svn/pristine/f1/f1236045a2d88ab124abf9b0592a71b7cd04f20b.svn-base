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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.assusoft.eFairEmall.databaseMaster.DatabaseHelper;
import com.assusoft.eFairEmall.entities.Exhibitors;
import com.assusoft.eFairEmall.entities.Products;
import com.assusoft.eFairEmall.entities.VenueMap;
import com.epch.efair.delhifair.EFairEmallApplicationContext;
import com.epch.efair.delhifair.HomeActivity;

public class SearchProductFragment extends Fragment{

	EditText listFilter;
	ListView exhibitorListView;
	ArrayList<Exhibitors> exhibitorList;
	ArrayList<VenueMap> venueMap;
	DatabaseHelper dbHelper;
	ArrayAdapter<String> adapter;
	Button srhFragmentHall, srhFragmentExhibitor; 
	ArrayList<Products> products;
	ArrayList<String> listData;
	@Override
	public View onCreateView(LayoutInflater inflater,
			  ViewGroup container,   Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.search_fragment_products,container,false);
		
		srhFragmentExhibitor = (Button) view.findViewById(R.id.btnExhibitors);
		srhFragmentHall = (Button) view.findViewById(R.id.btnHallNumber);
		exhibitorListView = (ListView) view.findViewById(R.id.lvSearchProduct);
		
		
		    	
		    	//get the products list in arraylist
		  		dbHelper=EFairEmallApplicationContext.getDatabaseHelper();
		  		dbHelper.openDatabase(DatabaseHelper.READMODE);
		  		products=new ArrayList<Products>();
		  		
		  		products=dbHelper.getMatchedProduct(SearchExhibitorsFragment.searchString);
		  		listData=new ArrayList<String>();
		  		
		 
		  		if(products.size()>0){
		  			for(int i=0;i<products.size();i++){
			  			listData.add(products.get(i).getProductName());
			  		}
					adapter=new ArrayAdapter<String>(getActivity(), R.layout.exb_details_listview_item, listData);
					exhibitorListView.setAdapter(adapter);
				}
				else{
					noResultFound();
				}
		    	
		
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
		srhFragmentExhibitor.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Context fm = getActivity();
				FragmentTransaction ft = ((FragmentActivity)fm).getSupportFragmentManager().beginTransaction();
				SearchExhibitorsFragment shf = new SearchExhibitorsFragment();
				Bundle bundle=new Bundle();
				bundle.putString("searchText",SearchExhibitorsFragment.searchString);
				shf.setArguments(bundle);
				ft.replace(((ViewGroup)getView().getParent()).getId(), shf, "");
				ft.commit();
			}
		});
		exhibitorListView.setOnItemClickListener(new  OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				if(products.isEmpty()||products==null){
					return;
				}
				FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
 		        ExhibitorsName fg=new ExhibitorsName();
 		        Bundle bundle=new Bundle();
 		        bundle.putString("PRODUCTNAME",products.get(pos).getProductName());
 		        bundle.putInt("FLAG",1);
 		        fg.setArguments(bundle);
                ft.replace(R.id.content_frame,fg);
                ft.addToBackStack(null);
                ft.commit();
				
			}
		});
		return view;	// super.onCreateView(inflater, container, savedInstanceState);
	}
	//++++++++++++++++++++++++++++++++++++++++++DisplayResult Method++++++++++++++++++++++++++++++++++++++++++
		private void displayResult(){
			/*//Log.i("SEARCH","within dispalyResult....");
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
	        
			*/
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
