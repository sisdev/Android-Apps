package com.assusoft.efair.socialMediaFb;



import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.assusoft.efair.epchfair.Fragments.FbCameraFragement;
import com.epch.efair.delhifair.R;
import com.facebook.FacebookRequestError;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.RequestAsyncTask;
import com.facebook.RequestBatch;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.LoginButton;

public class FbMainFragment extends Fragment {
        
	 // Image loading result to pass to startActivityForResult method.
    private static int LOAD_IMAGE_RESULTS = 1;
	public static Bitmap image=null;
    public static final String FILENAME="EfairEmall1.jpg";
	private static final String TAG = "FACEBOOK";
        
        // Set to true to upload an image to the staging
        // resource before creating the Open Graph object.
        static final boolean UPLOAD_IMAGE = true;
        
        private ProgressDialog progressDialog;
        
        private UiLifecycleHelper uiHelper;
        private boolean isImageSet=false;
    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(final Session session,
                        final SessionState state,
                        final Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };
    
        private Button shareButton;
        
        private static final List<String> PERMISSIONS = Arrays.asList("publish_actions");
        
        private static final String PENDING_PUBLISH_KEY = "pendingPublishReauthorization";
        
        private boolean pendingPublishReauthorization = false;
        private EditText edtCaption;
        private ImageView capturedImageView;
        
        public View onCreateView(LayoutInflater inflater,
                        ViewGroup container,
                        Bundle savedInstanceState) {
                View view = inflater.inflate(R.layout.main, container, false);
              //get the id of edit text
                edtCaption=(EditText) view.findViewById(R.id.captionForImage);
                
                capturedImageView=(ImageView) view.findViewById(R.id.capturedImageView);
                //set the image to imageview
              //instantiate the bitmap for custom camera activity
                if(FbCameraFragement.fbCameraFlag)
                {
            	try{FbCameraFragement.fbCameraFlag=false;
            	    FileInputStream fis = getActivity().openFileInput(FILENAME);
            	      image = BitmapFactory.decodeStream(fis);
            	       fis.close();
            	   // image = BitmapFactory.decodeFile(FILENAME);
            	       Matrix matrix = new Matrix();

            	      
            	       matrix.postRotate(90);
            	       image=Bitmap.createBitmap(image, 0, 0,image.getWidth(),
            	    		   image.getHeight(), matrix, true);
            	        
            	     Toast.makeText(getActivity(),"file reading",Toast.LENGTH_LONG).show();  
            	    }
            	    catch(Exception e){
            	    	e.printStackTrace();
            	    }
            	capturedImageView.setImageBitmap(image);
            	isImageSet=true;
                }else{
                	
            
                }
                
                LoginButton authButton = (LoginButton) view.findViewById(R.id.authButton);
                authButton.setFragment(this);
               
                shareButton = (Button) view.findViewById(R.id.shareButton);
                shareButton.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                	 /*if(!isImageSet){
                		 Toast.makeText(getActivity(), "Please add image to upload.",Toast.LENGTH_SHORT).show();
                		 return;
                	 }*/
                	 String caption="";
                	 try{
                	      caption=edtCaption.getText().toString();
                	      if(TextUtils.isEmpty(caption)){
                	    	  Toast.makeText(getActivity(), "At least add caption",Toast.LENGTH_SHORT).show();
                	    	  return;
                	      }
                     }catch(Exception e){
                    	 e.printStackTrace();
                     }
                	 publishStory(caption);
                	 
                 }
                });
                
                if (savedInstanceState != null) {
                        pendingPublishReauthorization =
                                savedInstanceState.getBoolean(PENDING_PUBLISH_KEY, false);
                }
                capturedImageView.setOnClickListener(new OnClickListener() {
        			
        			@Override
        			public void onClick(View v) {
        				// TODO Auto-generated method stub
        				new AlertDialog.Builder(getActivity())
        			    .setTitle("Add photo")
        			    .setMessage("Add photo")
        			    .setPositiveButton(R.string.camera, new DialogInterface.OnClickListener() {
        			        public void onClick(DialogInterface dialog, int which) { 
        			        	Context fm =getActivity();
        						FragmentTransaction ft = ((FragmentActivity)fm).getSupportFragmentManager().beginTransaction();
        				        
        						FbCameraFragement fg=new FbCameraFragement();
        				        ft.replace(((ViewGroup)getView().getParent()).getId(), fg,"");
        				        ft.commit();
        			        }
        			     })
        			    .setNegativeButton(R.string.gallery, new DialogInterface.OnClickListener() {
        			        public void onClick(DialogInterface dialog, int which) { 
        			        	// Create the Intent for Image Gallery.
        			        	// Create the Intent for Image Gallery.
        	                    Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        	                     
        	                    // Start new activity with the LOAD_IMAGE_RESULTS to handle back the results when image is picked from the Image Gallery.
        	                    startActivityForResult(i, LOAD_IMAGE_RESULTS);
        			        }
        			     })
        			    .setIcon(android.R.drawable.ic_dialog_alert)
        			     .show();
        			}
        		});
                return view;
        }
    
        @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uiHelper = new UiLifecycleHelper(getActivity(), callback);
        uiHelper.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
