package com.assusoft.efair.qrcode;

import com.epch.efair.delhifair.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class MainActivityQR extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void qrCodeCapture(View v){
		Intent intent=new Intent(this,CaptureActivity.class);
		startActivity(intent);
	}
	public void qrCodeDecoder(View v){
		Intent intent=new Intent(this,DecoderActivity.class);
		startActivity(intent);
	}
	public void qrCodeEncoder(View v){
		Intent intent=new Intent(this,ContactForm.class);
		startActivity(intent);
	}

}
