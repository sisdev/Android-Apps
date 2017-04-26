package com.assusoft.efair.epchfair.Fragments;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.assusoft.eFairEmall.soapWebService.WebService;
import com.assusoft.eFairEmall.util.MySharedPreferences;
import com.epch.efair.delhifair.HomeActivity;
import com.epch.efair.delhifair.R;

public class SendMailFragment extends Fragment {
	Context context;
	String param;
	EditText etSubject,etBody;
	Button btnSendMail;
	String email;
	String userEmailId;
	MySharedPreferences mySharedPreferences;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.send_mail_fragment, container, false);
		etSubject=(EditText) view.findViewById(R.id.etSubject);
		etBody=(EditText) view.findViewById(R.id.etBody);
		btnSendMail=(Button) view.findViewById(R.id.btnSendMail);
		
		/*//admob
    	AdView adView = new AdView(getActivity(), AdSize.SMART_BANNER, Commons.admobToken);    
		FrameLayout layout = (FrameLayout)view.findViewById(R.id.AdsFrameLayout);    
		layout.addView(adView); 
		AdRequest request = Commons.GetAds(getActivity());
		adView.loadAd(request);
		*/
	    email=this.getArguments().getString("EMAIL_ID");
		Log.i("packplus", "email:"+email);
		
		context=getActivity();
		 mySharedPreferences=new MySharedPreferences(context);
		 userEmailId=mySharedPreferences.getSavedValue(UserRegistrationFragment.USER_EMAIL_ID_KEY);
		 Log.i("EXPO", "userEmailId:"+userEmailId);
	      // if button is clicked, close the custom dialog
		btnSendMail.setOnClickListener(new OnClickListener() {
					@SuppressLint("ResourceAsColor")
					@Override
					public void onClick(View v) {
						String	subject	= etSubject.getText().toString();
						String	body	= etBody.getText().toString();
								body	= body.trim().replace("\n", "\\n");
			
						if(!((etSubject.length()>UserRegistrationFragment.FIELD_SIZE)&&(etBody.length()>UserRegistrationFragment.FIELD_SIZE)))
						{
							Toast.makeText(v.getContext(), "All fields are compulsory", Toast.LENGTH_LONG).show();
						}
						else{
							try { String name=WebService.randomString(WebService.RENDOM_STR_LENGTH);
				    	    String key = WebService.getHmac(name,WebService.SALT);
							
						 param="{\"to\":"+"\""+email+"\","+
						             "\"subject\":"+"\""+subject+"\","+
						             "\"body\":"+"\""+body+"\","+
						             "\"senderEmail\":"+"\""+userEmailId+"\","+
						             "\"name\":"+"\""+name+"\","+
								     "\"key\":"+"\""+key+"\""+"}";
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							SendMail sendMail=new SendMail();
							sendMail.execute();
						}
					}
				});
		return view;
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
//		Commons.getMyTracker(getActivity(), "SendMailFragment");
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
	private class SendMail extends AsyncTask<String, Void, String> {
		ProgressDialog progressDialog;
		String response;
	    @Override
	    protected String doInBackground(String... urls) {
	    	response=WebService.sendMailService(param,context);
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
				{  Bundle bundle;
					String resul=jData.getString(WebService.RESPONSE_RESULT);
					
					 FragmentManager fm = getActivity().getSupportFragmentManager();
				        FragmentTransaction ft = fm.beginTransaction(); 
				        ExhibitorDetailFragment fragment=new ExhibitorDetailFragment();
				    	 bundle=new Bundle();
				    	 bundle.putInt("EXHIBITORID",HomeActivity.exhibitorsID);
				    	 fragment.setArguments(bundle);
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
