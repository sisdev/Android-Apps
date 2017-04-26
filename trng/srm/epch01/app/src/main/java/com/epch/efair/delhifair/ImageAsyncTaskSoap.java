package com.epch.efair.delhifair;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.PowerManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;

import com.assusoft.eFairEmall.databaseMaster.DatabaseHelper;
import com.assusoft.eFairEmall.internetAndGpsMaster.InternetConnectionDetector;
import com.assusoft.eFairEmall.soapWebService.WebService;
import com.assusoft.eFairEmall.util.MySharedPreferences;
import com.assusoft.eFairEmall.util.Util;
import com.assusoft.efair.epchfair.Fragments.FirstViewFragment;
import com.assusoft.efair.epchfair.Fragments.UserRegistrationFragment;


public class ImageAsyncTaskSoap extends AsyncTask<String, Integer, String> {
	ProgressDialog mProgressDialog;
	MySharedPreferences mySharedPreferences;
	boolean isShowDialog=false;
	private static String FILE_EXTENSION_JPG=".jpg";
	private static String FILE_EXTENSION_PNG=".png";
	private static String FILE_EXTENSION_TXT=".txt";
	private static float progressUpdate=12;
	private static float PROGRESS_UPDATE_START=12;
	public static boolean onPostExecuteFlag;
	String testUrl;
	DatabaseHelper dbHelper;
	ImageUtils imageUtils;
	 HttpURLConnection connection = null;
	 InputStream input = null;
     OutputStream output = null;
	 File file;
	 InternetConnectionDetector icd;
	public static String FOLDER_NAME=ImageAsyncTask.FOLDER_NAME;
	//public String[] layoutes;//{"hall_layout_14.jpg","hall_layout_18.jpg","hall_layout_7.jpg","hall_layout_8_11.jpg","hall_layout_12_12a.jpg",
   // "layout_hall_1r.jpg"};
	public static String[] layoutes_txt;
	///public static String[] layoutes;
	   private Context context;
	   private PowerManager.WakeLock mWakeLock;
	   private long startTime,endTime;
	   private String baseURL;

	   public ImageAsyncTaskSoap(Context context,boolean isShowDialog) {
	       this.context = context; 
	       this.isShowDialog=isShowDialog;
	       imageUtils=new ImageUtils(context);
	   
			//ayoutes=imageUtils.layoutes;
			/*layoutes_txt=changeFExtension(layoutes);
			for(int i=0;i<layoutes.length;i++){
			Log.i("WEB_DATA","layoutes:"+layoutes[i]);
			}*/
	       baseURL = imageUtils.getImageUrl();
	       icd=new InternetConnectionDetector(context);
	   }

