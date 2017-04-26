package com.assusoft.efair.epchfair.googleMap;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class PlaceJSONParser {
/** Receives a JSONObject and returns a list */
public List<Place> parse(JSONObject jObject){		
	 Log.i("Place", "json parser is called");
	JSONArray jPlaces = null;
	try {			
		/** Retrieves all the elements in the 'places' array */
		jPlaces = jObject.getJSONArray("results");
	} catch (JSONException e) {
		e.printStackTrace();
	}
	/** Invoking getPlaces with the array of json object
	 * where each json object represent a place
	 */
	return getPlaces(jPlaces);
}


private List<Place> getPlaces(JSONArray jPlaces){
	int placesCount = jPlaces.length();
	List<Place> placesList = new ArrayList<Place>();
	Place place = null;	
	Log.i("Place", "in getPlaces+ "+placesCount);
	/** Taking each place, parses and adds to list object */
	for(int i=0; i<placesCount;i++){
		try {
			/** Call getPlace with place JSON object to parse the place */
			place = getPlace((JSONObject)jPlaces.get(i));
			placesList.add(place);

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	return placesList;
}

/** Parsing the Place JSON object */
private Place getPlace(JSONObject jPlace){

	Place place = new Place();
	String placeName = "-NA-";
	String vicinity="-NA-";
	String latitude="";
	String longitude="";
	String type="";
			
	
	try {
		// Extracting Place name, if available
		if(!jPlace.isNull("name")){
			placeName = jPlace.getString("name");
		}
		
		// Extracting Place Vicinity, if available
		if(!jPlace.isNull("vicinity")){
			vicinity = jPlace.getString("vicinity");
		}	
		if(!jPlace.isNull("types")){
			type=jPlace.getJSONArray("types").get(0).toString();
		}
		
		latitude = jPlace.getJSONObject("geometry").getJSONObject("location").getString("lat");
		longitude = jPlace.getJSONObject("geometry").getJSONObject("location").getString("lng");			
		
		//set the single place object
		place.setPlaceName( placeName); 
		place.setVicinity( vicinity);
		place.setLatitude( Double.parseDouble(latitude));
		place.setLongitude( Double.parseDouble(longitude));
		place.setType( type);
		
		
	} catch (JSONException e) {			
		e.printStackTrace();
	}		
	return place;
}
}
