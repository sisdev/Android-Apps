package com.epch.efair.delhifair;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.util.Log;

import com.assusoft.eFairEmall.databaseMaster.DatabaseHelper;
import com.assusoft.eFairEmall.entities.Links;
import com.assusoft.eFairEmall.soapWebService.WebService;
import com.assusoft.eFairEmall.util.JSonParserForSubmitRecords;
import com.assusoft.eFairEmall.util.MySharedPreferences;
import com.assusoft.efair.epchfair.gcm.CommonUtilities;

public class DownloadingData {
	 DatabaseHelper dbHelper;
	 public static ProgressDialog mProgressDialog;
	 public static InitializeData initializeData ;
	 private static final String UPDATE_RECORD_STATUS_KEY="recordStatus";
	 public static final String UPDATE_AVAILABLE_VALUE="updates available";
	 public static int PROGRESS_DIALOG_DIVIDING_LENGTH;
	 private static final int MAX_ATTEMPT=5;
	 public  static final String ON_POST_EXECUTE_DOWNLOAD_KEY="onPostExecuteFlag_download";
	 //public  static final String ON_POST_EXECUTE_DOWNLOAD_VALUE="true";
	 String dataInit;JSONObject jData; 
	 JSonParserForSubmitRecords parser;
	 public static boolean isDbUpdate=false;
	 ImageUtils imageUtils;
	 MySharedPreferences pref;
	 Context context;
	 public static long startTime;
	 public  DownloadingData()
	 {
		 
	 }
 public  DownloadingData(Context context) {
	 
	 Log.i("Downloading_data","enterd in downloading data");
	 this.context=context;
	 startTime=System.currentTimeMillis();
	 dbHelper=EFairEmallApplicationContext.getDatabaseHelper();
     mProgressDialog = new ProgressDialog(context);
     mProgressDialog.setMessage("Downloading Data...");
     mProgressDialog.setIndeterminate(true);
     mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
     mProgressDialog.setCancelable(false);
     imageUtils=new ImageUtils(context);
     Commons.imageUtils=imageUtils;
     
     pref	= new MySharedPreferences(context);
     
     dbHelper.openDatabase(DatabaseHelper.WRITEMODE);
    //Checking database is empty or not
     if(dbHelper.isTablesEmpty()){
         isDbUpdate=true;
     }else{
    	 isDbUpdate=true;
    	 Log.i("EFAIR", "Tables update database");
     }
     
     InitializeData initData=new InitializeData(context);
     initData.execute();
     initializeData=initData;
     mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
         @Override
         public void onCancel(DialogInterface dialog) {
            /// downloadTask.cancel(true);
         }
     });
}

/*************** AsyncTask to download data from sever ****************/
public class InitializeData extends AsyncTask<String, Integer, String> {

    private Context context;
    private PowerManager.WakeLock mWakeLock;