	   @SuppressWarnings("resource")
		@SuppressLint("SdCardPath")
		@Override
	   protected String doInBackground(String... sUrl) {
	       
//		   String imageUrls[]=sUrl[0].toString().split(",");
//	       int urlsLength=imageUrls.length;             
	       int urlsLength=MainActivity.updatingVenueMapFile.size();
	       Log.i("EFAIR", "NO of urls:"+urlsLength);
	       File folder = new File(Environment.getExternalStorageDirectory(),FOLDER_NAME);
	       if(!folder.exists()){
	    	   folder.mkdir();
	       }
	       try {
	    	   startTime=System.currentTimeMillis();
	    	   for(int i=0;i<MainActivity.updatingVenueMapFile.size();i++){
	    		   DownloadingData.initializeData.myProgress(80+(20*(float)(1+i)/urlsLength));	    		   
	    		   File file;
			       if(StorageHelper.isExternalStorageAvailableAndWriteable()){					        
					    file=new File(Environment.getExternalStorageDirectory()+File.separator+ImageAsyncTask.FOLDER_NAME+File.separator+MainActivity.updatingVenueMapFile.get(i).getFilePath());
					  
			       	}else{
			       		file=new File("file:///data/data/"+context.getPackageName()+"/files/"+MainActivity.updatingVenueMapFile.get(i).getFilePath());
			       	}			       
			       if(MainActivity.updatingVenueMapFile.get(i).getIsUpdated()==0){
			    	   Log.i("WEB_DATA","File Exists "+urlsLength+" "+i);
			    	   continue;
			       }
			       String param =makeResourceWeb(MainActivity.updatingVenueMapFile.get(i).getFilePath(), ImageDownloading.getImgBaseUrl);
	    		   Log.i("EFAIR","Image param:"+param);
	    		   parseImageData(param, MainActivity.updatingVenueMapFile.get(i).getFilePath(), MainActivity.updatingVenueMapFile.get(i).getLocationId());
	    		   
	    	   }
	    	   /*for(int i=0;i<urlsLength;i++){	    		   
	    		   Log.i("WEB_DATA","ImageAsyncTaskSoap "+urlsLength+" "+i);
	    		   DownloadingData.initializeData.myProgress(80+(20*(float)(1+i)/urlsLength));	    		   
	    		   File file;
			       if(StorageHelper.isExternalStorageAvailableAndWriteable()){					        
					    file=new File(Environment.getExternalStorageDirectory()+File.separator+ImageAsyncTask.FOLDER_NAME+File.separator+ImageDownloading.layoutes[i]);
					  
			       	}else{
			       		file=new File("file:///data/data/"+context.getPackageName()+"/files/"+ImageDownloading.layoutes[i]);
			       	}			       
			       if(file.exists()){
			    	   Log.i("WEB_DATA","File Exists "+urlsLength+" "+i);
			    	   continue;
			       }
	    		   String param =makeResourceWeb(ImageDownloading.layoutes[i], ImageDownloading.getImgBaseUrl);
	    		   Log.i("EFAIR","Image param:"+param);
	    		   parseImageData(param, ImageDownloading.layoutes[i]);
	    		   Log.i("WEB_DATA","ImageAsyncTaskSoap : "+layoutes[i]);
	    		}*/
	       }catch (Exception e) {
	    	   Log.i("WEB_DATA","Image write exception:"+e);
	          // return e.toString();
	          
	       } 
	       return null;
	   }
	   @Override
	   protected void onPreExecute() {
	       super.onPreExecute();
	       if(!MainActivity.activityInBackGround){
	       // take CPU lock to prevent CPU from going off if the user 
	       // presses the power button during download
	       PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
	       mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
	            getClass().getName());
	       mWakeLock.acquire();
	      
	       mySharedPreferences=new MySharedPreferences(context);
	      
	       }
	       
	   }

	   @Override
	   protected void onProgressUpdate(Integer... progress) {
	       super.onProgressUpdate(progress);
	       // if we get here, length is known, now set indeterminate to false
	       
	   }

	   @Override
	   protected void onPostExecute(String result) {
		   endTime=System.currentTimeMillis();
		   Log.i("IMAGE","download time is "+((endTime-startTime)/1000f)+"s");
		   Log.i("IMAGE","Database download time : "+((endTime-DownloadingData.startTime)/1000f)+"s");
		   if(!MainActivity.activityInBackGround)
		   {
	       mWakeLock.release();
	       DownloadingData.mProgressDialog.dismiss();
	      
	      /*if(mySharedPreferences.isUserRegistered(WebService.REGISTERED_STATUS))
		   {
	    	   Intent homepage=new Intent(context,HomeAcitityFirst.class);
		       MainActivity.activity.startActivity(homepage);
		       MainActivity.activity.finish();
		   }else{
	        UserRegistrationFragment fragment= new UserRegistrationFragment();
	        FragmentTransaction ft =MainActivity.fm.beginTransaction();
	        ft.replace(R.id.content_frame_registration, fragment,"Home");
	        ft.commit();
		   }*/
	       FirstViewFragment fragment= new FirstViewFragment();
	        FragmentTransaction ft =MainActivity.fm.beginTransaction();
	        ft.replace(R.id.content_frame_registration, fragment,"Home");
	        ft.commit();
		   }else{
			   onPostExecuteFlag=true;
			   Log.i("EXPO", "onPostExecute called in imageAsyncTask");
		   }
	       
	   }
	   @Override
       protected void onCancelled() {
		   super.cancel(true);
           Log.i("WEB_DATA", "AsyncTask onCancelled()");
       }
	   public String[] changeFExtension(String[] strArray)
	   {   String [] str=new String[strArray.length];
	       
	      for(int i=0;i<strArray.length;i++){  
	    	  try{
	    		  String string = strArray[i];
		    	  if (string.contains(FILE_EXTENSION_JPG)) {
		    		    // Split it.
		    		  String[] parts = string.split(FILE_EXTENSION_JPG);
		   	          String part1 = parts[0]; 
		   	          //String part2 = parts[1]; 
		   	          str[i]=part1+FILE_EXTENSION_TXT;
		    		}else if (string.contains(FILE_EXTENSION_PNG)) {
		    		    // Split it.
		    		  String[] parts = string.split("FILE_EXTENSION_PNG");
		   	          String part1 = parts[0]; 
		   	          //String part2 = parts[1]; 
		   	          str[i]=part1+FILE_EXTENSION_TXT;
		    		} /*else {
		    		    throw new IllegalArgumentException("String " + string + " does not contain extension jpg or png");
		    		}	 */ 
	    	  }catch(Exception e){
	    		  e.printStackTrace();
	    	  }
	      }
	      return str;
	   }
	   public long urlConnections(URL url)
	   {   int fileLength=0;
	       
		   /* Open a connection */
           URLConnection ucon;
		try {
			ucon = url.openConnection();
			connection = (HttpURLConnection)ucon;
	           connection.setRequestMethod("GET");
	           connection.setDoOutput(false);
	           connection.connect();
	           Log.i("EXPO", "response error "+connection.getResponseCode());
	             if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) 
	             {
	              input = connection.getInputStream();
	             }
	           // might be -1: server did not report the length
	           fileLength = connection.getContentLength();
	          
	           // download the file
	           input = connection.getInputStream();
			   output = new FileOutputStream(file);
			   FileOutputStream fos = new FileOutputStream(file);  
	           byte[] buffer = new byte[1024];
	           int bufferLength = 0;
	           long total = 0;
	           while ( (bufferLength = input.read(buffer)) >0 ) 
	           {     total += bufferLength;             
	             fos.write(buffer, 0, bufferLength);                    
	             // publishing the progress....
	            /* if (fileLength > 0) // only if total length is known
	                 ///publishProgress((int) (bufferLength * (100.0 /fileLength)));//(total*((float)100/fileLength))/(100*layoutes_txt.length)
	            	 
	           	DownloadingData.initializeData.myProgress(progressUpdate+(float)total/(fileLength));*/
	             
	            // Log.i("WEB_DATA", "imageDownloadCount:"+imageDownloadCount);
	           } 
	           /*progressUpdate+=(DownloadingData.PROGRESS_DIALOG_DIVIDING_LENGTH-PROGRESS_UPDATE_START)/layoutes_txt.length;*/
	    
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return fileLength;  
	   }
	   
	   
	   String makeResourceWeb(String fileName,String filePath){
			try{
				String param;
				String name=WebService.randomString(WebService.RENDOM_STR_LENGTH);
				String key=WebService.getHmac(name,WebService.SALT);
				
				HashMap<String,String> map=new HashMap<String, String>();
	    	    
	    	    map.put("name",name);
	    	    map.put("key",key);
	    	    map.put("fName",fileName);
	    	    map.put("fPath",filePath);
	    	    JSONObject obj=new JSONObject(map);
	    	    param=obj.toString();
	    	    if(icd.isConnectingToInternet()){
	    	    	//new ResourceDownload().execute();
	    	    	return param;
	    	    }
	    	    Log.i("EXPO","resource param "+param);
			}catch(Exception e){
				e.printStackTrace();
			}
			return null;
		}
	   
	   void parseImageData(String param,String fileName, int locationId){
		   try {
			    String  response=WebService.giveResoucefile(param);
			    Log.v("WEB_DATA", "Response: "+response);
				JSONObject jData=new JSONObject(response);
				String status=jData.getString(WebService.STATUS);
				File file;
				Log.i("EFAIR","parse image ");
				if(status.equalsIgnoreCase(WebService.RESPONSE_STATUS_PASS)){   
			        String statusCode=jData.getString(WebService.STATUS_CODE);
			        if(statusCode.equalsIgnoreCase(Util.RESOURCE_SUCCESS_STATUS)){
			        	 
			        	if(StorageHelper.isExternalStorageAvailableAndWriteable()){
					        
					        file=new File(Environment.getExternalStorageDirectory()+File.separator+ImageAsyncTask.FOLDER_NAME+File.separator+fileName); 
			        	}else{
			        		file=new File("file:///data/data/"+context.getPackageName()+"/files/"+fileName);
			        	}
			        	Log.i("EFAIR","parse image "+file.getAbsolutePath());
			        	String data=jData.getString("data");
			        	StorageHelper.base64ToFile(file, data, locationId);
			        	
			        }
			        
				}else if(status.equalsIgnoreCase(WebService.RESPONSE_ERROR))
				{ 
					String error=jData.getString(WebService.RESPONSE_ERROR);
					Toast.makeText(context,error,Toast.LENGTH_LONG).show();
				}
			} catch (Exception e) {
				e.printStackTrace();
				
				
			}
	   }
	}
