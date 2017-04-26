package casaneeds.taskactivity;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import casaneeds.appLaunch.R;

/**
 * Created by Sunita on 04-Jun-2016.
 */
public class RecyclerViewHolders extends RecyclerView.ViewHolder {

    TextView tvcode,tvname,oqty;
    EditText fqty;
    Button btnupdate;


    public RecyclerViewHolders(View layoutView) {
        super(layoutView);

        this.tvcode = (TextView)layoutView.findViewById(R.id.displaycode);
        this.tvname = (TextView)layoutView.findViewById(R.id.displayname);
        this.oqty = (TextView)layoutView.findViewById(R.id.displayorderqty);
        this.fqty = (EditText)layoutView.findViewById(R.id.etqty);

    }
}
