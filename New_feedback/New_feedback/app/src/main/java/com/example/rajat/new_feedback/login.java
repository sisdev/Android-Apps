package com.example.rajat.new_feedback;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class login extends AppCompatActivity implements View.OnClickListener {
    Button login;
    EditText luser, lpswd;
    TextView newUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        luser = (EditText) findViewById(R.id.luser);
        lpswd = (EditText) findViewById(R.id.lpswd);
        login = (Button) findViewById(R.id.login);
        newUser = (TextView) findViewById(R.id.newUser);
        login.setOnClickListener(this);
        newUser.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                break;
            case R.id.newUser:
                startActivity(new Intent(this, register.class));
                break;
        }
    }
}