package hcl.in.mylistapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Dell on 07-09-2016.
 */
public class EmpAdapter extends BaseAdapter {
    ArrayList<Emp> e1 ;
    Context ctx ;

    LayoutInflater l1 ;


    EmpAdapter(ArrayList<Emp> t1, Context c1)
    {
        e1 = t1 ;
        ctx = c1 ;
        l1 = (LayoutInflater) c1.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return e1.size();
    }

    @Override
    public Object getItem(int position) {
        // return e1.get(position);

        return null;

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

     @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v1 ;
        TextView tv1, tv2 ;
        v1 = l1.inflate(R.layout.list_emp, null);

/*
        if (convertView==null)
        {
            v1 = l1.inflate(R.layout.list_emp, null);
        }
        else
        {
            v1 = convertView ;
        }
*/
        tv1 = (TextView)v1.findViewById(R.id.textView);
        tv2 = (TextView)v1.findViewById(R.id.textView2);

        Emp emp1 = e1.get(position);
        Log.d("getView",":"+position+":"+emp1.getName());
        tv1.setText(emp1.getName());
        tv2.setText(emp1.getLocation());
        return v1 ;
    }
}
