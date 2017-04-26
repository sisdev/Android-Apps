package com.epch.efair.delhifair;

import java.util.List;

import com.epch.efair.delhifair.R;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PanelLvAdapter extends BaseAdapter  {
 
    // Declare Variables
    Context mContext;
    LayoutInflater inflater;
    private List<String> data;
    private EditText searchBoxId;//used to remove the filterbox content
    public PanelLvAdapter(Context context, List<String> data) {
        mContext = context;
        this.data = data;
        inflater = LayoutInflater.from(mContext);
    }
 
    public class ViewHolder {
        TextView panelItem;
        
    }
 
    @Override
    public int getCount() {
        return data.size();
    }
 
    @Override
    public String getItem(int pos) {
        return data.get(pos);
    }
 
    @Override
    public long getItemId(int pos) {
        return pos;
    }
    
	@Override
    public View getView(final int pos, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.panel_listview_item, null);
            // Locate the TextViews in listview_item.xml
            holder.panelItem = (TextView) view.findViewById(R.id.tvPanelListItem);
            
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        View someView = (TextView) view.findViewById(R.id.tvPanelListItem);
		 LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)someView .getLayoutParams();
        // Set the results into TextViews
        holder.panelItem.setText(data.get(pos));
        if((pos==0)||(pos==4)||(pos==11)||(pos==15))
        {
        	    holder.panelItem.setTextSize(15);//@color/action_txt
        	    holder.panelItem.setTextColor(view.getContext().getResources().getColor(R.color.white));
        		 params.height = 80;
        		 
        		 holder.panelItem.setBackgroundColor(view.getContext().getResources().getColor(R.color.light_blue));
        		 someView .setLayoutParams(params);
        }else{
        	 holder.panelItem.setTextSize(14);
        	 holder.panelItem.setTextColor(view.getContext().getResources().getColor(R.color.packplus_blue));
        	 params.height = 80;
        	 holder.panelItem.setBackgroundResource(R.drawable.exhibitor_list_selector);
    		 someView .setLayoutParams(params);
        }
        // Listen for ListView Item Click
        /*view.setOnClickListener(new OnClickListener() {
 
            public void onClick(View v) {
            	
            	Log.i("EFAIR","Clicked product Id is "+data.get(p));
                // Send single item click data to SingleItemView Clas
                
            }

			
        });*/
 
        return view;
    }
 
   
	
	
 
}


