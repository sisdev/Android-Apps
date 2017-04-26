package com.assusoft.efair.epchfair.Fragments;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.assusoft.eFairEmall.databaseMaster.DatabaseHelper;
import com.assusoft.eFairEmall.entities.Exhibitors;
import com.epch.efair.delhifair.EFairEmallApplicationContext;
import com.epch.efair.delhifair.HomeActivity;
import com.epch.efair.delhifair.R;
public class SearchFragment extends Fragment{
	View view ;
	RadioGroup rg;
	RadioButton rb;
	ImageView searchIcon;
	String searchString;
	AutoCompleteTextView autoCompleteTextView;
	String selectedOption;
	DatabaseHelper dbHelper;
	ArrayList<Exhibitors> exhibitor;//To store exhibitorName.
	ArrayList<String>productName;//To store productName.
	ArrayList<String> hallNo;//To store hallNo.
	ArrayList<String> languages;//To store hallNo.
	ArrayList<String>ExhibitorName;
	//String ExhibitorName[];//To store exhibitorName in string array.
	String ProductName[];//to store product name in string array.
	ArrayAdapter<String> adapter;//ArrayAdapter to store AutoCompleteTextVie data.
	boolean flag=false;
	ImageButton btnSearch;
	String txt;//Used in autoCompleteTextView for getting and setting text.
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	    view = inflater.inflate(R.layout.search_fragment, container, false);
	  /*  
	  //admob
    	AdView adView = new AdView(getActivity(), AdSize.SMART_BANNER, Commons.admobToken);    
		FrameLayout layout = (FrameLayout)view.findViewById(R.id.AdsFrameLayoutSearch);    
		layout.addView(adView); 
		AdRequest request = Commons.GetAds(getActivity());
		adView.loadAd(request);*/
		
