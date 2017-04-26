/**
 * @author UMESH SINGH KUSHWAHA
 */
package com.assusoft.efair.epchfair.googleMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
 
public class DirectionsJsonParser {
	
	public String routesInfo[] ;
	//routesInfo[0]="Select your route";
	public List<HashMap<String,String>> sourceDestAddress; 
	
    /** Receives a JSONObject and returns a list of lists containing latitude and longitude */
    public List<List<HashMap<String,String>>> parse(JSONObject jObject){
 
        List<List<HashMap<String, String>>> routes = new ArrayList<List<HashMap<String,String>>>() ;
        JSONArray jRoutes = null;
        JSONArray jLegs = null;
        JSONArray jSteps = null;
        
        try {
 
            jRoutes = jObject.getJSONArray("routes");
            routesInfo=new String[jRoutes.length()];
            sourceDestAddress=new ArrayList<HashMap<String, String>>();
            /** Traversing all routes */
            //Log.i("ROUTES",Integer.toString(jRoutes.length()));
            for(int i=0,m=0;i<jRoutes.length();i++,m++){
                jLegs = ( (JSONObject)jRoutes.get(i)).getJSONArray("legs");
                List path = new ArrayList<HashMap<String, String>>();
                //getting the route summary
                String routeInfo=(String) ((JSONObject) jRoutes.get(i)).get("summary");
                //getting the distance between two legs start and end point 
                routeInfo+="\n"+(String) ((JSONObject) ((JSONObject) jLegs.get(0)).get("distance")).get("text");
                //get the travel time 
               routeInfo+=" And "+(String) ((JSONObject) ((JSONObject) jLegs.get(0)).get("duration")).get("text");
               routesInfo[m]=routeInfo;
             //is used to get routes start and end point address
               HashMap<String, String> routeAddress = new HashMap<String, String>();
               //put the source address
               routeAddress.put("SOURCEADDRESS",(String)( ((JSONObject) jLegs.get(0)).get("start_address")));
               //put the destination address
               routeAddress.put("DESTINATIONADDRESS",(String)( ((JSONObject) jLegs.get(0)).get("end_address")));
               //add routes address in list path
               Log.i("ADDRESS",(String)( ((JSONObject) jLegs.get(0)).get("start_address"))+"\n"+(String)( ((JSONObject) jLegs.get(0)).get("end_address")));
               sourceDestAddress.add(routeAddress);
               //Log.i("ROUTESINFO",routesInfo[m]);
                /** Traversing all legs */
                for(int j=0;j<jLegs.length();j++){
                    jSteps = ( (JSONObject)jLegs.get(j)).getJSONArray("steps");
                    
                    /** Traversing all steps */
                    for(int k=0;k<jSteps.length();k++){
                        String polyline = "";
                        
                        polyline = (String)((JSONObject)((JSONObject)jSteps.get(k)).get("polyline")).get("points");
                        List<LatLng> list = decodePoly(polyline);
                       
                        /** Traversing all points */
                        for(int l=0;l<list.size();l++){
                            HashMap<String, String> hm = new HashMap<String, String>();
                            hm.put("lat", Double.toString(((LatLng)list.get(l)).latitude) );
                            hm.put("lng", Double.toString(((LatLng)list.get(l)).longitude) );
                            path.add(hm);
                        }
                    }
                   
                routes.add(path);
            }
        }
 
    } catch (JSONException e) {
        e.printStackTrace();
    }catch (Exception e){
    }
 
    return routes;
    }
    /**
    * Method to decode polyline points
    * Courtesy : http://jeffreysambells.com/2010/05/27/decoding-polylines-from-google-maps-direction-api-with-java
    * */
    private List<LatLng> decodePoly(String encoded) {
 
        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;
 
        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;
 
            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;
            
            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
                poly.add(p);
            }
     
            return poly;
        }
    //this is used to get routes info like routes name ,distance,and estimated time
    public String[] getRoutesInfo(){
    	return routesInfo;
    }
    
    public List<HashMap<String,String>> getRoutesStartAndEndAddress(){
		
    	return sourceDestAddress;    	
    }
    
  }