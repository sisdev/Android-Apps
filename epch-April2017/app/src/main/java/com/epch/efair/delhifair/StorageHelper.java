package com.epch.efair.delhifair;
import java.io.File;
import java.io.FileOutputStream;

import com.assusoft.eFairEmall.databaseMaster.DatabaseHelper;

import android.os.Environment;
import android.util.Base64;
import android.util.Log;

/**
 * Checks the state of the external storage of the device.
 * 
 * @author Akshay kumar
 */
public class StorageHelper {

    // Storage states
    private static boolean externalStorageAvailable, externalStorageWriteable;

    /**
     * Checks the external storage's state and saves it in member attributes.
     */
    private static void checkStorage() {
        // Get the external storage's state
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            // Storage is available and writeable
            externalStorageAvailable = externalStorageWriteable = true;
        } else if (state.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
            // Storage is only readable
            externalStorageAvailable = true;
            externalStorageWriteable = false;
        } else {
            // Storage is neither readable nor writeable
            externalStorageAvailable = externalStorageWriteable = false;
        }
    }

    /**
     * Checks the state of the external storage.
     * 
     * @return True if the external storage is available, false otherwise.
     */
    public static boolean isExternalStorageAvailable() {
        checkStorage();
        return externalStorageAvailable;
    }

    /**
     * Checks the state of the external storage.
     * 
     * @return True if the external storage is writeable, false otherwise.
     */
    public static boolean isExternalStorageWriteable() {
        checkStorage();
        return externalStorageWriteable;
    }

    /**
     * Checks the state of the external storage.
     * 
     * @return True if the external storage is available and writeable, false
     *         otherwise.
     */
    public static boolean isExternalStorageAvailableAndWriteable() {
        checkStorage();
        Log.i("WEN_DATA", "isExternalStorageAvailableAndWriteable() method call");
        if ((externalStorageAvailable)&&(externalStorageWriteable)) {
            return true;
        }  else {
            return false;
        }
    }
    
    
    public static boolean createFolder(String path){
    	checkStorage();
    	if(externalStorageAvailable && externalStorageWriteable){
    		File sdcard = Environment.getExternalStorageDirectory();
    		File f=new File(sdcard+"/"+path);
    		f.mkdir();
    		return true;
    	}else{
    		return false;
    	}
    }
    
    public static boolean isFolderExist(String path){
    	File sdcard = Environment.getExternalStorageDirectory();
		File f=new File(sdcard+"/"+path);
		if(f.isDirectory()){
			return true;
		}else{
			return false;
		}
    }
    /**
     * 
     * @param file must contain storage location with file name .pdf or .jpg,
     * @param data it contain base 64 string
     */
    public static void base64ToFile(final File file,final String data, int locationId){
    	/*final File dwldsPath = new File(DOWNLOADS_FOLDER + fileName + ".pdf");*/
    	byte[] pdfAsBytes = Base64.decode(data, Base64.DEFAULT);
    	FileOutputStream os;
    	try {
			os = new FileOutputStream(file, false);		
	    	os.write(pdfAsBytes);
	    	os.flush();
	    	os.close();
	    	if(locationId!=0){
	    		DatabaseHelper helper = EFairEmallApplicationContext.getDatabaseHelper();
		    	helper.openDatabase(DatabaseHelper.WRITEMODE);
		    	helper.updateDownloads(locationId);
	    	}
    	} catch (Exception e) {
			e.printStackTrace();
		}
    }
}