package com.assusoft.efair.epchfair.Fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;

import com.assusoft.tmxloader.library.TMXLoader;
import com.assusoft.tmxloader.library.TileMapData;
import com.assusoft.tmxloader.library.TouchImageView;
import com.epch.efair.delhifair.Commons;
import com.epch.efair.delhifair.HomeAcitityFirst;
import com.epch.efair.delhifair.HomeActivity;
import com.epch.efair.delhifair.ImageUtils;
import com.epch.efair.delhifair.R;
import com.epch.efair.delhifair.StorageHelper;

public class VenueMapWithLocation extends Fragment{

	TouchImageView touch;
	String fileName;
	String mapName;
	Button btnGetRoute;
	int exhibitorId;
	public static String CSVMAPFILE_EXTENTION=".assus";
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v=inflater.inflate(R.layout.venue_map_with_location,container,false);

		touch=(TouchImageView) v.findViewById(R.id.imgMap1);
		btnGetRoute=(Button) v.findViewById(R.id.btnvenueGetRoute1);
		
		Bundle bundle=getArguments();
		String xloc=bundle.getString("XLOCATION"),yloc=bundle.getString("YLOCATION");
		exhibitorId=bundle.getInt("EXHIBITORID");
		FrameLayout banner = (FrameLayout) v.findViewById(R.id.AdsFrameLayout);
		HomeAcitityFirst.adLoader.showBanner(banner);
		Log.i("TIMEV","xloc "+xloc +" and yloc " +yloc);
		int x1,y1;
		if(xloc.contains(".")){
			
			 x1=(int) Float.parseFloat(xloc);
			 Log.i("TIMEV","xloc "+x1);
			
		}else{
			x1=Integer.parseInt(xloc);
			Log.i("TIMEV","xloc "+x1);
		}
		if(yloc.contains(".")){
			y1=(int) Float.parseFloat(yloc);
			Log.i("TIMEV","yloc "+y1);
		}else{
			y1=Integer.parseInt(yloc);
			Log.i("TIMEV","yloc "+y1);
		}
		final int  x=x1*16+8,y=y1*16+8;
		fileName=bundle.getString("FILEPATH").replaceAll(".jpg",CSVMAPFILE_EXTENTION);//latter change it "floor2_1024.tmx";
		mapName=fileName;
		createMap(fileName, x,y,55,80);
		//getting image from mobile storage
		/*String filePath;
		if(StorageHelper.isExternalStorageAvailableAndWriteable())
		{
		String base = Environment.getExternalStorageDirectory().getAbsolutePath().toString();
		filePath = "file://"+ base + "/packplusData/";
		}else{
			filePath="file:///data/data/com.assusoft.efair.packplus/files/";
		}
		fileName=filePath+fileName;
        new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				wv.loadUrl("javascript:setMarkerLocation(\""+x+"\",\""+y+"\",\""+cPosX+"\",\""+cPosY+"\")"); 
				//wv.loadUrl("javascript:setMarkerLocation(\""+x+"\",\""+y+"\")"); 
				wv.loadUrl("javascript:setImageSrc(\""+fileName+"\")"); 
				Log.i("WEB_DATA", "javascript:setImageSrc(\""+fileName+"\")");
			}
		}, 3000);*/
		
		btnGetRoute.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Bundle data=new Bundle();
				data.putInt("EXHIBITORID",exhibitorId);
				
                FragmentTransaction ft = ((FragmentActivity)getActivity()).getSupportFragmentManager().beginTransaction();
		        
		        To_From fragment = new  To_From();
		        fragment.setArguments(data);
		        ft.replace(R.id.content_frame, fragment);
		        ft.addToBackStack(null);
		        ft.commit();
				
			}
		});
		
		
		return v;
	}
	
	
	
		 @Override
		public void onResume() {
			// TODO Auto-generated method stub
			super.onResume();
			HomeActivity.setTitle("Location");
//			Commons.getMyTracker(getActivity(), "VenueMapWithLocation");
			//Hiding key
			InputMethodManager inputManager = (InputMethodManager) getActivity()
		            .getSystemService(Context.INPUT_METHOD_SERVICE);
		    //check if no view has focus:
		    View v=getActivity().getCurrentFocus();
		    if(v==null)
		        return;
		    inputManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			checkMap();
		}
		
       public void checkMap()
		{  ImageUtils imageUtil=new ImageUtils(getActivity());
		   boolean isImageDownloaded=false;
		     if(StorageHelper.isExternalStorageAvailableAndWriteable())
				{
		    	 isImageDownloaded=imageUtil.isFileAvailabeInExternalStorage(mapName);
				}else{
				 isImageDownloaded=imageUtil.isFileAvailabeInInternalStorage(mapName);
				}
		     Log.i("EXPO","mapName:"+mapName);
		if(!isImageDownloaded)
		{
			new Commons().MyAlertDialog(getActivity());
		} 
			 
		}
       
       
       private void createMap(String fileName,int seedX,int seedY,int offsetX,int offsetY){
    	   
    	   // Create a Bitmap from the tilemap data
       
       	//get filename,x and y location of exhibitor
       	 // Start the parser, get back TMX data object
          TileMapData t = TMXLoader.readTMX(fileName, getActivity());
          System.out.println("No. of tilesets: "+ TMXLoader.noOfTilesets(t));
          
	      Bitmap  mapImage = TMXLoader.createBitmap(t, getActivity(), 0, t.layers.size()-1, 0, 0);
	      //TouchImageView touchImageView=new TouchImageView(getActivity());  
	        if (mapImage != null){
	        	
	        	// touch= new Touch(this,mapImage,width, height,t);
	        	//touch=new TouchImageView(getActivity(),height,width);
	        	Log.i("EFAIR","Marker location : "+seedX);
	        	touch.setImageBitmapWithMarker(mapImage,seedX,seedY,offsetX,offsetY);
	        	
	        	
	        }
	        
	        return ;
    	   
       }
       
       
}
