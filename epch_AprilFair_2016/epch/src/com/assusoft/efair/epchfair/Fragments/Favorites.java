package com.assusoft.efair.epchfair.Fragments;

import java.util.ArrayList;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.graphics.drawable.AnimationDrawable;
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
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.assusoft.eFairEmall.databaseMaster.DatabaseHelper;
import com.assusoft.eFairEmall.entities.Exhibitors;
import com.assusoft.eFairEmall.util.Util;
import com.epch.efair.delhifair.EFairEmallApplicationContext;
import com.epch.efair.delhifair.HomeAcitityFirst;
import com.epch.efair.delhifair.HomeActivity;
import com.epch.efair.delhifair.R;

public class Favorites extends Fragment{
	EditText listFilter;
	ListView listview;
	Button btnAdd;
	DatabaseHelper dh;
	ArrayList<Exhibitors> exhibitorList;
	ExbListViewAdapter adapter;
	FrameLayout banner;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			  ViewGroup container,   Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v=inflater.inflate(R.layout.favorites,container,false);
		listFilter=(EditText) v.findViewById(R.id.productSearchBoxSearch);
		listview=(ListView) v.findViewById(R.id.productListViewSearch);
		btnAdd=(Button) v.findViewById(R.id.btnFavorites);
		
		
		banner=(FrameLayout) v.findViewById(R.id.AdsFrameLayout);
		HomeAcitityFirst.adLoader.showBanner(banner);
	 	/*AnimationDrawable amin=(AnimationDrawable) banner.getBackground();
	 	amin.start();*/
	 	
		dh=EFairEmallApplicationContext.getDatabaseHelper();
		exhibitorList=new ArrayList<Exhibitors>();
		btnAdd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				Util.copyDatabase(getActivity(), DatabaseHelper.DBNAME);
//				Util.writeKeyHash(getActivity());

				if(dh.getAllExhibNameFromExhibTable().isEmpty()){
					Toast.makeText(getActivity(),"This feature will available soon",Toast.LENGTH_LONG).show();
					return;
				}else{
					FragmentTransaction ft = ((FragmentActivity)getActivity()).getSupportFragmentManager().beginTransaction();
	    	        
	    			Album fragment = new Album();
	    			Bundle bundle=new Bundle();
	    			bundle.putInt("FLAG",2);
	    			
	    			fragment.setArguments(bundle);
	    			
	    	        ft.replace(R.id.content_frame, fragment,"");
	    	        ft.addToBackStack(null);
	    	        ft.commit();
				}
			}
		});
		final View rootLayout = (LinearLayout) v.findViewById(R.id.llFavoritesID);
		rootLayout.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			
			@Override
			public void onGlobalLayout() {
				 int heightDiff = rootLayout.getRootView()
	                        .getHeight() - rootLayout.getHeight();
				 int heightView = rootLayout.getHeight();
                 int widthView = rootLayout.getWidth();
                 if (1.0 * widthView / heightView > 1) {
	               // if (heightDiff > 100) { // if more than 100 pixels, its
	                    if(banner.getVisibility()==View.VISIBLE)
	                    	banner.setVisibility(View.GONE);
	                } else {
	                    if(banner.getVisibility()==View.GONE)
	                    	banner.setVisibility(View.VISIBLE);
	                }
			}
		});
		
		//resize layout when soft input keyboard open 
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		return v;
	}// end of onCreateView method
	
	
	@SuppressLint("InlinedApi")
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		//removing effect of softInput keyboard for next layout.
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
	}


	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		HomeActivity.setTitle("My Favourites");
		dh.openDatabase(DatabaseHelper.WRITEMODE);
		exhibitorList=dh.getAllBookMark();
		if(exhibitorList==null){
			noResultFound();
			return;
		}
		if(exhibitorList.size()>0){
			adapter=new ExbListViewAdapter(getActivity(),exhibitorList,listFilter,true);
			listview.setAdapter(adapter);
		}else{
			noResultFound();
			return;
		}
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
		
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),R.layout.single_list_item, noresultFound);
		listview.setAdapter(adapter);
		
	}
	


}
