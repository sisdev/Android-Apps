package com.assusoft.efair.epchfair.Fragments;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.assusoft.eFairEmall.databaseMaster.DatabaseHelper;
import com.assusoft.eFairEmall.entities.ExhibitorLocation;
import com.assusoft.eFairEmall.entities.Exhibitors;
import com.assusoft.eFairEmall.entities.Favourite;
import com.assusoft.eFairEmall.entities.VenueMap;
import com.epch.efair.delhifair.EFairEmallApplicationContext;
import com.epch.efair.delhifair.HomeAcitityFirst;
import com.epch.efair.delhifair.HomeActivity;
import com.epch.efair.delhifair.R;
import com.google.ads.AdSize;
public class ExhibitorDetailFragment extends Fragment{
	Exhibitors exhib;
	DatabaseHelper dbHelper;
	List<Object> users;
	int exibitorId;
	public static String exhibitorName;//used in productImage class
	public static ArrayList<String> productList;
	Button btnHome,btnAdvanceSearch,btnAddToMyVisit,btnEmailLuncher;
    LinearLayout llWebsite,llLocationLuncher,llMobileCall,llLandlineCall,llEmail;
    TextView name,address1,address2,pinCode,type,floorName,stallNo,city,state, tvGallery;
    String standNo="Stand No:";
    AdSize adsize;
    TextView products,contactPerson,mobileNo,landlineNo,email,websit;//,tvdescription;
    String xLocation,yLocation,fileName;
    String hallNo;
    String stall="";
    String add="";
    ImageButton btnBookmark;
    Button btnAddToContacts;
    Bundle bundle;
   
