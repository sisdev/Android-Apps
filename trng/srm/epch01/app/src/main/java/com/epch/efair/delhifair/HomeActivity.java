/**
 * @author 
 * Umesh singh Kushwaha
 */
package com.epch.efair.delhifair;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.assusoft.eFairEmall.databaseMaster.DatabaseHelper;
import com.assusoft.eFairEmall.util.MySharedPreferences;
import com.assusoft.eFairEmall.util.Util;
import com.assusoft.efair.epchfair.Fragments.ConclaveFirstFragment;
import com.assusoft.efair.epchfair.Fragments.ConclaveSecondFragment;
import com.assusoft.efair.epchfair.Fragments.ExhibitorDetailFragment;
import com.assusoft.efair.epchfair.Fragments.Exhibitors;
import com.assusoft.efair.epchfair.Fragments.Favorites;
import com.assusoft.efair.epchfair.Fragments.FeedbackFragment;
import com.assusoft.efair.epchfair.Fragments.Map;
import com.assusoft.efair.epchfair.Fragments.NewsFragment;
import com.assusoft.efair.epchfair.Fragments.PhotoShoot;
import com.assusoft.efair.epchfair.Fragments.PhotoshootDetail;
import com.assusoft.efair.epchfair.Fragments.Planner;
import com.assusoft.efair.epchfair.Fragments.SearchExhibitorsFragment;
import com.assusoft.efair.epchfair.Fragments.SearchFragment;
import com.assusoft.efair.epchfair.Fragments.SearchHallFragment;
import com.assusoft.efair.epchfair.Fragments.SearchProductFragment;
import com.assusoft.efair.epchfair.Fragments.SeminarCalendar;
import com.assusoft.efair.epchfair.Fragments.To_From_Custom;
import com.assusoft.efair.epchfair.Fragments.UsefulInfo;
import com.assusoft.efair.epchfair.Fragments.UsefulInfoDelhifairFragment;
import com.assusoft.efair.epchfair.Fragments.UtilitiesFragment;
import com.assusoft.efair.epchfair.Fragments.VenueMapImageVewFragment;
import com.assusoft.efair.epchfair.Fragments.VenueMapWithLocation;
import com.assusoft.efair.epchfair.Fragments.VisitorRegistatrationFragment;
import com.assusoft.efair.epchfair.Fragments.WebViewFragment;
import com.assusoft.efair.epchfair.gcm.CommonUtilities;
import com.assusoft.efair.epchfair.gcm.ServerUtilities;
import com.assusoft.efair.epchfair.googleMap.GoogleMapFragment;
import com.assusoft.efair.epchfair.googleMap.HowToReachGMap;
import com.assusoft.efair.epchfair.googleMap.HowToReachRoutes;
import com.assusoft.efair.epchfair.googleMap.Place;
import com.assusoft.efair.epchfair.googleMap.PlacesList;
import com.assusoft.efair.qrcode.CaptureActivity1;
import com.epch.efair.delhifair.HomeAcitityFirst.Fragments;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
//import com.assusoft.efair.packplus.Fragments.FbFragment;


public class HomeActivity extends ActionBarActivity {
	// http://youtu.be/F_cpzF8fmMI
	
	public static boolean isDelhifair=true;
	
	static boolean softKeyboardFlag = false;
	public static int productsId;
	public static int exhibitorsID;
	public static String searchStringOnBack;
	public static String searchOptionOnBack;
	public static boolean isMatchMaking=false;//if searches by match making 
	public static boolean isLinkedInLogin=false;//linked login flag 
	public  static int goForExhibitorDatails=0;//1-search,2-event scheduler
	public  static int goForSearchResult=0;//1-for search fragment,2-match making
	MySharedPreferences mySharedPreferences;//reference of mySharedPreferences
	//event page flag
	public static int goForEvents=0;//0 from home, 1 from event scheduler
	//products flag 
	public static int goForProducts=0;//0 from home, 1 from event scheduler
	//booking reminder flag
	public static int goForBooking =0;//0 from event ,1 from exhibitors,2 from exhibitor search result,3 from eventSearchReasult
	//events details flag
	public static int goForEventsDetails=0;//0 from firstfragment ,1 from second fragment,2 from eventSearch
	public static boolean gMApFlag=true;//Used in google map when u exit from the application
	public static int aboutDetailsFlag=0;

