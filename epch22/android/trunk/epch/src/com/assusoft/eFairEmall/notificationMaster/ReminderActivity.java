package com.assusoft.eFairEmall.notificationMaster;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.epch.efair.delhifair.R;

public class ReminderActivity extends Activity {
	TextView tvExhibitorName,tvStartTime,tvEndTime,tvDate;
	Button btnOk,btnReschedule;
	Intent intent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notification);
		tvExhibitorName=(TextView) findViewById(R.id.tvNotiExbName);
		tvStartTime=(TextView) findViewById(R.id.tvNotiStartTime);
		tvEndTime=(TextView) findViewById(R.id.tvNotiEndTime);
		
		tvDate=(TextView) findViewById(R.id.tvBookRemEventDate1);
		
		//get intent
		intent=getIntent();
		try{
		tvDate.setText(intent.getStringExtra("DATE"));
		//set the text to textview
		tvExhibitorName.setText(intent.getStringExtra("EXHIBITORNAME"));
		tvEndTime.setText(intent.getStringExtra("ENDTIME"));
		tvStartTime.setText(intent.getStringExtra("STARTTIME"));
		}catch(Exception e){
			e.printStackTrace();
		}
			
	}
	
	public void clickOk(View v){
		
		finish();
	}
	
	public void gotoAssuranceSoftware(View v){
		Intent in=new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.assusoft.com"));
        startActivity(in);
	}
	public void gotoNbtIndia(View v){
		Intent in=new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.nbtindia.gov.in"));
        startActivity(in);
	}

}
