package com.assusoft.efair.epchfair.Fragments;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.Toast;

import com.assusoft.eFairEmall.internetAndGpsMaster.InternetConnectionDetector;
import com.assusoft.eFairEmall.soapWebService.WebService;
import com.assusoft.eFairEmall.util.MySharedPreferences;
import com.epch.efair.delhifair.HomeAcitityFirst;
import com.epch.efair.delhifair.HomeActivity;
import com.epch.efair.delhifair.R;



public class FeedbackFragment extends Fragment{
	private RatingBar ratingBarF,ratingbarUI,ratingbarDA,ratingbarP,ratingbarEU;
	private EditText remarks;
	private float ratingF=0f,ratingUI=0f,ratingDA=0f,ratingP=0f,ratingEU=0f;
	Button btnSubmit;
	View view;
	String remark="",email="",name="",key="";
	MySharedPreferences mysharedpreferences;
	InternetConnectionDetector icd;
	String cmpName,fname,mobile;
	FrameLayout banner;
	
	@SuppressLint("CutPasteId")
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mysharedpreferences=new MySharedPreferences(getActivity());
		view = inflater.inflate(R.layout.feedback_fragment, container, false);
		 banner=(FrameLayout) view.findViewById(R.id.AdsFrameLayout);
		 HomeAcitityFirst.adLoader.showBanner(banner);
	 	   /*AnimationDrawable amin=(AnimationDrawable) banner.getBackground();
	 	   amin.start();*/
		
		
			remarks=(EditText) view.findViewById(R.id.edtRemarks);
			ratingBarF = (RatingBar) view.findViewById(R.id.myRatingBarFirst);
			ratingbarUI=(RatingBar) view.findViewById(R.id.myRatingBarSecond);
			ratingbarDA=(RatingBar) view.findViewById(R.id.myRatingBarThird);
			ratingbarP=(RatingBar) view.findViewById(R.id.myRatingBarFourth);
			ratingbarEU=(RatingBar) view.findViewById(R.id.myRatingBarFiveth);
			btnSubmit=(Button) view.findViewById(R.id.btnfeedbackSubmit);
			//if rating value is changed,
			//display the current rating value in the result (textview) automatically
			ratingBarF.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
				public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
		             ratingF=rating;
					Log.i("EXPO", "rating:"+rating);
		 
				}
			});
			ratingbarUI.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
				public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
					 ratingUI=rating;
					Log.i("EXPO", "rating:"+rating);
		 
				}
			});
			ratingbarDA.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
				public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
					 ratingDA=rating;
					Log.i("EXPO", "rating:"+rating);
		 
				}
			});
			ratingbarP.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
				public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
					 ratingP=rating;
					Log.i("EXPO", "rating:"+rating);
		 
				}
			});
			ratingbarEU.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
				public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
					 ratingEU=rating;
					Log.i("EXPO", "rating:"+rating);
		 
				}
			});
			
			btnSubmit.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(remarks.getText().toString().trim().equalsIgnoreCase("")){
						Toast.makeText(getActivity(), "Remark is must for feedback.",Toast.LENGTH_SHORT).show();
					    return;
					}
					InternetConnectionDetector icd = new InternetConnectionDetector(getActivity());
					if(!icd.isConnectingToInternet()){
						Toast.makeText(getActivity(), "May be internet connection is not available.",Toast.LENGTH_SHORT).show();
						return;
					}
					if(!mysharedpreferences.isUserRegistered(WebService.REGISTERED_STATUS) && !mysharedpreferences.isUserRegistered(WebService.REGISTERED_STATUS_F)){
						FeedbackUserRegistrationFragment.flagFromFeedback=true;
//						UserRegistrationFragment.flagFromFeedback=true;
						getActivity().getSupportFragmentManager()
						.beginTransaction()
						.replace(R.id.content_frame, new FeedbackUserRegistrationFragment(), "UserRegistrationFragment")
						//.replace(R.id.content_frame, new UserRegistrationFragment(), "UserRegistrationFragment")
						.addToBackStack(null)
						.commit();
						return;
					}
					
					email=mysharedpreferences.getSavedValue(UserRegistrationFragment.USER_EMAIL_ID_KEY);
					cmpName=mysharedpreferences.getSavedValue(UserRegistrationFragment.CMPNAME);
					mobile=mysharedpreferences.getSavedValue(UserRegistrationFragment.MOBILE);
					fname=mysharedpreferences.getSavedValue(UserRegistrationFragment.USERNAME);
					remark = remarks.getText().toString().trim().replace("\n", "\\n");
					new UserFeedbackWebSubmit().execute();
				}
			});
			
			final View rootLayout = (LinearLayout) view.findViewById(R.id.llfeedbackFragment);
			rootLayout.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
				
				@Override
				public void onGlobalLayout() {
					 int heightDiff = rootLayout.getRootView()
		                        .getHeight() - rootLayout.getHeight();
					 int heightView = rootLayout.getHeight();
	                 int widthView = rootLayout.getWidth();
	                 if (1.0 * widthView / heightView > 1) {
		                //if (heightDiff > 100) { // if more than 100 pixels, its
		                    if(banner.getVisibility()==View.VISIBLE)
		                    	banner.setVisibility(View.GONE);
		                } else {
		                    if(banner.getVisibility()==View.GONE)
		                    	banner.setVisibility(View.VISIBLE);
		                }
				}
			});
			
			final View activityRootView = (LinearLayout) view.findViewById(R.id.llfeedbackFragment);
			 activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
				
				@Override
				public void onGlobalLayout() {
					
					 int heightView = activityRootView.getHeight();
	                int widthView = activityRootView.getWidth();
	                if (1.0 * widthView / heightView > 1) {
	               	if(banner.getVisibility()==View.VISIBLE)
	     					 banner.setVisibility(View.GONE);
	               } else {
	               	if(banner.getVisibility()==View.GONE)
	    					 banner.setVisibility(View.VISIBLE);
	               }
				}
			});
			
			getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);		
			
		return view;
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
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		//Removing effect of SOFT_INPUT_ADJUST_RESIZE when shutdown soft keyboard
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
	}

	@Override
	public void onResume() {
	// TODO Auto-generated method stub
	super.onResume();
	HomeActivity.setTitle("Feedback");
	}



	private class UserFeedbackWebSubmit extends AsyncTask<String, Void, String> {
	ProgressDialog progressDialog;
	String response;
	@Override
    protected void onPreExecute() {
    	super.onPreExecute();
    	progressDialog = new ProgressDialog(getActivity());
    	progressDialog.setMessage("Sending Feedback...");
    	progressDialog.setIndeterminate(true);
    	progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    	progressDialog.setCancelable(false);
    	progressDialog.show();
    	
    }
    @Override
    protected String doInBackground(String... urls) {
    	String name,key = null;
    	name=WebService.randomString(WebService.RENDOM_STR_LENGTH);
	     try {
			key = WebService.getHmac(name,WebService.SALT);
		} catch (Exception e) {
			e.printStackTrace();
		}
	    String userType="";
    	String param="{\"name\":"+"\""+name+"\","+
	             "\"key\":"+"\""+key+"\","+
	             "\"userType\":"+"\""+userType+"\","+
	             "\"cName\":"+"\""+cmpName+"\","+
	             "\"mobileNo\":"+"\""+mobile+"\","+
	             "\"username\":"+"\""+fname+"\","+
	             
	             "\"features\":"+"\""+ratingF+"\","+
	             "\"performance\":"+"\""+ratingP+"\","+
	             "\"email\":"+"\""+email+"\","+
	             "\"userInterface\":"+"\""+ratingUI+"\","+
	             "\"easeOfAccess\":"+"\""+ratingEU+"\","+
	             "\"dataAccess\":"+"\""+ratingDA+"\","+
	             "\"remarks\":"+"\""+remark+"\""+
	             "}";
    	
        response=WebService.sendUserFeedback(param);
		Log.i("WEB_DATA","Feed back response:"+response);
      return response;
    }
    
    @Override
    protected void onPostExecute(String result) {
    	progressDialog.dismiss();
    	try {
			JSONObject jData=new JSONObject(response);
			String status=jData.getString(WebService.STATUS);
			
			if(status.equalsIgnoreCase(WebService.RESPONSE_STATUS_PASS))
			{  
		
				String results=jData.getString(WebService.RESPONSE_RESULT);
				MySharedPreferences mysharedpreferences=new MySharedPreferences(getActivity());
				mysharedpreferences.saveUserData(UserRegistrationFragment.FEEDBACK_STATUS,"yes");
				
				Toast.makeText(getActivity(), results, Toast.LENGTH_SHORT).show();
		        getActivity().finish();
			}else if(status.equalsIgnoreCase(WebService.RESPONSE_STATUS_FAIL))
			{ 
				String error=jData.getString(WebService.RESPONSE_ERROR);
				Log.i("WEB_DATA",error);
				Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) {
			e.printStackTrace();
			if(!icd.isConnectingToInternet()){
				icd.showInternetSettingsAlert();
			}else{
				
			}
			
		}
    }
  }	
}