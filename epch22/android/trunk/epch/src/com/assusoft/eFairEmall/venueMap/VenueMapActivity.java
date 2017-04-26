package com.assusoft.eFairEmall.venueMap;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class VenueMapActivity extends Activity {
	ZoomView zoom=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 Intent intent= getIntent();
		 int x=Integer.parseInt(intent.getStringExtra("LOCATION_X"));
		 int y=Integer.parseInt(intent.getStringExtra("LOCATION_Y"));
		 Log.i("EMALL", "X,Y="+x+","+y);
		 zoom=new ZoomView(this,x,y);
		//ZoomView zoom=new ZoomView(this,11f,22f);//F-01/07
		//(21,20) E-03/07,
		//(21,46) E-03/16
		//zoom.setLayoutParams(new LayoutParams(1344, 1984));
		
		setContentView(zoom);
	}
	
	@Override
	public void onBackPressed() {
	   zoom=null;
	 super.onBackPressed();
	}
	
	

}
