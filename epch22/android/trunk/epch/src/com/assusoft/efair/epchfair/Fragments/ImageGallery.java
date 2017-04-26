package com.assusoft.efair.epchfair.Fragments;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.epch.efair.delhifair.HomeActivity;
import com.epch.efair.delhifair.R;

public class ImageGallery extends Fragment{
	
	private static Context mContext;
	private static String imageString;
	private String link;				////http://ihgfspringfair.epch.in/(new_date -11/02/2016)
	private String	imagesLink = "http://ihgfdelhifair.epch.in/epch/response/?e=";
	private WebView myBrowser;
	private ProgressBar progressBar;
	
	private int linkFlag = 0;
	private String notImage = "<html><body></br></br><center><h2>No image found.</h2></center></body></html>";
	
	String mime = "text/html";
	String encoding = "utf-8";
	String html1 = "<!DOCTYPE html>"+
					"<html><head><meta charset=\"UTF-8\">"+
					"<meta name=\"viewport\" content=\"width=device-width; user-scalable=0;\" />"+
					"<head><title>Product Gallery</title>"+
					"<script language=\"javascript\">"+
					"function showAndroidToast(imageUrl){"+
					"AndroidFunction.showToast(imageUrl);}"+
					"</script>"+
					"</head><body>";
	
	@SuppressLint({ "SetJavaScriptEnabled", "JavascriptInterface" })
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view		= inflater.inflate(R.layout.web_open, container, false);		
		myBrowser		= (WebView) view.findViewById(R.id.mybrowser);
		progressBar	= (ProgressBar) view.findViewById(R.id.progressBar1); 
		 
		mContext	= getActivity();
		
		Bundle bundle 	 = getArguments();
		link =imagesLink+bundle.getString("REFNO");
		Log.i("WEB_DATA","[URL FOR IMAGE JSON RESTFUL] "+link);
		new ImageUrl().execute();
		
	        final MyJavaScriptInterface myJavaScriptInterface
	        	= new MyJavaScriptInterface(getActivity());
	        myBrowser.addJavascriptInterface(myJavaScriptInterface, "AndroidFunction");	        
	        myBrowser.getSettings().setJavaScriptEnabled(true); 
	        myBrowser.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
	        
		myBrowser.setWebViewClient(new WebViewClient() {

			@Override
			public void onPageFinished(WebView view, String url) {
				progressBar.setVisibility(View.GONE);
				super.onPageFinished(view, url);
			}
			
		});
		return view;
	}
	
	
	@Override
	public void onResume() {
		super.onResume();
		HomeActivity.setTitle("Product Gallery");
	}


	public class ImageUrl extends AsyncTask<Void, Void, String>{
		
		String data="";
		String getASCIIContentFromEntity(HttpEntity entity) throws IllegalStateException, IOException {
			InputStream in = entity.getContent();
			StringBuffer out = new StringBuffer();
			int n = 1;
			while (n>0) {
				byte[] b = new byte[4096];
				n =  in.read(b);

				if (n>0)
					out.append(new String(b, 0, n));
			}

			
			return out.toString();
		}
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressBar.setVisibility(View.VISIBLE);
		}

		@Override
		protected String doInBackground(Void... params) {

			Log.i("EFAIR", "Image url--> "+link);
			HttpClient		httpClient	= new DefaultHttpClient();
			HttpContext		httpContext	= new BasicHttpContext();
			HttpGet			httpGet		= new HttpGet(link);
			
			try {
					HttpResponse response = httpClient.execute(httpGet, httpContext);
					HttpEntity entity = response.getEntity();

					imageString = getASCIIContentFromEntity(entity);

			} catch (Exception e) {
				Log.i("DATA","Data in exception-->\n"+imageString);
			   return e.getLocalizedMessage();
			}
			Log.i("DATA","Data-->\n"+imageString);
			
			try {
				JSONObject jsonObj	 = new JSONObject(imageString);
				JSONArray  arrJson	 = jsonObj.getJSONArray("img_url");
				Log.i("DATA","ArrayLenth-->"+arrJson.length());  
				if(arrJson.length()<=0){
					linkFlag = 1;
				}
				for(int i=0;i<arrJson.length();i++){
					if(i<arrJson.length())
					data +="<div  style=\" width:50%; height:33%; float:left; \">"+
							"<img src=\""+arrJson.getString(i)+"\" width = 100% height = 33% border=\"2\" onClick=\"showAndroidToast(this.src)\" /></div>";
					if((i+1)<arrJson.length())
						data +="<div  style=\" width:50%; height:33%; float:right; \">"+
								"<img src=\""+arrJson.getString(++i)+"\" width = 100% height = 33% border=\"2\" onClick=\"showAndroidToast(this.src)\" /></div>";
				}
				data ="<div>"+data+"</div>"+"</body></html>";
				Log.i("EFAIR","html"+"data");
			} catch (JSONException e) {
				Log.i("DATA","Exception in JSON Parsing.."+"data");
				linkFlag = 1;
				e.printStackTrace();
			}
			
			return data;
		}
		protected void onPostExecute(final String results) {
			try{
				if(linkFlag==1){
					Log.i("DATA","second html.."+notImage);
					myBrowser.loadData(notImage, mime, encoding);
				}
				if (results!=null && linkFlag!=1) { 
					Log.i("DATA","second html.."+notImage);
	                myBrowser.loadData(html1+results, mime, encoding);				
				}
				linkFlag = 0;
				Log.i("DATA","onPost-->\n"+html1+results);  
			}catch(Exception e){
				e.printStackTrace();
				progressBar.setVisibility(View.GONE);
			}
		}
	}
	public static void loadImageInLagerView(String src){
		Bundle bundle	= new Bundle();
		bundle.putString("IMAGESRC", src);
		ProductImageLargeViewFragment  fragment = new ProductImageLargeViewFragment();
		fragment.setArguments(bundle);
		((FragmentActivity)mContext).getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.content_frame, fragment)
		.addToBackStack(null)
		.commit();
		Log.i("WEB_DATA","image loading");
	}
	public class MyJavaScriptInterface {
		Context mContext;

	    MyJavaScriptInterface(Context c) {
	        mContext = c;
	    }
	    @JavascriptInterface
	    public void showToast(String imageUrl){
	    	
	    	Log.i("WEB_DATA","Image url-->\n"+imageUrl);
	    	loadImageInLagerView(imageUrl);
	        //Toast.makeText(mContext, imageUrl, Toast.LENGTH_SHORT).show();
	    }
	}
}
