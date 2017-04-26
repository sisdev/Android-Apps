package com.assusoft.efair.qrcode;

import java.io.File;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.assusoft.eFairEmall.databaseMaster.DatabaseHelper;
import com.assusoft.eFairEmall.entities.ExhibitorLocation;
import com.assusoft.eFairEmall.entities.PhotoShoot;
import com.assusoft.eFairEmall.entities.VenueMap;
import com.epch.efair.delhifair.R;
import com.assusoft.efair.epchfair.Fragments.QRCodeFragement;
import com.epch.efair.delhifair.EFairEmallApplicationContext;
import com.epch.efair.delhifair.HomeActivity;
import com.epch.efair.delhifair.ImageAsyncTask;
import com.epch.efair.delhifair.StorageHelper;
import com.epch.efair.delhifair.HomeAcitityFirst.Fragments;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.jwetherell.quick_response_code.result.ResultHandler;
import com.jwetherell.quick_response_code.result.ResultHandlerFactory;

public class CaptureActivity1 extends DecoderActivity {

    private static final String TAG = CaptureActivity.class.getSimpleName();
    private static final Set<ResultMetadataType> DISPLAYABLE_METADATA_TYPES = EnumSet.of(ResultMetadataType.ISSUE_NUMBER, ResultMetadataType.SUGGESTED_PRICE,
            ResultMetadataType.ERROR_CORRECTION_LEVEL, ResultMetadataType.POSSIBLE_COUNTRY);

    private TextView statusView = null;
    
    
    //resultView1 is displayed if qrcode is encoded by other application otherwise resultview
    private View resultView = null,resultView1=null,resultView2=null;
    private boolean inScanMode = false;
   
