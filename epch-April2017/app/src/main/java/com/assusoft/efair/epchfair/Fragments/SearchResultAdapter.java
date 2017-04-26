package com.assusoft.efair.epchfair.Fragments;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.epch.efair.delhifair.R;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.assusoft.eFairEmall.entities.Exhibitors;

public class SearchResultAdapter extends BaseAdapter  {
 
    // Declare Variables
	 ExhibitorDetailFragment exhDetailsfragment;
    Context mContext;
    LayoutInflater inflater;
    private EditText searchBoxId;
    private List<Exhibitors> userlist = null;
    private ArrayList<Exhibitors> arraylist;
    public static boolean searchFlage=false;
    public SearchResultAdapter(Context context, List<Exhibitors> userList1,EditText searchBoxId) {
        super();
    	mContext = context;
        this.userlist = userList1;
        this.searchBoxId=searchBoxId;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<Exhibitors>();
        this.arraylist.addAll(userlist);
        exhDetailsfragment=new ExhibitorDetailFragment();
        Log.i("EFAIR","ExbListViewAdapter constructor executed and size "+Integer.toString(userlist.size()));
    }
 
    public class ViewHolder {
        TextView exhibitorName;//companyName
        
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
    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        Log.i("EFAIR","Override getview method");
        if (view == null) {
        	Log.i("EFAIR"," if Override getview method");
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.search_result_item, null);
            // Locate the TextViews in listview_item.xml
            holder.exhibitorName = (TextView) view.findViewById(R.id.tvlistitemSearch);
           
            view.setTag(holder);
        } else {
        	Log.i("EFAIR"," else Override getview method");
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.exhibitorName.setText(userlist.get(position).getExhibitorName());
       
 
        // Listen for ListView Item Click
        view.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // Send single item click data to SingleItemView Class
            	Context fm =(v.getContext());
 				FragmentTransaction ft = ((FragmentActivity)fm).getSupportFragmentManager().beginTransaction();
 		        ft.replace(R.id.content_frame, exhDetailsfragment,"ExhibitorDetailFragment");
 		        
 		        ft.commit();
 		       Bundle bundle=new Bundle();
		        bundle.putInt("EXHIBITORID",userlist.get(position).getExhibitorId());
		        exhDetailsfragment.setArguments(bundle);
		        Log.i("EFAIR", "exibitorPosition="+position);
		        Log.i("EFAIR", "EXHIBITORID="+userlist.get(position).getExhibitorId()); 
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

	
	
 
}


