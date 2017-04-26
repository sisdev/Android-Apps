package com.assusoft.efair.epchfair.Fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.assusoft.eFairEmall.databaseMaster.DatabaseHelper;
import com.assusoft.eFairEmall.entities.ExhibitorEntryExit;
import com.assusoft.eFairEmall.entities.FacilityInformation;
import com.assusoft.eFairEmall.entities.VenueMap;
import com.assusoft.tmxloader.library.AStar;
import com.assusoft.tmxloader.library.AStar.Node;
import com.assusoft.tmxloader.library.PathFinder;
import com.assusoft.tmxloader.library.TMXLoader;
import com.assusoft.tmxloader.library.TileMapData;
import com.assusoft.tmxloader.library.TouchImageView;
import com.epch.efair.delhifair.EFairEmallApplicationContext;
import com.epch.efair.delhifair.HomeActivity;
import com.epch.efair.delhifair.R;

public class VenueMapRouteThree extends Fragment{

	ImageView mapView;
	//String FILENAME = "path4.tmx";
	public static String EXTRA_IMAGE="EXTRA_IMAGE";
	static HashMap<PathFinder.Node,ArrayList<PathFinder.Node>> hashMap;
	Button route;
	Spinner start,dest;
	int x1=0,y1=0,x2=0,y2=0;
	ImageView mapview;
	//Touch touch;
	TouchImageView touch;
	TileMapData t;
    ImageView leftTextview,rightTextview;
     TextView stepsTextView;
	private static final int screenOffsetHeight=120;
	private int height,width;
	Bundle extras;
	ArrayList<String> steps;
	private boolean isSameFloor=true;
	int counter=0;
	long[][] map;
	
    /** Called when the activity is first created. 
     * this fragment is launched in three state 
     * 1. without marker without route
     * 2. only with marker
     * 3. only with route*/
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v=inflater.inflate(R.layout.venue_with_route,container,false);
		leftTextview=(ImageView) v.findViewById(R.id.tvVenueleft);
		rightTextview=(ImageView) v.findViewById(R.id.tvVenueright);
		stepsTextView=(TextView) v.findViewById(R.id.tvVenuesteps);
		route=(Button) v.findViewById(R.id.btnvenueGetRoute);
		touch = (TouchImageView)v.findViewById(R.id.imgMap);
		steps=new ArrayList<String>();
		route.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 FragmentTransaction ft = ((FragmentActivity)getActivity()).getSupportFragmentManager().beginTransaction();
			        
