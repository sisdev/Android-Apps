package com.assusoft.efair.epchfair.Fragments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.assusoft.eFairEmall.soapWebService.WebService;
import com.epch.efair.delhifair.R;

public class PromotionPlanFragment extends Fragment{
	Context context;
	String param;
	EditText etCmpName,etIndrustry,etContactP,etDesignation,etEmail,etMobile;
	Button btnSubmitPromotion;
	Spinner spinner;
	String email;
	String promotionOption;
	List<String> list;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.promotion_plan, container, false);
		etCmpName=(EditText) view.findViewById(R.id.etCmpName);
		etIndrustry=(EditText) view.findViewById(R.id.etIndrustry);
		etContactP=(EditText) view.findViewById(R.id.etContactP);
		etDesignation=(EditText) view.findViewById(R.id.etDesignation);
		etEmail=(EditText) view.findViewById(R.id.etE_mail);
		etMobile=(EditText) view.findViewById(R.id.etMobile);
		spinner=(Spinner) view.findViewById(R.id.spinnerPOption);
		btnSubmitPromotion=(Button) view.findViewById(R.id.btnPromotionSubmit);
		
		/*//admob
    	AdView adView = new AdView(getActivity(), AdSize.SMART_BANNER, Commons.admobToken);    
		FrameLayout layout = (FrameLayout)view.findViewById(R.id.AdsFrameLayout);    
		layout.addView(adView); 
		AdRequest request = Commons.GetAds(getActivity());
		adView.loadAd(request);*/
		
		context=getActivity();
	      
		list = new ArrayList<String>();
		String[] strArray=getActivity().getResources().getStringArray(R.array.promotion_option);
		
		list.addAll(Arrays.asList(strArray));
		Log.i("EXPO", "promotion list size:"+list);
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, list);
			dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner.setAdapter(dataAdapter);
		btnSubmitPromotion.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						String cmpName=etCmpName.getText().toString();
						String indrustry=etIndrustry.getText().toString();
						String contactP=etContactP.getText().toString();
						String designation=etDesignation.getText().toString();
						String email=etEmail.getText().toString();
						String mobile=etMobile.getText().toString();
						
						
						if(!((cmpName.length()>UserRegistrationFragment.FIELD_SIZE)&&(indrustry.length()>UserRegistrationFragment.FIELD_SIZE)&&(contactP.length()>UserRegistrationFragment.FIELD_SIZE)&&
								(designation.length()>UserRegistrationFragment.FIELD_SIZE)&&(email.length()>UserRegistrationFragment.FIELD_SIZE)&&
								(mobile.length()>UserRegistrationFragment.FIELD_SIZE)))
						{
							Toast.makeText(v.getContext(), "All fields are compulsory", Toast.LENGTH_LONG).show();
						}else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
						{
							Toast.makeText(v.getContext(), "Invalid Email-Id", Toast.LENGTH_LONG).show();
							
						}else if(!(mobile.length()==10))
						{
							Toast.makeText(v.getContext(), "Invalid Mobile Number", Toast.LENGTH_LONG).show();
						}
						else{
							try { String name=WebService.randomString(WebService.RENDOM_STR_LENGTH);
				    	    String key = WebService.getHmac(name,WebService.SALT);
							
				    	    param="{\"promotionOption\":"+"\""+promotionOption+"\","+
				    	    		"\"cmpName\":"+"\""+cmpName+"\","+
						             "\"designation\":"+"\""+designation+"\","+
						             "\"email\":"+"\""+email+"\","+
						             "\"mobileNo\":"+"\""+mobile+"\","+
						             "\"contactPerson\":"+"\""+contactP+"\","+
						             "\"industry\":"+"\""+indrustry+"\","+
						             "\"name\":"+"\""+name+"\","+
								     "\"key\":"+"\""+key+"\""+"}";
						 Log.i("EXPO","param:"+param);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							SendPromotion sendPromotion=new SendPromotion();
							sendPromotion.execute();
						}
					}
				});
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				promotionOption=list.get(position);
				 Log.i("EXPO","promotionOption:"+promotionOption);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		return view;
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
//		Commons.getMyTracker(getActivity(), "PromotionPlanFragment");
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
	private class SendPromotion extends AsyncTask<String, Void, String> {
		ProgressDialog progressDialog;
		String response;
	    @Override
	    protected String doInBackground(String... urls) {
	    	response=WebService.setPromotionalInfo(param);
			Log.i("WEB_DATA","param:"+param);
			Log.i("WEB_DATA","Send Mail responce:"+response);
	      return response;
	    }
	    @Override
	    protected void onPreExecute() {
	    	// TODO Auto-generated method stub
	    	super.onPreExecute();
	    	progressDialog = new ProgressDialog(context);
	    	progressDialog.setMessage("Sending Data...");
	    	progressDialog.setIndeterminate(true);
	    	progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	    	progressDialog.setCancelable(false);
	    	progressDialog.show();
	    	
	    }
	    @Override
	    protected void onPostExecute(String result) {
	    	progressDialog.dismiss();
	    	try {
				JSONObject jData=new JSONObject(response);
				String status=jData.getString(WebService.STATUS);
				
				if(status.equalsIgnoreCase(WebService.RESPONSE_STATUS_PASS))
				{  
					String resul=jData.getString(WebService.RESPONSE_RESULT);
					 FragmentManager fm = getActivity().getSupportFragmentManager();
				        FragmentTransaction ft = fm.beginTransaction(); 
				        HomeFragment fragment= new HomeFragment();
				        ft.add(R.id.content_frame, fragment,"Home");
				        ft.commit();  
					
				}else if(status.equalsIgnoreCase(WebService.RESPONSE_STATUS_FAIL))
				{ 
					String error=jData.getString(WebService.RESPONSE_ERROR);
					Toast.makeText(context, error, Toast.LENGTH_LONG).show();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    }
	}
}
