package com.assusoft.efair.epchfair.Fragments;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.assusoft.eFairEmall.databaseMaster.DatabaseHelper;
import com.assusoft.tmxloader.library.TouchImageView;
import com.epch.efair.delhifair.EFairEmallApplicationContext;
import com.epch.efair.delhifair.HomeActivity;
import com.epch.efair.delhifair.ImageAsyncTask;
import com.epch.efair.delhifair.R;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class EHub_Image_View extends Fragment{

	private View			view;
	private TouchImageView	img;
	private DisplayMetrics	dimenssion;
	private int				displayWidth;
	private int				displayHeight;
	private Bitmap			bitmap = null;
	private Options			options;
	private InputStream		in = null;
	private AssetManager	assetManager;
	private DatabaseHelper	dbHelper;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view	= inflater.inflate(R.layout.venue_map_with_location, container, false);
		img		= (TouchImageView) view.findViewById(R.id.imgMap1);
		
		dbHelper=EFairEmallApplicationContext.getDatabaseHelper();
		dbHelper.openDatabase(DatabaseHelper.READMODE);
		
		try{

			assetManager = getActivity().getAssets();
			in=new FileInputStream(new File(Environment.getExternalStorageDirectory()
					+File.separator+ImageAsyncTask.FOLDER_NAME+File.separator
					+dbHelper.getFileNameOfVenueMap(3)));
			
			dimenssion	= new DisplayMetrics();
			options		= new Options();
			
			getActivity().getWindowManager().getDefaultDisplay().getMetrics(dimenssion);
			displayWidth	= dimenssion.widthPixels;
			displayHeight	= dimenssion.heightPixels;
			
			options.inSampleSize = ProductImageLargeViewFragment
									.calculateOption(options.outWidth, options.outHeight, displayWidth, displayHeight);
			bitmap			= BitmapFactory.decodeStream(in, null, options);
			img.setImageBitmap(bitmap);
		}catch(IOException io){
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return view;
	}
	@Override
	public void onResume() {
		super.onResume();
		HomeActivity.setTitle("E-Hub");
	}

}
