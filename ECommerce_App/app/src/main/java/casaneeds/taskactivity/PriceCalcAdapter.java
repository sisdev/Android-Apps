package casaneeds.taskactivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import casaneeds.appLaunch.R;

/**
 * Created by Sunita on 12-Jul-2016.
 */
public class PriceCalcAdapter extends CursorAdapter {
    LayoutInflater li ;
    Cursor c2;
    Context ctx ;

     public PriceCalcAdapter(Context context, Cursor cursor, int flags)
    {
        super(context,cursor, flags);
        this.ctx = context ;
        this.c2 = cursor ;
        li = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return li.inflate(R.layout.confirmdeliveryrow,parent,false) ;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tv_item_name, tv_qty, tv_price ;
        tv_item_name = (TextView)view.findViewById(R.id.itemname);
        tv_qty = (TextView)view.findViewById(R.id.qty);
        tv_price = (TextView)view.findViewById(R.id.price);
        String str_itemName ;
        int itemQty;
        float itemUnitPrice ;
        str_itemName = cursor.getString(1);
        itemQty = Integer.parseInt(cursor.getString(2));
        itemUnitPrice = Float.parseFloat(cursor.getString(3));

        tv_item_name.setText(str_itemName);
        tv_qty.setText(""+itemQty);
        tv_price.setText(""+itemUnitPrice);
//        totalAmount += itemUnitPrice ;
    }

}
