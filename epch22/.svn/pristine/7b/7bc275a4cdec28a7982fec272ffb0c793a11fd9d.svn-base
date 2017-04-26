package com.assusoft.efair.epchfair.Fragments;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.assusoft.tmxloader.library.TouchImageView;
import com.epch.efair.delhifair.HomeAcitityFirst;
import com.epch.efair.delhifair.HomeActivity;
import com.epch.efair.delhifair.R;

public class ProductImageLargeViewFragment extends Fragment{

	private View		view;
	/*private WebView		webView;*/
	private Bundle		bundle;
	private String		imageSRC;
	private Bitmap		bitmap;
	Options options;
	/*private String		html;

	private String mime = "text/html";
	private String encoding = "utf-8";*/
	private TouchImageView img;
	
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view	= inflater.inflate(R.layout.venue_map_with_location, container, false);
		/*webView	= (WebView) view.findViewById(R.id.mybrowser);*/
		img		= (TouchImageView) view.findViewById(R.id.imgMap1);
		
		FrameLayout banner=(FrameLayout) view.findViewById(R.id.AdsFrameLayout);
		HomeAcitityFirst.adLoader.showBanner(banner);
//		AnimationDrawable amin=(AnimationDrawable) banner.getBackground();
//		   amin.start();
		
		bundle	= getArguments();
		imageSRC= bundle.getString("IMAGESRC");
		
		Log.i("WEB_DATA","image url "+imageSRC);
		/*html	= "<!DOCTYPE html>"+
					"<html><head><meta charset=\"UTF-8\">"+
					"<meta name=\"viewport\" content=\"width=device-width; user-scalable=0;\" />"+
					"<head><title>Product Gallery</title>"+
					"<body><div>"+
					"<IMG SRC=\""+imageSRC+"\" />"+			//imageSRC is dynamic getting from bundle.
					"</div></body>"+
					"</html>";*/
		/*webView.getSettings().setJavaScriptEnabled(true);
		webView.loadData(html, mime, encoding);*/
		
		DisplayMetrics dimension = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dimension);
		int displayWidth = dimension.widthPixels;
		int displayHieght = dimension.heightPixels;
		
		Log.i("WEB_DATA","options value W --> "+displayWidth+"\n H --> "+displayHieght);
		
		options	= new Options();
		options.inJustDecodeBounds = false;
		
		options.inSampleSize = calculateOption(options.outWidth, options.outHeight, displayWidth, displayHieght);
		new ImageLoader().execute();
		
		Log.i("WEB_DATA","Bitmap value --> "+ bitmap);
		return view;
	}
	@Override
	public void onResume() {
		super.onResume();
		HomeActivity.setTitle("Product Gallery");
	}
	public static int calculateOption(int w, int h, int disW, int disH){
		final float srcAspect = (float)disW / (float)disH;
        final float dstAspect = (float)w / (float)h;
        
        if(srcAspect>dstAspect){
        	return w/disW;
        }
        else{
        	return h/disH;
        }
	}
	public class ImageLoader extends AsyncTask<Void, Void, Bitmap>{
		ProgressDialog dialog;
		Bitmap bitmap = null;
		InputStream in= null;
		private Bitmap loadImage(String URL, BitmapFactory.Options options){
			try{
				in		= OpenHttpConnection(URL);
				bitmap	= BitmapFactory.decodeStream(in, null, options);
			}catch(Exception e){
				e.printStackTrace();
			}
			return bitmap;
		}
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog	= new ProgressDialog(getActivity());
			dialog.setMessage("Please wait...");
			dialog.setCanceledOnTouchOutside(true);
			dialog.show();
		}

		@Override
		protected Bitmap doInBackground(Void... params) {
			bitmap = loadImage(imageSRC, options);
			return bitmap;
		}
		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			try{
				dialog.dismiss();
				img.setImageBitmap(bitmap);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}
	private InputStream OpenHttpConnection(String strURL) throws IOException{
		InputStream inputStream = null;
		URL url = new URL(strURL);
		URLConnection conn = url.openConnection();

		try{
			HttpURLConnection httpConn = (HttpURLConnection)conn;
			httpConn.setRequestMethod("GET");
			httpConn.connect();
			Log.i("WEB_DATA","Http connection \n"+httpConn);

			if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				inputStream = new BufferedInputStream(httpConn.getInputStream());
			}
		}
		catch (Exception ex)
		{
			Log.i("WEB_DATA","Exception in  \n"+ex);
		}
		return inputStream;
		}
}
