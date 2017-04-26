package com.assusoft.efair.epchfair.Fragments;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.assusoft.eFairEmall.databaseMaster.DatabaseHelper;
import com.assusoft.eFairEmall.entities.Exhibitors;
import com.assusoft.efair.qrcode.CaptureActivity1;
import com.epch.efair.delhifair.EFairEmallApplicationContext;
import com.epch.efair.delhifair.HomeActivity;
import com.epch.efair.delhifair.R;

public class To_From_Custom extends Fragment{
	TextView source,destination;
	EditText filter;
	ListView exbListview;
	ArrayList<Exhibitors> exhibitors;
	ArrayList<String> exhibitorName;
	private List<Exhibitors> userlist = null;
	ArrayAdapter<String> adapter;
	int counter=0;
	boolean isDestOrSourceSelected=false;
	boolean isToast=false;
	ImageView imgcancel;
	Button done;
	ImageButton scanSource,scanDest;
	int selectedExbIdTo,selectedExbIdFrom;
	String selectedStallNoFrom="",selectedStallNoTo="";
	
	LinearLayout llTo, llFrom;
	DatabaseHelper dh;
	ToFromListAdapter listAdapter;
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
	View v=inflater.inflate(R.layout.to_from_list,container,false);
	
	exbListview=(ListView) v.findViewById(R.id.lvToFrom);
	filter=(EditText) v.findViewById(R.id.tvtofromFilter);
	source=(TextView) v.findViewById(R.id.tvFrom);
	destination=(TextView) v.findViewById(R.id.tvdest);
	imgcancel=(ImageView) v.findViewById(R.id.imgcancel);
	scanSource=(ImageButton) v.findViewById(R.id.btnToFromDone2);
	scanDest=(ImageButton) v.findViewById(R.id.btnToFromDone1);
	done=(Button) v.findViewById(R.id.btnToFromDone);
	
	llTo	= (LinearLayout) v.findViewById(R.id.llto);
	llFrom	= (LinearLayout) v.findViewById(R.id.llbottom);
	
	selectedExbIdFrom=0;
	selectedExbIdTo=0;
	
	scanDest.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			HomeActivity.hideSoftKeyboard(getActivity());
			//invoke capture1 activity and set intent param ScanFlag=false and TOFROM=false
			Intent intent=new Intent(getActivity(), CaptureActivity1.class);
			intent.putExtra("SCANFLAG",false);
			intent.putExtra("TOFROM",false);
			startActivityForResult(intent,1);
		}
	});
	scanSource.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			HomeActivity.hideSoftKeyboard(getActivity());
			//invoke capture1 activity and set intent param ScanFlag=false and TOFROM=true
			Intent intent=new Intent(getActivity(), CaptureActivity1.class);
			intent.putExtra("SCANFLAG",false);
			intent.putExtra("TOFROM",true);
			startActivityForResult(intent,2);
		}
	});
	imgcancel.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			filter.setText("");
			imgcancel.setVisibility(View.GONE);
		}
	});
	source.setOnClickListener(new OnClickListener() {
		
		@SuppressWarnings("deprecation")
		@Override
		public void onClick(View v) {
			isDestOrSourceSelected=false;
		llFrom.setBackgroundDrawable(getResources().getDrawable(R.drawable.black_border_shape));
		llTo.setBackgroundDrawable(getResources().getDrawable(R.drawable.white_border_shape));
			HomeActivity.setTitle("From Location");
			listAdapter.setSelection(isDestOrSourceSelected);
		}
		
	});
	
	destination.setOnClickListener(new OnClickListener() {
		
		@SuppressWarnings("deprecation")
		@Override
		public void onClick(View v) {
			isDestOrSourceSelected=true;
			llTo.setBackgroundDrawable(getResources().getDrawable(R.drawable.black_border_shape));
			llFrom.setBackgroundDrawable(getResources().getDrawable(R.drawable.white_border_shape));
			HomeActivity.setTitle("TO Location");
			listAdapter.setSelection(isDestOrSourceSelected);
		}
	});
	
	done.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			HomeActivity.hideSoftKeyboard(getActivity());
			if(selectedExbIdFrom==0 || selectedExbIdTo==0){
				Toast.makeText(getActivity(), "Please select source and destination.",Toast.LENGTH_SHORT).show();
			    
			    return;
			}
			if(selectedExbIdFrom==selectedExbIdTo && selectedStallNoFrom.equalsIgnoreCase(selectedStallNoTo)){
			   
			   Toast.makeText(getActivity(), "To and From are same, please select other location.",Toast.LENGTH_SHORT).show();
			   
				return ;
			}
			if(imgcancel.getVisibility()==View.VISIBLE){
            	imgcancel.setVisibility(View.GONE);
            }
			 
			Bundle data=new Bundle();
			
			data.putString("EXTRA_IMAGE","hall1-6.png");//"floor2_1024.tmx"
			data.putInt("FLAG",1);
			data.putInt("EXHIBITORIDTO",selectedExbIdTo);
			data.putInt("EXHIBITORIDFROM",selectedExbIdFrom);
			data.putString("EXHIBITORSTALLNOFROM",selectedStallNoFrom);
			data.putString("EXHIBITORSTALLNOTO",selectedStallNoTo);
            FragmentTransaction ft = ((FragmentActivity)getActivity()).getSupportFragmentManager().beginTransaction();
            Log.i("TO_FROM", "DATA"+data);
	        VenueMapWithRoute fragment = new  VenueMapWithRoute();
	        fragment.setArguments(data);
	        ft.replace(R.id.content_frame, fragment);
	        ft.addToBackStack(null);
	        ft.commit();
	        
			
		}
	});
	exbListview.setOnTouchListener(new OnTouchListener() {
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			HomeActivity.hideSoftKeyboard(getActivity());
			return false;
		}
	});
	if(imgcancel.getVisibility()==View.VISIBLE){
    	imgcancel.setVisibility(View.GONE);
    }
	dh=EFairEmallApplicationContext.getDatabaseHelper();
	dh.openDatabase(DatabaseHelper.READMODE);
	userlist=dh.getAllExhibitorWiseSearchResult("");
	//Toast.makeText(getActivity(), "size-"+exhibitors.size(),Toast.LENGTH_LONG).show();
	listAdapter=new ToFromListAdapter(getActivity(), userlist, this,source, destination);
	exbListview.setAdapter(listAdapter);
	
	
	
	filter.addTextChangedListener(new TextWatcher() {
		 
        @Override
        public void afterTextChanged(Editable arg0) {
            if(imgcancel.getVisibility()==View.GONE){
            	imgcancel.setVisibility(View.VISIBLE);
            }
            String text = filter.getText().toString().toLowerCase(Locale.getDefault());
            listAdapter.filter(text);
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1,
                int arg2, int arg3) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                int arg3) {
            // TODO Auto-generated method stub
        }			
    });
	try{
	   Bundle data=getArguments();
	   source.setText(" From : "+exhibitorName.get(data.getInt("EXHIBITORID",0)-1));
	   isDestOrSourceSelected=true;
	   counter=1;
	   selectedExbIdFrom=data.getInt("EXHIBITORID",0);
	}catch(Exception e){
		e.printStackTrace();
	}
	//resizing layout when soft input keyboard open.
	//getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
	
	return v;
}	//END of onCreateView method

 
 

 
 
 
 @Override
