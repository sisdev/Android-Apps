package com.assusoft.efair.epchfair.Fragments;

import java.util.HashMap;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.assusoft.eFairEmall.soapWebService.WebService;
import com.assusoft.eFairEmall.util.EditTextValidator;
import com.assusoft.eFairEmall.util.ImplementTextWatcher;
import com.assusoft.eFairEmall.util.Util;
import com.epch.efair.delhifair.HomeAcitityFirst;
import com.epch.efair.delhifair.HomeActivity;
import com.epch.efair.delhifair.R;

public class VisitorRegistatrationFragment extends Fragment implements OnClickListener, OnFocusChangeListener{

	public static final int MIN_NO_LENGTH = 1;
	public static final int MIN_TEXT_SIZE = 2;
	public static final int MIN_TELPONE_NO= 10;
	
	public static HashMap<String, String> map1;
	
	private View view;
	private EditText edtName,edtNoPerson,edtTitle,edtCompany,edtAddress,edtCountry,
				edtTelephone,edtFax,edtWebsite,edtEmail;
	
	private Pattern alphabeticWord;
	private Context mContext;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.visitor_registration, container, false);
		
		edtName		= (EditText)view.findViewById(R.id.edtName);
		edtNoPerson	= (EditText)view.findViewById(R.id.edtNoPerson);
		edtTitle	= (EditText)view.findViewById(R.id.edtTitle);
		edtCompany	= (EditText)view.findViewById(R.id.edtCompany);
		edtAddress	= (EditText)view.findViewById(R.id.edtAddress);
		edtCountry	= (EditText)view.findViewById(R.id.edtCountry);
		edtTelephone= (EditText)view.findViewById(R.id.edtTelephone);
		edtFax		= (EditText)view.findViewById(R.id.edtFax);
		edtWebsite	= (EditText)view.findViewById(R.id.edtWebsite);
		edtEmail	= (EditText)view.findViewById(R.id.edtEmail);
		
		view.findViewById(R.id.btnNext).setOnClickListener(this);
		
		edtName.setOnFocusChangeListener(this);edtNoPerson.setOnFocusChangeListener(this);
		edtTitle.setOnFocusChangeListener(this);edtCompany.setOnFocusChangeListener(this);
		edtAddress.setOnFocusChangeListener(this);edtCountry.setOnFocusChangeListener(this);
		edtTelephone.setOnFocusChangeListener(this);edtFax.setOnFocusChangeListener(this);
		edtWebsite.setOnFocusChangeListener(this);edtEmail.setOnFocusChangeListener(this);
		
		mContext = getActivity();
		
		final FrameLayout banner=(FrameLayout) view.findViewById(R.id.AdsFrameLayout);
