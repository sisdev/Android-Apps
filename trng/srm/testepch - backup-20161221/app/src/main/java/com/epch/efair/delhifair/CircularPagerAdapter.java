package com.epch.efair.delhifair;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

public class CircularPagerAdapter extends PagerAdapter{

	private int[] pageIDsArray;
	private int count;
	public Context context;

	public CircularPagerAdapter(Context context ,final ViewPager datePager,final ViewPager eventPager, int... pageIDs) {
	    super();
	    this.context=context;
	    count=pageIDs.length;
	    pageIDsArray = new int[count];
	    for (int i = 0; i < count; i++) {
	        pageIDsArray[i ] = pageIDs[i];
	    }
	    
	    
	
	}
	public CircularPagerAdapter(Context context ,final ViewPager datePager, int... pageIDs) {
	    super();
	    this.context=context;
	    count=pageIDs.length;
	    pageIDsArray = new int[count];
	    for (int i = 0; i < count; i++) {
	        pageIDsArray[i ] = pageIDs[i];
	    }
	    
	    
	
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
	
}
