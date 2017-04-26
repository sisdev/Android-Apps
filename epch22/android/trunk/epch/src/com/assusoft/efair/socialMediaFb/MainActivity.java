package com.assusoft.efair.socialMediaFb;


import java.io.FileInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;

import com.epch.efair.delhifair.R;


public class MainActivity extends FragmentActivity {
        
        private FbMainFragment mainFragment;
        Intent intent;
       public static Bitmap image=null;
       public static final String FILENAME="EfairEmall.jpg";
        
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.myfirstintegration", 
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                }
        } catch (NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
        
        //instantiate the bitmap for custom camera activity
       
        	try{
        	    FileInputStream fis = MainActivity.this.openFileInput(FILENAME);
        	        image = BitmapFactory.decodeStream(fis);
        	        fis.close();
        	       
        	    }
        	    catch(Exception e){
        	    }
		         
       
       // image=getIntent().getExtras().getParcelable("PHOTO");
        
        
        if (savedInstanceState == null) {
                // Add the fragment on initial activity setup
                mainFragment = new FbMainFragment();
            getSupportFragmentManager()
            .beginTransaction()
            .add(android.R.id.content, mainFragment)
            .commit();
        } else {
                // Or set the fragment from restored state info
                mainFragment = (FbMainFragment) getSupportFragmentManager()
                .findFragmentById(android.R.id.content);
        }
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    protected void onResume() {
    // TODO Auto-generated method stub
    super.onResume();
//    Commons.getMyTracker(getApplicationContext(), "socialMediaFb MainActivity");
    }
}