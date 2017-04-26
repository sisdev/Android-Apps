package casaneeds.taskactivity;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import casaneeds.appLaunch.AppConstants;
import casaneeds.entity.DatabaseHelper;
import casaneeds.appLaunch.R;
import casaneeds.entity.Items;

/**
 * Created by Sunita on 04-Jun-2016.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecycleViewHolder> {

    Context c;
    ArrayList<Items> itemlist = new ArrayList<>();
    View v;
    RecycleViewHolder rv;
    int s, oty;
    int color_Fulfilled ;

    public RecyclerViewAdapter(Context ctx, ArrayList<Items> list)
    {
        this.c = ctx;
        this.itemlist = list;
        color_Fulfilled = c.getResources().getColor(R.color.OrderFullFilled);

    }

    @Override
    public RecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_orderfullfillment,parent,false);
       rv = new RecycleViewHolder(v);
        return rv;
    }

    @Override
    public void onBindViewHolder(final RecycleViewHolder holder, int position) {
    final Items item = itemlist.get(position);
        holder.tvcode.setText(item.getItemNo() +" ");
        holder.tvname.setText(item.getItemName());
        final String qty = String.valueOf(item.getItemQty());
        int qt = Integer.parseInt(qty);
        holder.oqty.setText(qty);
        String fqty = String.valueOf(item.getFqty());
        int fqt = Integer.parseInt(fqty);

        holder.fqty.setText(fqty);
        holder.fqty.setSelection(fqty.length());

        if(fqt == qt) {
            holder.cv.setCardBackgroundColor(color_Fulfilled);
        }
        if(fqt < qt) {
            if (fqt == 0) {
                holder.cv.setCardBackgroundColor(Color.BLUE);
            }
            else {
                holder.cv.setCardBackgroundColor(Color.GRAY);
            }
        }

        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String str = holder.fqty.getText().toString();
                s = Integer.parseInt(str);
                oty = Integer.parseInt(qty);
                item.setFqty(s);
                if(s == oty) {
                    holder.cv.setCardBackgroundColor(color_Fulfilled);
                }
                if(s > oty) {
                    Toast.makeText(c, "Quantity Greater than Ordered Qty", Toast.LENGTH_LONG).show();
                }
                if(s < oty) {
                    if (s == 0)
                        holder.cv.setCardBackgroundColor(Color.BLUE);
                    else
                        holder.cv.setCardBackgroundColor(Color.GRAY);
                }

                DatabaseHelper dbh = new DatabaseHelper(c);
                dbh.updateqty(item.getItemNo(), Integer.parseInt(str));
                dbh.updatePackingStatus(item.getOrdNo(), AppConstants.YetToPack);  // Reset Order to Yet To Pack Status
                Log.d("Position",str );


            }
        });

    }

    @Override
    public int getItemCount() {
        return itemlist.size();
    }



    public static class RecycleViewHolder extends  RecyclerView.ViewHolder
    {
        TextView tvcode,tvname,oqty;
        EditText fqty;
        Button update;
        CardView cv;

        public RecycleViewHolder(View v) {
            super(v);

            this.cv = (CardView)v.findViewById(R.id.card_view);
            this.tvcode = (TextView)v.findViewById(R.id.displaycode);
            this.tvname = (TextView)v.findViewById(R.id.displayname);
            this.oqty = (TextView)v.findViewById(R.id.displayorderqty);
            this.fqty = (EditText)v.findViewById(R.id.etqty);
            this.update = (Button)v.findViewById(R.id.update);

        }
    }

    int proceedOk()
    {
        int fullfilCount = 0 ; // How may order items have been fullfilled.

        for (Items i1:itemlist)
        {
            if (i1.getFqty()>0)
            {
                fullfilCount++ ;
            }
        }
        return fullfilCount ;
    }
}

