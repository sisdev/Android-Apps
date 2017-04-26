package com.assusoft.efair.socialMediaFb;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.epch.efair.delhifair.R;
import com.assusoft.efair.socialMediaFb.MainActivity;



public class MainActivityFor extends Activity {

	TextView welcome;
	Button login;
	final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	 
     
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login=(Button) findViewById(R.id.button1);
        login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				/**///call custom camera app
				Intent intent=new Intent(MainActivityFor.this,CameraActivity.class);
				startActivity(intent);
				// create Intent to take a picture and return control to the calling application
                /*Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);              
                          
                
                // start the image capture Intent
                startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);*/
			}
		});
        
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) 
    {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) 
        {
            Bitmap imageData = null;
            if (resultCode == RESULT_OK) 
            {
                imageData = (Bitmap) data.getExtras().get("data");

                Intent i = new Intent(this, MainActivity.class);
                i.putExtra("PHOTO", imageData );
                startActivity(i);

            } 
            else if (resultCode == RESULT_CANCELED) 
            {
                // User cancelled the image capture
            } 
            else 
            {
                // Image capture failed, advise user
            }
        }
    }    


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
   /* */
    
}
