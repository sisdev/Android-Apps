package com.assusoft.efair.epchfair.Fragments;

import java.util.ArrayList;
import java.util.HashMap;

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

import com.assusoft.eFairEmall.databaseMaster.DatabaseHelper;
import com.assusoft.eFairEmall.entities.ExhibitorEntryExit;
import com.assusoft.eFairEmall.entities.FacilityInformation;
import com.assusoft.eFairEmall.entities.VenueMap;
import com.assusoft.tmxloader.library.AStar;
import com.assusoft.tmxloader.library.PathFinder;
import com.assusoft.tmxloader.library.TMXLoader;
import com.assusoft.tmxloader.library.TileMapData;
import com.assusoft.tmxloader.library.TouchImageView;
import com.epch.efair.delhifair.EFairEmallApplicationContext;
import com.epch.efair.delhifair.HomeActivity;
import com.epch.efair.delhifair.R;

public class VenueMapRouteFour extends Fragment{

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
		rightTextview.setVisibility(View.INVISIBLE);
		
		route.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FragmentTransaction ft = ((FragmentActivity)getActivity()).getSupportFragmentManager().beginTransaction();
		        
		        //To_From fragment = new  To_From();
				To_From_Custom fragment = new  To_From_Custom();
		        ft.replace(R.id.content_frame, fragment);
		        ft.commit();
			}
		});
		leftTextview.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(rightTextview.getVisibility()==View.INVISIBLE){
					rightTextview.setVisibility(View.VISIBLE);
				}
				 FragmentManager fm=getActivity().getSupportFragmentManager();   
				 fm.popBackStack();
				
			}
		});
		
		rightTextview.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		});
		 
		 final DisplayMetrics displayMetrics = new DisplayMetrics();
         getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
          height = displayMetrics.heightPixels-screenOffsetHeight;
          width = displayMetrics.widthPixels;
     
		 extras = getArguments();		

        if (extras != null) {
          	createMapWithRoute();
       
        }
		
		return v;
	}
	
	
	public static void createHashMap()
	{
		 hashMap = new HashMap<PathFinder.Node,ArrayList<PathFinder.Node>>();
		

	}
	
	public static HashMap<PathFinder.Node,ArrayList<PathFinder.Node>> obtainHashMap()
	{
		return hashMap;
	}
	
	public static void addToHashMap(PathFinder.Node n1, PathFinder.Node n2)
	{
		if(hashMap.containsKey(n1)==false)
		{
			ArrayList<PathFinder.Node> al= new ArrayList<AStar.Node>();
			hashMap.put(n1, al);
			
		}
		hashMap.get(n1).add(n2);
	}
	
	 public  void drawRoute(int x1,int y1,int x2,int y2){
		 map= TMXLoader.convertToArray(t,1);
	       
		 PathFinder pf= new PathFinder(map);
		 ArrayList<Integer> minmax= new ArrayList<Integer>();
		 
		 Log.i("POINTS",Long.toString(System.currentTimeMillis()));
		 map=pf.pfcompute(x1,y1,x2,y2,	t);
		    
		 //create bitmap with route layout
		 final Bitmap bmp=TMXLoader.createBitmap(t, getActivity(), 0, t.layers.size()-1, 0, 0);
		 touch.createBitmap(t, bmp, map, x1*16+8, y1*16+8, x2*16+8, y2*16+8, extras.getString("MARKERTYPEFROM"),extras.getString("MARKERTYPETO"));
		     
	 }
	 
	 private void createMapWithRoute(){
		
		 int x1,x2,y1,y2,floorNo;
		 
     	int locationId = extras.getInt("DEST_LOCATIONID");
     	int exhibitorId= extras.getInt("DEST_EXHOBITORID");
     	String facName = extras.getString("dsGATENAME");
     	DatabaseHelper helper = EFairEmallApplicationContext.getDatabaseHelper();
     	helper.openDatabase(DatabaseHelper.READMODE);
     	VenueMap map = helper.getVenueMapByLocationId(locationId);
     	FacilityInformation from;
     	if(map.getFilePath().equalsIgnoreCase("hall-9-10-11-12.jpg")||map.getFilePath().equalsIgnoreCase("hall-14-15.jpg"))
     		from = helper.getFacilityInfronationByLocationName(locationId,facName, ""+1);
     	else
     		from = helper.getFacilityInfronationByLocationName(locationId,facName, ""+2);
     	
     	ExhibitorEntryExit entryExit = helper.getExhibitorEntryExitByExhibitor(exhibitorId, locationId);

     	String FILENAME = map.getFilePath().replace(".jpg", ".assus");
     	x1=Integer.valueOf(from.getxLoc());
     	y1=Integer.valueOf(from.getyLoc());     	
     	x2=Integer.valueOf(entryExit.getxLoc());
     	y2=Integer.valueOf(entryExit.getyLoc());
     	
     	//ExhibitorEntryExit entryExit = helper.ge
     	 // Start the parser, get back TMX data object
         t = TMXLoader.readTMX(FILENAME, getActivity());
       
         createHashMap();
         
         drawRoute(x1,y1, x2,y2);
         String text="From : "+facName+", "+extras.getString("FLOOR")+"\nTo : "+extras.getString("EXHIBITORNAME");
         stepsTextView.setText(text);
	 }
	 
	 @Override
	public void onResume() {
		super.onResume();
		HomeActivity.setTitle("Location");
	}
}
