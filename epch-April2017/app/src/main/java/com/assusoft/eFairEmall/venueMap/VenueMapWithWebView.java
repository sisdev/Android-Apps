package com.assusoft.eFairEmall.venueMap;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

public class VenueMapWithWebView extends Activity{
	
	/*SimpleSideDrawer slide_me;
	boolean flag=false;
	Button btnMenuHome,btnMenuGF,btnMenuFF,btnMenuSF,btnMenuTF,btnMenuTempH;
	Button btnMenu,btnBack;
	
	WebView webView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.venu_map_with_webview);
		//set the sliding menu
		slide_me = new SimpleSideDrawer(this);//create the simple drawer object
		
		slide_me.setRightBehindContentView(R.layout.menu_venue_map_webview);//set menu layout
		btnMenuHome=(Button) findViewById(R.id.btnHomeVenueMapWeb);
		btnMenu =(Button) findViewById(R.id.btnMenuVenumapWeb);
		btnBack=(Button) findViewById(R.id.btnBackVenumapWeb);
		//get the id of menu buttons
		btnMenuFF=(Button) findViewById(R.id.btnVenueMapFirstFloor);
		btnMenuGF=(Button) findViewById(R.id.btnVenueMapGroundFloor);
		btnMenuSF=(Button) findViewById(R.id.btnVenueMapSecondFloor);
		btnMenuTF=(Button) findViewById(R.id.btnVenueMapThirdFloor);
		btnMenuTempH=(Button) findViewById(R.id.btnVenueMapTempHall);
		
		//find id of webview
		webView=(WebView) findViewById(R.id.webViewVenumap);
		WebSettings webviewSettings=webView.getSettings();
		//enable the zoom control
		webviewSettings.setBuiltInZoomControls(true);
		webView.loadDataWithBaseURL("file:///android_res/drawable/", 
				"<img src='hall1.jpg' />", "text/html", "utf-8", null);
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
		btnMenuHome.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					 disableMenuItem();
					Intent goHome=new Intent(getApplicationContext(),HomeActivity.class);
			    	startActivity(goHome);
			    	finish();
				}
			});
		btnMenuFF.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				 disableMenuItem();
				webView.loadDataWithBaseURL("file:///android_res/drawable/", 
						"<img src='firstfloor1.jpg' />", "text/html", "utf-8", null);
				slide_me.toggleLeftDrawer();
			}
		});
		btnMenuGF.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				 disableMenuItem();
				webView.loadDataWithBaseURL("file:///android_res/drawable/", 
						"<img src='ground.jpg' />", "text/html", "utf-8", null);
				slide_me.toggleLeftDrawer();
			}
		});
		btnMenuSF.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				 disableMenuItem();
				webView.loadDataWithBaseURL("file:///android_res/drawable/", 
						"<img src='hall3.jpg' />", "text/html", "utf-8", null);
				slide_me.toggleLeftDrawer();
			}
		});
		btnMenuTF.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				 disableMenuItem();
				webView.loadDataWithBaseURL("file:///android_res/drawable/", 
						"<img src='hall4.jpg' />", "text/html", "utf-8", null);
				slide_me.toggleLeftDrawer();
			}
		});
		btnMenuTempH.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				 disableMenuItem();
				webView.loadDataWithBaseURL("file:///android_res/drawable/", 
						"<img src='hall9.jpg' />", "text/html", "utf-8", null);
				slide_me.toggleLeftDrawer();
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
	    	if(btnMenuHome.isEnabled()){
	    		btnMenuHome.setEnabled(false);
	    	}
	    	if(btnMenuFF.isEnabled()){
	    		btnMenuFF.setEnabled(false);
	    	}
	    	if(btnMenuGF.isEnabled()){
	    		btnMenuGF.setEnabled(false);
	    	}
	    	if(btnMenuSF.isEnabled()){
	    		btnMenuSF.setEnabled(false);
	    	}
	    	if(btnMenuTempH.isEnabled()){
	    		btnMenuTempH.setEnabled(false);
	    	}
	    	if(btnMenuTF.isEnabled()){
	    		btnMenuTF.setEnabled(false);
	    	}
	    }
	    
	    public void enableMenuItem(){
	    	if(!btnMenuHome.isEnabled()){
	    		btnMenuHome.setEnabled(true);
	    	}
	    	if(!btnMenuFF.isEnabled()){
	    		btnMenuFF.setEnabled(true);
	    	}
	    	if(!btnMenuGF.isEnabled()){
	    		btnMenuGF.setEnabled(true);
	    	}
	    	if(!btnMenuSF.isEnabled()){
	    		btnMenuSF.setEnabled(true);
	    	}
	    	if(!btnMenuTempH.isEnabled()){
	    		btnMenuTempH.setEnabled(true);
	    	}
	    	if(!btnMenuTF.isEnabled()){
	    		btnMenuTF.setEnabled(true);
	    	}
	    }
*/
}
