package com.assusoft.efair.epchfair.Fragments;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.regex.Pattern;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.assusoft.eFairEmall.soapWebService.WebService;
import com.assusoft.eFairEmall.util.ImplementTextWatcher;
import com.assusoft.eFairEmall.util.MySharedPreferences;
import com.assusoft.eFairEmall.util.Util;
import com.epch.efair.delhifair.HomeAcitityFirst;
import com.epch.efair.delhifair.HomeAcitityFirst.Fragments;
import com.epch.efair.delhifair.HomeActivity;
import com.epch.efair.delhifair.ImageAsyncTask;
import com.epch.efair.delhifair.MainActivity;
import com.epch.efair.delhifair.R;

public class UserRegistrationFragment extends Fragment implements OnFocusChangeListener{
	Context context;
	String param;
	public static boolean isRegistered=false;
	public static final int FIELD_SIZE=2;
	public static final String REGISTERED_NO="no";
	public static final String REGISTERED_YES="yes";
	public static final String USER_EMAIL_ID_KEY="email_id";
	public static final String FEEDBACK_STATUS = "feedback_status";
	public static final String CMPNAME="cmp_name";
	public static final String MOBILE="mobile";
	public static final String USERNAME="user_name";
	
	public static boolean flagFromFeedback=false; //this variable having true value when submit feedback without registration.
	
