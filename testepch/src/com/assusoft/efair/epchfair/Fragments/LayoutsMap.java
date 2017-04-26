package com.assusoft.efair.epchfair.Fragments;
import java.io.File;
import java.util.ArrayList;

import com.epch.efair.delhifair.R;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Environment;
  
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;

import com.assusoft.eFairEmall.entities.VenueMap;
import com.assusoft.tmxloader.library.ScalingUtilities;
import com.assusoft.tmxloader.library.TMXLoader;
import com.assusoft.tmxloader.library.TileMapData;
import com.assusoft.tmxloader.library.ScalingUtilities.ScalingLogic;
import com.assusoft.tmxloader.library.TouchImageView;
import com.epch.efair.delhifair.HomeAcitityFirst;
import com.epch.efair.delhifair.HomeActivity;
import com.epch.efair.delhifair.ImageAsyncTask;
import com.epch.efair.delhifair.StorageHelper;


public class LayoutsMap extends Fragment{

	Spinner sp;
	ArrayList<VenueMap> mapList;
	ArrayList<String> spData;
	TouchImageView img;
	String basePath;
	Bitmap mapImg;
	static final int IMAGE_WIDTH=1800,IMAGE_HEIGHT=1800;
	@Override
	public View onCreateView(LayoutInflater inflater,
			  ViewGroup container,   Bundle savedInstanceState) {
		View v=inflater.inflate(R.layout.layouts_map,container,false);
		sp=(Spinner) v.findViewById(R.id.splayoutmap);
		img=(TouchImageView) v.findViewById(R.id.imgLayoutmap);
		
		FrameLayout banner=(FrameLayout) v.findViewById(R.id.AdsFrameLayout);
		HomeAcitityFirst.adLoader.showBanner(banner);
//	 	   AnimationDrawable amin=(AnimationDrawable) banner.getBackground();
//	 	   amin.start();
		Bundle bundle=getArguments();
		mapList=bundle.getParcelableArrayList("DATA");
		Log.i("NUM",""+mapList.size());
		if(mapList.get(0).getType().equalsIgnoreCase(Integer.toString(Map.hall))){
			HomeActivity.setTitle("Exhibition Halls");
		}else{
			HomeActivity.setTitle("Mart Area");
		}
		
		spData=new ArrayList<String>();
		for(int i=0;i<mapList.size();i++){
			spData.add(mapList.get(i).getHallNo());
			
		}
		if(StorageHelper.isExternalStorageAvailable()){
			basePath=Environment.getExternalStorageDirectory()+File.separator+ImageAsyncTask.FOLDER_NAME+File.separator;
		
		}else{
			//basepath is internal storage
			basePath=getActivity().getFilesDir().getAbsolutePath()+File.separator;
		}
		 /*mapImg=BitmapFactory.decodeFile(basePath+mapList.get(0).getFilePath());
		 img.setImageBitmap(mapImg);*/
		Log.i("EXPO","mart file name "+basePath+mapList.get(0).getFilePath());
		
		
		
		
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item,spData);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp.setAdapter(adapter);
		sp.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int pos, long arg3) {
				String fileName=mapList.get(pos).getFilePath();
				//search data into sd card 
				//getScaledownBitmap(basePath+fileName);
				createMap(mapList.get(pos).getFilePath().replaceAll(".jpg",VenueMapWithLocation.CSVMAPFILE_EXTENTION));
				/* mapImg=BitmapFactory.decodeFile(basePath+fileName);
				 img.setImageBitmap(mapImg);*/
				
				
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		return v;
	}
	
	private void getScaledownBitmap(String imgPath){
		Bitmap unscaledBitmap = ScalingUtilities.decodeFile(imgPath,IMAGE_WIDTH,IMAGE_HEIGHT, ScalingLogic.FIT);

        if(unscaledBitmap==null){
        	return ;
        }
        // Part 2: Scale image
        Bitmap scaledBitmap = ScalingUtilities.createScaledBitmap(unscaledBitmap,IMAGE_WIDTH,IMAGE_HEIGHT, ScalingLogic.FIT);
        unscaledBitmap.recycle();

       
       img.setImageBitmap(scaledBitmap);
	}
	public void createMap(String fileName){
		TileMapData t = TMXLoader.readTMX(fileName, getActivity());
        Log.i("EXPO","No. of tilesets: "+ TMXLoader.noOfTilesets(t));
        
	      Bitmap  mapImage = TMXLoader.createBitmap(t, getActivity(), 0, t.layers.size()-1, 0, 0);
	      
	        if (mapImage != null){
	        	 Log.i("EXPO","if map is null "+ fileName);
	        	
	        	img.setImageBitmap(mapImage);
	        	
	        	
	        }
	        
	}
	
}
