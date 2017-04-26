 package casaneeds.unassignedTask;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import casaneeds.appLaunch.AppConstants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import casaneeds.appLaunch.R;
import casaneeds.appLaunch.WelcomeScreen;
import casaneeds.entity.Order;
import casaneeds.utils.LocationTracker;

 public class UnassignedTaskActivity extends AppCompatActivity {

    LocationTracker gps;

    // JSON Node names mytask.txt
    public static final String TAG_UTASK = "unassignedtask";
    public static final String TAG_ORDERID = "orderid";
    public static final String TAG_ADDRESS = "custaddress";

    String id, address,data;


    InputStream in;

    JSONObject jo;
    JSONArray ja;

    // Hashmap for ListView
    ArrayList<HashMap<String, String>> unattend_tasklist;
    HashMap<String, String> hm;

    ListView lv;

    double latitude, longitude;

    UnassignedListAdapter unassignedData;


    String url = "http://android.casaneeds.com/seller/orders_unassigned.php";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unassigned__task);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Pending Orders");
        getSupportActionBar().setIcon(R.drawable.back_btn);

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UnassignedTaskActivity.this, WelcomeScreen.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();

            }
        });

        ConnectivityManager connMgr = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        android.net.NetworkInfo activeNetwork = connMgr.getActiveNetworkInfo() ;

        if (activeNetwork==null)
        {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(UnassignedTaskActivity.this);
            alertBuilder.setTitle("Warning");
            alertBuilder.setMessage("No Internet Connection Available");
            alertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener()  {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(UnassignedTaskActivity.this, "Connect with Internet.. and Retry..", Toast.LENGTH_LONG).show();
                    dialog.cancel();  }; });

            AlertDialog ad = alertBuilder.create();
            ad.show() ;
        }
        else {

            lv = (ListView) findViewById(R.id.list_items);
            UnassignedAsync unassign = new UnassignedAsync(UnassignedTaskActivity.this, this, lv);
            unassign.execute(url);
        }
    }
    @Override
    public void onBackPressed () {
        Intent i = new Intent(UnassignedTaskActivity.this, WelcomeScreen.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }

    public void assignTask(Order ord3)
    {
     //   Toast.makeText(this, "Assign Order:"+ ord3.getOrderNo(), Toast.LENGTH_LONG).show();

        SharedPreferences sp;
        sp = getSharedPreferences(AppConstants.mypreference, Context.MODE_PRIVATE);

        String uid = sp.getString(AppConstants.USERID,"0");

            gps = LocationTracker.getInstance();
            // check if GPS enabled

                latitude = gps.getLatitude();
                longitude = gps.getLongitude();

            //    Toast.makeText(getApplicationContext(), "Langitude" + latitude + "Longitude" + longitude, Toast.LENGTH_LONG).show();
                Log.d("UnAssignedTaskActivity","Location:"+"Langitude" + latitude + "Longitude" + longitude);





        OrderAssignAsync orderAssign = new OrderAssignAsync(UnassignedTaskActivity.this, ord3);
        orderAssign.execute(String.valueOf(ord3.getOrderNo()), uid,String.valueOf(latitude),String.valueOf(longitude) );


    }


}