	MySharedPreferences mySharedPreferences;
	EditText etFname,etCompany,etDesignation,etEmail,etMobile;
	Button btnSubmit, btnRemindMeLater;
	LinearLayout llContainer;
	FrameLayout banner;
	TextView fixedActionBar;
	public static String userEmailId;
	CheckBox chReceiving,chParticipate,chAttendConclave;
	Pattern pattent;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.user_registration, container, false);
		llContainer=(LinearLayout) v.findViewById(R.id.ll_container);
		fixedActionBar=(TextView)v.findViewById(R.id.hardcodeActionBar);
		 etFname=(EditText) v.findViewById(R.id.etFirstName);
		 etCompany=(EditText) v.findViewById(R.id.etCompanyName);
		 etDesignation=(EditText) v.findViewById(R.id.etDesignation);
		 etEmail=(EditText) v.findViewById(R.id.etE_mail);
		 etMobile=(EditText) v.findViewById(R.id.etMobile);
		 chReceiving=(CheckBox) v.findViewById(R.id.checkBoxReceiving);
         chParticipate=(CheckBox) v.findViewById(R.id.checkBoxParticipate);
         chAttendConclave=(CheckBox) v.findViewById(R.id.checkBoxAttendConclave);
         btnSubmit = (Button) v.findViewById(R.id.btnRegistrationSubmit);
         btnRemindMeLater=(Button) v.findViewById(R.id.btnRemidMeLater);
         
         
         etFname.setOnFocusChangeListener(this);
         etCompany.setOnFocusChangeListener(this);
         etDesignation.setOnFocusChangeListener(this);
         etEmail.setOnFocusChangeListener(this);
         etMobile.setOnFocusChangeListener(this);;
         
         banner  = (FrameLayout) v.findViewById(R.id.AdsFrameLayout);
         AnimationDrawable amin=(AnimationDrawable) banner.getBackground();
	 	   amin.start();
	 	   //Animation
	 	   move();
	 	  // blink();

	// Name and designation validation for only alphabet and space
	 	pattent = Pattern.compile(Util.ALPHABETIC_WORD_PATTERN);
	 	
	 	  if(flagFromFeedback){
		 		 if(fixedActionBar.getVisibility()==View.VISIBLE)
		 			 fixedActionBar.setVisibility(View.GONE);
		 		 HomeActivity.setTitle("User Registration");
		  }
	 	   
         context=getActivity();
         mySharedPreferences=new MySharedPreferences(context);
      
         /**Remind me later*/ 
         btnRemindMeLater.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!flagFromFeedback){
					mySharedPreferences.saveUserRegistrationStatus(WebService.REGISTERED_STATUS,REGISTERED_NO);
					Intent homepage=new Intent(getActivity(),HomeAcitityFirst.class);
				    MainActivity.activity.startActivity(homepage);
				    MainActivity.activity.finish();	
				}else{
					flagFromFeedback=false;
					getActivity().getSupportFragmentManager().popBackStack();
				}
			}
		});
         
      // if button is clicked, close the custom dialog
         btnSubmit.setOnClickListener(new OnClickListener() {
				@SuppressLint("ResourceAsColor")
				@Override
				public void onClick(View v) {
					Intent intent	=new Intent(getActivity(),HomeActivity.class);
					Fragments fragment	=Fragments.VISITOR_REGIS;
					intent.putExtra("FLAG",fragment.toString());
					startActivity(intent);
					/**This method only call when exporting database in sd card otherwise comment it.*/
					//copyDatabase(getActivity(), "EPCH_DB.db");
		/*			HomeActivity.hideSoftKeyboard(getActivity());
					String fName=etFname.getText().toString().trim();
					String lName=etFname.getText().toString().trim();
					String cmp=etCompany.getText().toString().trim();
					String designation=etDesignation.getText().toString().trim();
					String mobile=etMobile.getText().toString().trim();
					String email = etEmail.getText().toString().trim();
					String recvStatus="";
					String exbStatus="";
					String attendConclave="";
					
					fName		= fName.trim();
					lName		= lName.trim();
					designation	= designation.trim();
					
					
					if(chReceiving.isChecked()){
					recvStatus="Yes";
					}else{
						recvStatus="No";
					}
					if(chParticipate.isChecked()){
						exbStatus="Yes";
					}else{
						exbStatus="No";
					}
					if(chAttendConclave.isChecked()){
						attendConclave="Yes";
					}else{
						attendConclave="No";
					}
					if(fName.isEmpty() || designation.isEmpty() || cmp.isEmpty() || email.isEmpty() || mobile.isEmpty()){
						EditTextValidator.makeToastTop(getActivity(), getString(R.string.toast_msg_all_field_complusory));
						return;
					}else if(!EditTextValidator.validateEditField(etFname, false, FIELD_SIZE, getString(R.string.error_message_name),
					Color.BLACK, getActivity(), pattent)){
						return;
					}else if(!EditTextValidator.validateEditField(etCompany, false, FIELD_SIZE, getString(R.string.error_message_company),
					Color.BLACK, getActivity(), new Pattern[]{null})){
						return;
					}else if(!EditTextValidator.validateEditField(etDesignation, false, FIELD_SIZE, getString(R.string.error_message_designation),
							Color.BLACK, getActivity(), pattent)){
						return;
					}else if(!EditTextValidator.validateEditField(etEmail, false, FIELD_SIZE, getString(R.string.error_message_name),
							Color.BLACK, getActivity(), android.util.Patterns.EMAIL_ADDRESS)){
						return;
					}else if(!EditTextValidator.validateEditField(etMobile, false, 10, getString(R.string.error_message_mobile_no),
							Color.BLACK, getActivity(), new Pattern[]{null})){
						return;
					}else{
						try { String name=WebService.randomString(WebService.RENDOM_STR_LENGTH);
			    	    String key = WebService.getHmac(name,WebService.SALT);
						
					 param="{\"fname\":"+"\""+fName+"\","+
					             "\"lname\":"+"\""+lName+"\","+
					             "\"cmpName\":"+"\""+cmp+"\","+
					             "\"designation\":"+"\""+designation+"\","+
					             "\"recvNewsStatus\":"+"\""+recvStatus+"\","+
					             "\"exbStatus\":"+"\""+exbStatus+"\","+
					             "\"attendConclave\":"+"\""+attendConclave+"\","+
					             "\"email\":"+"\""+email+"\","+
					             "\"mobileNo\":"+"\""+mobile+"\","+
					             "\"name\":"+"\""+name+"\","+
							     "\"key\":"+"\""+key+"\""+"}";
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					    UserRegistrationWebSubmit webSubmit=new UserRegistrationWebSubmit();
						webSubmit.execute();
					}*/
				}
			});
         
			etEmail.setOnClickListener(new OnClickListener() {
				
				@SuppressLint("ResourceAsColor")
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					etEmail.setTextColor(R.color.black);
				}
			});
			
			final View activityRootView = (LinearLayout) v.findViewById(R.id.llUserRegistration);
			 activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
				
				@Override
				public void onGlobalLayout() {
					try{
						 int heightView = activityRootView.getHeight();
		                 int widthView = activityRootView.getWidth();
		                 if (1.0 * widthView / heightView > 1) {
		                	if(banner.getVisibility()==View.VISIBLE)
		      					 banner.setVisibility(View.GONE);
		                } else {
		                	if(banner.getVisibility()==View.GONE)
		     					 banner.setVisibility(View.VISIBLE);
		                }
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			});
			 
			
			getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
			
		
		return v;
	}
	
	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}



	private class UserRegistrationWebSubmit extends AsyncTask<String, Void, String> {
		ProgressDialog progressDialog;
		String response;
	    @Override
	    protected String doInBackground(String... urls) {
	    	Log.i("WEB_DATA","Visitor_registration_param:"+param);
	        response=WebService.getSubmitVisitor(param);
			Log.i("WEB_DATA","Visitor_registration_Respounce:"+response);
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
				
				if(status.equalsIgnoreCase(WebService.RESPONSE_STATUS_PASS)){
					mySharedPreferences.saveUserRegistrationStatus(WebService.REGISTERED_STATUS,REGISTERED_YES);
				    mySharedPreferences.saveUserRegistrationStatus(USER_EMAIL_ID_KEY,etEmail.getText().toString().trim());
				    mySharedPreferences.saveUserRegistrationStatus(USERNAME,etFname.getText().toString().trim());
				    mySharedPreferences.saveUserRegistrationStatus(CMPNAME,etCompany.getText().toString().trim());
				    mySharedPreferences.saveUserRegistrationStatus(MOBILE,etMobile.getText().toString().trim());
					String resul=jData.getString(WebService.RESPONSE_RESULT);
					
					Toast.makeText(getActivity(), resul, Toast.LENGTH_SHORT).show();
					/*Intent homepage=new Intent(getActivity(),HomeAcitityFirst.class);
				    MainActivity.activity.startActivity(homepage);
				    MainActivity.activity.finish();*/
				    if(!flagFromFeedback){
				    	//go to home screen if successful registered
						Intent homepage=new Intent(getActivity(),HomeAcitityFirst.class);
					    MainActivity.activity.startActivity(homepage);
					    MainActivity.activity.finish();	
					}else{
						flagFromFeedback=false;
						getActivity().getSupportFragmentManager().popBackStack();
					}
				    
				}else if(status.equalsIgnoreCase(WebService.RESPONSE_STATUS_FAIL)){ 
					String error=jData.getString(WebService.RESPONSE_ERROR);
					Toast.makeText(context, error, Toast.LENGTH_LONG).show();
				}
			} catch (Exception e) {
				Toast.makeText(context, "Network connection error.", Toast.LENGTH_LONG).show();
				e.printStackTrace();
				
			}
	    	
	    }
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.v("EFAIR","isRegistered="+isRegistered);
		if(isRegistered){
			isRegistered=false;
			startActivity(new Intent(getActivity(), HomeAcitityFirst.class));
			getActivity().finish();
		}
