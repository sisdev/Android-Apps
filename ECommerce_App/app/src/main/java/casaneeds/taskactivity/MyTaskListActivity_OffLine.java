package casaneeds.taskactivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import casaneeds.entity.DatabaseHelper;
import casaneeds.appLaunch.R;
import casaneeds.appLaunch.WelcomeScreen;

public class MyTaskListActivity_OffLine extends AppCompatActivity {
    ListView lv;

    String uid;

    SharedPreferences sp;
    SharedPreferences.Editor editor;

    DatabaseHelper dbHelper;

    int orderno;
    String name, address, status, area;
    float amt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_off_line_task);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Assigned Orders - Offline");
        getSupportActionBar().setIcon(R.drawable.back_btn);

        lv = (ListView) findViewById(R.id.list_mytask);

        dbHelper= new DatabaseHelper(this);

      //  toolbar.setLogo(R.drawable.casaneeds_icon);

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MyTaskListActivity_OffLine.this, WelcomeScreen.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();

            }
        });

    }

        //	**************************************************************		call onStart() to Load Data		***************************************************************************************************

    @Override
    public void onStart()
    {
        super.onStart();
        dbHelper=new DatabaseHelper(this);
        LoadData();


    }

// 	**************************************************************		Load Data 		***************************************************************************************************

    public void LoadData()
    {

        try
        {
            Cursor c=dbHelper.getMyActiveOrders();
            startManagingCursor(c);


            // The desired columns to be bound
            String [] from=new String []{DatabaseHelper.ORDERNO,DatabaseHelper.AREA,DatabaseHelper.STATUS};

            // the XML defined views which the data will be bound to
            int [] to=new int [] {R.id.orderno,R.id.address,R.id.status};

            // create the adapter using the cursor pointing to the desired data as well as the layout information
            SimpleCursorAdapter sca=new SimpleCursorAdapter(this,R.layout.mytask_row,c,from,to,0);
            lv.setAdapter(sca);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String str = String.valueOf(position);

                    // Showing selected ListView item
     /*               Toast.makeText(parent.getContext(), "You selected: " + str,
                            Toast.LENGTH_LONG).show();
*/
                    Cursor cursor = (Cursor) parent.getItemAtPosition(position);
                    int orderno = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.ORDERNO));
                    String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.NAME));
                    String area = cursor.getString(cursor.getColumnIndex(DatabaseHelper.AREA));
                    float amt = cursor.getFloat(cursor.getColumnIndex(DatabaseHelper.AMOUNT));
                    String address = cursor.getString(cursor.getColumnIndex(DatabaseHelper.ADDRESS));

                    String info = String.valueOf(orderno) + " : " + name +"\n"
                            +area +"\n"+ amt;
       //             Toast.makeText(MyTaskListActivity_OffLine.this, info, Toast.LENGTH_LONG).show();

     //  Intent in = new Intent(getApplicationContext(),Offline_OrderFullfillment.class);
             Intent in = new Intent(getApplicationContext(),OrderFullfillmentActivity.class);
                    in.putExtra("orderno", orderno);
                    in.putExtra("name", name);
                    in.putExtra("address", address);
                    in.putExtra("totalamt", amt);
                    in.putExtra("area", area);

                    startActivity(in);


                }
            });



        }

        catch(Exception ex)
        {
            Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_LONG).show();
            Log.d("MyTaskListActivity_Off", ex.toString());
        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(MyTaskListActivity_OffLine.this, WelcomeScreen.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }


}
