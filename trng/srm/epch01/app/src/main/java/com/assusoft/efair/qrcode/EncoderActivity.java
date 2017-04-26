/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.assusoft.efair.qrcode;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.epch.efair.delhifair.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.jwetherell.quick_response_code.data.Contents;
import com.jwetherell.quick_response_code.qrcode.QRCodeEncoder;

/**
 * Example Encoder Activity.
 * 
 * @author Justin Wetherell (phishman3579@gmail.com)
 */
public final class EncoderActivity extends Activity {

    private static final String TAG = EncoderActivity.class.getSimpleName();
    String content;
    Button btnSave;
    Bitmap bitmap;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.encoder);

        // This assumes the view is full screen, which is a good assumption
        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();
        int smallerDimension = width < height ? width : height;
        smallerDimension = smallerDimension * 7 / 8;
        //get the content for encoding
        Intent intent=getIntent();
        content=intent.getStringExtra("CONTENTS");
        //get the id of button
        btnSave=(Button) findViewById(R.id.saveQrcode);

        try {
            QRCodeEncoder qrCodeEncoder = null;
            // qrCodeEncoder = new QRCodeEncoder("AT", null, Contents.Type.TEXT,
            // BarcodeFormat.CODABAR.toString(), smallerDimension);
            // qrCodeEncoder = new QRCodeEncoder("HI", null, Contents.Type.TEXT,
            // BarcodeFormat.CODE_39.toString(), smallerDimension);
            // qrCodeEncoder = new QRCodeEncoder("Hello", null,
            // Contents.Type.TEXT, BarcodeFormat.CODE_128.toString(),
            // smallerDimension);
            // qrCodeEncoder = new QRCodeEncoder("1234567891011", null,
            // Contents.Type.TEXT, BarcodeFormat.EAN_13.toString(),
            // smallerDimension);
            // qrCodeEncoder = new QRCodeEncoder("12345678", null,
            // Contents.Type.TEXT, BarcodeFormat.EAN_8.toString(),
            // smallerDimension);
            // qrCodeEncoder = new QRCodeEncoder("1234", null,
            // Contents.Type.TEXT, BarcodeFormat.ITF.toString(),
            // smallerDimension);
            // qrCodeEncoder = new QRCodeEncoder("2345", null,
            // Contents.Type.TEXT, BarcodeFormat.PDF_417.toString(),
            // smallerDimension);
            qrCodeEncoder = new QRCodeEncoder(content, null, Contents.Type.TEXT, BarcodeFormat.QR_CODE.toString(), smallerDimension);
            // qrCodeEncoder = new QRCodeEncoder("12345678910", null,
            // Contents.Type.TEXT, BarcodeFormat.UPC_A.toString(),
            // smallerDimension);

             bitmap = qrCodeEncoder.encodeAsBitmap();
            ImageView view = (ImageView) findViewById(R.id.image_view);
            view.setImageBitmap(bitmap);
            
            TextView contents = (TextView) findViewById(R.id.contents_text_view);
            
           // contents.setText(qrCodeEncoder.getDisplayContents());
            StringBuffer data=new StringBuffer(qrCodeEncoder.getDisplayContents());
           //remove the identification mark of the app encoded data
            data.replace(0,10,"");
            Log.i("QRCODE",data.toString());
            contents.setText(data);
            setTitle(getString(R.string.app_name) + " - " + qrCodeEncoder.getTitle());
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
				}
				
			}
		});
    }
    
    
    private void saveQRCodeToSDCard(){
    	 try {
             File sd = Environment.getExternalStorageDirectory();
            // File data = Environment.getDataDirectory();
             String folder="QRCode";
             File dir=new File(Environment.getExternalStorageDirectory()+"/"+folder);
             dir.mkdirs();
             Random generator = new Random();
             int n = 10000;
             n = generator.nextInt(n);
             String fname = "QRCode-"+ n +".jpg";
             File file = new File (dir, fname);
             if(file.exists()){
             	file.delete();
             }
            
             if (sd.canWrite()) {
            	 FileOutputStream out = new FileOutputStream(file);
            	 bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                 out.flush();
                 out.close();
                 Toast.makeText(this, "File is saved Successfully.", Toast.LENGTH_LONG).show();
             }
         } catch (Exception e) {

             Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG).show();


         }
    }
}