     @Override
 	public View onCreateView(LayoutInflater inflater, ViewGroup container,
 			Bundle savedInstanceState) {
 		View view = inflater.inflate(R.layout.exhibitors_details_modified, container, false);
 		btnBookmark=(ImageButton) view.findViewById(R.id.bookmark);
 		
 		//Getting reference to the TextView of the Fragment
		name			= (TextView) view.findViewById(R.id.tvExbDName);	
		
		address1		= (TextView) view.findViewById(R.id.tvExbDAddress1);		
		//pinCode		= (TextView) view.findViewById(R.id.tvPinCode);
		email			= (TextView) view.findViewById(R.id.tvEmailId);
		websit			= (TextView) view.findViewById(R.id.tvWebsit);
		products		= (TextView) view.findViewById(R.id.tvProducts);
		contactPerson	= (TextView) view.findViewById(R.id.tvContactPerson);
		mobileNo		= (TextView) view.findViewById(R.id.tvMobileNo);
		landlineNo		= (TextView) view.findViewById(R.id.tvLandlineContact);
		
		tvGallery		= (TextView) view.findViewById(R.id.tvGalleryIcon);
		
		llMobileCall	= (LinearLayout) view.findViewById(R.id.llMobileCall);
		llLandlineCall	= (LinearLayout) view.findViewById(R.id.llLandlineCall);
		llEmail			= (LinearLayout) view.findViewById(R.id.llEmailLuncher);
		llWebsite		= (LinearLayout) view.findViewById(R.id.llWebsitLuncher);
		
		btnAddToContacts=(Button) view.findViewById(R.id.btnAddContacts);
		//tvdescription=(TextView)view.findViewById(R.id.tvDescription);
		
		FrameLayout banner=(FrameLayout) view.findViewById(R.id.AdsFrameLayout);
		HomeAcitityFirst.adLoader.showBanner(banner);
//		AnimationDrawable amin=(AnimationDrawable) banner.getBackground();
//		   amin.start();
		floorName=(TextView) view.findViewById(R.id.tvType);
		///stallNo=(TextView) view.findViewById(R.id.tvStandNo);
		//llWebsite=(LinearLayout) view.findViewById(R.id.llWebsite);
		llLocationLuncher=(LinearLayout) view.findViewById(R.id.llLocationLuncher);
		//btnEmailLuncher=(Button) view.findViewById(R.id.btnEmailLuncher);
		//get the value of exhibitorId from intent
		
		bundle=new Bundle();
		
	    exibitorId=this.getArguments().getInt("EXHIBITORID");
	    stall=this.getArguments().getString("STALLNO");
	    HomeActivity.exhibitorsID=exibitorId;//this value is hold for back stack
	    Log.i("EFAIR", " exhibitor details exibitorId= "+exibitorId);
	      dbHelper=EFairEmallApplicationContext.getDatabaseHelper();
	      
		//get the readable database
		dbHelper.openDatabase(DatabaseHelper.READMODE);
		users=dbHelper.getDetailOfSelectedExibitor(Integer.toString(exibitorId),stall);
		productList=dbHelper.getProductListOfExibitor(Integer.toString(exibitorId));
		
		try{
			ExhibitorLocation location=(ExhibitorLocation) users.get(0);
			
			VenueMap vmap=(VenueMap) users.get(1);
			xLocation=location.getxLocation();
			yLocation=location.getyLocation();
			fileName=vmap.getFilePath();
			hallNo=vmap.getHallNo().trim();
		    exhib=(Exhibitors) users.get(2);
			
		    String text="";
		    if(vmap.getType().equalsIgnoreCase("1"))	text = "Mart : ";
		    else text = "Hall No. : ";
		    floorName.setText(text+vmap.getHallNo()+"\n\n"+"Stall No. : "+location.getStandNo());
		  //stallNo.setText("Stall No. : "+location.getStandNo());
		
		    if(!exhib.getAddress2().equalsIgnoreCase("Not Available")){
		    	add=exhib.getAddress2();
		    }
		    Log.i("DATA","Address2- "+exhib.getAddress2());
		//set the details to user
		    address1.setText(exhib.getAddress1()+", "+add+"\n\n"+exhib.getCity());
		    mobileNo.setText(exhib.getMobileNo());		   
		    landlineNo.setText(exhib.getContactNo());
		    
		    
		    final Intent callIntent = new Intent(Intent.ACTION_CALL);
		 //Lunching telephonic calls, click on mobile number or Landline number		    
		    llMobileCall.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					callIntent.setData(Uri.parse("tel:"+exhib.getMobileNo()));
					startActivity(callIntent);
				}
			});
		    llLandlineCall.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					callIntent.setData(Uri.parse("tel:"+exhib.getContactNo()));
					startActivity(callIntent);
				}
			});
		 // set the contact person 
		    contactPerson.setText(exhib.getContactPerson());
			
			name.setText(exhib.getExhibitorName());
			//pinCode.setText("Pin code : "+exhib.getPinCode());
	
			email.setText(exhib.getEmail());
			websit.setText(exhib.getWebSite());
			
			//Lunching website and sending a mail 
			llEmail.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					try{
		                	Intent intent=new Intent();
		                	intent.setAction(Intent.ACTION_SENDTO);
		                	intent.setData(Uri.parse("mailto:"+exhib.getEmail()));
		                	startActivity(intent);
		               }catch(Exception e){
		                    e.printStackTrace();
		                }

				}
			});
			llWebsite.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://"+exhib.getWebSite()));
					startActivity(webIntent);
					
				}
			});
			//email.setText("Email : "+exhib.getEmail());
			///email.setText("Send Mail");
			
			//tvdescription.setText(exhib.getDescription());
			Log.i("EMALL", "exhib.getExhibitorName()="+exhib.getExhibitorName());
			exhibitorName=exhib.getExhibitorName();
			
			
			//set the products list 
			if(!productList.isEmpty()){
				products.setText(TextUtils.join(",\n\n",productList));
			}
			
			
			//check exhibitor is already added to favorite list or not
			if(dbHelper.isBookMark(exibitorId)){
	   		    btnBookmark.setImageResource(R.drawable.book_mark_icon_white);
	    		
	   	    }
			btnBookmark.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(dbHelper.isBookMark(exibitorId)){
			   		    btnBookmark.setImageResource(R.drawable.book_mark_icon);
			   		    //delete from favourite table if already book marked
			   		     dbHelper.deleteBookMark(exibitorId);
			   		  Toast.makeText(getActivity(), "This exhibitor is removed from your favorite list.",Toast.LENGTH_SHORT).show();
			   	    }else{
			   	    	//book mark and insert in favourite table
			   	    	btnBookmark.setImageResource(R.drawable.book_mark_icon_white);
			   	    	Favourite favorites=new Favourite();
	                	 favorites.setExhibitorId(exibitorId);
	                	 dbHelper.submitBookMark(favorites);
	                	 Toast.makeText(getActivity(), "This exhibitor is added to your favorite list.",Toast.LENGTH_SHORT).show();
			   	    }
					
				}
			});
			
			tvGallery.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					bundle.putString("REFNO",exhib.getRefNo());
					ImageGallery imgFragmrnt = new ImageGallery();
					imgFragmrnt.setArguments(bundle);
					((FragmentActivity)v.getContext()).getSupportFragmentManager()
					.beginTransaction().replace(R.id.content_frame,imgFragmrnt)
					.addToBackStack(null).commit();
				}
			});
			btnAddToContacts.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent addPersonIntent = new Intent(Intent.ACTION_INSERT);
                    addPersonIntent.setType(ContactsContract.Contacts.CONTENT_TYPE);

                    addPersonIntent.putExtra(ContactsContract.Intents.Insert.NAME,contactPerson.getText());
                    addPersonIntent.putExtra(ContactsContract.Intents.Insert.PHONE,mobileNo.getText());
                    addPersonIntent.putExtra(ContactsContract.Intents.Insert.EMAIL,email.getText());
                    addPersonIntent.putExtra(ContactsContract.Intents.Insert.POSTAL, address1.getText());
                    addPersonIntent.putExtra(ContactsContract.Intents.Insert.COMPANY, name.getText());
                    addPersonIntent.putExtra(ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE,websit.getText());

                    getActivity().startActivity(addPersonIntent);
					
				}
			});
		}catch(Exception e){ 
			Log.i("EFAIR", "OBject exceptions");
			e.printStackTrace();}
		
		
		
 		return view;
 	}
     
     @Override
    public void onStart() {
    	super.onStart();
    	
    	llLocationLuncher.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//set the back flag
				//HomeActivity.venuemapFlag=true;
				FragmentTransaction ft = ((FragmentActivity)getActivity()).getSupportFragmentManager().beginTransaction();
		        bundle.putString("XLOCATION",xLocation);
		        bundle.putString("YLOCATION",yLocation);
		        bundle.putString("FILEPATH",fileName);
		        bundle.putInt("EXHIBITORID",exibitorId);
		        VenueMapWithLocation fragment = new VenueMapWithLocation();
		        
		        fragment.setArguments(bundle);
		        ft.replace(R.id.content_frame, fragment,"Products");
		        ft.addToBackStack(null);
		        ft.commit();
			}
		});