    public InitializeData(Context context) {
        this.context = context;
        
    }
	@SuppressLint("SimpleDateFormat")
	@Override
    protected String doInBackground(String... sUrl) {
		
        	
		parser=new JSonParserForSubmitRecords(context);
		for(int i=0;i<MAX_ATTEMPT;i++){
			 Log.i("PACKPLUS", "Thread interrupted: abort remaining retries!"+i);
			if(isDbUpdate)	{
				getDatabaseUpdate(context);
			}else{
				getDatabaseInitialize(context);
				//publishProgress((int) (total * 100 / length));
			}
			if((WebService.isResponseOk)){
				 Log.d("EXPO", "WebService.isResponseOk!"+WebService.isResponseOk);
				return null;
			}
			try {
               
                Thread.sleep(1000);
            } catch (InterruptedException e1) {
                // Activity finished before we complete - exit.
                Log.d(CommonUtilities.TAG, "Thread interrupted: abort remaining retries!");
                Thread.currentThread().interrupt();
                return null;
            }
		}	
	 
        return null;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // take CPU lock to prevent CPU from going off if the user 
        // presses the power button during download
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
             getClass().getName());
        mWakeLock.acquire();
        mProgressDialog.show();
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        super.onProgressUpdate(progress);
        // if we get here, length is known, now set indeterminate to false
        if(!MainActivity.activityInBackGround){
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setMax(100);
        mProgressDialog.setProgress(progress[0]);
        }
    }

    @Override
    protected void onPostExecute(String result) {
    	if(!MainActivity.activityInBackGround){
	        mWakeLock.release();
	       /// mProgressDialog.dismiss();
	        if((WebService.isResponseOk)){
	        	WebService.isResponseOk=false;
	          Log.i("EXPO", "isResponseOk");
	        	//Start image downloading
	        	new ImageDownloading(context);
	        }else{
	        	mProgressDialog.dismiss();
	        	new AlertDialog.Builder(context)
			    .setTitle("Download error")
			    .setMessage("Not able to download data, Please retry")
			    .setCancelable(true)
			    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { 
			        	System.exit(0);
			        }
			     })
			    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { 
			        	dialog.dismiss();
			        	/*Intent intent=new Intent(context, MainActivity.class);
			        	MainActivity.activity.startActivity(intent);
			        	MainActivity.activity.finish();*/
			        	new DownloadingData(context);
			        }
			     })
			    .setIcon(android.R.drawable.ic_dialog_alert)
			     .show();
	        	
	           }
        }else{
        	SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        	 SharedPreferences.Editor editor = preferences.edit();
   		     editor.putBoolean(ON_POST_EXECUTE_DOWNLOAD_KEY,true);
   		     editor.commit();
   		  Log.i("EXPO", "on post executed is saves:");
        }
       
    }
    public void getDatabaseInitialize(Context context){
    	 try{
    	    String name=WebService.randomString(WebService.RENDOM_STR_LENGTH);
    	    String key=WebService.getHmac(name,WebService.SALT);
    	    String param="{\"name\":"+"\""+name+"\","+
    	             "\"key\":"+"\""+key+"\""+"}";
    	     WebService.getDatabaseInitialize(context,param);
    	    if(!Commons.webDataFile.compareFile())
    	     {   String name2=WebService.randomString(WebService.RENDOM_STR_LENGTH);
     	         String key2=WebService.getHmac(name,WebService.SALT);
     	         String param2="{\"name\":"+"\""+name2+"\","+
     	             "\"key\":"+"\""+key2+"\""+"}";
    	    	 WebService.getDatabaseInitialize(context,param2);
    	     }
    	     dataInit=Commons.webDataFile.readFromFile();
    	     
    	     Log.i("json data",dataInit);
    	     
    	     jData=new JSONObject(dataInit);
    	    
    	     PROGRESS_DIALOG_DIVIDING_LENGTH=jData.length();
    	     parser.JSonParser(jData);
    	     submitData();
    	     
    	 }catch(Exception e){
    		 e.printStackTrace(); 
    	 }
    }
    @SuppressLint("SimpleDateFormat")
    public void getDatabaseUpdate(Context context){
    	try {
    		String timeStamp = dbHelper.getLattestTimeStam();
    		Log.i("WEB_DATA", "timeStamp:"+timeStamp);
    		String name=WebService.randomString(WebService.RENDOM_STR_LENGTH);
    	    String key=WebService.getHmac(name,WebService.SALT);
    	    Log.i("WEB_DATA", "name:"+name+"key:"+key);
    	 if(timeStamp!=null){	
    		String param="{\"greatestTimeStamp\":"+"\""+timeStamp+"\","+
     	             "\"name\":"+"\""+name+"\","+
     	             "\"key\":"+"\""+key+"\""+"}";
    		Log.i("Database_update TimeStamp","greatestTimeStamp: "+timeStamp);
    		WebService.getUpdatedRecords(context,param);
    		dataInit=Commons.webDataFile.readFromFile();
    		//Log.i("JSON Response",dataInit);
    		long endTime=System.currentTimeMillis();
    		Log.i("DATA","data download complete :"+((endTime-MainActivity.startTime)/1000f));
    		
    		jData=new JSONObject(dataInit);
    		 Log.i("EFAIR","updateStatus : "+jData.get(UPDATE_RECORD_STATUS_KEY).toString());
    		 if(UPDATE_AVAILABLE_VALUE.equals(jData.get(UPDATE_RECORD_STATUS_KEY).toString())){
    			 
    			 PROGRESS_DIALOG_DIVIDING_LENGTH=jData.length();
    			 parser.JSonParser(jData);
    		     submitData();
    		     //for updating venue map file
    		     /*if(MainActivity.updatingVenueMapFile!=null){
    		    	 for(int i=0;i<MainActivity.updatingVenueMapFile.size();i++){
	    		    	 boolean result=imageUtils.updateVenueMapFile(MainActivity.updatingVenueMapFile.get(i).getFilePath());
		    		    	 Log.i("EXPO", "Image update result:"+result);
		    		       }   
		    		     //DatabaseHelper.updatingVenueMapFile.clear();    		     
    		     }*/
    		     Log.i("WEB_DATA","Size of Links "+parser.getLinks().size());
    		     /**Downloading html file**/
    		     if(!parser.getLinks().isEmpty()){
    		    	 downloadHTML(parser.getLinks());
    		     }
    		 }else
    		 {
    			 return;
    		 }
    		}
    	} catch (JSONException e) {
    		e.printStackTrace();
    	} catch (Exception e) {
			e.printStackTrace();
		}
    	 
    	Log.i("WEB_DATA", "updating data:"+dataInit);
    	 
    }
    public void submitData()
    {   
    	try { 
	    	dbHelper.openDatabase(DatabaseHelper.WRITEMODE);
	    	
	        if(!parser.getSubmitProducts().isEmpty()){
	        	dbHelper.submitProducts(parser.getSubmitProducts());
	        	myProgress(1*(float)60/PROGRESS_DIALOG_DIVIDING_LENGTH);
	        }
	       /*dbHelper.submitRoles(parser.getUpdatedRoles());
	        dbHelper.submitUser(parser.getUpdatedUsers());*/
	        //parser.JSonParser(jData);
	        if(!parser.getSubmitExhibitors().isEmpty()){
	        	dbHelper.submitExhibitors(parser.getSubmitExhibitors());
	        	myProgress(2*(float)60/PROGRESS_DIALOG_DIVIDING_LENGTH);        
	        }
	        if(!parser.getSubmitLanguage().isEmpty()){
	        	dbHelper.submitLanguage(parser.getSubmitLanguage());
	        	myProgress(3*(float)60/PROGRESS_DIALOG_DIVIDING_LENGTH);
	        }
	        if(!parser.getSubmitExhibitorLang().isEmpty()){
	        	dbHelper.submitExhibitorLang(parser.getSubmitExhibitorLang());
	        	myProgress(4*(float)60/PROGRESS_DIALOG_DIVIDING_LENGTH);
	        }
	        if(!parser.getSubmitVenueMap().isEmpty()){
	        	dbHelper.submitVenueMap(parser.getSubmitVenueMap());
	        	myProgress(5*(float)60/PROGRESS_DIALOG_DIVIDING_LENGTH);
	        }
	        if(!parser.getSubmitExhibitorLocation().isEmpty()){
	        	dbHelper.submitExhibitorLocation(parser.getSubmitExhibitorLocation());
	        	myProgress(6*(float)60/PROGRESS_DIALOG_DIVIDING_LENGTH);
	        }
	        if(!parser.getSubmitEvents().isEmpty()){
	        	dbHelper.submitEvent(parser.getSubmitEvents());
	        	myProgress(7*(float)60/PROGRESS_DIALOG_DIVIDING_LENGTH);
	        }
	        if(!parser.getSubmitEventInfo().isEmpty()){
	        	dbHelper.submitEventInfo(parser.getSubmitEventInfo());
	        	myProgress(8*(float)60/PROGRESS_DIALOG_DIVIDING_LENGTH);
	        }
	        if(!parser.getSubmitEventLocation().isEmpty()){
	        	dbHelper.submitEventLocation(parser.getSubmitEventLocation());
	        	myProgress(9*(float)60/PROGRESS_DIALOG_DIVIDING_LENGTH);
	        }
	        if(!parser.getSubmitExhibitorProducts().isEmpty()){
	        	dbHelper.submitExhibitorProduct(parser.getSubmitExhibitorProducts());
	        	myProgress(10*(float)60/PROGRESS_DIALOG_DIVIDING_LENGTH);
	        }
	        if(!parser.getSubmitProductPhoto().isEmpty()){
	        	dbHelper.submitProductPhoto(parser.getSubmitProductPhoto());
	        	myProgress(11*(float)60/PROGRESS_DIALOG_DIVIDING_LENGTH);
	        }
	        if(!parser.getSubmitCompany().isEmpty()){
	        	dbHelper.submitCompany(parser.getSubmitCompany());
	        	myProgress(12*(float)60/PROGRESS_DIALOG_DIVIDING_LENGTH);
	        }
	        if(!parser.getSubmitResource().isEmpty()){
	        	dbHelper.submitResource(parser.getSubmitResource());
	        	myProgress(13*(float)60/PROGRESS_DIALOG_DIVIDING_LENGTH);
	        }
	        if(!parser.getSubmitFileSetting().isEmpty()){
	        	dbHelper.submitFilesetting(parser.getSubmitFileSetting());
	        	myProgress(14*(float)60/PROGRESS_DIALOG_DIVIDING_LENGTH);
	        }
	        if(!parser.getSubmitExhibitorEntryExit().isEmpty()){
	        	dbHelper.submitExhibitorEntryExit(parser.getSubmitExhibitorEntryExit());
	        	myProgress(15*(float)60/PROGRESS_DIALOG_DIVIDING_LENGTH);
	        }
	        if(!parser.getSubmitfacilityInformation().isEmpty()){
	        	dbHelper.submitFacilityInfo(parser.getSubmitfacilityInformation());
	        	myProgress(16*(float)60/PROGRESS_DIALOG_DIVIDING_LENGTH);
	        }
	        if(!parser.getSubmitfacilityEntryExit().isEmpty()){
	        	dbHelper.submitfacilityEntryExit(parser.getSubmitfacilityEntryExit());
	        	myProgress(17*(float)60/PROGRESS_DIALOG_DIVIDING_LENGTH);
	        }
	        
	        if(!parser.getLinks().isEmpty()){
	        	dbHelper.submitLinks(parser.getLinks());
	        	myProgress(18*(float)60/PROGRESS_DIALOG_DIVIDING_LENGTH);
	        }
	        if(!parser.getExhibitorCategories().isEmpty()){
	        	dbHelper.submitExhibitorCategory(parser.getExhibitorCategories());
	        	myProgress(19*(float)60/PROGRESS_DIALOG_DIVIDING_LENGTH);
	        }
	        if(!parser.getQuestionnaires().isEmpty()){
	        	dbHelper.submitQuestionnaires(parser.getQuestionnaires());
	        	myProgress(20*(float)60/PROGRESS_DIALOG_DIVIDING_LENGTH);
	        }
	        Log.i("EFAIR","SIZE- "+parser.getOptions().size());
	        if(!parser.getOptions().isEmpty()){
	        	dbHelper.submitQueOption(parser.getOptions());
	        	myProgress(21*(float)60/PROGRESS_DIALOG_DIVIDING_LENGTH);
	        }
	        if(!parser.getAdServices().isEmpty()){
	        	dbHelper.submitAdService(parser.getAdServices());
	        	myProgress(22*(float)60/PROGRESS_DIALOG_DIVIDING_LENGTH);
	        }
	        if(!parser.getMartFacilities().isEmpty()){
	        	dbHelper.submitMartFacilities(parser.getMartFacilities());
	        	myProgress(23*(float)60/PROGRESS_DIALOG_DIVIDING_LENGTH);
	        }
	        
	        dbHelper.close();
        } catch (Exception e) {
        	Log.i("UPDATES", "exception in submit:"+e);
        	//dbHelper.close();
        }
    	
    }
    public  void myProgress(float count)
	{  
    	 publishProgress((int)count);
	} 
}
	public void downloadHTML(ArrayList<Links> links){
		File file;
		InputStream is = null;
		FileOutputStream output = null;
		int count, length;
		byte[] data;
		String linkUrl="", baseUrl="";
		URL url;
		URLConnection connection;
		
		file	=	new File(Environment.getExternalStorageDirectory(),ImageAsyncTask.FOLDER_NAME);
		if(!file.exists()){
			file.mkdir();
		}
		
		baseUrl	= pref.getSavedValue(MySharedPreferences.STATIC_PAGE_URL);
		Log.i("WEB_DATA","Base URL : "+baseUrl);
		length	= links.size();
		data	= new byte[1024];
		
		for(int i=0;i<length;i++){
			
			linkUrl	= links.get(i).getLinkUrl();
			if(!linkUrl.startsWith("http")){
				try {
					url			= new URL(baseUrl+linkUrl);
					connection	= url.openConnection();
					connection.connect();
					
					is			= new BufferedInputStream(url.openStream(),8192); //8kb
					output		= new FileOutputStream(Environment.getExternalStorageDirectory().getAbsoluteFile().toString()+
									File.separator+ImageAsyncTask.FOLDER_NAME+File.separator+linkUrl);
					while((count=is.read(data))!=-1){
						output.write(data, 0, count);
					}
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (Exception e){
					e.printStackTrace();
				} finally{
					try {
						if(output!=null){
							output.flush();
							output.close();
							is.close();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			initializeData.myProgress(60+(20*(float)(i+1)/length));
			Log.i("WEB_DATA","file '"+linkUrl+"' successfully downloaded..");
		}
	}


}