    Button btnWebSearch,btnExbDetails,btnLocate,btnSelect,btnAlbum;
    String contents;
    boolean flag=false;
    private final static String IDENTIFICATION_STRING="(.*)exb=(.*.)memberid=(.*.)stallno=(.*.)locid=(.*)hallno=(.*)";
    TextView contentsTextView;
    TextView type;//check the type of qrcode
    String qrCodeType;
    Intent intent;
    DatabaseHelper  dbHelper;
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.capture1);
         dbHelper=EFairEmallApplicationContext.getDatabaseHelper();
		//get the readable database
	    dbHelper.openDatabase(DatabaseHelper.WRITEMODE);
        Log.v(TAG, "onCreate()");

        resultView = findViewById(R.id.result_view);
        resultView1=findViewById(R.id.result_view1);
        resultView2=findViewById(R.id.result_view2);
        statusView = (TextView) findViewById(R.id.status_view);
      

        inScanMode = false;
        intent=getIntent();
      
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
        resultView2.setVisibility(View.GONE);
        statusView.setText(R.string.msg_default_status);
        statusView.setVisibility(View.VISIBLE);
        viewfinderView.setVisibility(View.VISIBLE);
    }

    protected void showResults() {
        inScanMode = false;
        statusView.setVisibility(View.GONE);
        viewfinderView.setVisibility(View.GONE);
        final Uri uri=Uri.parse(contents);
        
        Log.i("QRCODE","my app "+contents.matches(IDENTIFICATION_STRING));
        Log.i("QRCODE","my app "+IDENTIFICATION_STRING);
        Log.i("QRCODE","my app "+contents);
        
//*********************************************************************************************/
        boolean flag=intent.getBooleanExtra("SCANFLAG",true);
        if(!(contents.matches(IDENTIFICATION_STRING))){
        	
        	if(!flag){
        		Toast.makeText(this, "QR code is not valid.", Toast.LENGTH_LONG).show();
        		finish();
        		return;
        	}
        	resultView.setVisibility(View.VISIBLE);
        	btnWebSearch=(Button) findViewById(R.id.btnWebSearch);
        	contentsTextView=(TextView) findViewById(R.id.tvResultView);
        	contentsTextView.setText(contents);
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
        }else{
        	
        	String contentData="";
        	try{
        		contentData+="Fair\t\t\t\t\t\t\t\t\t\t: "+uri.getQueryParameter("fair").toUpperCase()+"\n"+
                        "Exhibitor Name\t: "+uri.getQueryParameter("exb").toUpperCase()+
                        "\n"+"Stall No\t\t\t\t\t\t\t: "+uri.getQueryParameter("stallno");
        	}catch(Exception e){
        		e.printStackTrace();
        		Toast.makeText(this, "Information is not available for this QR Code.", Toast.LENGTH_SHORT).show();
        		finishFromChild(getParent());
        	}
        	
        	final String exhibitorId=uri.getQueryParameter("memberid").trim();
        	
        	final String exhibitorName=uri.getQueryParameter("exb");
        	final String stallNo=uri.getQueryParameter("stallno").toUpperCase();
        	Log.i("EXPO","Stall no  "+stallNo.toUpperCase());
        	if(flag){
        		//true will be replaced with flag that define scanning QR for Source and destination selection or for exhibitor details
        		resultView1.setVisibility(View.VISIBLE);
        		contentsTextView=(TextView) findViewById(R.id.tvResultView1);
        		btnExbDetails=(Button) findViewById(R.id.btncapturExhibitorInfo);
        		btnLocate=(Button) findViewById(R.id.btnCaptureLocate);
        		btnAlbum=(Button) findViewById(R.id.btnCaptureAlbum);
        		btnExbDetails.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
					 try{	
						/* HomeActivity.goForExhibitorDatails=1;
						FragmentTransaction ft = HomeActivity.getSupportFragmentManager1();
				        
				        ExhibitorDetailFragment fragment = new ExhibitorDetailFragment();
				        ft.replace(R.id.content_frame, fragment,"");
				        fragment.setArguments(data);
				       
				        ft.commitAllowingStateLoss();
				        finish();*/
						 Bundle bundle=new Bundle();
					     bundle.putInt("EXHIBITORID",Integer.parseInt(exhibitorId));
					     bundle.putString("STALLNO", stallNo);
						 Fragments a=Fragments.EXHIBITORDETAILS;
							Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
							intent.putExtra("FLAG",a.toString());
							intent.putExtra("DATA",bundle);
							startActivity(intent);
							finish();
				      
					 }catch(Exception e){
						 e.printStackTrace();
						 finish();
					 }
					}
				});
        		btnLocate.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
					  try{
						  HomeActivity.venueMapWithLocationOnBack=1;
						
						List<Object> users=dbHelper.getDetailOfSelectedExibitor(exhibitorId,stallNo);
						VenueMap vmap=(VenueMap) users.get(1);
						ExhibitorLocation location=(ExhibitorLocation) users.get(0);
						String xLocation=location.getxLocation();
						String yLocation=location.getyLocation();
						String fileName=vmap.getFilePath();
						
						
				        Bundle bundle=new Bundle();
				        bundle.putString("XLOCATION",xLocation);
				        bundle.putString("YLOCATION",yLocation);
				        bundle.putString("FILEPATH",fileName);
				        /*VenueMapWithLocation fragment = new VenueMapWithLocation();
				        
				        fragment.setArguments(bundle);
				        ft.replace(R.id.content_frame, fragment,"Products");
				       
				        ft.commitAllowingStateLoss();
				        finish();
				        */

						Fragments a=Fragments.VENUEMAPQR;
						Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
						intent.putExtra("FLAG",a.toString());
						intent.putExtra("DATA",bundle);
						startActivity(intent);
						finish();
					  }catch(Exception e){
						  e.printStackTrace();
						  finish();
					  }
					}
				});
        		
        		btnAlbum.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						try{
							// create directory 
							StorageHelper.createFolder(ImageAsyncTask.FOLDER_NAME+File.separator+uri.getQueryParameter("exb"));
							//insert data to photoshoot table
							PhotoShoot photo=new PhotoShoot(0,Integer.parseInt(exhibitorId),0,0,ImageAsyncTask.FOLDER_NAME+File.separator+uri.getQueryParameter("exb"),null,null,null);
							
							dbHelper.insertPhotoShoot(photo,true);
							
							
							//getActivity().getSupportFragmentManager().popBackStack();
							
							
							Bundle bundle=new Bundle();
							bundle.putInt("EXHIBITORID",Integer.parseInt(exhibitorId));
							bundle.putString("FOLDERNAME",uri.getQueryParameter("exb"));
							bundle.putBoolean("QRALBUMFLAG", true);
							
							Fragments a=Fragments.PHOTOSHOOT;
							Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
							intent.putExtra("FLAG",a.toString());
							intent.putExtra("DATA",bundle);
							startActivity(intent);
							finish();
						}catch(Exception e){
							e.printStackTrace();
							finish();
						}
						
					}
				});
        		
        		
        		
        	}else{
        		//for source and destination selection
        		resultView2.setVisibility(View.VISIBLE);
        		contentsTextView=(TextView) findViewById(R.id.tvResultView2);
        		btnSelect=(Button) findViewById(R.id.btncapturSelect);
        		btnSelect.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						/*try{	
							 
							FragmentTransaction ft = HomeActivity.getSupportFragmentManager1();
					        Bundle data=new Bundle();
					        data.putInt("EXHIBITORID",Integer.parseInt(exhibitorId));
					        data.putBoolean("TOFROM",intent.getBooleanExtra("TOFROM",false));
					        data.putString("EXHIBITORNAME",exhibitorName);
					        To_From fragment = new To_From();
					        ft.replace(R.id.content_frame, fragment,"");
					        fragment.setArguments(data);
					       
					        ft.commitAllowingStateLoss();
					        finish();
					      
						 }catch(Exception e){
							 e.printStackTrace();
							 finish();
						 }*/
						Intent intent1=new Intent();
						intent1.putExtra("TOFROM",intent.getBooleanExtra("TOFROM",false));
						intent1.putExtra("EXHIBITORID",Integer.parseInt(exhibitorId));
						intent1.putExtra("EXHIBITORNAME",exhibitorName);
						intent1.putExtra("STALLNO",stallNo);
						setResult(3,intent1);
						finish();
						
					}
				});
        	}
        	contentsTextView.setText(contentData);
        	
        }
        
        
   
        
      
       
        
    }

    // Put up our own UI for how to handle the decodBarcodeFormated contents.
    private void handleDecodeInternally(Result rawResult, ResultHandler resultHandler, Bitmap barcode) {
        onPause();
       try{
    	   CharSequence displayContents = resultHandler.getDisplayContents();
           Log.i("QRCODE", displayContents.toString());
           contents=displayContents.toString();
           qrCodeType=resultHandler.getType().toString();
           showResults();
          
          // contentsTextView.setText(contents);
           // Crudely scale betweeen 22 and 32 -- bigger font for shorter text
           int scaledSize = Math.max(22, 32 - displayContents.length() / 4);
           contentsTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, scaledSize);
       }catch(Exception e){
    	   return;
       }
    }

	@Override
	public void finishFromChild(Activity child) {
		// TODO Auto-generated method stub
		super.finishFromChild(child);
	}
    
    
    
}