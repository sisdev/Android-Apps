package com.assusoft.efair.epchfair.Fragments;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import com.epch.efair.delhifair.HomeActivity;
import com.epch.efair.delhifair.R;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.assusoft.eFairEmall.databaseMaster.DatabaseHelper;
import com.assusoft.eFairEmall.entities.ExhibitorLocation;
import com.assusoft.eFairEmall.entities.Exhibitors;
import com.assusoft.eFairEmall.entities.VenueMap;
import com.epch.efair.delhifair.EFairEmallApplicationContext;

public class ExbListViewAdapter extends BaseAdapter  {
 
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
    public ExbListViewAdapter(Context context, List<Exhibitors> userList1,EditText searchBoxId,boolean flag) {
        super();
        this.flag=flag;
    	mContext = context;
        this.userlist = userList1;
        this.searchBoxId=searchBoxId;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<Exhibitors>();
        this.arraylist.addAll(userlist);

        exhDetailsfragment = new ExhibitorDetailFragment();
        getCurrentTimeStamp();
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
    	Log.i("EFAIR","i am in getcount"+Integer.toString(userlist.size()));
        return userlist.size();
    }
 
    @Override
    public Exhibitors getItem(int position) {
    	Log.i("EFAIR","i am in getitem"+Integer.toString(userlist.size()));
        return userlist.get(position);
    }
 
    @Override
    public long getItemId(int position) {
    	Log.i("EFAIR","i am in getitemid"+Integer.toString(userlist.size()));
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
         String[] parts = userlist.get(position).getExhibitorName().split("~~");
         String part1 = parts[0]; // Name
         String part2 = parts[1]; // location
         holder.exhibitorName.setText(parts[0]);
         holder.exhibitorLoc.setText(parts[1]);
 
        // Listen for ListView Item Click
        holder.llExhibiotrs.setOnClickListener(new OnClickListener() {
 
            public void onClick(View v) {
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
            	Log.i("EXPO","exb stall No "+stallNo);
            	if(flag){
            		//then load favorites fragment data 

            		ExhibitorDetailFragment fragment = new ExhibitorDetailFragment();
            		Log.i("EXPO","exb stall No "+userlist.get(position).getExhibitorId());
            		Bundle bundle=new Bundle();
      		        bundle.putInt("EXHIBITORID",userlist.get(position).getExhibitorId());
      		        
      		        bundle.putString("STALLNO",stallNo);
            		showOptionDialog(fragment, bundle,userlist.get(position).getExhibitorId(),position);
            		return;
            	}
 				FragmentTransaction ft = ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction();
 		        Bundle bundle=new Bundle();
		        bundle.putInt("EXHIBITORID",userlist.get(position).getExhibitorId());
		        bundle.putString("STALLNO",stallNo);
		        exhDetailsfragment.setArguments(bundle);
                Log.i("BOOKFAIR", "get(position).getExhibitorId()="+userlist.get(position).getExhibitorId());
                
                ft.replace(R.id.content_frame, exhDetailsfragment);
                ft.addToBackStack(null);
                ft.commit();
              
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
    
    
    private void getCurrentTimeStamp(){
		final Calendar c = Calendar.getInstance();        
		int currentYear = c.get(Calendar.YEAR);         
		 int currentMonth = c.get(Calendar.MONTH);  
		 int currentDay=c.get(Calendar.DAY_OF_MONTH)<15?15:c.get(Calendar.DAY_OF_MONTH);
		 int currentHour = c.get(Calendar.HOUR_OF_DAY)>12?(c.get(Calendar.HOUR_OF_DAY)-12):c.get(Calendar.HOUR_OF_DAY);         
		 int  currentMinute =c.get(Calendar.MINUTE); 
		 
		 strDate=currentYear+"-"+"02"+"-"+currentDay;
		 startTime="11:00";//(currentHour<10?"0"+currentHour:currentHour)+":"+(currentMinute<10?"0"+currentMinute:currentMinute);
		 endTime="08:00";//((currentHour+1)<10?"0"+(currentHour+1):(currentHour+1))+":"+(currentMinute<10?"0"+currentMinute:currentMinute);
	}
 
    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        userlist.clear();
        if (charText.length() == 0) {
            userlist.addAll(arraylist);
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
    }
    
  //create a dialog box for user question
  	private void showOptionDialog(final Fragment fragment,final Bundle bundle,final int exhibitorId,final int position) {
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
  						int row=dbHelper.deleteBookMark(exhibitorId);
  						if(row>0){
  							Log.i("delete","APPOINTMEMT DELETED SUCCESSFULLY");
  							removeItemFromList(exhibitorId);
  							//removeDeletedItemFromList(position);
  							
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
  	
  	private void removeItemFromList(int exhibitorId){
  		List<Exhibitors> list=new ArrayList<Exhibitors>();
  		
  		for(int i=0;i<userlist.size();i++){
  			if(userlist.get(i).getExhibitorId()==exhibitorId){
  				list.add(userlist.get(i));
  			}
  		}
  		for(int i=0;i<list.size();i++){
  			userlist.remove(list.get(i));
  	  		notifyDataSetChanged();
  		}
  	}
  	
  	private void removeDeletedItemFromList(int position){
  		// remove the deleted list item from list
  		userlist.remove(position);
  		
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


