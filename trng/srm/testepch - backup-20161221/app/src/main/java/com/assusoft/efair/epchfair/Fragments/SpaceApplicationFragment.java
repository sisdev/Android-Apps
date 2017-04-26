package com.assusoft.efair.epchfair.Fragments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
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

public class SpaceApplicationFragment extends Fragment{
	EditText etCmpName,etIndrustry,etContactP,etDesignation,etEmail,etMobile,etArea,etAmout;
	Button btnSpaceAppSubmit;
	String param;
	Spinner spinner;
	Context context;
	List<String> list;
	String pStall;
	static final String amout="0";
	public boolean spaceAppRequestedFlag=false;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.space_application_fragment, container, false);
		etCmpName=(EditText) view.findViewById(R.id.etCmpName);
		etIndrustry=(EditText) view.findViewById(R.id.etIndrustry);
		etContactP=(EditText) view.findViewById(R.id.etContactP);
		etDesignation=(EditText) view.findViewById(R.id.etDesignation);
		etEmail=(EditText) view.findViewById(R.id.etE_mail);
		etMobile=(EditText) view.findViewById(R.id.etMobile);
		etArea=(EditText) view.findViewById(R.id.etArea);
		
		spinner=(Spinner) view.findViewById(R.id.spinnerSpaceApplicaion);
		btnSpaceAppSubmit=(Button) view.findViewById(R.id.btnSpaceAppSubmit);
		
		/*//admob
    	AdView adView = new AdView(getActivity(), AdSize.SMART_BANNER, Commons.admobToken);    
		FrameLayout layout = (FrameLayout)view.findViewById(R.id.AdsFrameLayout);    
		layout.addView(adView); 
		AdRequest request = Commons.GetAds(getActivity());
		adView.loadAd(request);
		*/
		context=getActivity();
		
		list = new ArrayList<String>();
		String[] strArray=getActivity().getResources().getStringArray(R.array.prefered_stall_option);
		
		list.addAll(Arrays.asList(strArray));
		Log.i("EXPO", "promotion list size:"+list);
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, list);
			dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner.setAdapter(dataAdapter);
		
		btnSpaceAppSubmit.setOnClickListener(new OnClickListener() {
				@SuppressLint("ResourceAsColor")
				@Override
				public void onClick(View v) {
					if(spaceAppRequestedFlag)
					{
						Toast.makeText(v.getContext(), "You have Already Requested", Toast.LENGTH_LONG).show();
						return;
					}
					String cmpName=etCmpName.getText().toString();
					String indrustry=etIndrustry.getText().toString();
					String contactP=etContactP.getText().toString();
					String designation=etDesignation.getText().toString();
					String email=etEmail.getText().toString();
					String mobile=etMobile.getText().toString();
					String area=etArea.getText().toString();
					
					
					if(!((cmpName.length()>UserRegistrationFragment.FIELD_SIZE)&&(indrustry.length()>UserRegistrationFragment.FIELD_SIZE)&&(contactP.length()>UserRegistrationFragment.FIELD_SIZE)&&
							(designation.length()>UserRegistrationFragment.FIELD_SIZE)&&(email.length()>UserRegistrationFragment.FIELD_SIZE)&&(pStall.length()>UserRegistrationFragment.FIELD_SIZE)&&
							(area.length()>UserRegistrationFragment.FIELD_SIZE)&&(mobile.length()>UserRegistrationFragment.FIELD_SIZE)&&(area.length()>UserRegistrationFragment.FIELD_SIZE)))
					{
						Toast.makeText(v.getContext(), "All fields are compulsory", Toast.LENGTH_LONG).show();
					}else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
					{
						Toast.makeText(v.getContext(), "Invalid Email-Id", Toast.LENGTH_LONG).show();
						etEmail.setTextColor(R.color.red);
					}else if(!(mobile.length()==10))
					{
						Toast.makeText(v.getContext(), "Invalid Mobile Number", Toast.LENGTH_LONG).show();
					}
					else{
						try { String name=WebService.randomString(WebService.RENDOM_STR_LENGTH);
			    	    String key = WebService.getHmac(name,WebService.SALT);
			    	                         				
					 param="{\"cmpName\":"+"\""+cmpName+"\","+
					             "\"designation\":"+"\""+designation+"\","+
					             "\"email\":"+"\""+email+"\","+
					             "\"mobileNo\":"+"\""+mobile+"\","+
					             "\"contactPerson\":"+"\""+contactP+"\","+
					             "\"industry\":"+"\""+indrustry+"\","+
					             "\"preferStall\":"+"\""+pStall+"\","+
					             "\"area\":"+"\""+area+"\","+
					             "\"amount\":"+"\""+amout+"\","+
					             "\"name\":"+"\""+name+"\","+
							     "\"key\":"+"\""+key+"\""+"}";
					 Log.i("EXPO","param:"+param);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						SpaceApplication spaceApp=new SpaceApplication();
						spaceApp.execute();
					}
				}
			});
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				pStall=list.get(position);
				 Log.i("EXPO","pStall:"+pStall);
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
//		Commons.getMyTracker(getActivity(), "SpaceApplicationFragment");
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
	private class SpaceApplication extends AsyncTask<String, Void, String> {
		ProgressDialog progressDialog;
		String response;
	    @Override
	    protected String doInBackground(String... urls) {
	        response=WebService.getRegisterForSpace(param);
			Log.i("WEB_DATA","Space application_Respounce:"+response);
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
				{   spaceAppRequestedFlag=true;
					String resul=jData.getString(WebService.RESPONSE_RESULT);
					
					//go to home screen if successful registered 
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
