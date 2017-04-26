package com.assusoft.efair.epchfair.Fragments;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

import com.epch.efair.delhifair.R;

import android.annotation.SuppressLint;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
  
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.assusoft.eFairEmall.databaseMaster.DatabaseHelper;
import com.assusoft.eFairEmall.entities.PhotoShoot;
import com.assusoft.eFairEmall.entities.Products;
import com.epch.efair.delhifair.EFairEmallApplicationContext;
import com.epch.efair.delhifair.HomeAcitityFirst;
import com.epch.efair.delhifair.HomeActivity;

public class PhotoShootNotes extends Fragment implements OnItemSelectedListener{
	EditText notes;
	Button btnsave,btnedit;
	Bundle bundle;
	PhotoShoot photo;
	Spinner spnProduct;
	ArrayList<Products> arrayProduct;
	int pos;
	boolean posFlag;
	FrameLayout banner;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater,
			  ViewGroup container,   Bundle savedInstanceState) {
		View v=inflater.inflate(R.layout.photoshoot_notes,container,false);
		
		notes		= (EditText) v.findViewById(R.id.editNotes1);
		btnsave		= (Button) v.findViewById(R.id.btnSaveNotes);
		btnedit		= (Button) v.findViewById(R.id.btnEditNotes);
		spnProduct	= (Spinner) v.findViewById(R.id.spinnerProduct);
		
		banner=(FrameLayout) v.findViewById(R.id.AdsFrameLayout);
		HomeAcitityFirst.adLoader.showBanner(banner);
//		AnimationDrawable amin=(AnimationDrawable) banner.getBackground();
//		   amin.start();
		
		bundle=getArguments();
		photo=bundle.getParcelable("PHOTOSHOOT");
		if(photo.getNotes()==null){
			btnedit.setEnabled(false);
			
		}else{
			btnsave.setEnabled(false);
			
			notes.setText(photo.getNotes());
			notes.setEnabled(false);
		}
		
//product name selection
		DatabaseHelper dh;
		dh=EFairEmallApplicationContext.getDatabaseHelper();
		dh.openDatabase(DatabaseHelper.READMODE);
		arrayProduct = dh.getProductListOfExibitor1(String.valueOf(bundle.getInt("EXHIBITORID")));
		String[] productList;
		
		/* To handle the empty product list we add a dummy product named "Miscellaneous" which will be at position 0	*/		
		if(!arrayProduct.isEmpty())
			productList = new String[arrayProduct.size()+1]; //one is added in the product list size so that it can adjust the one miscellaneous entry
		else
			productList = new String[1];
		
		
		Iterator<Products> itr=arrayProduct.iterator(); 		// iterator object of product array list 
		int i =0;
		int id = photo.getProductId();
		 pos = 0;
		 productList[i++]="Miscellaneous";
		while(itr.hasNext()){								
			Products product = (Products)itr.next();
			productList[i] = product.getProductName().toUpperCase(Locale.getDefault());
			if(id == product.getProductId()){			//comparing with the selected product id
				pos=i;									//getting position of selected item
			}
			 //Log.i("EPCH","position of product- "+pos+"\nProduct name "+product.getProductId());
			i++;			
		}
		spnProduct.setSelection(pos);
		posFlag = false;
		spnProduct.setOnItemSelectedListener(this);
		
		 ArrayAdapter<String> aaProduct = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,productList); 
		 aaProduct.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		 spnProduct.setAdapter(aaProduct);
		 
		btnsave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//update the notes data
				if(notes.getText().toString().equalsIgnoreCase("")){
					Toast.makeText(getActivity(), getString(R.string.toast_msg_all_field_complusory), Toast.LENGTH_SHORT).show();
					return;
				}
				photo.setNotes(notes.getText().toString());
				Log.i("EXPO", "Position "+spnProduct.getSelectedItemPosition());
				//To handle dummy product position we insert this 
				if(spnProduct.getSelectedItemPosition()==0)
					photo.setProductId(0);
				else	//To handle the position of selected product 1 is subtracted...
					photo.setProductId(arrayProduct.get(spnProduct.getSelectedItemPosition()-1).getProductId());
				DatabaseHelper dbh=EFairEmallApplicationContext.getDatabaseHelper();
				dbh.insertPhotoShoot(photo, false);
				getActivity().getSupportFragmentManager().popBackStack();
			}
		});
		btnedit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				notes.setEnabled(true);
				btnedit.setEnabled(false);
				btnsave.setEnabled(true);
			}
		});
		
		final View activityRootView = (RelativeLayout) v.findViewById(R.id.rlPhotoShototNote);
		 activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			
			@Override
			public void onGlobalLayout() {
				
				 int heightView = activityRootView.getHeight();
                int widthView = activityRootView.getWidth();
                if (1.0 * widthView / heightView > 1) {
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
	
	@SuppressLint("InlinedApi")
	@Override
	public void onPause() {
		super.onPause();
		HomeActivity.hideSoftKeyboard(getActivity());
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
	}
	@SuppressLint("InlinedApi")
	@Override
	public void onResume() {
		super.onResume();
		HomeActivity.setTitle("Notes");
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
	}
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
	
		//spnProduct.setSelection(position);
		if(posFlag){
			spnProduct.setSelection(position);
		}
		else{
			spnProduct.setSelection(pos);
			posFlag = true;
		}
		Log.i("EPCH","id"+pos+"parent"+position); 
		
		//pos = position;
	}
	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		
		
	}
}
