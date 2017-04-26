package com.assusoft.efair.epchfair.Fragments;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.assusoft.eFairEmall.databaseMaster.DatabaseHelper;
import com.assusoft.eFairEmall.entities.ExhibitorLocation;
import com.assusoft.eFairEmall.entities.Exhibitors;
import com.assusoft.eFairEmall.entities.VenueMap;
import com.epch.efair.delhifair.EFairEmallApplicationContext;
import com.epch.efair.delhifair.HomeActivity;
import com.epch.efair.delhifair.R;

public class ToFromListAdapter extends BaseAdapter  {
 
    // Declare Variables
    Context mContext;
    LayoutInflater inflater;
    private List<Exhibitors> userlist = null;
    private ArrayList<Exhibitors> arraylist;
    private EditText searchBoxId;
    List<Object> users;
    FragmentManager fragMan;
    Fragment activeFragment;
    ExhibitorDetailFragment exhDetailsfragment;
    public static int premiumMembers=0;//Setting value from DatabaseHelper class
    boolean flag=true;// this flag is used to identify the which fragment is used this adapter ExhibitorsFragement
    //or SearchResultFragment true then ExhibitorFragment otherwise SearchResultFragment
    String strDate,startTime,endTime;
    private TextView source;
	private TextView destination;
	private int counter=0;
	protected boolean isDestOrSourceSelected;
	To_From_Custom tofrom;
	DatabaseHelper dh;
	private boolean isToast=false;
    public ToFromListAdapter(Context context, List<Exhibitors> userList1,To_From_Custom tofrom,TextView source,TextView destination) {
        super();
        
    	mContext = context;
        this.userlist = userList1;
        this.tofrom=tofrom;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<Exhibitors>();
        this.arraylist.addAll(userlist);
        this.source=source;
        this.destination=destination;
        exhDetailsfragment = new ExhibitorDetailFragment();
        this.dh=EFairEmallApplicationContext.getDatabaseHelper();
        Log.i("EFAIR","ExbListViewAdapter constructor executed and size "+Integer.toString(userlist.size()));
    }
 
    public class ViewHolder {
    	LinearLayout llExhibiotrs;
        TextView exhibitorName;//exhibitorName
        TextView exhibitorLoc;//exhibitorLocation
        ImageButton eventIcon;
    }
 
    @Override
    public int getCount() {
    	//Log.i("EFAIR","i am in getcount"+Integer.toString(userlist.size()));
        return userlist.size();
    }
 
    @Override
    public Exhibitors getItem(int position) {
    	//Log.i("EFAIR","i am in getitem"+Integer.toString(userlist.size()));
        return userlist.get(position);
    }
 