	public static int venueMapWithLocationOnBack=0;//handle back to open other fragments
    
	//used in google map when press back
	public static double lat,lng;
	public static String type;
	public static String name;
	public static int eventId;
	//MyTask objMyTask;//Used in creating async task.
	public static List<Place> placesD=null;
	//--------------------------------------
	private DrawerLayout mDrawerLayout;	
	private ActionBarDrawerToggle mDrawerToggle;	
	private LinearLayout mDrawer ;	
	private List<HashMap<String,String>> mList ;	
	private SimpleAdapter mAdapter;
	//Activity activity;
	View actionBarTitleLayout;
	LayoutInflater inflater;
    static TextView actionBarTitle;
	ListView panelListView;
	//private ArrayAdapter<String> panelListAdapter ;
	private PanelLvAdapter panelListAdapter;
	ImageView imageViewHome;
	Fragment fragment1;
	//THIS IS USED BY QR ENCODER ACTIVITY
	public static WindowManager manager;
	
	//gcm related fields 
	GoogleCloudMessaging gcm;
    AtomicInteger msgId = new AtomicInteger();
    Context context;

    String regid;
    public static Stack<String>titleStack;
    
    static Context context1;//this is used to get the instance of fragement transation
    Intent intent;
    private DatabaseHelper helper;
    private Bundle bundle;
    
