package com.assusoft.efair.epchfair.Fragments;

import java.io.File;

import android.content.Context;
import android.os.Environment;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.assusoft.eFairEmall.databaseMaster.DatabaseHelper;
import com.epch.efair.delhifair.EFairEmallApplicationContext;
import com.epch.efair.delhifair.ImageAsyncTask;
import com.epch.efair.delhifair.R;

public class SeminarCalendarPager extends PagerAdapter{
	private int[] pageIDsArray;
	private int count;
	private Context context;
	private String base;
	private DatabaseHelper helper;

	public SeminarCalendarPager(Context context ,final ViewPager eventPager,final ViewPager datePager, int... pageIDs) {
	    super();
	    this.context=context;
	    count=pageIDs.length;
	    pageIDsArray = new int[count];
	    for (int i = 0; i < count; i++) {
	        pageIDsArray[i ] = pageIDs[i];
	    } 
	 helper = EFairEmallApplicationContext.getDatabaseHelper();
	   base = "file://"+Environment.getExternalStorageDirectory().getAbsolutePath()
			   +File.separator+ImageAsyncTask.FOLDER_NAME
			   +File.separator;
	}

	public int getCount() {
	    return count;
	}

	public Object instantiateItem(View container, int position) {
		Log.i("NBTI","view "+position);
	    LayoutInflater inflater = (LayoutInflater) container.getContext()
	            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    int pageId = pageIDsArray[position];
	    View view = inflater.inflate(pageId, null);
	    ((ViewPager) container).addView(view, 0);
	    WebView webview= (WebView) view.findViewById(R.id.mybrowser);
	    helper.openDatabase(DatabaseHelper.READMODE);
	    // if event view pager
	    switch (position) {
		case 0:
			  webview.loadUrl(base+helper.getLinkURLByLinkName("seminar_18-04-15"));
			  break;
			  
		case 1:
			  webview.loadUrl(base+helper.getLinkURLByLinkName("seminar_17-04-15"));
			  break;
			  
		case 2:
			  webview.loadUrl(base+helper.getLinkURLByLinkName("seminar_16-04-15"));
			  break;
			  
		/*case 3:
			  webview.loadUrl(base+helper.getLinkURLByLinkName("seminar_23-02-16"));
			  break;*/
			  
		/*case 4:
			  webview.loadUrl(base+helper.getLinkURLByLinkName("seminar_18-10-15"));
			  break;*/
			  
    	default:
			break;
		}
	    
	    
	    
	    return view;
	}

	@Override
	public void destroyItem(View container, int position, Object object) {
	    ((ViewPager) container).removeView((View) object);
	}

	@Override
	public void finishUpdate(View container) {
	    // TODO Auto-generated method stub
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
	    return view == ((View) object);
	}

	@Override
	public void restoreState(Parcelable state, ClassLoader loader) {
	    // TODO Auto-generated method stub
	}

	@Override
	public Parcelable saveState() {
	    // TODO Auto-generated method stub
	    return null;
	}

	@Override
	public void startUpdate(View container) {
	    // TODO Auto-generated method stub
	}
	private void nothingScheduled(ListView listview){
		String noresultFound[]=new String[]{"Nothing Scheduled."};
		
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(context,R.layout.single_list_item, noresultFound);
		listview.setAdapter(adapter);
		
	}
}
