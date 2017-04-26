package com.example.rajat.new_feedback;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button logout;
    EditText u, id, no;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        u = (EditText) findViewById(R.id.u);
        id = (EditText) findViewById(R.id.id);
        no = (EditText) findViewById(R.id.no);
        logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logout:
                startActivity(new Intent(this, login.class));
                break;
        }
    }
}
