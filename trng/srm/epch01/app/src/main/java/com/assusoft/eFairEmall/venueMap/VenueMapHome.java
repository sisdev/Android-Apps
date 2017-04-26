package com.assusoft.eFairEmall.venueMap;

import java.util.ArrayList;

import com.assusoft.eFairEmall.databaseMaster.DatabaseHelper;
import com.assusoft.eFairEmall.entities.ExhibitorLocation;
import com.assusoft.eFairEmall.entities.VenueMap;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class VenueMapHome extends Activity{
	/*SimpleSideDrawer slide_me;
	boolean flag=false;
	Button btnMenuHom;
	Button btnMenu,btnBack;
	DatabaseHelper dbHelper;
	ArrayList<ExhibitorLocation> locationList;
	TextView txtGroundFloor,txtFirstFloor,txtSecondFloor,txtThirdFloor,txtTemporaryHall,txtHall,txtMart;
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.venue_map_home);

			slide_me = new SimpleSideDrawer(this);//create the simple drawer object
			
			slide_me.setRightBehindContentView(R.layout.menu_venue_map);//set menu layout
			btnMenuHom=(Button) findViewById(R.id.btnHomeVenueMap);
			btnMenu =(Button) findViewById(R.id.btnMenuVenueMap);
			btnBack=(Button) findViewById(R.id.btnBackVenueMap);
			txtGroundFloor=(TextView) findViewById(R.id.txtGroundFloor);
			txtFirstFloor=(TextView) findViewById(R.id.txtFirstFloor);
			txtSecondFloor=(TextView) findViewById(R.id.txtSecondFloor);
			txtThirdFloor=(TextView) findViewById(R.id.txtThirdFloor);
			txtTemporaryHall=(TextView) findViewById(R.id.txtTemporaryHall);
			txtHall=(TextView) findViewById(R.id.txtHall);
			txtMart=(TextView) findViewById(R.id.txtMart);
			dbHelper=EFairEmallApplicationContext.getDatabaseHelper();
			//get the readable database
			dbHelper.openDatabase(DatabaseHelper.READMODE);
			btnBack.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					onBackPressed();
					
				}
			});
			btnMenu.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					enableMenuItem();
					flag=true;
					slide_me.toggleRightDrawer();
				}
			});
			btnMenuHom.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						disableMenuItem();
						
						Intent goHome=new Intent(getApplicationContext(),HomeActivity.class);
				    	startActivity(goHome);
				    	finish();
					}
				});
			txtGroundFloor.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent gotoVenueMap=new Intent(getApplicationContext(),VenueMapWithWebView.class);
					startActivity(gotoVenueMap);
					locationList=dbHelper.getLocation("1");
					VenueMap vMap=new VenueMap();
					vMap=locationList.get(0);
					Intent gotoVenueMap=new Intent(getApplicationContext(),VenueMapActivity.class);
					gotoVenueMap.putExtra("LOCATION_X",vMap.getxLocation());
					gotoVenueMap.putExtra("LOCATION_Y",vMap.getyLocation());
					gotoVenueMap.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			    	startActivity(gotoVenueMap);
				}
			});
			txtFirstFloor.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					locationList=dbHelper.getLocation("1538");
					VenueMap vMap=new VenueMap();
					vMap=locationList.get(0);
					Intent gotoVenueMap=new Intent(getApplicationContext(),VenueMapActivity.class);
					gotoVenueMap.putExtra("LOCATION_X",vMap.getxLocation());
					gotoVenueMap.putExtra("LOCATION_Y",vMap.getyLocation());
					gotoVenueMap.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			    	startActivity(gotoVenueMap);
				}
			});
			txtSecondFloor.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					locationList=dbHelper.getLocation("3");
					ExhibitorLocation exhiLoc=new ExhibitorLocation();
					exhiLoc=locationList.get(0);
					Intent gotoVenueMap=new Intent(getApplicationContext(),VenueMapActivity.class);
					gotoVenueMap.putExtra("LOCATION_X",exhiLoc.getxLocation());
					gotoVenueMap.putExtra("LOCATION_Y",exhiLoc.getyLocation());
					gotoVenueMap.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			    	startActivity(gotoVenueMap);
				}
			});
			txtThirdFloor.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					locationList=dbHelper.getLocation("1533");
					ExhibitorLocation exhiLoc=new ExhibitorLocation();
					exhiLoc=locationList.get(0);
					Intent gotoVenueMap=new Intent(getApplicationContext(),VenueMapActivity.class);
					gotoVenueMap.putExtra("LOCATION_X",exhiLoc.getxLocation());
					gotoVenueMap.putExtra("LOCATION_Y",exhiLoc.getyLocation());
			    	startActivity(gotoVenueMap);
				}
			});
			txtTemporaryHall.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					locationList=dbHelper.getLocation("3");
					ExhibitorLocation exhiLoc=new ExhibitorLocation();
					exhiLoc=locationList.get(0);
					Intent gotoVenueMap=new Intent(getApplicationContext(),VenueMapActivity.class);
					gotoVenueMap.putExtra("LOCATION_X",exhiLoc.getxLocation());
					gotoVenueMap.putExtra("LOCATION_Y",exhiLoc.getyLocation());
			    	startActivity(gotoVenueMap);
				}
			});
			txtHall.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					locationList=dbHelper.getLocation("3");
					ExhibitorLocation exhiLoc=new ExhibitorLocation();
					exhiLoc=locationList.get(0);
					Intent gotoVenueMap=new Intent(getApplicationContext(),VenueMapActivity.class);
					gotoVenueMap.putExtra("LOCATION_X",exhiLoc.getxLocation());
					gotoVenueMap.putExtra("LOCATION_Y",exhiLoc.getyLocation());
			    	startActivity(gotoVenueMap);
				}
			});
			txtMart.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					locationList=dbHelper.getLocation("3");
					ExhibitorLocation exhiLoc=new ExhibitorLocation();
					exhiLoc=locationList.get(0);
					Intent gotoVenueMap=new Intent(getApplicationContext(),VenueMapActivity.class);
					gotoVenueMap.putExtra("LOCATION_X",exhiLoc.getxLocation());
					gotoVenueMap.putExtra("LOCATION_Y",exhiLoc.getyLocation());
			    	startActivity(gotoVenueMap);
				}
			});
	 }
	 @Override
	    public void onBackPressed() {
	     disableMenuItem();	
		 if (slide_me.isShown() && flag) {
	            flag=false;
	    		slide_me.toggleDrawer();
	           
	        }
	        else {
	            super.onBackPressed();
	        }
	    }
	 
	 public void disableMenuItem(){
	    	
	    	if(btnMenuHom.isEnabled()){
	    		btnMenuHom.setEnabled(false);
	    	}
	    }
	    
	    public void enableMenuItem(){
	    	
	    	if(!btnMenuHom.isEnabled()){
	    		btnMenuHom.setEnabled(true);
	    	}
	    }*/
}
