package com.epch.efair.delhifair;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;

import com.assusoft.eFairEmall.databaseMaster.DatabaseHelper;
import com.assusoft.eFairEmall.entities.FileSettings;
import com.assusoft.eFairEmall.util.Util;
import com.assusoft.efair.epchfair.Fragments.QuestionnairePagerAdapter;
import com.epch.efair.delhifair.HomeAcitityFirst.Fragments;

public class HomeActivityDelhiFair extends Activity implements OnClickListener{

	private DatabaseHelper helper;
	private ArrayList<FileSettings> fileSettings;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_delhifair);
		
		FrameLayout banner=(FrameLayout) findViewById(R.id.AdsFrameLayout);
	 	   AnimationDrawable amin=(AnimationDrawable) banner.getBackground();
	 	   amin.start();
		
		findViewById(R.id.aboutIHGF).setOnClickListener(this);
		findViewById(R.id.userRegistration).setOnClickListener(this);
		findViewById(R.id.venueMap).setOnClickListener(this);
		findViewById(R.id.nearBy).setOnClickListener(this);
		findViewById(R.id.usefulInfo).setOnClickListener(this);
		findViewById(R.id.contactUs).setOnClickListener(this);
	 	   
		helper = EFairEmallApplicationContext.getDatabaseHelper();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	public void onClick(View v) {
		Fragments fragment = null;
		Intent intent=new Intent(this,HomeActivity.class);
		switch (v.getId()) {
		case R.id.aboutIHGF:
			fragment=Fragments.ABOUTIHGF;
			break;

		case R.id.userRegistration:
			fragment=Fragments.VISITOR_REGIS;
			QuestionnairePagerAdapter.selectedOption=null;
			break;

		case R.id.venueMap:
			fragment=Fragments.VENUEMAP;
			break;

		case R.id.nearBy:
			helper.openDatabase(DatabaseHelper.READMODE);
			fileSettings = helper.getAllFileSettings(5);
			if(fileSettings.size()==1){
				Util.launchFile(fileSettings.get(0).getFileName(), fileSettings.get(0).getFilePath(), this);
			}
			return;

		case R.id.usefulInfo:
			fragment=Fragments.USEFUL_INFO_DELHIFAIR;
			break;

		case R.id.contactUs:
			fragment=Fragments.CONTACTUS;
			break;

		default:
			break;
		}
		intent.putExtra("FLAG",fragment.toString());
		startActivity(intent);
	}
}
