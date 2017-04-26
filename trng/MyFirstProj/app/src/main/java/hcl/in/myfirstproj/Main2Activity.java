package hcl.in.myfirstproj;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    Button btn1 ;
int reqCode ;
    String name ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        btn1 = (Button) findViewById(R.id.button3);
        btn1.setOnClickListener(this);
        Intent int4 = getIntent();
        reqCode = int4.getIntExtra("REQ_CODE",0);
        name = int4.getStringExtra("Name");

    }

    @Override
    public void onClick(View v) {
        if (reqCode==10) {
            Intent int5 = getIntent();
            int5.putExtra("RETURN_VAL","REturn Value for Main Activity");
            setResult(Activity.RESULT_OK,int5);
        }
        finish() ;
    }
}
