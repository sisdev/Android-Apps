package hcl.in.myfirstproj;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn1, btn2, btn4;
    EditText et1, et2, et3;
    TextView tv1;

    int reqCode1 = 10 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg_scr);
        et1 = (EditText) findViewById(R.id.editText);
        et2 = (EditText) findViewById(R.id.editText2);
        et3 = (EditText) findViewById(R.id.editText3);
        btn1 = (Button) findViewById(R.id.button);
        btn1.setOnClickListener(this);

        btn2 = (Button) findViewById(R.id.button2);
        btn2.setOnClickListener(this);

        btn4 = (Button) findViewById(R.id.button4);
        btn4.setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast t1 = Toast.makeText(this, "On Start", Toast.LENGTH_LONG);
        t1.show();
        Log.d("MainActivity", "On Start");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast t1 = Toast.makeText(this, "On Resume", Toast.LENGTH_LONG);
        t1.show();
        Log.d("MainActivity", "On Resume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast t1 = Toast.makeText(this, "On Pause", Toast.LENGTH_LONG);
        t1.show();
        Log.d("MainActivity", "On Pause");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast t1 = Toast.makeText(this, "On Stop", Toast.LENGTH_LONG);
        t1.show();
        Log.d("MainActivity", "On Stop");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast t1 = Toast.makeText(this, "On Destroy", Toast.LENGTH_LONG);
        t1.show();
        Log.d("MainActivity", "On Destroy");


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                String name, email, phone;
                name = et1.getText().toString();
                email = et2.getText().toString();
                phone = et3.getText().toString();
                Toast t1 = Toast.makeText(this, name + ":" + email + ":" + phone, Toast.LENGTH_LONG);
                t1.show();
                break;
            case R.id.button2:
                Intent int2 = new Intent(this, Main2Activity.class);
                name = et1.getText().toString();
                int2.putExtra("Name",name);
                int2.putExtra("REQ_CODE", 0);
                startActivity(int2);
                break;

            case R.id.button4:
                Intent int3 = new Intent(this, Main2Activity.class);
                name = et1.getText().toString();
                int3.putExtra("Name",name);
                int3.putExtra("REQ_CODE",10);
                startActivityForResult(int3,reqCode1);
                break;


        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == reqCode1)
        {
            if (resultCode == Activity.RESULT_OK)
            {
                String returnVal = data.getStringExtra("RETURN_VAL");
                Toast.makeText(this,"Return Value:"+returnVal, Toast.LENGTH_LONG).show();
            }
        }

    }
}
