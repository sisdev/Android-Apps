package com.assusoft.eFairEmall.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.assusoft.eFairEmall.soapWebService.WebService;

import android.content.Context;
import android.util.Log;

public class WebDataFile {
	Context con;
	String FILENAME="data.txt";
	String FILENAME_HIDDEN="data.ass";
	public WebDataFile(Context con)
	{
		this.con=con;
	}
	public void writeToFile(String data) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(con.openFileOutput(FILENAME, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();  
        }
        catch (IOException e) {
           Log.i("WEB_DATA", "File write failed: " + e.toString());
        }
         
    }
 
	public String readFromFile() {
         
        String ret = "";
         
        try {
            InputStream inputStream = con.openFileInput(FILENAME);
             
            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();
                 
                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }
                 
                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.i("WEB_DATA","File not found: " + e.toString());
        } catch (IOException e) {
            Log.i("WEB_DATA", "Can not read file: " + e.toString());
        }
 
        return ret;
    }
	public String deletFile()
	{
		String path=con.getFilesDir().getAbsolutePath()+"/"+FILENAME;
		File fi = new File ( path );
		//fi.delete();
		return "delete+"+fi.delete();
	}
	public void changeExtension()
	{
		/*String path=con.getFilesDir().getAbsolutePath()+"/"+FILENAME_HIDDEN;
         String path2=con.getFilesDir().getAbsolutePath()+"/"+FILENAME;
         File from = new File(path);
         File to = new File(path2);
            from.renameTo(to);*/
		File oldfile = con.getFileStreamPath(FILENAME_HIDDEN);
		File newfile = con.getFileStreamPath(FILENAME);
		oldfile.renameTo(newfile);
	}
	public boolean isFileAvailabeInInternalStorage()
	{   
		String path=con.getFilesDir().getAbsolutePath()+"/"+FILENAME;
	    File file = new File ( path ); 
	      if(!file.exists())
	      {
	      Log.i("WEB_DATA", "db txt file not exist");
		  return false;
		 
	      }
	   
	return true;
	}
	public void writeToFile_update(String data) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(con.openFileOutput(FILENAME, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
           Log.i("WEB_DATA", "File write failed: " + e.toString());
        }
         
    }
	public boolean compareFile()
	{
		String path2=con.getFilesDir().getAbsolutePath()+"/"+FILENAME;
        File file = new File(path2);
        if(file.length()==WebService.contentLengthOfInitDb)
        {   Log.i("WEB_DATA", "database file size are matched...");
        	return true;
        }else{
        	return false;
        }
	}
}
