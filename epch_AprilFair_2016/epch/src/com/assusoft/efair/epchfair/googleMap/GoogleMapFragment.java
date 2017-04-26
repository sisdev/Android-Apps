package com.assusoft.efair.epchfair.googleMap;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
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
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class GoogleMapFragment extends Fragment implements LocationListener {
	Context context;
	ListView listView;
	GoogleMap  gMap=null;
	//GeoPoint myLocation;
	LocationManager lManager;
	Location location;
	LatLng target;//this is current location
	LatLng newDelhi;//is used if location is unavailable
	GPSTracker gpsTracker;  
    
    private InternetConnectionDetector icd;
	boolean flag=false; 
	 List<RowItem> rowItems;
	 Fragment mapFragment;
	private PopupWindow pwindo;
	ArrayList<String> items;
	// Spinner places;
    TextView tvDownArrow;
    ImageButton btnClosePopup;
    ListView popupLv;
    View v; 
    
   // LayoutInflater inflater;
    
    
	//@SuppressWarnings("static-access")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		 Log.i("BOOKFAIR","HomeActivity.gMApFlag="+HomeActivity.gMApFlag);
		 if (v != null) {
		        ViewGroup parent = (ViewGroup) v.getParent();
		        if (parent != null)
		            parent.removeView(v);
		        if(!HomeActivity.gMApFlag)
				 {
					 v = inflater.inflate(R.layout.places, container, false);
					 Log.i("EFAIR","Rturn from flag view");
					 HomeActivity.gMApFlag=true;
					return v;
				 }
		        return v;
		    }
		 
	    try{
	    	v = inflater.inflate(R.layout.places, container, false);
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
	    
	   FrameLayout banner=(FrameLayout) v.findViewById(R.id.AdsFrameLayout);
	   banner.setBackgroundDrawable(HomeAcitityFirst.animatin);
		HomeAcitityFirst.animatin.setOneShot(false);
		HomeAcitityFirst.animatin.start();
		   /*AnimationDrawable amin=(AnimationDrawable) banner.getBackground();
		   amin.start();*/
		 
		 Log.i("EFAIR","Loading view");
		return v;
	}
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
//		Commons.getMyTracker(getActivity(), "GoogleMapFragment");
		tvDownArrow=(TextView)v.findViewById(R.id.tvDownRow);
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
					Toast.makeText(getActivity(),"Internet is not connected", Toast.LENGTH_LONG).show();
				}else{
					 Toast.makeText(getActivity(), "Map is loading...",Toast.LENGTH_SHORT).show();
				}
				FragmentManager fmanager=getActivity().getSupportFragmentManager();
				mapFragment= fmanager.findFragmentById(R.id.placeFragement);
				SupportMapFragment supportmapfragment = (SupportMapFragment)mapFragment;
				
				Log.i("BOOKFAIR","fmanager="+mapFragment);
				Log.i("BOOKFAIR","fragment="+mapFragment);
				Log.i("BOOKFAIR","supportmapfragment111="+supportmapfragment);
		        try{ if(supportmapfragment!=null)
		              {
		        	   gMap =gMap!=null?gMap: supportmapfragment.getMap();
		        	   Log.i("BOOKFAIR","groupPosition="+supportmapfragment);
		              }
		        }catch(Exception e){
		        	
		        	e.printStackTrace();
		        	return ;
		        }
		     
		        // Getting Map for the SupportMapFragment
   	         
		          //setting the maptype
		        try{
				        gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
				        //disabling the zoom control button from map
				        gMap.getUiSettings().setZoomControlsEnabled(true);
				        gMap.getUiSettings().setZoomGesturesEnabled(true);
				        //set the default location
				        newDelhi=new LatLng(28.461800f,77.499904f);		        	
				    	gMap.moveCamera( CameraUpdateFactory.newLatLngZoom(newDelhi , 18.0f) ); 
				    	setMarkerToMap(newDelhi);
				    	gMap.setMyLocationEnabled(true);
				    	lManager=(LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
				        //location=lManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
				    	location=lManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
				    	
				        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity());
				        if(status!=ConnectionResult.SUCCESS){ // Google Play Services are not available
		
				        	int requestCode = 10;
				            try{
				            	Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, getActivity(), requestCode);
				            	dialog.setCancelable(true);
				            	dialog.show();
				            }catch(Exception e){
				            	e.printStackTrace();
				            }
		
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
						        	target=newDelhi;//new LatLng(location.getLatitude(), location.getLongitude());
						        	
						        	gMap.moveCamera( CameraUpdateFactory.newLatLngZoom(target , 18.0f) ); 
						        	
						        	
						        	setMarkerToMap(target);
						        	
						        	
						        	
						        	
						        }
						        else {
						        	
						           if(lManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
						        	lManager.requestLocationUpdates(
						                    lManager.NETWORK_PROVIDER, 500L, 250.0f, this);
						        }
				           }
						String[] placesItem=getResources().getStringArray(R.array.googleMapItems);
						rowItems = new ArrayList<RowItem>();
				        for (int i = 0; i < placesItem.length; i++) {
				            RowItem item = new RowItem(placesItem[i]);
				            rowItems.add(item);
				        }
		        
		        }catch(Exception e){
		        	e.printStackTrace();
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
	private void setMarkerToMap(LatLng latlng){
		gMap.clear();//clear all the marker of google map
		MarkerOptions markerOption=new MarkerOptions();
		//add the custom icon to marker
		markerOption.icon(BitmapDescriptorFactory.fromResource(R.drawable.you));
		//set the position of marker to map
		markerOption.position(latlng);
		markerOption.anchor(0.55f,0.6f);
		//This will be displayed on taping the marker
       //add marker to the map	 
		 gMap.addMarker(markerOption);
	}
	
    private void goToNextActivity(LatLng target,String type,String name){
    	if(location==null)
        {   
    		
        	return;
        }
    	FragmentTransaction ft = ((FragmentActivity)getActivity()).getSupportFragmentManager().beginTransaction();
        PlacesList placesList=new PlacesList();
    	
      
       
       //...
        Bundle bundle=new Bundle();
        bundle.putDouble("LATITUDE",  target.latitude);
        bundle.putDouble("LONGITUDE",  target.longitude);
        bundle.putString("TYPE",type);
        bundle.putString("NAME",name);
          //set Fragmentclass Arguments
       // Fragmentclass fragobj=new Fragmentclass();
        placesList.setArguments(bundle);
        ft.replace(R.id.content_frame, placesList,"PlacesList");
        //ft.addToBackStack(null);
        ft.commit();
        HomeActivity.lat= target.latitude;
        HomeActivity.lng= target.longitude;
        HomeActivity.type=type;
        HomeActivity.name=name;
	}
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		this.location=location;//change the location instance
		target=new LatLng(location.getLatitude(),location.getLongitude());ConnectivityManager cm =(ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        
       NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
       boolean isConnected = activeNetwork != null &&
                             activeNetwork.isConnectedOrConnecting();
       if(!isConnected)
       {
       	Toast.makeText(getActivity(), "Internet is not connected", Toast.LENGTH_LONG).show();
       }
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
		
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		HomeActivity.setTitle("Nearby");
		
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
		int popoupHieght=(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 390,resources.getDisplayMetrics());
	    LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.popup_window_places,(ViewGroup)getActivity().findViewById(R.id.popup_element));
		pwindo = new PopupWindow(layout,popupWidth,popoupHieght, true);
		pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);

		btnClosePopup = (ImageButton) layout.findViewById(R.id.btn_close_popup);
	    popupLv = (ListView) layout.findViewById(R.id.popupListView);
	    pwindo.setOutsideTouchable(true);
	    //pwindo.showAsDropDown(v);
	    pwindo.update();
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
				// TODO Auto-generated method stub
				Log.i("BOOKFAIR", "item clicked popopup windoe="+pos);
			try{
				if(pos==0)
		     	{ pwindo.dismiss();
				   if(location!=null){
					   goToNextActivity(target, "atm","atm");
					  
					}else{
						 goToNextActivity(newDelhi, "atm","atm");
						Toast.makeText(getActivity(), "your current location is not found",Toast.LENGTH_SHORT).show();
						return;
					}
		     	}
			    if(pos==4)
	     	   {    pwindo.dismiss();
			    	if(location!=null){
						 goToNextActivity(target, "police","police");
					}else{
						 goToNextActivity(newDelhi, "police","police");
						Toast.makeText(getActivity(), "your current location is not found",Toast.LENGTH_SHORT).show();
						return;
					}
	     	   }
			    if(pos==2)
		     	{   pwindo.dismiss();
			    	if(location!=null){
						
						   goToNextActivity(target, "restaurant","restaurant");	   
						   
					}else{
						goToNextActivity(newDelhi, "restaurant","restaurant");	   
						Toast.makeText(getActivity(), "your current location is not found",Toast.LENGTH_SHORT).show();
						return;
					}
		     	}
			    if(pos==1)
		     	{
			    	if(location!=null){
			    		pwindo.dismiss();
						goToNextActivity(target, "hospital","medical");	   
						  
					}else{
						goToNextActivity(newDelhi, "hospital","medical");	   
						Toast.makeText(getActivity(), "your current location is not found",Toast.LENGTH_SHORT).show();
						return;
					}
					
		     	}
			    if(pos==5)
		     	{   pwindo.dismiss();
			    	if(location!=null){
						
						goToNextActivity(target, "currency+exchange","currency exchange");	   
					}else{
						goToNextActivity(newDelhi, "currency+exchange","currency exchange");	 
						Toast.makeText(getActivity(), "your current location is not found",Toast.LENGTH_SHORT).show();
						return;
					}
		     	}
			    if(pos==3)
		     	{   pwindo.dismiss();
			    	if(location!=null){
						goToNextActivity(target, "historical+places", "attraction");
						
					}else{
						goToNextActivity(newDelhi, "historical+places", "attraction");
						
						Toast.makeText(getActivity(), "your current location is not found",Toast.LENGTH_SHORT).show();
						return;
					}
		     	}
			}catch(Exception e){
				e.printStackTrace();
			}
			}
		});
		}
	private OnClickListener cancel_button_click_listener = new OnClickListener() {
		public void onClick(View v) {
		try{
			pwindo.dismiss();
		}catch(Exception e){
			e.printStackTrace();
		}

		}
		};
		public void dismiss() {
			try{
				pwindo.dismiss();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
}
