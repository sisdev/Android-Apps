package in.sisoft.myreadapk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;


/* Linux SeLinux Command Reference
https://github.com/jackpal/Android-Terminal-Emulator/wiki/Android-Shell-Command-Reference
http://www.androidcentral.com/android-201-10-basic-terminal-commands-you-should-know
http://www.all-things-android.com/content/se-android-commands
http://www.all-things-android.com/content/android-toolbox-command-reference

 */

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "TEST";

    EditText edt ;
    TextView tv ;

    static{
        System.loadLibrary("helloFromC");
    }

//    public native String stringFromJNI();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edt = (EditText) findViewById(R.id.edt1);
        tv = (TextView) findViewById(R.id.textView);
       // Toolbox
    }

    public void listApkDir(View v)
    {
        StringBuffer output = new StringBuffer();
        Runtime r1 = Runtime.getRuntime();
        String str1 = edt.getText().toString();
        try {
            Process p = r1.exec(str1);
            p.waitFor();
            int exitVal = p.exitValue();
            if (exitVal == 0) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    output.append(line + "\n");
                }
            }
            else
            {
                BufferedReader reader = new BufferedReader(new InputStreamReader(p.getErrorStream()));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    output.append(line + "\n");
                }
            }
        }
        catch (Exception e1)
        {
            Log.d(TAG, e1.getMessage());
        }

        Log.d(TAG,output.toString());

        tv.setText(output.toString());
        /*File appsDir = new File("/data/app");

        String[] files = appsDir.list();

        for (int i = 0 ; i < files.length ; i++ ) {
            Log.d(TAG, "File: "+files[i]);

        }*/
    }

    public void testSU(View v)
    {
        StringBuffer output = new StringBuffer();
        Runtime r1 = Runtime.getRuntime();
        String str1 = edt.getText().toString();
        try {
            Process p = r1.exec("su");
            p.waitFor();
            int exitVal = p.exitValue();
            if (exitVal == 0) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    output.append(line + "\n");
                }
            }
            else
            {
                BufferedReader reader = new BufferedReader(new InputStreamReader(p.getErrorStream()));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    output.append(line + "\n");
                }
            }
        }
        catch (Exception e1)
        {
            Log.d(TAG, e1.getMessage());
        }

        Log.d(TAG,output.toString());

        tv.setText(output.toString());
    }
}
