package com.assusoft.efair.epchfair.Fragments;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.EnumSet;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.epch.efair.delhifair.R;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.assusoft.eFairEmall.util.MySharedPreferences;
import com.assusoft.efair.socialMediaFb.LinkedInConfig;
import com.assusoft.efair.socialMediaFb.LinkedinDialog;
import com.assusoft.efair.socialMediaFb.LinkedinDialog.OnVerifyListener;
import com.epch.efair.delhifair.Commons;
import com.epch.efair.delhifair.HomeActivity;
import com.google.code.linkedinapi.client.LinkedInApiClient;
import com.google.code.linkedinapi.client.LinkedInApiClientFactory;
import com.google.code.linkedinapi.client.enumeration.CompanyField;
import com.google.code.linkedinapi.client.enumeration.ProfileField;
import com.google.code.linkedinapi.client.oauth.LinkedInAccessToken;
import com.google.code.linkedinapi.client.oauth.LinkedInOAuthService;
import com.google.code.linkedinapi.client.oauth.LinkedInOAuthServiceFactory;
import com.google.code.linkedinapi.client.oauth.LinkedInRequestToken;
import com.google.code.linkedinapi.schema.Companies;
import com.google.code.linkedinapi.schema.DateOfBirth;
import com.google.code.linkedinapi.schema.Location;
import com.google.code.linkedinapi.schema.Person;
public class LinkedInFragment extends android.support.v4.app.Fragment{
	Button login;
	Button share;
	EditText et;
	TextView name;
	ImageView photo;
	String token;
	String linkedInAPIKey;
	String linkedSecretKey;
	LinkedInConfig config;
	public static final String OAUTH_CALLBACK_HOST = "calback";
    LinkedInOAuthService oAuthService;
    LinkedInApiClientFactory factory;
	LinkedInRequestToken liToken;
	LinkedInApiClient client;
	LinkedInAccessToken accessToken = null;
	OAuthConsumer consumer ;
	MySharedPreferences mySharedPreferences;
	ProgressDialog progressDialog;
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressLint("NewApi")
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.linked_in, container, false);
       /************************** */
		config=new LinkedInConfig(getActivity());
		linkedInAPIKey=config.linkedInAPIKey;
		linkedSecretKey=config.linkedSecretKey;
		oAuthService = LinkedInOAuthServiceFactory
	            .getInstance().createLinkedInOAuthService(
	            		linkedInAPIKey,linkedSecretKey,LinkedInConfig.scopeParams);
		factory = LinkedInApiClientFactory
				.newInstance(linkedInAPIKey,linkedSecretKey);
		//
		if( Build.VERSION.SDK_INT >= 9){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy); 
		}
		share = (Button) view.findViewById(R.id.share);
		name = (TextView) view.findViewById(R.id.name);
		et = (EditText) view.findViewById(R.id.et_share);
		login = (Button) view.findViewById(R.id.login);
		photo = (ImageView) view.findViewById(R.id.photo);

		login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				progressDialog = new ProgressDialog(getActivity());
				linkedInLogin();
			}
		});

		// share on linkedin
		share.setOnClickListener(new OnClickListener() {
			DefaultHttpClient httpclient;
			@Override
			public void onClick(View v) {
				String share = et.getText().toString();
				if (null != share && !share.equalsIgnoreCase("")) {
					//OAuthConsumer consumer = new CommonsHttpOAuthConsumer(linkedInAPIKey, linkedSecretKey);
				    //consumer.setTokenWithSecret(accessToken.getToken(), accessToken.getTokenSecret());
				    Log.i("EFAIR","accessToken.getToken():"+accessToken.getToken());
				    httpclient = new DefaultHttpClient();
					HttpPost post = new HttpPost("https://api.linkedin.com/v1/people/~/shares");
					
					try {
						consumer.sign(post);
					} catch (OAuthMessageSignerException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (OAuthExpectationFailedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (OAuthCommunicationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} // here need the consumer for sign in for post the share
					post.setHeader("content-type", "text/XML");
					String myEntity = "<share><comment>"+ share +"</comment><visibility><code>anyone</code></visibility></share>";
					try {
						post.setEntity(new StringEntity(myEntity));
						org.apache.http.HttpResponse response = httpclient.execute(post);
						Toast.makeText(getActivity(),
								"Shared sucessfully", Toast.LENGTH_SHORT).show();
						HttpEntity r_entity = response.getEntity();
				        String xmlString = EntityUtils.toString(r_entity);
						Log.i("WEB_DATA", "Responce1:"+xmlString);
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ClientProtocolException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else {
					Toast.makeText(getActivity(),
							"Please enter the text to share",
							Toast.LENGTH_SHORT).show();
				}
				
				/*String share = et.getText().toString();
				if (null != share && !share.equalsIgnoreCase("")) {
					client = factory.createLinkedInApiClient(accessToken);
					client.postNetworkUpdate(share);
					et.setText("");
					Toast.makeText(LinkedInSampleActivity.this,
							"Shared sucessfully", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(LinkedInSampleActivity.this,
							"Please enter the text to share",
							Toast.LENGTH_SHORT).show();
				}*/
				
              
			}
			
		});
		
		return view;
	}
	private void linkedInLogin() {
		
		LinkedinDialog d = new LinkedinDialog(getActivity(),
				progressDialog);
		d.show();
		// set call back listener to get oauth_verifier value
		d.setVerifierListener(new OnVerifyListener() {
			@Override
			public void onVerify(String verifier) {
				try {
					Log.i("LinkedinSample", "verifier: " + verifier);
					accessToken = LinkedinDialog.oAuthService.getOAuthAccessToken(LinkedinDialog.liToken,verifier);
					Log.i("LinkedinSample", "LinkedinDialog.liToken: " + LinkedinDialog.liToken);
					LinkedinDialog.factory.createLinkedInApiClient(accessToken);
					client = factory.createLinkedInApiClient(accessToken);
					// client.postNetworkUpdate("Testing by Mukesh!!! LinkedIn wall post from Android app");
					Log.i("LinkedinSample",
							"ln_access_token: " + accessToken.getToken());
					Log.i("LinkedinSample",
							"ln_access_token_secret: " + accessToken.getTokenSecret());
					token=accessToken.getToken();
					Person p = client.getProfileForCurrentUser();
					//Person p = client.getProfileById("Akshaymca2013@gmail.com");
					name.setText("Welcome " + p.getFirstName() + " "
							+ p.getLastName());
					name.setVisibility(0);
					login.setVisibility(4);
					share.setVisibility(0);
					et.setVisibility(0);
					/********************************/
					
					condidateData("");
				} catch (Exception e) {
					Log.i("LinkedinSample", "error to get verifier");
					e.printStackTrace();
				}
			}
		});
	
		// set progress dialog
		progressDialog.setMessage("Loading...");
		progressDialog.setCancelable(true);
		progressDialog.show();
	}
	public void condidateData(String id)
	{
		
		Person profile = client.getProfileForCurrentUser(EnumSet.of(
                ProfileField.ID, ProfileField.FIRST_NAME,
                ProfileField.LAST_NAME, ProfileField.HEADLINE,
                ProfileField.INDUSTRY, ProfileField.PICTURE_URL,
                ProfileField.DATE_OF_BIRTH, ProfileField.LOCATION_NAME,
                ProfileField.MAIN_ADDRESS, ProfileField.LOCATION_COUNTRY,ProfileField.EDUCATIONS
                ,ProfileField.LANGUAGES,ProfileField.EDUCATIONS_DEGREE,ProfileField.TWITTER_ACCOUNTS,
                ProfileField.SUMMARY,ProfileField.SPECIALTIES,ProfileField.SKILLS,ProfileField.SKILLS_PROFICIENCY_NAME
                ,ProfileField.API_STANDARD_PROFILE_REQUEST));
		
      System.out.println("PersonID : " + profile.getId());
      System.out.println("Name : " + profile.getFirstName() + " "
              + profile.getLastName());
      System.out.println("Headline : " + profile.getHeadline());
      System.out.println("Industry : " + profile.getIndustry());
      System.out.println("Picture : " + profile.getFirstName());
     
      DateOfBirth dateOfBirth = profile.getDateOfBirth();
      System.out.println("DateOfBirth: " + dateOfBirth.getDay() + "/"
              + dateOfBirth.getMonth() + "/" + dateOfBirth.getYear());
      System.out.println("MAin Address : " + profile.getMainAddress());
      Location location = profile.getLocation();
      System.out.println("Location:" + location.getName() + " - "
              + location.getCountry().getCode());
     
      System.out.println("getLanguages : " + profile.getLanguages().getLanguageList().size());
      System.out.println("getEducations : " + profile.getEducations().getEducationList().get(0).getActivities());
      System.out.println("getTwitterAccounts : " + profile.getTwitterAccounts());
      System.out.println("getSummary() : " + profile.getSummary());
      System.out.println("getSpecialties : " + profile.getSpecialties());
      System.out.println("getSkills : " + profile.getSkills().getSkillList().size());
      //System.out.println("getSkills : " + profile.get);
      Companies following= client.getFollowedCompanies(EnumSet.of(CompanyField.COMPANY_TYPE,CompanyField.INDUSTRY,
    		  CompanyField.NAME,CompanyField.DESCRIPTION));
      System.out.println("following: " + following.getCompanyList().size());
      if(Commons.mySharedPreferences!=null)
      {
    	  mySharedPreferences=Commons.mySharedPreferences;
      }else{
    	  mySharedPreferences=new MySharedPreferences(getActivity());
    	  Commons.mySharedPreferences=mySharedPreferences;
      }
      mySharedPreferences.saveFollowingSize(following.getCompanyList().size());//saving following size
      for(int i=0;i<following.getCompanyList().size();i++)
      {
    	  System.out.println("CompanyName: "+following.getCompanyList().get(i).getName());
    	  System.out.println("CompanyType: "+following.getCompanyList().get(i).getCompanyType().getName());
    	  System.out.println("Industry: "+following.getCompanyList().get(i).getIndustry());
    	  mySharedPreferences.saveFollowing("companyName"+i,following.getCompanyList().get(i).getName());//saving companyName
      }
      if(HomeActivity.isLinkedInLogin)
      {  HomeActivity.isLinkedInLogin=false;
        Bundle bundle=new Bundle();
		FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
      SearchResultFragment fragment = new SearchResultFragment();
      bundle.putString("SEARCH_STRING","matchMaking");
      bundle.putString("SEARCH_OPTION","matchMaking");
      ft.replace(R.id.content_frame, fragment);
      fragment.setArguments(bundle);
      ft.commit();
      HomeActivity.goForSearchResult=2;
      HomeActivity.isMatchMaking=true;
      }
	}
	
}