public void onActivityResult(int requestCode, int resultCode, Intent data) {
	// TODO Auto-generated method stub
	super.onActivityResult(requestCode, resultCode, data);
	try{
		
		if(!dh.isExhibitorLocationExist(""+data.getIntExtra("EXHIBITORID",0),data.getStringExtra("STALLNO"))){
			Toast.makeText(getActivity(), "Unable to find location,please select another exhibitor.",Toast.LENGTH_SHORT).show();
		    return;
		}
		if(requestCode==1){
			destination.setText(" To : "+getExhibitorNameById(data.getIntExtra("EXHIBITORID",0),true));
			selectedExbIdTo=data.getIntExtra("EXHIBITORID",0);
			selectedStallNoTo=data.getStringExtra("STALLNO");
			
		 }else{
			 source.setText(" From : "+getExhibitorNameById(data.getIntExtra("EXHIBITORID",0),false));
		     selectedExbIdFrom=data.getIntExtra("EXHIBITORID",0);
		     selectedStallNoFrom=data.getStringExtra("STALLNO");
		 }
	}catch(Exception e){
		e.printStackTrace();
	}
}
 
private String getExhibitorNameById(int id,boolean flag){
	for (Exhibitors user : userlist)
    {
        if (user.getExhibitorId()==id)
        {
        	String[] parts = user.getExhibitorName().split("~~");
            String part1 = parts[0]; // Name
            String part2 = parts[1]; // location
            String exbLoc[];
        	if(part1.contains("Hall")|| part1.contains("Mart")){
        		exbLoc=part1.split("\\,");
        	}else{
        		exbLoc=part2.split("\\,");
        	}
        	String stallNo="";
        	if(exbLoc.length>1){
        		String stall[]=exbLoc[1].split("\\.");
        		if(stall.length>1){
        			stallNo=stall[1].trim();
        		}
        	}
            if(flag){
            	selectedStallNoTo=stallNo;
            }else{
            	selectedStallNoFrom=stallNo;
            }
            Log.i("FILTER",user.getExhibitorName());
            return part1;
        }
    }
	return "";
}
 
	 @SuppressLint("InlinedApi")
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		//affect remove of resizing layout for next layout.
		//getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		HomeActivity.setTitle("Locate Exhibitors");
	}
	 
	 public void setSelectedExhibitor(int exbId,String stallNo,boolean flag){
		 if(flag){
			 selectedExbIdFrom=exbId;
		     selectedStallNoFrom=stallNo;
		 }else{
			 selectedExbIdTo=exbId;
			 selectedStallNoTo=stallNo;
		 }
	 }
	 
	 public void hideCacelIcon(){
		 if(imgcancel.getVisibility()==View.VISIBLE){
		    	imgcancel.setVisibility(View.GONE);
		    }
	 }

}