	@Override
	protected void onCreate(Bundle savedInstanceState){
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//instantiate title
		titleStack=new Stack<String>();
		 context1=HomeActivity.this; 
		//call app rater 
		 AppRater.app_launched(this);
		 
		  //checking and creating my sharedPreferences obj
		  if(Commons.mySharedPreferences!=null)
	      {
	    	  mySharedPreferences=Commons.mySharedPreferences;
	      }else{
	    	  mySharedPreferences=new MySharedPreferences(this);
	    	  Commons.mySharedPreferences=mySharedPreferences;
	      }
		try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.assusoft.efair.packplus", 
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.i("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                }
        } catch (NameNotFoundException e) {
           e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
           e.printStackTrace();
        }
        
		manager = (WindowManager) getSystemService(WINDOW_SERVICE);
		

		setContentView(R.layout.activity_main1);
		
		helper = EFairEmallApplicationContext.getDatabaseHelper();
		bundle = new Bundle();
		        
     /***********************************************************************************
      * inflate Action bar title
	  */     
		        LayoutInflater layoutInflater = (LayoutInflater)(HomeActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
		        actionBarTitleLayout = layoutInflater.inflate(R.layout.action_bar, null); 
		        actionBarTitle=(TextView) actionBarTitleLayout.findViewById(R.id.txtTitleActionBar);
		        imageViewHome=(ImageView) actionBarTitleLayout.findViewById(R.id.imagviewHome);
		        //imageViewAssuLogo=(ImageView) actionBarTitleLayout.findViewById(R.id.imagviewAssuLogo);
		        //actionBarTitle.setGravity(Gravity.CENTER | Gravity.BOTTOM);     
		       /* FragmentManager fm = HomeActivity.this.getSupportFragmentManager();
		        FragmentTransaction ft = fm.beginTransaction(); 
		        HomeFragment fragment= new HomeFragment();
		        ft.add(R.id.content_frame, fragment,"Home");
		        ft.commit();  */
		        // Setting event listener for the drawer
		       // mDrawerLayout.setDrawerListener(mDrawerToggle);
	/**********************************************************************************
	 * Customization of Action bar	        
	 */     
		        getSupportActionBar().setIcon(R.color.buttonbackground); 
		        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM); 
		        getSupportActionBar().setCustomView(actionBarTitleLayout);
		        if(isDelhifair)
		        	getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.ihfg_header));
		        else
		        	getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.blue_header));
		     // Enabling Up navigation
		        getSupportActionBar().setDisplayHomeAsUpEnabled(false);     
		        
		        getSupportActionBar().setDisplayShowHomeEnabled(true);
		       
		/*********************************************************************************************
		* Click handling on Home     
		*/   
		        imageViewHome.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
						FragmentManager fm = HomeActivity.this.getSupportFragmentManager();
						//clear all backstack 
						
						fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
				       
						
						
						finish();
				        
					}
				});
		  
		        /*********************************************************************************************
				* Click handling on On menu item     
				*/
		        
		        
		         intent=getIntent();
		        String flag=intent.getStringExtra("FLAG");
		        loadFragment(flag);
		
	}
	private void loadFragment(String flag){
		Fragments fragment=Fragments.valueOf(flag);
		switch (fragment) {
		case EXHIBITORS:
	    
			exhibitors();
			break;
		case VENUEMAP:
			 gotoVenuMap();
			break;
		case NEARBY:
			nearBy();
			break;
		case QRCODE:
			gotoMyQR();
	        break;
		case MAP:
			map();
			break;
		case LOCATE:
			locate();
			break;
		case FAVORITES:
			favorites();
			break;
		case PLANNER:
			planner();
			break;
		case PRODUCTSELECTION:
			productSelection();
			break;
		case  SOCIALMEDIA:
			socialMedia();
			break;
		case FEEDBACK:
			feedback();
			break;
		case USEFULINFO:
			usefulInfo();
			break;
		case PHOTOSHOOT:
			gotoPhotoShoot();
			break;
		case VENUEMAPQR:
			gotoLocate();
			break;
		case EXHIBITORDETAILS:
			gotoExhibitorDetails();
			break;
		case INFO:
			info();
			break;
		case VISITOR_REGIS:
			visitorRegistration();
			break;
		case ABOUTIHGF:
			aboutIHGF();
			break;
		case CONTACTUS:
			contactUs();
			break;
		case USEFUL_INFO_DELHIFAIR:
			usefulInfoDelhifair();
			break;
		default:
			break;
		}
	}
	//it is called from QR COde scanner screen
	public void gotoPhotoShoot(){
		FragmentTransaction ft=HomeActivity.getSupportFragmentManager1();
		
		PhotoshootDetail album=new PhotoshootDetail();
		ft.replace(R.id.content_frame,album);
		fragment1=album;
		album.setArguments(intent.getBundleExtra("DATA"));
		ft.addToBackStack(null);
		ft.commit();
		
	}
	public void gotoLocate(){
		FragmentTransaction ft=HomeActivity.getSupportFragmentManager1();
		VenueMapWithLocation fragment = new VenueMapWithLocation();
		fragment1=fragment;
        fragment.setArguments(intent.getBundleExtra("DATA"));
        ft.replace(R.id.content_frame, fragment,"Products");
       
        ft.addToBackStack(null);
		ft.commit();
       
		
	}
	public void gotoExhibitorDetails(){
		FragmentTransaction ft=HomeActivity.getSupportFragmentManager1();
		ExhibitorDetailFragment fragment = new ExhibitorDetailFragment();
        
		fragment1=fragment;
        fragment.setArguments(intent.getBundleExtra("DATA"));
        ft.replace(R.id.content_frame, fragment,"Products");
       
        ft.addToBackStack(null);
		ft.commit();
       
		
	}
	/****************************************************************************************8
	 * Calling several fragments when click on buttons
	 * @param v
	 */
	public void conclave(View v){
    	//actionBarTitle.setText("About");
    	//mDrawerLayout.closeDrawers();
		Context fm =(v.getContext());
		FragmentTransaction ft = ((FragmentActivity)fm).getSupportFragmentManager().beginTransaction();
        
        ConclaveFirstFragment fragment = new ConclaveFirstFragment();
        ft.replace(R.id.content_frame, fragment);
        ft.addToBackStack(null);
        ft.commit();
	}
	public void news(View v){
    	//actionBarTitle.setText("About");
    	//mDrawerLayout.closeDrawers();
		Context fm =(v.getContext());
		FragmentTransaction ft = ((FragmentActivity)fm).getSupportFragmentManager().beginTransaction();
        
        NewsFragment fragment = new NewsFragment();
        ft.replace(R.id.content_frame, fragment);
        ft.addToBackStack(null);
        ft.commit();
	}
	
	public void gotoMedia(View v){
		//actionBarTitle.setText("Media");
		  // mDrawerLayout.closeDrawers();
			Context fm =(v.getContext());
			FragmentTransaction ft = ((FragmentActivity)fm).getSupportFragmentManager().beginTransaction();
	        
			ConclaveSecondFragment fragment = new ConclaveSecondFragment();
	        ft.replace(R.id.content_frame, fragment,"Products");
	        ft.addToBackStack(null);
	        ft.commit();
	}
	
	public void search(View v){
		//actionBarTitle.setText("Search");
		   //mDrawerLayout.closeDrawers();
			Context fm =(v.getContext());
			FragmentTransaction ft = ((FragmentActivity)fm).getSupportFragmentManager().beginTransaction();
	        
			SearchFragment fragment = new SearchFragment();
	        ft.replace(R.id.content_frame, fragment,"Search");
	        ft.addToBackStack(null);
	        ft.commit();
	}
	public void exhibitors(){
		Util.copyDatabase(this, DatabaseHelper.DBNAME);
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		//exportDB();
		Exhibitors fragment = new Exhibitors();
		fragment1=fragment;
        ft.replace(R.id.content_frame, fragment,null);
        ft.addToBackStack(null);
        ft.commit();
	}
	public void nearBy(){
		
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			
			GoogleMapFragment fragment = new GoogleMapFragment();
			//getSupportFragmentManager().popBackStack();
			fragment1=fragment;
	        ft.replace(R.id.content_frame, fragment,"NearBy");
	       // ft.addToBackStack(null);
	        ft.commit();
	}
	
	public void gotoMyQR(){
		
		   	Intent intent=new Intent(this,CaptureActivity1.class);
			intent.putExtra("SCANFLAG", true);
			startActivity(intent);
	}
	
	
	public void gotoVenuMap(){
		
			FragmentTransaction ft =getSupportFragmentManager().beginTransaction();
	        
			//VenuMapFragment fragment = new VenuMapFragment();
			VenueMapImageVewFragment fragment = new VenueMapImageVewFragment();
			fragment1=fragment;
	        ft.replace(R.id.content_frame, fragment,"VenueMap");
	        ft.addToBackStack(null);
	        ft.commit();
	}
	public void map(){
		actionBarTitle.setText("Layouts");
	  
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        
		//VenuMapFragment fragment = new VenuMapFragment();
		Map fragment = new Map();
        ft.replace(R.id.content_frame, fragment,"VenueMap");
        ft.addToBackStack(null);
        fragment1=fragment;
        ft.commit();
	}
	public void locate(){
		
		
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
	        
			//To_From fg=new To_From();
			To_From_Custom fg=new To_From_Custom();
		    ft.replace(R.id.content_frame, fg,"");
		    fragment1=fg;
		    ft.addToBackStack(null);
	        ft.commit();
	}
	public void favorites(){
		
			FragmentTransaction ft =getSupportFragmentManager().beginTransaction();
	        
			
			Favorites fragment = new Favorites();
	        ft.replace(R.id.content_frame, fragment,"VenueMap");
	        fragment1=fragment;
	        ft.addToBackStack(null);
	        ft.commit();
	}
	public void planner(){
		
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
	        
			
			Planner fragment = new Planner();
	        ft.replace(R.id.content_frame, fragment,"VenueMap");
	        fragment1=fragment;
	        ft.addToBackStack(null);
	        ft.commit();
	}
	public void socialMedia(){
			
			FragmentTransaction ft =getSupportFragmentManager().beginTransaction();
	        
			
			UtilitiesFragment fragment = new UtilitiesFragment();
	        ft.replace(R.id.content_frame, fragment,"VenueMap");
	        fragment1=fragment;
	        ft.addToBackStack(null);
	        ft.commit();
	}
	public void productSelection(){
		
			FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
				PhotoShoot fg=new PhotoShoot();
				ft.replace(R.id.content_frame, fg,"");
				fragment1=fg;
				ft.addToBackStack(null);
				ft.commit();
	}
	public void feedback(){
		
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
	        
			//VenuMapFragment fragment = new VenuMapFragment();
			FeedbackFragment fragment = new FeedbackFragment();
	        ft.replace(R.id.content_frame, fragment,"VenueMap");
	        fragment1=fragment;
	        ft.addToBackStack(null);
	        ft.commit();
	}
	public void usefulInfo(){
		actionBarTitle.setText("Useful Info");
		  
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        
		//VenuMapFragment fragment = new VenuMapFragment();
		SeminarCalendar fragment = new SeminarCalendar();
        ft.replace(R.id.content_frame, fragment,"Seminar Calendar");
        ft.addToBackStack(null);
        fragment1=fragment;
        ft.commit();
	}
	public void info(){
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		UsefulInfo fragment = new UsefulInfo();
        ft.replace(R.id.content_frame, fragment,"Fair Services");
        ft.addToBackStack(null);
        fragment1=fragment;
        ft.commit();
	}
	
	public void contactUs(){
		bundle.clear();
		helper.openDatabase(DatabaseHelper.READMODE);
		bundle.putString("URL", helper.getLinkURLByLinkName("ContactUsIHGF"));
		bundle.putString("TITLE", "Contact Us");
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		WebViewFragment fragment = new WebViewFragment();
		fragment.setArguments(bundle);
        ft.replace(R.id.content_frame, fragment,"AboutIHGFFragment");
        ft.addToBackStack(null);
        fragment1=fragment;
        ft.commit();
	}
	
	public void aboutIHGF(){
		bundle.clear();
		helper.openDatabase(DatabaseHelper.READMODE);
		bundle.putString("URL", helper.getLinkURLByLinkName("AboutIHGF"));
		bundle.putString("TITLE", "About IHGF");
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		WebViewFragment fragment = new WebViewFragment();
		fragment.setArguments(bundle);
        ft.replace(R.id.content_frame, fragment,"AboutIHGFFragment");
        ft.addToBackStack(null);
        fragment1=fragment;
        ft.commit();
	}
	
	public void visitorRegistration(){
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		VisitorRegistatrationFragment fragment = new VisitorRegistatrationFragment();
        ft.replace(R.id.content_frame, fragment,"VisitorRegistatrationFragment");
        ft.addToBackStack(null);
        fragment1=fragment;
        ft.commit();
	}
	
	public void usefulInfoDelhifair(){
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		UsefulInfoDelhifairFragment fragment = new UsefulInfoDelhifairFragment();
        ft.replace(R.id.content_frame, fragment,"UsefulInfoDelhifairFragment");
        ft.addToBackStack(null);
        fragment1=fragment;
        ft.commit();
}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		return super.onOptionsItemSelected(item);
	}	
			
	
	@Override
	public void onBackPressed() {
		
		 FragmentManager fm = getSupportFragmentManager();
		 Fragment fragment=fm.findFragmentById(R.id.content_frame);
		 FragmentTransaction ft = ((FragmentActivity)this).getSupportFragmentManager().beginTransaction();
		
		 try{				 
			 if(fragment.getClass().equals(fragment1.getClass())){
				 fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
	    	     finish();	    	
		    	 
		     }else if(fragment instanceof PlacesList){
		    	 try{
		    		 placesD=null;
		    		 ft.replace(R.id.content_frame, new GoogleMapFragment(), "GoogleMapFragment").commit();
		    	 }catch(Exception e){}
		    	 
		     }else if(fragment instanceof HowToReachGMap){
		    	 try{
		    		 ft.replace(R.id.content_frame, new VenueMapImageVewFragment(), "VenueMapImageVewFragment").commit();
		    	 }catch(Exception e){}
		    	 
		     }else if(fragment instanceof HowToReachRoutes){
		    	 try{
		    		 ft.replace(R.id.content_frame, new HowToReachGMap(), "HowToReachGMap").commit();
		    	 }catch(Exception e){
		    		 e.printStackTrace();
		    	 }
		    	 
		     }else{
		    	 fm.popBackStack(); 
		    	 if(fm.getBackStackEntryCount()==0){
		    		finish();
		    	 }
		     }
		 
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		 
		 if(fragment instanceof SearchExhibitorsFragment || fragment instanceof SearchProductFragment || fragment instanceof SearchHallFragment){
			 fm.popBackStack();
			 ft.replace(R.id.content_frame, new Exhibitors(), "Exhibitors").commit();
		 }
		 
		 
		
	}
	

	/*******************************************************************************************
	 * OnAttachEvent when a fragment added
	 */
	@Override
	public void onAttachFragment(Fragment fragment) {
	 
	}
	
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		context = getApplicationContext();

        // Check device for Play Services APK. If check succeeds, proceed with GCM registration.
        if (checkPlayServices()) {
            gcm = GoogleCloudMessaging.getInstance(this);
            regid = getRegistrationId(context);

            if (regid==null || regid.equals("")) {
                registerInBackground();
            }else{
            	int status=getServerRegistrationStatus();
            	if(status==0){
            		//try to register on third party server
            		sendRegistrationIdToBackend();
            	}
            }
        } else {
            Log.i(CommonUtilities.TAG, "No valid Google Play Services APK found.");
        }
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		actionBarTitle.setText("EPCH");
		// Check device for Play Services APK.
        checkPlayServices();
//        Commons.getMyTracker(this, "Home Screen");
	}
	
	@Override
	protected void onDestroy() {		
		super.onDestroy();
		gMApFlag=false;
		Log.i("BOOKFAIR", "onDestroy method");
		
	}
	/**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                       CommonUtilities.PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(CommonUtilities.TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    /**
     * Stores the registration ID and the app versionCode in the application's
     * {@code SharedPreferences}.
     *
     * @param context application's context.
     * @param regId registration ID
     */
    private void storeRegistrationId(Context context, String regId) {
        final SharedPreferences prefs = getGcmPreferences(context);
        float appVersion = getAppVersion(context);
        Log.i(CommonUtilities.TAG, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(CommonUtilities.PROPERTY_REG_ID, regId);
        editor.putFloat(CommonUtilities.PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }

    /**
     * Gets the current registration ID for application on GCM service, if there is one.
     * <p>
     * If result is empty, the app needs to register.
     *
     * @return registration ID, or empty string if there is no existing
     *         registration ID.
     */
    private String getRegistrationId(Context context) {
        final SharedPreferences prefs = getGcmPreferences(context);
        String registrationId = prefs.getString(CommonUtilities.PROPERTY_REG_ID, "");
        if (registrationId==null || registrationId.equals("")) {
            Log.i(CommonUtilities.TAG, "Registration not found.");
            return "";
        }
        // Check if app was updated; if so, it must clear the registration ID
        // since the existing regID is not guaranteed to work with the new
        // app version.
        float registeredVersion = prefs.getFloat(CommonUtilities.PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        float currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(CommonUtilities.TAG, "App version changed.");
            return "";
        }
        return registrationId;
    }

    /**
     * Registers the application with GCM servers asynchronously.
     * <p>
     * Stores the registration ID and the app versionCode in the application's
     * shared preferences.
     */
    private void registerInBackground() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(context);
                    }
                    regid = gcm.register(CommonUtilities.SENDER_ID);
                    msg = "Device registered, registration ID=" + regid;

                    // You should send the registration ID to your server over HTTP, so it
                    // can use GCM/HTTP or CCS to send messages to your app.
                    sendRegistrationIdToBackend();

                    // For this demo: we don't need to send it because the device will send
                    // upstream messages to a server that echo back the message using the
                    // 'from' address in the message.

                    // Persist the regID - no need to register again.
                    storeRegistrationId(context, regid);
                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                    // If there is an error, don't just keep trying to register.
                    // Require the user to click a button again, or perform
                    // exponential back-off.
                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                //mDisplay.append(msg + "\n");
            }
        }.execute(null, null, null);
    }

	

    /**
     * @return Application's version code from the {@code PackageManager}.
     */
    public static float getAppVersion(Context context) {
        try {
            /*PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            Log.i("EFAIR",""+packageInfo.versionName);
            return packageInfo.versionCode;*/
        	float versionName;
        	String version;
        	
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            Log.i("EFAIR","version name "+packageInfo.versionName);
            version= packageInfo.versionName;//.split("\\.");
            versionName= Float.parseFloat(version.toString());
            Log.i("EFAIR","splitted version name "+versionName);
            return versionName;
            
        } catch (NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    /**
     * @return Application's {@code SharedPreferences}.
     */
    public SharedPreferences getGcmPreferences(Context context) {
        // This sample app persists the registration ID in shared preferences, but
        // how you store the regID in your app is up to you.
        return getSharedPreferences(MainActivity.class.getSimpleName(),
                Context.MODE_PRIVATE);
    }
    /**
     * Sends the registration ID to your server over HTTP, so it can use GCM/HTTP or CCS to send
     * messages to your app. 
     */
    private void sendRegistrationIdToBackend() {
      // Your implementation here.
    	int status=ServerUtilities.register(this,"Assusoft", "contacts@assusoft.com", regid);
    	storeServerRegistrationStatus(getApplicationContext(), status);
    }
    //this method is used to check the status of third party server registration
    private void storeServerRegistrationStatus(Context context, int status) {
        final SharedPreferences prefs = getGcmPreferences(context);
      
       
        SharedPreferences.Editor editor = prefs.edit();
        
        editor.putInt(CommonUtilities.SERVER_REGISTRATION_STATUS,status);
        editor.commit();
    }
    //get the third party server registration status
    private int getServerRegistrationStatus(){
    	final SharedPreferences prefs = getGcmPreferences(context);
        int status = prefs.getInt(CommonUtilities.SERVER_REGISTRATION_STATUS,0);
        
       
    	return status;
    }
 

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	return super.onKeyDown(keyCode, event);
    }
    
    public static FragmentTransaction getSupportFragmentManager1(){
    	
    	return ((FragmentActivity) context1).getSupportFragmentManager().beginTransaction();
    }
    
    
    public static void setTitle(String title){
    	EFairEmallApplicationContext.tracker().setScreenName(title);
    	EFairEmallApplicationContext.tracker().send(new HitBuilders.ScreenViewBuilder().build());
    	actionBarTitle.setText(title);
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
    	
    	super.onSaveInstanceState(outState);
    	
    }
    
    public static void hideSoftKeyboard(Activity activity) {
    	try{
		    InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
		    softKeyboardFlag = inputMethodManager.isAcceptingText();
		    if(softKeyboardFlag){
		    	inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
		    	softKeyboardFlag = false;
		    }
    	}catch(Exception e){
    		e.printStackTrace();
    	}
	}
    
   /* private void exportDB(){
  		File sd = Environment.getExternalStorageDirectory();
  	      	File data = Environment.getDataDirectory();
  	       FileChannel source=null;
  	       FileChannel destination=null;
  	       String currentDBPath = "/data/"+ getPackageName() +"/databases/"+ DatabaseHelper.DBNAME;
  	       String backupDBPath = DatabaseHelper.DBNAME;
  	       File currentDB = new File(data, currentDBPath);
  	       File backupDB = new File(sd, backupDBPath);
  	       try {
  	            source = new FileInputStream(currentDB).getChannel();
  	            destination = new FileOutputStream(backupDB).getChannel();
  	            destination.transferFrom(source, 0, source.size());
  	            source.close();
  	            destination.close();
  	            Toast.makeText(this, "DB Exported!", Toast.LENGTH_SHORT).show();
  	        } catch(IOException e) {
  	        	e.printStackTrace();
  	        }
  	}*/
   
}


