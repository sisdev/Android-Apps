package com.assusoft.efair.socialMediaFb;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.epch.efair.delhifair.R;
import com.assusoft.efair.socialMediaFb.MainActivity;

public class CameraActivity extends Activity{
	 private Camera mCamera;
	 private CameraPreview mPreview;
	 private static final String TAG="SOCIALMEDIA";
	 public static final int MEDIA_TYPE_IMAGE = 1;
	 public static final int MEDIA_TYPE_VIDEO = 2;
	 final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;

	 
	 Bitmap photo=null;
     ImageView imageview;
     private Button btnOk;
     Button captureButton;
     private String FILENAME="EfairEmall.jpg";
	 
	private PictureCallback mPicture = new PictureCallback() {

 	    @Override
 	    public void onPictureTaken(byte[] data, Camera camera) {
            
 	    	//photo=BitmapFactory.decodeByteArray(data, 0,data.length);
 	    	//imageview=(ImageView) findViewById(R.id.photo);
 	    	//imageview.setImageBitmap(photo);
 	    	Log.i("FACEBOOK","image size "+data.length);
 	    	btnOk.setVisibility(View.VISIBLE);
 	    	captureButton.setVisibility(View.INVISIBLE);
 	    	FileOutputStream fos = null;
			try {
				fos = openFileOutput(FILENAME, CameraActivity.MODE_PRIVATE);
				//photo.compress(Bitmap.CompressFormat.JPEG,90,fos);
				fos.write(data);
				fos.close();
				//fos.write(data);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
 	    	
 	    	
 	        /*File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
 	        if (pictureFile == null){
 	            Log.d(TAG, "Error creating media file, check storage permissions: " );
 	            return;
 	        }

 	        try {
 	            FileOutputStream fos = new FileOutputStream(pictureFile);
 	            fos.write(data);
 	            fos.close();
 	        } catch (FileNotFoundException e) {
 	            Log.d(TAG, "File not found: " + e.getMessage());
 	        } catch (IOException e) {
 	            Log.d(TAG, "Error accessing file: " + e.getMessage());
 	        }*/
 	    }
 	};

	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	
    	setContentView(R.layout.camera_preview_social);
    	
        // Create an instance of Camera
        mCamera = getCameraInstance();
        
        mCamera.setDisplayOrientation(90);

        // Create our Preview view and set it as the content of our activity.
        mPreview = new CameraPreview(this, mCamera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);
        
         
     // Add a listener to the Capture button
        btnOk=(Button) findViewById(R.id.button_ok);
    	 captureButton = (Button) findViewById(R.id.button_capture);
    	captureButton.setOnClickListener(
    		    new View.OnClickListener() {
    		        @Override
    		        public void onClick(View v) {
    		            // get an image from the camera
    		            mCamera.takePicture(null, null, mPicture);
    		        	
    		        	
    		        }
    		    }
    		);
    	btnOk.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				releaseCamera();
				Intent intent=new Intent(CameraActivity.this,MainActivity.class);
	 	    	//intent.putExtra("PHOTO",photo);
	 	    	startActivity(intent);
	 	    	finish();
	 	    	
			}
		});
    	

    }
	
	 
	
	/** A safe way to get an instance of the Camera object. */
	public static Camera getCameraInstance(){
	    Camera c = null;
	    try {
	        c = Camera.open(); // attempt to get a Camera instance
	    }
	    catch (Exception e){
	        // Camera is not available (in use or does not exist)
	    }
	    return c; // returns null if camera is unavailable
	}
	
	
	/** Create a file Uri for saving an image or video */
	private static Uri getOutputMediaFileUri(int type){
	      return Uri.fromFile(getOutputMediaFile(type));
	}

	/** Create a File for saving an image or video */
	private static File getOutputMediaFile(int type){
	    // To be safe, you should check that the SDCard is mounted
	    // using Environment.getExternalStorageState() before doing this.

	    File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
	              Environment.DIRECTORY_PICTURES), "MyCameraApp");
	    // This location works best if you want the created images to be shared
	    // between applications and persist after your app has been uninstalled.

	    // Create the storage directory if it does not exist
	    if (! mediaStorageDir.exists()){
	        if (! mediaStorageDir.mkdirs()){
	            Log.d("MyCameraApp", "failed to create directory");
	            return null;
	        }
	    }

	    // Create a media file name
	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	    File mediaFile;
	    if (type == MEDIA_TYPE_IMAGE){
	        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
	        "IMG_"+ timeStamp + ".jpg");
	    } else if(type == MEDIA_TYPE_VIDEO) {
	        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
	        "VID_"+ timeStamp + ".mp4");
	    } else {
	        return null;
	    }

	    return mediaFile;
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		releaseCamera();
	}
	
	 private void releaseCamera(){
	        if (mCamera != null){
	            mCamera.release();        // release the camera for other applications
	            mCamera = null;
	        }
	    }

	

}
