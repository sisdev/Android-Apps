package com.epch.efair.delhifair;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

public class PdfFiles {
Context context;	
OutputStream out = null;
File pdfFile; 
@SuppressLint("SdCardPath")
public static String fileName="/packplus_event_report.pdf";
public static String filePath;
public PdfFiles(Context context)
{
	this.context=context;
}
public void pdfLunch()
{   File fileBrochure;
    Log.i("EXPO", "mount:"+StorageHelper.isExternalStorageAvailableAndWriteable());
	 if(StorageHelper.isExternalStorageAvailableAndWriteable())
	 {  filePath=Environment.getExternalStorageDirectory().getAbsolutePath()+fileName;
	    fileBrochure = new File(filePath);
      if (!fileBrochure.exists())
       {    
    	Log.i("EXPO", "CopyAssetsbrochure():");
         CopyAssetsbrochure();
       } 
	 }else{
		 Log.i("EXPO", "Internal storage:"+pdfFile);
		 filePath=context.getFilesDir().getAbsolutePath()+fileName;
		 fileBrochure = new File(filePath);
		 if (!fileBrochure.exists())
	       {    
	    	Log.i("EXPO", "intenal drive pdf is not exist:");
	         CopyAssetsbrochure();
	       } 
	 }

    /** PDF reader code */
    if(StorageHelper.isExternalStorageAvailableAndWriteable())
    {
    pdfFile = new File(filePath); 
    Log.i("EXPO", "external pdfFile:"+pdfFile);
    }else{
    	filePath="file:///data/data/"+context.getPackageName()+"/files"+fileName;
    	pdfFile = new File(filePath);
    	 Log.i("EXPO", "Internal pdfFile:"+pdfFile);
    }
    Intent intent = new Intent(Intent.ACTION_VIEW);
    intent.setDataAndType(Uri.fromFile(pdfFile),"application/pdf");
    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    try 
    {
    	context.getApplicationContext().startActivity(intent);
    } 
    catch (ActivityNotFoundException e) 
    {
         Toast.makeText(context, "PDF Viewer not available. Please download", Toast.LENGTH_SHORT).show();
         Log.i("EXPO", "FILE open exception:"+e);
    }	
}
//method to write the PDFs file to sd card
@SuppressLint("SdCardPath")
private void CopyAssetsbrochure() {
    AssetManager assetManager = context.getAssets();
    String[] files = null;
    try 
    {
        files = assetManager.list("");
        Log.i("WEB_DATA", "PDF..Copy");
    } 
    catch (IOException e)
    {
        Log.i("WEB_DATA", "PDF..IOException:"+e.getMessage());
    }
    for(int i=0; i<files.length; i++)
    {  
       // String fStr = files[i].contains("report"));
        
       // Log.i("WEB_DATA", "PDF..contan"+fStr);
        if(files[i].contains("report"))
        {   Log.i("WEB_DATA", "PDF..contan");
            InputStream in = null;
            try 
            {
              in = assetManager.open(files[i]);
              out = new FileOutputStream(filePath);
              copyFile(in, out);
              in.close();
              in = null;
              out.flush();
              out.close();
              out = null;
              break;
            } 
            catch(Exception e)
            {
                Log.e("WEB_DATA", "file copy Exception"+e.getMessage());
            } 
        }
    }
}

private void copyFile(InputStream in, OutputStream out) throws IOException {
    byte[] buffer = new byte[1024];
    int read;
    while((read = in.read(buffer)) != -1){
      out.write(buffer, 0, read);
      
    }
    Log.i("WEB_DATA", "PDF..writed");
}
}
