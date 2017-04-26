package com.assusoft.efair.epchfair.Fragments;

import java.io.File;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.assusoft.efair.qrcode.CaptureActivity;

public class QRCodeFragement extends Fragment{
   
	Button btnQRNCreate,btnQRNCapture;
	ImageView imageView;
	Bitmap bitMap;
    File sd ; 
    String path,fileName="QRCode_Assurance_Software"+".jpg";
    String folder="QRCode";
    boolean flag=false;
    View v;
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
		
		
		try{
			sd=Environment.getExternalStorageDirectory();
			path=sd.toString()+"/"+folder;
			flag=isFileExist(path, fileName);
		}catch(Exception e){
			e.printStackTrace();
		}
		/*if(flag){
			v = inflater.inflate(R.layout.qrcode_exist, container, false);
			btnQRNCreate=(Button) v.findViewById(R.id.btnQRCreat);
			btnQRNCapture=(Button) v.findViewById(R.id.btnQRCapture);
			imageView=(ImageView) v.findViewById(R.id.imageViewQRexist);
			bitMap=BitmapFactory.decodeFile(path+"/"+fileName);
			imageView.setImageBitmap(bitMap);
		}else{
			v = inflater.inflate(R.layout.qrcode_not_exist, container, false);
			btnQRNCreate=(Button) v.findViewById(R.id.btnQRNCreat);
			btnQRNCapture=(Button) v.findViewById(R.id.btnQRNCapture);
		}
		*/
		btnQRNCreate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				/*final FragmentActivity activity = getActivity();
				activity.getSupportFragmentManager().popBackStackImmediate();*/
				FragmentTransaction ft = ((FragmentActivity)getActivity()).getSupportFragmentManager().beginTransaction();
		        
		        QRCodeFormFragment fragment = new  QRCodeFormFragment();
		        
		        ft.replace(((ViewGroup)getView().getParent()).getId(), fragment,"QRFORM");
		        //ft.addToBackStack(null); 
		        ft.commit();
			}
		});
		
		btnQRNCapture.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(getActivity(),CaptureActivity.class);
				startActivity(intent);
			}
		});
    	 
    	return v;
    }
	
	private boolean isFileExist(String path,String fileName){
		
          File dir=new File(path);
          if(dir.exists()){
        	  
              File file = new File (dir, fileName);
              if(file.exists()){
            	  return true;
              }else {
            	  return false;
              }
          }
          
		return false;
		
	}
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
//		Commons.getMyTracker(getActivity(), "QRCodeFragment");
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
