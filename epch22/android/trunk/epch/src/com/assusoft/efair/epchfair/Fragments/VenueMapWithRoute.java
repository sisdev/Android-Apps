package com.assusoft.efair.epchfair.Fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
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
import com.assusoft.eFairEmall.entities.ExhibitorEntryExit;
import com.assusoft.eFairEmall.entities.ExhibitorLocation;
import com.assusoft.eFairEmall.entities.Exhibitors;
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

public class VenueMapWithRoute extends Fragment implements Parcelable{

	ImageView mapView;
	//String FILENAME = "path4.tmx";
	public static String EXTRA_IMAGE="EXTRA_IMAGE";
	static HashMap<PathFinder.Node,ArrayList<PathFinder.Node>> hashMap;
	Button route;
	Spinner start,dest;
	int x1=0,y1=0,x2=0,y2=0,eleX=0,eleY=0,secondMapsX,secondMapsY,floorNo;
	String secondMapGateName,mMarkerTypeFrom,mMarkerTypeTo,sMarkerTypeFrom,sMarkerTypeTo;
	final static public int expoCenterX=30,expoCenterY=17;
	static public int groundFloorEntryX=110;
	public static int groundFloorEntryY=66;
	public static final int groundFloorEleX=97;
	public static final int groundFloorEleY=72;
	final static public int hall7EntryX=7,hall7EntryY=63;//109,63
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
	private int isSameFloor=0;//0-same map, 1-same floor,2-different floor,3-
	int counter=0;
	ExhibitorLocation location1=null,location2=null;
    VenueMap vmap1=null,vmap2;
    Exhibitors exhib1=null,exhib2;
    List<ExhibitorEntryExit> entryExit;
    
    ArrayList<ExhibitorEntryExit> startLoc,endLoc;
    ArrayList<FacilityInformation> eleLoc;
    
    public final static String ELEVATOR_TYPE="2";
    public final static String MAIN_ENTRANCE_TYPE="3";
    public final static String GATE_TYPE="1";
    
