package casaneeds.taskactivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import casaneeds.appLaunch.R;
import casaneeds.entity.Order;

/**
 * Created by Sunita on 27-May-2016.
 */
public class MyTaskListAdapter extends BaseAdapter {

    List<Order> task_list = null;
    private static LayoutInflater inflater = null;
    View_Holder holder;
    Context ctx ;
    MyTaskListActivity_Online task;


    public MyTaskListAdapter(Context context, ArrayList<Order> tasklist, MyTaskListActivity_Online t) {
        this.task_list = tasklist;
        this.ctx = context ;
        this.task = t;
        inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return task_list.size();
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

        if (convertView == null) {

            inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            vi = inflater.inflate(R.layout.mytask_row, null);
            holder = new View_Holder();

            holder.tvorderno = (TextView) vi.findViewById(R.id.orderno); // code
            holder.tvarea = (TextView) vi.findViewById(R.id.address); // name
            holder.tvstatus = (TextView) vi.findViewById(R.id.status); // qty

            vi.setTag(holder);


        }
        else{

            holder = (View_Holder)vi.getTag();
        }

        // Setting all values in listview

        Order od = task_list.get(position);

        holder.tvorderno.setText(od.getOrderNo() +" ");
        holder.tvarea.setText(od.getCustArea());
        holder.tvstatus.setText(od.getStatus());

        return vi;
    }


    private class View_Holder {

        TextView tvorderno, tvarea, tvstatus;
       }
}



