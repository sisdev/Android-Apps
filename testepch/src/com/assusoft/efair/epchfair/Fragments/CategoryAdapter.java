package com.assusoft.efair.epchfair.Fragments;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.assusoft.eFairEmall.entities.ExhibitorCategory;
import com.epch.efair.delhifair.R;

public class CategoryAdapter extends BaseAdapter  {
 
    // Declare Variables
    Context mContext;
    LayoutInflater inflater;
    private List<ExhibitorCategory> productlist = null;
    private ArrayList<ExhibitorCategory> arraylist;
    private EditText searchBoxId;//used to remove the filterbox content
    public CategoryAdapter(Context context, List<ExhibitorCategory> productList1,EditText searchBoxId) {
        mContext = context;
        this.searchBoxId=searchBoxId;
        this.productlist = productList1;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<ExhibitorCategory>();
        this.arraylist.addAll(productlist);
    }
 
    public class ViewHolder {
        TextView productName;
        
    }
 
    @Override
    public int getCount() {
        return productlist.size();
    }
 
    @Override
    public ExhibitorCategory getItem(int position) {
        return productlist.get(position);
    }
 
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.list_item, null);
            // Locate the TextViews in listview_item.xml
            holder.productName = (TextView) view.findViewById(R.id.tvlistitem);
            
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.productName.setText(productlist.get(position).getCategoryName().trim());
      
        // Listen for ListView Item Click
        view.setOnClickListener(new OnClickListener() {
 
            public void onClick(View arg0) {
            	FragmentTransaction ft = ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction();
 		        ExhibitorsName fg=new ExhibitorsName();
 		        Bundle bundle=new Bundle();
 		        bundle.putInt("CATEGORYID",productlist.get(position).getCategoryId());
 		        bundle.putInt("FLAG",4);
 		        fg.setArguments(bundle);
                ft.replace(R.id.content_frame,fg);
                ft.addToBackStack(null);
                ft.commit();
            }

			
        });
 
        return view;
    }
 
    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        productlist.clear();
        if (charText.length() == 0) {
            productlist.addAll(arraylist);
        }
        else
        {
            for (ExhibitorCategory product : arraylist)
            {
                if (product.getCategoryName().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    productlist.add(product);
                }
            }
        }
        notifyDataSetChanged();
    }

	
	
 
}


