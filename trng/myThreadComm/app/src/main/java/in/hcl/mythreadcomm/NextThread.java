package in.hcl.mythreadcomm;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

/**
 * Created by Dell on 19-09-2016.
 */
public class NextThread extends Thread {

    Handler h1;

    NextThread(Handler l1) {
        h1 = l1;
    }

    @Override
    public void run() {
        super.run();
        for (int i = 0; i < 10; i++) {
            String data = "From Thread:" + i ;
            Message msg = h1.obtainMessage(1, (String) data);
            h1.sendMessage(msg);
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                Log.d("NextThread", e.getMessage());
            }
        }
    }
}
