package com.assusoft.efair.epchfair.Fragments;

import java.util.ArrayList;
import java.util.List;

import com.epch.efair.delhifair.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.assusoft.eFairEmall.entities.EventInfo;

public class ConclaveAdapter extends BaseAdapter{
	 // Declare Variables
    Context mContext;
    LayoutInflater inflater;
    private List<EventInfo> eventInfoList = null;
    private ArrayList<EventInfo> eventInfoArraylist;
    FragmentManager fragMan;
    Fragment activeFragment;
    public static int premiumMembers=0;//Setting value from DatabaseHelper class
    boolean flag=true;// this flag is used to identify the which fragment is used this adapter ExhibitorsFragement
    //or SearchResultFragment true then ExhibitorFragment otherwise SearchResultFragment
    String strDate,startTime,endTime;
    public ConclaveAdapter(Context context, List<EventInfo> eventInfoList) {
        super();
    	mContext = context;
        this.eventInfoList = eventInfoList;
        inflater = LayoutInflater.from(mContext);
        this.eventInfoArraylist = new ArrayList<EventInfo>();
        this.eventInfoArraylist.addAll(eventInfoList);

        //Log.i("EFAIR","ExbListViewAdapter constructor executed and size "+Integer.toString(userlist.size()));
    }
 
    public class ViewHolder {
    	LinearLayout llConclave;
        TextView conclaveName;//ConclaveName
        TextView conclaveTime;//exhibitorLocation
    }
 
    @Override
    public int getCount() {
    	Log.i("EFAIR","i am in getcount"+Integer.toString(eventInfoList.size()));
        return eventInfoList.size();
    }
 
    @Override
    public EventInfo getItem(int position) {
    	Log.i("EFAIR","i am in getitem"+Integer.toString(eventInfoList.size()));
        return eventInfoList.get(position);
    }
 
    @Override
    public long getItemId(int position) {
    	Log.i("EFAIR","i am in getitemid"+Integer.toString(eventInfoList.size()));
        return position;
    }
    @SuppressLint("ResourceAsColor")
	@Override
    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        Log.i("EFAIR","Override getview method");
        if (view == null) {
        	Log.i("EFAIR"," if Override getview method");
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.conclave_list_item, null);
            // Locate the TextViews in listview_item.xml
            holder.conclaveName = (TextView) view.findViewById(R.id.tvConclaveName);
            holder.conclaveTime=(TextView) view.findViewById(R.id.tvConclaveStartTime);
            view.setTag(holder);
        } else {
        	Log.i("EFAIR"," else Override getview method");
            holder = (ViewHolder) view.getTag();
        }
        
           
        // Set the results into TextViews
         String conclaveName = eventInfoList.get(position).getEventName();
         String conclaveTime = eventInfoList.get(position).getStartTime();
         
        holder.conclaveName.setText(conclaveName);
        holder.conclaveTime.setText(conclaveTime);
    	if(eventInfoList.get(position).getModerater().equalsIgnoreCase("Not Available"))
    	{  
    		holder.conclaveName.setCompoundDrawables(null, null,null, null);
    	}else{
    		Drawable draw=mContext.getResources().getDrawable(R.drawable.click_icon);
    		draw.setBounds( 0, 0, 45, 45 );
    		holder.conclaveName.setCompoundDrawables(null, null,draw, null);
    		Log.i("EXPO", "Drawable");
    	}
        // Listen for ListView Item Click
        holder.conclaveName.setOnClickListener(new OnClickListener() {
 
            public void onClick(View v) {
                // Send single item click data to SingleItemView Class
            	if(eventInfoList.get(position).getModerater().equalsIgnoreCase("Not Available"))
            	{
            		return;
            	}
            	
 				FragmentTransaction ft = ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction();
 	            Bundle bundle=new Bundle();
		        bundle.putInt("EVENTID",eventInfoList.get(position).getEventId());
		        EventDetailsFragment eventD=new EventDetailsFragment();
		        eventD.setArguments(bundle);
                Log.i("BOOKFAIR", "get(position).getEventId()="+eventInfoList.get(position).getEventId());
                
                ft.replace(R.id.content_frame, eventD);
                ft.commit();
             
            }

			
        });
        
 
        return view;
    }
    
    
   
 
   

}
