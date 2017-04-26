package com.assusoft.efair.epchfair.Fragments;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.assusoft.eFairEmall.databaseMaster.DatabaseHelper;
import com.assusoft.eFairEmall.entities.MartFacilities;
import com.assusoft.efair.epchfair.googleMap.HowToReachGMap;
import com.assusoft.tmxloader.library.TouchImageView;
import com.epch.efair.delhifair.EFairEmallApplicationContext;
import com.epch.efair.delhifair.HomeAcitityFirst;
import com.epch.efair.delhifair.HomeActivity;
import com.epch.efair.delhifair.ImageAsyncTask;
import com.epch.efair.delhifair.R;

public class VenueMapImageVewFragment extends Fragment{

	private DisplayMetrics	dimenssion;
	private int				displayWidth;
	private int				displayHeight;
	private Bitmap			bitmap = null;
	private Options			options;
	private InputStream		in = null;
	public Handler handler,handler1;
	public static Runnable runnable1,runnable2;
	TouchImageView imgView;
	Button btnHowToR;
	private Spinner sp;
	private String mapFile, basePath, img_name = null;
	private ArrayList<MartFacilities> mapList;
	private ArrayList<String> spData;
	DatabaseHelper dbHelper;
	int facility_id=1000;
	
	@SuppressLint("NewApi") 
	@Override
	public View onCreateView(LayoutInflater inflater,
			  ViewGroup container,   Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.venue_map_image_view_fragment,container,false);
		sp=(Spinner) view.findViewById(R.id.splayoutmap);
		imgView = (TouchImageView) view.findViewById(R.id.webViewVenueMap);
		btnHowToR = (Button) view.findViewById(R.id.btnHowtoReach);
		
		dbHelper=EFairEmallApplicationContext.getDatabaseHelper();
		dbHelper.openDatabase(DatabaseHelper.READMODE);
		
		FrameLayout banner=(FrameLayout) view.findViewById(R.id.AdsFrameLayout);
		HomeAcitityFirst.adLoader.showBanner(banner);
		/*AnimationDrawable amin=(AnimationDrawable) banner.getBackground();
		amin.start();*/
		
		if(HomeActivity.isDelhifair){
			btnHowToR.setBackground(getResources().getDrawable(R.drawable.comman_button_ihgf_selector));
			view.findViewById(R.id.rootLayout).setBackground(getResources().getDrawable(R.drawable.bg_delhifair));
		}
		mapList =dbHelper.mapFacilites();
		spData=new ArrayList<String>();
		
		try{
			Log.i("EFAIR","MapList Size: "+mapList.size());
			for(int i=0;i<mapList.size();i++){
				spData.add("  "+mapList.get(i).getFacilityName());
				//Log.i("NUM","mapListName loop  : "+mapList.get(i).getFacilityName());
			}
			spData.add(" Select Facilities");	//Entry at last index in spList
			}catch(Exception e){
			e.printStackTrace();
			}
		
		final ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(), R.layout.custom_spinner_dropdown,spData){
			@Override
            public int getCount() {
				//Log.i("NUM","super.getCount():"+super.getCount()+"\nspData.size():"+spData.size());
                return spData.size()-1; //(super.getCount()-1) spData.size()-1 you dont display last item. It is used as hint.
            }
		};
		sp.setAdapter(adapter);
		Log.i("NUM","adapter count"+adapter.getCount());
		sp.setSelection(adapter.getCount());	//Default Text to show at Spinner
		Log.i("NUM","adapter count"+adapter.getCount());
		try{
			if(HomeActivity.isDelhifair){
				in=new FileInputStream(new File(Environment.getExternalStorageDirectory()
						+File.separator+ImageAsyncTask.FOLDER_NAME+File.separator
						+dbHelper.getFileNameOfVenueMap(5)));
			}else{
				in=new FileInputStream(new File(Environment.getExternalStorageDirectory()
						+File.separator+ImageAsyncTask.FOLDER_NAME+File.separator
						+dbHelper.getFileNameOfVenueMap(4)));
			}
			dimenssion	= new DisplayMetrics();
			options		= new Options();
			
			getActivity().getWindowManager().getDefaultDisplay().getMetrics(dimenssion);
			displayWidth	= dimenssion.widthPixels;
			displayHeight	= dimenssion.heightPixels;
			
			options.inSampleSize = ProductImageLargeViewFragment
									.calculateOption(options.outWidth, options.outHeight, displayWidth, displayHeight);
			bitmap			= BitmapFactory.decodeStream(in, null, options);
//			Log.i("NUM","img View Initially called"+bitmap);
			imgView.createBitmap(bitmap);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		sp.setOnItemSelectedListener(new OnItemSelectedListener() {
			
			
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				/*if(pos==adapter.getCount()-1){
						Log.i("NUM","in If loop");
						return;
					}*/
					//Remove the callback of handler threads, when an item is selected.
					try{
				    	handler.removeCallbacks(runnable2);
					    handler1.removeCallbacks(runnable1);
						handler.removeCallbacks(runnable1);
					    handler1.removeCallbacks(runnable2);
					    runnable1=null;
					    runnable2=null;
				    }catch(Exception e){
				    	e.printStackTrace();
				    }
					try{
						facility_id = mapList.get(pos).getFacilityId();
						img_name = mapList.get(pos).getImgName();
//						Log.i("EFAIR","Facility id and img_name from db and set bundle: "+facility_id+" and "+img_name);
						
						//Initializing handlers
						handler=new Handler();
						handler1 = new Handler();
//						Log.i("NUM","In OnTouchView"+bitmap+handler+ handler1+img_name+facility_id);
						imgView.createBitmap(bitmap,handler, handler1,img_name,facility_id);
					}catch(Exception e){
						e.printStackTrace();
					}

				
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});

		btnHowToR.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FragmentTransaction ft = ((FragmentActivity)getActivity()).getSupportFragmentManager().beginTransaction();			       
		        HowToReachGMap fragment = new HowToReachGMap();       		      
		        ft.replace(R.id.content_frame, fragment,"HowToReachGMap");
		        ft.addToBackStack(null);		       
		        ft.commit();
				
			}
		});
		return view;
	}
	@Override
	public void onResume() {
		super.onResume();
		HomeActivity.setTitle("Venue");
	}
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		try{
		handler.removeCallbacks(runnable1);
		handler.removeCallbacks(runnable2);
		runnable1=null;
		runnable2=null;
		}catch(NullPointerException e){
			e.printStackTrace();
		} 
	}
	
	@Override
	public void onPause() {
	    super.onPause();
	}
}


