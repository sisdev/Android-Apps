package com.assusoft.efair.epchfair.Fragments;

import java.io.File;
import java.io.FileOutputStream;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.epch.efair.delhifair.HomeActivity;
import com.epch.efair.delhifair.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.jwetherell.quick_response_code.data.Contents;
import com.jwetherell.quick_response_code.qrcode.QRCodeEncoder;

public class QRCodeEncoderFragment extends Fragment{
	private static final String TAG = com.assusoft.efair.qrcode.EncoderActivity.class.getSimpleName();
    String content;
    Button btnSave;
    Bitmap bitmap;
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v=inflater.inflate(R.layout.encoder,container,false);
		
		 // This assumes the view is full screen, which is a good assumption
       // WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = HomeActivity.manager.getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();
        int smallerDimension = width < height ? width : height;
        smallerDimension = smallerDimension * 7 / 8;
        //get the content for encoding
        /*Intent intent=getActivity().getIntent();
        content=intent.getStringExtra("CONTENTS");*/
        Bundle bundle = this.getArguments();
        content = bundle.getString("CONTENTS");
        //get the id of button
        btnSave=(Button) v.findViewById(R.id.saveQrcode);

        try {
            QRCodeEncoder qrCodeEncoder = null;
           
            qrCodeEncoder = new QRCodeEncoder(content, null, Contents.Type.TEXT, BarcodeFormat.QR_CODE.toString(), smallerDimension);
            

             bitmap = qrCodeEncoder.encodeAsBitmap();
            ImageView view = (ImageView) v.findViewById(R.id.image_view);
            view.setImageBitmap(bitmap);
            
            TextView contents = (TextView) v.findViewById(R.id.contents_text_view);
            
           // contents.setText(qrCodeEncoder.getDisplayContents());
            StringBuffer data=new StringBuffer(qrCodeEncoder.getDisplayContents());
           //remove the identification mark of the app encoded data
            data.replace(0,10,"");
            Log.i("QRCODE",data.toString());
            contents.setText(data);
           // getActivity().setTitle(getString(R.string.app_name) + " - " + qrCodeEncoder.getTitle());
        } catch (WriterException e) {
            Log.e(TAG, "Could not encode barcode", e);
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "Could not encode barcode", e);
        }
        
        
        btnSave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(bitmap!=null){
					saveQRCodeToSDCard();
					FragmentTransaction ft = ((FragmentActivity)getActivity()).getSupportFragmentManager().beginTransaction();
			        
			        QRCodeFragement fragment = new  QRCodeFragement();
			        
			        ft.replace(((ViewGroup)getView().getParent()).getId(), fragment,"QRFORM");
			        //ft.addToBackStack(null); 
			        ft.commit();
				}
				
			}
		});
		
		return v;
	}
	
	 private void saveQRCodeToSDCard(){
    	 try {
             File sd = Environment.getExternalStorageDirectory();
            // File data = Environment.getDataDirectory();
             String folder="QRCode";
             File dir=new File(Environment.getExternalStorageDirectory()+"/"+folder);
             dir.mkdirs();
            /* Random generator = new Random();
             int n = 10000;
             n = generator.nextInt(n);*/
             String fname ="QRCode_Assurance_Software"+".jpg";
             File file = new File (dir, fname);
             if(file.exists()){
             	file.delete();
             }
            
             if (sd.canWrite()) {
            	 FileOutputStream out = new FileOutputStream(file);
            	 bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                 out.flush();
                 out.close();
                 Toast.makeText(getActivity(), "File is saved Successfully.", Toast.LENGTH_LONG).show();
             }
         } catch (Exception e) {

             Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();


         }
    }
	 @Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
//		Commons.getMyTracker(getActivity(), "QRCodeEncoderFragment");
	}
	 @Override
		public void onDestroy() {
			// TODO Auto-generated method stub
			super.onDestroy();
		}
		
		@Override
		public void onDestroyView() {
			// TODO Auto-generated method stub
			super.onDestroyView();
		}
}
