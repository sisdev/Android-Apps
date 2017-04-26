package com.assusoft.efair.epchfair.Fragments;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.epch.efair.delhifair.R;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
  
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.assusoft.eFairEmall.databaseMaster.DatabaseHelper;
import com.assusoft.eFairEmall.entities.PhotoShoot;
import com.assusoft.tmxloader.library.ExtendedViewPager;
import com.assusoft.tmxloader.library.ScalingUtilities;
import com.assusoft.tmxloader.library.ScalingUtilities.ScalingLogic;
import com.assusoft.tmxloader.library.TouchImageView;
import com.epch.efair.delhifair.EFairEmallApplicationContext;
import com.epch.efair.delhifair.HomeAcitityFirst;
import com.epch.efair.delhifair.HomeActivity;
import com.epch.efair.delhifair.ImageAsyncTask;
import com.epch.efair.delhifair.StorageHelper;

public class PhotoshootDetail extends Fragment{
	
	ImageButton edit,delete,newPhoto;
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	private Uri fileUri;
	public static final int MEDIA_TYPE_IMAGE = 1;

    Bundle bundle;
    static int exhibitorId;
    static String folderName;
    DatabaseHelper dbh;
    static ArrayList<PhotoShoot> photos;
    static String imagePath;
    static int displayWidth;
	static int displayHieght;
	static ExtendedViewPager mViewPager;
	TouchImageAdapter adapter;
	static TextView tv,counting;
	@Override
	public View onCreateView(LayoutInflater inflater,
			  ViewGroup container,   Bundle savedInstanceState) {
		View v=inflater.inflate(R.layout.photoshoot_folder_details,container,false);
		FrameLayout banner=(FrameLayout) v.findViewById(R.id.AdsFrameLayout);
		HomeAcitityFirst.adLoader.showBanner(banner);
//	 	   AnimationDrawable amin=(AnimationDrawable) banner.getBackground();
//	 	   amin.start();
		
		edit			= (ImageButton) v.findViewById(R.id.photoshootbtnedit);
		delete			= (ImageButton) v.findViewById(R.id.photoshootbtndelete);
		newPhoto		= (ImageButton) v.findViewById(R.id.photoshootbtncapture);
		mViewPager		= (ExtendedViewPager) v.findViewById(R.id.view_pagerimage);
		tv				= (TextView) v.findViewById(R.id.textviewphotoshoot);
		counting		= (TextView)v.findViewById(R.id.tvCount);
		
		bundle			= getArguments();
		exhibitorId		= bundle.getInt("EXHIBITORID");
		folderName		= bundle.getString("FOLDERNAME");
		dbh=EFairEmallApplicationContext.getDatabaseHelper();
		photos=dbh.getAlbumPhotopathList(Integer.toString(exhibitorId), ImageAsyncTask.FOLDER_NAME+File.separator+folderName+File.separator);
		
		if(photos.size()<=0){
			edit.setEnabled(false);
			delete.setEnabled(false);
		}else{
			tv.setVisibility(View.INVISIBLE);
		}


		DisplayMetrics dimension = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dimension);
		displayWidth = dimension.widthPixels;
		displayHieght = dimension.heightPixels;
		
