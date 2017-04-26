package com.example.irshadahamd.bluetoothapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    BluetoothAdapter bluetoothAdapter;
    ListView listView;
   IntentFilter filter ;
   MyBluetoothReceiver myBluetoothReceiver;

    ArrayList listDiscoveredDevices;
    ArrayAdapter adapterDiscveredDevice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listDiscoveredDevices = new ArrayList();

        filter = new IntentFilter();
       filter.addAction(BluetoothDevice.ACTION_FOUND);

     /*  filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);*/

        myBluetoothReceiver = new MyBluetoothReceiver();

        this.registerReceiver(myBluetoothReceiver,filter);

        listView = (ListView) findViewById(R.id.listview);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();


    }


    public   void turnOn(View view)
    {
        Log.d("mainactivity","turnOn ");
        bluetoothAdapter.enable();

/*
        if (bluetoothAdapter.isEnabled())
        {
            Toast.makeText(this,"Already Turned On",Toast.LENGTH_SHORT).show();
        }
        else
        {
            startActivity(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE));

        }*/

    }

    public   void turnOf(View view)
    {
        listDiscoveredDevices.clear();
        if(adapterDiscveredDevice != null)
        adapterDiscveredDevice.notifyDataSetChanged();

      //  Log.d("mainactivity","turnOff");
        bluetoothAdapter.disable();
        Toast.makeText(this,"Turned Off",Toast.LENGTH_SHORT).show();
    }

    public  void pairedDevices(View view)
    {
        ArrayList listPairedDevices = new ArrayList();
        BluetoothDevice bluetoothDevice1;

       Set<BluetoothDevice> set = bluetoothAdapter.getBondedDevices();
        for (BluetoothDevice bluetoothDevice : set)
        {
            listPairedDevices.add(bluetoothDevice.getName());

        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,listPairedDevices);

        listView.setAdapter(arrayAdapter);



    }

    public  void discoverable(View view)
    {
        startActivity(new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE));
        Log.d("mainactivity","discoverable method");

    }

    public  void discover(View view)
    {
        Log.d("mainactivity","discover method");
       /* Log.d("Tag","Discover fileter = "+filter.toString()) ;
        Log.d("Tag","Discover receiver = "+ myBluetoothReceiver.toString()) ;*/
        bluetoothAdapter.startDiscovery();
        adapterDiscveredDevice = new ArrayAdapter(this,android.R.layout.simple_list_item_1, listDiscoveredDevices);
        listView.setAdapter(adapterDiscveredDevice);

    }






    public void setDisciveredDevice(BluetoothDevice bluetoothDevice, Short rssi)
    {
       /* Toast.makeText(this,"from receiver",Toast.LENGTH_SHORT).show();
        Log.d("receive","from receiver");*/
        String name = bluetoothDevice.getName();
        String address = bluetoothDevice.getAddress();
        String device = "Name = "+name+"\n"+"MAC Address = "+address+"\n"+"RSSI Value = "+rssi+"\n";
        listDiscoveredDevices.add(device);
        adapterDiscveredDevice.notifyDataSetChanged();
    }
    

}
