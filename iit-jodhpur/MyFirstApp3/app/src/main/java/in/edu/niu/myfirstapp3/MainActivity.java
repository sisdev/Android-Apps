package in.edu.niu.myfirstapp3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int x,y;
    EditText input1, input2 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input1 = (EditText)findViewById(R.id.et1);
        input2 = (EditText)findViewById(R.id.et2);
    }

    public void method1(View v){
        x = Integer.parseInt(input1.getText().toString());
        y = Integer.parseInt(input2.getText().toString());

        int result = x + y;
        Toast.makeText(this, ""+result, Toast.LENGTH_SHORT).show();
    }


    public void method2(View v){

        x = Integer.parseInt(input1.getText().toString());
        y = Integer.parseInt(input2.getText().toString());

        int result = x - y;
        Toast.makeText(this,""+result, Toast.LENGTH_SHORT).show();
    }
}