		if(photos.size()!=0){
			counting.setText("1/"+photos.size());
		}else if(photos.size()==0){
			counting.setText("");
		}

		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				String strCount = (1+arg0)+"/"+photos.size();
	            counting.setText(strCount);
				Log.i("EPCH","Image count no. -  "+strCount);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		newPhoto.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				open();
			}
		});
		
		edit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(photos.size()<=0) return;
				Bundle bundle=new Bundle();
				int currentpage=mViewPager.getCurrentItem();
				bundle.putParcelable("PHOTOSHOOT",photos.get(currentpage));
				bundle.putInt("EXHIBITORID",exhibitorId);
				FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
				PhotoShootNotes notes=new PhotoShootNotes();
				notes.setArguments(bundle);
				ft.replace(R.id.content_frame, notes);
				ft.addToBackStack(null);
				ft.commit();
			}
		});
		
		delete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				openAlert();
				
				
			}
		});
		
		 adapter=new TouchImageAdapter();
        mViewPager.setAdapter(adapter);
        
		return v;
	}
	public void open(){
	      Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
	      fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image
	      intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name

	   // start the image capture Intent
	      startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
	      
	      if(photos.size()!=0){
	    	  counting.setText(1+mViewPager.getCurrentItem()+"/"+photos.size());
	      }

	   }
	
	/** Create a file Uri for saving an image or video */
	private static Uri getOutputMediaFileUri(int type){
	      return Uri.fromFile(getOutputMediaFile(type));
	}
	/** Create a File for saving an image or video */
	private static File getOutputMediaFile(int type){
	    // To be safe, you should check that the SDCard is mounted
	    // using Environment.getExternalStorageState() before doing this.

	    /*File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
	              Environment.DIRECTORY_PICTURES), "MyCameraApp");*/
		File mediaStorageDir = null;
		if(StorageHelper.isExternalStorageAvailable()){
			mediaStorageDir=new File(Environment.getExternalStorageDirectory()+File.separator+ImageAsyncTask.FOLDER_NAME+File.separator+folderName);
		}
	    // This location works best if you want the created images to be shared
	    // between applications and persist after your app has been uninstalled.
		Log.i("EXPO","File path--> "+mediaStorageDir);
	    // Create the storage directory if it does not exist
	    if (! mediaStorageDir.exists()){
	        if (! mediaStorageDir.mkdirs()){
	        	Log.i("EXPO","File path--> "+mediaStorageDir);
	            Log.d("EXPO", "failed to create directory");
	            return null;
	        }
	    }

	    // Create a media file name
	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	    File mediaFile;
	    if (type == MEDIA_TYPE_IMAGE){
	        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
	        "IMG_"+ timeStamp + ".jpg");
	    } else {
	        return null;
	    }
        imagePath=mediaFile.getPath();
        Log.i("EXPO","Image Path--> "+imagePath);
	    return mediaFile;
	}

	   @Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	      // TODO Auto-generated method stub
		   super.onActivityResult(requestCode, resultCode, data); 
		   if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
		        if (resultCode == getActivity().RESULT_OK) {
		            // Image captured and saved to fileUri specified in the Intent
		            /*Toast.makeText(getActivity(), "Image saved to:\n" +
		                     imagePath, Toast.LENGTH_LONG).show();*/
		            //save image path to database into photoshoot table
		            PhotoShoot ph=new PhotoShoot();
		            ph.setExhibitorId(exhibitorId);
		            ph.setPhotopath(imagePath);
		            dbh.insertPhotoShoot(ph, true);
		            photos.add(0,ph);
		            adapter.notifyDataSetChanged();
		            if(!edit.isEnabled()){
		            	edit.setEnabled(true);
		            }
		            if(!delete.isEnabled()){
		            	delete.setEnabled(true);
		            }
		            if(tv.getVisibility()==View.VISIBLE){
		            	tv.setVisibility(View.INVISIBLE);
		            }
		            
		        } else if (resultCode == getActivity().RESULT_CANCELED) {
		            // User cancelled the image capture
		        } else {
		            // Image capture failed, advise user
		        }
		    }

	     
	     // Bitmap bp = (Bitmap) data.getExtras().get("data");
	      
	   }
	
	 static class TouchImageAdapter extends PagerAdapter {

	        private static int[] images = { /*R.drawable.nature_1, R.drawable.nature_2, R.drawable.nature_3, R.drawable.nature_4, R.drawable.nature_5*/ };

	        @Override
	        public int getCount() {
	        	//return images.length;
	        	return photos.size();
	        }

	        @Override
	        public View instantiateItem(ViewGroup container, int position) {
	            TouchImageView img = new TouchImageView(container.getContext());
	            //img.setImageResource(images[position]);
	            
	         // Decode the filepath with BitmapFactory followed by the position
	            /*Bitmap bmp = BitmapFactory.decodeFile(photos.get(position).getPhotopath());
	            img.setImageBitmap(bmp);*/
	            // Part 1: Decode image	            
		        Bitmap unscaledBitmap = ScalingUtilities.decodeFile(photos.get(position).getPhotopath(),displayWidth,displayHieght, ScalingLogic.FIT);

		        if(unscaledBitmap==null){
		        	return img;
		        }
		        // Part 2: Scale image
		        Bitmap scaledBitmap = ScalingUtilities.createScaledBitmap(unscaledBitmap, displayWidth,displayHieght, ScalingLogic.FIT);
		        unscaledBitmap.recycle();

		       
		       img.setImageBitmap(scaledBitmap);
	            
	            container.addView(img, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
	            return img;
	        }
	        

	        @Override
	        public void destroyItem(ViewGroup container, int position, Object object) {
	            container.removeView((View) object);
	        }

	        @Override
	        public boolean isViewFromObject(View view, Object object) {
	            return view == object;
	        }

	        @Override
	        public int getItemPosition(Object object) {
	        // TODO Auto-generated method stub
	        return POSITION_NONE;
	        }
	    }
	 
	 void scaledImage(String filePath,TouchImageView img) {
	       
	        // Part 1: Decode image
	        Bitmap unscaledBitmap = ScalingUtilities.decodeFile(filePath,displayWidth,displayHieght, ScalingLogic.FIT);

	        // Part 2: Scale image
	        Bitmap scaledBitmap = ScalingUtilities.createScaledBitmap(unscaledBitmap, displayWidth,displayHieght, ScalingLogic.FIT);
	        unscaledBitmap.recycle();

	       
	       img.setImageBitmap(scaledBitmap);
	       
	    }
	 
	 
	 public void openAlert(){
		
	      AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
	      alertDialogBuilder.setMessage("Do you want to delete current photo or album ? ");
	      alertDialogBuilder.setTitle("Delete");
	      alertDialogBuilder.setIcon(R.drawable.delete);
	      alertDialogBuilder.setPositiveButton("Photo", 
	      new DialogInterface.OnClickListener() {
			
	         @Override
	         public void onClick(DialogInterface arg0, int arg1) {
	           
				dbh.deletePhotoshoot(photos.get(mViewPager.getCurrentItem()).getPhotopath());
				photos.remove(mViewPager.getCurrentItem());
				adapter.notifyDataSetChanged();
				if(photos.size()<=0){
					counting.setText("");
					if(tv.getVisibility()==View.INVISIBLE) tv.setVisibility(View.VISIBLE);
					delete.setEnabled(false);
					edit.setEnabled(false);
				}
				else if(photos.size()>0){
					counting.setText(1+mViewPager.getCurrentItem()+"/"+photos.size());
				}
	         }
	      });
	      alertDialogBuilder.setNeutralButton("Album", 
	    	      new DialogInterface.OnClickListener() {
	    				
	    	         @Override
	    	         public void onClick(DialogInterface dialog, int which) {
	    	           
	    	        	dbh.deletePhotoshoot(folderName);
	    	        	File file=new File(Environment.getExternalStorageDirectory()+File.separator+ImageAsyncTask.FOLDER_NAME+File.separator+folderName);
	    	        	try{
	    	        		if(file.exists()){
	    	        			DeleteRecursive(file);
	    	        			Log.i("EPCH","Deleted file name - "+file);
	    	        		}
	    	        	}catch(Exception e){
	    	        		e.printStackTrace();
	    	        	}
	    	        	boolean flag=false;
	    	        	try{
	    	        		flag=bundle.getBoolean("QRALBUMFLAG");
	    	        	}catch(Exception e){
	    	        		e.printStackTrace();
	    	        		flag=false;
	    	        	}
	    	        	if(flag){
	    	        		getActivity().finish();
	    	        	}else{
	    	        	   getActivity().getSupportFragmentManager().popBackStack();
	    	        	}
	    			 }
	    	      });
	      alertDialogBuilder.setNegativeButton("Cancel", 
	      new DialogInterface.OnClickListener() {
				
	         @Override
	         public void onClick(DialogInterface dialog, int which) {
	           /* Intent negativeActivity = new Intent(getApplicationContext(),com.example.alertdialog.NegativeActivity.class);
	            startActivity(negativeActivity);*/
	        	 
			 }
	      });
		    
	      AlertDialog alertDialog = alertDialogBuilder.create();
	      alertDialog.show();
		    
	   }
	 
	 @Override
	public void onPause() {
		super.onPause();
		HomeActivity.hideSoftKeyboard(getActivity());
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		HomeActivity.setTitle("Album");
	}
	 void DeleteRecursive(File fileOrDirectory) {
		    if (fileOrDirectory.isDirectory())
		        for (File child : fileOrDirectory.listFiles())
		            DeleteRecursive(child);

		    fileOrDirectory.delete();
		}
 
}
