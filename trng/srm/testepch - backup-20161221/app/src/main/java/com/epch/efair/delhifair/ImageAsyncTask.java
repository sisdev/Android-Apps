package com.epch.efair.delhifair;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.PowerManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.assusoft.eFairEmall.databaseMaster.DatabaseHelper;
import com.assusoft.eFairEmall.soapWebService.WebService;
import com.assusoft.eFairEmall.util.MySharedPreferences;
import com.epch.efair.delhifair.R;
import com.assusoft.efair.epchfair.Fragments.FirstViewFragment;
import com.assusoft.efair.epchfair.Fragments.UserRegistrationFragment;
	/*************** AsyncTask to download data from sever ****************/
	public class ImageAsyncTask extends AsyncTask<String, Integer, String> {
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
	public static String FOLDER_NAME=".EPCH_ASSUSOFT";
	public String[] layoutes;//{"hall_layout_14.jpg","hall_layout_18.jpg","hall_layout_7.jpg","hall_layout_8_11.jpg","hall_layout_12_12a.jpg",
   // "layout_hall_1r.jpg"};
	public static String[] layoutes_txt;
	///public static String[] layoutes;
	   private Context context;
	   private PowerManager.WakeLock mWakeLock;
	   long startTime,endTime;

	   public ImageAsyncTask(Context context,boolean isShowDialog) {
	       this.context = context; 
	       this.isShowDialog=isShowDialog;
	       imageUtils=new ImageUtils(context);
	   
			layoutes=imageUtils.layoutes;
			layoutes_txt=changeFExtension(layoutes);
			for(int i=0;i<layoutes.length;i++)
			{
			Log.i("WEB_DATA","layoutes:"+layoutes[i]);
			}
			
	   }

	   @SuppressWarnings("resource")
		@SuppressLint("SdCardPath")
		@Override
	   protected String doInBackground(String... sUrl) {
	      
	     //Creating folder
	       File folder = new File(Environment.getExternalStorageDirectory(),FOLDER_NAME);
	       String urlfinal[]=sUrl[0].split(",");
	       int urlsLength=urlfinal.length;
	       Log.i("WEB_DATA", "NO of urls:"+urlfinal.length+",urlfinal:"+urlfinal[0]);
	       try {
	    	   startTime=System.currentTimeMillis();
	    	   for(int i=0;i<urlsLength;i++)
	    	   { 	   
	           URL url = new URL(urlfinal[i]);
	           //Checking external storage is available or not
	           if(StorageHelper.isExternalStorageAvailableAndWriteable())
	           { Log.i("WEN_DATA", "External storage is available:");
                   
	                file = new File (folder, layoutes_txt[i]);
	              
	               if (!folder.exists()) {
	                   folder.mkdir();
	                   output = new FileOutputStream(file);
	               }else{
	            	   if(!imageUtils.isFileAvailabeInExternalStorage())
	            	   {  
	            		  long fileLength=urlConnections(url);
	       	           if((file.length()==fileLength))
	       	           {  
	       	              //file.delete(); 
	       	              File from = new File(folder,layoutes_txt[i]);
	       	              File to = new File(folder,layoutes[i]);
	       	              from.renameTo(to);
	       	           Log.i("WEB_DATA", "file.length():"+file.length()+",fileLength:"+fileLength);
	       	           }
	            	   }else{
	            		   Log.i("WEB_DATA", "Break file is exist");
	            	   } 
	               }
	        	     
	           }else{
	           	 /****************/
	           	 Log.i("WEN_DATA", "Internal storage:");
	               String path=context.getFilesDir().getAbsolutePath()+"/"+layoutes_txt[i];
	               String path2=context.getFilesDir().getAbsolutePath()+"/"+layoutes[i];
	               Log.i("WEN_DATA", "path:"+path);
	               file = new File ( path ); 
	               if(!imageUtils.isFileAvailabeInInternalStorage())
	               {   
	            	   long fileLength=urlConnections(url);
	                if((file.length()==fileLength))
	       	           {  
	       	              File from = new File(path);
	       	              File to = new File(path2);
	       	              from.renameTo(to);
	       	           }
	                Log.i("WEB_DATA", "file is not exist in internal storage");
	               }else{
	            	   Log.i("WEB_DATA", "file is exist in internal storage");  
	               }
	               /**************************/
	           }
	       }
	       }catch (Exception e) {
	    	   Log.i("WEB_DATA","Image write exception:"+e);
	           return e.toString();
	          
	       } finally {
	           try {
	               if (output != null)
	                   output.close();
	               if (input != null)
	                   input.close();
	           } catch (IOException ignored) {
	           }

	           if (connection != null)
	               connection.disconnect();
	       }
	       return null;
	   }
	   @Override
	   protected void onPreExecute() {
	       super.onPreExecute();
	       if(!MainActivity.activityInBackGround)
	       {
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
		   Log.i("IMAGE","image download time is "+((endTime-startTime)/1000f)+"s");
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
		   }else{*/
	       	FirstViewFragment fragment= new FirstViewFragment();
	        FragmentTransaction ft =MainActivity.fm.beginTransaction();
	        ft.replace(R.id.content_frame_registration, fragment,"Home");
	        ft.commit();
		   //}
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
	       
	      for(int i=0;i<strArray.length;i++)
	      {  
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
	    		} else {
	    		    throw new IllegalArgumentException("String " + string + " does not contain extension jpg or png");
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
	             if (fileLength > 0) // only if total length is known
	                 ///publishProgress((int) (bufferLength * (100.0 /fileLength)));//(total*((float)100/fileLength))/(100*layoutes_txt.length)
	            	 
	           	DownloadingData.initializeData.myProgress(progressUpdate+(float)total/(fileLength));
	             
	            // Log.i("WEB_DATA", "imageDownloadCount:"+imageDownloadCount);
	           } 
	           progressUpdate+=(DownloadingData.PROGRESS_DIALOG_DIVIDING_LENGTH-PROGRESS_UPDATE_START)/layoutes_txt.length;
	    
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return fileLength;  
	   }
	}