    @Override
    public long getItemId(int position) {
    	//Log.i("EFAIR","i am in getitemid"+Integer.toString(userlist.size()));
        return position;
    }
    @SuppressLint("ResourceAsColor")
	@Override
    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
       // Log.i("EFAIR","Override getview method");
        if (view == null) {
        	Log.i("EFAIR"," if Override getview method");
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.exb_list_item, null);
            // Locate the TextViews in listview_item.xml
            holder.exhibitorName = (TextView) view.findViewById(R.id.tvlistitemExb);
            holder.exhibitorLoc=(TextView) view.findViewById(R.id.tvlistitemExbLoc);
            holder.eventIcon= (ImageButton) view.findViewById(R.id.btnEventLogo);
            holder.llExhibiotrs=(LinearLayout) view.findViewById(R.id.llListitemExb);
            view.setTag(holder);
        } else {
        	//Log.i("EFAIR"," else Override getview method");
            holder = (ViewHolder) view.getTag();
        }
         /*if(position<premiumMembers)
             {
        	 holder.exhibitorName.setTextColor(Color.BLACK);
        	 holder.exhibitorLoc.setTextColor(Color.BLACK);
        	 Log.i("EXPO"," txt color changed blue=");
             }else
             {
            	 holder.exhibitorName.setTextColor(Color.BLUE); 
            	 holder.exhibitorLoc.setTextColor(Color.BLUE);
            	 Log.i("EXPO"," txt color changed wight=");
            	
             }*/
           
        // Set the results into TextViews
         String[] parts = userlist.get(position).getExhibitorName().split("~~");
         String part1 = parts[0]; // Name
         String part2 = parts[1]; // location
         holder.exhibitorName.setText(parts[0]);
         holder.exhibitorLoc.setText(parts[1]);
 
        // Listen for ListView Item Click
        holder.llExhibiotrs.setOnClickListener(new OnClickListener() {
 
            

			public void onClick(View v) {
				
				HomeActivity.hideSoftKeyboard((Activity)mContext);
                // Send single item click data to SingleItemView Class
            	String[] parts = userlist.get(position).getExhibitorName().split("~~");
                String part1 = parts[0]; // Name
                String part2 = parts[1]; // location
                String exbLoc[];
            	if(part1.contains("Hall")|| part1.contains("Mart")){
            		exbLoc=part1.split("\\,");
            	}else{
            		exbLoc=part2.split("\\,");
            	}
            	String stallNo="";
            	if(exbLoc.length>1){
            		String stall[]=exbLoc[1].split("\\.");
            		if(stall.length>1){
            			stallNo=stall[1].trim();
            		}
            	}
            	if(!dh.isExhibitorLocationExist(""+userlist.get(position).getExhibitorId(),stallNo)){
					Toast.makeText(mContext, "Unable to find location,please select another exhibitor.",Toast.LENGTH_SHORT).show();
				    return;
				}
            	if(counter==0 && !isDestOrSourceSelected){
    				source.setText(" From : "+part1);
    				isDestOrSourceSelected=true;
    				HomeActivity.setTitle("TO Location");
    				tofrom.setSelectedExhibitor(userlist.get(position).getExhibitorId(), stallNo, true);
    				//Toast.makeText(getActivity(), ""+userlist.get(arg2).getExhibitorId(),Toast.LENGTH_LONG).show();
    			}
            	
            	else if(counter==1 && isDestOrSourceSelected){
    				destination.setText(" To : "+part1);
    				isDestOrSourceSelected=true;
    				
    				tofrom.setSelectedExhibitor(userlist.get(position).getExhibitorId(), stallNo, false);
    			}else if( !isDestOrSourceSelected){
    				source.setText(" From : "+part1);
    				
    				tofrom.setSelectedExhibitor(userlist.get(position).getExhibitorId(), stallNo, true);
    			}else{
    				destination.setText(" To : "+part1);
    				
    				tofrom.setSelectedExhibitor(userlist.get(position).getExhibitorId(), stallNo, false);
    			}
    			counter++;
              
            }

			
        });
        
        holder.eventIcon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String[] parts = userlist.get(position).getExhibitorName().split("~~");
                String part1 = parts[0]; // Name
                String part2 = parts[1]; // location
            	String exbLoc[];
            	if(part1.contains("Hall")|| part1.contains("Mart")){
            		exbLoc=part1.split("\\,");
            	}else{
            		exbLoc=part2.split("\\,");
            	}
            	String stallNo="";
            	if(exbLoc.length>1){
            		String stall[]=exbLoc[1].split("\\.");
            		if(stall.length>1){
            			stallNo=stall[1].trim();
            		}
            	}
            	Log.i("EXPO","exb stall No "+stallNo);
				DatabaseHelper dbHelper=EFairEmallApplicationContext.getDatabaseHelper();
                dbHelper.openDatabase(DatabaseHelper.READMODE);
        		users=dbHelper.getDetailOfSelectedExibitor(Integer.toString(userlist.get(position).getExhibitorId()),stallNo);
        		ExhibitorLocation location=(ExhibitorLocation) users.get(0);
    			VenueMap vmap=(VenueMap) users.get(1);
    			String xLocation=location.getxLocation();
    			String yLocation=location.getyLocation();
    			String fileName=vmap.getFilePath();
    			String hallNo=vmap.getHallNo().trim();
    			
				FragmentTransaction ft = ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction();
		        Bundle bundle=new Bundle();
		        bundle.putString("XLOCATION",xLocation);
		        bundle.putString("YLOCATION",yLocation);
		        bundle.putString("FILEPATH",fileName);
		        bundle.putInt("EXHIBITORID",userlist.get(position).getExhibitorId());
		        Log.i("EXPO", "fileName by db:"+fileName);
		       // HomeActivity.venueMapWithLocationOnBack=1;
		        VenueMapWithLocation fragment = new VenueMapWithLocation();
		        
		        fragment.setArguments(bundle);
		        ft.replace(R.id.content_frame, fragment,"Products");
		        ft.addToBackStack(null);
		        ft.commit();
			}
		});
 
        return view;
    }
    
    
  
 
    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        userlist.clear();
        if (charText.length() == 0) {
            userlist.addAll(arraylist);
            isToast=false;
            tofrom.hideCacelIcon();
        }
        else
        {
            for (Exhibitors user : arraylist)
            {
                if (user.getExhibitorName().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    userlist.add(user);
                }
            }
        }
        notifyDataSetChanged();
        if(userlist.size()==0 && (!isToast)){
			 Toast toast=Toast.makeText(mContext,"No results found,try a different search",Toast.LENGTH_LONG);
			 toast.setGravity(Gravity.TOP,20,20);
			 toast.show();
			 isToast=true;
		 }
    }
    
  public void setSelection(boolean isDestOrSourceSelected){
	  this.isDestOrSourceSelected=isDestOrSourceSelected;
  }
 
}


