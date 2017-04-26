package in.hcl.myappcounter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tv1 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv1 = (TextView) findViewById(R.id.text1);

        SharedPreferences sp = getSharedPreferences("MyCounter", Context.MODE_PRIVATE);
        int count = sp.getInt("MyCount",0);
        count++ ;
        tv1.setText("App Open Count:"+count);

        SharedPreferences.Editor spe = sp.edit();

        spe.putInt("MyCount", count);
        spe.commit() ;

    }
}
