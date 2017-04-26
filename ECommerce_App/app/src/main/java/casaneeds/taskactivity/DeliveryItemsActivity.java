package casaneeds.taskactivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import casaneeds.appLaunch.R;
import casaneeds.entity.DatabaseHelper;
import casaneeds.utils.LocationTracker;

public class DeliveryItemsActivity extends AppCompatActivity {

    Button confirmdelivery;
    ListView lv;

    int od_no, pmode;
    String name, address, area, amt;
    float od_amt;

    double latitude, longitude;
    LocationTracker gps;


    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_items);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Delivery Items");
        getSupportActionBar().setIcon(R.drawable.back_btn);

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
     //           Intent i = new Intent(DeliveryItemsActivity.this, Packing_Activity.class);
     //           i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
     //           startActivity(i);
                finish();

            }
        });

        confirmdelivery = (Button)findViewById(R.id.delivery);
        lv = (ListView)findViewById(R.id.list_items);

        od_no = getIntent().getExtras().getInt("orderno");
        name = getIntent().getExtras().getString("name");
        area = getIntent().getExtras().getString("area");
        address = getIntent().getExtras().getString("address");
        od_amt = getIntent().getExtras().getFloat("totalamt");
        pmode = getIntent().getExtras().getInt("payment");

            gps = LocationTracker.getInstance();
            // check if GPS enabled
                latitude = gps.getLatitude();
                longitude = gps.getLongitude();

                Log.d("OrderFullfillment", "Location:Langitude:" + latitude + "Longitude:" + longitude);

        confirmdelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://android.casaneeds.com/seller/orders_delivered.php?OrderID="+od_no+"&DeliveredItems=1&locLAT="+latitude+"&locLONG="+longitude;
                Log.d("OrderFullFillment", "Update Server:" + url);
                UpdateServerAsync confirmDelivery = new UpdateServerAsync(DeliveryItemsActivity.this);
                confirmDelivery.execute(url);

                Intent i = new Intent(getApplicationContext(), AcceptPaymentActivity.class);
                i.putExtra("orderno", od_no);
                i.putExtra("name", name);
                i.putExtra("address", address);
                i.putExtra("totalamt", od_amt);
                i.putExtra("area", area);
                i.putExtra("payment",pmode);
                startActivity(i);
            }
        });



    }

    //	**************************************************************		call onStart() to Load Data		***************************************************************************************************

    @Override
    public void onStart() {
        super.onStart();
        dbHelper = new DatabaseHelper(this);
        LoadData();


    }

    public void LoadData() {
        try {
            Cursor c = dbHelper.getDeliveryItems(od_no);
            startManagingCursor(c);


            // The desired columns to be bound
            String[] from = new String[]{DatabaseHelper.ITEMNAME, DatabaseHelper.ITEMQTY, DatabaseHelper.ITEMFQTY};

            // the XML defined views which the data will be bound to
            int[] to = new int[]{R.id.itemname, R.id.oty, R.id.fqty};

            // create the adapter using the cursor pointing to the desired data as well as the layout information
            SimpleCursorAdapter sca = new SimpleCursorAdapter(this, R.layout.deliveryitems, c, from, to, 0);
            lv.setAdapter(sca);

        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_LONG).show();
        }
    }
}
