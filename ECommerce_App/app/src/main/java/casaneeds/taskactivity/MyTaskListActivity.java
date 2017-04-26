package casaneeds.taskactivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import casaneeds.appLaunch.R;
import casaneeds.appLaunch.WelcomeScreen;

public class MyTaskListActivity extends AppCompatActivity {

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Assigned Orders");
        getSupportActionBar().setIcon(R.drawable.back_btn);

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MyTaskListActivity.this, WelcomeScreen.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();


            }
        });


    }

    //	**************************************************************		call onStart() to Load Data		***************************************************************************************************

    @Override
    public void onStart() {
        super.onStart();

        ConnectivityManager connMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        android.net.NetworkInfo activeNetwork = connMgr.getActiveNetworkInfo();

        if (activeNetwork == null) {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MyTaskListActivity.this);
            alertBuilder.setTitle("Warning");
            alertBuilder.setMessage("No Internet Connection Available Use Local DataBase");
            alertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(MyTaskListActivity.this, "Showing Offline Orders...", Toast.LENGTH_LONG).show();
                    dialog.cancel();
                    Intent i = new Intent(getApplicationContext(), MyTaskListActivity_OffLine.class);
                    startActivity(i);

                }

                ;
            });

            AlertDialog ad = alertBuilder.create();
            ad.show();
        } else {

            Intent i = new Intent(getApplicationContext(), MyTaskListActivity_Online.class);
            startActivity(i);

        }


    }
 /*   private void showdata() {
        Order od = new Order();

        int orderno = od.getOrderNo();
        String name = od.getCustName();
        String address = od.getCustAddress();
        float amt = od.getTotalAmount();
        String status = od.getStatus();
        String area = od.getCustArea();

        Order order = new Order(orderno, name, address, area, amt, status);

        dbHelper.addTask(order);
        Toast.makeText(getApplicationContext(), "Task Added Successfully in LocalDB", Toast.LENGTH_LONG).show();

        Intent in = new Intent(getApplicationContext(),Order_Fullfillment.class);

        in.putExtra("orderno", orderno);
        in.putExtra("name", name);
        in.putExtra("address", address);
        in.putExtra("totalamt", amt);
        in.putExtra("area", area);

        startActivity(in);


    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(MyTaskListActivity.this, WelcomeScreen.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }

    public void processMyTask(final ArrayList<Order> t1)
    {
        MyTaskListAdapter mytaskdata = new MyTaskListAdapter(this,t1, this);

        lv.setAdapter(mytaskdata);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                String str = String.valueOf(position);

                Order ord3 = t1.get(position);
                fulfillOrder(ord3);

                Toast.makeText(getApplicationContext(), "List View click " + str, Toast.LENGTH_SHORT).show();

            }
        });

    }


    public void fulfillOrder(Order od) {

        int orderno = od.getOrderNo();
        String name = od.getCustName();
        String address = od.getCustAddress();
        float amt = od.getTotalAmount();
        String status = od.getStatus();
        String area = od.getCustArea();

        Order order = new Order(orderno, name, address, area, amt, status);

        dbHelper.addTask(order);
        Toast.makeText(getApplicationContext(), "Task Added Successfully in WEB", Toast.LENGTH_LONG).show();

        Intent in = new Intent(getApplicationContext(),Order_Fullfillment.class);

        in.putExtra("orderno", orderno);
        in.putExtra("name", name);
        in.putExtra("address", address);
        in.putExtra("totalamt", amt);
        in.putExtra("area", area);

        startActivity(in);

        String s = "Name" + name;
        Log.d("Name Displayed", s);


    }

//	**************************************************************		call onStart() to Load Data		***************************************************************************************************

    @Override
    public void onStart()
    {
        super.onStart();
        dbHelper=new DatabaseHelper(this);
        LoadData();

        ConnectivityManager connMgr = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        android.net.NetworkInfo activeNetwork = connMgr.getActiveNetworkInfo() ;

        if (activeNetwork==null)
        {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MyTaskListActivity.this);
            alertBuilder.setTitle("Warning");
            alertBuilder.setMessage("No Internet Connection Available Use Local DataBase");
            alertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener()  {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(MyTaskListActivity.this, "Positive Confirmation", Toast.LENGTH_LONG).show();
                    dialog.cancel();
                    dbHelper= new DatabaseHelper(MyTaskListActivity.this);
                    showdata();

                }; });

            AlertDialog ad = alertBuilder.create();
            ad.show() ;
        }
        else {
            sp = getSharedPreferences(AppConstants.mypreference, Context.MODE_PRIVATE);
            editor = sp.edit();

            uid = sp.getString(AppConstants.USERID,"");


            String url="http://android.casaneeds.com/seller/orders_assigned.php?UserID="+uid;

            MyTaskAsync mtask = new MyTaskAsync(MyTaskListActivity.this,this,lv);
            mtask.execute(url);
        }

    }

// 	**************************************************************		Load Data 		***************************************************************************************************

    public void LoadData()
    {

        try
        {
            Cursor c=dbHelper.getMyActiveOrders();
            startManagingCursor(c);


            // The desired columns to be bound
            String [] from=new String []{DatabaseHelper.TASKID, DatabaseHelper.ORDERNO,DatabaseHelper.NAME,DatabaseHelper.ADDRESS, DatabaseHelper.AREA,DatabaseHelper.AMOUNT,DatabaseHelper.STATUS};

            // the XML defined views which the data will be bound to
            int [] to=new int [] {R.id.order_no,R.id.address,R.id.status};

            // create the adapter using the cursor pointing to the desired data as well as the layout information
            SimpleCursorAdapter sca=new SimpleCursorAdapter(this,R.layout.mytask_row,c,from,to,0);
            lv.setAdapter(sca);



        }

        catch(Exception ex)
        {
            Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_LONG).show();
        }
    }

*/
}




