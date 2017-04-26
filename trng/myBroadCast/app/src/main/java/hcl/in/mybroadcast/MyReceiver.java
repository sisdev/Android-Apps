package hcl.in.mybroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {
    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
     //   throw new UnsupportedOperationException("Not yet implemented");
        String str1 = intent.getStringExtra("MyVal");
        Toast.makeText(context, "Broast cast recevied:"+str1, Toast.LENGTH_LONG).show();
    }
}
