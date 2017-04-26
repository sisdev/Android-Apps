package com.assusoft.efair.epchfair.Fragments;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import com.assusoft.eFairEmall.databaseMaster.DatabaseHelper;
import com.assusoft.eFairEmall.entities.FileSettings;
import com.assusoft.eFairEmall.internetAndGpsMaster.InternetConnectionDetector;
import com.assusoft.eFairEmall.soapWebService.WebService;
import com.assusoft.eFairEmall.util.Util;
import com.epch.efair.delhifair.EFairEmallApplicationContext;
import com.epch.efair.delhifair.HomeAcitityFirst;
import com.epch.efair.delhifair.HomeActivity;
import com.epch.efair.delhifair.ImageAsyncTask;
import com.epch.efair.delhifair.R;
import com.epch.efair.delhifair.StorageHelper;

public class UsefulInfo extends Fragment{
	
	ImageButton btnFairfacility,btnShuttleInfo,btnHotels,btnHelpDeskNo,btnIHGFIntro,btnHelp;
	DatabaseHelper dbHelper;
	ArrayList<FileSettings> data;
	String fileName,filePath;
	InternetConnectionDetector icd;
	String param;
	File file;
	private String base;
	static int count=1;
	@Override
	public View onCreateView(LayoutInflater inflater,
			  ViewGroup container,   Bundle savedInstanceState) {
		View v=inflater.inflate(R.layout.useful_info,container,false);
		btnFairfacility=(ImageButton) v.findViewById(R.id.btnUsefulfairfacility);
		btnShuttleInfo=(ImageButton) v.findViewById(R.id.btnusefulshuttle);
		btnHotels=(ImageButton) v.findViewById(R.id.btnUsefulhotel);
		btnHelpDeskNo=(ImageButton) v.findViewById(R.id.btnusefulhelpdeskNo);
		btnIHGFIntro=(ImageButton) v.findViewById(R.id.btnusefulIHGFIntro);
		btnHelp		= (ImageButton) v.findViewById(R.id.btnusefulHelp);
		
		FrameLayout banner=(FrameLayout) v.findViewById(R.id.AdsFrameLayout);
		HomeAcitityFirst.adLoader.showBanner(banner);
	 	   /*AnimationDrawable amin=(AnimationDrawable) banner.getBackground();
	 	   amin.start();*/
		dbHelper=EFairEmallApplicationContext.getDatabaseHelper();
		dbHelper.openDatabase(DatabaseHelper.WRITEMODE);
		
		icd=new InternetConnectionDetector(getActivity());
		
		base = "file://"+Environment.getExternalStorageDirectory().getAbsolutePath()
				   +File.separator+ImageAsyncTask.FOLDER_NAME
				   +File.separator;
		
		btnFairfacility.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				HomeActivity.setTitle("Fair Facilities");
				FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
				HelpDesk fm=new HelpDesk();
				Bundle bundle=new Bundle();
				bundle.putString("URL",base+dbHelper.getLinkURLByLinkName("fairFacilites"));
				fm.setArguments(bundle);
				ft.replace(R.id.content_frame,fm);
				ft.addToBackStack(null);
				ft.commit();
				
			}
		});
		btnShuttleInfo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				data=dbHelper.getAllFileSettings(1);
				if(data.size()>0){
					fileName=data.get(0).getFileName();
					filePath=data.get(0).getFilePath();
				}else{
					Toast.makeText(getActivity(),"More information will be available soon.",Toast.LENGTH_LONG).show();
					return;
				}
				Util.launchFile(fileName, filePath, getActivity());
			}
		});
		btnHotels.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				data=dbHelper.getAllFileSettings(2);
				if(data.size()>0){
					fileName=data.get(0).getFileName();
					filePath=data.get(0).getFilePath();
				}else{
					Toast.makeText(getActivity(),"More information will be available soon.",Toast.LENGTH_LONG).show();
					return;
				}
				Util.launchFile(fileName, filePath, getActivity());				
			}
		});
		btnHelpDeskNo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				HomeActivity.setTitle("Help Desk Nubmer");
				FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
				HelpDesk fm=new HelpDesk();
				Bundle bundle=new Bundle();
				bundle.putString("URL",base+dbHelper.getLinkURLByLinkName("Help_desk_number"));
				fm.setArguments(bundle);
				ft.replace(R.id.content_frame,fm);
				ft.addToBackStack(null);
				ft.commit();
				
			}
		});
		
		btnIHGFIntro.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				HomeActivity.setTitle("About");
				FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
				HelpDesk fm=new HelpDesk();
				Bundle bundle=new Bundle();
				bundle.putString("URL",base+dbHelper.getLinkURLByLinkName("IHGF_Intro"));
				fm.setArguments(bundle);
				ft.replace(R.id.content_frame,fm);
				ft.addToBackStack(null);
				ft.commit();
			}
		});
		btnHelp.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				HomeActivity.setTitle("About App");
				FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
				HelpDesk fm=new HelpDesk();
				Bundle bundle=new Bundle();
				bundle.putString("URL",base+dbHelper.getLinkURLByLinkName("App_Feature_Intro"));
				fm.setArguments(bundle);
				ft.replace(R.id.content_frame,fm);
				ft.addToBackStack(null);
				ft.commit();
			}
		});
		
		return v;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		HomeActivity.setTitle("Useful Info");
	}
}
