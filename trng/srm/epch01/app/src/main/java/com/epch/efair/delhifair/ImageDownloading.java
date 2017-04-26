package com.epch.efair.delhifair;

import android.content.Context;
import android.util.Log;

public class ImageDownloading {
	
	public static String getImgBaseUrl;
	 static StorageHelper storageHelper;
	 public static String[] layoutes;
	 public static ImageAsyncTask imageAsyncObj;
	 ImageUtils imgUtils;
	 String urls="";
	 Context context;
public  ImageDownloading(Context context) {
	this.context=context;
	try {   
			imgUtils=new ImageUtils(context);
			Commons.imageUtils=imgUtils;
		
	    getImgBaseUrl=imgUtils.getImageUrl();
	    Log.i("WEB_DATA","getImgBaseUrl:"+getImgBaseUrl);
	    ImageAsyncTaskSoap imageAsyncTask=new ImageAsyncTaskSoap(context, true);
	    imageAsyncTask.execute(urls);
	    //ImageAsyncTask downloadTask = new ImageAsyncTask(context,true);
	    //layoutes=imgUtils.layoutes;
	   /* for(int i=0;i<layoutes.length;i++){
	    	urls=(i!=layoutes.length-1)?(urls+=getImgBaseUrl+layoutes[i]+","):(urls+=getImgBaseUrl+layoutes[i]);
	    }*/
	    Log.i("WEB_DATA","urls: size -"+urls);
	    /*imageAsyncObj=downloadTask;
	     downloadTask.execute(urls);*/
	    /*ImageAsyncTaskSoap imageAsyncTask=new ImageAsyncTaskSoap(context, true);
	    imageAsyncTask.execute(urls);*/
	} catch (Exception e) {
		Log.i("WEB_DATA",urls+",exception:"+e);
	}
	
     
}

}

