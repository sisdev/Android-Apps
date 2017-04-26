package phonecall.sisoft.in.myphonecall;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import io.fabric.sdk.android.Fabric;
import com.crashlytics.android.Crashlytics;


public class MainActivity extends AppCompatActivity {

    Intent int1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);

    }

    public void callPhone(View v) {
        Context ctx1;
        EditText ed1 = (EditText) findViewById(R.id.editText);
        String str1 = ed1.getText().toString();
        int1 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + str1));


        //   if (ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.CALL_PHONE))
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this,"Check Self Permission: Okay", Toast.LENGTH_LONG ).show();
            startActivity(int1);
        } else {
            Toast.makeText(this,"Check Self Permission: Not Okay.. REquest Permission", Toast.LENGTH_LONG ).show();

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 10);
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 10: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Toast.makeText(this,"On Request Granted:", Toast.LENGTH_LONG ).show();
                    startActivity(int1);


                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }

            }


        }
    }
    public void clickCrash(View v) {
        throw new RuntimeException("This is a crash");
    }

    }