				    To_From_Custom fragment = new  To_From_Custom();
			        ft.replace(R.id.content_frame, fragment);
			        ft.commit();
			}
		});
		
		leftTextview.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				     FragmentManager fm=getActivity().getSupportFragmentManager();   
				       fm.popBackStack();
				
			}
		});
		
		rightTextview.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(leftTextview.getVisibility()==View.INVISIBLE){
					leftTextview.setVisibility(View.VISIBLE);
				}
					//rightTextview.setVisibility(View.INVISIBLE);
					FragmentTransaction ft = ((FragmentActivity)getActivity()).getSupportFragmentManager().beginTransaction();
			        VenueMapRouteFour fragment = new VenueMapRouteFour();
			        ft.replace(R.id.content_frame, fragment);
			        fragment.setArguments(extras);
			        ft.addToBackStack(null);
			        ft.commit();
			}
		});
		 
		 final DisplayMetrics displayMetrics = new DisplayMetrics();
         getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
         height = displayMetrics.heightPixels-screenOffsetHeight;
         width = displayMetrics.widthPixels;
     
		 extras = getArguments();		

        if (extras != null) {
        	if(extras.getBoolean("WITH_OUTER_MAP")){
        		rightTextview.setVisibility(View.INVISIBLE);
        	}
          	createMapWithRoute();
       
        }
		
		return v;
	}
	
	
	public static void createHashMap(){
		 hashMap = new HashMap<PathFinder.Node,ArrayList<PathFinder.Node>>();

	}
	
	public static HashMap<PathFinder.Node,ArrayList<PathFinder.Node>> obtainHashMap(){
		return hashMap;
	}
	
	public static void addToHashMap(PathFinder.Node n1, PathFinder.Node n2){
		if(hashMap.containsKey(n1)==false){
			ArrayList<PathFinder.Node> al= new ArrayList<AStar.Node>();
			hashMap.put(n1, al);
			
		}
		hashMap.get(n1).add(n2);
	}
	
	public FacilityInformation drawRoute(FacilityInformation start, ArrayList<FacilityInformation> end){
		map= TMXLoader.convertToArray(t,1);	    
        PathFinder pf= new PathFinder(map);
        final int x1=Integer.valueOf(start.getxLoc());
        final int y1=Integer.valueOf(start.getyLoc());
        
        final List<Node> nodes=pf.findPath(x1, y1, end, t);
        FacilityInformation information = new FacilityInformation();
        int x2=0,y2=0;
        if(nodes!=null){
        	x2=nodes.get(nodes.size()-1).x;
        	y2=nodes.get(nodes.size()-1).y;
        }
        for(int i=0;i<end.size();i++){
        	if(Integer.valueOf(end.get(i).getxLoc())==x2 && Integer.valueOf(end.get(i).getyLoc())==y2){
        		information = end.get(i);
        		break;
        	}
        }
        
        final Bitmap bmp=TMXLoader.createBitmap(t, getActivity(), 0, t.layers.size()-1, 0, 0);
	       if(bmp==null ){	    	   
		    	   Toast.makeText(getActivity(), "Memory is too low.",Toast.LENGTH_LONG).show();
		    	   return  null;
	       }
	       if(extras.getBoolean("IS_TOP_DOWN"))
	    	   touch.createBitmap(t, nodes, bmp,x1*16+8,y1*16+8,
						x2*16+8,y2*16+8,extras.getString("MARKERTYPEFROM"),extras.getString("MARKERTYPETO"));
	       else
	    	   touch.createBitmap(t, nodes, bmp,x1*16+8,y1*16+8,
						x2*16+8,y2*16+8,extras.getString("MMARKERTYPEFROM"),extras.getString("MARKERTYPEFROM"));
	       
        return information;
	}
	 public  void drawRoute(final int x1,final int y1,final int x2,int y2,final int destMarkerX,final int destMarkerY){
	        map= TMXLoader.convertToArray(t,1);
    
	        PathFinder pf= new PathFinder(map);
	       
	        Log.i("POINTS",Long.toString(System.currentTimeMillis()));
	        map=pf.pfcompute(x1,y1,x2,y2,	t);
	      
	       //create bitmap with route layout
	       final Bitmap bmp=TMXLoader.createBitmap(t, getActivity(), 0, t.layers.size()-1, 0, 0);
	       if(bmp==null ){	    	   
		    	   Toast.makeText(getActivity(), "Memory is too low.",Toast.LENGTH_LONG).show();
		    	   return;
	    	  
	       }
	      
	       new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				   //touch.setImageBitmap(bmp);
				touch.createBitmap(t, bmp, map,x1*16+8,y1*16+8,
						destMarkerX*16+8,destMarkerY*16+8,extras.getString("MARKERTYPEFROM"),extras.getString("MARKERTYPETO"));
			}
		}, 500);
	    
	     
	      
   }
	 private void createMapWithRoute(){	
     	String FILENAME = extras.getString(EXTRA_IMAGE);
     	 // Start the parser, get back TMX data object
        t = TMXLoader.readTMX(FILENAME, getActivity());
        
        createHashMap();
        
        String stepsText;
        DatabaseHelper helper = EFairEmallApplicationContext.getDatabaseHelper();
        helper.openDatabase(DatabaseHelper.READMODE);
        int locationId;
       if(extras.getInt("FLAG")==3){
    	   ArrayList<FacilityInformation> to=new ArrayList<FacilityInformation>();
           if(FILENAME.equalsIgnoreCase("hall-1-3-5-7.assus")){
           	locationId=1;
           	to = helper.getEntryExitBylocationId(""+locationId, VenueMapWithRoute.ELEVATOR_TYPE);
           	stepsText="Enter : Gate, Hall 1\nTake : Elevator";
           }
           else{
           	locationId=helper.getVenueMapDetailByType(""+4).get(0).getLocationId();
           	int dslocationId = extras.getInt("DEST_LOCATIONID");
           	VenueMap map = helper.getVenueMapByLocationId(dslocationId);
           	if(map.getFilePath().equalsIgnoreCase("hall-14-15.jpg"))
           		to = helper.getEntryExitBylocationId(""+locationId, "6");
           	else
           		to = helper.getEntryExitBylocationId(""+locationId, "5");
           	
           	stepsText="Exit : Gate, Hall 1\nEnter : Gate, Hall "+map.getHallNo();
           }
           
           FacilityInformation from = helper.getFacilityInfronationByLocationName(locationId,extras.getString("GATENAME"), ""+3);
           from = drawRoute(from, to);
           extras.putString("dsGATENAME", from.getLocationName());
           
       }else{
    	   int exhibitorId = extras.getInt("DEST_EXHOBITORID");
    	   locationId = extras.getInt("DEST_LOCATIONID");
    	   ExhibitorEntryExit entryExit = helper.getExhibitorEntryExitByExhibitor(exhibitorId, locationId);
    	   int x1=extras.getInt("dsX1");
    	   int y1=extras.getInt("dsY1");
    	   int x2=Integer.valueOf(entryExit.getxLoc());
    	   int y2=Integer.valueOf(entryExit.getyLoc());
    	   drawRoute(x1,y1,x2,y2,x2,y2);

           stepsText="From: "+extras.getString("GATENAME")+", "+extras.getString("FLOOR")
           		+"\nTo : "+extras.getString("EXHIBITORNAME")+", "+extras.getString("FLOOR");
       }
        
        stepsTextView.setText(stepsText);
        
	 }
	 
	 @Override
	public void onResume() {
		super.onResume();
		HomeActivity.setTitle("Location");
	}
	   
}