//        Commons.getMyTracker(getActivity(), "FbMainFragment");
        // For scenarios where the main activity is launched and user
                // session is not null, the session state change notification
                // may not be triggered. Trigger it if it's open/closed.
        Log.i("FACEBOOK","onresume");
                Session session = Session.getActiveSession();
                if (session != null &&
                                (session.isOpened() || session.isClosed()) ) {
                        onSessionStateChange(session, session.getState(), null);
                }
                
        uiHelper.onResume();
        
    	}
    
    
    
    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }
    
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(PENDING_PUBLISH_KEY, pendingPublishReauthorization);
        uiHelper.onSaveInstanceState(outState);
    }
        
        private void onSessionStateChange(Session session, SessionState state, Exception exception) {
            Log.i("FACEBOOK","onsessionchanged  " + exception);
        	if (state.isOpened()) {
        		 Log.i("FACEBOOK","onsessionchanged state.isOpened() "+state.isOpened());
            shareButton.setVisibility(View.VISIBLE);
            if (pendingPublishReauthorization &&
                            state.equals(SessionState.OPENED_TOKEN_UPDATED)) {
                    pendingPublishReauthorization = false;
                   
            }
            
           
        } else if (state.isClosed()) {
            shareButton.setVisibility(View.INVISIBLE);
        }
    }

        /*
         * Method to publish a story.
         */
        private void publishStory(String caption) {
                // Un-comment the line below to turn on debugging of requests
                //Settings.addLoggingBehavior(LoggingBehavior.REQUESTS);
        	 Log.i("FACEBOOK","publish story  ");      
         Session session = Session.getActiveSession();
         if (session != null) {
                 // Check for publish permissions
                 List<String> permissions = session.getPermissions();
                 Log.i("FACEBOOK", "permissions   "+session.getAccessToken());
                 
                 if (!isSubsetOf(PERMISSIONS,permissions)) {
                         pendingPublishReauthorization = true;
                         Session.NewPermissionsRequest newPermissionsRequest = new Session
                         .NewPermissionsRequest(this, PERMISSIONS);
                         session.requestNewPublishPermissions(newPermissionsRequest);
                         return;
                 }
                
         // Show a progress dialog because the batch request could take a while.
         progressDialog = ProgressDialog.show(getActivity(), "",
         getActivity().getResources().getString(R.string.progress_dialog_text), true);
                 
                 
               
                 
                 RequestBatch requestBatch = new RequestBatch();

                 // Request: Staging image upload request
                 
                 
              // Set up image upload request parameters
                 Bundle imageParams = new Bundle();
                /* Bitmap image1 = BitmapFactory.decodeResource(this.getResources(),
                         R.drawable.a_game_of_thrones);*/
                 imageParams.putParcelable("file",image);
                 imageParams.putString("caption",caption);

                 // Set up the image upload request callback
                 Request.Callback imageCallback = new Request.Callback() {

                     @Override
                     public void onCompleted(Response response) {
                    	
                         // Log any response error
                         FacebookRequestError error = response.getError();
                         if (error != null) {
                             dismissProgressDialog();
                             Log.i(TAG, error.getErrorMessage());
                         }
                         
                         String postId = null;
                         
						      try {
						    	  JSONObject graphResponse = response.getGraphObject().getInnerJSONObject();
							      
						    	  dismissProgressDialog();
						          postId = graphResponse.getString("id");
						      } catch (Exception e) {
						          Log.i("publishStory",
						              "JSON error "+ e.getMessage());
						          dismissProgressDialog();
						      }
						      if(postId!=null){
						    	  Toast.makeText(getActivity(),"your photo uploaded successfully",Toast.LENGTH_LONG).show();
						    	  //shareButton.setVisibility(View.INVISIBLE);
						    	  deleteImageFile();
						    	  Log.i(TAG, "image uploaded successfully");
						      }
						
						      
						                         
                         
                     }
                 };

               
                 // Create the request for the image upload
                 Request imageRequest = new Request(Session.getActiveSession(),
                                 "me/photos", imageParams,
                            HttpMethod.POST, imageCallback);

                
                 // Add the request to the batch
                 requestBatch.add(imageRequest);
                 RequestAsyncTask task = new RequestAsyncTask(requestBatch);
                 task.execute();
     
               
      
                }
        }
        
        /*
         * Helper method to dismiss the progress dialog.
         */
        private void dismissProgressDialog() {
                // Dismiss the progress dialog
                if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
        }
        
        /*
         * Helper method to check a collection for a string.
         */
        private boolean isSubsetOf(Collection<String> subset, Collection<String> superset) {
         for (String string : subset) {
         if (!superset.contains(string)) {
         return false;
         }
         }
         return true;
        }
        //delete the file after uploading file to facebook
        private boolean deleteImageFile(){
        	
        	boolean deleted=false;
        	try{
        		File dir = getActivity().getFilesDir();
        		File file = new File(dir, MainActivity.FILENAME);
        		deleted = file.delete();
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        	
        	
        	
        	return deleted;
        }
        
        
        @Override
       public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
          
            // Here we need to check if the activity that was triggers was the Image Gallery.
            // If it is the requestCode will match the LOAD_IMAGE_RESULTS value.
            // If the resultCode is RESULT_OK and there is some data we know that an image was picked.
            if (requestCode == LOAD_IMAGE_RESULTS && resultCode == getActivity().RESULT_OK && data != null) {
                // Let's read picked image data - its URI
                Uri pickedImage = data.getData();
                // Let's read picked image path using content resolver
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor cursor = getActivity().getContentResolver().query(pickedImage, filePath, null, null, null);
                cursor.moveToFirst();
                String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));
                 
                // Now we need to set the GUI ImageView data with data read from the picked file.
                image= BitmapFactory.decodeFile(imagePath);
                capturedImageView.setImageBitmap(image);
                isImageSet=true;
                // At the end remember to close the cursor or you will end with the RuntimeException!
                cursor.close();
            }else{
            	  uiHelper.onActivityResult(requestCode, resultCode, data);
            }
        }
   
 

}