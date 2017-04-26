package casaneeds.appLaunch;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginScreen extends AppCompatActivity {

    EditText _emailText;
    EditText _passwordText;
    Button _loginButton;

    String email, password;

    SharedPreferences sp;
    SharedPreferences.Editor editor;


    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

         _emailText = (EditText) findViewById(R.id.input_email);
        _passwordText = (EditText) findViewById(R.id.input_password);
        _loginButton = (Button) findViewById(R.id.btn_login);

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                email = _emailText.getText().toString();
                password = _passwordText.getText().toString();

                ConnectivityManager connMgr = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                android.net.NetworkInfo activeNetwork = connMgr.getActiveNetworkInfo() ;

                if (activeNetwork==null)
                {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(LoginScreen.this);
                    alertBuilder.setTitle("Warning");
                    alertBuilder.setMessage("No Internet Connection Available");
                    alertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener()  {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(LoginScreen.this, "Connect with Internet.. and Retry..", Toast.LENGTH_LONG).show();
                            dialog.cancel();  }; });

                    AlertDialog ad = alertBuilder.create();
                    ad.show() ;
                }
                else {
                    LoginAsync loginTask = new LoginAsync(LoginScreen.this);
                    loginTask.execute(new String[]{email, password});
                }

            }
        });
    }
}





