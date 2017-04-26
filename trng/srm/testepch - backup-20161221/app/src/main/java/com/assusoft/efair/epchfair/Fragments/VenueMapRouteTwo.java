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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.assusoft.eFairEmall.databaseMaster.DatabaseHelper;
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

public class VenueMapRouteTwo extends Fragment{

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
    TextView stepsTextView;
    ImageView leftTextview,rightTextview;
	private static final int screenOffsetHeight=120;
	private int height,width;
	Bundle extras;
	ArrayList<String> steps;
	private boolean isSameFloor=true;
	int counter=0;
	long[][] map;
	String stepsText;
	FacilityInformation destination=new FacilityInformation();
	
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
			        
			        //To_From fragment = new  To_From();
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
				
				if(extras.getInt("FLAG")==3||extras.getInt("FLAG")==1){
					FragmentTransaction ft = ((FragmentActivity)getActivity()).getSupportFragmentManager().beginTransaction();
			        VenueMapRouteThree fragment = new VenueMapRouteThree();
			        ft.replace(R.id.content_frame, fragment);
			        fragment.setArguments(extras);
			        ft.addToBackStack(null);
			        ft.commit();
					
				}
			}
		});
		 
		 final DisplayMetrics displayMetrics = new DisplayMetrics();
         getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
          height = displayMetrics.heightPixels-screenOffsetHeight;
          width = displayMetrics.widthPixels;
     
		 extras = getArguments();	
		 if((extras.getInt("FLAG")==1||extras.getInt("FLAG")==2)&&!extras.getBoolean("WITH_OUTER_MAP")){
			 rightTextview.setVisibility(View.GONE);
		 }

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
	
	
	
	 public  void drawRoute(final int x1,final int y1,final int x2,final int y2,
			 final int destMarkerX,final int destMarkerY,final String markerTypeFrom,final String markerTypeTO){
	  	        map= TMXLoader.convertToArray(t,1);
	       
		        PathFinder pf= new PathFinder(map);
		        
		       
		        Log.i("POINTS",Long.toString(System.currentTimeMillis()));
		        map=pf.pfcompute(x1,y1,x2,y2,
		        		t);
		      
		       //create bitmap with route layout
		        final Bitmap bmp=TMXLoader.createBitmap(t, getActivity(), 0, t.layers.size()-1, 0, 0);
		       if(bmp==null ){
		    	   
			    	   Toast.makeText(getActivity(), "Memory is too low.",Toast.LENGTH_LONG).show();
			    	   return;
		    	  
		       }
		       Log.i("EFAIR","SecondMap-X1-"+x1+",y1-"+y1+",x2-"+x2+",y2-"+y2);
		       new Handler().postDelayed(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					   //touch.setImageBitmap(bmp);
					touch.createBitmap(t, bmp, map,x1*16+8,y1*16+8,destMarkerX*16+8,
							destMarkerY*16+8,markerTypeFrom,markerTypeTO);
				}
			}, 500);  
	   }
	 
	 public   ArrayList<FacilityInformation> drawRoute(ArrayList<FacilityInformation> start,ArrayList<FacilityInformation> end,
			 int markerX1,int markerY1,int markerX2,int markerY2,String fromMarker,String toMarker){
	  	       ArrayList<FacilityInformation> info=new ArrayList<FacilityInformation>();
		       long[][] map= TMXLoader.convertToArray(t,1);
	          
		        PathFinder pf= new PathFinder(map); 
		        map=pf.pfcompute(start,end,this);
		      
		       //create bitmap with route layout
		      // Bitmap bmp=TMXLoader.createBitmap(t,getActivity(), 0, t.layers.size(), map);
		      //create bitmap with route layout
			       Bitmap bmp=TMXLoader.createBitmap(t, getActivity(), 0, t.layers.size()-1, 0, 0);
			      
			       if(bmp==null ){
			    	   
				    	   Toast.makeText(getActivity(), "Memory is too low.",Toast.LENGTH_LONG).show();
				    	   info.add(end.get(0));
				    	   return info;
			       }
			       if(destination!=null && destination.getxLoc()!=null && destination.getyLoc()!=null ){
				    	info.add(destination);
				    	Log.i("EFAIR","destination!=null"+info.get(0).getxLoc()+","+info.get(0).getyLoc());
				    }else{
				    	info.add(end.get(0));
				    }
			       touch.createBitmap(t, bmp, map,markerX1*16+8,markerY1*16+8,Integer.valueOf(info.get(0).getxLoc())*16+8,
			    		              Integer.valueOf(info.get(0).getyLoc())*16+8,fromMarker,toMarker);
			    
		     
		    return info;  
	   }
	 
	 private void createMapWithRoute(){ 
		
     	String FILENAME ;
     	DatabaseHelper dbHelper=EFairEmallApplicationContext.getDatabaseHelper();
        dbHelper.openDatabase(DatabaseHelper.READMODE);
     	ArrayList<FacilityInformation> to=new ArrayList<FacilityInformation>();
        VenueMap map = dbHelper.getVenueMapDetailByType(""+4).get(0);
     	if(extras.getInt("FLAG")==3){
     		FILENAME=extras.getString("FILENAME");
     		 // Start the parser, get back TMX data object
            t = TMXLoader.readTMX(FILENAME, getActivity());
          
            createHashMap();
            ArrayList<FacilityInformation> from=new ArrayList<FacilityInformation>();
            FacilityInformation info=new FacilityInformation();
       	 
	       	 info.setxLoc(""+extras.getInt("mX1"));info.setyLoc(""+extras.getInt("mY1"));
	       	 from.add(info);
	       
	       			to=extras.getParcelableArrayList("MULTIDEST");
	       	Log.i("EFAIR","From "+to.isEmpty());
	       	if(extras.getBoolean("IS_TOP_DOWN")){
	       		from=drawRoute(from,to, extras.getInt("mX1"), extras.getInt("mY1"),0,0, extras.getString("MMARKERTYPEFROM"),extras.getString("MARKERTYPEFROM"));
	       		stepsText="Exit : "+extras.getString("MIDDLEMAP")+"\nTo : "+from.get(0).getLocationName()+", "+extras.getString("FLOOR");
	       	}
	       	else{
	       		from=drawRoute(from,to, extras.getInt("mX1"), extras.getInt("mY1"),0,0, extras.getString("MMARKERTYPEFROM"),extras.getString("MARKERTYPETO"));
	       		stepsText="Exit : Gate, Hall "+extras.getString("EXIT")+"\nEnter : Gate, Hall 1";
	       	}
	       	
            
            stepsTextView.setText(stepsText);
            String destId=extras.getString("DESTID");
            
            Log.i("EFAIR","DEST_IS ON SECOND "+destId);
          
           if(extras.getBoolean("IS_TOP_DOWN")){
        	   extras.putString(EXTRA_IMAGE, map.getFilePath().replaceFirst(".jpg",".assus"));
        	   from=dbHelper.getEntryExitBylocationId(""+1,VenueMapWithRoute.MAIN_ENTRANCE_TYPE,from.get(0).getLocationName());
           }
           else{
        	   extras.putString(EXTRA_IMAGE, "hall-1-3-5-7.assus");
        	   from=dbHelper.getEntryExitBylocationId(""+map.getLocationId(),VenueMapWithRoute.MAIN_ENTRANCE_TYPE,from.get(0).getLocationName());
           }           
           
           extras.putInt("X1",Integer.parseInt(from.get(0).getxLoc()));
           extras.putInt("Y1",Integer.parseInt(from.get(0).getyLoc()));
           extras.putString("GATENAME",from.get(0).getLocationName());
            
     	}else{
     		FILENAME=extras.getString("FILENAME");
     		if(FILENAME==null)
     			FILENAME=extras.getString(EXTRA_IMAGE);
     		 // Start the parser, get back TMX data object
            t = TMXLoader.readTMX(FILENAME, getActivity());
          
            createHashMap();
            
            if(extras.getBoolean("WITH_OUTER_MAP")){
            	
            	Log.i("EFAIR","GATENAME"+extras.getString("GATENAME"));
            	if(extras.getString(EXTRA_IMAGE).equalsIgnoreCase("hall-9-10-11-12.assus"))
            		to = dbHelper.getEntryExitBylocationId(""+map.getLocationId(), ""+5);
            	else if(extras.getString(EXTRA_IMAGE).equalsIgnoreCase("hall-1-3-5-7.assus")||extras.getString(EXTRA_IMAGE).equalsIgnoreCase("GroundFloor.assus"))
            		to = dbHelper.getEntryExitBylocationId(""+map.getLocationId(), "","Main entrance1");
            	else
            		to = dbHelper.getEntryExitBylocationId(""+map.getLocationId(), "","Main entrance2");
            	
            	 ArrayList<FacilityInformation> from=new ArrayList<FacilityInformation>();
                 FacilityInformation info=new FacilityInformation();
            	 
     	       	info.setxLoc(""+extras.getInt("X1"));info.setyLoc(""+extras.getInt("Y1"));
     	       	from.add(info);
     	       	from=drawRoute(from,to, extras.getInt("X1"), extras.getInt("Y1"),0,0, extras.getString("MARKERTYPEFROM"),extras.getString("MARKERTYPETO"));
     	       	
     	       	from = dbHelper.getEntryExitBylocationId(""+extras.getInt("DEST_LOCATIONID"), "", from.get(0).getLocationName());
     	       	extras.putInt("dsX1", Integer.valueOf(from.get(0).getxLoc()));
     	       	extras.putInt("dsY1", Integer.valueOf(from.get(0).getyLoc()));
     	       	stepsText="Exit : Gate, Hall "+extras.getString("EXIT")
     	    		   			+"\n Enter : Gate, Hall "+extras.getString("ENTER");
            }else{   
                drawRoute(extras.getInt("X1"),extras.getInt("Y1"),extras.getInt("X2"),extras.getInt("Y2"),
      		          extras.getInt("XLOC"),extras.getInt("YLOC"),extras.getString("MARKERTYPEFROM"),extras.getString("MARKERTYPETO"));
                stepsText=""+extras.getString("GATENAME")+", "+extras.getString("FLOOR")
                		+"\n To : "+extras.getString("EXHIBITORNAME")+", "+extras.getString("FLOOR");
            }
            
            
            stepsTextView.setText(stepsText);
     	}
	 }
	 
	 public void setFacility(FacilityInformation info){
		 destination.setxLoc(info.getxLoc());
		 destination.setyLoc(info.getyLoc());
		 destination.setLocationName(info.getLocationName());
		 Log.i("EFAIR","setFacility "+info.getLocationName());
	 }
	 
	 @Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		HomeActivity.setTitle("Location");
	} 
}
