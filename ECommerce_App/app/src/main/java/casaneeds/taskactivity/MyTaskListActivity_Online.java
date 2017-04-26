package casaneeds.taskactivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import casaneeds.appLaunch.AppConstants;
import casaneeds.entity.DatabaseHelper;
import casaneeds.appLaunch.R;
import casaneeds.appLaunch.WelcomeScreen;
import casaneeds.entity.Order;

public class MyTaskListActivity_Online extends AppCompatActivity {

    ListView lv;

    String uid;

    SharedPreferences sp;
    SharedPreferences.Editor editor;

    ArrayList<Order> t1;

    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_task_);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Assigned Orders");
        getSupportActionBar().setIcon(R.drawable.back_btn);

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MyTaskListActivity_Online.this, WelcomeScreen.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();

            }
        });


        lv = (ListView) findViewById(R.id.list_mytask);

        sp = getSharedPreferences(AppConstants.mypreference, Context.MODE_PRIVATE);
        editor = sp.edit();

        uid = sp.getString(AppConstants.USERID,"");

        String url="http://android.casaneeds.com/seller/orders_assigned.php?UserID="+uid;

        MyTaskAsync mtask = new MyTaskAsync(MyTaskListActivity_Online.this,this,lv);
        mtask.execute(url);

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(MyTaskListActivity_Online.this, WelcomeScreen.class);
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

   //             Toast.makeText(getApplicationContext(), "List View click " + str, Toast.LENGTH_SHORT).show();

            }
        });

        for( Order o1:t1) {
            String url = "http://android.casaneeds.com/seller/orders_details.php?OrderID=" + o1.getOrderNo();

            GetOrderDetailsAsync mtask = new GetOrderDetailsAsync(MyTaskListActivity_Online.this, o1.getOrderNo());
            mtask.execute(url);
        }
    }



    public void fulfillOrder(Order od) {

        int orderno = od.getOrderNo();
        String name = od.getCustName();
        String address = od.getCustAddress();
        float amt = od.getTotalAmount();
        String status = od.getStatus();
        String area = od.getCustArea();
        int payment = od.getPayment();

        Intent in = new Intent(getApplicationContext(), OrderFullfillmentActivity.class);

        in.putExtra("orderno", orderno);
        in.putExtra("name", name);
        in.putExtra("address", address);
        in.putExtra("totalamt", amt);
        in.putExtra("area", area);
        in.putExtra("payment",payment);

        startActivity(in);
    }

}





