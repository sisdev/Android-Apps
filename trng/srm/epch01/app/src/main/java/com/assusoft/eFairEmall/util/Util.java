package com.assusoft.eFairEmall.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.assusoft.eFairEmall.internetAndGpsMaster.InternetConnectionDetector;
import com.assusoft.eFairEmall.soapWebService.WebService;
import com.epch.efair.delhifair.ImageAsyncTask;
import com.epch.efair.delhifair.StorageHelper;

public class Util {
	
/***Local Test Server**/
	public static final String BASE_URL="http://epchmobile.com/epch2016April/admin/webServicesForUser/services/ws/1";
//public static final String			BASE_URL="http://epchmobile.com/epch2016-testing/admin/webServicesForUser/services/ws/1";
	
/***Final Release Server**/
//public static final String BASE_URL="http://epchmobile.com/epch2016/admin/webServicesForUser/services/ws/1";//http://epchmobile.com/Delhifair/admin/WebServicesForUser/services/ws/1";
	
/***Target Name Space ***/	
	public static final String			NAMESPACE="http://WebServicesForUserControllerwsdl/"; 
			 
	
	 public static final String ALPHABETIC_WORD_PATTERN	=	"(([a-zA-Z]+\\s)*[a-zA-Z]+)";	//pattern for alphabetic word with space
	 
	 public static final String				USER_URL="submitUsers";
	 public static final String				PRODUCTS_URL="submitProducts";
	 public static final String				EXIBITORS_PRODUCTS_URL="submitExibitorsProducts";
	 public static final String				PRODUCT_PHOTO_URL="submitProductPhoto";
	 public static final String				LOGIN_DETAILS_URL="submitLoginDetails";
	 public static final String				ROLE_AREA_URL="submitRoleArea";
	 public static final String				SERVER_TIME_STAMP="giveTimestamp";
	 public static final String				LOAD_DATABASE_FILE="SendUpdatedData";
	 public static final String             DATABASE_FILE="NEWDATA";
	 public static final String             UPDATED_DATA="1";
	 
	 public static final String             REGISTER_GCM_USER="registerGcmUser";
	 public static final String             DATABASE_INITIALIZE="initializeDatabase";
	 public static final String				GET_UPDATED_RECORD="getUpdatedRecords"; 
	 public static final String				SEND_MAIL="sendMailService"; 
	 public static final String				SUBMIT_VISITOR="submitVisitor";
	 public static final String				REGISTER_FOR_SPACE="registerForSpace";
	 public static final String				SET_PROMOTIONAL_INFO="setPromotionalInfo";
	 public static final String 			GIVE_RESOURCE="GiveResource";
	 
	 public static final String 			CHECK_FOR_UPDATES="checkUpdateAvaiable";
	 public static final String 			RESOURCE_SUCCESS_STATUS="05";
	 public static final String 			USER_FEEDBACK="UserFeedback";
	 public static final String 			VISITPR_REGISTRATION_FOR_DELHIFAIR="submitVisitorRegistration";	 

	 
	 private static InternetConnectionDetector icd;
	 private static Context mContext;
	 private static int count;
	 private static String fileName, filePath;
	 private static File file;
	 
	 private static void launchPDF(File file, String fPath){
	    try{
	    	 Intent intent = new Intent(Intent.ACTION_VIEW);
			    intent.setDataAndType(Uri.fromFile(file),"application/pdf");
			    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    	mContext.startActivity(intent);
	    } 
	    catch (ActivityNotFoundException e){
	    	if(icd.isConnectingToInternet())
	    	{
	    		Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://docs.google.com/viewer?url="+fPath));
				mContext.startActivity(browserIntent);
	    	}
	    	else{
	    		Toast.makeText(mContext, "Please Connect to Internet.", Toast.LENGTH_SHORT).show();
	    		icd.showInternetSettingsAlert();
	    	}
	    		
	    }
	 }
	 
