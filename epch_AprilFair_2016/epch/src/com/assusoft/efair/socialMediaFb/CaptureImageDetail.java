package com.assusoft.efair.socialMediaFb;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.epch.efair.delhifair.R;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;

public class CaptureImageDetail extends Activity{
  
    Button save,share;
    EditText edtCaption;
    ImageView imageViewaPhoto;
    Intent intent;
	@Override
        protected void onCreate(Bundle savedInstanceState) {
        	
        	super.onCreate(savedInstanceState);
        	
        	setContentView(R.layout.share_image_with_social_media);
        	save=(Button) findViewById(R.id.shareImgSave);
        	share=(Button) findViewById(R.id.shareImgWithFb);
        	edtCaption=(EditText) findViewById(R.id.shareCaption);
        	imageViewaPhoto=(ImageView) findViewById(R.id.sharePhoto);
           /* byte[] data  = getIntent().getByteArrayExtra("PHOTO");
        	Bitmap image=BitmapFactory.decodeByteArray(data,0,data.length);*/
        	Bitmap image=getIntent().getExtras().getParcelable("PHOTO");
        	imageViewaPhoto.setImageBitmap(image);
        	share.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// start Facebook Login
			        Session.openActiveSession(CaptureImageDetail.this, true, new Session.StatusCallback() {

			          // callback when session changes state
			          @Override
			          public void call(Session session, SessionState state, Exception exception) {
			            if (session.isOpened()) {

			              // make request to the /me API
			              Request.newMeRequest(session, new Request.GraphUserCallback() {

			                // callback after Graph API response with user object
			                @Override
			                public void onCompleted(GraphUser user, Response response) {
			                  if (user != null) {
			                   Log.i("FACEBOOK",""+user.getName());
			                	  /* TextView welcome = (TextView) findViewById(R.id.welcome);
			                    welcome.setText("Hello " + user.getName() + "!");*/
			                  }
			                }
			              }).executeAsync();
			            }
			          }
			        });
					
				}
			});
        }
	
	
	@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
      super.onActivityResult(requestCode, resultCode, data);
      Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
    }
}
