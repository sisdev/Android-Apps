package com.assusoft.efair.epchfair.Fragments;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.assusoft.eFairEmall.databaseMaster.DatabaseHelper;
import com.epch.efair.delhifair.Commons;
import com.epch.efair.delhifair.EFairEmallApplicationContext;
import com.epch.efair.delhifair.ImageAsyncTask;
import com.epch.efair.delhifair.ImageUtils;
import com.epch.efair.delhifair.R;
import com.epch.efair.delhifair.StorageHelper;

public class FloorPlanFragment extends Fragment {
	DatabaseHelper dbHelper;
	public  String[] layoutes;
	
	ImageView  btnMapFirst,btnMapSecond;
	String imagePath;
	String mapName;
	String base ;
	Bundle bundle;
	Resources res;
	String pathName;
	Bitmap bitmap;
	BitmapDrawable bd ;
	FileInputStream fi;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.floor_plan_fragment, container, false);
		btnMapFirst=(ImageView) view.findViewById(R.id.btnMapFirst);
		btnMapSecond=(ImageView) view.findViewById(R.id.btnMapSecond);
		
		/*//admob
    	AdView adView = new AdView(getActivity(), AdSize.SMART_BANNER, Commons.admobToken);    
		FrameLayout layout = (FrameLayout)view.findViewById(R.id.AdsFrameLayout);    
		layout.addView(adView); 
		AdRequest request = Commons.GetAds(getActivity());
		adView.loadAd(request);*/
		
		base = Environment.getExternalStorageDirectory().getAbsolutePath().toString();	
		bundle=new Bundle();
		dbHelper=EFairEmallApplicationContext.getDatabaseHelper();
		//get the readable database
		dbHelper.openDatabase(DatabaseHelper.READMODE);
		layoutes=dbHelper.getAllLayoutsImage2015();
		
		if(StorageHelper.isExternalStorageAvailableAndWriteable())
		{
		imagePath = "file://"+base+ "/packplusData/";
		}else{
			imagePath="file:///data/data/com.assusoft.efair.packplus/files/";
		}
	    
        
        
		btnMapFirst.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mapName=layoutes[0];
				imagePath += mapName;
				bundle.putString("FLOOR_PLAN_MAP",imagePath);
				bundle.putString("MAP_NAME",mapName);
				FragmentTransaction ft = ((FragmentActivity)getActivity()).getSupportFragmentManager().beginTransaction();
			     
				 FloorPlanWebViewFragment fragment = new FloorPlanWebViewFragment();
				 fragment.setArguments(bundle);
			     ft.replace(R.id.content_frame, fragment,"");
			     ft.commit();
			}
		});
		btnMapSecond.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mapName=layoutes[1];
				imagePath +=mapName;
				
				bundle.putString("FLOOR_PLAN_MAP",imagePath);
				bundle.putString("MAP_NAME",mapName);
				FragmentTransaction ft = ((FragmentActivity)getActivity()).getSupportFragmentManager().beginTransaction();
			     
				 FloorPlanWebViewFragment fragment = new FloorPlanWebViewFragment();
				 fragment.setArguments(bundle);
			     ft.replace(R.id.content_frame, fragment,"");
			     ft.commit();
			}
		});
		
		return view;
	}
	@SuppressLint("NewApi")
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
//		Commons.getMyTracker(getActivity(), "FloorPlanFragment");
		
		
		File folder = new File(Environment.getExternalStorageDirectory(),ImageAsyncTask.FOLDER_NAME);
		File file = new File (folder, layoutes[0]);
		try {
		    fi = new FileInputStream(file);
		    Bitmap bitmap = BitmapFactory.decodeStream(fi);
			btnMapFirst.setImageBitmap(bitmap);
			
			file = new File (folder, layoutes[1]);
			fi = new FileInputStream(file);
		     bitmap = BitmapFactory.decodeStream(fi);
		     btnMapSecond.setImageBitmap(bitmap);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
		      if (fi!= null) {
		        try {
		        	fi.close();
		        } catch (IOException e) {
		          e.printStackTrace();
		        }
		      }
		}
		checkMap();
		
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
	public void checkMap()
	{  ImageUtils imageUtil=new ImageUtils(getActivity());
	   boolean isImageDownloaded=false;
	     if(StorageHelper.isExternalStorageAvailableAndWriteable())
			{
	    	 isImageDownloaded=imageUtil.isFileAvailabeInExternalStorage(layoutes[0]);
			}else{
			 isImageDownloaded=imageUtil.isFileAvailabeInInternalStorage(layoutes[0]);
			}
	     Log.i("EXPO","mapName:"+mapName);
	if(!isImageDownloaded)
	{
		new Commons().MyAlertDialog(getActivity());
	} 
		 
	}
}
