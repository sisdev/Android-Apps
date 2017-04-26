package com.example.irshadahamd.bluetoothapp;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Irshad Ahamd on 05-Feb-17.
 */
public class MyBluetoothReceiver extends BroadcastReceiver {

    Short rssi;

    @Override
    public void onReceive(Context context, Intent intent) {

/*
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        bluetoothAdapter.getAddress();
        bluetoothAdapter.setName("irshad vivo");
        Log.d("receive","adpter = "+ bluetoothAdapter.getName());*/

    //    Toast.makeText(context,"action found",Toast.LENGTH_SHORT).show();
    //    Log.d("receive","action found");

        MainActivity activity = (MainActivity) context;


        String action = intent.getAction();
        if (BluetoothDevice.ACTION_FOUND.equals(action));
        {

            Log.d("receive","action found");
         try {

         //       rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI, Short.MIN_VALUE);


                BluetoothDevice bluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                Short rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI,Short.MIN_VALUE);

                activity.setDisciveredDevice(bluetoothDevice,rssi);

             Log.d("receive", " Name = "+bluetoothDevice.getName() );
             Log.d("receive", " Address = "+bluetoothDevice.getAddress() );

             Toast.makeText(context,"action found",Toast.LENGTH_SHORT).show();

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }

    }
}
