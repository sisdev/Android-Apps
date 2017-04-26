package in.sisoft.a20_bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    BluetoothAdapter bta ;
    TextView tv ;
    MyReceiver mReceiver ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        bta = BluetoothAdapter.getDefaultAdapter();
        tv = (TextView) findViewById(R.id.tv1);
        if (bta.isEnabled()){
            tv.setText("Status:Enabled");
        }
        else {
            tv.setText("Status:Disabled");
        }

        mReceiver = new MyReceiver();
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void toggle(View v){
        if (!bta.isEnabled()){
            // REquest to enable the bluetooth
            String enableBT = BluetoothAdapter.ACTION_REQUEST_ENABLE;
            Intent enableIntent = new Intent(enableBT);
            startActivityForResult(enableIntent,20);
        }
        else
        {
            bta.disable() ;
            tv.setText("Status:Disabled");

            // There no call or intent to disable bluetooth

        }

    }

    public void enableDiscover(View v) {
        int scanMode = bta.getScanMode();
        if (scanMode == BluetoothAdapter.SCAN_MODE_CONNECTABLE) {
            Toast.makeText(this,"Already Connectable",Toast.LENGTH_LONG).show() ;

            String aDiscoverable = BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE;
            startActivityForResult(new Intent(aDiscoverable),40);

        }
        else if (scanMode == BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE){
            Toast.makeText(this,"Already Connectable and Discoverable",Toast.LENGTH_LONG).show() ;
        }
        else if (scanMode == BluetoothAdapter.SCAN_MODE_NONE){
            Toast.makeText(this,"Not Discoverable",Toast.LENGTH_LONG).show() ;
            String aDiscoverable = BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE;
            startActivityForResult(new Intent(aDiscoverable),30);

        }
    }

    public void scanBluetooth(View v){
        Toast.makeText(this,"scan Bluetooth",Toast.LENGTH_LONG).show();
        if (!bta.isDiscovering())
        {
            bta.startDiscovery() ;
            Toast.makeText(this,"Started scan Bluetooth",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(this, "On Activity Result"+requestCode,Toast.LENGTH_LONG).show();
        if ((requestCode == 20) && (resultCode==RESULT_OK)){
            tv.setText("Status:Enabled");
        }
        if ((requestCode == 30) && (resultCode==RESULT_OK)){
            tv.setText("Status:Enabled-Discoverable");
        }

        if ((requestCode == 40) && (resultCode==RESULT_OK)){
            tv.setText("Status:40:Enabled-Discoverable");
        }

    }


    private class MyReceiver extends BroadcastReceiver {
        public MyReceiver() {
                    }

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO: This method is called when the BroadcastReceiver is receiving
            // an Intent broadcast.
            String action = intent.getAction();
            Toast.makeText(context,"ON Muy Receiver",Toast.LENGTH_LONG).show();

            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Discovery has found a device. Get the BluetoothDevice
                // object and its info from the Intent.
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
                Toast.makeText(context,deviceName+":"+deviceHardwareAddress,Toast.LENGTH_LONG).show();
            }
        }
    }



}
