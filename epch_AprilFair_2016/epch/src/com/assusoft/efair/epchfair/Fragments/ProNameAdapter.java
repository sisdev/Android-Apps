package com.assusoft.efair.epchfair.Fragments;

import java.util.List;

import com.epch.efair.delhifair.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class ProNameAdapter extends ArrayAdapter<ProductItem>{
	Context context;
	 
    public ProNameAdapter(Context context, int resourceId,
            List<ProductItem> items) {
        super(context, resourceId, items);
        this.context = context;
    }
 
    /*private view holder class*/
    private class ViewHolder {
        TextView txt;
        Button button;
   
    }
 
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        ProductItem rowItem = getItem(position);
 
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.product_list, null);
            holder = new ViewHolder();
            holder.txt= (TextView) convertView.findViewById(R.id.tvTitle);
            holder.button =(Button)convertView.findViewById(R.id.imgBtn);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();
 
        holder.txt.setText(rowItem.getTxt());
        //holder.button.(rowItem.getRate());
 
        return convertView;
    }

}
