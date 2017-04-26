package in.sisoft.firstproj;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn1, btn2, btn3, btn4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1 = (Button) findViewById(R.id.button);
        btn2 = (Button) findViewById(R.id.button2);
        btn3 = (Button) findViewById(R.id.button3);
        btn4 = (Button) findViewById(R.id.button4);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Button1 Clicked", Toast.LENGTH_LONG).show();

            }
        });

        btnClicked bc = new btnClicked(this);
        btn4.setOnClickListener(bc);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                Toast.makeText(this, "Button1 Clicked", Toast.LENGTH_LONG).show();
                break;
            case R.id.button2:
                Toast.makeText(this, "Button2 Clicked", Toast.LENGTH_LONG).show();
                break;

        }
    }
}

class btnClicked implements View.OnClickListener {

    Context ctx ;
    btnClicked(Context c)
    {
        ctx = c ;
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(ctx, "Button4 Clicked", Toast.LENGTH_LONG).show();

    }
}