//		HomeAcitityFirst.adLoader.showBanner(banner);
		AnimationDrawable amin=(AnimationDrawable) banner.getBackground();
		amin.start();
		
		map1 = new HashMap<String, String>();
		   
		alphabeticWord		= Pattern.compile(Util.ALPHABETIC_WORD_PATTERN);
		
		final View rootView = (RelativeLayout) view.findViewById(R.id.rlLayout);
		rootView.getRootView().getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			
			@Override
			public void onGlobalLayout() {
				int viewHeight = rootView.getHeight();
				int viewWidth  = rootView.getWidth();
				if(1.0*viewWidth/viewHeight>1){
					banner.setVisibility(View.GONE);
				}else{
					banner.setVisibility(View.VISIBLE);
				}
			}
		});
		
		return view;
	}

	@SuppressLint("InlinedApi") 
	@Override
	public void onPause() {
		super.onPause();
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
	}

	@Override
	public void onResume() {
		super.onResume();
		HomeActivity.setTitle("Visitor Registration");
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
	}

	private void nextToQuestionnaires(){
		String fname	= edtName.getText().toString().trim();
		String noPerson	= edtNoPerson.getText().toString().trim();
		String title	= edtTitle.getText().toString().trim();
		String company	= edtCompany.getText().toString().trim();
		String address	= edtAddress.getText().toString().trim();
		String country	= edtCountry.getText().toString().trim();;
		String telephone= edtTelephone.getText().toString().trim();;
		String fax		= edtFax.getText().toString().trim();
		String website	= edtWebsite.getText().toString().trim();
		String email	= edtEmail.getText().toString().trim();
		
		if(fname.isEmpty() || noPerson.isEmpty() || title.isEmpty() || company.isEmpty() || address.isEmpty() || telephone.isEmpty() || email.isEmpty()){
			EditTextValidator.makeToastTop(getActivity(), getString(R.string.toast_msg_mandatory_field_));
			return;			
		}else if(!EditTextValidator.validateEditField(edtName, false, MIN_TEXT_SIZE, getString(R.string.error_message_name),
				Color.BLACK, mContext, alphabeticWord)){
			return;
		}else if(!EditTextValidator.validateEditField(edtNoPerson, false, MIN_NO_LENGTH, getString(R.string.error_message_no_of_persons),
				Color.BLACK, mContext, new Pattern[]{null})){
			return;
		}else if(!EditTextValidator.validateEditField(edtTitle, false, MIN_TEXT_SIZE, getString(R.string.error_message_title),
				Color.BLACK, mContext, new Pattern[]{null})){
			return;
		}else if(!EditTextValidator.validateEditField(edtCompany, false, MIN_TEXT_SIZE, getString(R.string.error_message_company),
				Color.BLACK, mContext, new Pattern[]{null})){
			return;
		}else if(!EditTextValidator.validateEditField(edtAddress, false, MIN_TEXT_SIZE, getString(R.string.error_message_address),
				Color.BLACK, mContext, new Pattern[]{null})){
			return;
		}else if(!EditTextValidator.validateEditField(edtTelephone, false, MIN_TELPONE_NO, getString(R.string.error_message_telephone),Color.BLACK, 
				mContext, new Pattern[]{null})){
			return;
		}else if(!EditTextValidator.validateEditField(edtEmail, false, MIN_TEXT_SIZE, getString(R.string.error_message_title),
				Color.BLACK, mContext, android.util.Patterns.EMAIL_ADDRESS)){
			return;
		}
		
		try{
			String name = WebService.randomString(WebService.RENDOM_STR_LENGTH);
			String key = WebService.getHmac(name, WebService.SALT);
			map1.put("name", name);
			map1.put("key", key);
			map1.put("fname", fname);
			map1.put("title", title);
			map1.put("cmpName", company);
			map1.put("number_of_persons", noPerson);
			map1.put("address", address);
			map1.put("country", country);
			map1.put("mobileNo", telephone);
			map1.put("faxNo", fax);
			map1.put("website", website);
			map1.put("email", email);
			
		}catch(Exception e){}
		
		getActivity().getSupportFragmentManager().beginTransaction()
		.replace(R.id.content_frame, new QuestionnaireFragment())
		.addToBackStack(null)
		.commit();
	}
	
	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		switch (v.getId()) {
		case R.id.edtName:
			new  ImplementTextWatcher(edtName, hasFocus, MIN_TEXT_SIZE, getString(R.string.error_message_name),
					Color.BLACK, mContext, alphabeticWord);
			break;

		case R.id.edtNoPerson:
			new ImplementTextWatcher(edtNoPerson, hasFocus, MIN_NO_LENGTH, getString(R.string.error_message_no_of_persons),
					Color.BLACK, mContext, new Pattern[]{null});
			break;
			
		case R.id.edtTitle:
			new ImplementTextWatcher(edtTitle, hasFocus, MIN_TEXT_SIZE, getString(R.string.error_message_title),Color.BLACK, 
					mContext, new Pattern[]{null});
			break;
		
		case R.id.edtCompany:
			new ImplementTextWatcher(edtCompany, hasFocus, MIN_TEXT_SIZE, getString(R.string.error_message_company),
					Color.BLACK, mContext, new Pattern[]{null});
			break;
			
		case R.id.edtAddress:
			new ImplementTextWatcher(edtAddress, hasFocus, MIN_TEXT_SIZE, getString(R.string.error_message_address),
					Color.BLACK, mContext, new Pattern[]{null});
			break;
		
		case R.id.edtCountry:
			break;
			
		case R.id.edtTelephone:
			new ImplementTextWatcher(edtTelephone, hasFocus, MIN_TELPONE_NO, getString(R.string.error_message_telephone),
					Color.BLACK, mContext, new Pattern[]{null});
			break;
		case R.id.edtFax:
			break;
		case R.id.edtWebsite:
			break;
		
		case R.id.edtEmail:
			new ImplementTextWatcher(edtEmail, hasFocus, MIN_TEXT_SIZE, getString(R.string.error_message_email),
					Color.BLACK, mContext, android.util.Patterns.EMAIL_ADDRESS);
			break;
			
		default:
			break;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnNext:
			HomeActivity.hideSoftKeyboard(getActivity());
			nextToQuestionnaires();
			break;

		default:
			break;
		}
	}

}
