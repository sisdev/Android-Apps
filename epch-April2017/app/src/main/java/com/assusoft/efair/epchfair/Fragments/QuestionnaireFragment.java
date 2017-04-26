package com.assusoft.efair.epchfair.Fragments;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.assusoft.eFairEmall.internetAndGpsMaster.InternetConnectionDetector;
import com.assusoft.eFairEmall.soapWebService.WebService;
import com.assusoft.eFairEmall.util.MySharedPreferences;
import com.epch.efair.delhifair.HomeAcitityFirst;
import com.epch.efair.delhifair.HomeActivity;
import com.epch.efair.delhifair.R;

import android.app.ProgressDialog;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.Preference;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

public class QuestionnaireFragment extends Fragment implements OnClickListener, OnPageChangeListener{

	private View view;
	private TextView queCount;
	private Button btnPrevious, btnNext;
	private ViewPager pager;
	private QuestionnairePagerAdapter adapter;
	private HashMap<String, ArrayList<Integer>> selectedOptMap;
	private HashMap<String, Object> paramMap;
	
	private int totalQues, currentItemPosition;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.questionnaires, container, false);
		
		queCount	= (TextView) view.findViewById(R.id.queCount);
		pager		= (ViewPager) view.findViewById(R.id.queViewPager);
		btnPrevious	= (Button) view.findViewById(R.id.btnPrevious);
		btnNext		= (Button) view.findViewById(R.id.btnNext);
		FrameLayout banner=(FrameLayout) view.findViewById(R.id.AdsFrameLayout);
	 	   AnimationDrawable amin=(AnimationDrawable) banner.getBackground();
	 	   amin.start();
		btnPrevious.setOnClickListener(this);btnNext.setOnClickListener(this);
		pager.setOnPageChangeListener(this);
		
		selectedOptMap = new HashMap<String, ArrayList<Integer>>();
		paramMap = new HashMap<String, Object>();
		
		adapter = new QuestionnairePagerAdapter(getActivity());
		pager.setAdapter(adapter);
		
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		HomeActivity.setTitle("Visitor Registration");
		
		totalQues = adapter.getCount();
		pager.setOffscreenPageLimit(totalQues);
		currentItemPosition = pager.getCurrentItem();
		pager.setCurrentItem(currentItemPosition);
		queCount.setText(currentItemPosition+1+"/"+totalQues);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnPrevious:
			if(currentItemPosition==0){
				getActivity().getSupportFragmentManager().popBackStack();
			}else{
				pager.setCurrentItem(currentItemPosition-1);
			}
			break;

		case R.id.btnNext:
			if(currentItemPosition==totalQues-1){
				//btnNext.setText("Submit");
				InternetConnectionDetector icd= new InternetConnectionDetector(getActivity());
				if(!icd.isConnectingToInternet()){
					icd.showInternetSettingsAlert();
					return;
				}
				selectedOptMap.put("optionId", QuestionnairePagerAdapter.selectedOption);
				paramMap.putAll(VisitorRegistatrationFragment.map1);
				paramMap.putAll(selectedOptMap);
				JSONObject paramJson = new JSONObject(paramMap);
				new VisitorRegistrationTask().execute(paramJson.toString());
			}else{
				pager.setCurrentItem(currentItemPosition+1);
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		
	}

	@Override
	public void onPageSelected(int arg0) {
		currentItemPosition = arg0;
		queCount.setText(currentItemPosition+1+"/"+totalQues);
		if(currentItemPosition==0){
		}else{
			btnPrevious.setText("Previous");
		}
		if(currentItemPosition==totalQues-1){
			btnNext.setText("Submit");
		}else{
			btnNext.setText("Next");
		}
	}
	
	private class VisitorRegistrationTask extends AsyncTask<String, Void, String>{

		String response=null;
		ProgressDialog dialog = new ProgressDialog(getActivity());
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog.setMessage("Sending data, please wait...");
			dialog.setIndeterminate(true);
			dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			dialog.setCancelable(false);
			dialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			Log.i("WEB_DATA","[param] "+params[0]);
			response = WebService.getRegisterForVisitorDelhifair(params[0]);
			return response;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			Log.i("WEB_DATA","[Response Visitor Registration Delhifair] "+result);
			if(result==null){
				Toast.makeText(getActivity(), "Internal server error.", Toast.LENGTH_SHORT).show();
				return;
			}
			
			try {
				JSONObject object = new JSONObject(result);
				String status = object.getString(WebService.STATUS);
				if(status.equalsIgnoreCase(WebService.RESPONSE_STATUS_PASS)){
					UserRegistrationFragment.isRegistered=true;
					MySharedPreferences sharedpreferences =new MySharedPreferences(getActivity());
					sharedpreferences.saveUserRegistrationStatus(WebService.REGISTERED_STATUS,UserRegistrationFragment.REGISTERED_YES);
					Toast.makeText(getActivity(), object.getString(WebService.RESPONSE_RESULT), Toast.LENGTH_SHORT).show();
					getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
					getActivity().finish();
					
				}else if(status.equalsIgnoreCase(WebService.RESPONSE_ERROR)){
					Toast.makeText(getActivity(), object.getString(WebService.RESPONSE_ERROR), Toast.LENGTH_SHORT).show();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
		}
		
	}

}
