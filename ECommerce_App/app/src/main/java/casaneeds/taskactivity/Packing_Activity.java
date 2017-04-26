package casaneeds.taskactivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import casaneeds.appLaunch.AppConstants;
import casaneeds.entity.DatabaseHelper;
import casaneeds.appLaunch.R;
import casaneeds.utils.LocationTracker;

public class Packing_Activity extends AppCompatActivity {

    Button delivery;
    ListView lv;

    int od_no, pmode;
    String name, address, area, amt;
    float od_amt;

    double latitude, longitude;
    LocationTracker gps;

    PackingItemsAdapter pia;

    DatabaseHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packing_);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Packed Items");
        getSupportActionBar().setIcon(R.drawable.back_btn);

        od_no = getIntent().getExtras().getInt("orderno");
        name = getIntent().getExtras().getString("name");
        area = getIntent().getExtras().getString("area");
        address = getIntent().getExtras().getString("address");
        od_amt = getIntent().getExtras().getFloat("totalamt");
        pmode = getIntent().getExtras().getInt("payment");



        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
  //              Intent i = new Intent(Packing_Activity.this, OrderFullfillmentActivity.class);
   //             i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    //            startActivity(i);
                finish();

            }
        });

        delivery = (Button)findViewById(R.id.delivery);
        lv = (ListView)findViewById(R.id.list_items);

        od_no = getIntent().getExtras().getInt("orderno");

            gps = LocationTracker.getInstance();
         // check if GPS enabled

                latitude = gps.getLatitude();
                longitude = gps.getLongitude();

                Log.d("OrderFullfillment", "Location:Langitude:" + latitude + "Longitude:" + longitude);

        delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pia.validateAllPacked()) {
                    String url = "http://android.casaneeds.com/seller/orders_packed.php?OrderID=" + od_no + "&PackedItems=1&locLAT=" + latitude + "&locLONG=" + longitude;
                    Log.d("OrderFullFillment", "Update Server:" + url);
                    UpdateServerAsync packingStatus = new UpdateServerAsync(Packing_Activity.this);
                    packingStatus.execute(url);

                    dbHelper.updatePackingStatus(od_no, AppConstants.Packed);  // Packing Completed

                    Intent i = new Intent(getApplicationContext(), DeliveryItemsActivity.class);
                    i.putExtra("orderno", od_no);
                    i.putExtra("name", name);
                    i.putExtra("address", address);
                    i.putExtra("totalamt", od_amt);
                    i.putExtra("area", area);
                    i.putExtra("payment", pmode);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Please pack all items",Toast.LENGTH_LONG).show();
                }

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

    public void LoadData()
    {
   try {

       String packingStatus = dbHelper.getPackingStatus(od_no);

       Cursor c = dbHelper.getpackingData(od_no);

       pia = new PackingItemsAdapter(this,c,0,packingStatus);
       lv.setAdapter(pia);

       lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               String str = String.valueOf(position);
               // Showing selected ListView item
       //        Toast.makeText(parent.getContext(), "You selected: " + str, Toast.LENGTH_LONG).show();

           }
       });


    } catch (Exception ex) {
        Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_LONG).show();
    }
}



}
