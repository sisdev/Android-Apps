package com.assusoft.efair.epchfair.googleMap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.epch.efair.delhifair.Commons;
import com.epch.efair.delhifair.ImageUtils;
import com.epch.efair.delhifair.R;
import com.epch.efair.delhifair.StorageHelper;

public class VenueMapLayoutFragment extends Fragment{
    WebView webview; 
    String imageFileName;
    String imageName;
    ImageUtils imageUtil;
    boolean isImageDownloaded=false;
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
    	View v=inflater.inflate(R.layout.venue_map_layout,container,false);
    	webview=(WebView) v.findViewById(R.id.webView_venue_map_layout);
    	WebSettings webviewSettings=webview.getSettings();
		
    	/*AdView adView = new AdView(getActivity(), AdSize.SMART_BANNER, Commons.admobToken);    
		FrameLayout layout = (FrameLayout)v.findViewById(R.id.AdsFrameLayout);    
		layout.addView(adView); 
		AdRequest request = Commons.GetAds(getActivity());
		adView.loadAd(request);*/
		webviewSettings.setBuiltInZoomControls(true);
		imageUtil=new ImageUtils(getActivity());
		Bundle bundle=getArguments();
		imageFileName=bundle.getString("IMAGEFILENAME");
		imageName=bundle.getString("MAP_IMAGE_NAME");
		     if(StorageHelper.isExternalStorageAvailableAndWriteable())
				{
		    	 isImageDownloaded=imageUtil.isFileAvailabeInExternalStorage(imageName);
				}else{
				 isImageDownloaded=imageUtil.isFileAvailabeInInternalStorage(imageName);
				}
		     
		if(!isImageDownloaded)
		{
			new Commons().MyAlertDialog(getActivity());
		}
		
		webview.loadDataWithBaseURL("", 
				"<img src='"+imageFileName+"' />", "text/html", "utf-8", null);
		/*webview.loadDataWithBaseURL("file:///android_res/drawable/", 
				"<img src='"+imageFileName+"' />", "text/html", "utf-8", null);*/
    	return v;
    }
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
//		Commons.getMyTracker(getActivity(), "VenueMapLayoutFragment");
	}
}