//		Commons.getMyTracker(getActivity(), "UserRegistrationFragment");
	}
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
	}
	
	
	
// Copy to sdcard for debug use
	public static void copyDatabase(Context c, String DATABASE_NAME) {
        String databasePath = c.getDatabasePath(DATABASE_NAME).getPath();
        File f = new File(databasePath);
        OutputStream myOutput = null;
        InputStream myInput = null;
        Log.d("testing", " testing db path " + databasePath);
        Log.d("testing", " testing db exist " + f.exists());

        if (f.exists()) {
            try {

                File directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+ImageAsyncTask.FOLDER_NAME);
                if (!directory.exists())
                    directory.mkdir();

                myOutput = new FileOutputStream(directory.getAbsolutePath()
                        + File.separator + DATABASE_NAME);
                myInput = new FileInputStream(databasePath);

                byte[] buffer = new byte[1024];
                int length;
                while ((length = myInput.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }

                myOutput.flush();
            } catch (Exception e) {
            } finally {
                try {
                    if (myOutput != null) {
                        myOutput.close();
                        myOutput = null;
                    }
                    if (myInput != null) {
                        myInput.close();
                        myInput = null;
                    }
                } catch (Exception e) {
                }
            }
        }
    }
	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		
		switch (v.getId()) {
		case R.id.etFirstName:
			new ImplementTextWatcher(etFname, hasFocus, FIELD_SIZE, getString(R.string.error_message_name),
					Color.BLACK, getActivity(), pattent);
			break;

		case R.id.etCompanyName:
			new ImplementTextWatcher(etCompany, hasFocus, FIELD_SIZE, getString(R.string.error_message_company),
					Color.BLACK, getActivity(), new Pattern[]{null});
			break;

		case R.id.etDesignation:
			new ImplementTextWatcher(etDesignation, hasFocus, FIELD_SIZE, getString(R.string.error_message_designation),
					Color.BLACK, getActivity(), pattent);
			break;

		case R.id.etE_mail:
			new ImplementTextWatcher(etEmail, hasFocus, FIELD_SIZE, getString(R.string.error_message_name),
					Color.BLACK, getActivity(), android.util.Patterns.EMAIL_ADDRESS);
			break;

		case R.id.etMobile:
			new ImplementTextWatcher(etMobile, hasFocus, 10, getString(R.string.error_message_mobile_no),
					Color.BLACK, getActivity(), new Pattern[]{null});
			break;

		default:
			break;
		}
	}
	public void move(){
	      //ImageView image = (ImageView)findViewById(R.id.imageView);
	      Animation animation1 = AnimationUtils.loadAnimation(this.getActivity().getApplicationContext(), R.anim.move);
	      llContainer.startAnimation(animation1);
	   }
	public void blink(){
	      //ImageView image = (ImageView)findViewById(R.id.imageView);
	      Animation animation1 = AnimationUtils.loadAnimation(this.getActivity().getApplicationContext(), R.anim.blink);
	      llContainer.startAnimation(animation1);
	   }
}
