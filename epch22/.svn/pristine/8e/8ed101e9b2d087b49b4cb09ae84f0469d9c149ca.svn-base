package com.assusoft.efair.epchfair.gcm;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.epch.efair.delhifair.R;

public class GCMReminderActivity extends Activity {
	
	TextView message,provider;
	Button btnOk,btnHome;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gcm_reminder);
		message=(TextView) findViewById(R.id.tvGCMReminderActivity);
		provider=(TextView) findViewById(R.id.tvNotiExbNameProvider);
		Intent intent=getIntent();
		message.setText(intent.getStringExtra("MSG"));
		provider.setText(intent.getStringExtra("PROVIDER"));
		
	}
	
public void clickOkgcm(View v){
		
		finish();
	}
	
public void gotoAssuranceSoftware(View v){
	/*Intent in=new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.assusoft.com"));
    startActivity(in);*/
    }
public void gotoNbtIndia(View v){
	/*Intent in=new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.packplus.in"));
    startActivity(in);*/
    }
}
