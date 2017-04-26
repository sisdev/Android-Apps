package casaneeds.taskactivity;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Set;

import casaneeds.appLaunch.R;

/**
 * Created by admin on 7/19/2016.
 */
public class PackingItemsAdapter extends CursorAdapter {
    LayoutInflater li ;
    Cursor c2;
    Context ctx ;
    HashMap<String,String> itemPacked = new HashMap<>();
    String packingStatus ; // 0 - Not packed, 1- Packed

    public PackingItemsAdapter(Context context, Cursor c, int flags, String status) {
        super(context, c, flags);
        this.ctx = context ;
        this.c2 = c ;
        this.packingStatus = status ;
        li = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return li.inflate(R.layout.packing_row,parent,false) ;
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        TextView tv_item_name, tv_qty ;
        final CheckBox cb_packing ;
        tv_item_name = (TextView)view.findViewById(R.id.itemname);
        tv_qty = (TextView)view.findViewById(R.id.qty);
        cb_packing = (CheckBox)view.findViewById(R.id.packingFlag);
        final String str_itemName ;
        int itemQty;
        final String itemNo ;

        itemNo = cursor.getString(1);
        str_itemName = cursor.getString(2);
        itemQty = Integer.parseInt(cursor.getString(3));

        if (packingStatus.equalsIgnoreCase("1"))
        {
            cb_packing.setChecked(true);
            itemPacked.put(itemNo,"1");
        }
        else
        {
            cb_packing.setChecked(false);
            itemPacked.put(itemNo, "0");
        }

        tv_item_name.setText(str_itemName);
        tv_qty.setText(""+itemQty);
        cb_packing.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (cb_packing.isChecked())
                {
                    itemPacked.put(itemNo,"1");
                }
                else
                {
                    itemPacked.put(itemNo,"0");

                }
            }
        });


    }

    boolean validateAllPacked()
    {
        String itemPackingStatus;
        boolean allPackedFlag = true ;
        Set<String> allItems = itemPacked.keySet();
        for (String x: allItems)
        {
            itemPackingStatus = itemPacked.get(x);
            if (itemPackingStatus.equalsIgnoreCase("0"))
            {
                allPackedFlag = false ;
            }
        }
        return allPackedFlag ;
    }
}
