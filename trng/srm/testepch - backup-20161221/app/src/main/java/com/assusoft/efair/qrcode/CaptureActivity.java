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

import java.text.DateFormat;
import java.util.Date;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Intents;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.epch.efair.delhifair.R;
import com.assusoft.efair.epchfair.Fragments.QRCodeFragement;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.jwetherell.quick_response_code.result.ResultHandler;
import com.jwetherell.quick_response_code.result.ResultHandlerFactory;

/**
 * Example Capture Activity.
 * 
 * @author Justin Wetherell (phishman3579@gmail.com)
 */
public class CaptureActivity extends DecoderActivity {

    private static final String TAG = CaptureActivity.class.getSimpleName();
    private static final Set<ResultMetadataType> DISPLAYABLE_METADATA_TYPES = EnumSet.of(ResultMetadataType.ISSUE_NUMBER, ResultMetadataType.SUGGESTED_PRICE,
            ResultMetadataType.ERROR_CORRECTION_LEVEL, ResultMetadataType.POSSIBLE_COUNTRY);

    private TextView statusView = null;
    
    
    //resultView1 is displayed if qrcode is encoded by other application otherwise resultview
    private View resultView = null,resultView1=null;
    private boolean inScanMode = false;
    TextView contentsTextViewLink;
    Button btnWebSearch,btnShare,btnAdd;
    String contents;
    boolean flag=false;
    private final static String IDENTIFICATION_STRING="+assusoft+";
    TextView contentsTextView;
    TextView type;//check the type of qrcode
    String qrCodeType;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.capture);
        Log.v(TAG, "onCreate()");

        resultView = findViewById(R.id.result_view);
        resultView1=findViewById(R.id.result_view1);
        statusView = (TextView) findViewById(R.id.status_view);
       // contentsTextViewLink=(TextView) findViewById(R.id.contents_textview_link);
        //my modification
       /* btnWebSearch=(Button) findViewById(R.id.btnWebSearch);
        btnShare=(Button) findViewById(R.id.btnShare);
        btnAdd=(Button) findViewById(R.id.btnAdd);*/

        inScanMode = false;
        
       /* contentsTextViewLink.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try{
					String url=contentsTextViewLink.getText().toString().trim();
					Log.i("QRCODE", url);
					Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
					  startActivity(myIntent);
				}catch(Exception e){
					e.printStackTrace();
				}
				
			}
		});
        
        btnShare.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent sendIntent = new Intent();
				sendIntent.setAction(Intent.ACTION_SEND);
				sendIntent.putExtra(Intent.EXTRA_TEXT, contents);
				sendIntent.setType("text/plain");
				startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
				
			}
		});
        btnWebSearch.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent webSearch=new Intent();
				webSearch.setAction(Intent.ACTION_WEB_SEARCH);
				webSearch.putExtra(Intent.EXTRA_TEXT, contents);
				webSearch.setType("text/plain");
				startActivity(Intent.createChooser(webSearch, getResources().getText(R.string.search_with)));
				
			}
		});*/
       /* btnAdd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_INSERT_OR_EDIT);
                i.setType(ContactsContract.Contacts.CONTENT_ITEM_TYPE);
                i.putExtra(Insert.NAME,"Name");
                i.putExtra(Insert.PHONE,"Number");
                startActivity(i);
				
			}
		});*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "onDestroy()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v(TAG, "onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(TAG, "onPause()");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (inScanMode)
                finish();
            else
            {// onResume();
            	//Context fm =(v.getContext());
            	try{
				FragmentTransaction ft = ((FragmentActivity)getApplicationContext()).getSupportFragmentManager().beginTransaction();
		        
		        QRCodeFragement fragment = new QRCodeFragement();
		        ft.replace(R.id.content_frame, fragment,"");
		       
		        ft.commit();
            	}catch(Exception e){
            		e.printStackTrace();
            		finish();
            	}
		        
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void handleDecode(Result rawResult, Bitmap barcode) {
        drawResultPoints(barcode, rawResult);

        ResultHandler resultHandler = ResultHandlerFactory.makeResultHandler(this, rawResult);
        handleDecodeInternally(rawResult, resultHandler, barcode);
    }

    protected void showScanner() {
        inScanMode = true;
        resultView.setVisibility(View.GONE);
        resultView1.setVisibility(View.GONE);
        statusView.setText(R.string.msg_default_status);
        statusView.setVisibility(View.VISIBLE);
        viewfinderView.setVisibility(View.VISIBLE);
    }

    protected void showResults() {
        inScanMode = false;
        statusView.setVisibility(View.GONE);
        viewfinderView.setVisibility(View.GONE);
       
        Log.i("QRCODE","my app "+contents.contains(IDENTIFICATION_STRING));
        if(!contents.contains(IDENTIFICATION_STRING)){
        	
        	resultView1.setVisibility(View.VISIBLE);
        	btnWebSearch=(Button) findViewById(R.id.btnWebSearch1);
            btnShare=(Button) findViewById(R.id.btnShare1);
            contentsTextViewLink=(TextView) findViewById(R.id.contents_textview_link1);
            type=(TextView) findViewById(R.id.type_text_view1);
        	
        }else{
        	btnWebSearch=(Button) findViewById(R.id.btnWebSearch);
            btnShare=(Button) findViewById(R.id.btnShare);
            btnAdd=(Button) findViewById(R.id.btnAdd);
            contentsTextViewLink=(TextView) findViewById(R.id.contents_textview_link);
            type=(TextView) findViewById(R.id.type_text_view);
            StringBuffer str=new StringBuffer(contents);
        	str.replace(0,10,"");
        	contents=str.toString();
        	resultView.setVisibility(View.VISIBLE);
        	flag=true;
            btnAdd.setOnClickListener(new OnClickListener() {
    			
    			@Override
    			public void onClick(View v) {
    				/*Intent i = new Intent(Intent.ACTION_INSERT_OR_EDIT);
                    i.setType(ContactsContract.Contacts.CONTENT_ITEM_TYPE);
                    i.putExtra(Insert.NAME,"Name");
                    i.putExtra(Insert.PHONE,"Number");
                    startActivity(i);*/
    				String data[]=contents.split("\n");
    				
    				Intent intent=new Intent(Intents.Insert.ACTION);
                 // Sets the MIME type to match the Contacts Provider
                    intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                 // Inserts an email address
                   
                    if(data.length > 0 )
                    {
                    	intent.putExtra(Intents.Insert.NAME,data[0]);
                    }
                    if(data.length > 1 )
                    {
                    	intent.putExtra(Intents.Insert.JOB_TITLE,data[1]);
                    }
                    if(data.length > 2 )
                    {
                    	intent.putExtra(Intents.Insert.COMPANY,data[2]);
                    }
                    if(data.length > 3 )
                    {
                    	intent.putExtra(Intents.Insert.POSTAL,data[3]);
                    }
                    if(data.length > 4 )
                    {
                    	intent.putExtra(Intents.Insert.PHONE,data[4]);
                    }
                    if(data.length > 5 )
                    {
                    	Log.v(TAG, "Intents.Insert.SECONDARY_PHONE.length() " + Intents.Insert.SECONDARY_PHONE.length());
                    	intent.putExtra(Intents.Insert.SECONDARY_PHONE,data[5]);
                    }
                    if(data.length > 6 )
                    {
                    	intent.putExtra(Intents.Insert.EMAIL, data[6]);
                    }
                    //intent.putExtra(Intents.Insert, data[7]);
                    startActivity(intent);

    				
    			}
    		});
        	
        }
        
        
    contentsTextViewLink.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try{
					String url=contentsTextViewLink.getText().toString().trim();
					Log.i("QRCODE", url);
					Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
					  startActivity(myIntent);
				}catch(Exception e){
					e.printStackTrace();
				}
				
			}
		});
        
        btnShare.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent sendIntent = new Intent();
				sendIntent.setAction(Intent.ACTION_SEND);
				sendIntent.putExtra(Intent.EXTRA_TEXT, contents);
				sendIntent.setType("text/plain");
				startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
				
			}
		});
        btnWebSearch.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
				try{
					if(qrCodeType.equalsIgnoreCase("URI")){
						Intent in=new Intent(Intent.ACTION_VIEW,Uri.parse(contents));
			            startActivity(in);
					}
					else{
						Intent webSearch=new Intent();
						webSearch.setAction(Intent.ACTION_WEB_SEARCH);
						webSearch.putExtra(SearchManager.QUERY, contents);
						//webSearch.setType("text/plain");
						startActivity(webSearch);
					}
					
				}catch(Exception e){
					
				}
				
				
			}
		});
        
    }

    // Put up our own UI for how to handle the decodBarcodeFormated contents.
    private void handleDecodeInternally(Result rawResult, ResultHandler resultHandler, Bitmap barcode) {
        onPause();
        CharSequence displayContents = resultHandler.getDisplayContents();
        Log.i("QRCODE", displayContents.toString());
        contents=displayContents.toString();
        qrCodeType=resultHandler.getType().toString();
        showResults();
        if(flag==false){

			        ImageView barcodeImageView = (ImageView) findViewById(R.id.barcode_image_view1);
			        if (barcode == null) {
			            barcodeImageView.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.icon));
			        } else {
			            barcodeImageView.setImageBitmap(barcode);
			        }
			
			        TextView formatTextView = (TextView) findViewById(R.id.format_text_view1);
			        formatTextView.setText(rawResult.getBarcodeFormat().toString());
			
			        TextView typeTextView = (TextView) findViewById(R.id.type_text_view1);
			        typeTextView.setText(resultHandler.getType().toString());
			        
			        DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
			        String formattedTime = formatter.format(new Date(rawResult.getTimestamp()));
			        TextView timeTextView = (TextView) findViewById(R.id.time_text_view1);
			        timeTextView.setText(formattedTime);
			
			        TextView metaTextView = (TextView) findViewById(R.id.meta_text_view1);
			        View metaTextViewLabel = findViewById(R.id.meta_text_view_label1);
			        metaTextView.setVisibility(View.GONE);
			        metaTextViewLabel.setVisibility(View.GONE);
			        Map<ResultMetadataType, Object> metadata = rawResult.getResultMetadata();
			        if (metadata != null) {
			            StringBuilder metadataText = new StringBuilder(20);
			            for (Map.Entry<ResultMetadataType, Object> entry : metadata.entrySet()) {
			                if (DISPLAYABLE_METADATA_TYPES.contains(entry.getKey())) {
			                    metadataText.append(entry.getValue()).append('\n');
			                }
			            }
			            if (metadataText.length() > 0) {
			                metadataText.setLength(metadataText.length() - 1);
			                metaTextView.setText(metadataText);
			                metaTextView.setVisibility(View.VISIBLE);
			                metaTextViewLabel.setVisibility(View.VISIBLE);
			            }
			        }
			
			      contentsTextView = (TextView) findViewById(R.id.contents_text_view1);
        }else{
        	ImageView barcodeImageView = (ImageView) findViewById(R.id.barcode_image_view);
	        if (barcode == null) {
	            barcodeImageView.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.icon));
	        } else {
	            barcodeImageView.setImageBitmap(barcode);
	        }
	
	        TextView formatTextView = (TextView) findViewById(R.id.format_text_view);
	        formatTextView.setText(rawResult.getBarcodeFormat().toString());
	
	        TextView typeTextView = (TextView) findViewById(R.id.type_text_view);
	        typeTextView.setText(resultHandler.getType().toString());
	
	        DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
	        String formattedTime = formatter.format(new Date(rawResult.getTimestamp()));
	        TextView timeTextView = (TextView) findViewById(R.id.time_text_view);
	        timeTextView.setText(formattedTime);
	
	        TextView metaTextView = (TextView) findViewById(R.id.meta_text_view);
	        View metaTextViewLabel = findViewById(R.id.meta_text_view_label);
	        metaTextView.setVisibility(View.GONE);
	        metaTextViewLabel.setVisibility(View.GONE);
	        Map<ResultMetadataType, Object> metadata = rawResult.getResultMetadata();
	        if (metadata != null) {
	            StringBuilder metadataText = new StringBuilder(20);
	            for (Map.Entry<ResultMetadataType, Object> entry : metadata.entrySet()) {
	                if (DISPLAYABLE_METADATA_TYPES.contains(entry.getKey())) {
	                    metadataText.append(entry.getValue()).append('\n');
	                }
	            }
	            if (metadataText.length() > 0) {
	                metadataText.setLength(metadataText.length() - 1);
	                metaTextView.setText(metadataText);
	                metaTextView.setVisibility(View.VISIBLE);
	                metaTextViewLabel.setVisibility(View.VISIBLE);
	            }
	        }
	
	        contentsTextView = (TextView) findViewById(R.id.contents_text_view);
        }
       /* CharSequence displayContents = resultHandler.getDisplayContents();
        Log.i("QRCODE", displayContents.toString());*/
        //parse the contents link
        // contents=displayContents.toString();
        String[] contnt=contents.split("\n");
        StringBuffer withoutLink=new StringBuffer();
        for(int i=0;i<contnt.length;i++){
        	Log.i("QRCODE",contnt[i]+"  "+contnt[i].contains("http")+"  www  "+contnt[i].contains("www"));
        	if(contnt[i].contains("http")||contnt[i].contains("www")){
        		Log.i("QRCODE","if");
        		 contentsTextViewLink.setEnabled(true);
   	    	     contentsTextViewLink.setText(contnt[i]);
   	    	     contentsTextViewLink.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
   	    	     continue;
        	}
        	withoutLink.append(contnt[i]+"\n");
        }
        
        contentsTextView.setText(withoutLink);
        // Crudely scale betweeen 22 and 32 -- bigger font for shorter text
        int scaledSize = Math.max(22, 32 - displayContents.length() / 4);
        contentsTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, scaledSize);
    }
    
    
 }

