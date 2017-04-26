package com.assusoft.efair.epchfair.Fragments;

import java.util.ArrayList;
import java.util.List;

import com.epch.efair.delhifair.R;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.assusoft.eFairEmall.databaseMaster.DatabaseHelper;
import com.assusoft.eFairEmall.entities.Appointment;
import com.assusoft.eFairEmall.entities.ExhibitorLocation;
import com.assusoft.eFairEmall.entities.VenueMap;
import com.epch.efair.delhifair.EFairEmallApplicationContext;

public class PlannerListAdapter extends BaseAdapter  {
 
    // Declare Variables
    Context mContext;
    LayoutInflater inflater;
    private List<Appointment> eventlist = null;
    private ArrayList<Appointment> arraylist;
    List<Object> users;
    int pagePosition;//this variable tells that which page served by this adapter class
 
    public PlannerListAdapter(Context context, List<Appointment> userList1,int pagePosition) {
        super();
    	mContext = context;
        this.eventlist = userList1;
        this.pagePosition=pagePosition;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<Appointment>();
        this.arraylist.addAll(eventlist);
        Log.i("EFAIR","ExbListViewAdapter constructor executed and size "+Integer.toString(eventlist.size()));
    }
 
    public class ViewHolder {
        
        TextView exbName;
        
        LinearLayout llExhibiotrs;
        TextView exhibitorName;//exhibitorName
        TextView exhibitorLoc;//exhibitorLocation
        ImageButton eventIcon;
        
    }
    
   
    @Override
    public int getCount() {
    	Log.i("EFAIR","i am in getcount"+Integer.toString(eventlist.size()));
        return eventlist.size();
    }
 
    @Override
    public Appointment getItem(int position) {
    	Log.i("EFAIR","i am in getitem"+Integer.toString(eventlist.size()));
        return eventlist.get(position);
    }
 
    @Override
    public long getItemId(int position) {
    	Log.i("EFAIR","i am in getitemid"+Integer.toString(eventlist.size()));
        return position;
    }
    @Override
    public int getItemViewType(int position) {
    	
    	/*if(eventlist.get(position).getEventId()==0){
    		return VIEW_TYPE_LIST_HEADER;
    		
    	}else{
    		return VIEW_TYPE_LIST_ITEM;
    	}*/
    	return super.getItemViewType(position);
    }
    @Override
    public int getViewTypeCount() {
    	// TODO Auto-generated method stub
    	return 1;
    }
    
   
    @Override
    public View getView(final int position, View view, ViewGroup parent) {
    	final ViewHolder holder;
        Log.i("EFAIR","Override getview method");
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
        	Log.i("EFAIR"," else Override getview method");
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
         String[] parts = eventlist.get(position).getExhibitorName().split("~~");
         String part1 = parts[0]; // Name
         String part2 = parts[1]; // location
        holder.exhibitorName.setText(parts[0]);
        holder.exhibitorLoc.setText(parts[1]);
 
        // Listen for ListView Item Click
        holder.llExhibiotrs.setOnClickListener(new OnClickListener() {
 
            public void onClick(View v) {
               /* // Send single item click data to SingleItemView Class
 				FragmentTransaction ft = ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction();
 		      
		        ExhibitorDetailFragment exhDetailsfragment=new ExhibitorDetailFragment();
		        exhDetailsfragment.setArguments(bundle);
                Log.i("BOOKFAIR", "get(position).getExhibitorId()="+eventlist.get(position).getEhxibitorId());
                
                ft.replace(R.id.content_frame, exhDetailsfragment);
                ft.addToBackStack(null);
                ft.commit();*/
            	String[] parts = eventlist.get(position).getExhibitorName().split("~~");
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
        	
        		
        		ExhibitorDetailFragment fragment = new ExhibitorDetailFragment();
        		Bundle bundle=new Bundle();
  		        bundle.putInt("EXHIBITORID",eventlist.get(position).getEhxibitorId());
  		        bundle.putString("STALLNO",stallNo);
            
        		showOptionDialog(fragment, bundle,eventlist.get(position).getAppointmentId(),position);
              
            }

			
        });
        
        holder.eventIcon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String[] parts = eventlist.get(position).getExhibitorName().split("~~");
                String part1 = parts[0]; // Name
                String part2 = parts[1]; // location
            	String exbLoc[];
            	if(part1.contains("Hall")){
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
				DatabaseHelper dbHelper=EFairEmallApplicationContext.getDatabaseHelper();
                dbHelper.openDatabase(DatabaseHelper.READMODE);
        		users=dbHelper.getDetailOfSelectedExibitor(Integer.toString(eventlist.get(position).getEhxibitorId()),stallNo);
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
		        bundle.putInt("EXHIBITORID",eventlist.get(position).getEhxibitorId());
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
    
   
  //create a dialog box for user question
  	private void showOptionDialog(final Fragment fragment,final Bundle bundle,final int appointmentId,final int position) {
  		AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

  		// Setting Dialog Title
  		alertDialog.setTitle("Options...");

  		// Setting Dialog Message
  		alertDialog
  				.setMessage("What do you want ?");

  		// On pressing Wifi button
  		alertDialog.setPositiveButton("Delete",
  				new DialogInterface.OnClickListener() {
  					public void onClick(DialogInterface dialog, int which) {
  						
  						DatabaseHelper dbHelper=EFairEmallApplicationContext.getDatabaseHelper();
  						dbHelper.openDatabase(DatabaseHelper.WRITEMODE);
  						int row=dbHelper.deleteVisitorScheduleBasedOnId(Integer.toString(appointmentId));
  						if(row>0){
  							Log.i("delete","APPOINTMEMT DELETED SUCCESSFULLY");
  							removeDeletedItemFromList(position);
  							
  						}else{
  							Log.i("delete","APPOINTMEMT NOT DELETED SUCCESSFULLY");
  							dialog.cancel();
  	  						
  						}
  						
  					  }
  					
  				});
  		//On pressing 3g button
  		alertDialog.setNeutralButton("Details",
  				new DialogInterface.OnClickListener() {
  					public void onClick(DialogInterface dialog, int which) {
  						//dialog.cancel();
  						
  						callNextFragment(fragment, bundle);
  					}

  				});
  		// on pressing cancel button
  		alertDialog.setNegativeButton("Cancel",
  				new DialogInterface.OnClickListener() {
  					public void onClick(DialogInterface dialog, int which) {
  						dialog.cancel();
  					}
  				});

  		// Showing Alert Message
  		alertDialog.show();
  	}
  	
  	private void removeDeletedItemFromList(int position){
  		// remove the deleted list item from list
  		eventlist.remove(position);
  		
  		notifyDataSetChanged();
  		
  	}
  	
  	 private void callNextFragment(Fragment fragment, Bundle bundle){
     	
     	FragmentTransaction ft = ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction();
     	fragment.setArguments(bundle);
         ft.replace(R.id.content_frame, fragment,"");
         ft.addToBackStack(null);
         ft.commit();
           
     }
     
  	
}
