package com.assusoft.efair.epchfair.Fragments;

import java.util.ArrayList;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.assusoft.eFairEmall.databaseMaster.DatabaseHelper;
import com.assusoft.eFairEmall.entities.FileSettings;
import com.assusoft.eFairEmall.util.Util;
import com.epch.efair.delhifair.EFairEmallApplicationContext;
import com.epch.efair.delhifair.HomeAcitityFirst;
import com.epch.efair.delhifair.HomeActivity;
import com.epch.efair.delhifair.R;

public class UsefulInfoDelhifairFragment extends Fragment implements OnClickListener{

	private View view;
	private DatabaseHelper helper;
	private ArrayList<FileSettings> fileSettings;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.useful_info_delhifair, container, false);
		helper = EFairEmallApplicationContext.getDatabaseHelper();
		
		view.findViewById(R.id.fairFacility).setOnClickListener(this);
		view.findViewById(R.id.freeShuttle).setOnClickListener(this);
		view.findViewById(R.id.emaneledHotel).setOnClickListener(this);
		view.findViewById(R.id.helpDeskNumber).setOnClickListener(this);
		
		FrameLayout banner  = (FrameLayout) view.findViewById(R.id.AdsFrameLayout);
		HomeAcitityFirst.adLoader.showBanner(banner);
         /*AnimationDrawable amin=(AnimationDrawable) banner.getBackground();
	 	   amin.start();*/
		
		return view;
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
		HomeActivity.setTitle("Useful Info");
	}

	@Override
	public void onClick(View v) {
		helper.openDatabase(DatabaseHelper.READMODE);
		Bundle bundle = new Bundle();
		FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
		UseFulInfoWebFragment fragment = new UseFulInfoWebFragment();
		switch (v.getId()) {
		case R.id.fairFacility:
			bundle.putString("URL", helper.getLinkURLByLinkName("FairFacilitiesIHGF"));
			bundle.putString("TITLE", "Fair Facilities");
			break;

		case R.id.freeShuttle:
			fileSettings = helper.getAllFileSettings(1);
			if(fileSettings.size()==1){
				Util.launchFile(fileSettings.get(0).getFileName(), fileSettings.get(0).getFilePath(), getActivity());
			}
			return;

		case R.id.emaneledHotel:
			fileSettings = helper.getAllFileSettings(2);
			if(fileSettings.size()==1){
				Util.launchFile(fileSettings.get(0).getFileName(), fileSettings.get(0).getFilePath(), getActivity());
			}
			return;

		case R.id.helpDeskNumber:
			bundle.putString("URL", helper.getLinkURLByLinkName("HelpDeskIHGF"));
			bundle.putString("TITLE", "Help Desk Number");
			break;
			
		default:
			break;
		}
		fragment.setArguments(bundle);
		ft.replace(R.id.content_frame, fragment);
		ft.addToBackStack(null);
		ft.commit();
		
	}

}