	 public static void launchFile(String fName, String fPath, Context context){

		mContext = context;
		fileName = fName;
		filePath = fPath;
		icd = new InternetConnectionDetector(mContext);
		//Code to open PDF in browser
//		Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://docs.google.com/gview?embedded=true&url="+filePath+fileName));
//		mContext.startActivity(browserIntent);
			
		if(StorageHelper.isExternalStorageAvailableAndWriteable()){
	        file=new File(Environment.getExternalStorageDirectory()+File.separator+ImageAsyncTask.FOLDER_NAME+File.separator+fileName); 
    	}else{
    		file=new File("file:///data/data/"+mContext.getPackageName()+"/files/"+fileName);
    	}  
		
		if(file.exists()){

			launchPDF(file, fPath+fName);
			/*
			try{
			}catch(Exception e){
				e.printStackTrace();
				if(file!=null){
					Uri path=Uri.fromFile(file);
					Intent i =new Intent(Intent.ACTION_VIEW);
					i.setDataAndType(path,"application/pdf");
					i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					mContext.startActivity(i);
				}
				else
					return;
			}*/
		}else{
			if(count==5){
				Toast.makeText(mContext, "More information will be available soon.", Toast.LENGTH_SHORT).show();
				return;
			}else{
				makeResourceWeb(fPath+fName);
			}
		}
	}
	 private static void makeResourceWeb(String fPath){
		try{
			String name=WebService.randomString(WebService.RENDOM_STR_LENGTH);
			String key=WebService.getHmac(name,WebService.SALT);
			
			HashMap<String,String> map=new HashMap<String, String>();
    	    
    	    map.put("name",name);
    	    map.put("key",key);
    	    map.put("fName",fileName);
    	    map.put("fPath",filePath);
    	    JSONObject obj=new JSONObject(map);
    	    if(icd.isConnectingToInternet()){
    	    	Util x = new Util();
    	    	x.new ResourceDownload(fPath).execute(obj.toString());
    	    }
		}catch(Exception e){
			e.printStackTrace();
		}
	 }
	 private class ResourceDownload extends AsyncTask<String,Void,String>{
		 	String fPath;
			ProgressDialog progressDialog;
			String response;
			
			
			public ResourceDownload(String fPath) {
				super();
				this.fPath = fPath;
			}
			@Override
			protected String doInBackground(String... params) {
				Log.i("WEB_DATA","FileSetting Request- "+params[0]);
				
				response=WebService.giveResoucefile(params[0]);
				Log.i("WEB_DATA","FileSetting Response- "+response);
				count++;
				return response;
			}
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				progressDialog = new ProgressDialog(mContext);
		    	progressDialog.setMessage("Updating Data...");
		    	progressDialog.setIndeterminate(true);
		    	progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		    	progressDialog.setCancelable(false);
		    	progressDialog.show();
			}
			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				if(progressDialog!=null && progressDialog.isShowing()){
		    	     progressDialog.dismiss();
		    	}
		    	try {
					JSONObject jData=new JSONObject(result);
					String status=jData.getString(WebService.STATUS);
					if(status.equalsIgnoreCase(WebService.RESPONSE_STATUS_PASS)){   
				        String statusCode=jData.getString(WebService.STATUS_CODE);
				        if(statusCode.equalsIgnoreCase(Util.RESOURCE_SUCCESS_STATUS)){
				        	 
				        	if(StorageHelper.isExternalStorageAvailableAndWriteable()){
						        file=new File(Environment.getExternalStorageDirectory()+File.separator+ImageAsyncTask.FOLDER_NAME+File.separator+fileName); 
				        	}else{
				        		file=new File("file:///data/data/"+mContext.getPackageName()+"/files/"+fileName);
				        	}
				        	String data=jData.getString("data");
				        	StorageHelper.base64ToFile(file, data, 0);
				        	launchPDF(file, fPath);
				       }
					}else if(status.equalsIgnoreCase(WebService.RESPONSE_ERROR)){ 
						Toast.makeText(mContext,"More information will be available soon.",Toast.LENGTH_LONG).show();
					}
				} catch (Exception e) {
					Toast.makeText(mContext,"More information will be available soon.",Toast.LENGTH_LONG).show();
					e.printStackTrace();
				}
			}
		}
		public static void copyDatabase(Context c, String DATABASE_NAME) {
	        String databasePath = c.getDatabasePath(DATABASE_NAME).getPath();
	        File f = new File(databasePath);
	        OutputStream myOutput = null;
	        InputStream myInput = null;
	        Log.d("testing", " testing db path " + databasePath);
	        Log.d("testing", " testing db exist " + f.exists());

	        if (f.exists()) {
	            try {

	                File directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+ImageAsyncTask.FOLDER_NAME);
	                if (!directory.exists())
	                    directory.mkdir();

	                myOutput = new FileOutputStream(directory.getAbsolutePath()
	                        + File.separator + DATABASE_NAME);
	                myInput = new FileInputStream(databasePath);

	                byte[] buffer = new byte[1024];
	                int length;
	                while ((length = myInput.read(buffer)) > 0) {
	                    myOutput.write(buffer, 0, length);
	                }

	                myOutput.flush();
	            } catch (Exception e) {
	            } finally {
	                try {
	                    if (myOutput != null) {
	                        myOutput.close();
	                        myOutput = null;
	                    }
	                    if (myInput != null) {
	                        myInput.close();
	                        myInput = null;
	                    }
	                } catch (Exception e) {
	                }
	            }
	        }
	    }
			
		public static void writeKeyHash(Context context){
			try {
				   PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
				    
				   for (Signature signature : info.signatures)
				   {
				    MessageDigest md = MessageDigest.getInstance("SHA");
				    md.update(signature.toByteArray());
				    FileOutputStream out = new FileOutputStream(new File(Environment.getExternalStorageDirectory().getAbsoluteFile().toString()+
				    		File.separator+ImageAsyncTask.FOLDER_NAME+File.separator+"hash.txt"));
				    out.write(Base64.encodeToString(md.digest(), Base64.DEFAULT).getBytes());
				    Log.i("DATA","KeyHash: "+ Base64.encodeToString(md.digest(), Base64.DEFAULT));
				    out.close();
				   }
				    
				  } catch (IOException e) {
					  Log.i("DATA","File not found "+ e.toString());
				  } catch (NameNotFoundException e) {
				   Log.i("DATA","name not found "+ e.toString());
				  } catch (NoSuchAlgorithmException e) {
				   Log.i("DATA", "no such an algorithm "+ e.toString());
				  }
		}
		
		public static final String DEFAULT="Default";
		
		
		public static final String ALBUM				="Album";
		 public static final String ABOUTUS				="AboutUs";
		 public static final String ABOUT_DETAIL		="";
		 public static final String CITYLIST			="CityList";
		 public static final String EVENT				="Event";
		 public static final String EXHIBITORCATEGORY	="ExhibitorCategory";
		 public static final String EXHIBITORDETAILS	="ExhibitorDetails";
		 public static final String EXHIBITORS			="Exhibitors";
		 public static final String EXHIBITORNAME		="ExhibitorName";
		 public static final String FAVOURITES			="Favourites";
		 public static final String FEEDBACK			="Feedback";
		 public static final String HALL				="Hall";
		 public static final String HELPDESK			="HelpDesk";
		 public static final String HOWTOREACH			="HowToReach";
		 public static final String LAYOUTMAP			="LayoutMap";
		 public static final String MAP					="Map";
		 public static final String MAP_LAYOUT			="LayoutMap";
		 public static final String PHOTOSHOOT			="PhotoShoot";
		 public static final String PHOTOSHOOTDETAIL	="PhotoShootDetail";
		 public static final String PHOTONOTES			="PhotoNotes";
		 public static final String PLANNER				="";
		 public static final String PRODUCTLIST			="ProductList";
		 public static final String SEARCHEXHIBITOR		="SearchExhibitor ";
		 public static final String SEMINAR_CALENDER	="";
		 public static final String SOCIALMEDIA			="SocialMedia ";
		 public static final String USEFUL_INFO			="";
		 public static final String USERREGISTRATION	="Registration";
		 public static final String VENUEMAP			="VenueMap";
		 public static final String NEARBY				="NearBy";
		 public static final String HOWTOREACHROUTE		="HowToReachRoute";
		 public static final String HOME				="Home";
		 public static final String GOOGLE_MAP			="NearBy";
		 public static final String ACTIVATE_EXHIBITOR = "activateExhibitor";
		 public static final String FORGOT_PASSWORD = "ForgotPassword";
		 public static final String EDIT_USER = "editUser";
		 public static final String USER_LOGOUT ="UserLogout";
}
