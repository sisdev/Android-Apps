package casaneeds.unassignedTask;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import casaneeds.appLaunch.R;
import casaneeds.entity.Order;

/**
 * Created by Sunita on 25-May-2016.
 */
public class UnassignedListAdapter extends BaseAdapter {


    List<Order> unassigned_list = null;
    private static LayoutInflater inflater = null;
    View_Holder holder;
    Context ctx ;
    UnassignedTaskActivity activityUnassigned ;


    public UnassignedListAdapter(Context c1, ArrayList<Order> list, UnassignedTaskActivity act3 ) {
        this.unassigned_list = list;
        this.ctx = c1 ;
        this.activityUnassigned = act3 ;
        inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    @Override
    public int getCount() {
        return unassigned_list.size();
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
            vi = inflater.inflate(R.layout.unassignedtask_row, null);
            holder = new View_Holder();

            holder.tvid = (TextView) vi.findViewById(R.id.id); // code
            holder.tvaddress = (TextView) vi.findViewById(R.id.address); // name
            holder.btn = (Button) vi.findViewById(R.id.click);

            vi.setTag(holder);


        }
        else{

            holder = (View_Holder)vi.getTag();
        }

        // Setting all values in listview
        Order ord2 = unassigned_list.get(position);
        holder.tvid.setText(ord2.getOrderNo()+" ");
        holder.tvaddress.setText(ord2.getCustArea());

        holder.btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Order ord3 =  unassigned_list.get(position);
                activityUnassigned.assignTask(ord3);
                unassigned_list.remove(position);
                notifyDataSetChanged();
            }
        });
        return vi;
    }


    private class View_Holder {

        TextView tvid, tvaddress;
        Button btn;

    }
}



