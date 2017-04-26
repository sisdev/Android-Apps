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
import java.util.List;

import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.epch.efair.delhifair.HomeActivity;
import com.epch.efair.delhifair.R;
import com.google.android.gms.maps.model.LatLng;

public class PlacesList extends Fragment {
	ListView placeListView;
	ArrayList<String> placeList;
	public static final String PLACES_NOT_FOUND="Data not available";
	//List<Place> placesD;//hold the place object
	ProgressDialog pDialog;
	ParserTask parserTask;
	PlacesTask placesTask;
	TextView title;
	public static final String GOOGLE_PLACES_API_KEY="AIzaSyDfTKOXRSfGl-OrLos_Sr7tZo3PVY4sVU8";
	//FragmentTransaction ft;
	// RoutesFragment routsFragment;
    Button btnMenu,btnBack;
    Button btnHome;
   // public static View v;
    Bundle bundle;
    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
    	
      View  v = inflater.inflate(R.layout.places_list, container, false);
     FrameLayout banner=(FrameLayout) v.findViewById(R.id.AdsFrameLayout);
	   AnimationDrawable amin=(AnimationDrawable) banner.getBackground();
	   amin.start();
		//setContentView(R.layout.places_list);
		placeListView=(ListView)v.findViewById(R.id.lvPlacesList);
		
		placeList=new ArrayList<String>();

	   Bundle arguments = getArguments();
	   Log.i("BOOKFAIR", "arguments="+arguments+","+arguments.getDouble(("LATITUDE")));
		LatLng currentPosition=new LatLng(arguments.getDouble(("LATITUDE")),arguments.getDouble(("LONGITUDE")));
		String type=arguments.getString("TYPE");
		//set title of activity
		if(arguments!=null)
		{
		//title.setText(getArguments().getString("NAME").toUpperCase());
		}
		startBackgroundTask(currentPosition, type);
		
		/*if(HomeActivity.placesD==null){
		   startBackgroundTask(currentPosition, type);
		}else{
			parsePlacesName(HomeActivity.placesD);
			setDataToAdapter();
		}*/
		
		
	  bundle=new Bundle();
	  Context fm =(v.getContext());
	    