    FacilityInformation source=new FacilityInformation();
    FacilityInformation destination=new FacilityInformation();
    DatabaseHelper dbHelper;
	
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
		leftTextview.setVisibility(View.GONE);
		
		
		dbHelper=EFairEmallApplicationContext.getDatabaseHelper();
		dbHelper.openDatabase(DatabaseHelper.READMODE);
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
				
			}
		});
		
		rightTextview.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
					if(isSameFloor==1 || isSameFloor==2){
						//load another fragment that show the route from elevator to destination
						FragmentTransaction ft = ((FragmentActivity)getActivity()).getSupportFragmentManager().beginTransaction();
				        Bundle data=new Bundle();
				        
				        data.putInt("X2",x2);
				        data.putInt("Y2",y2);
				        data.putString("DEST",exhib2.getExhibitorName());
				        data.putString("EXHIBITORNAME",exhib2.getExhibitorName());
				        data.putString("MMARKERTYPETO", MARKER_TYPE.EXIT_MARKER.toString());
				        data.putString("MARKERTYPEFROM", sMarkerTypeFrom);
				        data.putString("MARKERTYPETO", sMarkerTypeTo);
				        data.putInt("FLAG",isSameFloor);
				        data.putString("ENTER", vmap2.getHallNo());
				        data.putString("EXIT", vmap1.getHallNo());
				        
				        if(vmap1.getFilePath().equalsIgnoreCase("hall-9-10-11-12.jpg")||vmap1.getFilePath().equalsIgnoreCase("hall-14-15.jpg")
			        			 ||vmap2.getFilePath().equalsIgnoreCase("hall-9-10-11-12.jpg")||vmap2.getFilePath().equalsIgnoreCase("hall-14-15.jpg")){
				        	data.putBoolean("WITH_OUTER_MAP", true);
				        	data.putInt("DEST_LOCATIONID", vmap2.getLocationId());;
					        data.putInt("DEST_EXHOBITORID", exhib2.getExhibitorId());
					        data.putString("EXTRA_IMAGE", vmap2.getFilePath().trim().replaceAll(".jpg",VenueMapWithLocation.CSVMAPFILE_EXTENTION));//replace file venue2.getFilePath().trim().replaceAll(".jpg",VenueMapWithLocation.CSVMAPFILE_EXTENTION)
				        	data.putInt("X1",secondMapsX);
					        data.putInt("Y1",secondMapsY);
					        data.putString("GATENAME",secondMapGateName);
					        data.putString("FILENAME","venuemap.assus");
				        }else{
				        	data.putBoolean("WITH_OUTER_MAP", false);
				        	data.putString(EXTRA_IMAGE, vmap2.getFilePath().trim().replaceAll(".jpg",VenueMapWithLocation.CSVMAPFILE_EXTENTION));//replace file venue2.getFilePath().trim().replaceAll(".jpg",VenueMapWithLocation.CSVMAPFILE_EXTENTION)
					        
				        	data.putInt("X1",secondMapsX);
					        data.putInt("Y1",secondMapsY);
					        data.putString("GATENAME",secondMapGateName);
				        }
				        data.putString("FLOOR", vmap2.getFloorName());
				        data.putInt("XLOC", Integer.parseInt(location2.getxLocation()));
				        data.putInt("YLOC", Integer.parseInt(location2.getyLocation()));
				        VenueMapRouteTwo fragment = new  VenueMapRouteTwo();
				        ft.replace(R.id.content_frame, fragment);
				        fragment.setArguments(data);
				        ft.addToBackStack(null);
				        ft.commit();
				       
					}else if(isSameFloor==3){
						//load another fragment that show the route from elevator to destination
						FragmentTransaction ft = ((FragmentActivity)getActivity()).getSupportFragmentManager().beginTransaction();
				        Bundle data=new Bundle();
				        data.putString(EXTRA_IMAGE, vmap2.getFilePath().trim().replaceAll(".jpg",VenueMapWithLocation.CSVMAPFILE_EXTENTION));//replace file venue2.getFilePath().trim().replaceAll(".jpg",VenueMapWithLocation.CSVMAPFILE_EXTENTION)
				        
				        data.putInt("X2",x2);
				        data.putInt("Y2",y2);
				        data.putString("DEST",exhib2.getExhibitorName());
				        data.putString("EXHIBITORNAME",exhib2.getExhibitorName());
				        data.putString("MARKERTYPEFROM", sMarkerTypeFrom);
				        data.putString("MARKERTYPETO", sMarkerTypeTo);
				        data.putString("MMARKERTYPEFROM", mMarkerTypeFrom);
				        data.putString("MMARKERTYPETO", mMarkerTypeTo);
				        data.putInt("FLAG",isSameFloor);
				        data.putString("ENTER", vmap2.getHallNo());
				        data.putString("EXIT", vmap1.getHallNo());
				        if(vmap2.getFilePath().equalsIgnoreCase("hall-9-10-11-12.jpg") || vmap2.getFilePath().equalsIgnoreCase("hall-14-15.jpg")){
				        	data.putInt("X1",expoCenterX);
					        data.putInt("Y1",expoCenterY);
					        data.putString("GATENAME","Expo center main entrance");
					        data.putString("MIDDLEMAP",""+eleLoc.get(0).getLocationName()+", "+vmap2.getFloorName());
					        data.putString("FLOOR", "Outer Halls");
					        data.putInt("mX1",Integer.parseInt(eleLoc.get(0).getxLoc()));
					        data.putInt("mY1",Integer.parseInt(eleLoc.get(0).getyLoc()));
					        data.putInt("DEST_LOCATIONID", vmap2.getLocationId());;
					        data.putInt("DEST_EXHOBITORID", exhib2.getExhibitorId());
					        data.putInt("mX2",groundFloorEntryX);
					        data.putInt("mY2",groundFloorEntryY);
					        Log.i("EFAIR"," ground floor : "+eleLoc.get(0).getLocationName());
					        eleLoc=dbHelper.getEntryExitBylocationId(""+1,MAIN_ENTRANCE_TYPE,"");
					        data.putParcelableArrayList("MULTIDEST",eleLoc);
					        data.putString("FILENAME", "hall-1-3-5-7.assus");
					        data.putBoolean("IS_TOP_DOWN", true);
					        Log.i("EFAIR"," ground floor : "+eleLoc.get(0).getxLoc()+""+eleLoc.get(0).getyLoc());
					        
				        }else{
				        	
				        	data.putInt("X1",secondMapsX);
					        data.putInt("Y1",secondMapsY);
					        data.putString("GATENAME",secondMapGateName);
					        data.putString("FLOOR", vmap2.getFloorName());
					        data.putInt("mX2",groundFloorEleX);
					        data.putInt("mY2",groundFloorEleY);
					        data.putInt("mX1",Integer.parseInt(eleLoc.get(0).getxLoc()));
					        data.putInt("mY1",Integer.parseInt(eleLoc.get(0).getyLoc()));
					        VenueMap map= dbHelper.getVenueMapDetailByType(""+4).get(0);
					        eleLoc=dbHelper.getEntryExitBylocationId(""+map.getLocationId(),MAIN_ENTRANCE_TYPE,"Main entrance1");
					        data.putString("FILENAME", map.getFilePath().replace(".jpg", VenueMapWithLocation.CSVMAPFILE_EXTENTION));
					        //data.putString("MIDDLEMAP",""+eleLoc.get(0).getLocationName());
					        data.putInt("DEST_LOCATIONID", vmap2.getLocationId());;
					        data.putInt("DEST_EXHOBITORID", exhib2.getExhibitorId());
					        data.putParcelableArrayList("MULTIDEST",eleLoc);
					        data.putBoolean("IS_TOP_DOWN", false);
				        }
				        data.putInt("XLOC", Integer.parseInt(location2.getxLocation()));
				        data.putInt("YLOC", Integer.parseInt(location2.getyLocation()));
				        Log.i("EFAIR","DEST_ID "+vmap2.getLocationId());
				        data.putString("DESTID",""+vmap2.getLocationId());
				        data.putParcelable("FRAGMENT",VenueMapWithRoute.this);
				        VenueMapRouteTwo fragment = new  VenueMapRouteTwo();
				        ft.replace(R.id.content_frame, fragment);
				        fragment.setArguments(data);
				        ft.addToBackStack(null);
				        ft.commit();
					}else{
						
					}
				
			}
		});
		 
		 final DisplayMetrics displayMetrics = new DisplayMetrics();
         getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
          height = displayMetrics.heightPixels-screenOffsetHeight;
          width = displayMetrics.widthPixels;
     
		 extras = getArguments();		

        if (extras != null) {
            
            int flag=extras.getInt("FLAG");
           
       
        
        Bitmap mapImage=null;
        if(flag==0){
	        // Create a Bitmap from the tilemap data
        	String FILENAME = extras.getString(EXTRA_IMAGE);
        	//get filename,x and y location of exhibitor
        	 // Start the parser, get back TMX data object
            t = TMXLoader.readTMX(FILENAME, getActivity());
            System.out.println("No. of tilesets: "+ TMXLoader.noOfTilesets(t));
            createHashMap();
	        mapImage = TMXLoader.createBitmap(t, getActivity(), 0, t.layers.size()-1, 0, 0);
        }else if(flag==1){
        	//create route with map, get exhibitorId's and make query
        	createMapWithRoute(true);
        	return v;
        }else{
        	//nothing to do 
        }
        // Set the imageview to show the map, if we have one
        if (mapImage != null){
        	
        	touch.setImageBitmap(mapImage);
        	
        	
        }
        // Map loading problem, inform the user.
        else{
	         Toast errorMessage = Toast.makeText(getActivity(), "Map could not be loaded", Toast.LENGTH_LONG);
	         errorMessage.show();
        }
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
	
	 public  void drawRoute(int x1,int y1,int x2,int y2,int markerX1,int markerY1,
			 int markerX2,int markerY2,String fromMarker,String toMarker){
	  	 long[][] map= TMXLoader.convertToArray(t,1);
	       
		        PathFinder pf= new PathFinder(map);
		        
		       
		        Log.i("POINTS",Long.toString(System.currentTimeMillis()));
		        map=pf.pfcompute(x1,y1,x2,y2,
		        		t);
		      
		       //create bitmap with route layout
		       Bitmap bmp=TMXLoader.createBitmap(t, getActivity(), 0, t.layers.size()-1, 0, 0);
		       if(bmp==null ){		    	   
		    	   Toast.makeText(getActivity(), "Memory is too low.",Toast.LENGTH_LONG).show();		    	   
		    	   return ;
	    	
	          }
		       Log.i("EFAIR","Routes on first x1,y1,x2,y2 : "+x1+","+y1+","+x2+","+y2);
		       Log.i("EFAIR","Routes on firstlocation id x1,y1,x2,y2 : "+markerX1+","+markerY1+","+markerX2+","+markerY2);
		       //touch.setImageBitmap(bmp);
		       touch.createBitmap(t, bmp, map,markerX1,markerY1,markerX2,markerY2,fromMarker,toMarker);
		     
		      
	   }
	 
	
	 
	 //called in case of multiple end and start point
	 public   ArrayList<FacilityInformation> drawRoute(ArrayList<FacilityInformation> start,ArrayList<FacilityInformation> end,
			 int markerX1,int markerY1,int markerX2,int markerY2,String fromMarker,String toMarker){
	  	       ArrayList<FacilityInformation> info=new ArrayList<FacilityInformation>();
		       long[][] map= TMXLoader.convertToArray(t,1);
	          
		        PathFinder pf= new PathFinder(map);
		        
		       
		        
		        map=pf.pfcompute(start,end,this);
		      
		       
		      //create bitmap with route layout
			       Bitmap bmp=TMXLoader.createBitmap(t, getActivity(), 0, t.layers.size()-1, 0, 0);
			      
			       if(bmp==null ){
			    	   
				    	   Toast.makeText(getActivity(), "Memory is too low.",Toast.LENGTH_LONG).show();
				    	   info.add(end.get(0));
				    	   return info;
			    	
			       }
			       if(destination!=null && destination.getxLoc()!=null && destination.getyLoc()!=null){
				    	info.add(destination);
				    	Log.i("EFAIR","1destination!=null"+info.get(0).getxLoc()+","+info.get(0).getyLoc());
				    }else{
				    	info.add(end.get(0));
				    }
			       touch.createBitmap(t, bmp, map,markerX1,markerY1,Integer.parseInt(info.get(0).getxLoc())*16+8,
			    		              Integer.parseInt(info.get(0).getyLoc())*16+8,fromMarker,toMarker);
			    
		     
		    return info;  
	   }
	 
	 
	/*
	 * this method is used to create map with route with multiple entry/exit point of map and elevator.
	 */
	 private void createMapWithRoute(boolean flag){
		 //make query to database to select map file, start location and destination
		 //if floor value is same then draw map with route
		 //otherwise draw map with route start point to nearest elevator
		 //and initialize the steps string array.
		 try{
		 String exhibitorFrom=Integer.toString(extras.getInt("EXHIBITORIDFROM"));
		 String exhibitorTo=Integer.toString(extras.getInt("EXHIBITORIDTO"));
		 String exhibitorFromStallNo=extras.getString("EXHIBITORSTALLNOFROM");
		 String exhibitorToStallNo=extras.getString("EXHIBITORSTALLNOTO");
		 Log.i("EFAIR","exhibitor stallNo "+exhibitorFromStallNo+","+exhibitorToStallNo);
		 String fileName;
		 
		 List<Object> dataFrom=dbHelper.getDetailOfsourceAndDestExibitor(exhibitorFrom,exhibitorFromStallNo);
		 List<Object> dataTo=dbHelper.getDetailOfsourceAndDestExibitor(exhibitorTo,exhibitorToStallNo);
		 try{
			  location1=(ExhibitorLocation) dataFrom.get(0);
		      vmap1=(VenueMap) dataFrom.get(1);
		      exhib1=(Exhibitors) dataFrom.get(2);
		      location2=(ExhibitorLocation) dataTo.get(0);
		      vmap2=(VenueMap) dataTo.get(1);
		      exhib2=(Exhibitors) dataTo.get(2);
		      //get entry exit point of both exhibitors
			  entryExit=dbHelper.getExhibitorEntryExitPoint(Integer.toString(location1.getExhibitorLocationId()),Integer.toString(location2.getExhibitorLocationId()));
			 Log.i("EFAIR","Entry exit size "+entryExit.size()+"location id1- "+entryExit.get(0).getxLoc()+" ,id 2- "+location2.getExhibitorLocationId());
			  // parse location for From exit point and location for to entry point 
			  parseEntryExitPoint(entryExit, location1.getExhibitorLocationId(), location2.getExhibitorLocationId());
		 }catch(Exception e){
			 e.printStackTrace();
			 
		 }
		
		 if(vmap1.getFilePath().trim().equalsIgnoreCase(vmap2.getFilePath().trim())){
			//to and from is on the same map.
			 isSameFloor=0;
			 rightTextview.setVisibility(View.GONE);
			 
			 Log.i("EXPO","Source and destination on same map");
		 }else if((!vmap1.getFilePath().trim().equalsIgnoreCase(vmap2.getFilePath().trim()))&& (vmap1.getFloorName().trim().equalsIgnoreCase(vmap2.getFloorName().trim()))){
			 isSameFloor=1;//is based on database record
			 //create query to find multiple exit and entry point of exhibitors location
			 Log.i("EFAIR","Source and destination on same floor but diffrent map");
			 //Toast.makeText(getActivity(), "is same floor "+isSameFloor,Toast.LENGTH_LONG).show();
		 }else if((!vmap1.getFloorName().trim().equalsIgnoreCase(vmap2.getFloorName().trim()))&& 
				 ((!vmap1.getFilePath().trim().equalsIgnoreCase("hall-9-10-11-12.jpg")&&!vmap1.getFilePath().trim().equalsIgnoreCase("hall-14-15.jpg"))&&
						 (!vmap2.getFilePath().trim().equalsIgnoreCase("hall-9-10-11-12.jpg")&&!vmap2.getFilePath().trim().equalsIgnoreCase("hall-14-15.jpg")))){
			 //source and destination are on the different floor
			 //but source and destination can't be hall9_12.jpg
			 
			
			 //find the nearest elevator from database 
			 //initialize the eleX and eleY
			 isSameFloor=2;
			 Log.i("EFAIR","Source and destination  diffrent floor but not hall9_12 "+isSameFloor);
			// Toast.makeText(getActivity(), "is same floor "+isSameFloor,Toast.LENGTH_LONG).show();
			 
			 
			 
		 }else if((!vmap1.getFloorName().trim().equalsIgnoreCase(vmap2.getFloorName().trim()))&&
				 ((vmap1.getFilePath().trim().equalsIgnoreCase("hall-9-10-11-12.jpg"))||(vmap2.getFilePath().trim().equalsIgnoreCase("hall-9-10-11-12.jpg"))||(vmap1.getFilePath().trim().equalsIgnoreCase("hall-14-15.jpg")||vmap2.getFilePath().equalsIgnoreCase("hall-14-15.jpg")))){
			 isSameFloor=3;
			 //Toast.makeText(getActivity(), "is same floor "+isSameFloor,Toast.LENGTH_LONG).show();
			 Log.i("EFAIR","Source and destination on same floor but diffrent map "+isSameFloor);
		 }
		 
		// Create a Bitmap from the tilemap data
           fileName =vmap1.getFilePath().trim().replaceAll(".jpg",VenueMapWithLocation.CSVMAPFILE_EXTENTION);//extras.getString(EXTRA_IMAGE);////replace file venue1.getFilePath().trim().replaceAll(".jpg",VenueMapWithLocation.CSVMAPFILE_EXTENTION)
           Log.i("EXPO","Tmx fileName "+fileName);
     	//get filename,x and y location of exhibitor
     	 // Start the parser, get back TMX data object
         t = TMXLoader.readTMX(fileName, getActivity());
       
         createHashMap();
       
         //create steps 
         steps.add(exhib1.getExhibitorName());
         String stepsText="";
         MARKER_TYPE source=MARKER_TYPE.FROM_MARKER;
    	 MARKER_TYPE dest=MARKER_TYPE.TO_MARKER;
         ArrayList<FacilityInformation> from=new ArrayList<FacilityInformation>();
         if(isSameFloor==2){
        	// source and destination on the different floor
        	 FacilityInformation info=new FacilityInformation();
        	 
        	 info.setxLoc(""+x1);info.setyLoc(""+y1);
        	 from.add(info);
        	 dest=MARKER_TYPE.ELEVATOR_MARKER;
        	 Log.i("EFAIR","info.setxLoc(x1): "+from.get(0).getxLoc()+","+from.get(0).getyLoc());
        	// source and destination on the different floor
        	 if(vmap1.getType().equalsIgnoreCase("1")){
        	    eleLoc=dbHelper.getEntryExitBylocationId(Integer.toString(vmap1.getLocationId()),ELEVATOR_TYPE,"");
        	 }else{
        		 eleLoc=dbHelper.getEntryExitBylocationId(""+vmap1.getLocationId(),ELEVATOR_TYPE,"");
        		
        	 }
        	 
        	 
       /*Hard code changes for Home Expo*/ 
        	/* if(eleLoc.size()>2){
        		 eleLoc = new ArrayList<FacilityInformation>(eleLoc.subList(0, 2));
        	 }*/
        	
       /*Remove above code for fully dynamic*/
        	 
        	eleLoc = drawRoute(from, eleLoc,Integer.parseInt(location1.getxLocation())*16+8,Integer.parseInt(location1.getyLocation())*16+8,
                     eleX*16+8,eleY*16+8,source.toString(),dest.toString());
        	  
        	 eleX=Integer.parseInt(eleLoc.get(0).getxLoc());
        	 eleY=Integer.parseInt(eleLoc.get(0).getyLoc());
        	 stepsText+="From : "+exhib1.getExhibitorName()+", "+vmap1.getFloorName()+"\n";
        	 stepsText+="Take elevator "+eleLoc.get(0).getLocationName()+", "+vmap2.getFloorName();
        	 Log.i("EFAIR","position "+x1+","+y1+","+eleX+","+eleY);
        	 if(vmap2.getType().trim().equalsIgnoreCase("1")){
         	    eleLoc=dbHelper.getEntryExitBylocationId(Integer.toString(vmap2.getLocationId()),ELEVATOR_TYPE,eleLoc.get(0).getLocationName());
         	 }else{
         		 eleLoc=dbHelper.getEntryExitBylocationId(""+vmap2.getLocationId(),ELEVATOR_TYPE,eleLoc.get(0).getLocationName());
         	 }
        	 
        	 Log.i("EXPO","eleLoc "+eleLoc.size());
        	 
        	 secondMapsX=Integer.parseInt(eleLoc.get(0).getxLoc());
        	 secondMapsY=Integer.parseInt(eleLoc.get(0).getyLoc());
        	 secondMapGateName=eleLoc.get(0).getLocationName();
        	 source=MARKER_TYPE.ELEVATOR_MARKER;
        	 dest=MARKER_TYPE.TO_MARKER;
        	 sMarkerTypeFrom=source.toString();
        	 sMarkerTypeTo=dest.toString();
        	 
        	
        	 
         }else if(isSameFloor==3){
        	 MARKER_TYPE sSource = null,sDest=null;
        	// source and destination on the different floor
        	 
        	 Log.i("EFAIR","vmap1.getFilePath(): "+vmap1.getFilePath()+", Type: "+vmap1.getType());
        	 Log.i("EFAIR","vmap2.getFilePath(): "+vmap2.getFilePath()+", Type: "+vmap2.getType());
        	 
        	 
        	 if(vmap1.getFilePath().equalsIgnoreCase("hall-9-10-11-12.jpg")||vmap1.getFilePath().equalsIgnoreCase("hall-14-15.jpg")){
        		 eleLoc=dbHelper.getEntryExitBylocationId(Integer.toString(vmap1.getLocationId()),GATE_TYPE,"");
        		 stepsText+="From : "+exhib1.getExhibitorName()+", "+vmap1.getFloorName()+"\n";
            	
            	 dest=MARKER_TYPE.EXIT_MARKER;
            	
            	 FacilityInformation info=new FacilityInformation();
            	 
            	 info.setxLoc(""+x1);info.setyLoc(""+y1);
            	 from.add(info);
            	 eleX=Integer.parseInt(eleLoc.get(0).getxLoc());
             	 eleY=Integer.parseInt(eleLoc.get(0).getyLoc());
             	 
             	 eleLoc=drawRoute(from,eleLoc,Integer.parseInt(location1.getxLocation())*16+8,Integer.parseInt(location1.getyLocation())*16+8,
                        eleX*16+8,eleY*16+8,source.toString(),dest.toString());
             	 if(vmap1.getFilePath().equalsIgnoreCase("hall-9-10-11-12.jpg"))
             		eleLoc=dbHelper.getEntryExitBylocationId(""+dbHelper.getVenueMapDetailByType(""+4).get(0).getLocationId(),""+5,eleLoc.get(0).getLocationName());
             	 else
             		eleLoc=dbHelper.getEntryExitBylocationId(""+dbHelper.getVenueMapDetailByType(""+4).get(0).getLocationId(),""+6,eleLoc.get(0).getLocationName());
             	 stepsText+="Exit : "+eleLoc.get(0).getLocationName()+", Outer Halls";
             	
             	groundFloorEntryX=Integer.parseInt(eleLoc.get(0).getxLoc());
             	groundFloorEntryY=Integer.parseInt(eleLoc.get(0).getyLoc());
             	 Log.i("EFAIR","[START] "+eleLoc.get(0).getxLoc());
             	 
            	 source=MARKER_TYPE.ENTRY_MARKER;
            	 dest=MARKER_TYPE.ELEVATOR_MARKER;
            	 sSource=MARKER_TYPE.ELEVATOR_MARKER;
            	 sDest=MARKER_TYPE.TO_MARKER;
        	 }else if(vmap2.getFilePath().equalsIgnoreCase("hall-9-10-11-12.jpg")||vmap2.getFilePath().equalsIgnoreCase("hall-14-15.jpg")){
        		
        		 if(vmap1.getType().equalsIgnoreCase("1")){
             	    eleLoc=dbHelper.getEntryExitBylocationId(Integer.toString(vmap1.getLocationId()),ELEVATOR_TYPE,"");
             	 }else{
             		 eleLoc=dbHelper.getEntryExitBylocationId(""+vmap1.getLocationId(),ELEVATOR_TYPE,"");
             	 }
        		 
        		 FacilityInformation info=new FacilityInformation();
            	 
            	 info.setxLoc(""+x1);info.setyLoc(""+y1);
            	 from.add(info);
             	 eleX=Integer.parseInt(eleLoc.get(0).getxLoc());
             	 eleY=Integer.parseInt(eleLoc.get(0).getyLoc());
             	 dest=MARKER_TYPE.ELEVATOR_MARKER;
             	 eleLoc=drawRoute(from,eleLoc,Integer.parseInt(location1.getxLocation())*16+8,Integer.parseInt(location1.getyLocation())*16+8,
                        eleX*16+8,eleY*16+8,source.toString(),dest.toString());
             	// find elevator location on ground floor
             	  eleLoc=dbHelper.getEntryExitBylocationId(""+1,ELEVATOR_TYPE,eleLoc.get(0).getLocationName());
             	 
             	  stepsText+="From : "+exhib1.getExhibitorName()+", "+vmap1.getFloorName()
             			  +"\n Take : "+eleLoc.get(0).getLocationName()+", "+vmap2.getFloorName();
        	      source=MARKER_TYPE.ELEVATOR_MARKER;
        	      dest=MARKER_TYPE.EXIT_MARKER;
        	      sSource=MARKER_TYPE.ENTRY_MARKER;
        	      sDest=MARKER_TYPE.TO_MARKER;
        	 }
        	 
        	 secondMapsX=Integer.parseInt(eleLoc.get(0).getxLoc());
        	 secondMapsY=Integer.parseInt(eleLoc.get(0).getyLoc());
        	 secondMapGateName=""+eleLoc.get(0).getLocationName();
        	 mMarkerTypeFrom=source.toString();
        	 mMarkerTypeTo=dest.toString();
        	 sMarkerTypeFrom=sSource.toString();
        	 sMarkerTypeTo=sDest.toString();
        	 
        	
         }else if(isSameFloor==0){
        	 //source and destination on the same map
        	 steps.add(exhib2.getExhibitorName());
        	 stepsText+="From : "+exhib1.getExhibitorName()+"\nTo      : "+exhib2.getExhibitorName();
        	 drawRoute(x1, y1, x2, y2,Integer.parseInt(location1.getxLocation())*16+8,Integer.parseInt(location1.getyLocation())*16+8,
        			                      Integer.parseInt(location2.getxLocation())*16+8,
        			                      Integer.parseInt(location2.getyLocation())*16+8,source.toString(),dest.toString());
        	
         }else if(isSameFloor==1){
        	 //source and destination on the same floor but different map
        	 //find the exit point from first map
        	 ArrayList<FacilityInformation> data;
        	 stepsText+="From : "+exhib1.getExhibitorName()+", "+vmap1.getFloorName();
        	 dest=MARKER_TYPE.EXIT_MARKER;
	        	 if(vmap1.getFilePath().equalsIgnoreCase("hall-9-10-11-12.jpg")||vmap1.getFilePath().equalsIgnoreCase("hall-14-15.jpg")
	        			 ||vmap2.getFilePath().equalsIgnoreCase("hall-9-10-11-12.jpg")||vmap2.getFilePath().equalsIgnoreCase("hall-14-15.jpg")){
	        		 if(vmap1.getFilePath().equalsIgnoreCase("hall-9-10-11-12.jpg")){
	        			 data=dbHelper.getEntryExitBylocationId(""+vmap1.getLocationId(),""+1 	,"");
	        		 }else
	        			 data=dbHelper.getEntryExitBylocationId(""+vmap1.getLocationId(),MAIN_ENTRANCE_TYPE,""); 
	        		
	        	 }else{	        		 
        		  data=dbHelper.getEntryExitBylocationId(""+vmap1.getLocationId(),"1","");
	        	 }
	        	 
	        	 Log.i("EXPO", "LocationId "+vmap1.getLocationId());

	        	 /*Hard code changes for Home Expo*/ 
	        	/* if(data.size()>4){
	        		 data = new ArrayList<FacilityInformation>(data.subList(0, 4));
	        	 }*/
	        	 
	       /*Remove above code for fully dynamic*/
	        	 
        		 if(data.size()>0){
        			 FacilityInformation info=new FacilityInformation();
        			 info.setxLoc(""+x1);
        			 info.setyLoc(""+y1);
        			 from.add(info);
        			 from=drawRoute(from,data,Integer.parseInt(location1.getxLocation())*16+8,Integer.parseInt(location1.getyLocation())*16+8,
        					 Integer.parseInt(info.getxLoc())*16+8,Integer.parseInt(info.getyLoc())*16+8,source.toString(),dest.toString());
        		 }
        		 Log.i("EFAIR","setFacility "+from.get(0).getLocationName());
        		 int locationId=0;        		 
        			 locationId=vmap2.getLocationId();
        		 if(vmap1.getFilePath().equalsIgnoreCase("hall-9-10-11-12.jpg")||vmap1.getFilePath().equalsIgnoreCase("hall-14-15.jpg")
	        			 ||vmap2.getFilePath().equalsIgnoreCase("hall-9-10-11-12.jpg")||vmap2.getFilePath().equalsIgnoreCase("hall-14-15.jpg")){
        			 dbHelper.openDatabase(DatabaseHelper.READMODE);
        			 locationId = dbHelper.getVenueMapDetailByType(""+4).get(0).getLocationId();
        			 stepsText+="\nExit : "+from.get(0).getLocationName();
        			 if(vmap1.getFilePath().equalsIgnoreCase("hall-9-10-11-12.jpg"))
        				 data=dbHelper.getEntryExitBylocationId(""+locationId,"5",from.get(0).getLocationName());
        			 else
        				 data=dbHelper.getEntryExitBylocationId(""+locationId,"",from.get(0).getLocationName());
	        	 }else{
	        		 data=dbHelper.getEntryExitBylocationId(""+locationId,"1",from.get(0).getLocationName());
	        		 stepsText+="\n"+from.get(0).getLocationName()+", "+vmap1.getFloorName();
	        	 }
        		 
        		 FacilityInformation info=data.get(0);
        		 secondMapsX=Integer.parseInt(info.getxLoc());
        		 secondMapsY=Integer.parseInt(info.getyLoc());
        		 secondMapGateName=info.getLocationName();
        		 
        
        	 source=MARKER_TYPE.ENTRY_MARKER;
    		 dest=MARKER_TYPE.TO_MARKER;
    		 sMarkerTypeFrom=source.toString();
    		 sMarkerTypeTo=dest.toString();
    		 
         }
         //initialize steps view
         stepsTextView.setText(stepsText);
		 }catch(Exception e){
			 e.printStackTrace();
		 }
	    
	 }
	 
	 
	 private void parseEntryExitPoint(List<ExhibitorEntryExit> data,int exbLocFrom,int exbLocTo){
		 startLoc=new ArrayList<ExhibitorEntryExit>();
		 endLoc=new ArrayList<ExhibitorEntryExit>();
		 Log.i("EFAIR","parseEntryExitPoint size of data "+data.size());
		 for(int i=0;i<data.size();i++){
			 if(data.get(i).getExhibitorLocationId()==exbLocFrom /*&& data.get(i).getStatus().equalsIgnoreCase("exit")*/){
				try{
					startLoc.add(data.get(i)); 
					x1=Integer.parseInt(data.get(i).getxLoc().trim());
					y1=Integer.parseInt(data.get(i).getyLoc().trim());
					 Log.i("EFAIR","parseEntryExitPoint x1,y1 : "+x1+","+y1);
				}catch(Exception e){e.printStackTrace();}
			 }else if(data.get(i).getExhibitorLocationId()==exbLocTo /*&& data.get(i).getStatus().equalsIgnoreCase("entry")*/){
				try{
					endLoc.add(data.get(i)); 
					x2=Integer.parseInt(data.get(i).getxLoc().trim());
					y2=Integer.parseInt(data.get(i).getyLoc().trim());
					Log.i("EFAIR","parseEntryExitPoint x2,y2 : "+x2+","+y2);
				}catch(Exception e){e.printStackTrace();}
			 }
		 }
	 }
	 
	 @Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		HomeActivity.setTitle("Location");
	}
	 
	 public int getAvailableElevatorLocationId(String filePath){
		 if(filePath.equalsIgnoreCase("hall11_12_13_14.jpg")){
			 return 11;
		 }else{
			 return 7;
		 }
	 }
	 
	 public void setFacility(FacilityInformation info){
		 destination.setxLoc(info.getxLoc());
		 destination.setyLoc(info.getyLoc());
		 destination.setLocationName(info.getLocationName());
		 Log.i("EFAIR","setFacility "+info.getLocationName());
	 }
	 
	 
	public  static enum MARKER_TYPE{
		FROM_MARKER,
		TO_MARKER,
		ELEVATOR_MARKER,
		ENTRY_MARKER,
		EXIT_MARKER
		
	}


	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		
	}
	   
}
