package com.assusoft.eFairEmall.venueMap;


import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

public class VenueMapWithLocation extends Activity {
/*
	SimpleSideDrawer slide_me;
	boolean flag=false;
	WebView wv;
	Button btnMenu,btnBack,btnMenuHome;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.venu_map_with_webview);
		//set the sliding menu
		slide_me = new SimpleSideDrawer(this);//create the simple drawer object
				
		slide_me.setRightBehindContentView(R.layout.menu_info);//set menu layout
		btnMenuHome=(Button) findViewById(R.id.btnHomeCatelogue);
		btnMenu =(Button) findViewById(R.id.btnMenuVenumapWeb);
		btnBack=(Button) findViewById(R.id.btnBackVenumapWeb);
		
		wv=(WebView) findViewById(R.id.webViewVenumap);
		final MyJavaScriptInterface myJavascriptInterface=new MyJavaScriptInterface(this);
		//add the javascript interface
		wv.addJavascriptInterface(myJavascriptInterface,"AndroidFunction");
		WebSettings ws=wv.getSettings();//get the object of web settings
		ws.setJavaScriptEnabled(true);//enable javascript
		ws.setBuiltInZoomControls(true);//set the buildin zoom control for zoomin and zoomout
				
		wv.loadUrl("file:///android_asset/VenueMap.html");
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		final int cPosX = displaymetrics.widthPixels/2;
		final int cPosY = displaymetrics.heightPixels/2;
		final int x=21*32+16,y=20*32+16;
		final String fileName="hall1.jpg";
		//onStart();
      new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				wv.loadUrl("javascript:setMarkerLocation(\""+x+"\",\""+y+"\",\""+cPosX+"\",\""+cPosY+"\")"); 
				//wv.loadUrl("javascript:setMarkerLocation(\""+x+"\",\""+y+"\")"); 
				wv.loadUrl("javascript:setImageSrc(\""+fileName+"\")"); 
			}
		}, 3000);
      
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
		
	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
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
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	//is used as interface between android and javascript
	 public class MyJavaScriptInterface {
		   Context mContext;

		      MyJavaScriptInterface(Context c) {
		          mContext = c;
		      }
		      @JavascriptInterface
		      public void showToast(String msg,String msg1){
		         Toast.makeText(mContext,"called from javascript "+msg+"  "+msg1,Toast.LENGTH_LONG).show();
		      // wv.loadUrl("javascript:callFromActivity("+" message"+ ");");
		      
		          
		      }
	 }   
	 
	 public void disableMenuItem(){
	    	
	    	if(btnMenuHome.isEnabled()){
	    		btnMenuHome.setEnabled(false);
	    	}
	    }
	    
	    public void enableMenuItem(){
	    	
	    	if(!btnMenuHome.isEnabled()){
	    		btnMenuHome.setEnabled(true);
	    	}
	    }*/

}
