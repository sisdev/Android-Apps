package casaneeds.taskactivity;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import casaneeds.appLaunch.R;

/**
 * Created by Sunita on 20-May-2016.
 */
public class ItemListAdapter extends BaseAdapter {

    customCheckBoxListener customListner;


    List<HashMap<String, String>> item_list = null;
    private static LayoutInflater inflater = null;
    View_Holder holder;
    Context ctx ;


    public interface customCheckBoxListener {
        public void onCheckBoxClickListner(int position);
    }


    public void setCustomCheckBoxListener(customCheckBoxListener listener) {
        this.customListner = listener;
    }

    public ItemListAdapter(Context c1, ArrayList<HashMap<String, String>> itemlist) {
        this.item_list = itemlist;
        this.ctx = c1 ;
        inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return item_list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View vi = convertView;

        //Populate the Listview
        final int pos = position;
    //    Items items = item_list.get(pos);

        if (convertView == null) {

            inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            vi = inflater.inflate(R.layout.orderfullfillment_row, null);
            holder = new View_Holder();

            holder.tvcode = (TextView) vi.findViewById(R.id.itemcode); // code
            holder.tvname = (TextView) vi.findViewById(R.id.itemname); // name
            holder.tvqty = (TextView) vi.findViewById(R.id.qty); // qty
            holder.cb = (CheckBox) vi.findViewById(R.id.checkbox);

            vi.setTag(holder);


        }
        else{

            holder = (View_Holder)vi.getTag();
        }

        // Setting all values in listview

        holder.tvcode.setText(item_list.get(position).get("ItemId"));
        holder.tvname.setText(item_list.get(position).get("ItemName"));
        holder.tvqty.setText(item_list.get(position).get("ItemQty"));

        String status = item_list.get(position).get("Status");

        if(status.equals("Y"))
        holder.cb.setChecked(true);
        if(status.equals("N"))
        holder.cb.setChecked(false);

 /*       if(holder.cb.isChecked()){
            holder.cb.setChecked(false);
        }
        else {
            holder.cb.setChecked(true);
        }
*/
 /*       holder.cb.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (customListner != null) {
                    customListner.onCheckBoxClickListner(position);
                }

            }
        });
*/
        return vi;
        }


    private class View_Holder {

        TextView tvcode, tvname, tvqty;
        CheckBox cb;

        }
}


