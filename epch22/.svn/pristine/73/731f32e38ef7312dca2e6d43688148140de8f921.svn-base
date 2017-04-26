/**
 * @author UMESH SINGH KUSHWAHA
 */
package com.assusoft.efair.epchfair.googleMap;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.epch.efair.delhifair.HomeActivity;
import com.epch.efair.delhifair.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class RoutesFragment extends Fragment {
	 
    GoogleMap map;
    ArrayList<LatLng> markerPoints;
    Double startLat,startLng,destinationLat,destinationLng;
    Intent receiveDataTo;
    Spinner routeSelection;
    ProgressDialog pDialog;
    Polyline polylinesRef[];
    String routeDistanceTimeInfo[],routeSummaryInfo[];//it holds the distance and travelling time of a route
    List<HashMap<String,String>> sourceDestAddress;//hold the source and destination address
    String routesInfo[]; 
    TextView tvSourceAddress,tvDestinationAddress;
    RoutesFragment activity=null;
	boolean flag=false; 
	Fragment mapFragment; 
    Button btnMenu,btnBack;
    Button btnHome;
    public View view;
    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
    	if (view != null) {
	        ViewGroup parent = (ViewGroup) view.getParent();
	        if (parent != null)
	            parent.removeView(view);
	        return view;
	    }
    	view = inflater.inflate(R.layout.routes_relative_layout, container, false);
    	FrameLayout banner=(FrameLayout) view.findViewById(R.id.AdsFrameLayout);
 	   AnimationDrawable amin=(AnimationDrawable) banner.getBackground();
 	   amin.start();
//		Commons.getMyTracker(getActivity(), "RoutesFragment");
        return view;
    }
 
    
    
    @Override
    public void onStart() {
    	// TODO Auto-generated method stub
    	super.onStart();
    	//find the id of textview
    			tvSourceAddress=(TextView)view.findViewById(R.id.tvRoutesInfoSource);
    			tvDestinationAddress=(TextView)view.findViewById(R.id.tvRoutesInfoDest);
    	        
    	        //find the id of spinner
    			routeSelection=(Spinner)view.findViewById(R.id.spinnerRoute);
    	       //... receiveDataTo=getIntent();
    	        //initialize sourceDestAddress
    	        sourceDestAddress=new ArrayList<HashMap<String, String>>();
    	       
    	        // Initializing
    	        markerPoints = new ArrayList<LatLng>();
    	 
    	        // Getting reference to SupportMapFragment of the activity_main
    	        FragmentManager fmanager=getActivity().getSupportFragmentManager();
				mapFragment= fmanager.findFragmentById(R.id.routesFragement);
				SupportMapFragment supportmapfragment = (SupportMapFragment)mapFragment;
    	       
    	         try{
    	        	 // Getting Map for the SupportMapFragment
        	         map = map!=null?map:supportmapfragment.getMap();
    	        	 map.clear();
    	         }catch(Exception e){
    	        	 e.printStackTrace();
    	        	 return;
    	         }
    	        map.getUiSettings().setZoomControlsEnabled(false);
    	        if(map!=null){
    	        	
    	        	 startLat=getArguments().getDouble("CURRENTLATITUDE");
    	        	 startLng=getArguments().getDouble("CURRENTLONGITUDE");
    	        	 destinationLat=getArguments().getDouble("DESTLATITUDE");
    	        	 destinationLng=getArguments().getDouble("DESTLONGITUDE");
    	            // Enable MyLocation Button in the Map
    	            map.setMyLocationEnabled(true);// changed.....
    	            map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    	            map.getUiSettings().setMyLocationButtonEnabled(false);
    	            LatLng target = new LatLng(startLat,startLng);
    	            LatLng dest=new LatLng(destinationLat,destinationLng);

    	            MarkerOptions markerOptions = new MarkerOptions();
    	            markerOptions.draggable(false);
    	            markerOptions.position(target);
    	            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.you));
    	            markerOptions.anchor(0.55f,0.6f);
    	            map.addMarker(markerOptions);
    	           
    	            MarkerOptions markerOptions1 = new MarkerOptions();
    	            markerOptions1.draggable(false);
    	            markerOptions1.position(dest);
    	            markerOptions1.icon(BitmapDescriptorFactory.fromResource(R.drawable.destination_marker));
    	            markerOptions1.anchor(0.55f,0.6f);
    	            map.addMarker(markerOptions1);
    	           
    	            CameraPosition cp = new CameraPosition.Builder()
    	            .target(target)
    	            .zoom(16f)
    	            .build();     
    	            map.animateCamera(CameraUpdateFactory.newCameraPosition(cp));
    	         // Getting URL to the Google Directions API
    	           String url = getDirectionsUrl(target, dest);
    	           Log.i("URL", url);
    	           DownloadTask downloadTask = new DownloadTask();
    	    // Start downloading json data from Google Directions API
    	            downloadTask.execute(url);
    	            
    	        }
    	       
    	       // display only selected route on map
    	        routeSelection.setOnItemSelectedListener(new OnItemSelectedListener() {

    				@Override
    				public void onItemSelected(AdapterView<?> v, View view,
    						int arg2, long arg3) {
    					try{
    						int position=v.getSelectedItemPosition();
    						int length=polylinesRef.length;
    						//TextView	textView=(TextView) view.findViewById(R.id.tvMultiSpinner);
    						//textView.setText(routeSummaryInfo[position]);
    						//set the start and destination address according to use selection
    						HashMap<String, String> addr=sourceDestAddress.get(position);
    						tvSourceAddress.setText(addr.get("SOURCEADDRESS"));
    						tvDestinationAddress.setText(addr.get("DESTINATIONADDRESS"));
    					
    						if(length==1){
    							return;
    						}
    				
    						//textView.setTextColor(Color.BLACK);
    						if(position==1){
    							((TextView) v.getChildAt(0)).setTextColor(Color.BLUE);
    						}
    						//make selected polyline visible
    						for(int i=0;i<length;i++)
    						{
    							if(i==position){
    								polylinesRef[i].setVisible(true);
    							
    								continue;
    							}
    							polylinesRef[i].setVisible(false);
    						}
    						//tvRouteDistanceTime.setText(routeDistanceTimeInfo[position]);
    					}catch(Exception e){
    						e.printStackTrace();
    					}
    				}

    				@Override
    				public void onNothingSelected(AdapterView<?> arg0) {
    					// TODO Auto-generated method stub
    					
    				}
    			});
    	
    }
    private String getDirectionsUrl(LatLng origin,LatLng dest){
 
        // Origin of route
        String str_origin = "origin="+origin.latitude+","+origin.longitude;
 
        // Destination of route
        String str_dest = "destination="+dest.latitude+","+dest.longitude;
 
        // Sensor enabled
        String sensor = "sensor=false";
        //enable multiple route
        String alternatives="alternatives=true";
 
        // Building the parameters to the web service
        String parameters = str_origin+"&"+str_dest+"&"+sensor+"&"+alternatives;
 
        // Output format
        String output = "json";
 
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;
        Log.i("EFAIR","route url is "+url);
 
        return url;
    }
    /** A method to download json data from url */
    private String downloadUrl(String strUrl) throws IOException{
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);
         // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();
 
            // Connecting to url
            urlConnection.connect();
 
            // Reading data from url
            iStream = urlConnection.getInputStream();
 
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
 
            StringBuffer sb = new StringBuffer();
 
            String line = "";
            while( ( line = br.readLine()) != null){
                sb.append(line);
            }
 
            data = sb.toString();
 
            br.close();
 
        }catch(Exception e){
            Log.d("Exception while downloading url", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }
 
    // Fetches data from url passed
    private class DownloadTask extends AsyncTask<String, Void, String>{
 
    	 @Override
         protected void onPreExecute() {
             super.onPreExecute();
             pDialog = new ProgressDialog(getActivity());
             pDialog.setMessage("Loading route. Please wait...");
             pDialog.setIndeterminate(false);
             pDialog.setCancelable(true);
             pDialog.show();
         }
    	// Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {
 
            // For storing data from web service
            String data = "";
            try{
                // Fetching the data from web service
                data = downloadUrl(url[0]);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }
 
        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
 
            ParserTask parserTask = new ParserTask();
 
            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);
        }
    }
 
    /** A class to parse the Google Places in JSON format */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> >{
 
        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {
 
            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;
         //   String routesInfo[]; 
            try{
                jObject = new JSONObject(jsonData[0]);
                DirectionsJsonParser parser = new DirectionsJsonParser();
               
                // Starts parsing data
                routes = parser.parse(jObject);
                
                Log.i("EFAIR","number of routes "+routes.size());
                
                routesInfo=new String[routes.size()];
                routeSummaryInfo=new String[routes.size()];
                routeDistanceTimeInfo=new String[routes.size()];
                //get routes info like summary,duration and distance
                routesInfo=parser.getRoutesInfo();
                //get the route start and end address
                sourceDestAddress=parser.getRoutesStartAndEndAddress();
                //get summary info in another 
                routeSummaryInfo=splitRouteInfo(routesInfo);
                //adding routes info to spinner for route selection
                Log.i("ROUTES",Integer.toString(routesInfo.length));
                
               
            }catch(Exception e){
                e.printStackTrace();
            }
            return routes;
        }
        
        

		// Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
        	ArrayAdapter<String>routesAdapter=new ArrayAdapter<String>(getActivity(),R.layout.multi_line_spinner_for_route, routesInfo);
            routesAdapter.setDropDownViewResource(R.layout.multi_line_spinner_for_route);
            routeSelection.setAdapter(routesAdapter);
       	routeSelection.setSelection(0);
        
        	ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();
            //check the response is not zero if it is zero then exit from it
            if(result.size()==0){
            	pDialog.dismiss();
            	Toast.makeText(getActivity(), "No routes specified or your internet connection lost", Toast.LENGTH_LONG);
            	return;
            }
            polylinesRef=new Polyline[result.size()];
           //set the start and destination address
            HashMap<String,String> addr= sourceDestAddress.get(0);
            tvSourceAddress.setText(addr.get("SOURCEADDRESS"));
            tvDestinationAddress.setText(addr.get("DESTINATIONADDRESS"));
            // Traversing through all the routes
            Log.i("ROUTES","in post  "+Integer.toString(result.size()));
            for(int i=0;i<result.size();i++){
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();
 
                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);
                // fetching the route address
                
                // Fetching all the points in i-th route
                for(int j=0;j<path.size();j++){
                    HashMap<String,String> point = path.get(j);
 
                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);
 
                    points.add(position);
                }
 
                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(5);
                if(i==1){
                	 lineOptions.color(Color.BLUE);
                }
                else if(i==0){
                lineOptions.color(Color.RED);
                }
                else{
                	lineOptions.color(Color.BLACK);
                }
                //make reference to polyline and polylineoption for future use
                if(i==0){
                   polylinesRef[i]=(map.addPolyline(lineOptions));
                }
                else{
                	polylinesRef[i]=(map.addPolyline(lineOptions));
                	polylinesRef[i].setVisible(false);
                }
                
              
            }
            //set the route distance and time
            /*tvRouteDistanceTime.setText(routeDistanceTimeInfo[0]);*/
            pDialog.dismiss();
 
          }
    }
    
    
    //this method is used to split route info into summary info and distance ,time info
    public String[] splitRouteInfo(String[] routesInfo) {
		int numberofRoutes=routesInfo.length;
		String summary[]=new String[numberofRoutes];
		String splitedInfo[]=new String[2];
		for(int i=0;i<numberofRoutes;i++){
			splitedInfo=routesInfo[i].split("\n");
			summary[i]=splitedInfo[0];
			routeDistanceTimeInfo[i]=splitedInfo[1];
			
		}
		
		return summary;
	}
    //this method is used to get the distance route
    public String getRouteDistance(int position){
    	String distance[]=routeDistanceTimeInfo[position].split(" ");
    	
    	return distance[0];
    }
    public ArrayList<String>spinnerData()
    {  ArrayList<String> places=new ArrayList<String>();
    	for(int i=0;i<routesInfo.length;i++)
    	{
    		places.add(routesInfo[i]);
    	}
    	return places;
    }
    
    
    @Override
    public void onDestroy() {
    	// TODO Auto-generated method stub
    	super.onDestroy();
    	
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
		        Log.i("BOOKFAIR", "onDestroyView OF RoutFragment");
		    }
	    }catch(Exception e){
	    	e.printStackTrace();
	    }

	}
    
    @Override
    public void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	HomeActivity.setTitle("Routes");
    }
}