		// Getting reference to the TextView of the Fragment
		searchIcon=(ImageView) view.findViewById(R.id.imgViewSearchIcon);
		autoCompleteTextView=(AutoCompleteTextView) view.findViewById(R.id.autoCompleteTextView);
		btnSearch=(ImageButton) view.findViewById(R.id.btnSearch);
		rg=(RadioGroup) view.findViewById(R.id.radioGroup1);
		rb=(RadioButton) view.findViewById(R.id.radioExhibiorName);
		dbHelper=EFairEmallApplicationContext.getDatabaseHelper();
		//get the readable database
		dbHelper.openDatabase(DatabaseHelper.READMODE);
		dbHelper.isTablesEmpty();
		btnSearch.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				displaySearchedResult(v);
			}
		});
       searchIcon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				displaySearchedResult(v);
			}
		});
       autoCompleteTextView.setOnKeyListener(new OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if ((event.getAction() == KeyEvent.ACTION_DOWN)  && (keyCode==KeyEvent.KEYCODE_ENTER))
		        {
					InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		            displaySearchedResult(v);
		               return true;
		                
		        }
				return false;
			}
	    });
		//Calling method
        autoCompleteTextViewHandling();	
		return view;
	}
	/**************** Handling autoCompleteTextView **************************
	 * Added by Akshay kumar
	 * ****************/
	public void autoCompleteTextViewHandling()
	{     
		    exhibitor=dbHelper.getAllExhibNameFromExhibTable();
			productName=dbHelper.getAllProductNameFromProductTable();
			hallNo=dbHelper.selectAllHallNo();
			languages=dbHelper.selectAllLangName();
			ExhibitorName=new ArrayList<String>();
			for(int i=0;i<exhibitor.size();i++)
			{
				ExhibitorName.add(exhibitor.get(i).getExhibitorName());
				
			}
			
			adapter = new ArrayAdapter<String> (getActivity(),R.layout.autocomplete_dropdown_list_item,ExhibitorName);  
			 autoCompleteTextView.setThreshold(1);
			 autoCompleteTextView.setAdapter(adapter);
			 rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
	            public void onCheckedChanged(RadioGroup arg0, int id) {
	        	    rb=(RadioButton) view.findViewById(id);
	        		selectedOption=rb.getText().toString();
	        		/*********************************************************** 
	        		 * Refreshing autoCompleteTextView when change choice after type text into 
	        		 * autoCompleteTextView control by getText and setText method before and after 
	        		 * notifyDataSetChanged called
	        		 * *************************************************************/
	        	    //txt=autoCompleteTextView.getText().toString();
	        		autoCompleteTextView.setText("");
	        			  try{   if(adapter!= null)
 	        		            {
	        			    	if(selectedOption.equalsIgnoreCase("Exhibitor Name"))
	        	        		{  
	        			    	  autoCompleteTextView.setHint(R.string.enter_exbi_name);
	        	      		       adapter.clear();
	        	      		     
	        	      		       for(int i=0;i<exhibitor.size();i++)
	        	      				{  
	        	      					adapter.add(exhibitor.get(i).getExhibitorName());
	        	      				}
	        	      		       adapter.notifyDataSetChanged ();
	        	      		       //autoCompleteTextView.setText(txt); 
	        	      		      
	        	        		}else if(selectedOption.equalsIgnoreCase("Product Name"))
	        	        		{    autoCompleteTextView.setHint(R.string.enter_product_name);
	        	        		      adapter.clear();
	        	        		      for(int i=0;i<productName.size();i++)
	        	        				{  
	        	        					adapter.add(productName.get(i));
	        	        				}
	        	        		      adapter.notifyDataSetChanged ();
	        	        		      //autoCompleteTextView.setText(txt);
	        	        		      Log.i("EMALL", "we are in product="); 
	        	        		    
	        	        		}else if(selectedOption.equalsIgnoreCase("Hall Number"))
		        	        		   {  autoCompleteTextView.setHint(R.string.enter_hall_no);
		        	        		      adapter.clear();
		        	        		      for(int i=0;i<hallNo.size();i++)
		        	        				{  
		        	        					adapter.add(hallNo.get(i));
		        	        				}
		        	        		      adapter.notifyDataSetChanged ();
		        	        		      //autoCompleteTextView.setText(txt);
		        	        		      Log.i("EMALL", "we are searching hall number wise"); 
		        	        		    
		        	        		} else if(selectedOption.equalsIgnoreCase("Tag Sase Search"))
		        	        		   {    
		        	        		      adapter.clear();
		        	        		      //adding all data
		        	        		      for(int i=0;i<exhibitor.size();i++)
			        	      				{  
			        	      					adapter.add(exhibitor.get(i).getExhibitorName());
			        	      				}
		        	        		      for(int i=0;i<productName.size();i++)
		        	        				{  
		        	        					adapter.add(productName.get(i));
		        	        				}
		        	        		      for(int i=0;i<hallNo.size();i++)
		        	        				{  
		        	        					adapter.add(hallNo.get(i));
		        	        				}
		        	        		      for(int i=0;i<languages.size();i++)
		        	        				{  
		        	        					adapter.add(languages.get(i));
		        	        				}
		        	        		      adapter.notifyDataSetChanged ();
		        	        		      //autoCompleteTextView.setText(txt);
		        	        		      Log.i("EMALL", "we are searching hall number wise"); 
		        	        		    
		        	        		} 
	        	        		   else{   
	        	        			   
		        	        		      adapter.clear();
		        	        		      for(int i=0;i<languages.size();i++)
		        	        				{  
		        	        					adapter.add(languages.get(i));
		        	        				}
		        	        		      adapter.notifyDataSetChanged ();
		        	        		      //autoCompleteTextView.setText(txt);
	        	        		   }
	        			    	 }
	                             }
	        			    	catch (Exception e) {
	        						// TODO: handle exception
	        	        			Log.i("EMALL", "Exception="+e);
	        					}
	        		  Log.i("EMALL", "selectedOption="+selectedOption); 
	            }
	            
	          });	
	}
	/**************** This Method is to invoke other activity (Called in xml)******************/
	public void displaySearchedResult(View v){
		
		searchString=autoCompleteTextView.getText().toString();
		int id=rg.getCheckedRadioButtonId();
		RadioButton rb=(RadioButton)view.findViewById(id);
		selectedOption=rb.getText().toString();
		if(searchString.equalsIgnoreCase("")){
			if(selectedOption.equalsIgnoreCase("Product Name"))
			  {
				Toast.makeText(getActivity(),"Please enter Product Name in search box",Toast.LENGTH_SHORT).show();
				return;
			  }else if(selectedOption.equalsIgnoreCase("Hall Number"))
			  {
				  Toast.makeText(getActivity(),"Please enter Holl No. in search box",Toast.LENGTH_SHORT).show();
				  return;
			  }
			
		}
		
		//Context fm =(getActivity().getContext());
		FragmentTransaction ft = ((FragmentActivity)getActivity()).getSupportFragmentManager().beginTransaction();
        
        SearchResultFragment searchResultFragment = new SearchResultFragment();
        ft.replace(R.id.content_frame, searchResultFragment,"SearchResultFragment");
       
        ft.commit();
        Bundle bundle=new Bundle();
        bundle.putString("SEARCH_STRING",searchString);
        bundle.putString("SEARCH_OPTION",selectedOption);
        searchResultFragment.setArguments(bundle);
        HomeActivity.searchStringOnBack=searchString;
        HomeActivity.searchOptionOnBack=selectedOption;
        HomeActivity.goForExhibitorDatails=0;
		
	}
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
//		Commons.getMyTracker(getActivity(), "SearchFragment");
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