		//handle the click event on listview
		placeListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				//LatLng selectedItemPosition=new LatLng(placesD.get(position).getLatitude(),placesD.get(position).getLongitude());
			FragmentTransaction	ft = ((FragmentActivity)getActivity()).getSupportFragmentManager().beginTransaction();
			RoutesFragment	  routsFragment=new RoutesFragment();
		        ft.replace(R.id.content_frame,routsFragment,"RoutsFragment");
		     
		     
				bundle.putDouble("CURRENTLATITUDE", getArguments().getDouble("LATITUDE"));
				bundle.putDouble("CURRENTLONGITUDE", getArguments().getDouble("LONGITUDE"));
				bundle.putDouble("DESTLATITUDE", HomeActivity.placesD.get(position).getLatitude());
				bundle.putDouble("DESTLONGITUDE",HomeActivity.placesD.get(position).getLongitude());
				routsFragment.setArguments(bundle);	
				 ft.addToBackStack(null);
				   ft.commit();
				//Toast.makeText(getApplicationContext(), placesD.get(position).getVicinity(),Toast.LENGTH_SHORT).show();
				
			}
		});
		
       
        return v;
	}
	
	//this method is used to start the background services
		private void startBackgroundTask(LatLng latlng,String type){
			//start background process
		      //make url
		   String  url=    makeUrlForPlacesApi(latlng,type);
		     // Creating a new non-ui thread task to download Google place json data 
		        placesTask = new PlacesTask();		        			        
		        
				// Invokes the "doInBackground()" method of the class PlaceTask
		        placesTask.execute(url);
		}
			// makeUrl method is used to make the url to request Places services
		private String makeUrlForPlacesApi(LatLng geoPoint,String type){
				

				StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
				
				sb.append("location="+geoPoint.latitude+","+geoPoint.longitude);
				sb.append("&rankby=distance");
				
				if(type.equalsIgnoreCase("currency+exchange")||type.equalsIgnoreCase("historical+places")||type.equalsIgnoreCase("pragati+maidan")){
					sb.append("&keyword="+type);
					Log.i("BOOKFAIR", "type=="+type);
				}else{
					sb.append("&types="+type);
				}
				sb.append("&sensor=false");
				
				sb.append("&key="+GOOGLE_PLACES_API_KEY);
				
				Log.i("EFAIR","url is "+sb);
				return(sb.toString());
				// Log.i("Place", "url is created"+sb);
			}
			
			/** A method to download json data from url */
		    private String downloadUrl(String strUrl) throws IOException{
		        String data = "";
		        InputStream iStream = null;
		       // Log.i("Place", "downloading url"); 
		        HttpURLConnection urlConnection = null;
		        try{
		                URL url = new URL(strUrl);                
		                

		                // Creating an http connection to communicate with url 
		                urlConnection = (HttpURLConnection) url.openConnection();                

		                // Connecting to url 
		                urlConnection.connect();                
		           	 // Log.i("Place", "update requestconnection is created"); 
		                iStream = urlConnection.getInputStream();

		                BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

		                StringBuffer sb  = new StringBuffer();

		                String line = "";
		                while( ( line = br.readLine())  != null){
		                        sb.append(line);
		                       // Log.i("Place", "update requestconnection is created in while loop"); 
		                }

		                data = sb.toString();
		           	  //  Log.i("Place", "in download url");
		                br.close();

		        }catch(Exception e){
		                Log.d("Exception while downloading url", e.toString());
		        }finally{
		                iStream.close();
		                urlConnection.disconnect();
		        }

		        return data;
		    }         

			
			/** A class, to download Google Places */
			private class PlacesTask extends AsyncTask<String, Integer, String>{

				String data = null;
			     
				 @Override
		         protected void onPreExecute() {
		             super.onPreExecute();
		             pDialog = new ProgressDialog(getActivity());
		             pDialog.setMessage(" Please wait...");
		             pDialog.setIndeterminate(true);
		            // pDialog.setIndeterminateDrawable(getResources().getDrawable(R.drawable.beachcar));
		             pDialog.setCancelable(false);
		             pDialog.setCanceledOnTouchOutside(false);
		             
		             pDialog.show();
		           //dialog is automatically removed after 3 second
		             /*new Handler().postDelayed(new Runnable() {
		                 public void run() {                
		                	 pDialog.dismiss();         
		                 }
		             },5000); */
		             //-----------------------
		         }
				// Invoked by execute() method of this object
				@Override
				protected String doInBackground(String... url) {
					try{
						data = downloadUrl(url[0]);
					}catch(Exception e){
						 Log.d("Background Task",e.toString());
					}
					return data;
				}
				
				// Executed after the complete execution of doInBackground() method
				@Override
				protected void onPostExecute(String result){
					try{
						pDialog.dismiss();
					}catch(Exception e){}
					 parserTask = new ParserTask();
					// Log.i("Place", "post execute in places task ");
					// Start parsing the Google places in JSON format
					// Invokes the "doInBackground()" method of the class ParseTask
					parserTask.execute(result);
				}
				
			}
			
			
			/** A class to parse the Google Places in JSON format */
			private class ParserTask extends AsyncTask<String, Integer, List<Place>>{

				JSONObject jObject;
				
				// Invoked by execute() method of this object
				@Override
				protected List<Place> doInBackground(String... jsonData) {
					 Log.i("Place", "in parsertask");
					List<Place> places = null;			
					PlaceJSONParser placeJsonParser = new PlaceJSONParser();
		        
			        try{
			        	jObject = new JSONObject(jsonData[0]);
			        	
			            /** Getting the parsed data as a List construct */
			            places = placeJsonParser.parse(jObject);
			            
			        }catch(Exception e){
			                Log.d("Exception",e.toString());
			        }
			        return places;
				}
				
				// Executed after the complete execution of doInBackground() method
			
				@Override
				protected void onPostExecute(List<Place> places){			
					
					
					 if(places==null){
						 pDialog.dismiss();
						 Toast.makeText(getActivity(), "Your internet connection may be lost", Toast.LENGTH_LONG).show();
					     return;
					 }
					 HomeActivity.placesD=places;
					
				
					 
					 parsePlacesName(places);
					 setDataToAdapter();
					
					 Log.i("Place", "in post execute");
						pDialog.dismiss();
					
				}
				
			}//parser task class is finished here

	private ArrayList<String> parsePlacesName(List<Place> places){
		
		try{
			for(int i=0;i<places.size();i++){
				
				
		           Place hmPlace = places.get(i);
	
		            
		            // Getting name
		            String name = hmPlace.getPlaceName();
		            placeList.add(name);
		           
		          Log.i("Place", name);
		           
				}	
		}catch(Exception e){
			e.printStackTrace();
		}
		return placeList;
	}
	
	private void setDataToAdapter(){
		try{
			ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),R.layout.exb_details_listview_item, placeList);
			if(adapter.isEmpty()){
				adapter.add(PLACES_NOT_FOUND);
			}
			placeListView.setAdapter(adapter);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		HomeActivity.setTitle("Place List");
//		Commons.getMyTracker(getActivity(), "PlacesList");
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

}
