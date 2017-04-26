package in.hcl.mythreadcomm;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendMsg(View v)
    {
                myHandler h1 = new myHandler(this);
                NextThread n1 = new NextThread(h1);
                n1.start();
    }

}

class myHandler extends Handler{

    Context ctx ;
    myHandler(Context c1)
    {
        this.ctx = c1 ;
    }
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        String str = msg.toString();
        Toast.makeText(this.ctx, "handleMessage:"+str, Toast.LENGTH_LONG).show();

    }
}


