package in.sisoft.a16_whereami;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationListener;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LocationListener   {

    LocationManager mLocMgr ;
    TextView tv_gps, tv_long, tv_lat, tv_add ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_gps = (TextView) findViewById(R.id.textView3);
        tv_long = (TextView) findViewById(R.id.textView5);
        tv_lat = (TextView) findViewById(R.id.textView7);
        tv_add = (TextView) findViewById(R.id.textView9);

        mLocMgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean gps_stat = mLocMgr.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (gps_stat){
            tv_gps.setText("Enabled");
        }
        else {
            tv_gps.setText("Disabled") ;
        }

        // Check Permission and request for that..
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},10);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},20);
        }


    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            mLocMgr.removeUpdates(this);
        }
        catch(SecurityException e)
        {
            Log.d("WhereAmI",e.getMessage());
        }
    }

    public void whereAmI(View v){
        Toast.makeText(this,"Button Pressed",Toast.LENGTH_LONG).show();
        try{
            Location loc = mLocMgr.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (loc!= null){
                tv_lat.setText(loc.getLatitude()+"");
                tv_long.setText(loc.getLongitude()+"");
                getLocAddress(loc);
            }
            mLocMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,500,this);

        } catch(SecurityException e)
        {
            Log.d("WhereAmI", e.getMessage());
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        Toast.makeText(this,"Location Changed",Toast.LENGTH_LONG).show();
        if (location!= null) {
            tv_lat.setText(location.getLatitude() + "");
            tv_long.setText(location.getLongitude() + "");
            getLocAddress(location);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(this,"Provider Enabled",Toast.LENGTH_LONG).show();
        if (provider == LocationManager.GPS_PROVIDER)
        {
            tv_gps.setText("Enabled");
        }
    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public void getLocAddress(Location location)
    {
        Toast.makeText(this,"Location Address",Toast.LENGTH_LONG).show();

        try {
            Geocoder gc = new Geocoder(this);
            List<Address> la = gc.getFromLocation(location.getLatitude(), location.getLongitude(), 5);
            if (la.size()>0)
            {
                Address x0 = la.get(0);
                String str_address0 = x0.getAddressLine(0)+":"+x0.getLocality()+":"+x0.getPostalCode();
                tv_add.setText(str_address0);
                for (Address x: la)
                {
                    String str_address = x.getAddressLine(0)+":"+x.getLocality()+":"+x.getPostalCode();
                    Toast.makeText(this,str_address,Toast.LENGTH_LONG).show();
                }
            }
        }
        catch (Exception e)
        {
            Log.d("WhereAmI:GeoCoder",e.getMessage());
        }

    }

}
