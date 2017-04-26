package casaneeds.taskactivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import casaneeds.entity.DatabaseHelper;
import casaneeds.appLaunch.R;
import casaneeds.entity.Items;
import casaneeds.utils.LocationTracker;

public class OrderFullfillmentActivity extends AppCompatActivity {

    TextView tvod, tvcustname, tvarea, tvodamt,tvprice;
    int od_no, pmode;
    String name, address, area, od, amt;
    float od_amt;
    EditText etvoucher;
    String price;

    Button packing;
    RecyclerView recycleView;
    RecyclerViewAdapter adapter;
    DatabaseHelper dbHelper;
    double latitude, longitude;
    LocationTracker gps;

    private LinearLayoutManager lLayout;
    ArrayList<Items> itemlist = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline__card_view__order_fullfillment);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Order Details");
        getSupportActionBar().setIcon(R.drawable.back_btn);

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
   //             Intent i = new Intent(OrderFullfillmentActivity.this, WelcomeScreen.class);
   //             i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
   //             startActivity(i);
                finish();

            }
        });

        recycleView = (RecyclerView) findViewById(R.id.recycler_view);
        packing = (Button) findViewById(R.id.delivery);

        tvod = (TextView) findViewById(R.id.order_no);
        tvcustname = (TextView) findViewById(R.id.cust_name);
        tvarea = (TextView) findViewById(R.id.cust_area);
        tvodamt = (TextView) findViewById(R.id.order_amt);
        tvprice = (TextView)findViewById(R.id.price);

        etvoucher = (EditText)findViewById(R.id.etvoucher);


        od_no = getIntent().getExtras().getInt("orderno");
        name = getIntent().getExtras().getString("name");
        area = getIntent().getExtras().getString("area");
        address = getIntent().getExtras().getString("address");
        od_amt = getIntent().getExtras().getFloat("totalamt");
        pmode = getIntent().getExtras().getInt("payment");

        od = String.valueOf(od_no);
        amt = String.valueOf(od_amt);


        tvod.setText(od);
        tvcustname.setText(name);
        tvarea.setText(area);
        tvodamt.setText(amt);

            gps = LocationTracker.getInstance();
        // check if GPS enabled

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

            Log.d("OrderFullfillment", "Location:Langitude:" + latitude + "Longitude:" + longitude);


    packing.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String str = etvoucher.getText().toString();

            if (adapter.proceedOk()<=0)
            {
                Toast.makeText(getApplicationContext(), "Atleast one Order Item must be fullfilled...",
                        Toast.LENGTH_LONG).show();

            }
            else {
                if (str.equals(""))
                    etvoucher.setError("Please Enter Voucher Code");
                else {

                    String url = "http://android.casaneeds.com/seller/orders_processed.php?OrderID=" + od + "&VoucherNo=" + str + "&locLAT=" + latitude + "&locLONG=" + longitude;
                    Log.d("OrderFullFillment", "Update Server:" + url);
                    UpdateServerAsync voucher = new UpdateServerAsync(OrderFullfillmentActivity.this);
                    voucher.execute(url);

                    dbHelper.updateVoucherCode(od_no, str);

                    Intent i = new Intent(getApplicationContext(), Packing_Activity.class);
                    i.putExtra("orderno", od_no);
                    i.putExtra("name", name);
                    i.putExtra("address", address);
                    i.putExtra("totalamt", od_amt);
                    i.putExtra("area", area);
                    i.putExtra("payment", pmode);

                    startActivity(i);
                }
            }
        }
    });

        lLayout = new LinearLayoutManager(this);
        recycleView.setLayoutManager(lLayout);
        recycleView.setHasFixedSize(true);

        dbHelper = new DatabaseHelper(this);

        Cursor c = dbHelper.getItemdetailsData(od_no);
        c.moveToFirst();
        do {
            Items item = new Items(od_no,c.getInt(1),c.getString(2), c.getInt(3), c.getInt(4));
            itemlist.add(item);


        }while(c.moveToNext());
        dbHelper.close();

        adapter = new RecyclerViewAdapter(this,itemlist);
        recycleView.setAdapter(adapter);

        Cursor c2 = dbHelper.getVoucherCode(od_no);
        if (c2.moveToFirst())
        {
            if (c2.isNull(0))
            {
                Log.d("OrderFullfillActivity", "Voucher code is blank");
            }
            else
            {
                etvoucher.setText(c2.getString(0));
            }
        }



    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}



