package com.assusoft.efair.epchfair.googleMap;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.assusoft.eFairEmall.internetAndGpsMaster.GPSTracker;
import com.assusoft.eFairEmall.internetAndGpsMaster.InternetConnectionDetector;
import com.assusoft.efair.epchfair.Fragments.AboutListViewAdapter;
import com.assusoft.efair.epchfair.Fragments.RowItem;
import com.epch.efair.delhifair.HomeAcitityFirst;
import com.epch.efair.delhifair.HomeActivity;
import com.epch.efair.delhifair.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class HowToReachGMap extends Fragment implements OnMarkerClickListener, OnMapReadyCallback {
	Context context;
	ListView listView;
	GoogleMap  gMap=null;
	//GeoPoint myLocation;
	LocationManager lManager;
	Location location;
	SupportMapFragment supportmapfragment;
	LatLng target;//this is current location
	LatLng newDelhi;//is used if location is unavailable
	GPSTracker gpsTracker;  
	Fragment mapFragment;
    private InternetConnectionDetector icd;
	boolean flag=false; 
	 List<RowItem> rowItems;
	//FragmentTransaction ft;
	//PlacesList placesList;
	private PopupWindow pwindo;
	 ArrayList<String> items;
	// Spinner places;
    TextView tvDownArrow;
    ImageButton btnClosePopup;
    ListView popupLv;
    
  //pragti madan,gate no.5,halt,overhead tank,masjid,etc
    public static final float lat[]={28.461880f, 28.461803f};
    public static final float lng[]={77.502113f,77.498532f};
	public static final String markerIconPath[]={"drawable/you","drawable/you",
			"drawable/hall_no_10","drawable/hall_no_11","drawable/hall_no_12"}; 
	public static final String title[]={"Parking area","Registration area","Hall No.10","Hall No.11","Hall No.12"};
   // LayoutInflater inflater;
    private View view;
	//@SuppressWarnings("static-access")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (view != null) {
	        ViewGroup parent = (ViewGroup) view.getParent();
	        if (parent != null)
	            parent.removeView(view);
	        return view;
	    }
	    try {
	        view = inflater.inflate(R.layout.how_to_reach_gmap, container, false);
	    } catch (InflateException e) {
	        /* map is already there, just return view as it is */
	    }
	    
		 try{
			 FrameLayout banner=(FrameLayout) view.findViewById(R.id.AdsFrameLayout);
			 HomeAcitityFirst.adLoader.showBanner(banner);
//			 AnimationDrawable amin=(AnimationDrawable) banner.getBackground();
//			 amin.start();
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		  
		return view;
	}
	
	@SuppressLint("NewApi") 
	@Override
	public void onStart() {
		if(HomeActivity.isDelhifair){
			view.findViewById(R.id.rootLayout).setBackground(getResources().getDrawable(R.drawable.bg_delhifair));
		}
		super.onStart();
//		Commons.getMyTracker(getActivity(), "HowToReachGMap");
		tvDownArrow=(TextView)view.findViewById(R.id.tvDownRowHowtoReach);
		 tvDownArrow.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				initiatePopupWindow();
			}
		});
		
		        isGooglePlayInstalled();
				icd=new InternetConnectionDetector(getActivity());
				if(!icd.isConnectingToInternet()){
					Toast.makeText(getActivity(),"Please connect to internet,otherwise map is not loaded", Toast.LENGTH_SHORT).show();
				}
				FragmentManager fmanager =getActivity().getSupportFragmentManager();
		         mapFragment = fmanager.findFragmentById(R.id.placeFragementHowToReach);
		         supportmapfragment = (SupportMapFragment)mapFragment;
		         
		       try{
		       // gMap =gMap!=null?gMap: supportmapfragment.getMap();
				   supportmapfragment.getMapAsync(this);
		        Log.i("EXPO","Map "+gMap);
		        
		       }catch(Exception e){
		    	   Log.i("EXPO","Exception when map resume "+e+"\n"+gMap);
		    	   e.printStackTrace();
		    	   //return;
		       }
		        //setting the maptype
		     try{  
		        gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		        //disabling the zoom control button from map
		        gMap.getUiSettings().setZoomControlsEnabled(true);
		        gMap.getUiSettings().setZoomGesturesEnabled(true);
		        //set the default location
		        newDelhi=new LatLng(28.461800f,77.499904f);		        	
		    	gMap.moveCamera( CameraUpdateFactory.newLatLngZoom(newDelhi , 17.0f) ); 
		    	//setMarkerToMap();
		    	gMap.setMyLocationEnabled(true);
		    	lManager=(LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
		        location=lManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity());
		        if(status!=ConnectionResult.SUCCESS){ // Google Play Services are not available

		        	int requestCode = 10;
		            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, getActivity(), requestCode);
		            dialog.show();

		        }
		         {
			         gpsTracker=new GPSTracker(getActivity());
				         if(!gpsTracker.isGPSEnabled){
				        	 /*if(gpsTracker.isGPSAvailable){
					        	  gpsTracker.showSettingsAlert();
					        	 }*/
				        	 gpsTracker.showSettingsAlert();
				         }
				         if(gpsTracker.canGetLocation){
				        	location=gpsTracker.getLocation();
				         }
				        if (location != null){
				        	target=new LatLng(location.getLatitude(), location.getLongitude());
				        	//target=new LatLng(28.618432f,77.244786f);
				        	
				        	gMap.moveCamera( CameraUpdateFactory.newLatLngZoom(newDelhi , 17.0f) ); 
				        	
				        	
				        	//setMarkerToMap(target);
				        	
				        	
				        	
				        	
				        }
				        else {
				        	
				           
				        	/*lManager.requestLocationUpdates(
				                    lManager.NETWORK_PROVIDER, 500L, 250.0f, this);*/
				        }
		           }
				String[] placesItem=getResources().getStringArray(R.array.howtoreach);
				rowItems = new ArrayList<RowItem>();
		        for (int i = 0; i < placesItem.length; i++) {
		            RowItem item = new RowItem(placesItem[i]);
		            rowItems.add(item);
		        }
		     }catch(Exception e){
		    	 e.printStackTrace();
		    	 Log.i("EXPO","Exception "+e+"\n"+gMap);
		     }
		
	}
	double roundTwoDecimals(double d){
        DecimalFormat twoDForm = new DecimalFormat("#.######");
        return Double.valueOf(twoDForm.format(d));
     }

	private boolean isGooglePlayInstalled(){
		    int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity());
		    if(status == ConnectionResult.SUCCESS){
		        return true;
		    }else{
		        ((Dialog)GooglePlayServicesUtil.getErrorDialog(status, getActivity(),10)).show();
		    }
		    return false;
		}	
	private void setMarkerToMap(){
		gMap.clear();//clear all the marker of google map
		for(int i=0;i<lat.length;i++){
       	 int res = getActivity().getResources().getIdentifier(getActivity().getPackageName()+":"+markerIconPath[i], null, null);
       	gMap.addMarker(new MarkerOptions()
          .position(new LatLng(lat[i],lng[i]))
         .title(title[i])
         .icon(BitmapDescriptorFactory.fromResource(res))
        );
       }
    
     gMap.setOnMarkerClickListener(this);
	}
	
   
	/*@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		this.location=location;//change the location instance
		target=new LatLng(location.getLatitude(),location.getLongitude());
		setMarkerToMap(target);
		Toast.makeText(getActivity(),"location is changed",Toast.LENGTH_SHORT).show();
		Log.i("EFAIR","Location is changed");
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
		
	}*/
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		/*gMap=null;
		v=null;*/
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		HomeActivity.setTitle("How to Reach");
	}
	@Override
	public void onDestroyView() {
	    super.onDestroyView();
	    try{
		    if (this.mapFragment != null
		            && getFragmentManager().findFragmentById(
		                    this.mapFragment.getId()) != null) {

		        getFragmentManager().beginTransaction().remove(this.mapFragment)
		                .commit();
		        this.mapFragment = null;
		        Log.i("BOOKFAIR", "onDestroyView OF googmapFragment");
		    }
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
	}
    /*************************************************************	
	 * s
	 */
	private void initiatePopupWindow() {
		try {
		// We need to get the instance of the LayoutInflater
		Resources resources = getActivity().getResources();
		int popupWidth=(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300,resources.getDisplayMetrics());
		int popoupHieght=(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 290,resources.getDisplayMetrics());
	    LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.popup_window,(ViewGroup)getActivity().findViewById(R.id.popup_element));
		pwindo = new PopupWindow(layout,popupWidth,popoupHieght, true);
		pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);

		btnClosePopup = (ImageButton) layout.findViewById(R.id.btn_close_popup);
	    popupLv = (ListView) layout.findViewById(R.id.popupListView);
		btnClosePopup.setOnClickListener(cancel_button_click_listener);
		AboutListViewAdapter adapter = new AboutListViewAdapter(getActivity(),
                R.layout.about_list_item, rowItems);
		//ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, items);
		popupLv.setAdapter(adapter);
		} catch (Exception e) {
			Log.i("BOOKFAIR", "exception for popopup windoe="+e);
		e.printStackTrace();
		}
		 popupLv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				try{
					FragmentTransaction ft = ((FragmentActivity)getActivity()).getSupportFragmentManager().beginTransaction();
			        HowToReachRoutes route=new HowToReachRoutes();
			    	
			        Bundle bundle=new Bundle();
			        bundle.putDouble("CURRENTLATITUDE", target.latitude);
					bundle.putDouble("CURRENTLONGITUDE", target.longitude);
					bundle.putDouble("DESTLATITUDE",lat[pos]);
					bundle.putDouble("DESTLONGITUDE",lng[pos]);
					bundle.putInt("POSITION",pos);
			          //set Fragmentclass Arguments
			       // Fragmentclass fragobj=new Fragmentclass();
					route.setArguments(bundle);
			        ft.replace(R.id.content_frame, route,"PlacesList");
			        //ft.addToBackStack(null);
			        ft.commit();
			        pwindo.dismiss();
				}catch(Exception e){
					Toast.makeText(getActivity(),"Unable to find current location",Toast.LENGTH_LONG).show();
					 pwindo.dismiss();
					e.printStackTrace();
				}
			}
			
		});
		}
	private OnClickListener cancel_button_click_listener = new OnClickListener() {
		public void onClick(View v) {
		pwindo.dismiss();

		}
		};
		@Override
		public boolean onMarkerClick(Marker marker) {
			
/*			String imagePath;
			if(StorageHelper.isExternalStorageAvailableAndWriteable())
			{
			String base = Environment.getExternalStorageDirectory().getAbsolutePath().toString();
			imagePath = "file://"+ base + "/packplusData/";
			}else{
				imagePath="file:///data/data/com.assusoft.efair.packplus/files/";
			}
			
			String markerTitle=marker.getTitle();
			String mapFileName;
			String imageName;
			 if(markerTitle.equalsIgnoreCase(title[4])){
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
		     //ft.addToBackStack(null);
		     ft.commit();
			
			*/
			return false;
		}

	@Override
	public void onMapReady(GoogleMap googleMap) {
		gMap = googleMap ;
	}
}
