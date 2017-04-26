package in.sisoft.mydialogfrag;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openDialog(View v)
    {
       // DialogFragment b2 = new bb2();
        DialogFragment b2 = new AlertDialogFragment();
        b2.show(getFragmentManager(),"dialog2");
    }
}