//    	Commons.getMyTracker(getActivity(), "ExhibitorDetailFragment");
    	//Hiding key
    	InputMethodManager inputManager = (InputMethodManager) getActivity()
	            .getSystemService(Context.INPUT_METHOD_SERVICE);
	    //check if no view has focus:
	    View v=getActivity().getCurrentFocus();
	    if(v==null)
	        return;
	    inputManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
   @Override
public void onResume() {
	super.onResume();
	HomeActivity.hideSoftKeyboard(getActivity());
	HomeActivity.setTitle("Exhibitor Detail");
	
	/*llWebsite.setOnClickListener(new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			try{
				if(exhib.getWebSite()==null || exhib.getWebSite().equals("")){
			
				return;
			  }
			}catch(Exception e){
				e.printStackTrace();
				return;
			}
				
			// TODO Auto-generated method stub
			Intent in=new Intent(Intent.ACTION_VIEW,Uri.parse("http://"+exhib.getWebSite()));
            startActivity(in);
		}
	});*/
	/*btnEmailLuncher.setOnClickListener(new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			try{
				if(exhib.getEmail()==null || exhib.getEmail().equals("")){
			
				return;
			  }
			}catch(Exception e){
				e.printStackTrace();
				return;
			}
			Bundle bundle=new Bundle();
			 bundle.putString("EMAIL_ID",exhib.getEmail());
			 //bundle.putInt("EXHIBITORID", exibitorId);
			 
			FragmentTransaction ft = ((FragmentActivity)getActivity()).getSupportFragmentManager().beginTransaction();
	        
			SendMailFragment fragment=new SendMailFragment();
			fragment.setArguments(bundle);
	        ft.replace(R.id.content_frame, fragment);
	        
	        ft.commit();
	               
		}
	});
*/	
}  
   
   @Override
	public void onDestroy() {
		super.onDestroy();
	}
	
	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}
	
	
     
}
