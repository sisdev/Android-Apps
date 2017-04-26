package com.assusoft.efair.epchfair.googleMap;
import org.w3c.dom.Document;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.assusoft.eFairEmall.internetAndGpsMaster.InternetConnectionDetector;
import com.epch.efair.delhifair.HomeActivity;
import com.epch.efair.delhifair.ImageDownloading;
import com.epch.efair.delhifair.R;
import com.epch.efair.delhifair.StorageHelper;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class VenuMapFragment extends Fragment implements LocationListener,OnMarkerClickListener {

	ProgressDialog pDialog;
	GoogleMap gMap;
	LocationManager lManager;
	Location location;
	Document document;
	 LatLng fromPosition;
     LatLng toPosition;
     MarkerOptions markerOptions,marker2;
	GMapV2GetRouteDirection v2GetRouteDirection;
	SupportMapFragment supportmapfragment;
	Double startLat,startLng,nearestLat,nearestLng;
	TextView address;
	Button btnGo,btnSearchFrom;
	TextView nearestCabDistance;
	Fragment mapFragment; 
	boolean placeFlage=false; // is used control the call to google places api
	String urlForAtm=null;
	Intent receiveData;//is used to receive the data from getyourcab activity  , 
	public static String getImgBaseUrl;//Receive object from ImageDownloading
	public static String[] layoutes;//Receive object from ImageDownloading
	public static ImageDownloading imageDownloading; 
	 View view;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (view != null) {
	        ViewGroup parent = (ViewGroup) view.getParent();
	        if (parent != null)
	            parent.removeView(view);
	        if(!HomeActivity.gMApFlag)
			 {
	        	view = inflater.inflate(R.layout.from, container, false);
				 Log.i("EFAIR","Rturn from flag view");
				 HomeActivity.gMApFlag=true;
				return view;
			 }
	        return view;
	    }
		view = inflater.inflate(R.layout.from, container, false);
		
		
		return view;
	}
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		v2GetRouteDirection = new GMapV2GetRouteDirection();
     	//receiveData=getIntent();
		InternetConnectionDetector icd=new InternetConnectionDetector(getActivity());
		if(!icd.isConnectingToInternet()){
			Toast.makeText(getActivity(),"Internet is not connected", Toast.LENGTH_LONG).show();
		}else{
			 Toast.makeText(getActivity(), "Map is loading...",Toast.LENGTH_SHORT).show();
		}
		
		FragmentManager fmanager = getActivity().getSupportFragmentManager();
         mapFragment = fmanager.findFragmentById(R.id.mapFrom);
        supportmapfragment = (SupportMapFragment)mapFragment;
       
        try{
        	 gMap =gMap!=null?gMap: supportmapfragment.getMap();
        	
        }catch(Exception e){
        	e.printStackTrace();
        	return;
        }
        //setting the maptype
        gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        gMap.moveCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(28.619376,77.243781) , 16.5f) ); 
        //disabling the zoom control button from map
        gMap.getUiSettings().setZoomControlsEnabled(true);
        gMap.getUiSettings().setZoomGesturesEnabled(true);
        
       // int res = getActivity().getResources().getIdentifier(getActivity().getPackageName()+":"+imagePath[i], null, null);
        for(int i=0;i<HowToReachGMap.markerIconPath.length;i++){
        	 int res = getActivity().getResources().getIdentifier(getActivity().getPackageName()+":"+HowToReachGMap.markerIconPath[i], null, null);
        	gMap.addMarker(new MarkerOptions()
           .position(new LatLng(HowToReachGMap.lat[i],HowToReachGMap.lng[i]))
          .title(HowToReachGMap.title[i])
          .icon(BitmapDescriptorFactory.fromResource(res))
         );
        }
     
      gMap.setOnMarkerClickListener(this);
      //Toast.makeText(getActivity(),"Click on marker to see hall Layout", Toast.LENGTH_LONG).show();
        
	}

@Override
public boolean onMarkerClick(Marker marker) {
	// TODO Auto-generated method stub
	String imagePath;
	if(StorageHelper.isExternalStorageAvailableAndWriteable())
	{
	String base = Environment.getExternalStorageDirectory().getAbsolutePath().toString();
	imagePath = "file://"+ base + "/packplusData/";
	}else{
		imagePath="file:///data/data/com.assusoft.efair.packplus/files/";
	}
	
	String markerTitle=marker.getTitle();
	String mapFileName;
	String imageName="hall11bto12.png";
	 if(markerTitle.equalsIgnoreCase(HowToReachGMap.title[4])){
		 imageName="hall11bto12.png";
		 mapFileName=imagePath+imageName;
	 }else{
		 //8,9,10,11
		 imageName="hall8to11a.png";
		 mapFileName=imagePath+imageName;
	 }
	 Bundle bundle=new Bundle();
	 bundle.putString("IMAGEFILENAME",mapFileName);
	 bundle.putString("MAP_IMAGE_NAME",imageName);
	 FragmentTransaction ft = ((FragmentActivity)getActivity()).getSupportFragmentManager().beginTransaction();
     
     VenueMapLayoutFragment fragment = new VenueMapLayoutFragment();
		fragment.setArguments(bundle);
		
     ft.replace(R.id.content_frame, fragment,"");
     
     ft.commit();
	
	
	return false;
}

@Override
public void onResume() {
	// TODO Auto-generated method stub
	super.onResume();
//	Commons.getMyTracker(getActivity(), "VenuMapFragment");
}
@Override
public void onDestroy() {
	// TODO Auto-generated method stub
	super.onDestroy();
	
}
@Override
public void onDestroyView() {
    super.onDestroyView();

    if (this.mapFragment != null
            && getFragmentManager().findFragmentById(
                    this.mapFragment.getId()) != null) {

        getFragmentManager().beginTransaction().remove(this.mapFragment)
                .commit();
        this.mapFragment = null;
        Log.i("BOOKFAIR", "onDestroyView OF venue map");
    }

}
	
}
