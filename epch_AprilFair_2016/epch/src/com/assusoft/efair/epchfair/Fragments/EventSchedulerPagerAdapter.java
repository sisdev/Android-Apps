package com.assusoft.efair.epchfair.Fragments;

import com.epch.efair.delhifair.R;
import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class EventSchedulerPagerAdapter extends PagerAdapter{

	private int[] pageIDsArray;
	private int count;
	private Context context;

	public EventSchedulerPagerAdapter(Context context ,final ViewPager eventPager,final ViewPager datePager, int... pageIDs) {
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
	    
	    // if event view pager
	    switch (position) {
		case 0:
			  ListView lv=(ListView) view.findViewById(R.id.listViewEventDay_1);
			  if(Planner.dayInfo.get(0).size()==0){
				  nothingScheduled(lv);
				  break;
			  }
			  Log.i("LISTVIEW","else  "+Planner.dayInfo.get(0).size());
			  PlannerListAdapter adapter=new PlannerListAdapter(context,Planner.dayInfo.get(0),0);
			  lv.setAdapter(adapter);
			  break;
		case 1:
			  ListView lv1=(ListView) view.findViewById(R.id.listViewEventDay_2);
			  if(Planner.dayInfo.get(1).size()<=0){
				  Log.i("LISTVIEW","size  "+Planner.dayInfo.get(0).size());
				  nothingScheduled(lv1);
				  break;
			  }
			  Log.i("LISTVIEW","else  "+Planner.dayInfo.get(1).size());
			  PlannerListAdapter adapter1=new PlannerListAdapter(context,Planner.dayInfo.get(1),0);
			  lv1.setAdapter(adapter1);
			  break;
			  
		case 2:
			  ListView lv2=(ListView) view.findViewById(R.id.listViewEventDay_3);
			  if(Planner.dayInfo.get(2).size()==0){
				  Log.i("LISTVIEW","size  "+Planner.dayInfo.get(2).size());
				  nothingScheduled(lv2);
				  break;
			  }
			  Log.i("LISTVIEW","else  "+Planner.dayInfo.get(2).size());
			  PlannerListAdapter adapter2=new PlannerListAdapter(context,Planner.dayInfo.get(2),0);
			  lv2.setAdapter(adapter2);
			  break;
			  
		case 3:
			  ListView lv3=(ListView) view.findViewById(R.id.listViewEventDay_4);
			  if(Planner.dayInfo.get(3).size()==0){
				  nothingScheduled(lv3);
				  break;
			  }
			  Log.i("LISTVIEW","else  "+Planner.dayInfo.get(3).size());
			  PlannerListAdapter adapter4=new PlannerListAdapter(context,Planner.dayInfo.get(3),0);
			  lv3.setAdapter(adapter4);
			  break;
			  
		/*case 4:
			  ListView lv4=(ListView) view.findViewById(R.id.listViewEventDay_5);
			  if(Planner.dayInfo.get(4).size()==0){
				  nothingScheduled(lv4);
				  break;
			  }
			  PlannerListAdapter adapter5=new PlannerListAdapter(context,Planner.dayInfo.get(4),0);
			  lv4.setAdapter(adapter5);
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
