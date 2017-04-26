 package in.sisoft.srm_firstapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

 public class MainActivity extends AppCompatActivity {

     TextView tv1 ;
     EditText ed1 ;
     Button btn1 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast t1 = Toast.makeText(this,"Hello SRM TOast", Toast.LENGTH_LONG);
        t1.show() ;

        tv1 = (TextView) findViewById(R.id.textView);
        ed1 = (EditText) findViewById(R.id.editText);
        btn1 = (Button) findViewById(R.id.button);


    }

     public void btnClicked(View v)
     {
         String str1 = ed1.getText().toString();
         tv1.setText(str1);

     }
}
