package com.example.rajat.new_feedback;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class register extends AppCompatActivity  implements View.OnClickListener {
    Button button;
    EditText u, id, no, p1, cp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        button = (Button) findViewById(R.id.button);
        u = (EditText) findViewById(R.id.u);
        id = (EditText) findViewById(R.id.id);
        no = (EditText) findViewById(R.id.no);
        p1 = (EditText) findViewById(R.id.p1);
        cp = (EditText) findViewById(R.id.cp);
        button.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                break;
        }

    }
}
