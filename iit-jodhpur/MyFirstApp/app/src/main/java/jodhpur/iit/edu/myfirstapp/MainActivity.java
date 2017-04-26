package jodhpur.iit.edu.myfirstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.next_screen);
        Toast t1 = Toast.makeText(this,"Activity Started",Toast.LENGTH_LONG);
        t1.show();
        Log.d("Your App", "Application Started");
    }

    public void btnClicked(View v1)
    {
       Toast.makeText(this,"1Button Clicked",Toast.LENGTH_LONG).show();
       EditText ed1=(EditText) findViewById(R.id.editText);
       String str1 = ed1.getText().toString();
       Toast.makeText(this,"Entered Value:"+str1,Toast.LENGTH_LONG).show();
       Intent i1 = new Intent(this,Main2Activity.class);
        i1.putExtra("PASSED", str1);
        startActivity(i1);
    }
}
