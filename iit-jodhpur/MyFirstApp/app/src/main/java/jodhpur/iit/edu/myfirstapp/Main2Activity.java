package jodhpur.iit.edu.myfirstapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent i3 = getIntent();
        String str2 = i3.getStringExtra("PASSED");
        TextView tv2 = (TextView) findViewById(R.id.textView2);
        tv2.setText(str2);

    }

    public void goBack(View v) {
        finish();
    }

    public void openGoogle(View v)
    {
        Intent i2 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
        startActivity(i2);
    }
}
